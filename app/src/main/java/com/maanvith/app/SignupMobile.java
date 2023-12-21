package com.maanvith.app;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class SignupMobile extends AppCompatActivity {

    private EditText editTextMobile;
    private Button buttonVerify;
    private FirebaseAuth firebaseAuth;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_mobile);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.statusBar));
        }

        back = findViewById(R.id.buttonBackSignupMobile);

        editTextMobile = findViewById(R.id.editTextMobileNumber);
        buttonVerify = findViewById(R.id.buttonSignupMobileSendOTP);

        firebaseAuth = FirebaseAuth.getInstance();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        buttonVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!verify(editTextMobile.getText().toString())) {
                    Toast.makeText(SignupMobile.this, "Invalid Mobile Number", Toast.LENGTH_SHORT).show();
                } else {
                    String phoneNumber = "+91" + editTextMobile.getText().toString().trim();
                    if (!phoneNumber.isEmpty()) {
                        // Start the phone number verification process
                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                phoneNumber,
                                60, // Timeout duration
                                TimeUnit.SECONDS,
                                SignupMobile.this,
                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                        // This callback will be invoked in case of instant verification.
                                        // You can directly sign in the user with the provided credential.
                                        // No need to call signInWithPhoneAuthCredential here.
                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {
                                        // Verification process failed.
                                        // Handle the error as per your requirement.
                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String verificationId,
                                                           @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        // The verification code is successfully sent to the user's mobile number.
                                        // Save the verificationId and navigate to the EnterOtpActivity.
                                        Intent intent = new Intent(SignupMobile.this, SignupMobileOTP.class);
                                        intent.putExtra("mobilenumber",editTextMobile.getText().toString());
                                        intent.putExtra("verificationId", verificationId);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                        );
                    }
                }
            }
        });
    }
    public boolean verify(String s){
        if(s.length()==10){
            return true;
        }
        return false;
    }
}

