package it.uniba.di.sms.asilapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;

public class InformativeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //Variable declaration
    private CardView card_view_Video;
    private CardView card_view_Acceptance;
    private CardView card_view_CityInfo;
    private CardView card_view_Bylaw;
    private CardView card_view_UsefulNumbers;
    private CardView card_view_MyInfo;
    private DrawerLayout drawer;

    private GridLayout gridLayout;

    private ImageButton imgBtnLanguage;

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
        setContentView(R.layout.activity_informative);

        //Defined variables
        gridLayout = findViewById(R.id.gridInformativeLayout);

        card_view_Video = findViewById(R.id.card_video);
        card_view_Bylaw = findViewById(R.id.card_bylaw);
        card_view_CityInfo = findViewById(R.id.city_info);
        card_view_MyInfo = findViewById(R.id.card_myInfo);
        card_view_Acceptance = findViewById(R.id.card_acceptance);
        card_view_UsefulNumbers = findViewById(R.id.card_usefulNumbers);
        imgBtnLanguage = findViewById(R.id.imgBtnLanguage);
        Toolbar toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        //Set a Toolbar to act as the ActionBar for this activity window
        setSupportActionBar(toolbar);
        //Set a listener that will be notified when a menu item is selected.
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

        //Create new ActionBarDraweToggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //Adds the specified listener to the list of listeners that will be notified of drawer events.
        drawer.addDrawerListener(toggle);
        //Synchronize the indicator with the state of the linked DrawerLayout after onRestoreInstanceState has occurred.
        toggle.syncState();



        SharedPreferences prefs = getSharedPreferences("CommonPrefs",
                Activity.MODE_PRIVATE);
        imgBtnLanguage.setImageResource(R.drawable.italy);
        String language = prefs.getString("Language", "");
        if (language.equals("en")) {
            imgBtnLanguage.setImageResource(R.drawable.lang);
        }

        //Set a click listener on the button object
        imgBtnLanguage.setOnClickListener(imgBtnLanguage_listener);
        //Set a click listener on the cardView objects
        card_view_Video.setOnClickListener(card_view_Video_listener);
        card_view_Acceptance.setOnClickListener(card_view_Acceptance_listener);
        card_view_CityInfo.setOnClickListener(card_view_CityInfo_listener);
        card_view_Bylaw.setOnClickListener(card_view_Bylaw_listener);
        card_view_UsefulNumbers.setOnClickListener(card_view_UsefulNumbers_listener);
        card_view_MyInfo.setOnClickListener(card_view_MyInfo_listener);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Intent refresh = new Intent(this, InformativeActivity.class);
        startActivity(refresh);
        this.finish();
    }

    public View.OnClickListener imgBtnLanguage_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent languageIntent = new Intent(InformativeActivity.this, PopUpLanguageActivity.class);
            languageIntent.putExtra("callingActivity", "it.uniba.di.sms.asilapp.InformativeActivity");
            startActivityForResult(languageIntent, 1);
        }
    };


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent sens;
        switch (item.getItemId()) {
            case R.id.nav_home:
                drawer.closeDrawer(GravityCompat.START);
                Intent nav_homeIntent = new Intent(InformativeActivity.this, HomepageActivity.class);
                startActivity(nav_homeIntent);
                break;
            case R.id.nav_info:
                drawer.closeDrawer(GravityCompat.START);
                sens = new Intent(InformativeActivity.this, InformativeActivity.class);
                startActivity(sens);
                break;
            case R.id.nav_medicalRecords:
                drawer.closeDrawer(GravityCompat.START);
                sens = new Intent(InformativeActivity.this, MedicalRecordsActivity.class);
                startActivity(sens);
                break;
            case R.id.nav_personalData:
                drawer.closeDrawer(GravityCompat.START);
                sens = new Intent(InformativeActivity.this, PersonalDataActivity.class);
                startActivity(sens);
                break;
            case R.id.nav_questionnaires:
                drawer.closeDrawer(GravityCompat.START);
                sens = new Intent(InformativeActivity.this, QuestionnairesActivity.class);
                startActivity(sens);
                break;
            case R.id.nav_logout:
                drawer.closeDrawer(GravityCompat.START);
                FirebaseAuth.getInstance().signOut();
                sens = new Intent(InformativeActivity.this, MainActivity.class);
                startActivity(sens);
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    
    public View.OnClickListener card_view_Video_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent sens = new Intent(InformativeActivity.this, VideoActivity.class);
            startActivity(sens);

        }
    };
    public View.OnClickListener card_view_Acceptance_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent sens = new Intent(InformativeActivity.this, AcceptanceActivity.class);
            startActivity(sens);

        }
    };

    public View.OnClickListener card_view_CityInfo_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent sens = new Intent(InformativeActivity.this, CityInfoActivity.class);
            startActivity(sens);

        }
    };

    public View.OnClickListener card_view_Bylaw_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent sens = new Intent(InformativeActivity.this, BylawActivity.class);
            startActivity(sens);

        }
    };

    public View.OnClickListener card_view_UsefulNumbers_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent sens = new Intent(InformativeActivity.this, UsefulNumbersActivity.class);
            startActivity(sens);

        }
    };

    public View.OnClickListener card_view_MyInfo_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent sens = new Intent(InformativeActivity.this, MyInfoActivity.class);
            startActivity(sens);

        }
    };
}

