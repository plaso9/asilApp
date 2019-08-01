package it.uniba.di.sms.asilapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;

public class MedicalRecordsActivity extends AppCompatActivity {

    ImageView image_graphic;
    ImageView image_graphic2;
    ImageView image_graphic3;
    ImageView image_graphic4;
    ImageView image_graphic5;
    ImageView image_graphic6;
    ImageView image_graphic7;
    Button add_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_records);

        image_graphic = findViewById(R.id.image_graphic);
        image_graphic2 = findViewById(R.id.image_graphic2);
        image_graphic3 = findViewById(R.id.image_graphic3);
        image_graphic4 = findViewById(R.id.image_graphic4);
        image_graphic5 = findViewById(R.id.image_graphic5);
        image_graphic6 = findViewById(R.id.image_graphic6);
        image_graphic7 = findViewById(R.id.image_graphic7);

        add_button = findViewById(R.id.buttonAdd);


        image_graphic.setOnClickListener(image_graphic_listener);
        image_graphic2.setOnClickListener(image_graphic_listener2);
        image_graphic3.setOnClickListener(image_graphic_listener3);
        image_graphic4.setOnClickListener(image_graphic_listener4);
        image_graphic5.setOnClickListener(image_graphic_listener5);
        image_graphic6.setOnClickListener(image_graphic_listener6);
        image_graphic7.setOnClickListener(image_graphic_listener7);

        add_button.setOnClickListener(add_button_listener);



    }

    public View.OnClickListener add_button_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent sens = new Intent(MedicalRecordsActivity.this, PopUpTemperatureActivity.class);
            startActivity(sens);
        }
    };

    public View.OnClickListener image_graphic_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent sens = new Intent(MedicalRecordsActivity.this, HomepageActivity.class);
            startActivity(sens);
        }
    };
    public View.OnClickListener image_graphic_listener2 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent sens = new Intent(MedicalRecordsActivity.this, HomepageActivity.class);
            startActivity(sens);
        }
    };
    public View.OnClickListener image_graphic_listener3 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent sens = new Intent(MedicalRecordsActivity.this, HomepageActivity.class);
            startActivity(sens);
        }
    };
    public View.OnClickListener image_graphic_listener4 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent sens = new Intent(MedicalRecordsActivity.this, HomepageActivity.class);
            startActivity(sens);
        }
    };
    public View.OnClickListener image_graphic_listener5 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent sens = new Intent(MedicalRecordsActivity.this, HomepageActivity.class);
            startActivity(sens);
        }
    };
    public View.OnClickListener image_graphic_listener6 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent sens = new Intent(MedicalRecordsActivity.this, SymptomsListActivity.class);
            startActivity(sens);
        }
    };
    public View.OnClickListener image_graphic_listener7 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent sens = new Intent(MedicalRecordsActivity.this, PathologyListActivity.class);
            startActivity(sens);
        }
    };
}
