package it.uniba.di.sms.asilapp;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MyInfoActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    GridLayout gridLayout;
    CardView card_view_Pathology,
            card_view_RetrieveBasicNecessities,
            card_view_AppDetails,
            card_view_Rating;

    private ImageButton imgBtnLanguage;
    private DrawerLayout drawer;

    String user_clicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();




        //defined gridlayout variable
        gridLayout=findViewById(R.id.gridInformativeLayout);

        //defined card variable
        card_view_Pathology = findViewById(R.id.card_pathology);
        card_view_RetrieveBasicNecessities = findViewById(R.id.card_retrieveBasicNecessities);
        card_view_AppDetails = findViewById(R.id.card_appDetails);
        card_view_Rating = findViewById(R.id.card_rating);

        card_view_Pathology.setOnClickListener(card_view_Pathology_listener);
        card_view_RetrieveBasicNecessities.setOnClickListener( card_view_RetrieveBasicNecessities_listener);
        card_view_AppDetails.setOnClickListener(card_view_AppDetais_listener);
        card_view_Rating.setOnClickListener(card_view_Rating_listener);

        // Initialize FirebaseUser
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        // Get userId Logged
        user_clicked = user.getUid();
        imgBtnLanguage = findViewById(R.id.imgBtnLanguage);
        imgBtnLanguage.setOnClickListener(imgBtnLanguage_listener);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) { // english

            if (resultCode == Activity.RESULT_CANCELED) {
                imgBtnLanguage.setImageResource(R.drawable.lang);
                Intent refresh = new Intent(this, MyInfoActivity.class);
                startActivity(refresh);
                this.finish();
            }


        }
        if (requestCode == 2) { //italian

            if (resultCode == Activity.RESULT_CANCELED) {
                imgBtnLanguage.setImageResource(R.drawable.italy);
                Intent refresh = new Intent(this, MyInfoActivity.class);
                startActivity(refresh);
                this.finish();
            }

        }
    }

    public View.OnClickListener imgBtnLanguage_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent languageIntent = new Intent (MyInfoActivity.this,PopUpLanguageActivity.class);
            languageIntent.putExtra("callingActivity", "it.uniba.di.sms.asilapp.MyInfoActivity");
            startActivity(languageIntent);
        }
    };
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent sens;
        switch (item.getItemId()){
            case R.id.nav_info:
                drawer.closeDrawer(GravityCompat.START);
                sens = new Intent (MyInfoActivity.this, InformativeActivity.class);
                startActivity(sens);
                break;
            case R.id.nav_medicalRecords:
                drawer.closeDrawer(GravityCompat.START);
                sens = new Intent (MyInfoActivity.this, MedicalRecordsActivity.class);
                startActivity(sens);
                break;
            case R.id.nav_personalData:
                drawer.closeDrawer(GravityCompat.START);
                sens = new Intent (MyInfoActivity.this, PersonalDataActivity.class);
                startActivity(sens);
                break;
            case R.id.nav_questionnaires:
                drawer.closeDrawer(GravityCompat.START);
                sens = new Intent (MyInfoActivity.this, QuestionnairesActivity.class);
                startActivity(sens);
                break;
            case R.id.nav_logout:
                drawer.closeDrawer(GravityCompat.START);
                FirebaseAuth.getInstance().signOut();
                sens = new Intent(MyInfoActivity.this, MainActivity.class);
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




    public View.OnClickListener card_view_Pathology_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent sens = new Intent (MyInfoActivity.this,ReadMedicalRecordsActivity.class);
            sens.putExtra("user_clicked", user_clicked);
            sens.putExtra("_parameter", "7");
            startActivity(sens);

        }
    };
    public View.OnClickListener card_view_RetrieveBasicNecessities_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent sens = new Intent (MyInfoActivity.this,RetrieveBasicNecessitiesActivity.class);
            startActivity(sens);

        }
    };

    public View.OnClickListener card_view_AppDetais_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent sens = new Intent (MyInfoActivity.this, AppDetailsActivity.class);
            startActivity(sens);

        }
    };

    public View.OnClickListener card_view_Rating_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent sens = new Intent (MyInfoActivity.this, RatingActivity.class);
            startActivity(sens);

        }
    };
}
