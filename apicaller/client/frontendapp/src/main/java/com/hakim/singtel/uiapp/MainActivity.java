package com.hakim.singtel.uiapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

//    public TextView m_textView;
    public EditText m_editText;
    public Button m_btnSubmit;

    private AlertDialog m_alertDialog;

    public void showAlert(String szMessage)
    {
        // show popup
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("This is the result")
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

//        m_textView = (TextView)findViewById(R.id.textView);
        m_editText = (EditText)findViewById(R.id.editText);
        m_btnSubmit = (Button)findViewById(R.id.btnSubmit);
        m_btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String szText = m_editText.getText().toString();
                Log.d("frontendapp", "text is: " + szText);
//                m_textView.setText(szText);
                openApiConsumerApp(szText);
            }
        });

        Intent intent = getIntent();
        if ( intent != null ) {
            final String szResult = intent.getStringExtra(Intent.EXTRA_TEXT);
            if ( szResult != null && szResult.length() > 0 ) {
                showAlert(szResult);
            }
        }
    }

    public void openApiConsumerApp(String szText){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.setClassName("com.hakim.singtel.apiconsumerapp", "com.hakim.singtel.apiconsumerapp.MainActivity");
        sendIntent.putExtra(Intent.EXTRA_TEXT, szText);
        try {
            startActivity(sendIntent);
        } catch (ActivityNotFoundException anfe) {
            // user doesn't have apiconsumer app installed on his phone
            Log.d("frontendapp", "activity not found exception");
            Intent shareIntent = Intent.createChooser(sendIntent, "Please install apiconsumerapp");
            startActivity(shareIntent);
        }
    }
}
