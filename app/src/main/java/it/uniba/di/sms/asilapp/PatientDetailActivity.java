package it.uniba.di.sms.asilapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import it.uniba.di.sms.asilapp.models.User;

public class PatientDetailActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "PatientDetailActivity";
    //Define variable
    private String user_id;
    private TextView mName;
    private DatabaseReference mUserReference;
    private ImageButton imgBtnLanguage;


    //Define cards
    CardView card_view_personalData;
    CardView card_view_medicalRecords;
    CardView card_view_questionnaires;

    int PROGRESS_BAR_STATUS = 0;
    ProgressDialog progressBar;
    private DrawerLayout drawer;

    private MenuItem nav_addUser;
    private MenuItem nav_homeAdmin;
    private MenuItem nav_readRatings;
    private MenuItem nav_addAcceptance;
    private MenuItem nav_addRetrieveNecessities;
    private MenuItem nav_home;
    private MenuItem nav_info;
    private MenuItem nav_personalData;
    private MenuItem nav_medicalRecords;
    private MenuItem nav_questionnaires;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_detail);

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
        nav_addUser = menu.findItem(R.id.nav_add_user);
        nav_homeAdmin = menu.findItem(R.id.nav_homeAdmin);
        nav_readRatings = menu.findItem(R.id.nav_read_ratings);
        nav_personalData = menu.findItem(R.id.nav_personalData);
        nav_addAcceptance = menu.findItem(R.id.nav_add_new_acceptance);
        nav_medicalRecords = menu.findItem(R.id.nav_medicalRecords);
        nav_questionnaires = menu.findItem(R.id.nav_questionnaires);
        nav_addRetrieveNecessities = menu.findItem(R.id.nav_add_retrive_necessities);
        //Set item visibility
        nav_home.setVisible(false);
        nav_info.setVisible(false);
        nav_addUser.setVisible(false);
        nav_homeAdmin.setVisible(false);
        nav_readRatings.setVisible(false);
        nav_personalData.setVisible(false);
        nav_addAcceptance.setVisible(false);
        nav_medicalRecords.setVisible(false);
        nav_questionnaires.setVisible(false);
        nav_addRetrieveNecessities.setVisible(false);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //Get User Clicked Id
        if (getIntent().getExtras() != null) {
            user_id = getIntent().getStringExtra("user_clicked");
        }
        // Initialize Database Reference
        mUserReference = FirebaseDatabase.getInstance().getReference("user").child(user_id);
        // Defined patient data variable
        mName = findViewById(R.id.text_userNameClicked);
        //defined card variable
        card_view_personalData = findViewById(R.id.card_personalData);
        card_view_medicalRecords = findViewById(R.id.card_medicalRecords);
        card_view_questionnaires = findViewById(R.id.card_questionnaires);
        imgBtnLanguage = findViewById(R.id.imgBtnLanguage);

        imgBtnLanguage.setImageResource(R.drawable.italy);
        Configuration config = getBaseContext().getResources().getConfiguration();
        if (config.locale.getLanguage().equals("en")) {
            imgBtnLanguage.setImageResource(R.drawable.lang);
        }


        //set function to card
        card_view_personalData.setOnClickListener(card_view_Personaldata_listener);
        card_view_medicalRecords.setOnClickListener(card_view_MedicalRecords_listener);
        card_view_questionnaires.setOnClickListener(card_view_Questionnaries_listener);
        imgBtnLanguage.setOnClickListener(imgBtnLanguage_listener);

        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get User object and use the values to update the UI
                User user = dataSnapshot.getValue(User.class);
                mName.setText("Profilo di : " + user.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting User failed, log a message
                Log.w(TAG, "loadUser:onCancelled", databaseError.toException());
                Toast.makeText(PatientDetailActivity.this, "Failed to load user information.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        mUserReference.addValueEventListener(userListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) { // english
            if (resultCode == Activity.RESULT_CANCELED) {
                Intent refresh = new Intent(this, PatientDetailActivity.class);
                startActivity(refresh);
                this.finish();
            }
        }
        if (requestCode == 2) { //italian
            if (resultCode == Activity.RESULT_CANCELED) {
                Intent refresh = new Intent(this, PatientDetailActivity.class);
                startActivity(refresh);
                this.finish();
            }
        }
    }

    public View.OnClickListener imgBtnLanguage_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent languageIntent = new Intent(PatientDetailActivity.this, PopUpLanguageActivity.class);
            languageIntent.putExtra("callingActivity", "it.uniba.di.sms.asilapp.PatientDetailActivity");
            startActivity(languageIntent);
        }
    };


    //Open Personal Data Intent
    public View.OnClickListener card_view_Personaldata_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            progressBar = new ProgressDialog(PatientDetailActivity.this);
            progressBar.setIndeterminate(true);
            progressBar.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressBar.show();
            Intent personalDataIntent = new Intent(PatientDetailActivity.this, PersonalDataActivity.class);
            personalDataIntent.putExtra("user_clicked", user_id);
            startActivity(personalDataIntent);

            PROGRESS_BAR_STATUS = 1;
        }
    };
    //Open Questionnaires Intent
    public View.OnClickListener card_view_Questionnaries_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent questionnairesIntent = new Intent(PatientDetailActivity.this, QuestionnairesActivity.class);
            questionnairesIntent.putExtra("user_clicked", user_id);
            startActivity(questionnairesIntent);
        }
    };
    //Open Medical Records Intent
    public View.OnClickListener card_view_MedicalRecords_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent medicalRecordsIntent = new Intent(PatientDetailActivity.this, MedicalRecordsActivity.class);
            medicalRecordsIntent.putExtra("user_clicked", user_id);
            startActivity(medicalRecordsIntent);
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        afterExecution();
    }


    public void afterExecution() {
        if (PROGRESS_BAR_STATUS == 1) {
            progressBar.dismiss();
            PROGRESS_BAR_STATUS = 0;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_homeDoctor:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_homeDoctorIntent = new Intent(PatientDetailActivity.this, DoctorActivity.class);
                startActivity(nav_homeDoctorIntent);
                break;
            case R.id.nav_search_patient:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_searchPatientIntent = new Intent(PatientDetailActivity.this, SearchPatientActivity.class);
                startActivity(nav_searchPatientIntent);
                break;
            case R.id.nav_kit_opening:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_kitOpeningIntent = new Intent(PatientDetailActivity.this, KitOpeningActivity.class);
                startActivity(nav_kitOpeningIntent);
                break;
            case R.id.nav_visited_patient:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_visitedPatientIntent = new Intent(PatientDetailActivity.this, PatientListActivity.class);
                startActivity(nav_visitedPatientIntent);
                break;
            case R.id.nav_video:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_videoIntent = new Intent(PatientDetailActivity.this, VideoActivity.class);
                startActivity(nav_videoIntent);
                break;
            case R.id.nav_logout:
                drawer.closeDrawer(GravityCompat.START);
                //Sign out function
                FirebaseAuth.getInstance().signOut();
                //Create new Intent
                Intent nav_logoutIntent = new Intent(PatientDetailActivity.this, MainActivity.class);
                startActivity(nav_logoutIntent);
                finish();
                break;
        }
        return true;
    }
}
