package com.hakim.singtel.localizationapp;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

/**
 * Created by hakimhauston on 14/9/19.
 */

public class LocalisedTextView extends LinearLayout {

    private TextView m_targetText;
    private Button m_btnEnglish;
    private Button m_btnChinese;
    private Button m_btnHindi;

    private String m_textKey;
    private int m_iTextResId;

    private String m_szLocalizationKey;

    private int m_iId;

    public LocalisedTextView(Context context) {
        this(context,null);
    }

    public LocalisedTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LocalisedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LocalisedTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize(attrs);
    }

    private void changeTextToLang(LocalizationManager.LANGUAGE_SELECTION lang) {
        String szLocalizedString = LocalizationManager.GetInstance().GetLocalizedString(lang, m_textKey);
        m_targetText.setText(szLocalizedString);
    }

    private void initialize(AttributeSet attrs) {
        View v = inflate(getContext(), R.layout.localised_text_view, this);

        m_targetText = (TextView)v.findViewById(R.id.targetText);
        m_btnEnglish = (Button)v.findViewById(R.id.btnEnglish);
        m_btnChinese = (Button)v.findViewById(R.id.btnChinese);
        m_btnHindi = (Button)v.findViewById(R.id.btnHindi);

        m_btnEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeTextToLang(LocalizationManager.LANGUAGE_SELECTION.LANG_EN);
            }
        });

        m_btnChinese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeTextToLang(LocalizationManager.LANGUAGE_SELECTION.LANG_ZH);
            }
        });

        m_btnHindi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeTextToLang(LocalizationManager.LANGUAGE_SELECTION.LANG_HI);
            }
        });

        if ( attrs == null ) {
            return;
        }

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.LocalisedTextView);
        m_textKey = typedArray.getString(R.styleable.LocalisedTextView_textresid);
        Log.d(this.getClass().getName(), "textresid: " + m_textKey);

        m_iTextResId = getResources().getIdentifier(m_textKey, "string", getContext().getPackageName());
        String szResValue = getResources().getString(m_iTextResId);
        Log.d(this.getClass().getName(), "id: " + m_iTextResId + " value: " + szResValue);
        m_targetText.setText(szResValue);

//        m_targetText.setText(szId);
//        m_targetText.setText(getResources().getString(m_iId));

        typedArray.recycle();

    }


}
