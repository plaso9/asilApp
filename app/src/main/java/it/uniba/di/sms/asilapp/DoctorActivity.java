package it.uniba.di.sms.asilapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.GridLayout;

public class DoctorActivity extends AppCompatActivity {
    GridLayout gridLayout;
    CardView  card_view_searchPatient,
            card_view_kitOpening,
            card_view_patientList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        //defined gridlayout variable
        gridLayout=findViewById(R.id.gridDoctorLayout);

        //defined card variable
        card_view_searchPatient = findViewById(R.id.card_searchPatient);
        card_view_kitOpening = findViewById(R.id.card_kitOpening);
        card_view_patientList = findViewById(R.id.card_patientList);

        card_view_searchPatient.setOnClickListener(card_view_searchPatient_listener);
        card_view_kitOpening.setOnClickListener(card_view_kitOpening_listener);
        card_view_patientList.setOnClickListener(card_view_patientList_listener);

    }

    public View.OnClickListener card_view_searchPatient_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent sens = new Intent (DoctorActivity.this,SearchPatientActivity.class);
            startActivity(sens);

        }
    };
    public View.OnClickListener card_view_kitOpening_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent sens = new Intent (DoctorActivity.this,KitOpeningActivity.class);
            startActivity(sens);

        }
    };

    public View.OnClickListener card_view_patientList_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent sens = new Intent (DoctorActivity.this,PatientListActivity.class);
            startActivity(sens);

        }
    };

}
