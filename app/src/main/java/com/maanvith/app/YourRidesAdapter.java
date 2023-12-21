package com.maanvith.app;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class YourRidesAdapter extends RecyclerView.Adapter<YourRidesAdapter.MyViewHolder> {
    Context context;
    ArrayList<ArrayList<String>> list;
    FirebaseAuth auth;
    String user;
    FirebaseDatabase database;
    DatabaseReference reference;
    Dialog dialog;
    ALoadingDialog aLoadingDialog;


    public YourRidesAdapter(Context context, ArrayList<ArrayList<String>> list, String user) {
        this.context = context;
        this.list = list;
        this.user = user;

        database = FirebaseDatabase.getInstance();
        reference =  database.getReference();
        auth = FirebaseAuth.getInstance();
    }


    @NonNull
    @Override
    public YourRidesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.resultscard,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull YourRidesAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {


        reference.child("Rides").child(list.get(position).get(1)).child(list.get(position).get(0)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String userName1 = snapshot.child("user").getValue().toString();
                String from1 =list.get(position).get(1);
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
                String uid1 = snapshot.child("uid").getValue().toString();

                Picasso.get().load(image).into(holder.resultsProfile);
                holder.username.setText(userName1);
                holder.from.setText(from1);
                holder.to.setText(to1);
                holder.date.setText(date1);
                holder.intermediate.setText(intermediate1);
                holder.time.setText(time1);
                holder.description.setText(description1);


                holder.cardview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog = new Dialog(context);
                        dialog.setContentView(R.layout.activity_your_rides_dialogue);
                        dialog.getWindow().setBackgroundDrawable(context.getDrawable(R.drawable.background_dialoge));
                        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        dialog.setCancelable(false);

                        TextView yourRidesUsername,yourRidesRoute,yourRidesDate,yourRidesTime,yourRidesCar,yourRidesMobile,yourRidesPerson,yourRidesPrice;

                        yourRidesDate = dialog.findViewById(R.id.yourRidesDate);
                        yourRidesRoute = dialog.findViewById(R.id.yourRidesRoute);
                        yourRidesUsername = dialog.findViewById(R.id.yourRidesUsername);
                        yourRidesTime = dialog.findViewById(R.id.yourRidesTime);
                        yourRidesCar = dialog.findViewById(R.id.yourRidesCar);
                        yourRidesMobile = dialog.findViewById(R.id.yourRidesMobile);
                        yourRidesPerson = dialog.findViewById(R.id.yourRidesPerson);
                        yourRidesPrice = dialog.findViewById(R.id.yourRidesPrice);

                        ImageView yourRidesProfile = dialog.findViewById(R.id.yourRidesProfile);
                        Picasso.get().load(image).into(yourRidesProfile);
                        yourRidesCar.setText(model+" - "+vehicle);
                        yourRidesDate.setText(date1);
                        int a = Integer.parseInt(price)*Integer.parseInt(noSeats);
                        yourRidesPrice.setText(""+a);
                        yourRidesUsername.setText(userName1);
                        yourRidesMobile.setText(mobile);
                        yourRidesPerson.setText(noSeats);
                        yourRidesTime.setText(time1);
                        yourRidesRoute.setText(from1+" -> "+to1);



                        Button bContact = dialog.findViewById(R.id.yourRidesContact);
                        Button bOk = dialog.findViewById(R.id.yourRidesCancel);

                        bContact.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                reference.child("UsersList").child(auth.getUid()).child(uid1).setValue("hi");
                                reference.child("UsersList").child(uid1).child(auth.getUid()).setValue("hi");
                                Toast.makeText(context, "Contacted", Toast.LENGTH_SHORT).show();
                                Toast.makeText(context, "Open Inbox to message", Toast.LENGTH_SHORT).show();
                            }
                        });
                        bOk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });


                        dialog.show();


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

//    private void switchToFragmentInbox(){
//
//        aLoadingDialog = new ALoadingDialog(this);
//
//        InboxFragment fragmentB = new InboxFragment();
//        FragmentTransaction transaction = fragmentB.getFragmentManager().beginTransaction();
//        transaction.replace(R.id.frame_layout, fragmentB);
//        transaction.addToBackStack(null);
//        transaction.commit();
//    }
}
