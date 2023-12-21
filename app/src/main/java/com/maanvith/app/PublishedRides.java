package com.maanvith.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class PublishedRides extends AppCompatActivity {
    Toolbar toolbar;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    ArrayList<String> list = new ArrayList<>();
    RecyclerView recyclerView;
    ALoadingDialog aLoadingDialog;

    PublishedRidesAdapter publishedRidesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_published_rides);

        toolbar = findViewById(R.id.toolbarPublishedRides);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back_arrow);
        aLoadingDialog = new ALoadingDialog(this);

        toolbar.setBackgroundColor(getResources().getColor(R.color.statusBar));
        getWindow().setStatusBarColor(getResources().getColor(R.color.statusBar));
        aLoadingDialog.show();
        Handler handler  = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                aLoadingDialog.cancel();
            }
        };
        handler.postDelayed(runnable,5000);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PublishedRides.this,HomeScreen.class );
                startActivity(i);
            }
        });

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference();
        String user = auth.getUid();
        recyclerView = findViewById(R.id.RecyclerViewPublishedRides);

        list = getMyUsers(auth.getUid());
//        Toast.makeText(getContext(), ""+list.size()+"i", Toast.LENGTH_SHORT).show();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        publishedRidesAdapter = new PublishedRidesAdapter(this,list,user);
        recyclerView.setAdapter(publishedRidesAdapter);
    }




    public ArrayList<String> getMyUsers(String user){
        ArrayList<String> list = new ArrayList<>();
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference();
//        Toast.makeText(getContext(), "users", Toast.LENGTH_SHORT).show();



        reference.child("PublishRides").child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
//                    Toast.makeText(getContext(), "true", Toast.LENGTH_SHORT).show();
                    for(DataSnapshot rideSnapshot: snapshot.getChildren()){
                        String userr = (String) rideSnapshot.getKey();
                        list.add(userr);
                        publishedRidesAdapter.notifyDataSetChanged();
                    }
                }else{
//                    Toast.makeText(getContext(), "false", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return list;
    }
}