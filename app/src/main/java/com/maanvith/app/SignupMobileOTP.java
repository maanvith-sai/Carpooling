package com.maanvith.app;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class SignupMobileOTP extends AppCompatActivity {

    private String verificationId;
    private FirebaseAuth firebaseAuth;
    Button buttonVerifyOtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_mobile_otp);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.statusBar));
        }





        EditText box1,box2,box3,box4,box5,box6;
        ImageView buttonBackSignupMobileOTP = findViewById(R.id.buttonBackSignupMobileOTP);

        box1= findViewById(R.id.box1);
        box2= findViewById(R.id.box2);
        box3= findViewById(R.id.box3);
        box4= findViewById(R.id.box4);
        box5= findViewById(R.id.box5);
        box6= findViewById(R.id.box6);
        buttonVerifyOtp= findViewById(R.id.buttonSignupMobileVerifyOTP);
        buttonVerifyOtp.setEnabled(false);

        firebaseAuth = FirebaseAuth.getInstance();

        // Retrieve the verificationId from the previous activity
        verificationId = getIntent().getStringExtra("verificationId");

        box1.addTextChangedListener(new OtpTextWatcher(box1, box2));
        box2.addTextChangedListener(new OtpTextWatcher(box2, box3));
        box3.addTextChangedListener(new OtpTextWatcher(box3, box4));
        box4.addTextChangedListener(new OtpTextWatcher(box4, box5));
        box5.addTextChangedListener(new OtpTextWatcher(box5, box6));
        box6.addTextChangedListener(new OtpTextWatcher(box6, null));

        buttonBackSignupMobileOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        buttonVerifyOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otp = box1.getText().toString()+box2.getText().toString()+box3.getText().toString()+box4.getText().toString()+box5.getText().toString()+box6.getText().toString();
                if (!otp.isEmpty()) {
                    // Create the PhoneAuthCredential using verificationId and the entered OTP
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, otp);
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    String mobilen;
                    mobilen= getIntent().getStringExtra("mobilenumber");
                    FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
                    String user = user1.getUid();
                    if (task.isSuccessful()) {
                        Toast.makeText(SignupMobileOTP.this, "Verified", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(SignupMobileOTP.this, DetailsActivity.class);
                        i.putExtra("mobilenumber",mobilen);
                        i.putExtra("user",user);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(SignupMobileOTP.this, "Failed", Toast.LENGTH_SHORT).show();
                        // Handle the error as per your requirement.
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            // Invalid verification code.
                        }
                    }
                });
    }
    private class OtpTextWatcher implements TextWatcher {

        private EditText currentEditText;
        private EditText nextEditText;

        public OtpTextWatcher(EditText currentEditText, EditText nextEditText) {
            this.currentEditText = currentEditText;
            this.nextEditText = nextEditText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() == 1) {
                if (nextEditText != null) {
                    currentEditText.clearFocus();
                    nextEditText.requestFocus();
                } else {
                    // Last box reached, enable the Verify button
                    buttonVerifyOtp.setEnabled(true);
                }
            } else {
                // Disable the Verify button if any of the boxes are empty
                buttonVerifyOtp.setEnabled(false);
            }
        }
    }
}

