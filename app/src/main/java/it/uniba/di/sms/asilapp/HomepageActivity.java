package it.uniba.di.sms.asilapp;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.app.ProgressDialog;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;

public class HomepageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    //Variable declaration
    CardView card_view_Informative;
    CardView card_view_PersonalData;
    CardView card_view_MedicalRecords;
    CardView card_view_Questionnaires;

    FloatingActionButton chatButton;
    GridLayout gridLayout;
    ImageButton imgBtnLanguage;

    MenuItem nav_info;
    MenuItem nav_logout;
    MenuItem nav_addUser;
    MenuItem nav_homeDoctor;
    MenuItem nav_kitOpening;
    MenuItem nav_readRatings;
    MenuItem nav_personalData;
    MenuItem nav_addAcceptance;
    MenuItem nav_searchPatient;
    MenuItem nav_medicalRecords;
    MenuItem nav_questionnaires;
    MenuItem nav_visitedPatient;
    MenuItem nav_addRetrieveNecessities;

    ProgressDialog progressBar;
    int PROGRESS_BAR_STATUS=0;

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set the activity content from a layout resource.
        setContentView(R.layout.activity_homepage);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // get menu from navigationView
        Menu menu = navigationView.getMenu();

        // find MenuItem you want to change
        nav_homeDoctor = menu.findItem(R.id.nav_homeDoctor);
        nav_addUser = menu.findItem(R.id.nav_add_user);
        nav_kitOpening = menu.findItem(R.id.nav_kit_opening);
        nav_readRatings = menu.findItem(R.id.nav_read_ratings);
        nav_searchPatient = menu.findItem(R.id.nav_search_patient);
        nav_visitedPatient = menu.findItem(R.id.nav_visited_patient);
        nav_addAcceptance = menu.findItem(R.id.nav_add_new_acceptance);
        nav_addRetrieveNecessities = menu.findItem(R.id.nav_add_retrive_necessities);

        //Set item visibility
        nav_homeDoctor.setVisible(false);
        nav_addUser.setVisible(false);
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
        //defined gridlayout variable
        gridLayout=findViewById(R.id.gridHomeLayout);
        //defined card variable
        card_view_PersonalData = findViewById(R.id.card_personalData);
        card_view_Informative = findViewById(R.id.card_informationSection);
        card_view_Questionnaires = findViewById(R.id.card_questionnaires);
        card_view_MedicalRecords = findViewById(R.id.card_medicalRecords);
        chatButton = findViewById(R.id.chatBtn);
        imgBtnLanguage = findViewById(R.id.imgBtnLanguage);

        //Set a click listener on the card objects
        card_view_Informative.setOnClickListener(card_view_Informative_listener);
        card_view_PersonalData.setOnClickListener(card_view_Personaldata_listener);
        card_view_Questionnaires.setOnClickListener(card_view_Questionnaries_listener);
        card_view_MedicalRecords.setOnClickListener(card_view_MedicalRecords_listener);
        //Set a click listener on the FloatingActionButton object
        chatButton.setOnClickListener(chatButton_listener);
        //Set a click listener on the button object
        imgBtnLanguage.setOnClickListener(imgBtnLanguage_listener);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {   //Called when an item in the navigation menu is selected.
        Intent sens;
        switch (item.getItemId()){
            case R.id.nav_home:
                drawer.closeDrawer(GravityCompat.START);
                Intent nav_homeIntent = new Intent (HomepageActivity.this, HomepageActivity.class);
                startActivity(nav_homeIntent);
                break;
            case R.id.nav_info:
                drawer.closeDrawer(GravityCompat.START);
                sens = new Intent (HomepageActivity.this, InformativeActivity.class);
                startActivity(sens);
                break;
            case R.id.nav_medicalRecords:
                drawer.closeDrawer(GravityCompat.START);
                sens = new Intent (HomepageActivity.this, MedicalRecordsActivity.class);
                startActivity(sens);
                break;
            case R.id.nav_personalData:
                drawer.closeDrawer(GravityCompat.START);
                sens = new Intent (HomepageActivity.this, PersonalDataActivity.class);
                startActivity(sens);
                break;
            case R.id.nav_questionnaires:
                drawer.closeDrawer(GravityCompat.START);
                sens = new Intent (HomepageActivity.this, QuestionnairesActivity.class);
                startActivity(sens);
                break;
            case R.id.nav_logout:
                drawer.closeDrawer(GravityCompat.START);
                FirebaseAuth.getInstance().signOut();
                sens = new Intent(HomepageActivity.this, MainActivity.class);
                startActivityForResult(sens, 1);
                finish();
                break;
        }
        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) { // english
            if (resultCode == Activity.RESULT_CANCELED) {
                imgBtnLanguage.setImageResource(R.drawable.lang);
                Intent refresh = new Intent(this, HomepageActivity.class);
                startActivity(refresh);
                this.finish();
            }
        }
        if (requestCode == 2) { //italian
            if (resultCode == Activity.RESULT_CANCELED) {
                imgBtnLanguage.setImageResource(R.drawable.italy);
                Intent refresh = new Intent(this, HomepageActivity.class);
                startActivity(refresh);
                this.finish();
            }
        }
    }
    public View.OnClickListener imgBtnLanguage_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent change_languageIntent = new Intent (HomepageActivity.this,PopUpLanguageActivity.class);
            change_languageIntent.putExtra("callingActivity", "it.uniba.di.sms.asilapp.HomepageActivity");
            startActivity(change_languageIntent);
        }
    };
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }
    public View.OnClickListener card_view_Informative_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent informativeIntent = new Intent (HomepageActivity.this,InformativeActivity.class);
            startActivity(informativeIntent);
        }
    };
    public View.OnClickListener card_view_Personaldata_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            progressBar = new ProgressDialog(HomepageActivity.this);
            progressBar.setIndeterminate(true);
            progressBar.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressBar.show();
            Intent sens = new Intent (HomepageActivity.this,PersonalDataActivity.class);
            startActivity(sens);
            PROGRESS_BAR_STATUS=1;
        }
    };
    public View.OnClickListener card_view_Questionnaries_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent questionnairesIntent = new Intent(HomepageActivity.this, QuestionnairesActivity.class);
            startActivity(questionnairesIntent);
        }
    };
    public  View.OnClickListener card_view_MedicalRecords_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent medicalRecordsIntent = new Intent(HomepageActivity.this, MedicalRecordsActivity.class);
            startActivity(medicalRecordsIntent);
        }
    };
    public View.OnClickListener chatButton_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent chatIntent = new Intent(HomepageActivity.this, ChatActivity.class);
            startActivity(chatIntent);
        }
    };
    @Override
    protected void onStart(){
        super.onStart();
        afterExecution();
    }

    //Method used to dismiss the progressBar
    public void afterExecution(){
        if (PROGRESS_BAR_STATUS == 1){
            progressBar.dismiss();
            PROGRESS_BAR_STATUS=0;//Everytime is called, the methods sets this variable to 0 to show the progressBar again
        }
    }
}