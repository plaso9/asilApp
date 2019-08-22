package it.uniba.di.sms.asilapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import it.uniba.di.sms.asilapp.models.BasicNecessities;

public class RetrieveBasicNecessitiesActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    //variable declaration
    private static final String TAG = "RBasicNecesActivity"; //tag too long
    private String uId;
    private ImageView imageMapFood;
    private ImageView imageMapPharmacy;
    private String _acceptance;
    private TextView mAddressFood;
    private TextView mAddressPharmacy;
    private TextView userId;
    private TextView userId2;

    private DatabaseReference mUserReference;
    private DatabaseReference mBasicNecessities;
    private ImageButton imgBtnLanguage;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_basic_necessities);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();



        imageMapFood = findViewById(R.id.imageMapFood);
        imageMapFood.setOnClickListener(image_Map_Food_listener);
        imageMapPharmacy = findViewById(R.id.imageMapPharmacy);
        imageMapPharmacy.setOnClickListener(image_Map_Pharmacy_listener);

        // Initialize FirebaseUser
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        // Get userId
        uId = user.getUid();

        // Initialize Database Reference
        mUserReference = FirebaseDatabase.getInstance().getReference().child("user").child(uId);
        mBasicNecessities = FirebaseDatabase.getInstance().getReference().child("basic_necessities");

        mAddressFood = findViewById(R.id.textViewFoodAddress);
        mAddressPharmacy = findViewById(R.id.textViewPharmacyAddress);
        userId = findViewById(R.id.textViewUserId);
        userId2 = findViewById(R.id.textViewUserId2);
        imgBtnLanguage = findViewById(R.id.imgBtnLanguage);
        imgBtnLanguage.setOnClickListener(imgBtnLanguage_listener);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) { // english

            if (resultCode == Activity.RESULT_CANCELED) {
                imgBtnLanguage.setImageResource(R.drawable.lang);
                Intent refresh = new Intent(this, RetrieveBasicNecessitiesActivity.class);
                startActivity(refresh);
                this.finish();
            }


        }
        if (requestCode == 2) { //italian

            if (resultCode == Activity.RESULT_CANCELED) {
                imgBtnLanguage.setImageResource(R.drawable.italy);
                Intent refresh = new Intent(this, RetrieveBasicNecessitiesActivity.class);
                startActivity(refresh);
                this.finish();
            }


        }
    }

    public View.OnClickListener imgBtnLanguage_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent languageIntent = new Intent (RetrieveBasicNecessitiesActivity.this,PopUpLanguageActivity.class);
            languageIntent.putExtra("callingActivity", "it.uniba.di.sms.asilapp.RetrieveBasicNecessitiesActivity");
            startActivity(languageIntent);
        }
    };


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent sens;
        switch (item.getItemId()){
            case R.id.nav_info:
                drawer.closeDrawer(GravityCompat.START);
                sens = new Intent (RetrieveBasicNecessitiesActivity.this, InformativeActivity.class);
                startActivity(sens);
                break;
            case R.id.nav_medicalRecords:
                drawer.closeDrawer(GravityCompat.START);
                sens = new Intent (RetrieveBasicNecessitiesActivity.this, MedicalRecordsActivity.class);
                startActivity(sens);
                break;
            case R.id.nav_personalData:
                drawer.closeDrawer(GravityCompat.START);
                sens = new Intent (RetrieveBasicNecessitiesActivity.this, PersonalDataActivity.class);
                startActivity(sens);
                break;
            case R.id.nav_questionnaires:
                drawer.closeDrawer(GravityCompat.START);
                sens = new Intent (RetrieveBasicNecessitiesActivity.this, QuestionnairesActivity.class);
                startActivity(sens);
                break;
            case R.id.nav_logout:
                drawer.closeDrawer(GravityCompat.START);
                FirebaseAuth.getInstance().signOut();
                sens = new Intent(RetrieveBasicNecessitiesActivity.this, MainActivity.class);
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



    //Intent on image Map
    public View.OnClickListener image_Map_Food_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Uri uri = Uri.parse("https://www.google.com/maps/search/?api=1&query="+mAddressFood.getText());
            Intent mapIntent = new Intent(android.content.Intent.ACTION_VIEW, uri);
            startActivity(mapIntent);
        }
    };
    //Intent on image Map
    public View.OnClickListener image_Map_Pharmacy_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Uri uri = Uri.parse("https://www.google.com/maps/search/?api=1&query="+mAddressPharmacy.getText());
            Intent mapIntent = new Intent(android.content.Intent.ACTION_VIEW, uri);
            startActivity(mapIntent);
        }
    };

    //function to get basic necessities basically information
    public void getBasicNecessitiesInfo(String acceptanceId) {
        ValueEventListener cityListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                BasicNecessities basic_necessities = dataSnapshot.getValue(BasicNecessities.class);
                mAddressFood.setText(basic_necessities.name);
                mAddressPharmacy.setText(basic_necessities.name);
                userId.setText(uId);
                userId2.setText(uId);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting basic necessities failed
                Log.w(TAG, "loadBasicNecessities:onCancelled", databaseError.toException());
                Toast.makeText(RetrieveBasicNecessitiesActivity.this, "Failed to load basic necessities",
                        Toast.LENGTH_SHORT).show();
            }
        };
        mBasicNecessities.child(acceptanceId).addValueEventListener(cityListener);
    }

    // function to get foreign key _acceptance from basic necessities table
    public void getAcceptanceId() {
        mUserReference.child("_acceptance").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                _acceptance = dataSnapshot.getValue().toString();
                getBasicNecessitiesInfo(_acceptance);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Getting Acceptance Id failed, log a message
                Log.w(TAG, "loadAcceptanceId:onCancelled", databaseError.toException());
                Toast.makeText(RetrieveBasicNecessitiesActivity.this, "Failed to load Acceptance Id",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        getAcceptanceId();
    }
}
