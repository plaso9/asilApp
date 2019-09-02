package it.uniba.di.sms.asilapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
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

import it.uniba.di.sms.asilapp.models.Acceptance;
import it.uniba.di.sms.asilapp.models.City;

public class PopUpShowMoreActivity extends AppCompatActivity {
    //variable declaration
    private static final String TAG = "CityInfoActivity";

    private String uId;
    private String cityInfo;
    private String cityName;
    private String _acceptance;

    private TextView mDescription;

    private DatabaseReference mUserReference;
    private DatabaseReference mCityReference;
    private DatabaseReference mAcceptanceReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set the activity content from a layout resource.
        setContentView(R.layout.activity_pop_up_show_more);

        //Defined variables
        mDescription = findViewById(R.id.textViewDescription);

        //Set window dimentions
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * 0.8), (int) (height * 0.6));


        // Initialize FirebaseUser
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        // Get userId
        uId = user.getUid();
        // Initialize Database Reference
        mUserReference = FirebaseDatabase.getInstance().getReference("user").child(uId);
        mAcceptanceReference = FirebaseDatabase.getInstance().getReference("acceptance");
        mCityReference = FirebaseDatabase.getInstance().getReference("city");
    }


    //Method that retrieves from the Firebase Database the infos of the city given the iD of the city and sets the text of the TextView
    public void getCityInformation(final long cityId) {
        ValueEventListener cityListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    City city = snapshot.getValue(City.class);
                    if (city.id == cityId) {
                        cityName = city.name;
                        cityInfo = cityName + " - " + city.description;
                        mDescription.setText(cityInfo);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting city failed
                Log.w(TAG, "loadCity:onCancelled", databaseError.toException());
                Toast.makeText(PopUpShowMoreActivity.this, "Failed to load city",
                        Toast.LENGTH_SHORT).show();
            }
        };
        mCityReference.addValueEventListener(cityListener);
    }

    //Method that retrieves from the Firebase Database the iD of the city given the iD of the acceptance
    public void getCityId(String _acceptance) {
        mAcceptanceReference.child(_acceptance).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Acceptance acceptance = dataSnapshot.getValue(Acceptance.class);
                getCityInformation(acceptance.getCity());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Getting City Id failed, log a message
                Log.w(TAG, "loadCityId:onCancelled", databaseError.toException());
                Toast.makeText(PopUpShowMoreActivity.this, "Failed to load City Id",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Method that retrieves the acceptanceId in which the user is hosted from the Firebase Database
    public void getAcceptanceId() {
        mUserReference.child("acceptanceId").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                _acceptance = dataSnapshot.getValue().toString();
                getCityId(_acceptance);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Getting Acceptance Id failed, log a message
                Log.w(TAG, "loadAcceptanceId:onCancelled", databaseError.toException());
                Toast.makeText(PopUpShowMoreActivity.this, "Failed to load Acceptance Id",
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
