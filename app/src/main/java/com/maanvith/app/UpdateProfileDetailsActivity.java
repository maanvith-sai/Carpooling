package com.maanvith.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class UpdateProfileDetailsActivity extends AppCompatActivity {

    EditText firstName1, lastName1,userName1,mobileNumber1,email1;
    AutoCompleteTextView gender1, age1;
    Button updateProfile;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    String firstName,lastName,userName,mobileNumber,email;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile_details);

        firstName1 = findViewById(R.id.updatefirstName);
        lastName1 = findViewById(R.id.updatelastName);
        userName1 = findViewById(R.id.updateusername);
        mobileNumber1 = findViewById(R.id.updatemobilenumber);
        email1 = findViewById(R.id.updateemail);


        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference();

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.statusBar));

        updateProfile = findViewById(R.id.buttonUpdateProfiledetails);

        reference.child("Users").child(auth.getUid().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                 firstName = snapshot.child("First Name").getValue().toString();
                 lastName = snapshot.child("Last Name").getValue().toString();
                 userName = snapshot.child("userName").getValue().toString();
                 mobileNumber = snapshot.child("Mobile Number").getValue().toString();
                 email = snapshot.child("Email").getValue().toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        firstName1.setText(firstName);
        lastName1.setText(lastName);
        userName1.setText(userName);
        mobileNumber1.setText(mobileNumber);
        email1.setText(email);

        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfileDetails();
            }
        });

        fetchUserData();

    }

    private void updateProfileDetails() {

        reference.child("Users").child(auth.getUid()).child("First Name").setValue(firstName1.getText().toString());
        reference.child("Users").child(auth.getUid()).child("Last Name").setValue(lastName1.getText().toString());
        reference.child("Users").child(auth.getUid()).child("userName").setValue(userName1.getText().toString());
        reference.child("Users").child(auth.getUid()).child("Mobile Number").setValue(mobileNumber1.getText().toString());
        reference.child("Users").child(auth.getUid()).child("Email").setValue(email1.getText().toString())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(UpdateProfileDetailsActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void fetchUserData() {
        reference.child("Users").child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                firstName = snapshot.child("First Name").getValue(String.class);
                lastName = snapshot.child("Last Name").getValue(String.class);
                userName = snapshot.child("userName").getValue(String.class);
                mobileNumber = snapshot.child("Mobile Number").getValue(String.class);
                email = snapshot.child("Email").getValue(String.class);

                firstName1.setText(firstName);
                lastName1.setText(lastName);
                userName1.setText(userName);
                mobileNumber1.setText(mobileNumber);
                email1.setText(email);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}