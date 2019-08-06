package it.uniba.di.sms.asilapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ProgressBar;

public class HomepageActivity extends AppCompatActivity {
    int REQUEST_CODE=0;
    GridLayout gridLayout;
    CardView card_view_PersonalData;
    CardView card_view_Informative;
    CardView card_view_Questionnaires;
    CardView card_view_MedicalRecords;
    ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        //defined gridlayout variable
        gridLayout=findViewById(R.id.gridHomeLayout);
        //defined card variable
        card_view_PersonalData = findViewById(R.id.card_personalData);
        card_view_Informative = findViewById(R.id.card_informationSection);
        card_view_Questionnaires = findViewById(R.id.card_questionnaires);
        card_view_MedicalRecords = findViewById(R.id.card_medicalRecords);



        //set function to card
        card_view_Informative.setOnClickListener(card_view_Informative_listener);
        card_view_PersonalData.setOnClickListener(card_view_Personaldata_listener);
        card_view_Questionnaires.setOnClickListener(card_view_Questionnaries_listener);
        card_view_MedicalRecords.setOnClickListener(card_view_MedicalRecords_listener);
    }

    @Override
    protected void onStart(){
        super.onStart();
        afterExecution();
    }

    public View.OnClickListener card_view_Informative_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent sens = new Intent (HomepageActivity.this,InformativeActivity.class);
            startActivity(sens);
        }
    };

    public View.OnClickListener card_view_Personaldata_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            progressBar = new ProgressDialog(HomepageActivity.this);
            progressBar.setIndeterminate(true);
            progressBar.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressBar.show();
            Intent sens = new Intent (HomepageActivity.this,PersonalDataActivity.class);
            startActivity(sens);
            REQUEST_CODE=1;
        }
    };

    public View.OnClickListener card_view_Questionnaries_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(HomepageActivity.this, QuestionnairesActivity.class);
            startActivity(i);
        }
    };

    public  View.OnClickListener card_view_MedicalRecords_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(HomepageActivity.this, MedicalRecordsActivity.class);
            startActivity(intent);
        }
    };

   public void afterExecution(){
       if (REQUEST_CODE == 1){
           progressBar.dismiss();
           REQUEST_CODE=0;
       }
   }

}
