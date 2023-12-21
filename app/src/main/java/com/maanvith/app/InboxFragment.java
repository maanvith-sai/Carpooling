package com.maanvith.app;

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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class InboxFragment extends Fragment {
    Toolbar toolbar;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    ArrayList<String> list = new ArrayList<>();
    RecyclerView recyclerView;

    yourUsersAdapter yourUsersAdapter;

    public InboxFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_inbox, container, false);

        toolbar = view.findViewById(R.id.toolbarInbox);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back_arrow);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),HomeScreen.class );
                startActivity(i);
            }
        });

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference();
        String user = auth.getUid();
        recyclerView = view.findViewById(R.id.InboxRecyclerview);


        list = getMyUsers(auth.getUid());
//        Toast.makeText(getContext(), ""+list.size()+"i", Toast.LENGTH_SHORT).show();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        yourUsersAdapter = new yourUsersAdapter(getContext(),list,user);
        recyclerView.setAdapter(yourUsersAdapter);

//        Toast.makeText(getContext(), ""+list.size(), Toast.LENGTH_SHORT).show();
        return view;

    }


    public ArrayList<String> getMyUsers(String user){
        ArrayList<String> list = new ArrayList<>();
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference();
//        Toast.makeText(getContext(), "users", Toast.LENGTH_SHORT).show();



        reference.child("UsersList").child(user).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
//                    Toast.makeText(getContext(), "true", Toast.LENGTH_SHORT).show();
                    for(DataSnapshot rideSnapshot: snapshot.getChildren()){
                        String userr = (String) rideSnapshot.getKey();
                        list.add(userr);
                        yourUsersAdapter.notifyDataSetChanged();
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