package it.uniba.di.sms.asilapp;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import it.uniba.di.sms.asilapp.models.City;

public class CityInfoActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    //variable declaration
    private static final String TAG = "CityInfoActivity";

    private String uId;
    private String _acceptance;
    private String _city;
    private String cityInfo;

    private TextView mDescription;

    private DatabaseReference mUserReference;
    private DatabaseReference mAcceptanceReference;
    private DatabaseReference mCityReference;
    private CardView card_view_Ambulatory,
            card_view_Municipality,
            card_view_Postoffice,
            card_view_PlacesOfWorship,
            card_view_School,
            card_view_Pharmacy;

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cityinfo);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();



        card_view_Ambulatory = findViewById(R.id.card_ambulatory);
        card_view_Ambulatory.setOnClickListener(card_view_Ambulatory_listener);
        card_view_Municipality = findViewById(R.id.card_municipality);
        card_view_Municipality.setOnClickListener(card_view_Municipality_listener);
        card_view_Postoffice = findViewById(R.id.card_postoffice);
        card_view_Postoffice.setOnClickListener(card_view_Postoffice_listener);
        card_view_PlacesOfWorship = findViewById(R.id.card_placesofworship);
        card_view_PlacesOfWorship.setOnClickListener(card_view_PlacesOfWorship_listener);
        card_view_School = findViewById(R.id.card_school);
        card_view_School.setOnClickListener(card_view_School_listener);
        card_view_Pharmacy = findViewById(R.id.card_pharmacy);
        card_view_Pharmacy.setOnClickListener(card_view_Pharmacy_listener);


        // Initialize FirebaseUser
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        // Get userId
        uId = user.getUid();
        // Initialize Database Reference
        mUserReference = FirebaseDatabase.getInstance().getReference().child("user").child(uId);
        mAcceptanceReference = FirebaseDatabase.getInstance().getReference().child("acceptance");
        mCityReference = FirebaseDatabase.getInstance().getReference().child("city");

        mDescription = findViewById(R.id.textViewCityDescription);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent sens;
        switch (item.getItemId()){
            case R.id.nav_info:
                drawer.closeDrawer(GravityCompat.START);
                sens = new Intent (CityInfoActivity.this, InformativeActivity.class);
                startActivity(sens);
                break;
            case R.id.nav_medicalRecords:
                drawer.closeDrawer(GravityCompat.START);
                sens = new Intent (CityInfoActivity.this, MedicalRecordsActivity.class);
                startActivity(sens);
                break;
            case R.id.nav_personalData:
                drawer.closeDrawer(GravityCompat.START);
                sens = new Intent (CityInfoActivity.this, PersonalDataActivity.class);
                startActivity(sens);
                break;
            case R.id.nav_questionnaires:
                drawer.closeDrawer(GravityCompat.START);
                sens = new Intent (CityInfoActivity.this, QuestionnairesActivity.class);
                startActivity(sens);
                break;
            case R.id.nav_logout:
                drawer.closeDrawer(GravityCompat.START);
                FirebaseAuth.getInstance().signOut();
                sens = new Intent(CityInfoActivity.this, MainActivity.class);
                startActivity(sens);
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }



    //function to get city basically information
    public void getCityInformation(String cityId) {
        ValueEventListener cityListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                City city = dataSnapshot.getValue(City.class);
                cityInfo = city.name + ", " + city.description;
                mDescription.setText(cityInfo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting city failed
                Log.w(TAG, "loadCity:onCancelled", databaseError.toException());
                Toast.makeText(CityInfoActivity.this, "Failed to load city",
                        Toast.LENGTH_SHORT).show();
            }
        };
        mCityReference.child(cityId).addValueEventListener(cityListener);
    }

    // function to get foreign key _city from acceptance table
    public void getCityId(String _acceptance) {
        mAcceptanceReference.child(_acceptance).child("_city").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                _city = dataSnapshot.getValue().toString();
                getCityInformation(_city);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Getting City Id failed, log a message
                Log.w(TAG, "loadCityId:onCancelled", databaseError.toException());
                Toast.makeText(CityInfoActivity.this, "Failed to load City Id",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    // function to get foreign key _acceptance from user table
    public void getAcceptanceId() {
        mUserReference.child("_acceptance").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                _acceptance = dataSnapshot.getValue().toString();
                getCityId(_acceptance);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Getting Acceptance Id failed, log a message
                Log.w(TAG, "loadAcceptanceId:onCancelled", databaseError.toException());
                Toast.makeText(CityInfoActivity.this, "Failed to load Acceptance Id",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        getAcceptanceId();
    }

    //Open Google Maps with especific view (ambulatory, postoffice...)

    public View.OnClickListener card_view_Ambulatory_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Uri uri = Uri.parse("https://www.google.com/maps/search/?api=1&query=pronto+soccorso");
            Intent mapIntent = new Intent(android.content.Intent.ACTION_VIEW, uri);
            startActivity(mapIntent);
        }
    };
    public View.OnClickListener card_view_Municipality_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Uri uri = Uri.parse("https://www.google.com/maps/search/?api=1&query=municipio");
            Intent mapIntent = new Intent(android.content.Intent.ACTION_VIEW, uri);
            startActivity(mapIntent);
        }
    };
    public View.OnClickListener card_view_Postoffice_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Uri uri = Uri.parse("https://www.google.com/maps/search/?api=1&query=ufficio+postale");
            Intent mapIntent = new Intent(android.content.Intent.ACTION_VIEW, uri);
            startActivity(mapIntent);
        }
    };
    public View.OnClickListener card_view_PlacesOfWorship_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Uri uri = Uri.parse("https://www.google.com/maps/search/?api=1&query=luoghi+di+culto");
            Intent mapIntent = new Intent(android.content.Intent.ACTION_VIEW, uri);
            startActivity(mapIntent);
        }
    };
    public View.OnClickListener card_view_School_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Uri uri = Uri.parse("https://www.google.com/maps/search/?api=1&query=scuola+di+italiano");
            Intent mapIntent = new Intent(android.content.Intent.ACTION_VIEW, uri);
            startActivity(mapIntent);
        }
    };
    public View.OnClickListener card_view_Pharmacy_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Uri uri = Uri.parse("https://www.google.com/maps/search/?api=1&query=farmacia");
            Intent mapIntent = new Intent(android.content.Intent.ACTION_VIEW, uri);
            startActivity(mapIntent);
        }
    };
    
}
