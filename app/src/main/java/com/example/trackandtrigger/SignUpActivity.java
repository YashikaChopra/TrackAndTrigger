package com.example.trackandtrigger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.concurrent.TimeUnit;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    private FirebaseAuth mAuth;
    EditText username, email, password, repassword, emailOTP;
    Button createAccount, sendEmail, verifyEmail, resendEmail;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String TAG = "EmailPswdSignUp";

    private boolean emailVerified = false;
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
        //buttons for resending otp
        resendEmail = (Button)findViewById(R.id.b_resendEmail);
        //otps
        emailOTP = (EditText)findViewById(R.id.emailOTP);

        //Text change listeners
        email.addTextChangedListener(this);
        repassword.addTextChangedListener(this);

        //OnClickListeners
        createAccount.setOnClickListener(this);
        sendEmail.setOnClickListener(this);
        verifyEmail.setOnClickListener(this);
        resendEmail.setOnClickListener(this);

        //createAccount.setEnabled(false);
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
        Intent i1 = new Intent(SignUpActivity.this, MainActivity.class);*/
    }

    //functions for OnClickListeners
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
                }
                break;
            case R.id.b_resendEmail:
                //sendEmailVerification();
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

