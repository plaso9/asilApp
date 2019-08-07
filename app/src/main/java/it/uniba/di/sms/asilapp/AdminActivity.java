package it.uniba.di.sms.asilapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.GridLayout;

public class AdminActivity extends AppCompatActivity {
    GridLayout gridLayout;
    CardView card_view_addUser;
    CardView card_view_addAcceptance;
    CardView card_view_addFood;
    CardView card_view_readRatings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        //defined gridlayout variable
        gridLayout=findViewById(R.id.gridAdminLayout);
        //defined card variable
        card_view_addUser = findViewById(R.id.card_addUser);
        card_view_addAcceptance = findViewById(R.id.card_addAcceptance);
        card_view_addFood = findViewById(R.id.card_addFood);
        card_view_readRatings = findViewById(R.id.card_readRatings);



        //set function to card
        card_view_addAcceptance.setOnClickListener(card_view_addAcceptance_listener);
        card_view_addUser.setOnClickListener(card_view_addUser_listener);
        card_view_addFood.setOnClickListener(card_view_addFood_listener);
        card_view_readRatings.setOnClickListener(card_view_readRatings_listener);


    }

    public View.OnClickListener card_view_addAcceptance_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent sens = new Intent (AdminActivity.this,NewAcceptanceActivity.class);
            startActivity(sens);

        }
    };

    public View.OnClickListener card_view_addUser_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent sens = new Intent (AdminActivity.this,AddUserActivity.class);
            startActivity(sens);

        }
    };

    public View.OnClickListener card_view_addFood_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(AdminActivity.this, AdminActivity.class);
            startActivity(i);
        }
    };

    public  View.OnClickListener card_view_readRatings_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(AdminActivity.this, AdminActivity.class);
            startActivity(intent);
        }
    };


}
