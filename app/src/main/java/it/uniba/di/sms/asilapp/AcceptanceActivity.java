package it.uniba.di.sms.asilapp;

import android.content.Intent;
import android.provider.Settings;
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
    private TextView regulation;
    private String uId;
    private String acceptanceId;


    private TextView mName;
    private TextView mAddress;
    private TextView mCity;
    private TextView mListServices;

    private DatabaseReference mUserReference;
    private DatabaseReference mAcceptanceReference;

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceptance);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //Button for norms
        regulation = findViewById(R.id.editTextCenterRegulation);
        regulation.setOnClickListener(editTextCenterRegulation_listener);


        // Initialize FirebaseUser
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        // Get userId
        uId = user.getUid();
        // Initialize Database Reference
        mUserReference = FirebaseDatabase.getInstance().getReference().child("user").child(uId);
        mAcceptanceReference = FirebaseDatabase.getInstance().getReference().child("acceptance");

        mName = findViewById(R.id.editTextCenterName);
        mAddress = findViewById(R.id.editTextCenterLocation);
        mCity = findViewById(R.id.editTextCenterCity);
        mListServices = findViewById(R.id.editTextCenterServices);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent sens;
        switch (item.getItemId()){
            case R.id.nav_info:
                drawer.closeDrawer(GravityCompat.START);
                sens = new Intent (AcceptanceActivity.this, InformativeActivity.class);
                startActivity(sens);
                break;
            case R.id.nav_medicalRecords:
                drawer.closeDrawer(GravityCompat.START);
                sens = new Intent (AcceptanceActivity.this, MedicalRecordsActivity.class);
                startActivity(sens);
                break;
            case R.id.nav_personalData:
                drawer.closeDrawer(GravityCompat.START);
                sens = new Intent (AcceptanceActivity.this, PersonalDataActivity.class);
                startActivity(sens);
                break;
            case R.id.nav_questionnaires:
                drawer.closeDrawer(GravityCompat.START);
                sens = new Intent (AcceptanceActivity.this, QuestionnairesActivity.class);
                startActivity(sens);
                break;
            case R.id.nav_logout:
                drawer.closeDrawer(GravityCompat.START);
                FirebaseAuth.getInstance().signOut();
                sens = new Intent(AcceptanceActivity.this, MainActivity.class);
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

    // Listener for the button
    public View.OnClickListener editTextCenterRegulation_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent sens = new Intent (AcceptanceActivity.this,InternalRegulationActivity.class);
            startActivity(sens);

        }
    };


    //function to get city basically information
    public void getAcceptanceInfo(String acceptanceId) {
        ValueEventListener cityListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Acceptance acceptance = dataSnapshot.getValue(Acceptance.class);

                findCityName(acceptance.getCity());
                retrieveListOfServices(acceptance.id);

                mName.setText(acceptance.name);
                mAddress.setText(acceptance.address);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "loadAcceptance:onCancelled", databaseError.toException());
                Toast.makeText(AcceptanceActivity.this, "Failed to load acceptance",
                        Toast.LENGTH_SHORT).show();
            }
        };
        mAcceptanceReference.child(acceptanceId).addValueEventListener(cityListener);
    }

    private void retrieveListOfServices(String id) {
        FirebaseDatabase.getInstance().getReference("acceptance").child(id).child("listOfServices").addValueEventListener(new ValueEventListener() {
            ArrayList<String> stringArrayList = new ArrayList<>();

            @Override
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
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void findCityName(final long cityId) {
        DatabaseReference mCityDB = FirebaseDatabase.getInstance().getReference("city");
        ValueEventListener mListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    City city = snapshot.getValue(City.class);
                    if (cityId == city.getId()) {
                        mCity.setText(city.name);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mCityDB.addValueEventListener(mListener);
    }

    // function to get foreign key _acceptance from user table
    public void getAcceptanceId() {
        mUserReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                acceptanceId = dataSnapshot.getValue(User.class).getAcceptanceId();
                getAcceptanceInfo(acceptanceId);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Getting Acceptance Id failed, log a message
                Log.w(TAG, "loadAcceptanceId:onCancelled", databaseError.toException());
                Toast.makeText(AcceptanceActivity.this, "Failed to load Acceptance Id",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        getAcceptanceId();
    }
}
