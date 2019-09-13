package com.hakim.singtel.apiconsumerapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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
            String text = intent.getStringExtra(Intent.EXTRA_TEXT);

            StringRequest request = new StringRequest(Request.Method.POST, "https://postman-echo.com/post", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // Display the first 500 characters of the response string.
                    m_textView.setText(response.toString());
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    m_textView.setText("Error! " + error.getMessage());
                }
            });
            m_requestQueue.add(request);
        }
    }
}
