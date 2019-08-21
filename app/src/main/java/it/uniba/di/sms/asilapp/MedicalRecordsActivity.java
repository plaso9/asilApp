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
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
        Intent sens;
        switch (item.getItemId()){
            case R.id.nav_info:
                drawer.closeDrawer(GravityCompat.START);
                sens = new Intent (MedicalRecordsActivity.this, InformativeActivity.class);
                startActivity(sens);
                break;
            case R.id.nav_medicalRecords:
                drawer.closeDrawer(GravityCompat.START);
                sens = new Intent (MedicalRecordsActivity.this, MedicalRecordsActivity.class);
                startActivity(sens);
                break;
            case R.id.nav_personalData:
                drawer.closeDrawer(GravityCompat.START);
                sens = new Intent (MedicalRecordsActivity.this, PersonalDataActivity.class);
                startActivity(sens);
                break;
            case R.id.nav_questionnaires:
                drawer.closeDrawer(GravityCompat.START);
                sens = new Intent (MedicalRecordsActivity.this, QuestionnairesActivity.class);
                startActivity(sens);
                break;
            case R.id.nav_logout:
                drawer.closeDrawer(GravityCompat.START);
                FirebaseAuth.getInstance().signOut();
                sens = new Intent(MedicalRecordsActivity.this, MainActivity.class);
                startActivity(sens);
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
                Intent refresh = new Intent(this, MedicalRecordsActivity.class);
                startActivity(refresh);
                this.finish();
            }


        }
        if (requestCode == 2) { //italian

            if (resultCode == Activity.RESULT_CANCELED) {
                imgBtnLanguage.setImageResource(R.drawable.italy);
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
            Intent add_symptomsIntent = new Intent(MedicalRecordsActivity.this, SymptomsActivity.class);
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
}
