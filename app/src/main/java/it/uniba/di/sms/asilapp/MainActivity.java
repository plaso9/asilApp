package it.uniba.di.sms.asilapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //start LoginActivity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class); //New intent, redirect from MainActivity to LoginActivity
        startActivity(intent);
    }

}
