package com.maanvith.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimePersonsActivity extends AppCompatActivity {
    ArrayList<String> keys = new ArrayList<>();
    int noRides =1;

    String dropLoc;
    String pickLoc;

    String loc1,loc2,loc3,loc4,loc5;
    String date, startTime, endTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_persons);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.statusBar));
        TextView timePersonDate = findViewById(R.id.timePersonDate);
        TextView timePersonTime1 = findViewById(R.id.timePersonTime);
        TextView timePersonTime2 = findViewById(R.id.timePersonTime2);
        Button timePersonsNext = findViewById(R.id.timePersonsNext);

        Intent intent = getIntent();
        String c = intent.getStringExtra("c");
        dropLoc=intent.getStringExtra("dropLoc");
        pickLoc = intent.getStringExtra("pickLoc");

        loc1 = intent.getStringExtra("loc1");
        loc2 = intent.getStringExtra("loc2");
        loc3 = intent.getStringExtra("loc3");
        loc4 = intent.getStringExtra("loc4");
        loc5 = intent.getStringExtra("loc5");



        timePersonTime1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar currentTime = Calendar.getInstance();
                int currentHour = currentTime.get(Calendar.HOUR_OF_DAY);
                int currentMinute = currentTime.get(Calendar.MINUTE);

                MaterialTimePicker timePicker = new MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_12H)
                        .setHour(currentHour) // Set initial hour to the current hour
                        .setMinute(currentMinute) // Set initial minute to the current minute
                        .setTitleText("Select Departure Time")
                        .build();

                timePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int hour = timePicker.getHour();
                        int minute = timePicker.getMinute();

                        String selectedTime = String.format("%02d:%02d %s", (hour % 12 == 0 ? 12 : hour % 12), minute, (hour < 12 ? "AM" : "PM"));
                        timePersonTime1.setText(selectedTime);
                    }
                });

                timePicker.show(getSupportFragmentManager(), "timePicker");
            }
        });

        timePersonTime2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar currentTime = Calendar.getInstance();
                int currentHour = currentTime.get(Calendar.HOUR_OF_DAY);
                int currentMinute = currentTime.get(Calendar.MINUTE);

                MaterialTimePicker timePicker = new MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_12H)
                        .setHour(currentHour) // Set initial hour to the current hour
                        .setMinute(currentMinute) // Set initial minute to the current minute
                        .setTitleText("Select Departure Time")
                        .build();

                timePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int hour = timePicker.getHour();
                        int minute = timePicker.getMinute();

                        String selectedTime = String.format("%02d:%02d %s", (hour % 12 == 0 ? 12 : hour % 12), minute, (hour < 12 ? "AM" : "PM"));
                        timePersonTime2.setText(selectedTime);
                    }
                });

                timePicker.show(getSupportFragmentManager(), "timePicker");
            }
        });


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Rides").child(pickLoc);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        String key = childSnapshot.getKey();
                        keys.add(key);
                    }
                    noRides = keys.size()+1;
                    Toast.makeText(TimePersonsActivity.this, ""+noRides, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TimePersonsActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors, if any
                Log.e("FirebaseError", "Error: " + databaseError.getMessage());
            }
        });



        timePersonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialDatePicker<Long> materialDatePicker = MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Select Travelling Date")
                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                        .build();
                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @Override
                    public void onPositiveButtonClick(Long selection) {
                        String tdate = new SimpleDateFormat("dd-MM-yyy", Locale.getDefault()).format(new Date(selection));
                        timePersonDate.setText(tdate);
                    }
                });
                materialDatePicker.show(TimePersonsActivity.this.getSupportFragmentManager(), "tag");
            }
        });

        timePersonsNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(timePersonDate.getText().toString()=="" || timePersonTime1.getText().toString()==""|| timePersonTime2.getText().toString()==""){
                    Toast.makeText(TimePersonsActivity.this, "Fill all the detils to proceed Further", Toast.LENGTH_SHORT).show();
                }
                else{
                    date = timePersonDate.getText().toString();
                    startTime = timePersonTime1.getText().toString();
                    endTime = timePersonTime2.getText().toString();
                    Intent i = new Intent(TimePersonsActivity.this, PriceModelActivity.class);
                    i.putExtra("dropLoc",dropLoc);
                    i.putExtra("pickLoc",pickLoc);
                    i.putExtra("loc1",loc1);
                    i.putExtra("loc2",loc2);
                    i.putExtra("loc3",loc3);
                    i.putExtra("loc4",loc4);
                    i.putExtra("loc5",loc5);
                    i.putExtra("startTime",startTime);
                    i.putExtra("endTime",endTime);
                    i.putExtra("date",date);
                    i.putExtra("c",c);
                    i.putExtra("noRides",""+noRides);
                    startActivity(i);
                    finish();
                }
            }
        });
    }
}