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

    private RequestQueue m_requestQueue;

    private void initialize() {
        m_requestQueue = Volley.newRequestQueue(this);
        m_textView = (TextView)findViewById(R.id.textView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();

        Intent intent = getIntent();
        if ( intent != null ) {
            final String text = intent.getStringExtra(Intent.EXTRA_TEXT);

            StringRequest request = new StringRequest(Request.Method.POST, Constant.SERVER_URL + Constant.ECHO_PATH, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("ApiConsumer", "response: " + response);
                    m_textView.setText(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("ApiConsumer", "error: " + error.getMessage(), error);
                    m_textView.setText(error.toString());
                }
            }) {
                @Override
                public byte[] getBody() {
                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("data", text);
                    return new JSONObject(params).toString().getBytes();
                }

                @Override
                public String getBodyContentType() {
                    return "application/json";
                }
            };
            m_requestQueue.add(request);
        }
    }
}
