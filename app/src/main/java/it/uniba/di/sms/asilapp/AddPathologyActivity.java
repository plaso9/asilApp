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

import java.text.SimpleDateFormat;
import java.util.Date;

import it.uniba.di.sms.asilapp.models.MedicalRecord;
import it.uniba.di.sms.asilapp.models.Pathology;

public class AddPathologyActivity extends AppCompatActivity {
    private static final String TAG  = "AddPathologyActivity";

    private EditText mName;
    private EditText mDescription;
    private EditText mNutritional;
    private EditText mLifestyle;
    private EditText mMedicines;
    private Button submitButton;

    private String user_clicked;
    private String parameter_clicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pathology);

        mName = findViewById(R.id.edit_name_pathology);
        mDescription = findViewById(R.id.edit_description_pathology);
        mNutritional = findViewById(R.id.edit_nutritional_pathology);
        mLifestyle = findViewById(R.id.edit_lifestyle_pathology);
        mMedicines = findViewById(R.id.edit_medicines_pathology);
        submitButton = findViewById(R.id.buttonSavePathology);

        submitButton.setOnClickListener(submitButton_listener);
    }

    public View.OnClickListener submitButton_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            addPathology();
        }
    };

    public void addPathology(){
        user_clicked = getIntent().getExtras().getString("user_clicked");
        parameter_clicked = getIntent().getExtras().getString("_parameter");

        //Get value to insert in DB
        String name = mName.getText().toString();
        String description = mDescription.getText().toString();
        String nutritional = mNutritional.getText().toString();
        String lifestyle = mLifestyle.getText().toString();
        String medicine = mMedicines.getText().toString();

        //Get id of pathology pushed
        String pathology_id = FirebaseDatabase.getInstance().getReference("acceptance").push().getKey();

        //New Constructor pathology
        Pathology pathology = new Pathology(
                name,
                description,
                nutritional,
                lifestyle,
                medicine
        );

        FirebaseDatabase.getInstance().getReference("pathology").child(pathology_id).setValue(pathology);
        //Get current data and set format
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String current_data = formatter.format(date);
        //New Constructor medical record
        MedicalRecord medicalRecord = new MedicalRecord(
                name,
                user_clicked,
                parameter_clicked,
                current_data,
                pathology_id
        );

        //Adding value to DB
        FirebaseDatabase.getInstance().getReference().child("medical_records").push().setValue(medicalRecord).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    //success message
                    Toast.makeText(AddPathologyActivity.this, "Addedd successfully", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    //failure message
                    Toast.makeText(AddPathologyActivity.this, "Addedd failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
