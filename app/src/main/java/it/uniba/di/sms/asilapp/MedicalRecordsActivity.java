package it.uniba.di.sms.asilapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
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
    ImageView image_seeTemperatureStats;
    ImageView image_seeBloodPressureStats;
    ImageView image_seeGlycemiaStats;
    ImageView image_seeHeartbeatStats;
    ImageView image_seeECGStats;
    ImageView image_seeSymptomsStats;
    ImageView image_seePathologyStats;
    Button button_addTemperature;
    Button button_addBloodPressure;
    Button button_addGlycemia;
    Button button_addHeartbeat;
    Button button_addECG;
    Button button_addSymptoms;
    Button button_addPathology;
    private DatabaseReference mUserReference;
    private String uId;
    private Long mRole;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_records);

        image_seeTemperatureStats = findViewById(R.id.image_seeTemperatureStats);
        image_seeBloodPressureStats = findViewById(R.id.image_seeBloodPressureStats);
        image_seeGlycemiaStats = findViewById(R.id.image_seeGlycemiaStats);
        image_seeHeartbeatStats = findViewById(R.id.image_seeHeartbeatStats);
        image_seeECGStats = findViewById(R.id.image_seeECGStats);
        image_seeSymptomsStats = findViewById(R.id.image_seeSymptomsStats);
        image_seePathologyStats = findViewById(R.id.image_SeePathologyStats);

        button_addTemperature = findViewById(R.id.buttonAddTemperature);
        button_addBloodPressure = findViewById(R.id.buttonAddBloodPressure);
        button_addGlycemia = findViewById(R.id.buttonAddGlycemia);
        button_addHeartbeat = findViewById(R.id.buttonAddHeartbeat);
        button_addECG = findViewById(R.id.buttonAddECG);
        button_addSymptoms = findViewById(R.id.buttonAddSymptoms);
        button_addPathology = findViewById(R.id.buttonAddPathology);

        image_seeTemperatureStats.setOnClickListener(image_seeTemperatureStats_listener);
        image_seeBloodPressureStats.setOnClickListener(image_seeBloodPressureStats_listener);
        image_seeGlycemiaStats.setOnClickListener(image_seeGlycemiaStats_listener);
        image_seeHeartbeatStats.setOnClickListener(image_seeHeartbeatStats_listener);
        image_seeECGStats.setOnClickListener(image_seeECGStats_listener);
        image_seeSymptomsStats.setOnClickListener(image_seeSymptomsStats_listener);
        image_seePathologyStats.setOnClickListener(image_seePathologyStats_listener);

        button_addTemperature.setOnClickListener(button_addTemperature_listener);
        button_addBloodPressure.setOnClickListener(button_addBloodPressure_listener);
        button_addGlycemia.setOnClickListener(button_addGlycemia_listener);
        button_addHeartbeat.setOnClickListener(button_addHeartbeat_listener);
        button_addECG.setOnClickListener(button_addECG_listener);
        button_addSymptoms.setOnClickListener(button_addSymptoms_listener);
        button_addPathology.setOnClickListener(button_addPathology_listener);

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
                    button_addTemperature.setVisibility(View.INVISIBLE);
                    button_addBloodPressure.setVisibility(View.INVISIBLE);
                    button_addGlycemia.setVisibility(View.INVISIBLE);
                    button_addHeartbeat.setVisibility(View.INVISIBLE);
                    button_addECG.setVisibility(View.INVISIBLE);
                    button_addSymptoms.setVisibility(View.INVISIBLE);
                    button_addPathology.setVisibility(View.INVISIBLE);

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

    public View.OnClickListener button_addTemperature_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent sens = new Intent(MedicalRecordsActivity.this, PopUpTemperatureActivity.class);
            startActivity(sens);
        }
    };
    public View.OnClickListener button_addBloodPressure_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent sens = new Intent(MedicalRecordsActivity.this, PopUpTemperatureActivity.class);
            startActivity(sens);
        }
    };
    public View.OnClickListener button_addGlycemia_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent sens = new Intent(MedicalRecordsActivity.this, PopUpTemperatureActivity.class);
            startActivity(sens);
        }
    };
    public View.OnClickListener button_addHeartbeat_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent sens = new Intent(MedicalRecordsActivity.this, PopUpTemperatureActivity.class);
            startActivity(sens);
        }
    };
    public View.OnClickListener button_addECG_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent sens = new Intent(MedicalRecordsActivity.this, PopUpTemperatureActivity.class);
            startActivity(sens);
        }
    };
    public View.OnClickListener button_addSymptoms_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent sens = new Intent(MedicalRecordsActivity.this, SymptomsActivity.class);
            startActivity(sens);
        }
    };
    public View.OnClickListener button_addPathology_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent sens = new Intent(MedicalRecordsActivity.this, PopUpTemperatureActivity.class);
            startActivity(sens);
        }
    };

    public View.OnClickListener image_seeTemperatureStats_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent sens = new Intent(MedicalRecordsActivity.this, HomepageActivity.class);
            startActivity(sens);
        }
    };
    public View.OnClickListener image_seeBloodPressureStats_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent sens = new Intent(MedicalRecordsActivity.this, HomepageActivity.class);
            startActivity(sens);
        }
    };
    public View.OnClickListener image_seeGlycemiaStats_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent sens = new Intent(MedicalRecordsActivity.this, HomepageActivity.class);
            startActivity(sens);
        }
    };
    public View.OnClickListener image_seeHeartbeatStats_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent sens = new Intent(MedicalRecordsActivity.this, HomepageActivity.class);
            startActivity(sens);
        }
    };
    public View.OnClickListener image_seeECGStats_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent sens = new Intent(MedicalRecordsActivity.this, HomepageActivity.class);
            startActivity(sens);
        }
    };
    public View.OnClickListener image_seeSymptomsStats_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent sens = new Intent(MedicalRecordsActivity.this, HomepageActivity.class);
            startActivity(sens);
        }
    };
    public View.OnClickListener image_seePathologyStats_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent sens = new Intent(MedicalRecordsActivity.this, HomepageActivity.class);
            startActivity(sens);
        }
    };
}
