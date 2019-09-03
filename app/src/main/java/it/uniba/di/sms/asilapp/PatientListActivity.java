package it.uniba.di.sms.asilapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import it.uniba.di.sms.asilapp.adapter.PatientAdapter;
import it.uniba.di.sms.asilapp.models.User;

public class PatientListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //variable declaration
    private static final String TAG = "PatientListActivity";
    private DatabaseReference mUserReference;
    private DrawerLayout drawer;
    private EditText searchText;
    private ImageButton imgBtnLanguage;
    private List<User> mUserList;
    private MenuItem nav_home;
    private MenuItem nav_info;
    private MenuItem nav_addUser;
    private MenuItem nav_homeAdmin;
    private MenuItem nav_readRatings;
    private MenuItem nav_personalData;
    private MenuItem nav_addAcceptance;
    private MenuItem nav_medicalRecords;
    private MenuItem nav_questionnaires;
    private MenuItem nav_addRetrieveNecessities;
    private NavigationView navigationView;
    private PatientAdapter patientAdapter;
    private RecyclerView recyclerView;
    private TextView title;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {    //Called when the activity is starting.
        super.onCreate(savedInstanceState);
        //Set the activity content from a layout resource.
        setContentView(R.layout.activity_patient_list);

        //Defined variable
        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        imgBtnLanguage = findViewById(R.id.imgBtnLanguage);
        title = findViewById(R.id.patientListTitle);
        recyclerView = (RecyclerView) findViewById(R.id.userList);
        searchText = findViewById(R.id.searchText);

        //Set a Toolbar to act as the ActionBar for this Activity window.
        setSupportActionBar(toolbar);
        //Set a listener that will be notified when a menu item is selected.
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

        //Set a click listener on the imageButton objects
        imgBtnLanguage.setOnClickListener(imgBtnLanguage_listener);

        SharedPreferences prefs = getSharedPreferences("CommonPrefs",
                Activity.MODE_PRIVATE);
        imgBtnLanguage.setImageResource(R.drawable.italy);
        String language = prefs.getString("Language", "");
        if (language.equals("en")) {
            imgBtnLanguage.setImageResource(R.drawable.lang);
        }

        //Create new ActionBarDraweToggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //Adds the specified listener to the list of listeners that will be notified of drawer events.
        drawer.addDrawerListener(toggle);
        //Synchronize the indicator with the state of the linked DrawerLayout after onRestoreInstanceState has occurred.
        toggle.syncState();

        //Used to know size top avoid expensive layout operation
        recyclerView.setHasFixedSize(true);
        //Create layout manager, it is responsible for measuring and positioning item views within a RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { //receive result from activity
        //Create new Intent
        Intent refresh = new Intent(this, PatientListActivity.class);
        startActivity(refresh);
        this.finish();
    }

    @Override
    protected void onStart() {   //Called when the activity had been stopped.
        super.onStart();
        readUserList();
        int number_patient = mUserList.size();
        title.setText(getResources().getQuantityString(R.plurals.visitedPatient, number_patient, number_patient));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {   //Called when an item in the navigation menu is selected.
        switch (item.getItemId()) {
            case R.id.nav_homeDoctor:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_homeDoctorIntent = new Intent(PatientListActivity.this, DoctorActivity.class);
                startActivity(nav_homeDoctorIntent);
                break;
            case R.id.nav_search_patient:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_searchPatientIntent = new Intent(PatientListActivity.this, SearchPatientActivity.class);
                startActivity(nav_searchPatientIntent);
                break;
            case R.id.nav_kit_opening:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_kitOpeningIntent = new Intent(PatientListActivity.this, KitOpeningActivity.class);
                startActivity(nav_kitOpeningIntent);
                break;
            case R.id.nav_visited_patient:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_visitedPatientIntent = new Intent(PatientListActivity.this, PatientListActivity.class);
                startActivity(nav_visitedPatientIntent);
                break;
            case R.id.nav_video:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_videoIntent = new Intent(PatientListActivity.this, VideoActivity.class);
                startActivity(nav_videoIntent);
                break;
            case R.id.nav_logout:
                drawer.closeDrawer(GravityCompat.START);
                //Sign out function
                FirebaseAuth.getInstance().signOut();
                //Create new Intent
                Intent nav_logoutIntent = new Intent(PatientListActivity.this, MainActivity.class);
                startActivity(nav_logoutIntent);
                finish();
                break;
        }
        return true;
    }

    public void readUserList() {
        //ArrayList variable
        mUserList = new ArrayList<>();
        //Initialize Database Reference
        mUserReference = FirebaseDatabase.getInstance().getReference("user");
        //Used to synchronize data
        mUserReference.keepSynced(true);
        //Add value
        mUserReference.addValueEventListener(new ValueEventListener() {
            @Override
            //called with a snapshot of the data at this location
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Clear ArrayList
                mUserList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Get obj user
                    final User user = snapshot.getValue(User.class);
                    if (user.getRole() == 2) {
                        // Add obj to ArrayList
                        mUserList.add(user);
                        searchText.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            //Called to notify you that, within charSequence, the count characters beginning at start are about to be replaced by new text with length after.
                            }
                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            //Called to notify you that, within charSequence, the count characters beginning at start have just replaced old text that had length before
                            }
                            @Override
                            public void afterTextChanged(Editable editable) {
                            //Called to notify you that, somewhere within editable, the text has been changed.
                                mUserList.clear();
                                if (editable.toString().toLowerCase().contains(user.getSurname().toLowerCase())) {
                                    mUserList.add(user);
                                }
                            }
                        });
                    }
                    patientAdapter = new PatientAdapter(PatientListActivity.this, mUserList);
                    recyclerView.setAdapter(patientAdapter);
                }
            }

            @Override
            //triggered in the event that this listener either failed at the server, or is removed as a result of the security and Firebase rules.
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting user failed
                Log.w(TAG, "loadUser:onCancelled", databaseError.toException());
                Toast.makeText(PatientListActivity.this, "Failed to load user.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public View.OnClickListener imgBtnLanguage_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Create new Intent
            Intent languageIntent = new Intent(PatientListActivity.this, PopUpLanguageActivity.class);
            //Pass data between intents
            languageIntent.putExtra("callingActivity", "it.uniba.di.sms.asilapp.PatientListActivity");
            startActivityForResult(languageIntent, 1);
        }
    };
}
