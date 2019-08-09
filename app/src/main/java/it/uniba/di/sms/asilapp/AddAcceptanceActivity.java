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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import it.uniba.di.sms.asilapp.models.Acceptance;

public class AddAcceptanceActivity extends AppCompatActivity {
    private Button selectFile;
    private int PICK_PDF_CODE =0;

    private EditText eTcenterName;
    private EditText eTcenterLocation;
    private EditText eTcenterServices;
    private Button buttonSubmitAcceptance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_acceptance);

        eTcenterName = findViewById(R.id.editTextCenterName);
        eTcenterLocation = findViewById(R.id.editTextCenterLocation);
        eTcenterServices = findViewById(R.id.editTextCenterServices);
        buttonSubmitAcceptance = findViewById(R.id.btnSubmitAcceptance);

        selectFile = findViewById(R.id.btnCenterRegulation);
        selectFile.setOnClickListener(selectFile_listener);

        buttonSubmitAcceptance.setOnClickListener(btnSubmitAcceptance_listener);

    }

    //Open filePicker and choose a pdf-file
    public View.OnClickListener selectFile_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String title= String.valueOf(R.string.chooseFile);
            Intent intentPDF = new Intent(Intent.ACTION_GET_CONTENT);
            intentPDF.setType("application/pdf");
            intentPDF.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(Intent.createChooser(intentPDF ,  title), PICK_PDF_CODE);

        }
    };

    public View.OnClickListener btnSubmitAcceptance_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (eTcenterName.getText().toString().equals("") ||
                    eTcenterLocation.getText().toString().equals("") ||
                    eTcenterServices.getText().toString().equals("")){
                Toast.makeText(AddAcceptanceActivity.this, "No field should be empty", Toast.LENGTH_LONG).show();

            } else {
                addNewAcceptance();
            }
        }
    };

    private void addNewAcceptance() {
        final String nameCenter = eTcenterName.getText().toString();
        final String locationCenter = eTcenterLocation.getText().toString();
        final String centerServices = eTcenterServices.getText().toString();

        String[] services = centerServices.split(",");
        ArrayList<String> listOfServices = new ArrayList<String>();
        for (int i = 0; i<services.length; i++){
            listOfServices.add(services[i]);
        }

        Acceptance acceptance = new Acceptance(
                nameCenter,
                locationCenter,
                listOfServices
        );

        FirebaseDatabase.getInstance().getReference().child("acceptance").push().setValue(acceptance).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(AddAcceptanceActivity.this, "Addedd successfully", Toast.LENGTH_LONG).show();
                } else {
                    //add a failure message
                }
            }
        });
    }
}
