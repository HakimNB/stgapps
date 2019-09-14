package com.hakim.singtel.localizationapp;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class LocalizationMainActivity extends AppCompatActivity {

    public LocalisedTextView m_textView;

    private ApiManager m_apiManager = new ApiManager();
    private LocalizationManager m_locManager = null;

    private static LocalizationMainActivity m_instance = null;
    public static LocalizationMainActivity getInstance() { return m_instance; }

    private String m_szLocalizationFile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localization_main);

        m_textView = (LocalisedTextView)findViewById(R.id.ltv);

        m_apiManager.initialize(this);

        m_locManager = new LocalizationManager(this);

        m_instance = this;

        loadLocalizationFile();
    }

    public String GetLocalizationFile() {
        return m_szLocalizationFile;
    }

    private void loadLocalizationFile() {
        // for big file, might need to stream or cache to local sqlite
        m_apiManager.GetJsonAPIRequest(Constant.LOC_PATH, "", new ApiManager.OnApiCallback() {
            @Override
            public void success(String result) {
                LogManager.Debug(this.getClass().getName(), "get localization file success: " + result);
                m_szLocalizationFile = result;
                m_locManager.Initialize(m_szLocalizationFile);
            }

            @Override
            public void failed(Exception e) {
                LogManager.Error(this.getClass().getName(), "get localization file failed", e);
            }
        });
    }

}
