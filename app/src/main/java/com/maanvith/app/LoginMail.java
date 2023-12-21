package com.maanvith.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginMail extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser firebaseUser;


//    protected void onStart() {
//        super.onStart();
//        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
//        if(firebaseUser!= null){
//            Intent intent = new Intent(LoginMail.this, MainActivity.class);
//            startActivity(intent);
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_mail);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.statusBar));
        }
        auth = FirebaseAuth.getInstance();
        EditText loginMail = findViewById(R.id.editTextLoginMail);
        EditText loginPassword = findViewById(R.id.editTextLoginPassword);
        Button login = findViewById(R.id.loginButton);
        TextView forgotPassword = findViewById(R.id.loginForgotPassword);
        ImageView back =findViewById(R.id.backLoginMailButton);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginMail.this, ForgotActivity.class);
                startActivity(i);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = loginMail.getText().toString();
                String password = loginPassword.getText().toString();
                if(!mail.equals("") && !password.equals("")){
                    signin(mail,password);
                }
                else{
                    Toast.makeText(LoginMail.this, "Please enter login details", Toast.LENGTH_SHORT).show();
                }
            }
        });






    }
    public void signin(String email, String password){
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent = new Intent(LoginMail.this, HomeScreen.class);
                    Toast.makeText(LoginMail.this, "Welcome back!", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(LoginMail.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}