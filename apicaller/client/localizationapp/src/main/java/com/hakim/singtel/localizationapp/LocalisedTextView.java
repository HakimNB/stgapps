package com.hakim.singtel.localizationapp;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by hakimhauston on 14/9/19.
 */

public class LocalisedTextView extends LinearLayout {

    private TextView m_targetText;
    private Button m_btnEnglish;
    private Button m_btnChinese;
    private Button m_btnHindi;

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

    private void initialize(AttributeSet attrs) {
        View v = inflate(getContext(), R.layout.localised_text_view, this);

        m_targetText = (TextView)v.findViewById(R.id.targetText);
        m_btnEnglish = (Button)v.findViewById(R.id.btnEnglish);
        m_btnChinese = (Button)v.findViewById(R.id.btnChinese);
        m_btnHindi = (Button)v.findViewById(R.id.btnHindi);

        if ( attrs == null ) {
            return;
        }

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.LocalisedTextView);
        String szId = typedArray.getString(R.styleable.LocalisedTextView_textresid);
        Log.d(this.getClass().getName(), "textresid: " + szId);

        int iResId = getResources().getIdentifier(szId, "string", getContext().getPackageName());
        String szResValue = getResources().getString(iResId);
        Log.d(this.getClass().getName(), "id: " + iResId + " value: " + szResValue);
        m_targetText.setText(szResValue);

//        m_targetText.setText(szId);
//        m_targetText.setText(getResources().getString(m_iId));

        typedArray.recycle();

    }


}
