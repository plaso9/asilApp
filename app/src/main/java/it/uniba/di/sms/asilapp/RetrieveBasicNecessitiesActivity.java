package it.uniba.di.sms.asilapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import it.uniba.di.sms.asilapp.models.BasicNecessities;

public class RetrieveBasicNecessitiesActivity extends AppCompatActivity {
    //variable declaration
    private static final String TAG = "RBasicNecesActivity"; //tag too long
    private String uId;
    private String _acceptance;
    private TextView mAddress;

    private DatabaseReference mUserReference;
    private DatabaseReference mBasicNecessities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_basic_necessities);


        // Initialize FirebaseUser
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        // Get userId
        uId = user.getUid();
        // Initialize Database Reference
        mUserReference = FirebaseDatabase.getInstance().getReference().child("user").child(uId);
        mBasicNecessities = FirebaseDatabase.getInstance().getReference().child("basic_necessities");

        mAddress = findViewById(R.id.textViewFoodAddress);
    }


    //function to get basic necessities basically information
    public void getBasicNecessitiesInfo(String acceptanceId) {
        ValueEventListener cityListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                BasicNecessities basic_necessities = dataSnapshot.getValue(BasicNecessities.class);
                mAddress.setText(basic_necessities.name);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "loadBasicNecessities:onCancelled", databaseError.toException());
                Toast.makeText(RetrieveBasicNecessitiesActivity.this, "Failed to load basic necessities",
                        Toast.LENGTH_SHORT).show();
            }
        };
        mBasicNecessities.child(acceptanceId).addValueEventListener(cityListener);
    }

    // function to get foreign key _acceptance from basic necessities table
    public void getAcceptanceId() {
        mUserReference.child("_acceptance").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                _acceptance = dataSnapshot.getValue().toString();
                getBasicNecessitiesInfo(_acceptance);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Getting Acceptance Id failed, log a message
                Log.w(TAG, "loadAcceptanceId:onCancelled", databaseError.toException());
                Toast.makeText(RetrieveBasicNecessitiesActivity.this, "Failed to load Acceptance Id",
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
