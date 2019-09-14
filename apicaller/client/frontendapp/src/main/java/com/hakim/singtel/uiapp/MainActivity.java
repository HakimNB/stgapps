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

    public EditText m_num1;
    public EditText m_num2;
    public Button m_btnAdd;
    public Button m_btnSubstract;
    public Button m_btnMultiply;
    public Button m_btnDivide;

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

        // TODO: handle view objects nicely
        m_num1 = (EditText)findViewById(R.id.num1);
        m_num2 = (EditText)findViewById(R.id.num2);
        m_btnAdd = (Button)findViewById(R.id.btnAdd);
        m_btnSubstract = (Button)findViewById(R.id.btnSubstract);
        m_btnMultiply = (Button)findViewById(R.id.btnMultiply);
        m_btnDivide = (Button)findViewById(R.id.btnDivide);

        m_btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int num1 = Integer.parseInt(m_num1.getText().toString());
                    int num2 = Integer.parseInt(m_num2.getText().toString());
                    openApiConsumerAppForCalc("add", num1, num2);
                } catch ( Exception e ) {
                    showAlert(e.getMessage());
                }
            }
        });

        m_btnSubstract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int num1 = Integer.parseInt(m_num1.getText().toString());
                    int num2 = Integer.parseInt(m_num2.getText().toString());
                    openApiConsumerAppForCalc("substract", num1, num2);
                } catch ( Exception e ) {
                    showAlert(e.getMessage());
                }
            }
        });

        m_btnMultiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int num1 = Integer.parseInt(m_num1.getText().toString());
                    int num2 = Integer.parseInt(m_num2.getText().toString());
                    openApiConsumerAppForCalc("multiply", num1, num2);
                } catch ( Exception e ) {
                    showAlert(e.getMessage());
                }
            }
        });

        m_btnDivide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int num1 = Integer.parseInt(m_num1.getText().toString());
                    int num2 = Integer.parseInt(m_num2.getText().toString());
                    openApiConsumerAppForCalc("divide", num1, num2);
                } catch ( Exception e ) {
                    showAlert(e.getMessage());
                }
            }
        });

        // handle result if any
        Intent intent = getIntent();
        if ( intent != null ) {
            final String szResult = intent.getStringExtra(Intent.EXTRA_TEXT);
            final Bundle bundleResult = intent.getExtras();

            if ( szResult != null && szResult.length() > 0 ) {
                // handle echo result
                showAlert(szResult);
            } else if ( bundleResult != null ) {
                // handle calc result
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

    public void openApiConsumerAppForCalc(String szCommand, int iNum1, int iNum2)  {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.setClassName("com.hakim.singtel.apiconsumerapp", "com.hakim.singtel.apiconsumerapp.MainActivity");
        Bundle bundle = new Bundle();
        bundle.putString("command", szCommand);
        bundle.putString("num1", "" + iNum1);
        bundle.putString("num2", "" + iNum2);
        sendIntent.putExtras(bundle);
        try {
            Log.d("frontendapp", "openApiConsumerAppForCalc: " + szCommand + ": " + iNum1 + ", " + iNum2);
            startActivity(sendIntent);
        } catch (ActivityNotFoundException anfe) {
            // user doesn't have apiconsumer app installed on his phone
            Log.d("frontendapp", "activity not found exception");
            Intent shareIntent = Intent.createChooser(sendIntent, "Please install apiconsumerapp");
            startActivity(shareIntent);
        }
    }
}
