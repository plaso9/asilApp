package it.uniba.di.sms.asilapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.GridLayout;

public class MyInfoActivity extends AppCompatActivity {
    GridLayout gridLayout;
    CardView card_view_Pathology,
            card_view_RetrieveBasicNecessities,
            card_view_AppDetais,
            card_view_Rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo);

        //defined gridlayout variable
        gridLayout=findViewById(R.id.gridInformativeLayout);

        //defined card variable
        card_view_Pathology = findViewById(R.id.card_pathology);
        card_view_RetrieveBasicNecessities = findViewById(R.id.card_retrieveBasicNecessities);
        card_view_AppDetais = findViewById(R.id.card_appDetails);
        card_view_Rating = findViewById(R.id.card_rating);

        card_view_Pathology.setOnClickListener(card_view_Pathology_listener);
        card_view_RetrieveBasicNecessities.setOnClickListener( card_view_RetrieveBasicNecessities_listener);
        card_view_AppDetais.setOnClickListener(card_view_AppDetais_listener);
        card_view_Rating.setOnClickListener(card_view_Rating_listener);

    }

    public View.OnClickListener card_view_Pathology_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent sens = new Intent (MyInfoActivity.this,MyInfoActivity.class);
            startActivity(sens);

        }
    };
    public View.OnClickListener card_view_RetrieveBasicNecessities_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent sens = new Intent (MyInfoActivity.this,MyInfoActivity.class);
            startActivity(sens);

        }
    };

    public View.OnClickListener card_view_AppDetais_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent sens = new Intent (MyInfoActivity.this,MyInfoActivity.class);
            startActivity(sens);

        }
    };

    public View.OnClickListener card_view_Rating_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent sens = new Intent (MyInfoActivity.this, MyInfoActivity.class);
            startActivity(sens);

        }
    };
}
