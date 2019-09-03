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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import it.uniba.di.sms.asilapp.models.Acceptance;
import it.uniba.di.sms.asilapp.models.City;

public class AddAcceptanceActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //Variable declaration
    private static final String TAG = "AddAcceptanceActivity";

    private long idCity;
    private EditText eTcenterName;
    private EditText eTcenterLocation;
    private EditText eTcenterServices;
    private Spinner spinnerCity;
    private Button buttonSubmitAcceptance;
    private DrawerLayout drawer;
    private ImageButton imgBtnLanguage;
    private MenuItem nav_home;
    private MenuItem nav_info;
    private MenuItem nav_video;
    private MenuItem nav_homeDoctor;
    private MenuItem nav_kitOpening;
    private MenuItem nav_personalData;
    private MenuItem nav_searchPatient;
    private MenuItem nav_medicalRecords;
    private MenuItem nav_questionnaires;
    private MenuItem nav_visitedPatient;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set the activity content from a layout resource.
        setContentView(R.layout.activity_add_acceptance);

        //Defined variable
        eTcenterName = findViewById(R.id.editTextCenterName);
        eTcenterLocation = findViewById(R.id.editTextCenterLocation);
        eTcenterServices = findViewById(R.id.editTextCenterServices);
        spinnerCity = findViewById(R.id.spinnerCity);
        buttonSubmitAcceptance = findViewById(R.id.btnSubmitAcceptance);
        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        imgBtnLanguage = findViewById(R.id.imgBtnLanguage);

        SharedPreferences prefs = getSharedPreferences("CommonPrefs",
                Activity.MODE_PRIVATE);
        imgBtnLanguage.setImageResource(R.drawable.italy);
        String language = prefs.getString("Language", "");
        if (language.equals("en")) {
            imgBtnLanguage.setImageResource(R.drawable.lang);
        }

        // get menu from navigationView
        Menu menu = navigationView.getMenu();
        //Set a Toolbar to act as the ActionBar for this Activity window.
        setSupportActionBar(toolbar);

        // find MenuItem you want to change
        nav_home = menu.findItem(R.id.nav_home);
        nav_info = menu.findItem(R.id.nav_info);
        nav_video = menu.findItem(R.id.nav_video);
        nav_homeDoctor = menu.findItem(R.id.nav_homeDoctor);
        nav_kitOpening = menu.findItem(R.id.nav_kit_opening);
        nav_personalData = menu.findItem(R.id.nav_personalData);
        nav_searchPatient = menu.findItem(R.id.nav_search_patient);
        nav_medicalRecords = menu.findItem(R.id.nav_medicalRecords);
        nav_questionnaires = menu.findItem(R.id.nav_questionnaires);
        nav_visitedPatient = menu.findItem(R.id.nav_visited_patient);

        //Set item visibility
        nav_video.setVisible(false);
        nav_home.setVisible(false);
        nav_info.setVisible(false);
        nav_homeDoctor.setVisible(false);
        nav_kitOpening.setVisible(false);
        nav_personalData.setVisible(false);
        nav_searchPatient.setVisible(false);
        nav_medicalRecords.setVisible(false);
        nav_questionnaires.setVisible(false);
        nav_visitedPatient.setVisible(false);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //Set a listener that will be notified when a menu item is selected.
        navigationView.setNavigationItemSelectedListener(this);
        //Set a click listener on the button object
        buttonSubmitAcceptance.setOnClickListener(btnSubmitAcceptance_listener);
        imgBtnLanguage.setOnClickListener(imgBtnLanguage_listener);

        //Initialize DB to get acceptance reference
        DatabaseReference cityRef = FirebaseDatabase.getInstance().getReference("city");
        cityRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<City> cities = new ArrayList<>();
                for (DataSnapshot acceptanceSnapShot : dataSnapshot.getChildren()) {
                    cities.add(acceptanceSnapShot.getValue(City.class));
                }
                //Get all names of acceptance
                final List<String> name_list = new ArrayList<>();
                for (City city : cities) {
                    name_list.add(city.getName());
                }
                //Create adapter and set for spinner
                ArrayAdapter<String> stringArrayAdapterCity;
                stringArrayAdapterCity = new ArrayAdapter<>(AddAcceptanceActivity.this, android.R.layout.simple_list_item_1, name_list);
                spinnerCity.setAdapter(stringArrayAdapterCity);

                //Method to retrieve the id code of the item selected in the spinner
                spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        idCity = cities.get(i).getId();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting city failed
                Log.w(TAG, "loadCity:onCancelled", databaseError.toException());
                Toast.makeText(AddAcceptanceActivity.this, "Failed to load city.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Intent refresh = new Intent(this, AddAcceptanceActivity.class);
        startActivity(refresh);
        this.finish();

    }

    public View.OnClickListener imgBtnLanguage_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent change_languageIntent = new Intent(AddAcceptanceActivity.this, PopUpLanguageActivity.class);
            change_languageIntent.putExtra("callingActivity", "it.uniba.di.sms.asilapp.AddAcceptanceActivity");
            startActivityForResult(change_languageIntent, 1);
        }
    };


    public View.OnClickListener btnSubmitAcceptance_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (eTcenterName.getText().toString().equals("") ||
                    eTcenterLocation.getText().toString().equals("") ||
                    eTcenterServices.getText().toString().equals("")) { //Check if all the fields are not empty
                Toast.makeText(AddAcceptanceActivity.this, "No field should be empty", Toast.LENGTH_LONG).show();

            } else {
                addNewAcceptance(); //Method to add a new acceptance
                Toast.makeText(AddAcceptanceActivity.this, "Added successfully", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    };

    private void addNewAcceptance() {
        final String nameCenter = eTcenterName.getText().toString();
        final String locationCenter = eTcenterLocation.getText().toString();
        final String centerServices = eTcenterServices.getText().toString();
        String[] services = centerServices.split(","); //Split string every comma
        ArrayList<String> listOfServices = new ArrayList<>();
        //For loop to populate the list of services of the acceptance
        for (int i = 0; i < services.length; i++) {
            listOfServices.add(services[i].trim());//add string_service to the list
        }

        String id = FirebaseDatabase.getInstance().getReference("acceptance").push().getKey(); //push method enters the object in the db
        Acceptance acceptance = new Acceptance(
                nameCenter,
                locationCenter,
                listOfServices,
                id,
                idCity
        );

        FirebaseDatabase.getInstance().getReference("acceptance").child(id).setValue(acceptance);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_homeAdmin:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_homeDoctorIntent = new Intent(AddAcceptanceActivity.this, AdminActivity.class);
                startActivity(nav_homeDoctorIntent);
                break;
            case R.id.nav_add_user:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_addUserIntent = new Intent(AddAcceptanceActivity.this, AddUserActivity.class);
                startActivity(nav_addUserIntent);
                break;
            case R.id.nav_add_new_acceptance:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_addAcceptanceIntent = new Intent(AddAcceptanceActivity.this, AddAcceptanceActivity.class);
                startActivity(nav_addAcceptanceIntent);
                break;
            case R.id.nav_add_retrive_necessities:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_addFoodIntent = new Intent(AddAcceptanceActivity.this, AddFoodActivity.class);
                startActivity(nav_addFoodIntent);
                break;
            case R.id.nav_read_ratings:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_readRatingIntent = new Intent(AddAcceptanceActivity.this, ReadRatingsActivity.class);
                startActivity(nav_readRatingIntent);
                break;
            case R.id.nav_logout:
                drawer.closeDrawer(GravityCompat.START);
                //Sign out function
                FirebaseAuth.getInstance().signOut();
                //Create new Intent
                Intent nav_logoutIntent = new Intent(AddAcceptanceActivity.this, MainActivity.class);
                startActivity(nav_logoutIntent);
                finish();
                break;
        }
        return true;
    }
}
