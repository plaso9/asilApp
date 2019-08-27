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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import it.uniba.di.sms.asilapp.models.MedicalRecord;
import it.uniba.di.sms.asilapp.models.User;

public class MedicalRecordsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static final String TAG = "MedicalRecordsActivity";
    ImageView image_seeTemperatureStats;
    ImageView image_seeBloodPressureStats;
    ImageView image_seeGlycemiaStats;
    ImageView image_seeHeartbeatStats;
    ImageView image_seeECGStats;
    ImageView image_seeSymptomsStats;
    ImageView image_seePathologyStats;
    Button button_addTemperature;
    Button button_addBloodPressure;
    Button button_addGlycemia;
    Button button_addHeartbeat;
    Button button_addECG;
    Button button_addSymptoms;
    Button button_addPathology;
    private DatabaseReference mUserReference;
    private String uId;
    private String userClickedId;
    private int mRole;
    private ImageButton imgBtnLanguage;

    private DrawerLayout drawer;

    private MenuItem nav_home;
    private MenuItem nav_info;
    private MenuItem nav_video;
    private MenuItem nav_addUser;
    private MenuItem nav_homeAdmin;
    private MenuItem nav_homeDoctor;
    private MenuItem nav_kitOpening;
    private MenuItem nav_readRatings;
    private MenuItem nav_personalData;
    private MenuItem nav_addAcceptance;
    private MenuItem nav_searchPatient;
    private MenuItem nav_medicalRecords;
    private MenuItem nav_questionnaires;
    private MenuItem nav_visitedPatient;
    private MenuItem nav_addRetrieveNecessities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_records);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        //Get user role to hide some menu item
        getUserRole();


        image_seeTemperatureStats = findViewById(R.id.image_seeTemperatureStats);
        image_seeBloodPressureStats = findViewById(R.id.image_seeBloodPressureStats);
        image_seeGlycemiaStats = findViewById(R.id.image_seeGlycemiaStats);
        image_seeHeartbeatStats = findViewById(R.id.image_seeHeartbeatStats);
        image_seeECGStats = findViewById(R.id.image_seeECGStats);
        image_seeSymptomsStats = findViewById(R.id.image_seeSymptomsStats);
        image_seePathologyStats = findViewById(R.id.image_SeePathologyStats);

        button_addTemperature = findViewById(R.id.buttonAddTemperature);
        button_addBloodPressure = findViewById(R.id.buttonAddBloodPressure);
        button_addGlycemia = findViewById(R.id.buttonAddGlycemia);
        button_addHeartbeat = findViewById(R.id.buttonAddHeartbeat);
        button_addECG = findViewById(R.id.buttonAddECG);
        button_addSymptoms = findViewById(R.id.buttonAddSymptoms);
        button_addPathology = findViewById(R.id.buttonAddPathology);

        image_seeTemperatureStats.setOnClickListener(image_seeTemperatureStats_listener);
        image_seeBloodPressureStats.setOnClickListener(image_seeBloodPressureStats_listener);
        image_seeGlycemiaStats.setOnClickListener(image_seeGlycemiaStats_listener);
        image_seeHeartbeatStats.setOnClickListener(image_seeHeartbeatStats_listener);
        image_seeECGStats.setOnClickListener(image_seeECGStats_listener);
        image_seeSymptomsStats.setOnClickListener(image_seeSymptomsStats_listener);
        image_seePathologyStats.setOnClickListener(image_seePathologyStats_listener);

        button_addTemperature.setOnClickListener(button_addTemperature_listener);
        button_addBloodPressure.setOnClickListener(button_addBloodPressure_listener);
        button_addGlycemia.setOnClickListener(button_addGlycemia_listener);
        button_addHeartbeat.setOnClickListener(button_addHeartbeat_listener);
        button_addECG.setOnClickListener(button_addECG_listener);
        button_addSymptoms.setOnClickListener(button_addSymptoms_listener);
        button_addPathology.setOnClickListener(button_addPathology_listener);

        imgBtnLanguage = findViewById(R.id.imgBtnLanguage);

        imgBtnLanguage.setImageResource(R.drawable.italy);
        Configuration config = getBaseContext().getResources().getConfiguration();
        if (config.locale.getLanguage().equals("en")) {
            imgBtnLanguage.setImageResource(R.drawable.lang);
        }

        imgBtnLanguage.setOnClickListener(imgBtnLanguage_listener);

        // Initialize FirebaseUser
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //Initialize variable to get extra value from other intent
        if (getIntent().getExtras() != null) {
            userClickedId = getIntent().getExtras().getString("user_clicked");
        }
        // Get userId Logged
        uId = user.getUid();

        // Initialize Database Reference
        mUserReference = FirebaseDatabase.getInstance().getReference()
                .child("user").child(uId);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_homeDoctor:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_homeDoctorIntent = new Intent (MedicalRecordsActivity.this, DoctorActivity.class);
                startActivity(nav_homeDoctorIntent);
                break;
            case R.id.nav_home:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_homeIntent = new Intent (MedicalRecordsActivity.this, HomepageActivity.class);
                startActivity(nav_homeIntent);
                break;
            case R.id.nav_info:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_infoIntent = new Intent (MedicalRecordsActivity.this, InformativeActivity.class);
                startActivity(nav_infoIntent);
                break;
            case R.id.nav_medicalRecords:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_medicalRecordsIntent = new Intent (MedicalRecordsActivity.this, MedicalRecordsActivity.class);
                startActivity(nav_medicalRecordsIntent);
                break;
            case R.id.nav_personalData:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_personalDataIntent  = new Intent (MedicalRecordsActivity.this, PersonalDataActivity.class);
                startActivity(nav_personalDataIntent);
                break;
            case R.id.nav_questionnaires:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_questionnairesIntent = new Intent (MedicalRecordsActivity.this, QuestionnairesActivity.class);
                startActivity(nav_questionnairesIntent);
                break;
            case R.id.nav_search_patient:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_searchPatientIntent = new Intent (MedicalRecordsActivity.this, SearchPatientActivity.class);
                startActivity(nav_searchPatientIntent);
                break;
            case R.id.nav_kit_opening:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_kitOpeningIntent = new Intent (MedicalRecordsActivity.this, KitOpeningActivity.class);
                startActivity(nav_kitOpeningIntent);
                break;
            case R.id.nav_visited_patient:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_visitedPatientIntent = new Intent (MedicalRecordsActivity.this, PatientListActivity.class);
                startActivity(nav_visitedPatientIntent);
                break;
            case R.id.nav_video:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_videoIntent = new Intent (MedicalRecordsActivity.this, VideoActivity.class);
                startActivity(nav_videoIntent);
                break;
            case R.id.nav_logout:
                drawer.closeDrawer(GravityCompat.START);
                //Sign out function
                FirebaseAuth.getInstance().signOut();
                //Create new Intent
                Intent nav_logoutIntent = new Intent(MedicalRecordsActivity.this, MainActivity.class);
                startActivity(nav_logoutIntent);
                finish();
                break;
        }
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) { // english

            if (resultCode == Activity.RESULT_CANCELED) {
                Intent refresh = new Intent(this, MedicalRecordsActivity.class);
                startActivity(refresh);
                this.finish();
            }


        }
        if (requestCode == 2) { //italian

            if (resultCode == Activity.RESULT_CANCELED) {
                Intent refresh = new Intent(this, MedicalRecordsActivity.class);
                startActivity(refresh);
                this.finish();
            }


        }
    }

    public View.OnClickListener imgBtnLanguage_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent languageIntent = new Intent (MedicalRecordsActivity.this,PopUpLanguageActivity.class);
            languageIntent.putExtra("callingActivity", "it.uniba.di.sms.asilapp.MedicalRecordsActivity");
            startActivity(languageIntent);
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


    @Override
    protected void onStart() {
        super.onStart();
        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get User object and use the values to update the UI
                User user = dataSnapshot.getValue(User.class);
                mRole = user.getRole();

                if (mRole != 3){
                    button_addTemperature.setVisibility(View.INVISIBLE);
                    button_addBloodPressure.setVisibility(View.INVISIBLE);
                    button_addGlycemia.setVisibility(View.INVISIBLE);
                    button_addHeartbeat.setVisibility(View.INVISIBLE);
                    button_addECG.setVisibility(View.INVISIBLE);
                    button_addSymptoms.setVisibility(View.INVISIBLE);
                    button_addPathology.setVisibility(View.INVISIBLE);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting User failed, log a message
                Log.w(TAG, "loadUser:onCancelled", databaseError.toException());
                Toast.makeText(MedicalRecordsActivity.this, "Failed to load user.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        mUserReference.addValueEventListener(userListener);
    }

    //On Click Listener to add medical records
    public View.OnClickListener button_addTemperature_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent add_temperatureIntent = new Intent(MedicalRecordsActivity.this, PopUpTemperatureActivity.class);
            add_temperatureIntent.putExtra("user_clicked", userClickedId);
            add_temperatureIntent.putExtra("_parameter", "1");
            startActivity(add_temperatureIntent);
        }
    };
    public View.OnClickListener button_addBloodPressure_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent add_bloodPressureIntent = new Intent(MedicalRecordsActivity.this, PopUpTemperatureActivity.class);
            add_bloodPressureIntent.putExtra("user_clicked", userClickedId);
            add_bloodPressureIntent.putExtra("_parameter", "2");
            startActivity(add_bloodPressureIntent);
        }
    };
    public View.OnClickListener button_addGlycemia_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent add_glycemiaIntent = new Intent(MedicalRecordsActivity.this, PopUpTemperatureActivity.class);
            add_glycemiaIntent.putExtra("user_clicked", userClickedId);
            add_glycemiaIntent.putExtra("_parameter", "3");
            startActivity(add_glycemiaIntent);
        }
    };
    public View.OnClickListener button_addHeartbeat_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent add_heartbeatIntent = new Intent(MedicalRecordsActivity.this, PopUpTemperatureActivity.class);
            add_heartbeatIntent.putExtra("user_clicked", userClickedId);
            add_heartbeatIntent.putExtra("_parameter", "4");
            startActivity(add_heartbeatIntent);
        }
    };
    public View.OnClickListener button_addECG_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent add_ecgIntent = new Intent(MedicalRecordsActivity.this, PopUpTemperatureActivity.class);
            add_ecgIntent.putExtra("user_clicked", userClickedId);
            add_ecgIntent.putExtra("_parameter", "5");
            startActivity(add_ecgIntent);
        }
    };
    public View.OnClickListener button_addSymptoms_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent add_symptomsIntent = new Intent(MedicalRecordsActivity.this, PopUpTemperatureActivity.class);
            add_symptomsIntent.putExtra("user_clicked", userClickedId);
            add_symptomsIntent.putExtra("_parameter", "6");
            startActivity(add_symptomsIntent);
        }
    };
    public View.OnClickListener button_addPathology_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent add_pathologyIntent = new Intent(MedicalRecordsActivity.this, AddPathologyActivity.class);
            add_pathologyIntent.putExtra("user_clicked", userClickedId);
            add_pathologyIntent.putExtra("_parameter", "7");
            startActivity(add_pathologyIntent);
        }
    };

    //On Click Listener to see medical records
    public View.OnClickListener image_seeTemperatureStats_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent see_temperatureIntent = new Intent(MedicalRecordsActivity.this, ReadMedicalRecordsActivity.class);
            see_temperatureIntent.putExtra("user_clicked", userClickedId);
            see_temperatureIntent.putExtra("_parameter", "1");
            startActivity(see_temperatureIntent);
        }
    };
    public View.OnClickListener image_seeBloodPressureStats_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent see_bloodPressureIntent = new Intent(MedicalRecordsActivity.this, ReadMedicalRecordsActivity.class);
            see_bloodPressureIntent.putExtra("user_clicked", userClickedId);
            see_bloodPressureIntent.putExtra("_parameter", "2");
            startActivity(see_bloodPressureIntent);
        }
    };
    public View.OnClickListener image_seeGlycemiaStats_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent see_glycemiaIntent = new Intent(MedicalRecordsActivity.this, ReadMedicalRecordsActivity.class);
            see_glycemiaIntent.putExtra("user_clicked", userClickedId);
            see_glycemiaIntent.putExtra("_parameter", "3");
            startActivity(see_glycemiaIntent);
        }
    };
    public View.OnClickListener image_seeHeartbeatStats_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent see_heartbeatIntent = new Intent(MedicalRecordsActivity.this, ReadMedicalRecordsActivity.class);
            see_heartbeatIntent.putExtra("user_clicked", userClickedId);
            see_heartbeatIntent.putExtra("_parameter", "4");
            startActivity(see_heartbeatIntent);
        }
    };
    public View.OnClickListener image_seeECGStats_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent see_ecgIntent = new Intent(MedicalRecordsActivity.this, ReadMedicalRecordsActivity.class);
            see_ecgIntent.putExtra("user_clicked", userClickedId);
            see_ecgIntent.putExtra("_parameter", "5");
            startActivity(see_ecgIntent);
        }
    };
    public View.OnClickListener image_seeSymptomsStats_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent see_symptomsIntent = new Intent(MedicalRecordsActivity.this, ReadMedicalRecordsActivity.class);
            see_symptomsIntent.putExtra("user_clicked", userClickedId);
            see_symptomsIntent.putExtra("_parameter", "6");
            startActivity(see_symptomsIntent);
        }
    };
    public View.OnClickListener image_seePathologyStats_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent see_pathologyIntent = new Intent(MedicalRecordsActivity.this, ReadMedicalRecordsActivity.class);
            see_pathologyIntent.putExtra("user_clicked", userClickedId);
            see_pathologyIntent.putExtra("_parameter", "7");
            startActivity(see_pathologyIntent);
        }
    };


    public void removeItemDoctor(){
        NavigationView navigationView = findViewById(R.id.nav_view);
        // get menu from navigationView
        Menu menu = navigationView.getMenu();
        // find MenuItem you want to change
        nav_video = menu.findItem(R.id.nav_video);
        nav_homeDoctor = menu.findItem(R.id.nav_homeDoctor);
        nav_kitOpening = menu.findItem(R.id.nav_kit_opening);
        nav_searchPatient = menu.findItem(R.id.nav_search_patient);
        nav_visitedPatient = menu.findItem(R.id.nav_visited_patient);
        //Set item visibility
        nav_video.setVisible(false);
        nav_homeDoctor.setVisible(false);
        nav_kitOpening.setVisible(false);
        nav_searchPatient.setVisible(false);
        nav_visitedPatient.setVisible(false);
    }

    public void removeItemUser(){
        NavigationView navigationView = findViewById(R.id.nav_view);
        // get menu from navigationView
        Menu menu = navigationView.getMenu();
        // find MenuItem you want to change
        nav_home = menu.findItem(R.id.nav_home);
        nav_info = menu.findItem(R.id.nav_info);
        nav_personalData = menu.findItem(R.id.nav_personalData);
        nav_medicalRecords = menu.findItem(R.id.nav_medicalRecords);
        nav_questionnaires = menu.findItem(R.id.nav_questionnaires);
        //Set item visibility
        nav_home.setVisible(false);
        nav_info.setVisible(false);
        nav_personalData.setVisible(false);
        nav_medicalRecords.setVisible(false);
        nav_questionnaires.setVisible(false);
    }

    public void removeItemAdmin(){
        NavigationView navigationView = findViewById(R.id.nav_view);
        // get menu from navigationView
        Menu menu = navigationView.getMenu();
        // find MenuItem you want to change
        nav_addUser = menu.findItem(R.id.nav_add_user);
        nav_homeAdmin = menu.findItem(R.id.nav_homeAdmin);
        nav_readRatings = menu.findItem(R.id.nav_read_ratings);
        nav_addAcceptance = menu.findItem(R.id.nav_add_new_acceptance);
        nav_addRetrieveNecessities = menu.findItem(R.id.nav_add_retrive_necessities);
        //Set item visibility
        nav_addUser.setVisible(false);
        nav_homeAdmin.setVisible(false);
        nav_readRatings.setVisible(false);
        nav_addAcceptance.setVisible(false);
        nav_addRetrieveNecessities.setVisible(false);
    }

    public void getUserRole(){
        // Initialize FirebaseUser
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        mUserReference = FirebaseDatabase.getInstance().getReference("user").child(user.getUid());

        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get User object and use the values to update the UI
                User user = dataSnapshot.getValue(User.class);
                int role = user.getRole();
                if (role == 2) {    //role 2 = User
                    removeItemAdmin();
                    removeItemDoctor();
                } else if (role == 3) { //role 3 = Doctor
                    removeItemAdmin();
                    removeItemUser();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting User failed, log a message
                Log.w(TAG, "loadUser:onCancelled", databaseError.toException());
                Toast.makeText(MedicalRecordsActivity.this, "Failed to load user.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        mUserReference.addListenerForSingleValueEvent(userListener);
    }
}
