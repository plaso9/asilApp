package it.uniba.di.sms.asilapp;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
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
import com.google.firebase.auth.FirebaseUser;

public class MyInfoActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    //variable declaration
    private CardView card_view_Pathology;
    private CardView card_view_RetrieveBasicNecessities;
    private CardView card_view_AppDetails;
    private CardView card_view_Rating;
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
    private NavigationView navigationView;
    private Toolbar toolbar;

    private String user_clicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {    //Called when the activity is starting.
        super.onCreate(savedInstanceState);
        //Set the activity content from a layout resource.
        setContentView(R.layout.activity_myinfo);

        //Defined variable
        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        imgBtnLanguage = findViewById(R.id.imgBtnLanguage);
        gridLayout=findViewById(R.id.gridInformativeLayout);
        card_view_Rating = findViewById(R.id.card_rating);
        card_view_Pathology = findViewById(R.id.card_pathology);
        card_view_AppDetails = findViewById(R.id.card_appDetails);
        card_view_RetrieveBasicNecessities = findViewById(R.id.card_retrieveBasicNecessities);

        //Set a Toolbar to act as the ActionBar for this Activity window.
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

        //Set a click listener on the cards objects
        card_view_Pathology.setOnClickListener(card_view_Pathology_listener);
        card_view_RetrieveBasicNecessities.setOnClickListener( card_view_RetrieveBasicNecessities_listener);
        card_view_AppDetails.setOnClickListener(card_view_AppDetais_listener);
        card_view_Rating.setOnClickListener(card_view_Rating_listener);
        //Set a click listener on the imageButton objects
        imgBtnLanguage.setOnClickListener(imgBtnLanguage_listener);

        //Set image resource
        imgBtnLanguage.setImageResource(R.drawable.italy);
        Configuration config = getBaseContext().getResources().getConfiguration();
        if (config.locale.getLanguage().equals("en")) {
            //Set image
            imgBtnLanguage.setImageResource(R.drawable.lang);
        }

        //Create new ActionBarDraweToggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //Adds the specified listener to the list of listeners that will be notified of drawer events.
        drawer.addDrawerListener(toggle);
        //Synchronize the indicator with the state of the linked DrawerLayout after onRestoreInstanceState has occurred.
        toggle.syncState();

        // Initialize FirebaseUser
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        // Get userId Logged
        user_clicked = user.getUid();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { //receive result from activity
        if (requestCode == 1) { // english
            if (resultCode == Activity.RESULT_CANCELED) {
                //Set image
                imgBtnLanguage.setImageResource(R.drawable.lang);
                //Create new Intent
                Intent refresh = new Intent(this, MyInfoActivity.class);
                startActivity(refresh);
                this.finish();
            }
        }
        if (requestCode == 2) { //italian
            if (resultCode == Activity.RESULT_CANCELED) {
                //Set image
                imgBtnLanguage.setImageResource(R.drawable.italy);
                //Create new Intent
                Intent refresh = new Intent(this, MyInfoActivity.class);
                startActivity(refresh);
                this.finish();
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {   //Called when an item in the navigation menu is selected.
        switch (item.getItemId()){
            case R.id.nav_home:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_homeIntent = new Intent (MyInfoActivity.this, HomepageActivity.class);
                startActivity(nav_homeIntent);
                break;
            case R.id.nav_info:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_infoIntent = new Intent (MyInfoActivity.this, InformativeActivity.class);
                startActivity(nav_infoIntent);
                break;
            case R.id.nav_medicalRecords:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_medicalRecordIntent = new Intent (MyInfoActivity.this, MedicalRecordsActivity.class);
                startActivity(nav_medicalRecordIntent);
                break;
            case R.id.nav_personalData:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_personalDataIntent= new Intent (MyInfoActivity.this, PersonalDataActivity.class);
                startActivity(nav_personalDataIntent);
                break;
            case R.id.nav_questionnaires:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_questionnairesIntent = new Intent (MyInfoActivity.this, QuestionnairesActivity.class);
                startActivity(nav_questionnairesIntent);
                break;
            case R.id.nav_logout:
                drawer.closeDrawer(GravityCompat.START);
                //Sign out function
                FirebaseAuth.getInstance().signOut();
                //Create new Intent
                Intent nav_logoutIntent= new Intent(MyInfoActivity.this, MainActivity.class);
                startActivity(nav_logoutIntent);
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {   //Called when the activity has detected the user's press of the back key.
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    //Set on click listener
    public View.OnClickListener card_view_Pathology_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //Create new Intent
            Intent readMedRecordIntent = new Intent (MyInfoActivity.this,ReadMedicalRecordsActivity.class);
            //Pass data between intents
            readMedRecordIntent.putExtra("user_clicked", user_clicked);
            readMedRecordIntent.putExtra("_parameter", "7");
            startActivity(readMedRecordIntent);

        }
    };
    public View.OnClickListener card_view_RetrieveBasicNecessities_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //Create new Intent
            Intent retrieveIntent = new Intent (MyInfoActivity.this,RetrieveBasicNecessitiesActivity.class);
            startActivity(retrieveIntent);

        }
    };

    public View.OnClickListener card_view_AppDetais_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //Create new Intent
            Intent appDetailIntent = new Intent (MyInfoActivity.this, AppDetailsActivity.class);
            startActivity(appDetailIntent);

        }
    };

    public View.OnClickListener card_view_Rating_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //Create new Intent
            Intent ratingIntent = new Intent (MyInfoActivity.this, RatingActivity.class);
            startActivity(ratingIntent);

        }
    };

    public View.OnClickListener imgBtnLanguage_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Create new Intent
            Intent languageIntent = new Intent (MyInfoActivity.this,PopUpLanguageActivity.class);
            //Pass data between intents
            languageIntent.putExtra("callingActivity", "it.uniba.di.sms.asilapp.MyInfoActivity");
            startActivity(languageIntent);
        }
    };
}
