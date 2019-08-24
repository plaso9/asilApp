package it.uniba.di.sms.asilapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

import it.uniba.di.sms.asilapp.models.Parameter;
import it.uniba.di.sms.asilapp.models.MedicalRecord;

public class PopUpTemperatureActivity extends AppCompatActivity {
    private static final String TAG = "PopUpTempActivity";

    private String user_clicked_id;
    private String parameter_clicked_id;
    //Edit text for measurement value
    private EditText editValueParameter;
    //Button to submit measurement value
    private Button submitMeasurement;
    //TextView of xml
    private TextView mName;
    //Variable DB
    private DatabaseReference mParameterReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_temperature);

        //Get value from previous intent
        if (getIntent().getExtras() != null) {
            user_clicked_id = getIntent().getExtras().getString("user_clicked");
            parameter_clicked_id = getIntent().getExtras().getString("_parameter");
        }
        //find R.id from xml
        editValueParameter = findViewById(R.id.editValueParameter);
        submitMeasurement = findViewById(R.id.buttonSaveLanguage);

        //Set listener value variable
        submitMeasurement.setOnClickListener(submitMeasurement_listener);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.8), (int)(height*0.6));

    }

    @Override
    protected void onStart(){
        super.onStart();

        // Initialize Database Reference
        mParameterReference = FirebaseDatabase.getInstance().getReference()
                .child("parameter").child(parameter_clicked_id);

        // Defined parameter variable
        mName = findViewById(R.id.nameParameter);

        ValueEventListener parameterListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Parameter object and use the values to update the UI
                Parameter parameter = dataSnapshot.getValue(Parameter.class);
                mName.setText(parameter.name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting User failed, log a message
                Log.w(TAG, "loadParameter:onCancelled", databaseError.toException());
                Toast.makeText(PopUpTemperatureActivity.this, "Failed to load parameter.",
                        Toast.LENGTH_LONG).show();
            }
        };
        mParameterReference.addListenerForSingleValueEvent(parameterListener);
    }

    //Set on click listener submit button
    public View.OnClickListener submitMeasurement_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (editValueParameter.getText().toString().equals("")){
                Toast.makeText(PopUpTemperatureActivity.this, "No field should be empty", Toast.LENGTH_LONG).show();
            } else {
                addNewMeasurement();
            }
        }
    };

    //Function to add Measurement
    public void addNewMeasurement() {
        //Get value to insert in DB
        String value = editValueParameter.getText().toString();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String current_data = formatter.format(date);
        String pathology = "";

        //New Constructor
        MedicalRecord medicalRecord = new MedicalRecord(
            value,
            user_clicked_id,
            parameter_clicked_id,
            current_data,
            pathology
        );

        //Adding value to DB
        FirebaseDatabase.getInstance().getReference().child("medical_records").push().setValue(medicalRecord).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    //success message
                    Toast.makeText(PopUpTemperatureActivity.this, "Addedd successfully", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    //failure message
                    Toast.makeText(PopUpTemperatureActivity.this, "Addedd failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
