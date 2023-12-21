package com.maanvith.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ExpandRidesActivity extends AppCompatActivity {

    TextView expandRideFrom;
    FirebaseAuth auth;
    TextView expandRideTo;
    CircleImageView expandRideProfile;
    TextView expandRideUsername;
    TextView expandRidesGender;
    TextView expandRidesSeats;
    TextView expandRidesModel;
    TextView expandRidesDate;
    TextView expandRidesTime;
    TextView expandRidesLocations;
    TextView expandRidesPrice;
    TextView expandRidesMessage;
    TextView expandRidesAge;

    FirebaseDatabase database;
    DatabaseReference reference;
    String uid;
    String gender;
    String mobile;

    Button buttonContact, butoonBook;
    String location1,location2,location3,location4,location5, Loc, noLoc;
    int i=0;

    private static final int CALL_PERMISSION_REQUEST_CODE = 123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_rides);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        auth = FirebaseAuth.getInstance();
        String user = auth.getUid();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_back_arrow);
        setTitle("Rides");
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        toolbar.setBackgroundColor(getResources().getColor(R.color.statusBar));
        getWindow().setStatusBarColor(getResources().getColor(R.color.statusBar));

        Intent intent = getIntent();
        String ride =  intent.getStringExtra("Ride");
        String from = intent.getStringExtra("from");
        String image = intent.getStringExtra("image");
        String seats = intent.getStringExtra("seats");
        String username = intent.getStringExtra("username");
        String to = intent.getStringExtra("to");
        String description = intent.getStringExtra("description");
        String date = intent.getStringExtra("date");
        String time = intent.getStringExtra("time");
        mobile = intent.getStringExtra("mobile");
        gender = intent.getStringExtra("gender");
        String age = intent.getStringExtra("age");
        noLoc = intent.getStringExtra("noLoc");
        String price = intent.getStringExtra("price");
        String model = intent.getStringExtra("model");
        String vehicle = intent.getStringExtra("vehicle");
        String modelVehicle = vehicle+" - "+model;

        expandRideFrom = findViewById(R.id.expandRideFrom);
        expandRideTo = findViewById(R.id.expandRideTo);
        expandRideProfile= findViewById(R.id.resultProfile);
        expandRideUsername = findViewById(R.id.expandRideUsername);
        expandRidesGender= findViewById(R.id.expandRidesGender);
        expandRidesSeats = findViewById(R.id.expandRidesSeats);
        expandRidesModel= findViewById(R.id.expandRidesModel);
        expandRidesDate= findViewById(R.id.expandRidesDate);
        expandRidesTime= findViewById(R.id.expandRidesTime);
        expandRidesLocations = findViewById(R.id.expandRidesLocations);
        expandRidesPrice = findViewById(R.id.expandRidesPrice);
        expandRidesMessage = findViewById(R.id.expandRidesMessage);
        expandRidesAge = findViewById(R.id.expandRidesAge);
        butoonBook = findViewById(R.id.butoonBook);
        buttonContact = findViewById(R.id.buttonContact);

        expandRideFrom.setText(from);
        expandRideTo.setText(to);
        Picasso.get().load(image).into(expandRideProfile);
        expandRideUsername.setText(username);
        expandRidesTime.setText(time);
        expandRidesDate.setText(date);
        expandRidesMessage.setText(description);
        expandRidesSeats.setText(seats);
        expandRidesGender.setText(gender);
        expandRidesAge.setText(age);
        expandRidesPrice.setText(price);
        expandRidesModel.setText(modelVehicle);


        database = FirebaseDatabase.getInstance();
        reference =  database.getReference();


        reference.child("Rides").child(from).child(ride).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(noLoc.equals("1")){
                    location1 = (String) snapshot.child("loc1").getValue();
                    Loc = location1;
                } else if (noLoc.equals("2")) {
                    location1 = (String) snapshot.child("loc1").getValue();
                    location2 = (String) snapshot.child("loc2").getValue();
                    Loc = location1+" | "+location2;
                } else if (noLoc.equals("3")) {
                    location1 = (String) snapshot.child("loc1").getValue();
                    location2 = (String) snapshot.child("loc2").getValue();
                    location3 = (String) snapshot.child("loc3").getValue();
                    Loc = location1+" | "+location2+" | "+location3;
                } else if (noLoc.equals("4")) {
                    location1 = (String) snapshot.child("loc1").getValue();
                    location2 = (String) snapshot.child("loc2").getValue();
                    location3 = (String) snapshot.child("loc3").getValue();
                    location4 = (String) snapshot.child("loc4").getValue();
                    Loc = location1+" | "+location2+" | "+location3+" | "+location4;
                }else if(noLoc.equals("5")){
                    location1 = (String) snapshot.child("loc1").getValue();
                    location2 = (String) snapshot.child("loc2").getValue();
                    location3 = (String) snapshot.child("loc3").getValue();
                    location4 = (String) snapshot.child("loc4").getValue();
                    location5 = (String) snapshot.child("loc5").getValue();
                    Loc = location1+" | "+location2+" | "+location3+" | "+location4+" | "+location5;
                }
                expandRidesLocations.setText(Loc);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        butoonBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                reference.child("Rides").child(from).child(ride).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String rem = (String) snapshot.child("persons").getValue();
                        int remm = Integer.parseInt(rem);
                        if(remm<=0){
                            Toast.makeText(ExpandRidesActivity.this, "SEATS FILLED", Toast.LENGTH_SHORT).show();

                        }
                        else{
                            remm = remm-1;
                            i=i+1;
                            reference.child("Users").child(user).child("Rides").child("ride"+i).child("start").setValue(from);
                            reference.child("Users").child(user).child("Rides").child("ride"+i).child("ride").setValue(ride);
                            reference.child("Rides").child(from).child(ride).child("persons").setValue(""+remm).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(ExpandRidesActivity.this, "Booked Successfully", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        buttonContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(ExpandRidesActivity.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // Request the permission to make a call if it is not granted.
                    ActivityCompat.requestPermissions(ExpandRidesActivity.this, new String[]{Manifest.permission.CALL_PHONE}, CALL_PERMISSION_REQUEST_CODE);
                } else {
                    // Permission is already granted, so make the call.
                    makePhoneCall();
                }
            }
        });
    }
    private void makePhoneCall() {
        String phoneNumber = mobile; // Replace with the phone number you want to call.
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phoneNumber));

        try {
            startActivity(callIntent);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CALL_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                makePhoneCall();
            } else {
                Toast.makeText(this, "Allow the permission to contact publisher", Toast.LENGTH_SHORT).show();
            }
        }
    }
}