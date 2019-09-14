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

//    public TextView m_textTarget;
//    public Button m_btnChinese;

    public LocalisedTextView m_textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localization_main);

        m_textView = (LocalisedTextView)findViewById(R.id.ltv);
//        m_textTarget = (TextView)findViewById(R.id.helloworld);
//        m_btnChinese = (Button)findViewById(R.id.btnChinese);
//
//        m_btnChinese.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String szChinese = getResources().getString(R.string.helloworld);
//                szChinese = getStringByLocale(R.string.helloworld, "zh");
//                Log.d(this.getClass().getName(), "STRING: " + szChinese);
//                m_textTarget.setText(szChinese);
//            }
//        });
    }

    public String getStringByLocale(int id, String locale) {
        Configuration configuration = this.getResources().getConfiguration();
        Log.d(this.getClass().getName(), "PRE LOCALE: " + configuration.locale.toString());
        Locale loc = new Locale(locale);
        configuration.setLocale(loc);
        Log.d(this.getClass().getName(), "LOCALE: " + loc.toString());
        Log.d(this.getClass().getName(), "POST LOCALE: " + configuration.locale.toString());
        return createConfigurationContext(configuration).getResources().getString(id);
    }
}
