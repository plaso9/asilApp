package it.uniba.di.sms.asilapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class SearchPatientActivity extends AppCompatActivity {
    //Variable declaration
    public static EditText editTextCode;
    private Button buttonSearch;
    private ImageView imageViewScanCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set the activity content from a layout resource.
        setContentView(R.layout.activity_search_patient);

        //Defined variables
        buttonSearch = (Button) findViewById(R.id.buttonSearch);
        imageViewScanCode = (ImageView) findViewById(R.id.imageViewScanCode);

        //Set a click listener on the imageView objects
        imageViewScanCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent scanCodeIntent = new Intent(SearchPatientActivity.this, ScanActivity.class);
                startActivity(scanCodeIntent);
            }
        });

        //Set a click listener on the button objects
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchIntent = new Intent(SearchPatientActivity.this, PatientListActivity.class);
                startActivity(searchIntent);
            }
        });

    }
}
