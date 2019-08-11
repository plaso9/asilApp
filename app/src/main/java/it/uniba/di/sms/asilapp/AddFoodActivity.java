package it.uniba.di.sms.asilapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import it.uniba.di.sms.asilapp.models.Necessities;


public class AddFoodActivity extends AppCompatActivity {
    private static final String TAG = "PopUpTempActivity";
    //Edit text for necessities value
    private EditText mCity;
    private EditText mMall;
    private EditText mPharmacy;
    //Button to submit necessities value
    private Button submitNecessities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        //find R.id from xml
        mCity = findViewById(R.id.editTextCity);
        mMall = findViewById(R.id.editTextMallLocation);
        mPharmacy = findViewById(R.id.editTextPharmacyLocation);
        submitNecessities = findViewById(R.id.btnSubmit);

        //Set listener value variable
        submitNecessities.setOnClickListener(submitNecessities_listener);
    }

    //Set on click listener submit button
    public View.OnClickListener submitNecessities_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (mCity.getText().toString().equals("") ||
                mMall.getText().toString().equals("") ||
                mPharmacy.getText().toString().equals("")
                ) {
                Toast.makeText(AddFoodActivity.this, "No field should be empty", Toast.LENGTH_SHORT).show();
            } else {
                addNewRetrieveNecessities();
            }
        }
    };

    public void addNewRetrieveNecessities(){
        //Get value to insert in DB
        final String city = mCity.getText().toString();
        final String mall = mMall.getText().toString();
        final String pharmacy = mPharmacy.getText().toString();

        //New Constructor
        Necessities necessities = new Necessities(
                city,
                mall,
                pharmacy
        );

        //Adding value to DB
        FirebaseDatabase.getInstance().getReference().child("basic_necessities").push().setValue(necessities).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    //success message
                    Toast.makeText(AddFoodActivity.this, "Addedd successfully", Toast.LENGTH_LONG).show();
                    goToPreviousIntent();
                } else {
                    //failure message
                    Toast.makeText(AddFoodActivity.this, "Addedd failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void goToPreviousIntent(){
        Intent medicalRecordsIntent = new Intent (AddFoodActivity.this,AdminActivity.class);
        startActivity(medicalRecordsIntent);
    }
}
