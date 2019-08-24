package it.uniba.di.sms.asilapp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.GridLayout;

public class AdminActivity extends AppCompatActivity {
    //Variables declaration
    CardView card_view_addFood;
    CardView card_view_addUser;
    CardView card_view_readRatings;
    CardView card_view_addAcceptance;
    FloatingActionButton chatButton;
    GridLayout gridLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {    //Called when the activity is starting.
        super.onCreate(savedInstanceState);
        //Set the activity content from a layout resource.
        setContentView(R.layout.activity_admin);

        //Defined variable
        card_view_addFood = findViewById(R.id.card_addFood);
        card_view_addUser = findViewById(R.id.card_addUser);
        card_view_readRatings = findViewById(R.id.card_readRatings);
        card_view_addAcceptance = findViewById(R.id.card_addAcceptance);
        chatButton = findViewById(R.id.chatBtn);
        gridLayout=findViewById(R.id.gridAdminLayout);

        //Set a click listener on the card objects
        card_view_addAcceptance.setOnClickListener(card_view_addAcceptance_listener);
        card_view_addUser.setOnClickListener(card_view_addUser_listener);
        card_view_addFood.setOnClickListener(card_view_addFood_listener);
        card_view_readRatings.setOnClickListener(card_view_readRatings_listener);
        //Set a click listener on the FloatingActionButton object
        chatButton.setOnClickListener(chatButton_listener);
    }

    //Interface definition for a callback to be invoked when a view is clicked.
    public View.OnClickListener card_view_addAcceptance_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent add_acceptanceIntent = new Intent (AdminActivity.this, AddAcceptanceActivity.class);
            startActivity(add_acceptanceIntent);
        }
    };

    public View.OnClickListener card_view_addUser_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent add_userIntent = new Intent (AdminActivity.this,AddUserActivity.class);
            startActivity(add_userIntent);

        }
    };

    public View.OnClickListener card_view_addFood_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent add_foodIntent = new Intent(AdminActivity.this, AddFoodActivity.class);
            startActivity(add_foodIntent);
        }
    };

    public  View.OnClickListener card_view_readRatings_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent read_ratingsIntent = new Intent(AdminActivity.this, ReadRatingsActivity.class);
            startActivity(read_ratingsIntent);
        }
    };

    public View.OnClickListener chatButton_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent chatIntent = new Intent(AdminActivity.this, ChatActivity.class);
            startActivity(chatIntent);
        }
    };


}
