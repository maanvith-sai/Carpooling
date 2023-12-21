package com.maanvith.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

public class PriceModelActivity extends AppCompatActivity {

    EditText priceModelPrice, priceModelVehicleModel;
    Button buttonPriceModelNext;
    String vehicle,price,model;

    String dropLoc;
    String pickLoc;
    String username;

    String loc1,loc2,loc3,loc4,loc5;
    String date, startTime, endTime;

    String noRides;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_model);

        String[] vehicleOptions = {"Choose Vehicle" ,"Car", "Jeep", "Van","Mini Van", "Mini Bus","Others"};

        AutoCompleteTextView priceModelVehicle = findViewById(R.id.priceModelVehicle);

        ArrayAdapter<String> adapterVehicle = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, vehicleOptions);
        adapterVehicle.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        priceModelVehicle.setAdapter(adapterVehicle);

        priceModelPrice= findViewById(R.id.priceModelPrice);
        priceModelVehicleModel = findViewById(R.id.priceModelVehicleModel);
        buttonPriceModelNext= findViewById(R.id.buttonPriceModelNext);
        final String[] vehicles = new String[1];
        priceModelVehicle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i>0){
                    vehicles[0] = adapterView.getItemAtPosition(i).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Intent intent = getIntent();

        dropLoc=intent.getStringExtra("dropLoc");
        pickLoc = intent.getStringExtra("pickLoc");

        String c = intent.getStringExtra("c");
        loc1 = intent.getStringExtra("loc1");
        loc2 = intent.getStringExtra("loc2");
        loc3 = intent.getStringExtra("loc3");
        loc4 = intent.getStringExtra("loc4");
        loc5 = intent.getStringExtra("loc5");
        noRides = intent.getStringExtra("noRides");

        date = intent.getStringExtra("date");
        startTime = intent.getStringExtra("startTime");
        endTime = intent.getStringExtra("endTime");
        buttonPriceModelNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                vehicle = priceModelVehicle.getText().toString();
                price = priceModelPrice.getText().toString();
                model= priceModelVehicleModel.getText().toString();

                Intent i = new Intent(PriceModelActivity.this, PersonsDescriptionActivity.class);
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
                i.putExtra("vehicle", vehicle);
                i.putExtra("model",model);
                i.putExtra("price",price);
                startActivity(i);
                finish();
            }
        });
    }
}