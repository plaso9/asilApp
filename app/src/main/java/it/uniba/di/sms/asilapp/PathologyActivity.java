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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import it.uniba.di.sms.asilapp.models.Pathology;
import it.uniba.di.sms.asilapp.models.User;

public class PathologyActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private static final String TAG = "PathologyActivity";
    private DatabaseReference mUserReference;
    private String pathology_id;
    private EditText mName;
    private EditText mDescription;
    private EditText mNutritional;
    private EditText mLifestyle;
    private EditText mMedicines;
    private DatabaseReference mPathologyReference;

    private ImageButton imgBtnLanguage;
    private DrawerLayout drawer;
    private MenuItem nav_home;
    private MenuItem nav_info;
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
        setContentView(R.layout.activity_pathology);

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




        //Initialize variable to get extra value from other intent
        if (getIntent().getExtras() != null) {
            pathology_id = getIntent().getExtras().getString("pathology_clicked");
        }

        // Initialize Database Reference
        mPathologyReference = FirebaseDatabase.getInstance().getReference("pathology").child(pathology_id);

        // Defined personal data variable
        mName = findViewById(R.id.edit_name_pathology);
        mDescription = findViewById(R.id.edit_description_pathology);
        mNutritional = findViewById(R.id.edit_nutritional_pathology);
        mLifestyle = findViewById(R.id.edit_lifestyle_pathology);
        mMedicines = findViewById(R.id.edit_medicines_pathology);

        ValueEventListener pathologyListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get User object and use the values to update the UI
                Pathology pathology = dataSnapshot.getValue(Pathology.class);
                mName.setText(pathology.name);
                mDescription.setText(pathology.description);
                mNutritional.setText(pathology.nutritional);
                mLifestyle.setText(pathology.lifestyle);
                mMedicines.setText(pathology.medicine);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Pathology failed, log a message
                Log.w(TAG, "loadPathology:onCancelled", databaseError.toException());
                Toast.makeText(PathologyActivity.this, "Failed to load pathology.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        mPathologyReference.addListenerForSingleValueEvent(pathologyListener);
        imgBtnLanguage = findViewById(R.id.imgBtnLanguage);
        imgBtnLanguage.setOnClickListener(imgBtnLanguage_listener);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) { // english
            if (resultCode == Activity.RESULT_CANCELED) {
                imgBtnLanguage.setImageResource(R.drawable.lang);
                Intent refresh = new Intent(this, PathologyActivity.class);
                startActivity(refresh);
                this.finish();
            }
        }
        if (requestCode == 2) { //italian
            if (resultCode == Activity.RESULT_CANCELED) {
                imgBtnLanguage.setImageResource(R.drawable.italy);
                Intent refresh = new Intent(this, PathologyActivity.class);
                startActivity(refresh);
                this.finish();
            }
        }
    }

    public View.OnClickListener imgBtnLanguage_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent languageIntent = new Intent (PathologyActivity.this,PopUpLanguageActivity.class);
            languageIntent.putExtra("callingActivity", "it.uniba.di.sms.asilapp.PathologyActivity");
            startActivity(languageIntent);
        }
    };

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_homeDoctor:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_homeDoctorIntent = new Intent (PathologyActivity.this, DoctorActivity.class);
                startActivity(nav_homeDoctorIntent);
                break;
            case R.id.nav_home:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_homeIntent = new Intent (PathologyActivity.this, HomepageActivity.class);
                startActivity(nav_homeIntent);
                break;
            case R.id.nav_info:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_infoIntent = new Intent (PathologyActivity.this, InformativeActivity.class);
                startActivity(nav_infoIntent);
                break;
            case R.id.nav_medicalRecords:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_medicalRecordsIntent = new Intent (PathologyActivity.this, MedicalRecordsActivity.class);
                startActivity(nav_medicalRecordsIntent);
                break;
            case R.id.nav_personalData:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_personalDataIntent  = new Intent (PathologyActivity.this, PersonalDataActivity.class);
                startActivity(nav_personalDataIntent);
                break;
            case R.id.nav_questionnaires:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_questionnairesIntent = new Intent (PathologyActivity.this, QuestionnairesActivity.class);
                startActivity(nav_questionnairesIntent);
                break;
            case R.id.nav_search_patient:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_searchPatientIntent = new Intent (PathologyActivity.this, SearchPatientActivity.class);
                startActivity(nav_searchPatientIntent);
                break;
            case R.id.nav_kit_opening:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_kitOpeningIntent = new Intent (PathologyActivity.this, KitOpeningActivity.class);
                startActivity(nav_kitOpeningIntent);
                break;
            case R.id.nav_visited_patient:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_visitedPatientIntent = new Intent (PathologyActivity.this, PatientListActivity.class);
                startActivity(nav_visitedPatientIntent);
                break;
            case R.id.nav_logout:
                drawer.closeDrawer(GravityCompat.START);
                //Sign out function
                FirebaseAuth.getInstance().signOut();
                //Create new Intent
                Intent nav_logoutIntent = new Intent(PathologyActivity.this, MainActivity.class);
                startActivity(nav_logoutIntent);
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

    public void removeItemDoctor(){
        NavigationView navigationView = findViewById(R.id.nav_view);
        // get menu from navigationView
        Menu menu = navigationView.getMenu();
        // find MenuItem you want to change
        nav_homeDoctor = menu.findItem(R.id.nav_homeDoctor);
        nav_kitOpening = menu.findItem(R.id.nav_kit_opening);
        nav_searchPatient = menu.findItem(R.id.nav_search_patient);
        nav_visitedPatient = menu.findItem(R.id.nav_visited_patient);
        //Set item visibility
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
                Toast.makeText(PathologyActivity.this, "Failed to load user.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        mUserReference.addListenerForSingleValueEvent(userListener);
    }
}
