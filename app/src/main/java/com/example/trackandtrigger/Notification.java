package com.example.trackandtrigger;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class
Notification extends AppCompatActivity {
    private EditText etTo, etSubject,etMessage;
    private Button btSend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        etTo = findViewById(R.id.et_to);
        etSubject = findViewById(R.id.et_subject);
        etMessage = findViewById(R.id.et_message);
        btSend = (Button) findViewById(R.id.bt_send);

        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    JavaMailAPI sender = new JavaMailAPI("Your Email ID", "Your Email Password");
                    sender.sendMail(etSubject.getText().toString(),
                            etMessage.getText().toString(), "Your Email ID",
                            etTo.getText().toString());

                    Toast.makeText(com.example.trackandtrigger.Notification.this, "Mail Send", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    Log.e("Mail", e.getMessage(), e);
                }

            }
        });
    }}

   /*
    private void sendEmail() {
        String mEmail = etTo.getText().toString();
        String mSubject = etSubject.getText().toString();
        String mMessage = etMessage.getText().toString();


        JavaMailAPI javaMailAPI = new JavaMailAPI(this, mEmail, mSubject, mMessage);

        javaMailAPI.execute();
    }
}*/
