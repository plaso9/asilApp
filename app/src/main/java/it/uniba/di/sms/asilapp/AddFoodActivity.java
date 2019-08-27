package it.uniba.di.sms.asilapp;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import it.uniba.di.sms.asilapp.models.City;
import it.uniba.di.sms.asilapp.models.Necessities;


public class AddFoodActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    //Variable declaration
    private static final String TAG = "PopUpTempActivity";

    private Button submitNecessities;
    private EditText mMall;
    private EditText mPharmacy;
    private long idCity;
    private Spinner mCitySpinner;

    private DrawerLayout drawer;

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
    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set the activity content from a layout resource.
        setContentView(R.layout.activity_add_food);

        //Defined variables
        mMall = findViewById(R.id.editTextMallLocation);
        mPharmacy = findViewById(R.id.editTextPharmacyLocation);
        mCitySpinner = findViewById(R.id.spinnerCityFood);
        submitNecessities = findViewById(R.id.btnSubmit);
        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        // get menu from navigationView
        Menu menu = navigationView.getMenu();

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
        nav_home.setVisible(false);
        nav_info.setVisible(false);
        nav_video.setVisible(false);
        nav_homeDoctor.setVisible(false);
        nav_kitOpening.setVisible(false);
        nav_personalData.setVisible(false);
        nav_searchPatient.setVisible(false);
        nav_medicalRecords.setVisible(false);
        nav_questionnaires.setVisible(false);
        nav_visitedPatient.setVisible(false);

        //Create new ActionBarDraweToggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //Adds the specified listener to the list of listeners that will be notified of drawer events.
        drawer.addDrawerListener(toggle);
        //Synchronize the indicator with the state of the linked DrawerLayout after onRestoreInstanceState has occurred.
        toggle.syncState();



        //Set a Toolbar to act as the ActionBar for this Activity window.
        setSupportActionBar(toolbar);
        //Set listener value variable
        submitNecessities.setOnClickListener(submitNecessities_listener);
        //Set a listener that will be notified when a menu item is selected.
        navigationView.setNavigationItemSelectedListener(this);

        // Initialize Database Reference
        DatabaseReference cityRef = FirebaseDatabase.getInstance().getReference("city");
        cityRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<City> cities = new ArrayList<>();
                for(DataSnapshot acceptanceSnapShot:dataSnapshot.getChildren())
                {
                    cities.add(acceptanceSnapShot.getValue(City.class));
                }
                //Get all names of acceptance
                final List<String> name_list = new ArrayList<>();
                for(City city: cities){
                    name_list.add(city.getName());
                }
                //Create adapter and set for spinner
                ArrayAdapter<String> stringArrayAdapterCity;
                stringArrayAdapterCity = new ArrayAdapter<>(AddFoodActivity.this, android.R.layout.simple_list_item_1, name_list);
                mCitySpinner.setAdapter(stringArrayAdapterCity);

                //Method to retrieve the id code of the item selected in the spinner
                mCitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                Toast.makeText(AddFoodActivity.this, "Failed to load city.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Set on click listener submit button
    public View.OnClickListener submitNecessities_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (mMall.getText().toString().equals("") ||
                mPharmacy.getText().toString().equals("")
                ) {
                Toast.makeText(AddFoodActivity.this, "No field should be empty", Toast.LENGTH_SHORT).show();
            } else {
                addNewRetrieveNecessities();
            }
        }
    };

    public void addNewRetrieveNecessities(){
        //Get value to insert in DB
        final String mall = mMall.getText().toString();
        final String pharmacy = mPharmacy.getText().toString();

        //New Constructor
        Necessities necessities = new Necessities(
                idCity,
                mall,
                pharmacy
        );

        //Adding value to DB
        FirebaseDatabase.getInstance().getReference("basic_necessities").push().setValue(necessities).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    //success message
                    Toast.makeText(AddFoodActivity.this, "Addedd successfully", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    //failure message
                    Toast.makeText(AddFoodActivity.this, "Addedd failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_homeAdmin:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_homeDoctorIntent = new Intent (AddFoodActivity.this, AdminActivity.class);
                startActivity(nav_homeDoctorIntent);
                break;
            case R.id.nav_add_user:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_addUserIntent = new Intent (AddFoodActivity.this, AddUserActivity.class);
                startActivity(nav_addUserIntent);
                break;
            case R.id.nav_add_new_acceptance:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_addAcceptanceIntent = new Intent (AddFoodActivity.this, AddAcceptanceActivity.class);
                startActivity(nav_addAcceptanceIntent);
                break;
            case R.id.nav_add_retrive_necessities:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_addFoodIntent = new Intent (AddFoodActivity.this, AddFoodActivity.class);
                startActivity(nav_addFoodIntent);
                break;
            case R.id.nav_read_ratings:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_readRatingIntent = new Intent (AddFoodActivity.this, ReadRatingsActivity.class);
                startActivity(nav_readRatingIntent);
                break;
            case R.id.nav_logout:
                drawer.closeDrawer(GravityCompat.START);
                //Sign out function
                FirebaseAuth.getInstance().signOut();
                //Create new Intent
                Intent nav_logoutIntent = new Intent(AddFoodActivity.this, MainActivity.class);
                startActivity(nav_logoutIntent);
                finish();
                break;
        }
        return true;
    }
}
