package com.maanvith.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DetailsActivity extends AppCompatActivity {


    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
         AutoCompleteTextView detailgender = findViewById(R.id.detailpersons);
         AutoCompleteTextView detailage = findViewById(R.id.detailage);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.statusBar));
        }

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

         EditText detailfirstName = findViewById(R.id.searchFrom);
         EditText detaillastName = findViewById(R.id.detaillastname);
         EditText detailusername = findViewById(R.id.detailusername);
         EditText detailemail = findViewById(R.id.detailemail);
         EditText detailmobilenumber = findViewById(R.id.detailmobilenumber);
         Button save = findViewById(R.id.buttonPriceModelNext);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference();

        String[] genderOptions = {"Select Gender", "Male", "Female", "Prefer Not to Say"};
        String[] ageOptions = {"Select Age","<20", "20-30", "30-40", "40-50","50-60","60-70",">70"};

        ArrayAdapter<String> adaptergender = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, genderOptions);
        ArrayAdapter<String> adapterage = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ageOptions);

        adaptergender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        detailgender.setAdapter(adaptergender);
        detailage.setAdapter(adapterage);

        String xemail = getIntent().getStringExtra("email");
        String userName = getIntent().getStringExtra("userName");
        String user = getIntent().getStringExtra("user");
        String mobilen = getIntent().getStringExtra("mobilenumber");


        final String[] gender = new String[1];
        final String[] age = new String[1];


        detailgender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i>0){
                    gender[0] = adapterView.getItemAtPosition(i).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        detailage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i>0){
                    age[0] = adapterView.getItemAtPosition(i).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        detailemail.setText(xemail);
        detailusername.setText(userName);
        detailmobilenumber.setText(mobilen);



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstnames = detailfirstName.getText().toString();
                String lastnames = detaillastName.getText().toString();
                String mobileNumbers = detailmobilenumber.getText().toString();
                String emails = detailemail.getText().toString();
                String gender1 = detailgender.getText().toString();
                String age1 = detailage.getText().toString();
                if(firstnames!=""&&lastnames!=""&&mobileNumbers!=""&&emails!=""&&gender[0]!=""&&age[0]!=""){
                    reference.child("Users").child(auth.getUid()).child("First Name").setValue(firstnames);
                    reference.child("Users").child(auth.getUid()).child("Last Name").setValue(lastnames);
                    reference.child("Users").child(auth.getUid()).child("Email").setValue(emails);
                    reference.child("Users").child(auth.getUid()).child("Mobile Number").setValue(mobileNumbers);
                    reference.child("Users").child(auth.getUid()).child("Gender").setValue(gender1);
                    reference.child("Users").child(auth.getUid()).child("Age").setValue(age1).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(DetailsActivity.this, "Details Uploaded", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(DetailsActivity.this, WelcomePage.class);
                            startActivity(i);

                        }
                    });

                }else{
                    Toast.makeText(DetailsActivity.this, "Fill all the details", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}