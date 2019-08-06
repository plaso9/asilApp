package it.uniba.di.sms.asilapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.GridLayout;

public class InformativeActivity extends AppCompatActivity{
    GridLayout gridLayout;

    CardView card_view_Video,
            card_view_Acceptance,
            card_view_CityInfo,
            card_view_Bylaw,
            card_view_UsefulNumbers,
            card_view_MyInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informative);

        //defined gridlayout variable
        gridLayout=findViewById(R.id.gridInformativeLayout);

        //defined card variable
        card_view_Video = findViewById(R.id.card_video);
        card_view_Acceptance = findViewById(R.id.card_acceptance);
        card_view_CityInfo = findViewById(R.id.city_info);
        card_view_Bylaw = findViewById(R.id.card_bylaw);
        card_view_UsefulNumbers = findViewById(R.id.card_usefulNumbers);
        card_view_MyInfo = findViewById(R.id.card_myInfo);

        card_view_Video.setOnClickListener(card_view_Video_listener);
        card_view_Acceptance.setOnClickListener(card_view_Acceptance_listener);
        card_view_CityInfo.setOnClickListener(card_view_CityInfo_listener);
        card_view_Bylaw.setOnClickListener(card_view_Bylaw_listener);
        card_view_UsefulNumbers.setOnClickListener(card_view_UsefulNumbers_listener);
        card_view_MyInfo.setOnClickListener(card_view_MyInfo_listener);


    }

    public View.OnClickListener card_view_Video_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent sens = new Intent (InformativeActivity.this,TestActivity.class);
            startActivity(sens);

        }
    };
    public View.OnClickListener card_view_Acceptance_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent sens = new Intent (InformativeActivity.this,AcceptanceActivity.class);
            startActivity(sens);

        }
    };

    public View.OnClickListener card_view_CityInfo_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent sens = new Intent (InformativeActivity.this,CityInfoActivity.class);
            startActivity(sens);

        }
    };

    public View.OnClickListener card_view_Bylaw_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent sens = new Intent (InformativeActivity.this,BylawActivity.class);
            startActivity(sens);

        }
    };

    public View.OnClickListener card_view_UsefulNumbers_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent sens = new Intent (InformativeActivity.this,UsefulNumbersActivity.class);
            startActivity(sens);

        }
    };

    public View.OnClickListener card_view_MyInfo_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent sens = new Intent (InformativeActivity.this,MyInfoActivity.class);
            startActivity(sens);

        }
    };


}

