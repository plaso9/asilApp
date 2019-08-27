package it.uniba.di.sms.asilapp;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import it.uniba.di.sms.asilapp.models.Acceptance;
import it.uniba.di.sms.asilapp.models.City;

public class CityInfoActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    //variable declaration
    private static final String TAG = "CityInfoActivity";

    private String uId;
    private String cityInfo;
    private String cityName;
    private String _acceptance;

    private TextView mDescription;

    private DatabaseReference mUserReference;
    private DatabaseReference mCityReference;
    private DatabaseReference mAcceptanceReference;
    private CardView card_view_School;
    private CardView card_view_Pharmacy;
    private CardView card_view_Ambulatory;
    private CardView card_view_Postoffice;
    private CardView card_view_Municipality;
    private CardView card_view_PlacesOfWorship;
    private ImageButton imgBtnLanguage;
    private Button btnShowMore;
    private DrawerLayout drawer;
    private MenuItem nav_video;
    private MenuItem nav_addUser;
    private MenuItem nav_homeAdmin;
    private MenuItem nav_homeDoctor;
    private MenuItem nav_kitOpening;
    private MenuItem nav_readRatings;
    private MenuItem nav_addAcceptance;
    private MenuItem nav_searchPatient;
    private MenuItem nav_visitedPatient;
    private MenuItem nav_addRetrieveNecessities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set the activity content from a layout resource.
        setContentView(R.layout.activity_cityinfo);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // get menu from navigationView
        Menu menu = navigationView.getMenu();

        // find MenuItem you want to change
        nav_video = menu.findItem(R.id.nav_video);
        nav_addUser = menu.findItem(R.id.nav_add_user);
        nav_homeAdmin = menu.findItem(R.id.nav_homeAdmin);
        nav_homeDoctor = menu.findItem(R.id.nav_homeDoctor);
        nav_kitOpening = menu.findItem(R.id.nav_kit_opening);
        nav_readRatings = menu.findItem(R.id.nav_read_ratings);
        nav_searchPatient = menu.findItem(R.id.nav_search_patient);
        nav_visitedPatient = menu.findItem(R.id.nav_visited_patient);
        nav_addAcceptance = menu.findItem(R.id.nav_add_new_acceptance);
        nav_addRetrieveNecessities = menu.findItem(R.id.nav_add_retrive_necessities);

        //Set item visibility
        nav_video.setVisible(false);
        nav_addUser.setVisible(false);
        nav_homeAdmin.setVisible(false);
        nav_homeDoctor.setVisible(false);
        nav_kitOpening.setVisible(false);
        nav_readRatings.setVisible(false);
        nav_searchPatient.setVisible(false);
        nav_visitedPatient.setVisible(false);
        nav_addAcceptance.setVisible(false);
        nav_addRetrieveNecessities.setVisible(false);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        //Defined variables
        card_view_Ambulatory = findViewById(R.id.card_ambulatory);
        card_view_Municipality = findViewById(R.id.card_municipality);
        card_view_Postoffice = findViewById(R.id.card_postoffice);
        card_view_PlacesOfWorship = findViewById(R.id.card_placesofworship);
        card_view_School = findViewById(R.id.card_school);
        card_view_Pharmacy = findViewById(R.id.card_pharmacy);
        mDescription = findViewById(R.id.textViewCityDescription);
        imgBtnLanguage = findViewById(R.id.imgBtnLanguage);

        imgBtnLanguage.setImageResource(R.drawable.italy);
        Configuration config = getBaseContext().getResources().getConfiguration();
        if (config.locale.getLanguage().equals("en")) {
            imgBtnLanguage.setImageResource(R.drawable.lang);
        }

        btnShowMore = findViewById(R.id.btnShowMore);


        // Initialize FirebaseUser
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        // Get userId
        uId = user.getUid();
        // Initialize Database Reference
        mUserReference = FirebaseDatabase.getInstance().getReference("user").child(uId);
        mAcceptanceReference = FirebaseDatabase.getInstance().getReference("acceptance");
        mCityReference = FirebaseDatabase.getInstance().getReference("city");

        //Set a click listener on the button object
        imgBtnLanguage.setOnClickListener(imgBtnLanguage_listener);
        btnShowMore.setOnClickListener(btnShowMore_listener);
        //Set a click listener on the card objects
        card_view_School.setOnClickListener(card_view_School_listener);
        card_view_Pharmacy.setOnClickListener(card_view_Pharmacy_listener);
        card_view_Ambulatory.setOnClickListener(card_view_Ambulatory_listener);
        card_view_Postoffice.setOnClickListener(card_view_Postoffice_listener);
        card_view_Municipality.setOnClickListener(card_view_Municipality_listener);
        card_view_PlacesOfWorship.setOnClickListener(card_view_PlacesOfWorship_listener);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) { // english
            if (resultCode == Activity.RESULT_CANCELED) {
                Intent refresh = new Intent(this, CityInfoActivity.class);
                startActivity(refresh);
                this.finish();
            }
        }
        if (requestCode == 2) { //italian
            if (resultCode == Activity.RESULT_CANCELED) {
                Intent refresh = new Intent(this, CityInfoActivity.class);
                startActivity(refresh);
                this.finish();
            }
        }
    }

    public View.OnClickListener imgBtnLanguage_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent change_languageIntent = new Intent (CityInfoActivity.this,PopUpLanguageActivity.class);
            change_languageIntent.putExtra("callingActivity", "it.uniba.di.sms.asilapp.CityInfoActivity");
            startActivity(change_languageIntent);
        }
    };

    public View.OnClickListener btnShowMore_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent show_moreIntent = new Intent (CityInfoActivity.this,PopUpShowMoreActivity.class);
            startActivity(show_moreIntent);
        }
    };
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {   //Called when an item in the navigation menu is selected.
        Intent sens;
        switch (item.getItemId()){
            case R.id.nav_home:
                drawer.closeDrawer(GravityCompat.START);
                Intent nav_homeIntent = new Intent (CityInfoActivity.this, HomepageActivity.class);
                startActivity(nav_homeIntent);
                break;
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

    //Method that retrieves from the Firebase Database the infos of the city given the iD of the city and sets the text of the TextView
    public void getCityInformation(final long cityId) {
        ValueEventListener cityListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    City city = snapshot.getValue(City.class);
                    if (city.id == cityId){
                        cityName = city.name;
                        cityInfo = cityName + " - " + city.description;
                        mDescription.setText(cityInfo);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting city failed
                Log.w(TAG, "loadCity:onCancelled", databaseError.toException());
                Toast.makeText(CityInfoActivity.this, "Failed to load city",
                        Toast.LENGTH_SHORT).show();
            }
        };
        mCityReference.addValueEventListener(cityListener);
    }

    //Method that retrieves from the Firebase Database the iD of the city given the iD of the acceptance
    public void getCityId(String _acceptance) {
        mAcceptanceReference.child(_acceptance).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Acceptance acceptance = dataSnapshot.getValue(Acceptance.class);
                getCityInformation(acceptance.getCity());
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

    //Method that retrieves the acceptanceId in which the user is hosted from the Firebase Database
    public void getAcceptanceId() {
        mUserReference.child("acceptanceId").addListenerForSingleValueEvent(new ValueEventListener() {
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

    //Open Google Maps with specific view (ambulatory, postoffice...)
    public View.OnClickListener card_view_Ambulatory_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Uri uri = Uri.parse("https://www.google.com/maps/search/?api=1&query=pronto+soccorso%2C+"+cityName);  //Search on Google "pronto soccorso, cityName"
            Intent mapIntent = new Intent(android.content.Intent.ACTION_VIEW, uri);
            startActivity(mapIntent);
        }
    };
    public View.OnClickListener card_view_Municipality_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Uri uri = Uri.parse("https://www.google.com/maps/search/?api=1&query=municipio%2C+"+cityName);
            Intent mapIntent = new Intent(android.content.Intent.ACTION_VIEW, uri);
            startActivity(mapIntent);
        }
    };
    public View.OnClickListener card_view_Postoffice_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Uri uri = Uri.parse("https://www.google.com/maps/search/?api=1&query=ufficio+postale%2C+"+cityName);
            Intent mapIntent = new Intent(android.content.Intent.ACTION_VIEW, uri);
            startActivity(mapIntent);
        }
    };
    public View.OnClickListener card_view_PlacesOfWorship_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Uri uri = Uri.parse("https://www.google.com/maps/search/?api=1&query=luoghi+di+culto%2C+"+cityName);
            Intent mapIntent = new Intent(android.content.Intent.ACTION_VIEW, uri);
            startActivity(mapIntent);
        }
    };
    public View.OnClickListener card_view_School_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Uri uri = Uri.parse("https://www.google.com/maps/search/?api=1&query=scuola+di+italiano%2C+"+cityName);
            Intent mapIntent = new Intent(android.content.Intent.ACTION_VIEW, uri);
            startActivity(mapIntent);
        }
    };
    public View.OnClickListener card_view_Pharmacy_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Uri uri = Uri.parse("https://www.google.com/maps/search/?api=1&query=farmacia%2C+"+cityName);
            Intent mapIntent = new Intent(android.content.Intent.ACTION_VIEW, uri);
            startActivity(mapIntent);
        }
    };
    
}
