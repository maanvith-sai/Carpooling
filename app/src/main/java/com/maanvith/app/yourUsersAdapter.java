package com.maanvith.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class yourUsersAdapter extends RecyclerView.Adapter<yourUsersAdapter.MyViewHolder>{

    Context context;
    ArrayList<String> list;
    FirebaseAuth auth;
    String user;
    FirebaseDatabase database;
    DatabaseReference reference;

    public yourUsersAdapter(Context context, ArrayList<String> list, String user) {
        this.context = context;
        this.list = list;
        this.user = user;

        database = FirebaseDatabase.getInstance();
        reference =  database.getReference();
        auth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public yourUsersAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.inboxcard,parent,false);
        return new yourUsersAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull yourUsersAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        reference.child("Users").child(list.get(position)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String image = snapshot.child("image").getValue().toString();
                String username = snapshot.child("userName").getValue().toString();

                Picasso.get().load(image).into(holder.inboxProfile);
                holder.username.setText(username);

                holder.cardview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        Toast.makeText(context, ""+username, Toast.LENGTH_SHORT).show();
                        Bundle bundle = new Bundle();
                        bundle.putString("anotherUserId", list.get(position));
                        bundle.putString("anotherUsername",username);
                        bundle.putString("myUserId", auth.getUid());
                        Intent i = new Intent(context, InboxChatActivity.class);
                        i.putExtras(bundle);
                        ((Activity) context).startActivity(i);

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public int getItemCount() {
        if (list != null) {
//            Toast.makeText(context, ""+list.size(), Toast.LENGTH_SHORT).show();
            return list.size();
        } else {
//            Toast.makeText(context, ""+0, Toast.LENGTH_SHORT).show();
            return 0;
        }
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView username;
        CardView cardview;
        ImageView inboxProfile;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.inboxUsername);

            inboxProfile = itemView.findViewById(R.id.inboxProfile);
            cardview = itemView.findViewById(R.id.InboxcardView);

        }
    }
}
