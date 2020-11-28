package com.example.trackandtrigger;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.security.AccessController;
import java.security.Provider;
import java.security.Security;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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

                final GMailSender sender = new GMailSender("manasabollavaram2000@gmail.com", "Bits@1234");
                new AsyncTask<Void, Void, Void>() {
                    @SuppressLint("StaticFieldLeak")
                    @Override public Void doInBackground(Void... arg) {
                        try {
                            sender.sendMail(etSubject.getText().toString(),
                                    etMessage.getText().toString(),
                                    "manasabollavaram2000@gmail.com",
                                    etTo.getText().toString());
                        } catch (Exception e) {
                            Log.e("SendMail", e.getMessage(), e);
                        }
                        return null;}
                }.execute();

            }
        });
    }}

class GMailSender extends javax.mail.Authenticator {
    /*
        private String mailhost = "smtp.gmail.com";
    */
    private final String username;
    private final String password;
    private final Session session;

    static {
        Security.addProvider(new JSSEProvider());
    }

    public GMailSender(String user, String password) {
        this.username = user;
        this.password = password;

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        //session = Session.getDefaultInstance(props, this);
        session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
    }

    public synchronized void sendMail(String subject, String body, String sender, String recipients) throws Exception {
        try {
            javax.mail.Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("manasabollavaram2000@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(recipients));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            //Toast.makeText(getApplicationContext(), "Mail sent", Toast.LENGTH_SHORT).show();
            //System.out.println("Done");

        } catch (MessagingException e) {
            //throw new RuntimeException(e);
            //Toast.makeText(getApplicationContext(), "Unable to send mail", Toast.LENGTH_SHORT).show();
        }

    }
}

final class JSSEProvider extends Provider {

    public JSSEProvider() {
        super("HarmonyJSSE", 1.0, "Harmony JSSE Provider");
        AccessController.doPrivileged(new java.security.PrivilegedAction<Void>() {
            public Void run() {
                put("SSLContext.TLS",
                        "org.apache.harmony.xnet.provider.jsse.SSLContextImpl");
                put("Alg.Alias.SSLContext.TLSv1", "TLS");
                put("KeyManagerFactory.X509",
                        "org.apache.harmony.xnet.provider.jsse.KeyManagerFactoryImpl");
                put("TrustManagerFactory.X509",
                        "org.apache.harmony.xnet.provider.jsse.TrustManagerFactoryImpl");
                return null;
            }
        });
    }
}