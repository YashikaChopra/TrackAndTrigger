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
import com.google.firebase.FirebaseException;
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

public class PhoneVerificationActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    private FirebaseAuth mAuth;
    private boolean mVerificationInProgress = false;
    private PhoneAuthProvider.ForceResendingToken resendToken;
    private String verificationID;
    String phonePattern = "[0-9]{10}";
    EditText phone, phoneOTP;
    Button sendPhone, verifyPhone, resendPhone;
    boolean phoneVerified = false;
    String TAG = "PhoneVerify";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verification);

        phone = (EditText)findViewById(R.id.Phone);
        phoneOTP = (EditText)findViewById(R.id.phoneOTP);
        sendPhone = (Button)findViewById(R.id.b_sendPhone);
        verifyPhone = (Button)findViewById(R.id.b_verifyPhone);
        resendPhone = (Button)findViewById(R.id.b_resendPhone);

        phone.addTextChangedListener(this);
        verifyPhone.setOnClickListener(this);
        sendPhone.setOnClickListener(this);
        resendPhone.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

    @Override
    public void afterTextChanged(Editable editable) {
        if(!Patterns.PHONE.matcher(phone.getText().toString()).matches())
        {
            phone.setError("Invalid phone no.");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.b_sendPhone:

                if (phone.getText().toString().isEmpty()) {
                    phone.setError("This field cannot be empty");
                    return;
                }

                /*sendPhone.setVisibility(View.INVISIBLE);
                phoneOTP.setVisibility(View.VISIBLE);
                verifyPhone.setVisibility(View.VISIBLE);
                resendPhone.setVisibility(View.VISIBLE);*/
                startPhoneNumberVerification(phone.getText().toString());
                break;

            case R.id.b_verifyPhone:

                String code = phoneOTP.getText().toString();
                if (TextUtils.isEmpty(code)) {
                    phoneOTP.setError("Cannot be empty");
                    return;
                }
                verifyPhoneNumberWithCode(verificationID, code);
                break;
            case R.id.b_resendPhone:

                resendVerificationCode(phone.getText().toString(), resendToken);
                break;

        }
    }
    //PHONE NUMBER VERIFICATION CODE
    private void startPhoneNumberVerification(String phoneNumber) {
        // [START start_phone_auth]
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        verifyPhone.setEnabled(true);
        PhoneAuthProvider.verifyPhoneNumber(options);
        // [END start_phone_auth]

        //mVerificationInProgress = true;
    }

    private void verifyVerificationCode(String code) {
        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(this.verificationID, code);
        //signInWithPhoneAuthCredential(credential);
        linkCredential(credential);
    }
    // [END phone_auth_callbacks]

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        // [START verify_with_code]
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        // [END verify_with_code]
        //signInWithPhoneAuthCredential(credential);
        linkCredential(credential);
    }

    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .setForceResendingToken(token)     // ForceResendingToken from callbacks
                        .build();
        verifyPhone.setEnabled(true);
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    // [START sign_in_with_phone]
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            //updateUI(STATE_SIGNIN_SUCCESS, user);
                            Intent i = new Intent(PhoneVerificationActivity.this, MainActivity.class);
                            startActivity(i);
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                // [START_EXCLUDE silent]
                                phoneOTP.setError("Invalid code.");
                                // [END_EXCLUDE]
                            }
                            //updateUI(STATE_SIGNIN_FAILED);
                            phoneOTP.setText("");
                            verifyPhone.setEnabled(false);
                        }
                    }
                });
    }
    // [END sign_in_with_phone]

    // Initialize phone auth callbacks
    // [START phone_auth_callbacks]
    //the callback to detect the verification status
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                    //Getting the code sent by SMS
                    String code = phoneAuthCredential.getSmsCode();

                    //sometime the code is not detected automatically
                    //in this case the code will be null
                    //so user has to manually enter the code
                    if (code != null) {
                        phoneOTP.setText(code);
                        //verifying the code
                        verifyVerificationCode(code);
                    }
                }

                @Override
                public void onVerificationFailed(FirebaseException e) {
                    Toast.makeText(PhoneVerificationActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.i(TAG, "onVerificationFailed: " + e.getLocalizedMessage());
                }

                //when the code is generated then this method will receive the code.
                @Override
                public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//                super.onCodeSent(s, forceResendingToken);

                    //storing the verification id that is sent to the user
                    verificationID = s;
                }
            };
    public void linkCredential(AuthCredential credential) {
        mAuth.getCurrentUser().linkWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "linkWithCredential:success");
                            FirebaseUser user = task.getResult().getUser();
                            Toast.makeText(PhoneVerificationActivity.this, "Merged", Toast.LENGTH_SHORT).show();
                            Intent i= new Intent(PhoneVerificationActivity.this, MainActivity.class);
                            startActivity(i);
                        } else {
                            Log.w(TAG, "linkWithCredential:failure", task.getException());
                            Toast.makeText(PhoneVerificationActivity.this, "Failed to merge" + task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}