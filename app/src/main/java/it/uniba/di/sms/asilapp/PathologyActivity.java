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
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import it.uniba.di.sms.asilapp.models.Pathology;

public class PathologyActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private static final String TAG = "PathologyActivity";

    private String pathology_id;
    private EditText mName;
    private EditText mDescription;
    private EditText mNutritional;
    private EditText mLifestyle;
    private EditText mMedicines;
    private DatabaseReference mPathologyReference;

    private DrawerLayout drawer;


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
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent sens;
        switch (item.getItemId()){
            case R.id.nav_info:
                drawer.closeDrawer(GravityCompat.START);
                sens = new Intent (PathologyActivity.this, InformativeActivity.class);
                startActivity(sens);
                break;
            case R.id.nav_medicalRecords:
                drawer.closeDrawer(GravityCompat.START);
                sens = new Intent (PathologyActivity.this, MedicalRecordsActivity.class);
                startActivity(sens);
                break;
            case R.id.nav_personalData:
                drawer.closeDrawer(GravityCompat.START);
                sens = new Intent (PathologyActivity.this, PersonalDataActivity.class);
                startActivity(sens);
                break;
            case R.id.nav_questionnaires:
                drawer.closeDrawer(GravityCompat.START);
                sens = new Intent (PathologyActivity.this, QuestionnairesActivity.class);
                startActivity(sens);
                break;
            case R.id.nav_logout:
                drawer.closeDrawer(GravityCompat.START);
                FirebaseAuth.getInstance().signOut();
                sens = new Intent(PathologyActivity.this, MainActivity.class);
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
}
