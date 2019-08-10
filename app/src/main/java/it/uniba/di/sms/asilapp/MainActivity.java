package it.uniba.di.sms.asilapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import it.uniba.di.sms.asilapp.models.User;

public class MainActivity extends AppCompatActivity{
    private static final String TAG = "MainActivity";

    private TextView goHome;
    private DatabaseReference mUserReference;
    private String uId;
    private int mRole;

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

    //click goHome redirects to Correct Intent
    public View.OnClickListener goHome_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            onLoadCurrentUserRole();
        }
    };

    public void onLoadCurrentUserRole(){
        //Get Current User
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //Get userId
        uId = user.getUid();
        mUserReference = FirebaseDatabase.getInstance().getReference()
                .child("user").child(uId);

        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get User object
                User user = dataSnapshot.getValue(User.class);
                mRole = user.getRole();
                getRoleActivity(mRole);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting User Role failed, log a message
                Log.w(TAG, "loadUser:onCancelled", databaseError.toException());
                Toast.makeText(MainActivity.this, "Failed to load user.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        mUserReference.addValueEventListener(userListener);
    }

    public void getRoleActivity(int role_id) {
        if(role_id == 1){
            //Admin Role
            Intent intent = new Intent(MainActivity.this, AdminActivity.class);
            startActivity(intent);
            finish();
        } else if (role_id == 2) {
            //User Role
            Intent intent = new Intent(MainActivity.this, HomepageActivity.class);
            startActivity(intent);
            finish();
        } else if (role_id == 3) {
            //Doctor Role
            Intent intent = new Intent(MainActivity.this, DoctorActivity.class);
            startActivity(intent);
            finish();
        }
    }

}
