package com.hakim.singtel.apiconsumerapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private TextView m_textView;

    private ApiManager m_apiManager = new ApiManager();

    private void initialize() {
        m_textView = (TextView)findViewById(R.id.textView);
        m_apiManager.initialize(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();

        Intent intent = getIntent();
        if ( intent != null ) {
            final String text = intent.getStringExtra(Intent.EXTRA_TEXT);
            final Bundle bundle = intent.getExtras();

            boolean bHasText = text != null && text.length() > 0;
            boolean bHasBundle = bundle != null;

//            if ( text == null || text.length() == 0 && bundle == null ) {
//                return;
//            }

            if ( !bHasText && !bHasBundle ) {
                // first startup
                return;
            }

            if ( bHasText ) {
                processEcho(text);
            } else if ( bHasBundle ) {
                processCalculation(bundle);
            }

        }
    }

    private void processEcho(String text) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("data", text);
        String szJson = new JSONObject(params).toString();
        final ProgressDialog loadingIndicator = new ProgressDialog(this);
        loadingIndicator.setMessage("Loading from API Server");
        loadingIndicator.setCancelable(false);
        loadingIndicator.show();

        m_apiManager.PostJsonAPIRequest(Constant.ECHO_PATH, szJson, new ApiManager.OnApiCallback() {
            @Override
            public void success(String result) {
                loadingIndicator.dismiss();
                m_textView.setText(result);

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String data = jsonObject.getString("data");
                    LogManager.Debug(this.getClass().getName(), "data: " + data);
//                        showAlert(data);
                    openUiApp(data);
                }
                catch ( Exception e ) {
                    LogManager.Error(this.getClass().getName(), e.getMessage(), e);
                }
            }

            @Override
            public void failed(Exception e) {
                loadingIndicator.dismiss();
                m_textView.setText(e.toString());
            }
        });
    }

    private void processCalculation(Bundle bundle) {
        final String szCommand = bundle.getString("command");
        String szNum1 = bundle.getString("num1");
        String szNum2 = bundle.getString("num2");

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("num1", szNum1);
        params.put("num2", szNum2);

        String szJson = new JSONObject(params).toString();
        final ProgressDialog loadingIndicator = new ProgressDialog(this);
        loadingIndicator.setMessage("Loading calculation result from API Server");
        loadingIndicator.setCancelable(false);
        loadingIndicator.show();

        m_apiManager.PostJsonAPIRequest(szCommand, szJson, new ApiManager.OnApiCallback() {
            @Override
            public void success(String result) {
                loadingIndicator.dismiss();
                m_textView.setText(result);

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String data = jsonObject.getString("result");
                    LogManager.Debug(this.getClass().getName(), "result: " + data);
//                        showAlert(data);
                    openUiApp(szCommand + " result: " + data);
                }
                catch ( Exception e ) {
                    LogManager.Error(this.getClass().getName(), e.getMessage(), e);
                }
            }

            @Override
            public void failed(Exception e) {
                loadingIndicator.dismiss();
                m_textView.setText(e.toString());
            }
        });
    }

    public void openUiApp(final String szText){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.setClassName("com.hakim.singtel.uiapp", "com.hakim.singtel.uiapp.MainActivity");
        sendIntent.putExtra(Intent.EXTRA_TEXT, szText);
        try {
            startActivity(sendIntent);
        } catch (ActivityNotFoundException anfe) {
            // user doesn't have apiconsumer app installed on his phone
            LogManager.Debug(this.getClass().getName(), "activity not found");
            Intent shareIntent = Intent.createChooser(sendIntent, "Please install uiapp");
            startActivity(shareIntent);
        }
    }
}
