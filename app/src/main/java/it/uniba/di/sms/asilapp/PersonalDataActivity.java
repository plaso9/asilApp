package it.uniba.di.sms.asilapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import it.uniba.di.sms.asilapp.models.User;


public class PersonalDataActivity extends AppCompatActivity {
    private static final String TAG = "PersonalDataActivity";
    private String uId;
    private EditText mName;
    private EditText mSurname;
    private EditText mDateOfBirth;
    private EditText mBirthPlace;
    private EditText mEmail;
    private EditText mCell;
    private EditText mGender;
    private DatabaseReference mUserReference;
    private Button mSavePersonalData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);

        // Initialize FirebaseUser
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        // Get userId
        uId = user.getUid();
        // Initialize Database Reference
        mUserReference = FirebaseDatabase.getInstance().getReference()
                .child("user").child(uId);

        // Defined personal data variable
        mName = findViewById(R.id.editTextName);
        mSurname = findViewById(R.id.editTextSurname);
        mDateOfBirth = findViewById(R.id.editTextBirthday);
        mBirthPlace = findViewById(R.id.editTextBirthPlace);
        mEmail = findViewById(R.id.editTextEmail);
        mEmail.setText(user.getEmail());
        mCell = findViewById(R.id.editTextCell);
        mGender = findViewById(R.id.editTextGender);
        mSavePersonalData = findViewById(R.id.buttonSavePersonalData);
        mSavePersonalData.setOnClickListener(save_data_listener);

        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get User object and use the values to update the UI
                User user = dataSnapshot.getValue(User.class);
                mName.setText(user.name);
                mSurname.setText(user.surname);
                mDateOfBirth.setText(user.date_of_birth);
                mBirthPlace.setText(user.birth_place);
                mCell.setText(user.cell);
                mGender.setText(user.gender);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting User failed, log a message
                Log.w(TAG, "loadUser:onCancelled", databaseError.toException());
                Toast.makeText(PersonalDataActivity.this, "Failed to load user.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        mUserReference.addListenerForSingleValueEvent(userListener);


    }

    public View.OnClickListener save_data_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String newCell = mCell.getText().toString();
            if (!newCell.equals("")){
                mUserReference.child("cell").setValue(newCell);
            } else {
                //Show Toast
            }
        }
    };
}
