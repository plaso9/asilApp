package it.uniba.di.sms.asilapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import it.uniba.di.sms.asilapp.models.User;

public class PatientDetailActivity extends AppCompatActivity {
    private static final String TAG = "PatientDetailActivity";

    private String userClickedId;
    private TextView mName;
    private DatabaseReference mUserReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_detail);

        //Get User Clicked Id
        userClickedId = getIntent().getExtras().getString("user_clicked");
        // Initialize Database Reference
        mUserReference = FirebaseDatabase.getInstance().getReference().child("user").child(userClickedId);
        // Defined patient data variable
        mName = findViewById(R.id.text_userNameClicked);

        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get User object and use the values to update the UI
                User user = dataSnapshot.getValue(User.class);
                mName.setText("Profilo di : " + user.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting User failed, log a message
                Log.w(TAG, "loadUser:onCancelled", databaseError.toException());
                Toast.makeText(PatientDetailActivity.this, "Failed to load user information.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        mUserReference.addValueEventListener(userListener);
    }
}
