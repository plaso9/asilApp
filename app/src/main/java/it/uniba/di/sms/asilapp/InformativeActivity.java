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
        setContentView(R.layout.activity_main);

        //defined gridlayout variable
        gridLayout=findViewById(R.id.gridInformativeLayout);

        //defined card variable
        card_view_Video = findViewById(R.id.card_video);
        card_view_Acceptance = findViewById(R.id.card_acceptance);
        card_view_CityInfo = findViewById(R.id.city_info);
        card_view_Bylaw = findViewById(R.id.card_bylaw);
        card_view_UsefulNumbers = findViewById(R.id.card_usefulNumbers);
        card_view_MyInfo = findViewById(R.id.card_myInfo);

        //set function to card
      /*  card_view_Video.setOnClickListener(this);
        card_view_Acceptance.setOnClickListener(this);
        card_view_CityInfo.setOnClickListener(this);
        card_view_Bylaw.setOnClickListener(this);
        card_view_UsefulNumbers.setOnClickListener(this);
        card_view_MyInfo.setOnClickListener(this);

    }

    @Override
    //function called at card onclick
    public void onClick(View v) {
        Intent i;

        switch(v.getId()) {
            case R.id.card_video : i = new Intent(this, VideoActivity.class); startActivity(i); break;
            case R.id.card_acceptance : i = new Intent(this, AcceptanceActivity.class); startActivity(i); break;
            case R.id.city_info: i = new Intent(this, CityInfoActivity.class); startActivity(i); break;
            case R.id.card_bylaw : i = new Intent(this, BylawActivity.class); startActivity(i); break;
            case R.id.card_usefulNumbers : i = new Intent(this, UsefulNumbersActivity.class); startActivity(i); break;
            case R.id.card_myInfo : i = new Intent(this, MyInfoActivity.class); startActivity(i); break;

            default: break;
        }
        */
    }
}
