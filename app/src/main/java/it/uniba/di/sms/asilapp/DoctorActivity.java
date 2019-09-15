package it.uniba.di.sms.asilapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
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

public class DoctorActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //Variable declaration
    CardView card_view_kitOpening;
    CardView card_view_patientList;
    CardView card_view_searchPatient;
    CardView card_view_video;
    FloatingActionButton chatButton;
    GridLayout gridLayout;
    private ImageButton imgBtnLanguage;
    private DrawerLayout drawer;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set the activity content from a layout resource.
        setContentView(R.layout.activity_doctor);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        imgBtnLanguage = findViewById(R.id.imgBtnLanguage);
        SharedPreferences prefs = getSharedPreferences("CommonPrefs",
                Activity.MODE_PRIVATE);
        imgBtnLanguage.setImageResource(R.drawable.italy);
        String language = prefs.getString("Language", "");
        if (language.equals("en")) {
            imgBtnLanguage.setImageResource(R.drawable.lang);
        }

        //defined gridlayout variable
        gridLayout = findViewById(R.id.gridDoctorLayout);
        //defined card variable
        card_view_kitOpening = findViewById(R.id.card_kitOpening);
        card_view_patientList = findViewById(R.id.card_patientList);
        card_view_searchPatient = findViewById(R.id.card_searchPatient);
        card_view_video = findViewById(R.id.card_Video);
        //Defined FloatingActionButton
        chatButton = findViewById(R.id.chatBtn);

        imgBtnLanguage.setOnClickListener(imgBtnLanguage_listener);
        //Set a click listener on the card objects
        card_view_searchPatient.setOnClickListener(card_view_searchPatient_listener);
        card_view_kitOpening.setOnClickListener(card_view_kitOpening_listener);
        card_view_patientList.setOnClickListener(card_view_patientList_listener);
        card_view_video.setOnClickListener(card_view_video_listener);
        //Set a click listener on the FloatingActionButton object
        chatButton.setOnClickListener(chatButton_listener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Intent refresh = new Intent(this, DoctorActivity.class);
        startActivity(refresh);
        this.finish();

    }

    public View.OnClickListener imgBtnLanguage_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent change_languageIntent = new Intent(DoctorActivity.this, PopUpLanguageActivity.class);
            change_languageIntent.putExtra("callingActivity", "it.uniba.di.sms.asilapp.DoctorActivity");
            startActivityForResult(change_languageIntent, 1);
        }
    };

    public View.OnClickListener card_view_searchPatient_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent search_patientIntent = new Intent(DoctorActivity.this, SearchPatientActivity.class);
            startActivity(search_patientIntent);

        }
    };

    public View.OnClickListener card_view_video_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent search_patientIntent = new Intent(DoctorActivity.this, VideoActivity.class);
            startActivity(search_patientIntent);

        }
    };

    public View.OnClickListener card_view_kitOpening_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent open_kitIntent = new Intent(DoctorActivity.this, KitOpeningActivity.class);
            startActivity(open_kitIntent);

        }
    };

    public View.OnClickListener card_view_patientList_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent open_PatientListIntent = new Intent(DoctorActivity.this, PatientListActivity.class);
            open_PatientListIntent.putExtra("class_name", DoctorActivity.class.getSimpleName());
            startActivity(open_PatientListIntent);

        }
    };

    public View.OnClickListener chatButton_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent chatIntent = new Intent(DoctorActivity.this, ChatActivity.class);
            startActivity(chatIntent);
        }
    };

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_homeDoctor:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_homeDoctorIntent = new Intent(DoctorActivity.this, DoctorActivity.class);
                startActivity(nav_homeDoctorIntent);
                break;
            case R.id.nav_search_patient:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_searchPatientIntent = new Intent(DoctorActivity.this, SearchPatientActivity.class);
                startActivity(nav_searchPatientIntent);
                break;
            case R.id.nav_kit_opening:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_kitOpeningIntent = new Intent(DoctorActivity.this, KitOpeningActivity.class);
                startActivity(nav_kitOpeningIntent);
                break;
            case R.id.nav_visited_patient:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_visitedPatientIntent = new Intent(DoctorActivity.this, PatientListActivity.class);
                startActivity(nav_visitedPatientIntent);
                break;
            case R.id.nav_video:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_videoIntent = new Intent(DoctorActivity.this, VideoActivity.class);
                startActivity(nav_videoIntent);
                break;
            case R.id.nav_logout:
                drawer.closeDrawer(GravityCompat.START);
                //Sign out function
                FirebaseAuth.getInstance().signOut();
                //Create new Intent
                Intent nav_logoutIntent = new Intent(DoctorActivity.this, MainActivity.class);
                nav_logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(nav_logoutIntent);
                finish();
                break;
        }
        return true;
    }
}
