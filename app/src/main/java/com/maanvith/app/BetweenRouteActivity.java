package com.maanvith.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BetweenRouteActivity extends AppCompatActivity {
    String route;
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
    List<String> locationsList = Arrays.asList(locations);
    ImageView addRoute;
    TextView t1;
    TextView t2;
    TextView t3;
    TextView t4;
    TextView t5;
    int c =0;
    ArrayList<String> locationlist = new ArrayList<String>();
    ImageView imageVieww1,imageVieww2,imageVieww3,imageVieww4,imageVieww5;

    String dropLoc;
    String pickLoc;

    String loc1,loc2,loc3,loc4,loc5;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_between_route);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.statusBar));
        TextView toLocation, fromLocation;
        toLocation = findViewById(R.id.toLocation);
        fromLocation = findViewById(R.id.fromLocation);

        imageVieww1= findViewById(R.id.imageVieww1);
        imageVieww2= findViewById(R.id.imageVieww2);
        imageVieww3= findViewById(R.id.imageVieww3);
        imageVieww4= findViewById(R.id.imageVieww4);
        imageVieww5= findViewById(R.id.imageVieww5);
        Intent intent = getIntent();
        dropLoc=intent.getStringExtra("dropLoc");
        pickLoc = intent.getStringExtra("pickLoc");


        AutoCompleteTextView searchRoute = findViewById(R.id.searchRoute);
        addRoute = findViewById(R.id.addRoute);
        t1= findViewById(R.id.location1);
        t2= findViewById(R.id.location2);
        t3= findViewById(R.id.location3);
        t4= findViewById(R.id.location4);
        t5= findViewById(R.id.location5);
        Intent i = getIntent();
        String fromLocationn = i.getStringExtra("pickLoc");
        String toLocationn = i.getStringExtra("dropLoc");
        fromLocation.setText(fromLocationn);
        toLocation.setText(toLocationn);
        locationlist.add(fromLocationn);
        locationlist.add(toLocationn);
        Button betweenButton = findViewById(R.id.betweenButton);
        betweenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BetweenRouteActivity.this, TimePersonsActivity.class);
                i.putExtra("dropLoc",dropLoc);
                i.putExtra("pickLoc",pickLoc);
                if(c==1){
                    i.putExtra("loc1",loc1);
                }
                if (c==2) {
                    loc1 = t1.getText().toString();
                    i.putExtra("loc1",loc1);
                    loc2 = t2.getText().toString();
                    i.putExtra("loc2",loc2);
                }
                if (c==3) {
                    loc1 = t1.getText().toString();
                    i.putExtra("loc1",loc1);
                    loc2 = t2.getText().toString();
                    i.putExtra("loc2",loc2);
                    loc3 = t3.getText().toString();
                    i.putExtra("loc3",loc3);
                }
                if (c==4) {
                    loc1 = t1.getText().toString();
                    i.putExtra("loc1",loc1);
                    loc2 = t2.getText().toString();
                    i.putExtra("loc2",loc2);
                    loc3 = t3.getText().toString();
                    i.putExtra("loc3",loc3);
                    loc4 = t4.getText().toString();
                    i.putExtra("loc4",loc4);
                }
                if (c==5) {
                    loc1 = t1.getText().toString();
                    i.putExtra("loc1",loc1);
                    loc2 = t2.getText().toString();
                    i.putExtra("loc2",loc2);
                    loc3 = t3.getText().toString();
                    i.putExtra("loc3",loc3);
                    loc4 = t4.getText().toString();
                    i.putExtra("loc4",loc4);
                    loc5 = t5.getText().toString();
                    i.putExtra("loc5",loc5);
                }
                i.putExtra("c",""+c);
                startActivity(i);
                finish();
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_dropdown_item_1line, locations
        );

        searchRoute.setAdapter(adapter);
        searchRoute.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                route = (String)adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        addRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String r = searchRoute.getText().toString();

                if (c < 5) {
                    if(!locationsList.contains(r)){
                        Toast.makeText(BetweenRouteActivity.this, "Location Not Found", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        if(locationlist.contains(r)){
                            Toast.makeText(BetweenRouteActivity.this, "Location Already Added", Toast.LENGTH_SHORT).show();
                        }else{
                            locationlist.add(r);
                            if(c==0){
                                t1.setText(r);
                                t1.setVisibility(View.VISIBLE);
                                imageVieww1.setVisibility(View.VISIBLE);
                                searchRoute.setText("");

                            } else if (c==1) {
                                t2.setText(r);
                                t2.setVisibility(View.VISIBLE);
                                imageVieww2.setVisibility(View.VISIBLE);
                                searchRoute.setText("");

                            } else if (c==2) {
                                t3.setText(r);
                                t3.setVisibility(View.VISIBLE);
                                imageVieww3.setVisibility(View.VISIBLE);
                                searchRoute.setText("");

                            } else if (c==3) {
                                t4.setText(r);
                                t4.setVisibility(View.VISIBLE);
                                imageVieww4.setVisibility(View.VISIBLE);
                                searchRoute.setText("");

                            } else if (c==4) {
                                t5.setText(r);
                                t5.setVisibility(View.VISIBLE);
                                imageVieww5.setVisibility(View.VISIBLE);
                                searchRoute.setText("");

                            }
                            c++;
                        }

                    }
                }
                else{
                    Toast.makeText(BetweenRouteActivity.this, "Maximum Locations Added", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}