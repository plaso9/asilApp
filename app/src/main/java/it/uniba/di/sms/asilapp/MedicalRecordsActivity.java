package it.uniba.di.sms.asilapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;

public class MedicalRecordsActivity extends AppCompatActivity {

    ImageView image_seeTemperatureStats;
    ImageView image_seeBloodPressureStats;
    ImageView image_seeGlycemiaStats;
    ImageView image_seeHeartbeatStats;
    ImageView image_seeECGStats;
    ImageView image_seeSymptomsStats;
    ImageView image_SeePathologyStats;
    Button add_button;


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
        image_SeePathologyStats = findViewById(R.id.image_SeePathologyStats);

        add_button = findViewById(R.id.buttonAddTemperature);


        image_seeTemperatureStats.setOnClickListener(image_seeTemperatureStats_listener);
        image_seeBloodPressureStats.setOnClickListener(image_seeBloodPressureStats_listener);
        image_seeGlycemiaStats.setOnClickListener(image_seeGlycemiaStats_listener);
        image_seeHeartbeatStats.setOnClickListener(image_seeHeartbeatStats_listener);
        image_seeECGStats.setOnClickListener(image_seeECGStats_listener);
        image_seeSymptomsStats.setOnClickListener(image_seeSymptomsStats_listener);
        image_SeePathologyStats.setOnClickListener(image_SeePathologyStats_listener);

        add_button.setOnClickListener(add_button_listener);



    }

    public View.OnClickListener add_button_listener = new View.OnClickListener() {
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
    public View.OnClickListener image_SeePathologyStats_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent sens = new Intent(MedicalRecordsActivity.this, HomepageActivity.class);
            startActivity(sens);
        }
    };
}
