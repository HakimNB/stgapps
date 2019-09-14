package com.hakim.singtel.apiconsumerapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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
    private AlertDialog m_alertDialog;

    private ApiManager m_apiManager = new ApiManager();

    private void initialize() {
        m_textView = (TextView)findViewById(R.id.textView);
        m_apiManager.initialize(this);
    }

    public void showAlert(String szMessage)
    {
        // show popup
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Result")
                .setMessage(szMessage);

        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int code) {
                dismissAlert();
            }
        };
        builder.setPositiveButton("OK", (DialogInterface.OnClickListener) onClickListener);
        m_alertDialog = builder.create();
        m_alertDialog.show();

    }

    private void dismissAlert()
    {
        if ( m_alertDialog != null ) {
            m_alertDialog.dismiss();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();

        Intent intent = getIntent();
        if ( intent != null ) {
            final String text = intent.getStringExtra(Intent.EXTRA_TEXT);
//            showAlert(text);
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
                        showAlert(data);
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
    }
}
