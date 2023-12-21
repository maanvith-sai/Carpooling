package com.maanvith.app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.gms.common.api.Status;


import java.util.Arrays;
import java.util.List;

public class AddPlacesActivity extends AppCompatActivity implements OnMapReadyCallback {
    private TextView placeSearch;
    Place place;

    private String apiKey = "AIzaSyCMKQEoOJokPHZWS884lUOJ4TG7h2vq6a8";
    int AUTOCOMPLETE_REQUEST_CODE =1;
    private static final String TAG = "MyApp";

    private GoogleMap mMap;
    List<Place.Field> fields;
    String longitude,latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_places);

        placeSearch = findViewById(R.id.placeSearch);

        if(Places.isInitialized()){
            Places.initialize(getApplicationContext(),apiKey);
        }
        PlacesClient placesClient = Places.createClient(this);

        fields = Arrays.asList(Place.Field.ID, Place.Field.NAME);


        placeSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields).build(AddPlacesActivity.this);
                startActivityForResult(intent,AUTOCOMPLETE_REQUEST_CODE);
            }
        });
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        LatLng sydney = new LatLng(-33.852, 151.211);
        mMap.addMarker(new MarkerOptions()
                .position(sydney)
                .title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                place = Autocomplete.getPlaceFromIntent(data);
                Log.i(TAG, "Place: " + place.getName()); // Logging the place name as an example
                placeSearch.setText(place.getAddress());
                longitude = String.valueOf(place.getLatLng().longitude);
                latitude = String.valueOf(place.getLatLng().latitude);
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(place.getLatLng()).title(place.getName()));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(place.getLatLng()));
                // You can log more details or use the place data as needed
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // Handle the error case
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG, "Error: " + status.getStatusMessage());
                // You can display an error message to the user or handle the error in some way
            } else if (resultCode == RESULT_CANCELED) {
                // Handle the canceled request (user canceled the autocomplete)
                Log.i(TAG, "Autocomplete canceled");
            }
        }
    }

}