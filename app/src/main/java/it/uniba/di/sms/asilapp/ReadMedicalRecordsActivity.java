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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import it.uniba.di.sms.asilapp.adapter.MedicalRecordAdapter;
import it.uniba.di.sms.asilapp.models.MedicalRecord;
import it.uniba.di.sms.asilapp.models.User;

public class ReadMedicalRecordsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "ReadMedRecordsActivity";
    private DatabaseReference mUserReference;
    private String uId;
    private String userClickedId;
    private String parameter;

    private RecyclerView recyclerView;
    private DatabaseReference mMedicalRecordReference;
    private ImageButton imgBtnLanguage;

    private MedicalRecordAdapter medicalRecordAdapter;
    private List<MedicalRecord> mMedicalRecordsList;

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
        setContentView(R.layout.activity_read_medical_records);

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


        recyclerView = findViewById(R.id.medicalRecordsList);

        imgBtnLanguage = findViewById(R.id.imgBtnLanguage);

        imgBtnLanguage.setImageResource(R.drawable.italy);
        Configuration config = getBaseContext().getResources().getConfiguration();
        if (config.locale.getLanguage().equals("en")) {
            imgBtnLanguage.setImageResource(R.drawable.lang);
        }

        imgBtnLanguage.setOnClickListener(imgBtnLanguage_listener);
        //Used to know size top avoid expensive layout operation
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) { // english
            if (resultCode == Activity.RESULT_CANCELED) {
                Intent refresh = new Intent(this, ReadMedicalRecordsActivity.class);
                startActivity(refresh);
                this.finish();
            }
        }
        if (requestCode == 2) { //italian
            if (resultCode == Activity.RESULT_CANCELED) {
                Intent refresh = new Intent(this, ReadMedicalRecordsActivity.class);
                startActivity(refresh);
                this.finish();
            }
        }
    }

    public View.OnClickListener imgBtnLanguage_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent languageIntent = new Intent(ReadMedicalRecordsActivity.this, PopUpLanguageActivity.class);
            languageIntent.putExtra("callingActivity", "it.uniba.di.sms.asilapp.ReadMedicalRecordsActivity");
            startActivity(languageIntent);
        }
    };

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_homeDoctor:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_homeDoctorIntent = new Intent(ReadMedicalRecordsActivity.this, DoctorActivity.class);
                startActivity(nav_homeDoctorIntent);
                break;
            case R.id.nav_home:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_homeIntent = new Intent(ReadMedicalRecordsActivity.this, HomepageActivity.class);
                startActivity(nav_homeIntent);
                break;
            case R.id.nav_info:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_infoIntent = new Intent(ReadMedicalRecordsActivity.this, InformativeActivity.class);
                startActivity(nav_infoIntent);
                break;
            case R.id.nav_medicalRecords:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_medicalRecordsIntent = new Intent(ReadMedicalRecordsActivity.this, MedicalRecordsActivity.class);
                startActivity(nav_medicalRecordsIntent);
                break;
            case R.id.nav_personalData:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_personalDataIntent = new Intent(ReadMedicalRecordsActivity.this, PersonalDataActivity.class);
                startActivity(nav_personalDataIntent);
                break;
            case R.id.nav_questionnaires:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_questionnairesIntent = new Intent(ReadMedicalRecordsActivity.this, QuestionnairesActivity.class);
                startActivity(nav_questionnairesIntent);
                break;
            case R.id.nav_search_patient:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_searchPatientIntent = new Intent(ReadMedicalRecordsActivity.this, SearchPatientActivity.class);
                startActivity(nav_searchPatientIntent);
                break;
            case R.id.nav_kit_opening:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_kitOpeningIntent = new Intent(ReadMedicalRecordsActivity.this, KitOpeningActivity.class);
                startActivity(nav_kitOpeningIntent);
                break;
            case R.id.nav_visited_patient:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_visitedPatientIntent = new Intent(ReadMedicalRecordsActivity.this, PatientListActivity.class);
                startActivity(nav_visitedPatientIntent);
                break;
            case R.id.nav_logout:
                drawer.closeDrawer(GravityCompat.START);
                //Sign out function
                FirebaseAuth.getInstance().signOut();
                //Create new Intent
                Intent nav_logoutIntent = new Intent(ReadMedicalRecordsActivity.this, MainActivity.class);
                startActivity(nav_logoutIntent);
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();

        // Initialize FirebaseUser
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //Initialize variable to get extra value from other intent
        if (getIntent().getExtras() != null) {
            userClickedId = getIntent().getExtras().getString("user_clicked");
            parameter = getIntent().getExtras().getString("_parameter");
        }

        //Condition
        if (userClickedId != null) {
            uId = userClickedId;
        } else {
            // Get userId Logged
            uId = user.getUid();
        }

        readMedicalRecords(uId, parameter);
    }

    public void readMedicalRecords(final String userId, final String parameter) {
        //ArrayList variable
        mMedicalRecordsList = new ArrayList<>();
        //Initialize Database Reference
        mMedicalRecordReference = FirebaseDatabase.getInstance().getReference("medical_records");
        //Used to synchronize data
        mMedicalRecordReference.keepSynced(true);
        //Add value
        mMedicalRecordReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Clear ArrayList
                mMedicalRecordsList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Get obj medicalRecord
                    MedicalRecord medicalRecord = snapshot.getValue(MedicalRecord.class);
                    if (medicalRecord.get_user().equals(userId) && medicalRecord.get_parameter().equals(parameter)) {
                        // Add obj to ArrayList
                        mMedicalRecordsList.add(medicalRecord);
                    }

                    medicalRecordAdapter = new MedicalRecordAdapter(ReadMedicalRecordsActivity.this, mMedicalRecordsList);
                    recyclerView.setAdapter(medicalRecordAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Medical Record failed
                Log.w(TAG, "loadMedicalRecord:onCancelled", databaseError.toException());
                Toast.makeText(ReadMedicalRecordsActivity.this, "Failed to load medical record.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void removeItemDoctor() {
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

    public void removeItemUser() {
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

    public void removeItemAdmin() {
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

    public void getUserRole() {
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
                Toast.makeText(ReadMedicalRecordsActivity.this, "Failed to load user.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        mUserReference.addListenerForSingleValueEvent(userListener);
    }
}
