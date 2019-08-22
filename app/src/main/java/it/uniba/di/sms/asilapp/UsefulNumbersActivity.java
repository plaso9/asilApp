package it.uniba.di.sms.asilapp;

import android.app.Activity;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

public class UsefulNumbersActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    ImageView imageViewAmbulance,
    imageViewPolice,
    imageViewMilitaryPolice,
    imageViewFinanceGuard,
    imageViewSeaEmergency,
    imageViewFireBrigade;

    private DrawerLayout drawer;
    private ImageButton imgBtnLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usefulnumbers);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();



        imageViewAmbulance = findViewById(R.id.imageViewAmbulance);
        imageViewPolice = findViewById(R.id.imageViewPolice);
        imageViewMilitaryPolice = findViewById(R.id.imageViewMilitaryPolice);
        imageViewFinanceGuard = findViewById(R.id.imageViewFinanceGuard);
        imageViewSeaEmergency = findViewById(R.id.imageViewSeaEmergency);
        imageViewFireBrigade = findViewById(R.id.imageViewFireBrigade);

        imageViewAmbulance.setOnClickListener(imageViewAmbulance_listener);
        imageViewPolice.setOnClickListener(imageViewPolice_listener);
        imageViewMilitaryPolice.setOnClickListener(imageViewMilitaryPolice_listener);
        imageViewFinanceGuard.setOnClickListener(imageViewFinanceGuard_listener);
        imageViewSeaEmergency.setOnClickListener(imageViewSeaEmergency_listener);
        imageViewFireBrigade.setOnClickListener(imageViewFireBrigade_listener);

        imgBtnLanguage = findViewById(R.id.imgBtnLanguage);
        imgBtnLanguage.setOnClickListener(imgBtnLanguage_listener);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) { // english

            if (resultCode == Activity.RESULT_CANCELED) {
                imgBtnLanguage.setImageResource(R.drawable.lang);
                Intent refresh = new Intent(this, UsefulNumbersActivity.class);
                startActivity(refresh);
                this.finish();
            }


        }
        if (requestCode == 2) { //italian

            if (resultCode == Activity.RESULT_CANCELED) {
                imgBtnLanguage.setImageResource(R.drawable.italy);
                Intent refresh = new Intent(this, UsefulNumbersActivity.class);
                startActivity(refresh);
                this.finish();
            }


        }
    }

    public View.OnClickListener imgBtnLanguage_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent languageIntent = new Intent (UsefulNumbersActivity.this,PopUpLanguageActivity.class);
            languageIntent.putExtra("callingActivity", "it.uniba.di.sms.asilapp.UsefulNumbersActivity");
            startActivity(languageIntent);
        }
    };


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent sens;
        switch (item.getItemId()){
            case R.id.nav_info:
                drawer.closeDrawer(GravityCompat.START);
                sens = new Intent (UsefulNumbersActivity.this, InformativeActivity.class);
                startActivity(sens);
                break;
            case R.id.nav_medicalRecords:
                drawer.closeDrawer(GravityCompat.START);
                sens = new Intent (UsefulNumbersActivity.this, MedicalRecordsActivity.class);
                startActivity(sens);
                break;
            case R.id.nav_personalData:
                drawer.closeDrawer(GravityCompat.START);
                sens = new Intent (UsefulNumbersActivity.this, PersonalDataActivity.class);
                startActivity(sens);
                break;
            case R.id.nav_questionnaires:
                drawer.closeDrawer(GravityCompat.START);
                sens = new Intent (UsefulNumbersActivity.this, QuestionnairesActivity.class);
                startActivity(sens);
                break;
            case R.id.nav_logout:
                drawer.closeDrawer(GravityCompat.START);
                FirebaseAuth.getInstance().signOut();
                sens = new Intent(UsefulNumbersActivity.this, MainActivity.class);
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

    public View.OnClickListener imageViewAmbulance_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:118"));
            startActivity(intent);
        }
    };
    public View.OnClickListener imageViewPolice_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:113"));
            startActivity(intent);
        }
    };
    public View.OnClickListener imageViewMilitaryPolice_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:112"));
            startActivity(intent);
        }
    };
    public View.OnClickListener imageViewFinanceGuard_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:117"));
            startActivity(intent);
        }
    };
    public View.OnClickListener imageViewSeaEmergency_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:1530"));
            startActivity(intent);
        }
    };
    public View.OnClickListener imageViewFireBrigade_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:115"));
            startActivity(intent);
        }
    };


}
