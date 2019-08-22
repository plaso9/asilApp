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

public class InformativeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    GridLayout gridLayout;

    CardView card_view_Video,
            card_view_Acceptance,
            card_view_CityInfo,
            card_view_Bylaw,
            card_view_UsefulNumbers,
            card_view_MyInfo;

    private ImageButton imgBtnLanguage;
    private DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informative);

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
        card_view_Video = findViewById(R.id.card_video);
        card_view_Acceptance = findViewById(R.id.card_acceptance);
        card_view_CityInfo = findViewById(R.id.city_info);
        card_view_Bylaw = findViewById(R.id.card_bylaw);
        card_view_UsefulNumbers = findViewById(R.id.card_usefulNumbers);
        card_view_MyInfo = findViewById(R.id.card_myInfo);

        card_view_Video.setOnClickListener(card_view_Video_listener);
        card_view_Acceptance.setOnClickListener(card_view_Acceptance_listener);
        card_view_CityInfo.setOnClickListener(card_view_CityInfo_listener);
        card_view_Bylaw.setOnClickListener(card_view_Bylaw_listener);
        card_view_UsefulNumbers.setOnClickListener(card_view_UsefulNumbers_listener);
        card_view_MyInfo.setOnClickListener(card_view_MyInfo_listener);

        imgBtnLanguage = findViewById(R.id.imgBtnLanguage);
        imgBtnLanguage.setOnClickListener(imgBtnLanguage_listener);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) { // english

            if (resultCode == Activity.RESULT_CANCELED) {
                imgBtnLanguage.setImageResource(R.drawable.lang);
                Intent refresh = new Intent(this, InformativeActivity.class);
                startActivity(refresh);
                this.finish();
            }


        }
        if (requestCode == 2) { //italian

            if (resultCode == Activity.RESULT_CANCELED) {
                imgBtnLanguage.setImageResource(R.drawable.italy);
                Intent refresh = new Intent(this, InformativeActivity.class);
                startActivity(refresh);
                this.finish();
            }

        }
    }

    public View.OnClickListener imgBtnLanguage_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent languageIntent = new Intent (InformativeActivity.this,PopUpLanguageActivity.class);
            languageIntent.putExtra("callingActivity", "it.uniba.di.sms.asilapp.InformativeActivity");
            startActivity(languageIntent);
        }
    };


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent sens;
        switch (item.getItemId()){
            case R.id.nav_info:
                drawer.closeDrawer(GravityCompat.START);
                sens = new Intent (InformativeActivity.this, InformativeActivity.class);
                startActivity(sens);
                break;
            case R.id.nav_medicalRecords:
                drawer.closeDrawer(GravityCompat.START);
                sens = new Intent (InformativeActivity.this, MedicalRecordsActivity.class);
                startActivity(sens);
                break;
            case R.id.nav_personalData:
                drawer.closeDrawer(GravityCompat.START);
                sens = new Intent (InformativeActivity.this, PersonalDataActivity.class);
                startActivity(sens);
                break;
            case R.id.nav_questionnaires:
                drawer.closeDrawer(GravityCompat.START);
                sens = new Intent (InformativeActivity.this, QuestionnairesActivity.class);
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
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }



    public View.OnClickListener card_view_Video_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent sens = new Intent (InformativeActivity.this,VideoActivity.class);
            startActivity(sens);

        }
    };
    public View.OnClickListener card_view_Acceptance_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent sens = new Intent (InformativeActivity.this,AcceptanceActivity.class);
            startActivity(sens);

        }
    };

    public View.OnClickListener card_view_CityInfo_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent sens = new Intent (InformativeActivity.this,CityInfoActivity.class);
            startActivity(sens);

        }
    };

    public View.OnClickListener card_view_Bylaw_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent sens = new Intent (InformativeActivity.this,BylawActivity.class);
            startActivity(sens);

        }
    };

    public View.OnClickListener card_view_UsefulNumbers_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent sens = new Intent (InformativeActivity.this,UsefulNumbersActivity.class);
            startActivity(sens);

        }
    };

    public View.OnClickListener card_view_MyInfo_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent sens = new Intent (InformativeActivity.this,MyInfoActivity.class);
            startActivity(sens);

        }
    };


}

