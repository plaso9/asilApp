package it.uniba.di.sms.asilapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

public class SearchPatientActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //Variable declaration
    public static EditText editTextCode;
    private Button buttonSearch;
    private DrawerLayout drawer;
    private Button imageViewScanCode;
    private ImageButton imgBtnLanguage;
    private MenuItem nav_addUser;
    private MenuItem nav_homeAdmin;
    private MenuItem nav_readRatings;
    private MenuItem nav_addAcceptance;
    private MenuItem nav_addRetrieveNecessities;
    private MenuItem nav_home;
    private MenuItem nav_info;
    private MenuItem nav_personalData;
    private MenuItem nav_medicalRecords;
    private MenuItem nav_questionnaires;
    private NavigationView navigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {    //Called when the activity is starting.
        super.onCreate(savedInstanceState);
        //Set the activity content from a layout resource.
        setContentView(R.layout.activity_search_patient);

        //Defined variable
        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        imgBtnLanguage = findViewById(R.id.imgBtnLanguage);
        buttonSearch = findViewById(R.id.buttonSearch);
        imageViewScanCode = findViewById(R.id.imageViewScanCode);

        navigationView.setNavigationItemSelectedListener(this);
        setSupportActionBar(toolbar);

        // get menu from navigationView
        Menu menu = navigationView.getMenu();

        // find MenuItem you want to change
        nav_home = menu.findItem(R.id.nav_home);
        nav_info = menu.findItem(R.id.nav_info);
        nav_addUser = menu.findItem(R.id.nav_add_user);
        nav_homeAdmin = menu.findItem(R.id.nav_homeAdmin);
        nav_readRatings = menu.findItem(R.id.nav_read_ratings);
        nav_personalData = menu.findItem(R.id.nav_personalData);
        nav_addAcceptance = menu.findItem(R.id.nav_add_new_acceptance);
        nav_medicalRecords = menu.findItem(R.id.nav_medicalRecords);
        nav_questionnaires = menu.findItem(R.id.nav_questionnaires);
        nav_addRetrieveNecessities = menu.findItem(R.id.nav_add_retrive_necessities);

        //Set item visibility
        nav_home.setVisible(false);
        nav_info.setVisible(false);
        nav_addUser.setVisible(false);
        nav_homeAdmin.setVisible(false);
        nav_readRatings.setVisible(false);
        nav_personalData.setVisible(false);
        nav_addAcceptance.setVisible(false);
        nav_medicalRecords.setVisible(false);
        nav_questionnaires.setVisible(false);
        nav_addRetrieveNecessities.setVisible(false);

        SharedPreferences prefs = getSharedPreferences("CommonPrefs",
                Activity.MODE_PRIVATE);
        imgBtnLanguage.setImageResource(R.drawable.italy);
        String language = prefs.getString("Language", "");
        if (language.equals("en")) {
            imgBtnLanguage.setImageResource(R.drawable.lang);
        }
        //Set a click listener on the button object
        imgBtnLanguage.setOnClickListener(imgBtnLanguage_listener);
        
        //Create new ActionBarDraweToggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //Adds the specified listener to the list of listeners that will be notified of drawer events.
        drawer.addDrawerListener(toggle);
        //Synchronize the indicator with the state of the linked DrawerLayout after onRestoreInstanceState has occurred.
        toggle.syncState();

        //Set a click listener on the imageView objects
        imageViewScanCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create new Intent
                Intent scanCodeIntent = new Intent(SearchPatientActivity.this, ScanActivity.class);
                startActivity(scanCodeIntent);
            }
        });

        //Set a click listener on the button objects
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create new Intent
                Intent searchIntent = new Intent(SearchPatientActivity.this, PatientListActivity.class);
                searchIntent.putExtra("class_name", SearchPatientActivity.class.getSimpleName());
                startActivityForResult(searchIntent, 1);
            }
        });

    }

    @Override
    public void onBackPressed() {   //Called when the activity has detected the user's press of the back key.
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
        Intent intent = new Intent (SearchPatientActivity.this, DoctorActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { //receive result from activity
        //Create new Intent
        Intent refresh = new Intent(this, SearchPatientActivity.class);
        startActivity(refresh);
        this.finish();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {   //Called when an item in the navigation menu is selected.
        switch (item.getItemId()) {
            case R.id.nav_homeDoctor:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_homeDoctorIntent = new Intent(SearchPatientActivity.this, DoctorActivity.class);
                startActivity(nav_homeDoctorIntent);
                break;
            case R.id.nav_search_patient:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_searchPatientIntent = new Intent(SearchPatientActivity.this, SearchPatientActivity.class);
                startActivity(nav_searchPatientIntent);
                break;
            case R.id.nav_kit_opening:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_kitOpeningIntent = new Intent(SearchPatientActivity.this, KitOpeningActivity.class);
                startActivity(nav_kitOpeningIntent);
                break;
            case R.id.nav_visited_patient:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_visitedPatientIntent = new Intent(SearchPatientActivity.this, PatientListActivity.class);
                startActivity(nav_visitedPatientIntent);
                break;
            case R.id.nav_video:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_videoIntent = new Intent(SearchPatientActivity.this, VideoActivity.class);
                startActivity(nav_videoIntent);
                break;
            case R.id.nav_logout:
                drawer.closeDrawer(GravityCompat.START);
                //Sign out function
                FirebaseAuth.getInstance().signOut();
                //Create new Intent
                Intent nav_logoutIntent = new Intent(SearchPatientActivity.this, MainActivity.class);
                nav_logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(nav_logoutIntent);
                finish();
                break;
        }
        return true;
    }

    public View.OnClickListener imgBtnLanguage_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Create new Intent
            Intent languageIntent = new Intent(SearchPatientActivity.this, PopUpLanguageActivity.class);
            //Pass data between intents
            languageIntent.putExtra("callingActivity", "it.uniba.di.sms.asilapp.SearchPatientActivity");
            startActivityForResult(languageIntent, 1);
        }
    };
}
