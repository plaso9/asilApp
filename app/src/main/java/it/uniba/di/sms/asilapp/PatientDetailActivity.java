package it.uniba.di.sms.asilapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
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
    //Define variable
    private String user_id;
    private TextView mName;
    private DatabaseReference mUserReference;

    //Define cards
    CardView card_view_personalData;
    CardView card_view_medicalRecords;
    CardView card_view_questionnaires;

    int PROGRESS_BAR_STATUS=0;
    ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_detail);

        //Get User Clicked Id
        if (getIntent().getExtras() != null) {
            user_id = getIntent().getStringExtra("user_clicked");
        }
        // Initialize Database Reference
        mUserReference = FirebaseDatabase.getInstance().getReference("user").child(user_id);
        // Defined patient data variable
        mName = findViewById(R.id.text_userNameClicked);
        //defined card variable
        card_view_personalData = findViewById(R.id.card_personalData);
        card_view_medicalRecords = findViewById(R.id.card_medicalRecords);
        card_view_questionnaires = findViewById(R.id.card_questionnaires);

        //set function to card
        card_view_personalData.setOnClickListener(card_view_Personaldata_listener);
        card_view_medicalRecords.setOnClickListener(card_view_MedicalRecords_listener);
        card_view_questionnaires.setOnClickListener(card_view_Questionnaries_listener);


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

    //Open Personal Data Intent
    public View.OnClickListener card_view_Personaldata_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            progressBar = new ProgressDialog(PatientDetailActivity.this);
            progressBar.setIndeterminate(true);
            progressBar.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressBar.show();
            Intent personalDataIntent = new Intent (PatientDetailActivity.this,PersonalDataActivity.class);
            personalDataIntent.putExtra("user_clicked", user_id);
            startActivity(personalDataIntent);

            PROGRESS_BAR_STATUS=1;
        }
    };
    //Open Questionnaires Intent
    public View.OnClickListener card_view_Questionnaries_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent questionnairesIntent = new Intent(PatientDetailActivity.this, QuestionnairesActivity.class);
            questionnairesIntent.putExtra("user_clicked", user_id);
            startActivity(questionnairesIntent);
        }
    };
    //Open Medical Records Intent
    public  View.OnClickListener card_view_MedicalRecords_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent medicalRecordsIntent = new Intent(PatientDetailActivity.this, MedicalRecordsActivity.class);
            medicalRecordsIntent .putExtra("user_clicked", user_id);
            startActivity(medicalRecordsIntent );
        }
    };

    @Override
    protected void onStart(){
        super.onStart();
        afterExecution();
    }


    public void afterExecution(){
        if (PROGRESS_BAR_STATUS == 1){
            progressBar.dismiss();
            PROGRESS_BAR_STATUS=0;
        }
    }
}
