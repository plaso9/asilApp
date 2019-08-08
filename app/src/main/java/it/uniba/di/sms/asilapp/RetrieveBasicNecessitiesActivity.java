package it.uniba.di.sms.asilapp;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
    private ImageView imageMapFood;
    private ImageView imageMapPharmacy;
    private String _acceptance;
    private TextView mAddressFood;
    private TextView mAddressPharmacy;
    private TextView userId;
    private TextView userId2;

    private DatabaseReference mUserReference;
    private DatabaseReference mBasicNecessities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_basic_necessities);

        imageMapFood = findViewById(R.id.imageMapFood);
        imageMapFood.setOnClickListener(image_Map_Food_listener);
        imageMapPharmacy = findViewById(R.id.imageMapPharmacy);
        imageMapPharmacy.setOnClickListener(image_Map_Pharmacy_listener);

        // Initialize FirebaseUser
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        // Get userId
        uId = user.getUid();

        // Initialize Database Reference
        mUserReference = FirebaseDatabase.getInstance().getReference().child("user").child(uId);
        mBasicNecessities = FirebaseDatabase.getInstance().getReference().child("basic_necessities");

        mAddressFood = findViewById(R.id.textViewFoodAddress);
        mAddressPharmacy = findViewById(R.id.textViewPharmacyAddress);
        userId = findViewById(R.id.textViewUserId);
        userId2 = findViewById(R.id.textViewUserId2);
    }

    //Intent on image Map
    public View.OnClickListener image_Map_Food_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Uri uri = Uri.parse("https://www.google.com/maps/search/?api=1&query="+mAddressFood.getText());
            Intent mapIntent = new Intent(android.content.Intent.ACTION_VIEW, uri);
            startActivity(mapIntent);
        }
    };
    //Intent on image Map
    public View.OnClickListener image_Map_Pharmacy_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Uri uri = Uri.parse("https://www.google.com/maps/search/?api=1&query="+mAddressPharmacy.getText());
            Intent mapIntent = new Intent(android.content.Intent.ACTION_VIEW, uri);
            startActivity(mapIntent);
        }
    };

    //function to get basic necessities basically information
    public void getBasicNecessitiesInfo(String acceptanceId) {
        ValueEventListener cityListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                BasicNecessities basic_necessities = dataSnapshot.getValue(BasicNecessities.class);
                mAddressFood.setText(basic_necessities.name);
                mAddressPharmacy.setText(basic_necessities.name);
                userId.setText(uId);
                userId2.setText(uId);

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
