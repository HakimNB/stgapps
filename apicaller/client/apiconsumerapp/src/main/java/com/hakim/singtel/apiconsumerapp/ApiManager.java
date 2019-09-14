package com.hakim.singtel.apiconsumerapp;

import android.content.Context;
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
import java.util.function.Consumer;
import java.util.function.Function;

public class ApiManager {

    public interface OnApiCallback {
        public void success(String result);
        public void failed(Exception e);
    }

    private RequestQueue m_requestQueue = null;

    public void initialize(Context context) {
        m_requestQueue = Volley.newRequestQueue(context);
    }

    // poor thing.. can't use before update my dev env
//    public void PostJsonAPIRequest(String apiEndpoint, Consumer<String> successCallback, Consumer<Exception> failedCallback)
//    {
//        if ( m_requestQueue == null ) {
//            if ( failedCallback != null ) {
//                Exception e = new IllegalStateException("Please call initialize first!");
//                failedCallback.accept(e);
//            }
//        }
//    }
//
//    public void PostJsonAPIRequestFunc(String apiEndpoint, Function<String, String> successCallback, Function<String, Exception> failedCallback)
//    {
//        if ( failedCallback != null ) {
//            failedCallback.apply("yeah");
//        }
//    }

    public void PostJsonAPIRequest(final String szApiEndpoint, final String szPayload, final OnApiCallback callback) {
        if ( m_requestQueue == null ) {
            if ( callback != null ) {
                callback.failed(new IllegalAccessException("Please call initialize first!"));
            }
            return;
        }

        String szUrl = Constant.SERVER_URL + szApiEndpoint;
        LogManager.Debug(this.getClass().getName(), "PostJsonAPIRequest URL: " + szUrl);
        LogManager.Debug(this.getClass().getName(), "PostJsonAPIRequest Payload: " + szPayload);
        StringRequest request = new StringRequest(Request.Method.POST, szUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                LogManager.Debug(this.getClass().getName(), response);
                if (callback != null) {
                    callback.success(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LogManager.Error(this.getClass().getName(), error.getMessage(), error);
            }
        }) {
            @Override
            public byte[] getBody() {
                byte[] data = null;
                try {
                    data = szPayload.getBytes();
                } catch ( Exception e ) {
                    LogManager.Error(this.getClass().getName(), e.getMessage(), e);
                }
                return data;
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        m_requestQueue.add(request);
    }

}
