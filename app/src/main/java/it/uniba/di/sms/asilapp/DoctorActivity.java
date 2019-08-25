package it.uniba.di.sms.asilapp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.GridLayout;

public class DoctorActivity extends AppCompatActivity {
    //Variable declaration
    CardView card_view_kitOpening;
    CardView card_view_patientList;
    CardView card_view_searchPatient;
    FloatingActionButton chatButton;
    GridLayout gridLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set the activity content from a layout resource.
        setContentView(R.layout.activity_doctor);

        //defined gridlayout variable
        gridLayout=findViewById(R.id.gridDoctorLayout);
        //defined card variable
        card_view_kitOpening = findViewById(R.id.card_kitOpening);
        card_view_patientList = findViewById(R.id.card_patientList);
        card_view_searchPatient = findViewById(R.id.card_searchPatient);
        //Defined FloatingActionButton
        chatButton = findViewById(R.id.chatBtn);

        //Set a click listener on the card objects
        card_view_searchPatient.setOnClickListener(card_view_searchPatient_listener);
        card_view_kitOpening.setOnClickListener(card_view_kitOpening_listener);
        card_view_patientList.setOnClickListener(card_view_patientList_listener);
        //Set a click listener on the FloatingActionButton object
        chatButton.setOnClickListener(chatButton_listener);
    }

    public View.OnClickListener card_view_searchPatient_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent search_patientIntent = new Intent (DoctorActivity.this,SearchPatientActivity.class);
            startActivity(search_patientIntent);

        }
    };
    public View.OnClickListener card_view_kitOpening_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent open_kitIntent = new Intent (DoctorActivity.this,KitOpeningActivity.class);
            startActivity(open_kitIntent);

        }
    };

    public View.OnClickListener card_view_patientList_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent open_PatientListIntent = new Intent (DoctorActivity.this,PatientListActivity.class);
            startActivity(open_PatientListIntent);

        }
    };

    public View.OnClickListener chatButton_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent chatIntent = new Intent(DoctorActivity.this, ChatActivity.class);
            startActivity(chatIntent);
        }
    };

}
