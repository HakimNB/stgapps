package com.hakim.singtel.uiapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public TextView m_textView;
    public EditText m_editText;
    public Button m_btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m_textView = (TextView)findViewById(R.id.textView);
        m_editText = (EditText)findViewById(R.id.editText);
        m_btnSubmit = (Button)findViewById(R.id.btnSubmit);
        m_btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String szText = m_editText.getText().toString();
                Log.d("frontendapp", "text is: " + szText);
                m_textView.setText(szText);
            }
        });
    }
}
