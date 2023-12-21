package com.maanvith.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


public class RidesFragment extends Fragment {
    Toolbar toolbar;

    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    ArrayList<ArrayList<String>> req;

    YourRidesAdapter yourRidesAdapter;

    RecyclerView recyclerView;

    public RidesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rides, container, false);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference();
        String user = auth.getUid();
        recyclerView = view.findViewById(R.id.RecyclerViewYourRides);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        toolbar = view.findViewById(R.id.toolbarYourRides);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back_arrow);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),HomeScreen.class );
                startActivity(i);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        yourRidesAdapter = new YourRidesAdapter(getActivity(),req,user);
        recyclerView.setAdapter(yourRidesAdapter);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Users").child(user).child("Rides").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                req = getMyRides(user);
                yourRidesAdapter = new YourRidesAdapter(getActivity(),req,user);
                recyclerView.setAdapter(yourRidesAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
    public ArrayList<ArrayList<String>> getMyRides (String user){
        ArrayList<ArrayList<String>> req = new ArrayList<ArrayList<String>>();

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference();

        reference.child("Users").child(user).child("Rides").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot rideSnapshot: snapshot.getChildren()){
                        ArrayList<String> arr = new ArrayList<>();
                        String ride = (String) rideSnapshot.child("ride").getValue();
                        String start = (String) rideSnapshot.child("start").getValue();
                        arr.add(ride);
                        arr.add(start);
                        req.add(arr);
                        yourRidesAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return req;
    }
}