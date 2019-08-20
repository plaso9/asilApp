package it.uniba.di.sms.asilapp;

import android.content.Intent;
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
import android.view.MenuItem;
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

public class ReadMedicalRecordsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private static final String TAG = "ReadMedRecordsActivity";

    private String uId;
    private String userClickedId;
    private String parameter;

    private RecyclerView recyclerView;
    private DatabaseReference mMedicalRecordReference;

    private MedicalRecordAdapter medicalRecordAdapter;
    private List<MedicalRecord> mMedicalRecordsList;

    private DrawerLayout drawer;


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



        recyclerView = findViewById(R.id.medicalRecordsList);
        //Used to know size top avoid expensive layout operation
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent sens;
        switch (item.getItemId()){
            case R.id.nav_info:
                drawer.closeDrawer(GravityCompat.START);
                sens = new Intent (ReadMedicalRecordsActivity.this, InformativeActivity.class);
                startActivity(sens);
                break;
            case R.id.nav_medicalRecords:
                drawer.closeDrawer(GravityCompat.START);
                sens = new Intent (ReadMedicalRecordsActivity.this, MedicalRecordsActivity.class);
                startActivity(sens);
                break;
            case R.id.nav_personalData:
                drawer.closeDrawer(GravityCompat.START);
                sens = new Intent (ReadMedicalRecordsActivity.this, PersonalDataActivity.class);
                startActivity(sens);
                break;
            case R.id.nav_questionnaires:
                drawer.closeDrawer(GravityCompat.START);
                sens = new Intent (ReadMedicalRecordsActivity.this, QuestionnairesActivity.class);
                startActivity(sens);
                break;
            case R.id.nav_logout:
                drawer.closeDrawer(GravityCompat.START);
                FirebaseAuth.getInstance().signOut();
                sens = new Intent(ReadMedicalRecordsActivity.this, MainActivity.class);
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


    @Override
    protected void onStart(){
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

    public void readMedicalRecords(final String userId, final String parameter){
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
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    // Get obj medicalRecord
                    MedicalRecord medicalRecord = snapshot.getValue(MedicalRecord.class);
                    if(medicalRecord.get_user().equals(userId) && medicalRecord.get_parameter().equals(parameter)){
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
}
