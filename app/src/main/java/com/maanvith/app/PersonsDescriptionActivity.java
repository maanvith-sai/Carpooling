package com.maanvith.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PersonsDescriptionActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;



    String dropLoc;
    String pickLoc;
    String username;

    String loc1,loc2,loc3,loc4,loc5;
    String date, startTime, endTime;

    String[] persons = {"1 Seat","2 Seats","3 Seats","4 Seats","5 Seats","6 Seats"};
    String noPerson;
    String image;
    String gender;
    String age;
    String noRides;
    String price,model,vehicle,mobile;
    Long noofRides;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persons_description);
        AutoCompleteTextView personss = findViewById(R.id.seatsAvailable);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.statusBar));

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference();

        EditText editTextMessage = findViewById(R.id.editTextMessage);


        Button buttonPersonsDescriptionNext = findViewById(R.id.buttonPersonsDescriptionNext);
        Intent intent = getIntent();
        dropLoc=intent.getStringExtra("dropLoc");
        pickLoc = intent.getStringExtra("pickLoc");

        String c = intent.getStringExtra("c");
        //Toast.makeText(this, "c"+c, Toast.LENGTH_SHORT).show();
        loc1 = intent.getStringExtra("loc1");
        loc2 = intent.getStringExtra("loc2");
        loc3 = intent.getStringExtra("loc3");
        loc4 = intent.getStringExtra("loc4");
        loc5 = intent.getStringExtra("loc5");
        noRides = intent.getStringExtra("noRides");
        price = intent.getStringExtra("price");
        model = intent.getStringExtra("model");
        vehicle = intent.getStringExtra("vehicle");


        date = intent.getStringExtra("date");
        startTime = intent.getStringExtra("startTime");
        endTime = intent.getStringExtra("endTime");
        String user = auth.getUid();
        personss.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(i==0){
                    noPerson = "1";
                }
                else if(i==1){
                    noPerson = "2";
                }
                else if(i==2){
                    noPerson = "3";
                }
                else if(i==3){
                    noPerson = "4";
                }
                else if(i==4){
                    noPerson = "5";
                }
                else if(i==5){
                    noPerson = "6";
                }

            }
        });

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(user);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                username = snapshot.child("userName").getValue(String.class);
                image = snapshot.child("image").getValue(String.class);
                gender = snapshot.child("Gender").getValue(String.class);
                age = snapshot.child("Age").getValue(String.class);
                mobile = snapshot.child("Mobile Number").getValue(String.class);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });

        try{

            reference.child("Published Rides").child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    noofRides = snapshot.getChildrenCount();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }catch (Exception e){
            noofRides = 0L;
            Toast.makeText(this, "No Previous Rides Published", Toast.LENGTH_SHORT).show();
        }




        buttonPersonsDescriptionNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String n = "RIDE"+noRides;
                String k = "RIDE"+noofRides;

                //Toast.makeText(PersonsDescriptionActivity.this, n, Toast.LENGTH_SHORT).show();


                reference.child("Rides").child(pickLoc).child(n).child("dropLoc").setValue(dropLoc);

                if(c.equals("0")){

                } else if (c.equals("1")) {
                    reference.child("Rides").child(pickLoc).child(n).child("loc1").setValue(loc1);
                    reference.child("Rides").child(pickLoc).child(n).child("noLoc").setValue("1");
                    reference.child("PublishRides").child(auth.getUid()).child(k).child("loc1").setValue(loc1);
                    reference.child("PublishRides").child(auth.getUid()).child(k).child("noLoc").setValue("1");
                } else if (c.equals("2")) {
                    reference.child("Rides").child(pickLoc).child(n).child("loc1").setValue(loc1);
                    reference.child("Rides").child(pickLoc).child(n).child("loc2").setValue(loc2);
                    reference.child("Rides").child(pickLoc).child(n).child("noLoc").setValue("2");
                    reference.child("PublishRides").child(auth.getUid()).child(k).child("loc1").setValue(loc1);
                    reference.child("PublishRides").child(auth.getUid()).child(k).child("loc2").setValue(loc2);
                    reference.child("PublishRides").child(auth.getUid()).child(k).child("noLoc").setValue("2");
                } else if (c.equals("3")) {
                    reference.child("Rides").child(pickLoc).child(n).child("loc1").setValue(loc1);
                    reference.child("Rides").child(pickLoc).child(n).child("loc2").setValue(loc2);
                    reference.child("Rides").child(pickLoc).child(n).child("loc3").setValue(loc3);
                    reference.child("Rides").child(pickLoc).child(n).child("noLoc").setValue("3");
                    reference.child("PublishRides").child(auth.getUid()).child(k).child("loc1").setValue(loc1);
                    reference.child("PublishRides").child(auth.getUid()).child(k).child("loc2").setValue(loc2);
                    reference.child("PublishRides").child(auth.getUid()).child(k).child("loc3").setValue(loc3);
                    reference.child("PublishRides").child(auth.getUid()).child(k).child("noLoc").setValue("3");

                } else if (c.equals("4")) {
                    reference.child("Rides").child(pickLoc).child(n).child("loc1").setValue(loc1);
                    reference.child("Rides").child(pickLoc).child(n).child("loc2").setValue(loc2);
                    reference.child("Rides").child(pickLoc).child(n).child("loc3").setValue(loc3);
                    reference.child("Rides").child(pickLoc).child(n).child("loc4").setValue(loc4);
                    reference.child("Rides").child(pickLoc).child(n).child("noLoc").setValue("4");
                    reference.child("PublishRides").child(auth.getUid()).child(k).child("loc1").setValue(loc1);
                    reference.child("PublishRides").child(auth.getUid()).child(k).child("loc2").setValue(loc2);
                    reference.child("PublishRides").child(auth.getUid()).child(k).child("loc3").setValue(loc3);
                    reference.child("PublishRides").child(auth.getUid()).child(k).child("loc4").setValue(loc4);
                    reference.child("PublishRides").child(auth.getUid()).child(k).child("noLoc").setValue("4");
                } else if (c.equals("5")) {
                    reference.child("Rides").child(pickLoc).child(n).child("loc1").setValue(loc1);
                    reference.child("Rides").child(pickLoc).child(n).child("loc2").setValue(loc2);
                    reference.child("Rides").child(pickLoc).child(n).child("loc3").setValue(loc3);
                    reference.child("Rides").child(pickLoc).child(n).child("loc4").setValue(loc4);
                    reference.child("Rides").child(pickLoc).child(n).child("loc5").setValue(loc5);
                    reference.child("Rides").child(pickLoc).child(n).child("noLoc").setValue("5");
                    reference.child("PublishRides").child(auth.getUid()).child(k).child("loc1").setValue(loc1);
                    reference.child("PublishRides").child(auth.getUid()).child(k).child("loc2").setValue(loc2);
                    reference.child("PublishRides").child(auth.getUid()).child(k).child("loc3").setValue(loc3);
                    reference.child("PublishRides").child(auth.getUid()).child(k).child("loc4").setValue(loc4);
                    reference.child("PublishRides").child(auth.getUid()).child(k).child("loc5").setValue(loc5);
                    reference.child("PublishRides").child(auth.getUid()).child(k).child("noLoc").setValue("5");
                }
                if(noPerson!=null && !editTextMessage.getText().toString().equals("")){

                    reference.child("Rides").child(pickLoc).child(n).child("persons").setValue(noPerson);
                    reference.child("Rides").child(pickLoc).child(n).child("message").setValue(editTextMessage.getText().toString());
                    reference.child("Rides").child(pickLoc).child(n).child("user").setValue(username);
                    reference.child("Rides").child(pickLoc).child(n).child("image").setValue(image);
                    reference.child("Rides").child(pickLoc).child(n).child("date").setValue(date);
                    reference.child("Rides").child(pickLoc).child(n).child("age").setValue(age);
                    reference.child("Rides").child(pickLoc).child(n).child("gender").setValue(gender);
                    reference.child("Rides").child(pickLoc).child(n).child("uid").setValue(auth.getUid());
                    reference.child("Rides").child(pickLoc).child(n).child("startTime").setValue(startTime);
                    reference.child("Rides").child(pickLoc).child(n).child("price").setValue(price);
                    reference.child("Rides").child(pickLoc).child(n).child("mobile").setValue(mobile);
                    reference.child("Rides").child(pickLoc).child(n).child("model").setValue(model);
                    reference.child("Rides").child(pickLoc).child(n).child("vehicle").setValue(vehicle);


                    reference.child("PublishRides").child(auth.getUid()).child(k).child("startLoc").setValue(pickLoc);
                    reference.child("PublishRides").child(auth.getUid()).child(k).child("persons").setValue(noPerson);
                    reference.child("PublishRides").child(auth.getUid()).child(k).child("message").setValue(editTextMessage.getText().toString());
                    reference.child("PublishRides").child(auth.getUid()).child(k).child("user").setValue(username);
                    reference.child("PublishRides").child(auth.getUid()).child(k).child("image").setValue(image);
                    reference.child("PublishRides").child(auth.getUid()).child(k).child("date").setValue(date);
                    reference.child("PublishRides").child(auth.getUid()).child(k).child("age").setValue(age);
                    reference.child("PublishRides").child(auth.getUid()).child(k).child("gender").setValue(gender);
                    reference.child("PublishRides").child(auth.getUid()).child(k).child("uid").setValue(auth.getUid());
                    reference.child("PublishRides").child(auth.getUid()).child(k).child("startTime").setValue(startTime);
                    reference.child("PublishRides").child(auth.getUid()).child(k).child("price").setValue(price);
                    reference.child("PublishRides").child(auth.getUid()).child(k).child("mobile").setValue(mobile);
                    reference.child("PublishRides").child(auth.getUid()).child(k).child("model").setValue(model);
                    reference.child("PublishRides").child(auth.getUid()).child(k).child("vehicle").setValue(vehicle);
                    reference.child("PublishRides").child(auth.getUid()).child(k).child("endTime").setValue(endTime);
                    reference.child("PublishRides").child(auth.getUid()).child(k).child("dropLoc").setValue(dropLoc);



                    reference.child("Rides").child(pickLoc).child(n).child("endTime").setValue(endTime).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(PersonsDescriptionActivity.this, "Ride Published Successfully", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(PersonsDescriptionActivity.this, RidePublished.class);
                            startActivity(i);
                            finish();
                        }
                    });

                }
                else{
                    Toast.makeText(PersonsDescriptionActivity.this, "FIll details", Toast.LENGTH_SHORT).show();
                }

            }
        });



        ArrayAdapter<String> adapterperson = new ArrayAdapter<>(
                this, android.R.layout.simple_dropdown_item_1line, persons
        );

        personss.setAdapter(adapterperson);



    }
}