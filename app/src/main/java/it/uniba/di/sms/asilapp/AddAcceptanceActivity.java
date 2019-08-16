package it.uniba.di.sms.asilapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import it.uniba.di.sms.asilapp.models.Acceptance;
import it.uniba.di.sms.asilapp.models.City;

public class AddAcceptanceActivity extends AppCompatActivity {
    private Button selectFile;
    private int PICK_PDF_CODE =0;
    private long idCity;
    private EditText eTcenterName;
    private EditText eTcenterLocation;
    private EditText eTcenterServices;
    private Spinner spinnerCity;
    private Button buttonSubmitAcceptance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_acceptance);

        eTcenterName = findViewById(R.id.editTextCenterName);
        eTcenterLocation = findViewById(R.id.editTextCenterLocation);
        eTcenterServices = findViewById(R.id.editTextCenterServices);
        spinnerCity = findViewById(R.id.spinnerCity);
        buttonSubmitAcceptance = findViewById(R.id.btnSubmitAcceptance);

        selectFile = findViewById(R.id.btnCenterRegulation);
        selectFile.setOnClickListener(selectFile_listener);

        buttonSubmitAcceptance.setOnClickListener(btnSubmitAcceptance_listener);

        //Initialize DB to get acceptance reference
        DatabaseReference cityRef = FirebaseDatabase.getInstance().getReference("city");
        cityRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<City> cities = new ArrayList<>();
                for(DataSnapshot acceptanceSnapShot:dataSnapshot.getChildren())
                {
                    cities.add(acceptanceSnapShot.getValue(City.class));
                }
                //Get all names of acceptance
                final List<String> name_list = new ArrayList<>();
                for(City city: cities){
                    name_list.add(city.getName());
                }
                //Create adapter and set for spinner
                ArrayAdapter<String> stringArrayAdapterCity;
                stringArrayAdapterCity = new ArrayAdapter<>(AddAcceptanceActivity.this, android.R.layout.simple_list_item_1, name_list);
                spinnerCity.setAdapter(stringArrayAdapterCity);

                //Method to retrieve the id code of the item selected in the spinner
                spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        idCity = cities.get(i).getId();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Set toast message
            }
        });
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
                    eTcenterServices.getText().toString().equals("")){ //Check if all the fields are not empty
                Toast.makeText(AddAcceptanceActivity.this, "No field should be empty", Toast.LENGTH_LONG).show();

            } else {
                addNewAcceptance(); //Method to add a new acceptance
            }
        }
    };

    private void addNewAcceptance() {
        final String nameCenter = eTcenterName.getText().toString();
        final String locationCenter = eTcenterLocation.getText().toString();
        final String centerServices = eTcenterServices.getText().toString();
        String[] services = centerServices.split(","); //Split string every comma
        ArrayList<String> listOfServices = new ArrayList<>();
        for (int i = 0; i < services.length; i++){
            listOfServices.add(services[i]);//add string_service to the list
        }

        String id = FirebaseDatabase.getInstance().getReference("acceptance").push().getKey();
        Acceptance acceptance = new Acceptance(
                nameCenter,
                locationCenter,
                listOfServices,
                id,
                idCity
        );

        FirebaseDatabase.getInstance().getReference("acceptance").child(id).setValue(acceptance);
        /*FirebaseDatabase.getInstance().getReference().child("acceptance").push().setValue(acceptance).addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {
            if (task.isSuccessful()){
                Toast.makeText(AddAcceptanceActivity.this, "Added successfully", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(AddAcceptanceActivity.this, "Error, can't add the acceptance", Toast.LENGTH_LONG).show();
            }
        }
        */

    }


}
