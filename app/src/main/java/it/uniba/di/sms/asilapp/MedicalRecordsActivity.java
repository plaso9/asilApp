package it.uniba.di.sms.asilapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import it.uniba.di.sms.asilapp.models.User;

public class MedicalRecordsActivity extends AppCompatActivity {
    private static final String TAG = "MedicalRecordsActivity";
    private Button seeTemperatureStats;
    private Button addTemperature;
    private Button seeBloodPressureStats;
    private Button addBloodPressure;
    private Button seeGlycemiaStats;
    private Button addGlycemia;
    private Button seeHeartbeatStats;
    private Button addHeartbeat;
    private Button seeECGStats;
    private Button addECG;
    private Button seeSymptomsStats;
    private Button addSymptoms;
    private Button seePathologyStats;
    private Button addPathology;

    private DatabaseReference mUserReference;
    private String uId;
    private Long mRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_records);

        seeTemperatureStats = findViewById(R.id.buttonSeeTemperatureStats);
        addTemperature = findViewById(R.id.buttonAddTemperature);
        seeBloodPressureStats = findViewById(R.id.buttonSeeBloodPressureStats);
        addBloodPressure = findViewById(R.id.buttonAddBloodPressure);
        seeGlycemiaStats = findViewById(R.id.buttonSeeGlycemiaStats);
        addGlycemia = findViewById(R.id.buttonAddGlycemiaPressure);
        seeHeartbeatStats = findViewById(R.id.buttonSeeHeartBeatStats);
        addHeartbeat = findViewById(R.id.buttonAddHeartbeat);
        seeECGStats = findViewById(R.id.buttonSeeECGStats);
        addECG = findViewById(R.id.buttonAddECG);
        seeSymptomsStats = findViewById(R.id.buttonSeeSymptomsStats);
        addSymptoms = findViewById(R.id.buttonAddSymptoms);
        seePathologyStats = findViewById(R.id.buttonSeePathologyStats);
        addPathology = findViewById(R.id.buttonAddPathology);



        // Initialize FirebaseUser
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        // Get userId
        uId = user.getUid();
        // Initialize Database Reference
        mUserReference = FirebaseDatabase.getInstance().getReference()
                .child("user").child(uId);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get User object and use the values to update the UI
                User user = dataSnapshot.getValue(User.class);
               mRole = user.getRole();

               if (mRole != 3){
                   addTemperature.setVisibility(View.INVISIBLE);
                   addBloodPressure.setVisibility(View.INVISIBLE);
                   addGlycemia.setVisibility(View.INVISIBLE);
                   addHeartbeat.setVisibility(View.INVISIBLE);
                   addECG.setVisibility(View.INVISIBLE);
                   addSymptoms.setVisibility(View.INVISIBLE);
                   addPathology.setVisibility(View.INVISIBLE);

               }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting User failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                Toast.makeText(MedicalRecordsActivity.this, "Failed to load user.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        mUserReference.addValueEventListener(userListener);
    }
}
