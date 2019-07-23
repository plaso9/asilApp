package it.uniba.di.sms.asilapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.GridLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    GridLayout gridLayout;
    CardView card_view_PersonalData;
    CardView card_view_Informative;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //defined gridlayout variable
        gridLayout=findViewById(R.id.gridHomeLayout);
        //defined card variable
        card_view_PersonalData = findViewById(R.id.card_personalData);
        card_view_Informative = findViewById(R.id.card_informationSection);


        //set function to card
        card_view_PersonalData.setOnClickListener(this);
        card_view_Informative.setOnClickListener(this);
    }

    @Override
    //function called at card onclick
    public void onClick(View v) {
        Intent i;

        switch(v.getId()) {
            case R.id.card_personalData : i = new Intent(this, PersonalDataActivity.class); startActivity(i); break;
            case R.id.card_informationSection : i = new Intent(this, InformativeActivity.class); startActivity(i); break;
            default: break;
        }
    }
}
