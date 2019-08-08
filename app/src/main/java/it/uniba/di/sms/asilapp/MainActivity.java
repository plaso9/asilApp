package it.uniba.di.sms.asilapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{
    private TextView goHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goHome = findViewById(R.id.textViewHome);
        goHome.setOnClickListener(goHome_listener);
        //start LoginActivity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class); //New intent, redirect from MainActivity to LoginActivity
        startActivity(intent);
    }


    //click goHome redirects to Login
    public View.OnClickListener goHome_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent sens = new Intent (MainActivity.this,LoginActivity.class);
            startActivity(sens);

        }
    };

}
