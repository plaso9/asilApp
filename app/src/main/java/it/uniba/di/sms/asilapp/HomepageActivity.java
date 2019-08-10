package it.uniba.di.sms.asilapp;

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
import android.app.ProgressDialog;


public class HomepageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    GridLayout gridLayout;
    CardView card_view_PersonalData;
    CardView card_view_Informative;
    CardView card_view_Questionnaires;
    CardView card_view_MedicalRecords;

    int REQUEST_CODE=0;
    ProgressDialog progressBar;


    private DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

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
        gridLayout=findViewById(R.id.gridHomeLayout);
        //defined card variable
        card_view_PersonalData = findViewById(R.id.card_personalData);
        card_view_Informative = findViewById(R.id.card_informationSection);
        card_view_Questionnaires = findViewById(R.id.card_questionnaires);
        card_view_MedicalRecords = findViewById(R.id.card_medicalRecords);



        //set function to card
        card_view_Informative.setOnClickListener(card_view_Informative_listener);
        card_view_PersonalData.setOnClickListener(card_view_Personaldata_listener);
        card_view_Questionnaires.setOnClickListener(card_view_Questionnaries_listener);
        card_view_MedicalRecords.setOnClickListener(card_view_MedicalRecords_listener);


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent sens;
        switch (item.getItemId()){
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



    public View.OnClickListener card_view_Informative_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent sens = new Intent (HomepageActivity.this,InformativeActivity.class);
            startActivity(sens);

        }
    };

    public View.OnClickListener card_view_Personaldata_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent sens = new Intent (HomepageActivity.this,PersonalDataActivity.class);
            startActivity(sens);

        }
    };

    public View.OnClickListener card_view_Questionnaries_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(HomepageActivity.this, QuestionnairesActivity.class);
            startActivity(i);
        }
    };

    public  View.OnClickListener card_view_MedicalRecords_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(HomepageActivity.this, MedicalRecordsActivity.class);
            startActivity(intent);
        }
    };


    @Override
    protected void onStart(){
        super.onStart();
        afterExecution();
    }


   public void afterExecution(){
       if (REQUEST_CODE == 1){
           progressBar.dismiss();
           REQUEST_CODE=0;
       }
   }

}
