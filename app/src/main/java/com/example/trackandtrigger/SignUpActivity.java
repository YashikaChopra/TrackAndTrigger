package com.example.trackandtrigger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.security.AccessController;
import java.security.Provider;
import java.security.Security;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import static com.facebook.FacebookSdk.getApplicationContext;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    private FirebaseAuth mAuth;
    EditText username, email, password, repassword, emailOTP;
    Button createAccount, sendEmail, verifyEmail;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String TAG = "EmailPswdSignUp";

    private boolean emailVerified = false;
    private String otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        //text fields
        username = (EditText)findViewById(R.id.UserName);
        email = (EditText)findViewById(R.id.EmailAddress);
        password = (EditText)findViewById(R.id.Password);
        repassword = (EditText)findViewById(R.id.RePassword);
        //create account button
        createAccount = (Button)findViewById(R.id.CreateAccount);
        //buttons for sending otp
        sendEmail = (Button)findViewById(R.id.b_sendEmail);
        //buttons for verifying otp
        verifyEmail = (Button)findViewById(R.id.b_verifyEmail);
        //otps
        emailOTP = (EditText)findViewById(R.id.emailOTP);

        //Text change listeners
        email.addTextChangedListener(this);
        repassword.addTextChangedListener(this);

        //OnClickListeners
        createAccount.setOnClickListener(this);
        sendEmail.setOnClickListener(this);
        verifyEmail.setOnClickListener(this);

        createAccount.setEnabled(false);

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }

    //check if password and re password match
    //check if email id matches regex pattern
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}
    @Override
    public void afterTextChanged(Editable s) {
        String pswd = password.getText().toString();
        String re_pswd = repassword.getText().toString();
        if(!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches())
        {
            email.setError("invalid email ID");
        }
        if(!pswd.equals(re_pswd))
        {
            repassword.setError("Passwords don't match");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null)
        {
            Intent i= new Intent(SignUpActivity.this, PhoneVerificationActivity.class);
            startActivity(i);
        }
        /*// Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Intent i1 = new Intent(SignUpActivity.this, CategoryActivity.class);*/
    }

    //functions for OnClickListeners
    @SuppressLint("StaticFieldLeak")
    @Override
    public void onClick(View v) {
        boolean flag1=true;
        boolean flag2=false;
            switch(v.getId())
            {
                case R.id.b_sendEmail:
                if(email.getText().toString().isEmpty()) {
                    email.setError("This field cannot be empty");
                    flag1=false;
                    //Toast.makeText(getApplicationContext(), "Enter email address", Toast.LENGTH_SHORT).show();
                    //return;
                }
                if(password.getText().toString().isEmpty()){
                    password.setError("This field cannot be empty");
                    flag1=false;
                    //Toast.makeText(getApplicationContext(), "Enter password", Toast.LENGTH_SHORT).show();
                    //return;
                }
                if(repassword.getText().toString().isEmpty()){
                    repassword.setError("This field cannot be empty");
                    flag1=false;
                    //Toast.makeText(getApplicationContext(), "Enter password", Toast.LENGTH_SHORT).show();
                    //return;
                }
                if(flag1) {
                    final GMailSender1 sender = new GMailSender1("manasabollavaram2000@gmail.com", "Bits@1234");
                    Random rnd = new Random();
                    int number = rnd.nextInt(999999);
                    otp = Integer.toString(number);
                    new AsyncTask<Void, Void, Void>() {
                            @Override public Void doInBackground(Void... arg) {
                                try {
                                    sender.sendMail("TrackAndTrigger OTP Verification",
                                            otp+" is your OTP",
                                            "manasabollavaram2000@gmail.com",
                                            email.getText().toString());
                                } catch (Exception e) {
                                    Log.e("SendMail", e.getMessage(), e);
                                }
                                return null;}
                        }.execute();
                }
                break;
            /*case R.id.b_resendEmail:
                //sendEmailVerification();
                break;*/
                case R.id.b_verifyEmail:
                    if(emailOTP.getText().toString().equals(otp))
                    {
                        createAccount.setEnabled(true);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Incorrect OTP", Toast.LENGTH_SHORT).show();
                    }
                    break;
            case R.id.CreateAccount:
                createAccount(email.getText().toString(),password.getText().toString());

        }
    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        Toast.makeText(getApplicationContext(), "Creating Account", Toast.LENGTH_SHORT).show();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                            Intent i = new Intent(SignUpActivity.this, PhoneVerificationActivity.class);
                            startActivity(i);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
        }
};

class GMailSender1 extends javax.mail.Authenticator {
/*
    private String mailhost = "smtp.gmail.com";
*/
    private final String username;
    private final String password;
    private final Session session;

    static {
        Security.addProvider(new JSSEProvider());
    }

    public GMailSender1(String user, String password) {
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
                        Message message = new MimeMessage(session);
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

final class JSSEProvider1 extends Provider {

    public JSSEProvider1() {
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
