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

import it.uniba.di.sms.asilapp.models.City;

public class CityInfoActivity extends AppCompatActivity {
    //variable declaration
    private static final String TAG = "CityInfoActivity";

    private String uId;
    private String _acceptance;
    private String _city;
    private String cityInfo;

    private TextView mDescription;

    private DatabaseReference mUserReference;
    private DatabaseReference mAcceptanceReference;
    private DatabaseReference mCityReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cityinfo);

        // Initialize FirebaseUser
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        // Get userId
        uId = user.getUid();
        // Initialize Database Reference
        mUserReference = FirebaseDatabase.getInstance().getReference().child("user").child(uId);
        mAcceptanceReference = FirebaseDatabase.getInstance().getReference().child("acceptance");
        mCityReference = FirebaseDatabase.getInstance().getReference().child("city");

        mDescription = findViewById(R.id.textViewCityDescription);
    }

    //function to get city basically information
    public void getCityInformation(String cityId) {
        ValueEventListener cityListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                City city = dataSnapshot.getValue(City.class);
                cityInfo = city.name + ", " + city.description;
                mDescription.setText(cityInfo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "loadCity:onCancelled", databaseError.toException());
                Toast.makeText(CityInfoActivity.this, "Failed to load city",
                        Toast.LENGTH_SHORT).show();
            }
        };
        mCityReference.child(cityId).addValueEventListener(cityListener);
    }

    // function to get foreign key _city from acceptance table
    public void getCityId(String _acceptance) {
        mAcceptanceReference.child(_acceptance).child("_city").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                _city = dataSnapshot.getValue().toString();
                getCityInformation(_city);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Getting City Id failed, log a message
                Log.w(TAG, "loadCityId:onCancelled", databaseError.toException());
                Toast.makeText(CityInfoActivity.this, "Failed to load City Id",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    // function to get foreign key _acceptance from user table
    public void getAcceptanceId() {
        mUserReference.child("_acceptance").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                _acceptance = dataSnapshot.getValue().toString();
                getCityId(_acceptance);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Getting Acceptance Id failed, log a message
                Log.w(TAG, "loadAcceptanceId:onCancelled", databaseError.toException());
                Toast.makeText(CityInfoActivity.this, "Failed to load Acceptance Id",
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
