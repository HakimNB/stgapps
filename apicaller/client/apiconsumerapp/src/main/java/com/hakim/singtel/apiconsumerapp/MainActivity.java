package com.hakim.singtel.apiconsumerapp;

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
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("data", text);
            String szJson = new JSONObject(params).toString();
            m_apiManager.PostJsonAPIRequest(Constant.ECHO_PATH, szJson, new ApiManager.OnApiCallback() {
                @Override
                public void success(String result) {
                    m_textView.setText(result);
                }

                @Override
                public void failed(Exception e) {
                    m_textView.setText(e.toString());
                }
            });

        }
    }
}
