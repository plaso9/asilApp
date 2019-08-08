package it.uniba.di.sms.asilapp;

import android.content.Intent;
import android.support.annotation.NonNull;
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

import it.uniba.di.sms.asilapp.models.Acceptance;

public class AcceptanceActivity extends AppCompatActivity {
    //variable declaration
    private static final String TAG = "AcceptanceActivity";
    private TextView regulation;
    private String uId;
    private String _acceptance;

    private TextView mName;
    private TextView mAddress;

    private DatabaseReference mUserReference;
    private DatabaseReference mAcceptanceReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceptance);

        //Button for norms
        regulation = findViewById(R.id.editTextCenterRegulation);
        regulation.setOnClickListener(editTextCenterRegulation_listener);


        // Initialize FirebaseUser
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        // Get userId
        uId = user.getUid();
        // Initialize Database Reference
        mUserReference = FirebaseDatabase.getInstance().getReference().child("user").child(uId);
        mAcceptanceReference = FirebaseDatabase.getInstance().getReference().child("acceptance");

        mName = findViewById(R.id.editTextCenterName);
        mAddress = findViewById(R.id.editTextCenterLocation);
    }

    // Listener for the button
    public View.OnClickListener editTextCenterRegulation_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent sens = new Intent (AcceptanceActivity.this,InternalRegulationActivity.class);
            startActivity(sens);

        }
    };


    //function to get city basically information
    public void getAcceptanceInfo(String acceptanceId) {
        ValueEventListener cityListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Acceptance acceptance = dataSnapshot.getValue(Acceptance.class);
                mName.setText(acceptance.name);
                mAddress.setText(acceptance.address);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "loadAcceptance:onCancelled", databaseError.toException());
                Toast.makeText(AcceptanceActivity.this, "Failed to load acceptance",
                        Toast.LENGTH_SHORT).show();
            }
        };
        mAcceptanceReference.child(acceptanceId).addValueEventListener(cityListener);
    }

    // function to get foreign key _acceptance from user table
    public void getAcceptanceId() {
        mUserReference.child("_acceptance").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                _acceptance = dataSnapshot.getValue().toString();
                getAcceptanceInfo(_acceptance);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Getting Acceptance Id failed, log a message
                Log.w(TAG, "loadAcceptanceId:onCancelled", databaseError.toException());
                Toast.makeText(AcceptanceActivity.this, "Failed to load Acceptance Id",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        getAcceptanceId();
    }
}
