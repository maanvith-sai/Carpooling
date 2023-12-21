package com.maanvith.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.location.LocationRequest;

import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class PickupActivity extends AppCompatActivity {
    static final String[] locations = {
            "Chintapalle",
            "Chintur",
            "Etapaka",
            "Kunavaram",
            "Vararamachandrapuram",
            "Ananthagiri",
            "Araku Valley",
            "Dumbriguda",
            "Ganagaraju Madugula",
            "Gudem Kotha Veedhi",
            "Hukumpeta",
            "Koyyuru",
            "Paderu",
            "Peda Bayalu",
            "Addateegala",
            "Devipatnam",
            "Gangavaram",
            "Maredumilli",
            "Munchingi Puttu",
            "Rajavommangi",
            "Rampachodavaram",
            "Y. Ramavaram",
            "Anakapalle",
            "Atchutapuram",
            "Butchayyapeta",
            "Chodavaram",
            "Devarapalli",
            "Elamanchili",
            "K.Kotapadu",
            "Kasimkota",
            "Munagapaka",
            "Paravada",
            "Rambilli",
            "Sabbavaram",
            "Cheedikada",
            "Golugonda",
            "Kotauratla",
            "Madugula",
            "Makavarapalem",
            "Nakkapalle",
            "Narsipatnam",
            "Nathavaram",
            "Payakaraopeta",
            "Ravikamatham",
            "Rolugunta",
            "Sarvasiddhi Rayavaram",
            "Anantapur Urban",
            "Anantapur Rural",
            "Atmakur",
            "Bukkaraya Samudram",
            "Garladinne",
            "Kudair",
            "Narpala",
            "Peddapappur",
            "Putlur",
            "Raptadu",
            "Singanamala",
            "Tadipatri",
            "Yellanur",
            "Gooty",
            "Guntakal",
            "Pamidi",
            "Peddavadugur",
            "Uravakonda",
            "Vajrakarur",
            "Vidapanakal",
            "Yadiki",
            "Beluguppa",
            "Bommanahal",
            "Brahmasamudram",
            "D.Hirehal",
            "Gummagatta",
            "Kalyandurg",
            "Kambadur",
            "Kanekal",
            "Kundurpi",
            "Rayadurg",
            "Settur",
            "Beerangi Kothakota",
            "Kalikiri",
            "Kurabalakota",
            "Madanapalle",
            "Mulakalacheruvu",
            "Nimmanapalle",
            "Pedda Thippasamudram",
            "Peddamandyam",
            "Ramasamudram",
            "Thamballapalle",
            "Valmikipuram",
            "Chitvel",
            "Railway Koduru",
            "Nandalur",
            "Obulavaripalle",
            "Penagalur",
            "Pullampeta",
            "Rajampet",
            "T Sundupalle",
            "Veeraballi",
            "Chinnamandyam",
            "Galiveedu",
            "Gurramkonda",
            "Kalakada",
            "Kambhamvaripalle",
            "Lakkireddypalle",
            "Pileru",
            "Ramapuram",
            "Rayachoti",
            "Sambepalli",
            "Bapatla",
            "Karlapalem",
            "Martur",
            "Parchur",
            "Pittalavanipalem",
            "Yeddanapudi",
            "Addanki",
            "Ballikurava",
            "Chinaganjam",
            "Chirala",
            "Inkollu",
            "Janakavarampanguluru",
            "Karamchedu",
            "Korisapadu",
            "Santhamaguluru",
            "Vetapalem",
            "Amruthalur",
            "Bhattiprolu",
            "Cherukupalle",
            "Kolluru",
            "Nagaram",
            "Nizampatnam",
            "Repalle",
            "Tsundur",
            "Vemuru",
            "Chittoor Rural",
            "Chittoor Urban",
            "Gangadhara Nellore",
            "Gudipala",
            "Irala",
            "Penumuru",
            "Pulicherla",
            "Puthalapattu",
            "Rompicherla",
            "Srirangarajapuram",
            "Thavanampalle",
            "Vedurukuppam",
            "Yadamarri",
            "Gudupalle",
            "Kuppam",
            "Ramakuppam",
            "Santhipuram",
            "Karvetinagar",
            "Nagari",
            "Nindra",
            "Palasamudram",
            "Vijayapuram",
            "Baireddipalle",
            "Bangarupalem",
            "Chowdepalle",
            "Gangavaram",
            "Palamaner",
            "Peddapanjani",
            "Punganur",
            "Sodam",
            "Somala",
            "Venkatagirikota",
            "Chagallu",
            "Devarapalle",
            "Gopalapuram",
            "Kovvur",
            "Nallajerla",
            "Nidadavole",
            "Peravali",
            "Tallapudi",
            "Undrajavaram",
            "Anaparthi",
            "Biccavolu",
            "Gokavaram",
            "Kadiam",
            "Korukonda",
            "Rajahmundry Urban",
            "Rajahmundry Rural",
            "Rajanagaram",
            "Rangampeta",
            "Seethanagaram",
            "Bhimadole",
            "Denduluru",
            "Eluru",
            "Kaikalur",
            "Kalidindi",
            "Mandavalli",
            "Mudinepalle",
            "Nidamarru",
            "Pedapadu",
            "Pedavegi",
            "Unguturu",
            "Buttayagudem",
            "Dwaraka Tirumala",
            "Jangareddygudem",
            "Jeelugu Milli",
            "Kamavarapukota",
            "Koyyalagudem",
            "Kukunoor",
            "Polavaram",
            "T. Narasapuram",
            "Velairpadu",
            "Agiripalle",
            "Chatrai",
            "Chintalapudi",
            "Lingapalem",
            "Musunuru",
            "Nuzvid",
            "Guntur East",
            "Guntur West",
            "Medikonduru",
            "Pedakakani",
            "Pedanandipadu",
            "Phirangipuram",
            "Prathipadu",
            "Tadikonda",
            "Thullur",
            "Vatticherukuru",
            "Chebrolu",
            "Duggirala",
            "Kakumanu",
            "Kollipara",
            "Mangalagiri",
            "Ponnur",
            "Tadepalle",
            "Tenali",
            "Gollaprolu",
            "Kajuluru",
            "Kakinada Rural",
            "Kakinada Urban",
            "Karapa",
            "U.Kothapalli",
            "Pedapudi",
            "Pithapuram",
            "Samalkota",
            "Thallarevu",
            "Gandepalli",
            "Jaggampeta",
            "Kirlampudi",
            "Kotananduru",
            "Peddapuram",
            "Prathipadu",
            "Rowthulapudi",
            "Sankhavaram",
            "Thondangi",
            "Tuni",
            "Yeleswaram",
            "Allavaram",
            "Amalapuram",
            "Island Polavaram",
            "Katrenikona",
            "Malikipuram",
            "Mamidikuduru",
            "Mummidivaram",
            "Razole",
            "Sakhinetipalle",
            "Uppalaguptam",
            "Ainavilli",
            "Alumuru",
            "Ambajipeta",
            "Atreyapuram",
            "Kothapeta",
            "Patha Gannavaram",
            "Ravulapalem",
            "Gangavaram",
            "Kapileswarapuram",
            "Mandapeta",
            "Ramachandrapuram",
            "Rayavaram",
            "Bapulapadu",
            "Gannavaram",
            "Gudivada",
            "Gudlavalleru",
            "Nandivada",
            "Pedaparupudi",
            "Unguturu",
            "Avanigadda",
            "Bantumilli",
            "Challapalli",
            "Ghantasala",
            "Guduru",
            "Koduru",
            "Kruthivennu",
            "Machilipatnam North",
            "Machilipatnam South",
            "Mopidevi",
            "Nagayalanka",
            "Pedana",
            "Kankipadu",
            "Movva",
            "Pamarru",
            "Pamidimukkala",
            "Penamaluru",
            "Thotlavalluru",
            "Vuyyuru",
            "Adoni",
            "Gonegandla",
            "Holagunda",
            "Kosigi",
            "Kowthalam",
            "Mantralayam",
            "Nandavaram",
            "Pedda Kadubur",
            "Yemmiganur",
            "C.Belagal",
            "Gudur",
            "Kallur",
            "Kodumur",
            "Kurnool Urban",
            "Kurnool Rural",
            "Orvakal",
            "Veldurthi",
            "Alur",
            "Aspari",
            "Chippagiri",
            "Devanakonda",
            "Halaharvi",
            "Krishnagiri",
            "Maddikera East",
            "Pattikonda",
            "Tuggali",
            "Atmakur",
            "Bandi Atmakur",
            "Jupadu Bungalow",
            "Kothapalle",
            "Midthuru",
            "Nandikotkur",
            "Pagidyala",
            "Pamulapadu",
            "Srisailam",
            "Velgodu",
            "Banaganapalle",
            "Bethamcherla",
            "Dhone",
            "Koilkuntla",
            "Owk",
            "Peapally",
            "Allagadda",
            "Chagalamarri",
            "Dornipadu",
            "Gadivemula",
            "Gospadu",
            "Kolimigundla",
            "Mahanandi",
            "Nandyal Rural",
            "Nandyal Urban",
            "Panyam",
            "Rudravaram",
            "Sanjamala",
            "Sirvella",
            "Uyyalawada",
            "Chandarlapadu",
            "Jaggayyapeta",
            "Kanchikacherla",
            "Nandigama",
            "Penuganchiprolu",
            "Vatsavai",
            "Veerullapadu",
            "A. Konduru",
            "Gampalagudem",
            "Reddigudem",
            "Tiruvuru",
            "Vissannapeta",
            "G.Konduru",
            "Ibrahimpatnam",
            "Mylavaram",
            "Vijayawada Rural",
            "Vijayawada North",
            "Vijayawada Central",
            "Vijayawada East",
            "Vijayawada West",
            "Dachepalle",
            "Durgi",
            "Gurazala",
            "Karempudi",
            "Macharla",
            "Machavaram",
            "Piduguralla",
            "Rentachintala",
            "Veldurthi",
            "Bollapalle",
            "Chilakaluripet",
            "Edlapadu",
            "Ipuru",
            "Nadendla",
            "Narasaraopet",
            "Nuzendla",
            "Rompicherla",
            "Savalyapuram",
            "Vinukonda",
            "Amaravathi",
            "Atchampet",
            "Bellamkonda",
            "Krosuru",
            "Muppalla",
            "Nekarikallu",
            "Pedakurapadu",
            "Rajupalem",
            "Sattenapalle",
            "Bhamini",
            "Gummalakshmipuram",
            "Jiyyammavalasa",
            "Kurupam",
            "Palakonda",
            "Seethampeta",
            "Veeraghattam",
            "Parvathipuram",
            "Balijipeta",
            "Garugubilli",
            "Komarada",
            "Makkuva",
            "Pachipenta",
            "Parvathipuram",
            "Salur",
            "Seethanagaram",
            "Chandra Sekhara Puram",
            "Darsi",
            "Donakonda",
            "Hanumanthuni Padu",
            "Kanigiri",
            "Konakanamitla",
            "Kurichedu",
            "Marripudi",
            "Pamuru",
            "Pedacherlo Palle",
            "Podili",
            "Ponnaluru",
            "Veligandla",
            "Ardhaveedu",
            "Bestawaripeta",
            "Cumbum",
            "Dornala",
            "Giddalur",
            "Komarolu",
            "Markapuram",
            "Peda Araveedu",
            "Pullalacheruvu",
            "Racherla",
            "Tarlupadu",
            "Tripuranthakam",
            "Yerragondapalem",
            "Chimakurthi",
            "Kondapi",
            "Kotha Patnam",
            "Maddipadu",
            "Mundlamuru",
            "Naguluppala Padu",
            "Ongole Rural",
            "Ongole Urban",
            "Santhanuthala Padu",
            "Singarayakonda",
            "Tangutur",
            "Thallur",
            "Zarugumilli",
            "Ananthasagaram",
            "Anumasamudrampeta",
            "Atmakuru",
            "Chejerla",
            "Kaluvoya",
            "Marripadu",
            "Sangam",
            "Sitarampuramu",
            "Udayagiri",
            "Gudluru",
            "Kandukur",
            "Kondapuram",
            "Lingasamudram",
            "Ulavapadu",
            "Varikuntapadu",
            "Voletivaripalem",
            "Allur",
            "Bogolu",
            "Dagadarthi",
            "Duttaluru",
            "Jaladanki",
            "Kaligiri",
            "Kavali",
            "Kodavaluru",
            "Vidavaluru",
            "Vinjamuru",
            "Buchireddypalem",
            "Indukurpet",
            "Kovuru",
            "Manubolu",
            "Muttukuru",
            "Nellore Urban",
            "Nellore Rural",
            "Podalakuru",
            "Rapuru",
            "Saidapuramu",
            "Thotapalligudur",
            "Venkatachalam",
            "Bathalapalle",
            "Chennekothapalle",
            "Dharmavaram",
            "Kanaganapalle",
            "Mudigubba",
            "Ramagiri",
            "Tadimarri",
            "Amadagur",
            "Gandlapenta",
            "Kadiri",
            "Lepakshi",
            "Nallacheruvu",
            "Nambulipulikunta",
            "Tanakal",
            "Agali",
            "Amarapuram",
            "Chilamathur",
            "Gudibanda",
            "Hindupur",
            "Madakasira",
            "Parigi",
            "Penukonda",
            "Roddam",
            "Rolla",
            "Somandepalle",
            "Talupula",
            "Bukkapatnam",
            "Gorantla",
            "Kothacheruvu",
            "Nallamada",
            "Obuladevaracheruvu",
            "Puttaparthi",
            "Ichchapuram",
            "Kanchili",
            "Kaviti",
            "Mandasa",
            "Nandigam",
            "Palasa",
            "Sompeta",
            "Vajrapukothuru",
            "Amadalavalasa",
            "Burja",
            "Etcherla",
            "Ganguvarisigadam",
            "Gara",
            "Jalumuru",
            "Laveru",
            "Narasannapeta",
            "Polaki",
            "Ponduru",
            "Ranastalam",
            "Sarubujjili",
            "Srikakulam",
            "Hiramandalam",
            "Kotabommali",
            "Kothuru",
            "Lakshminarsupeta",
            "Meliaputti",
            "Pathapatnam",
            "Santhabommali",
            "Saravakota",
            "Tekkali",
            "Balayapalli",
            "Chilakuru",
            "Chittamuru",
            "Dakkili",
            "Gudur",
            "Kota",
            "Vakadu",
            "Venkatagiri",
            "K. V. B. Puram",
            "Nagalapuram",
            "Narayanavanam",
            "Pichatur",
            "Renigunta",
            "Srikalahasti",
            "Thottambedu",
            "Yerpedu",
            "Buchinaidu Kandriga",
            "Doravarisatramu",
            "Naidupeta",
            "Ojili",
            "Pellakuru",
            "Satyavedu",
            "Sullurpeta",
            "Tada",
            "Varadaiahpalem",
            "Chandragiri",
            "Chinnagottigallu",
            "Pakala",
            "Puttur",
            "Ramachandrapuram",
            "Tirupati Rural",
            "Tirupati Urban",
            "Vadamalapeta",
            "Yerravaripalem",
            "Anandapuram",
            "Bheemunipatnam",
            "Padmanabham",
            "Seethammadhara",
            "Visakhapatnam Rural",
            "Gajuwaka",
            "Gopalapatnam",
            "Maharanipeta",
            "Mulagada",
            "Pedagantyada",
            "Pendurthi",
            "Badangi",
            "Bobbili",
            "Dattirajeru",
            "Gajapathinagaram",
            "Mentada",
            "Ramabhadrapuram",
            "Therlam",
            "Cheepurupalle",
            "Garividi",
            "Gurla",
            "Merakamudidam",
            "Rajam",
            "Regidi Amadalavalasa",
            "Santhakaviti",
            "Vangara",
            "Bhogapuram",
            "Bondapalle",
            "Denkada",
            "Gantyada",
            "Jami",
            "Kothavalasa",
            "Lakkavarapukota",
            "Nellimarla",
            "Pusapatirega",
            "Srungavarapukota",
            "Vepada",
            "Vizianagaram Rural",
            "Vizianagaram Urban",
            "Akividu",
            "Bhimavaram",
            "Kalla",
            "Palacoderu",
            "Undi",
            "Veeravasaram",
            "Achanta",
            "Mogalthur",
            "Palakollu",
            "Penugonda",
            "Penumantra",
            "Poduru",
            "T.Narasapuram",
            "Yelamanchili",
            "Attili",
            "Ganapavaram",
            "Iragavaram",
            "Pentapadu",
            "Tadepalligudem",
            "Tanuku",
            "Atlur",
            "B.Kodur",
            "Badvel",
            "Brahmamgarimattam",
            "Chapad",
            "Duvvur",
            "Gopavaram",
            "Kalasapadu",
            "Khajipet",
            "Porumamilla",
            "S.Mydukur",
            "Sri Avadhutha Kasinayana",
            "Jammalamadugu",
            "Kondapuram",
            "Muddanur",
            "Mylavaram",
            "Peddamudium",
            "Proddatur",
            "Rajupalem",
            "Chennur",
            "Chinthakommadinne",
            "Kadapa",
            "Kamalapuram",
            "Pendlimarri",
            "Sidhout",
            "Vallur",
            "Vontimitta",
            "Yerraguntla",
            "Chakrayapet",
            "Lingala",
            "Pulivendla",
            "Simhadripuram",
            "Thondur",
            "Veerapunayunipalle",
            "Vempalle"
    };
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private GoogleMap googleMap;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    ImageView getlocation;
    AutoCompleteTextView pickupLocation;
    FloatingActionButton next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickup);

        pickupLocation = findViewById(R.id.editTextPickupLocation);



        next = findViewById(R.id.pickupnext);
        ImageView pickupSearch = findViewById(R.id.pickupSearch);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pickupLocation.getText().toString().isEmpty()){
                    Toast.makeText(PickupActivity.this, "Enter the Location", Toast.LENGTH_SHORT).show();
                }else{
                    String pickLoc = pickupLocation.getText().toString();
                    Intent i = new Intent(PickupActivity.this,DropActivity.class);
                    i.putExtra("pickLoc", pickLoc);
                    startActivity(i);
                    finish();
                }
            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_dropdown_item_1line, locations
        );

        pickupLocation.setAdapter(adapter);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.statusBar));


        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.startmaps, mapFragment);
        transaction.commit();

        getlocation = findViewById(R.id.getLocation);

        getlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCurrentLocation();
            }
        });


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        createLocationRequest();
        createLocationCallback();

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap gMap) {
                googleMap = gMap;
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    googleMap.setMyLocationEnabled(true);
                } else {
                    ActivityCompat.requestPermissions(PickupActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
                }
            }
        });


        pickupSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String location = pickupLocation.getText().toString();
                LatLng latLng = getLatLngFromLocation(PickupActivity.this, location);
                if (latLng != null && googleMap != null) {
                    googleMap.clear();
                    googleMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title(location));
                    googleMap.animateCamera(com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom(latLng, 15));


                }
            }
        });
    }


    private void getCurrentLocation() {
        googleMap.clear();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                        try{
                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            String address = addresses.get(0).getAddressLine(0);
                            pickupLocation.setText(address);
                        }
                        catch (IOException e){
                            e.printStackTrace();
                        }

                        googleMap.addMarker(new MarkerOptions()
                                .position(currentLatLng)
                                .title("Current Location"));


                        // Move the camera to the current location
                        googleMap.moveCamera(com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom(currentLatLng, 15));



                    }
                }
            });

            // Request location updates
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION_PERMISSION);
        }
    }


    private void createLocationRequest() {
        locationRequest = new LocationRequest.Builder(LocationRequest.PRIORITY_HIGH_ACCURACY, 10000)
                .build();

        locationRequest.setFastestInterval(5000);
    }

    private void createLocationCallback() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }

                Location location = locationResult.getLastLocation();
                if (location != null) {
                    LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                    // Move the camera to the current location
                    googleMap.moveCamera(com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom(currentLatLng, 15));
                }
            }
        };
    }


    private com.google.android.gms.maps.model.LatLng getLatLngFromLocation(Context context, String location) {
        Geocoder geocoder = new Geocoder(context);
        try {
            List<Address> addressList = geocoder.getFromLocationName(location, 1);
            if (!addressList.isEmpty()) {
                Address address = addressList.get(0);
                double latitude = address.getLatitude();
                double longitude = address.getLongitude();
                LatLng l = new LatLng(latitude, longitude);


//
                return l;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}