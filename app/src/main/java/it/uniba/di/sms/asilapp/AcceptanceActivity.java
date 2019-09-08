package it.uniba.di.sms.asilapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import it.uniba.di.sms.asilapp.models.Acceptance;
import it.uniba.di.sms.asilapp.models.City;
import it.uniba.di.sms.asilapp.models.User;

public class AcceptanceActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    //variable declaration
    private static final String TAG = "AcceptanceActivity";
    private DatabaseReference mUserReference;
    private DatabaseReference mAcceptanceReference;
    private DrawerLayout drawer;
    private ImageButton imgBtnLanguage;
    private MenuItem nav_video;
    private MenuItem nav_addUser;
    private MenuItem nav_homeAdmin;
    private MenuItem nav_homeDoctor;
    private MenuItem nav_kitOpening;
    private MenuItem nav_readRatings;
    private MenuItem nav_addAcceptance;
    private MenuItem nav_searchPatient;
    private MenuItem nav_visitedPatient;
    private MenuItem nav_addRetrieveNecessities;
    private NavigationView navigationView;
    private String uId;
    private String acceptanceId;
    private TextView mName;
    private TextView mCity;
    private TextView mAddress;
    private TextView regulation;
    private TextView mListServices;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {    //Called when the activity is starting.
        super.onCreate(savedInstanceState);
        //Set the activity content from a layout resource.
        setContentView(R.layout.activity_acceptance);

        //Defined variable
        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        imgBtnLanguage = findViewById(R.id.imgBtnLanguage);
        mName = findViewById(R.id.editTextCenterName);
        mCity = findViewById(R.id.editTextCenterCity);
        mAddress = findViewById(R.id.editTextCenterLocation);
        mListServices = findViewById(R.id.editTextCenterServices);
        regulation = findViewById(R.id.editTextCenterRegulation);   //Button for norms

        //Set a Toolbar to act as the ActionBar for this Activity window.
        setSupportActionBar(toolbar);
        //Set a listener that will be notified when a menu item is selected.
        navigationView.setNavigationItemSelectedListener(this);
        // get menu from navigationView
        Menu menu = navigationView.getMenu();

        // find MenuItem you want to change
        nav_video = menu.findItem(R.id.nav_video);
        nav_addUser = menu.findItem(R.id.nav_add_user);
        nav_homeAdmin = menu.findItem(R.id.nav_homeAdmin);
        nav_homeDoctor = menu.findItem(R.id.nav_homeDoctor);
        nav_kitOpening = menu.findItem(R.id.nav_kit_opening);
        nav_readRatings = menu.findItem(R.id.nav_read_ratings);
        nav_searchPatient = menu.findItem(R.id.nav_search_patient);
        nav_visitedPatient = menu.findItem(R.id.nav_visited_patient);
        nav_addAcceptance = menu.findItem(R.id.nav_add_new_acceptance);
        nav_addRetrieveNecessities = menu.findItem(R.id.nav_add_retrive_necessities);

        //Set item visibility
        nav_video.setVisible(false);
        nav_addUser.setVisible(false);
        nav_homeAdmin.setVisible(false);
        nav_homeDoctor.setVisible(false);
        nav_kitOpening.setVisible(false);
        nav_readRatings.setVisible(false);
        nav_searchPatient.setVisible(false);
        nav_visitedPatient.setVisible(false);
        nav_addAcceptance.setVisible(false);
        nav_addRetrieveNecessities.setVisible(false);

        //Set a click listener on the text object
        regulation.setOnClickListener(editTextCenterRegulation_listener);
        //Set a click listener on the button object
        imgBtnLanguage.setOnClickListener(imgBtnLanguage_listener);

        SharedPreferences prefs = getSharedPreferences("CommonPrefs",
                Activity.MODE_PRIVATE);
        //Set image resource
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

        // Initialize FirebaseUser
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        // Get userId
        uId = user.getUid();
        // Initialize Database Reference
        mUserReference = FirebaseDatabase.getInstance().getReference("user").child(uId);
        mAcceptanceReference = FirebaseDatabase.getInstance().getReference("acceptance");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  //receive result from activity
        //Create new Intent
        Intent refresh = new Intent(this, AcceptanceActivity.class);
        startActivity(refresh);
        this.finish();
    }

    @Override
    public void onBackPressed() {   //Called when the activity has detected the user's press of the back key.
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    protected void onStart() {  //Method called when the activity is started
        super.onStart();
        getAcceptanceId();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {   //Called when an item in the navigation menu is selected.
        switch (item.getItemId()){
            case R.id.nav_home:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_homeIntent = new Intent (AcceptanceActivity.this, HomepageActivity.class);
                startActivity(nav_homeIntent);
                break;
            case R.id.nav_info:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_infoIntent = new Intent (AcceptanceActivity.this, InformativeActivity.class);
                startActivity(nav_infoIntent);
                break;
            case R.id.nav_medicalRecords:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_medicalRecordIntent = new Intent (AcceptanceActivity.this, MedicalRecordsActivity.class);
                startActivity(nav_medicalRecordIntent);
                break;
            case R.id.nav_personalData:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_personalDataIntent= new Intent (AcceptanceActivity.this, PersonalDataActivity.class);
                startActivity(nav_personalDataIntent);
                break;
            case R.id.nav_questionnaires:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_questionnairesIntent = new Intent (AcceptanceActivity.this, QuestionnairesActivity.class);
                startActivity(nav_questionnairesIntent);
                break;
            case R.id.nav_logout:
                drawer.closeDrawer(GravityCompat.START);
                //Sign out function
                FirebaseAuth.getInstance().signOut();
                //Create new Intent
                Intent nav_logoutIntent= new Intent(AcceptanceActivity.this, MainActivity.class);
                nav_logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(nav_logoutIntent);
                finish();
                break;
        }
        return true;
    }

    //Method that retrieves from the Firebase Database the info about the acceptance given the iD acceptance and sets the TextViews
    public void getAcceptanceInfo(String acceptanceId) {
        ValueEventListener cityListener = new ValueEventListener() {
            @Override
            //called with a snapshot of the data at this location
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Acceptance acceptance = dataSnapshot.getValue(Acceptance.class);
                findCityName(acceptance.getCity());
                retrieveListOfServices(acceptance.id);
                mName.setText(acceptance.name);
                mAddress.setText(acceptance.address);
            }
            @Override
            //triggered in the event that this listener either failed at the server, or is removed as a result of the security and Firebase rules.
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting acceptance failed
                Log.w(TAG, "loadAcceptance:onCancelled", databaseError.toException());
                Toast.makeText(AcceptanceActivity.this, "Failed to load acceptance",
                        Toast.LENGTH_SHORT).show();
            }
        };
        mAcceptanceReference.child(acceptanceId).addValueEventListener(cityListener);
    }

    //Method that retrieves from the Firebase Database the list of services offered from a specific acceptance and sets the text of the TextView
    private void retrieveListOfServices(String id) {
        FirebaseDatabase.getInstance().getReference("acceptance").child(id).child("listOfServices").addValueEventListener(new ValueEventListener() {
            ArrayList<String> stringArrayList = new ArrayList<>();
            @Override
            //called with a snapshot of the data at this location
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    stringArrayList.add(snapshot.getValue().toString());
                }
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < stringArrayList.size(); i++){
                    sb.append(stringArrayList.get(i));
                    if (i==stringArrayList.size()-1){
                        sb.append(".");
                    } else {
                        sb.append(", ");
                    }
                }
                mListServices.setText(sb.toString());
            }
            @Override
            //triggered in the event that this listener either failed at the server, or is removed as a result of the security and Firebase rules.
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting list of services failed
                Log.w(TAG, "loadListService:onCancelled", databaseError.toException());
                Toast.makeText(AcceptanceActivity.this, "Failed to load list of services.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Method that retrieves from the Firebase Database the name of the city given the iD and sets the text of the TextView
    public void findCityName(final long cityId) {
        DatabaseReference mCityDB = FirebaseDatabase.getInstance().getReference("city");
        ValueEventListener mListener = new ValueEventListener() {
            @Override
            //called with a snapshot of the data at this location
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    City city = snapshot.getValue(City.class);
                    if (cityId == city.getId()) {
                        mCity.setText(city.name);
                    }
                }
            }
            @Override
            //triggered in the event that this listener either failed at the server, or is removed as a result of the security and Firebase rules.
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting city failed
                Log.w(TAG, "loadCity:onCancelled", databaseError.toException());
                Toast.makeText(AcceptanceActivity.this, "Failed to load city.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        mCityDB.addValueEventListener(mListener);
    }

    //Method that retrieves the acceptanceId in which the user is hosted from the Firebase Database
    public void getAcceptanceId() {
        mUserReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            //called with a snapshot of the data at this location
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                acceptanceId = dataSnapshot.getValue(User.class).getAcceptanceId();
                getAcceptanceInfo(acceptanceId);
            }
            @Override
            //triggered in the event that this listener either failed at the server, or is removed as a result of the security and Firebase rules.
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Getting Acceptance Id failed, log a message
                Log.w(TAG, "loadAcceptanceId:onCancelled", databaseError.toException());
                Toast.makeText(AcceptanceActivity.this, "Failed to load Acceptance Id",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Listener for the button
    public View.OnClickListener imgBtnLanguage_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Create new Intent
            Intent change_languageIntent = new Intent (AcceptanceActivity.this,PopUpLanguageActivity.class);
            //Pass data between intents
            change_languageIntent.putExtra("callingActivity", "it.uniba.di.sms.asilapp.AcceptanceActivity");
            startActivityForResult(change_languageIntent, 1);
        }
    };

    // Listener for the button
    public View.OnClickListener editTextCenterRegulation_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //Create new Intent
            Intent open_RegulationIntent = new Intent (AcceptanceActivity.this,InternalRegulationActivity.class);
            startActivity(open_RegulationIntent);
        }
    };
}
