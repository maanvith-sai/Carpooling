package com.maanvith.app;

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

public class PublishedRidesAdapter extends RecyclerView.Adapter<PublishedRidesAdapter.MyViewHolder> {


    Context context;
    ArrayList<String> list;
    FirebaseAuth auth;
    String user;
    FirebaseDatabase database;
    DatabaseReference reference;

    public PublishedRidesAdapter(Context context, ArrayList<String> list,String user) {
        this.context = context;
        this.list = list;
        this.user = user;

        database = FirebaseDatabase.getInstance();
        reference =  database.getReference();
        auth = FirebaseAuth.getInstance();

    }

    @NonNull
    @Override
    public PublishedRidesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.resultscard,parent,false);
        return new PublishedRidesAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PublishedRidesAdapter.MyViewHolder holder, int position) {


        reference.child("PublishRides").child(auth.getUid()).child(list.get(position)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String userName1 = snapshot.child("user").getValue().toString();
                String from1 =snapshot.child("startLoc").getValue().toString();
                String to1 = snapshot.child("dropLoc").getValue().toString();
                String date1 = snapshot.child("date").getValue().toString();
                String intermediate1 = snapshot.child("loc1").getValue().toString();
                String time1 = snapshot.child("startTime").getValue().toString();
                String description1 = snapshot.child("message").getValue().toString();
                String image = snapshot.child("image").getValue().toString();
                String noSeats = snapshot.child("persons").getValue(String.class);
                String gender = snapshot.child("gender").getValue(String.class);
                String age = snapshot.child("age").getValue(String.class);
                String noLoc = snapshot.child("noLoc").getValue(String.class);
                String price = snapshot.child("price").getValue(String.class);
                String model = snapshot.child("model").getValue(String.class);
                String mobile = snapshot.child("mobile").getValue(String.class);
                String vehicle = snapshot.child("vehicle").getValue(String.class);

                Picasso.get().load(image).into(holder.resultsProfile);
                holder.username.setText(userName1);
                holder.from.setText(from1);
                holder.to.setText(to1);
                holder.date.setText(date1);
                holder.intermediate.setText(intermediate1);
                holder.time.setText(time1);
                holder.description.setText(description1);
//                holder.kk.setText("-->");

                holder.cardview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(context, userName1+"", Toast.LENGTH_SHORT).show();
//                        Intent i = new Intent(context,ExpandRidesActivity.class);
////                        i.putExtra("Ride",list.get(position));
//                        i.putExtra("from",from1);
//                        i.putExtra("image", image);
//                        i.putExtra("seats",noSeats);
//                        i.putExtra("username",userName1);
//                        i.putExtra("to",to1);
//                        i.putExtra("description", description1);
//                        i.putExtra("date",date1);
//                        i.putExtra("time",time1);
//                        i.putExtra("gender",gender);
//                        i.putExtra("age",age);
//                        i.putExtra("noLoc",noLoc);
//                        i.putExtra("price",price);
//                        i.putExtra("model",model);
//                        i.putExtra("vehicle",vehicle);
//                        i.putExtra("mobile",mobile);
//                        context.startActivity(i);
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
            return 0; // or some other appropriate value
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView username, from, to, date, intermediate, time, description;
        CardView cardview;
        ImageView resultsProfile;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.resultUsername);
            from = itemView.findViewById(R.id.resultFrom);
            to = itemView.findViewById(R.id.resultTo);
            date = itemView.findViewById(R.id.resultsDate);
            time = itemView.findViewById(R.id.resultStartTime);
            intermediate = itemView.findViewById(R.id.resultIntermediateLoc);
            description = itemView.findViewById(R.id.resultDescription);
            resultsProfile = itemView.findViewById(R.id.resultProfile);
            cardview = itemView.findViewById(R.id.cardView);

        }
    }
}
