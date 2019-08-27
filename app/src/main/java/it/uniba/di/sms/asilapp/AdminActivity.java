package it.uniba.di.sms.asilapp;

import android.app.Activity;
import android.content.Intent;
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

public class AdminActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    //Variables declaration
    private ImageButton imgBtnLanguage;
    CardView card_view_addFood;
    CardView card_view_addUser;
    CardView card_view_readRatings;
    CardView card_view_addAcceptance;
    FloatingActionButton chatButton;
    GridLayout gridLayout;
    private DrawerLayout drawer;

    MenuItem nav_home;
    MenuItem nav_info;
    MenuItem nav_video;
    MenuItem nav_homeDoctor;
    MenuItem nav_kitOpening;
    MenuItem nav_personalData;
    MenuItem nav_searchPatient;
    MenuItem nav_medicalRecords;
    MenuItem nav_questionnaires;
    MenuItem nav_visitedPatient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {    //Called when the activity is starting.
        super.onCreate(savedInstanceState);
        //Set the activity content from a layout resource.
        setContentView(R.layout.activity_admin);

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
        nav_video = menu.findItem(R.id.nav_video);
        nav_homeDoctor = menu.findItem(R.id.nav_homeDoctor);
        nav_kitOpening = menu.findItem(R.id.nav_kit_opening);
        nav_personalData = menu.findItem(R.id.nav_personalData);
        nav_searchPatient = menu.findItem(R.id.nav_search_patient);
        nav_medicalRecords = menu.findItem(R.id.nav_medicalRecords);
        nav_questionnaires = menu.findItem(R.id.nav_questionnaires);
        nav_visitedPatient = menu.findItem(R.id.nav_visited_patient);


        //Set item visibility
        nav_home.setVisible(false);
        nav_info.setVisible(false);
        nav_video.setVisible(false);
        nav_homeDoctor.setVisible(false);
        nav_kitOpening.setVisible(false);
        nav_personalData.setVisible(false);
        nav_searchPatient.setVisible(false);
        nav_medicalRecords.setVisible(false);
        nav_questionnaires.setVisible(false);
        nav_visitedPatient.setVisible(false);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //Defined variable
        card_view_addFood = findViewById(R.id.card_addFood);
        card_view_addUser = findViewById(R.id.card_addUser);
        card_view_readRatings = findViewById(R.id.card_readRatings);
        card_view_addAcceptance = findViewById(R.id.card_addAcceptance);
        chatButton = findViewById(R.id.chatBtn);
        gridLayout=findViewById(R.id.gridAdminLayout);
        imgBtnLanguage = findViewById(R.id.imgBtnLanguage);

        imgBtnLanguage.setImageResource(R.drawable.italy);
        Configuration config = getBaseContext().getResources().getConfiguration();
        if (config.locale.getLanguage().equals("en")) {
            imgBtnLanguage.setImageResource(R.drawable.lang);
        }

        imgBtnLanguage.setOnClickListener(imgBtnLanguage_listener);
        //Set a click listener on the card objects
        card_view_addAcceptance.setOnClickListener(card_view_addAcceptance_listener);
        card_view_addUser.setOnClickListener(card_view_addUser_listener);
        card_view_addFood.setOnClickListener(card_view_addFood_listener);
        card_view_readRatings.setOnClickListener(card_view_readRatings_listener);
        //Set a click listener on the FloatingActionButton object
        chatButton.setOnClickListener(chatButton_listener);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

            if (resultCode == Activity.RESULT_CANCELED) {
                Intent refresh = new Intent(this, AdminActivity.class);
                startActivity(refresh);
                this.finish();

        }
    }

    public View.OnClickListener imgBtnLanguage_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent change_languageIntent = new Intent (AdminActivity.this,PopUpLanguageActivity.class);
            change_languageIntent.putExtra("callingActivity", "it.uniba.di.sms.asilapp.AdminActivity");
            startActivity(change_languageIntent);
        }
    };


    //Interface definition for a callback to be invoked when a view is clicked.
    public View.OnClickListener card_view_addAcceptance_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent add_acceptanceIntent = new Intent (AdminActivity.this, AddAcceptanceActivity.class);
            startActivity(add_acceptanceIntent);
        }
    };

    public View.OnClickListener card_view_addUser_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent add_userIntent = new Intent (AdminActivity.this,AddUserActivity.class);
            startActivity(add_userIntent);

        }
    };

    public View.OnClickListener card_view_addFood_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent add_foodIntent = new Intent(AdminActivity.this, AddFoodActivity.class);
            startActivity(add_foodIntent);
        }
    };

    public  View.OnClickListener card_view_readRatings_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent read_ratingsIntent = new Intent(AdminActivity.this, ReadRatingsActivity.class);
            startActivity(read_ratingsIntent);
        }
    };

    public View.OnClickListener chatButton_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent chatIntent = new Intent(AdminActivity.this, ChatActivity.class);
            startActivity(chatIntent);
        }
    };


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_homeAdmin:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_homeAdminIntent = new Intent (AdminActivity.this, AdminActivity.class);
                startActivity(nav_homeAdminIntent);
                break;
            case R.id.nav_add_user:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_addUserIntent = new Intent (AdminActivity.this, AddUserActivity.class);
                startActivity(nav_addUserIntent);
                break;
            case R.id.nav_add_new_acceptance:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_addAcceptanceIntent = new Intent (AdminActivity.this, AddAcceptanceActivity.class);
                startActivity(nav_addAcceptanceIntent);
                break;
            case R.id.nav_add_retrive_necessities:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_addFoodIntent = new Intent (AdminActivity.this, AddFoodActivity.class);
                startActivity(nav_addFoodIntent);
                break;
            case R.id.nav_read_ratings:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_readRatingIntent = new Intent (AdminActivity.this, ReadRatingsActivity.class);
                startActivity(nav_readRatingIntent);
                break;
            case R.id.nav_logout:
                drawer.closeDrawer(GravityCompat.START);
                //Sign out function
                FirebaseAuth.getInstance().signOut();
                //Create new Intent
                Intent nav_logoutIntent = new Intent(AdminActivity.this, MainActivity.class);
                startActivity(nav_logoutIntent);
                finish();
                break;
        }
        return true;
    }
}
