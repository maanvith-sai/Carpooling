package com.maanvith.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class SearchResultsActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    int noRides;
    ArrayList<String> keys = new ArrayList<>();
    TextView searchResultsfrom;
    TextView searchResultsto;
    RecyclerView recyclerView;
    ALoadingDialog aLoadingDialog;

    DatabaseReference database1;
    MyAdapter myAdapter;
    ArrayList<String> list;
    ImageView resultsProfile;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        aLoadingDialog = new ALoadingDialog(this);
        aLoadingDialog.show();
        Handler handler  = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                aLoadingDialog.cancel();
            }
        };
        handler.postDelayed(runnable,3000);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.statusBar));
        Intent intent = getIntent();
        String from = intent.getStringExtra("from");
        String to = intent.getStringExtra("to");





        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference();
        searchResultsfrom = findViewById(R.id.searchResultsfrom);
        searchResultsto = findViewById(R.id.searchResultsto);
        resultsProfile = findViewById(R.id.resultProfile);
        TextView searchResultsfrom = findViewById(R.id.searchResultsfrom);
        TextView searchResultsto = findViewById(R.id.searchResultsto);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setBackgroundColor(getResources().getColor(R.color.statusBar));
        getWindow().setStatusBarColor(getResources().getColor(R.color.statusBar));

        searchResultsfrom.setText(from);
        searchResultsto.setText(to);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        list = new ArrayList<>();
        myAdapter = new MyAdapter(this,list,from);
        recyclerView.setAdapter(myAdapter);


        toolbar.setNavigationIcon(R.drawable.ic_back_arrow);
        setTitle("Rides");
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Rides");
        reference.child(from).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    getRides(reference,from,to);
                    myAdapter = new MyAdapter(SearchResultsActivity.this,list,from);
                    recyclerView.setAdapter(myAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("FirebaseError", "Error: " + databaseError.getMessage());
            }
        });

    }
    public void getRides(DatabaseReference reference, String from, String to){
        reference.child(from).addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot rideSnapshot : snapshot.getChildren()) {
                        String dropLoc = (String) rideSnapshot.child("dropLoc").getValue();

                        if (dropLoc != null && dropLoc.equals(to)) {
                            list.add(rideSnapshot.getKey()); // Add the ride's unique identifier (key) to the list
                            myAdapter.notifyDataSetChanged();
                        }
                    }

                }
            }

            @Override
            public void onCancelled( DatabaseError error) {

            }
        });
    }
}