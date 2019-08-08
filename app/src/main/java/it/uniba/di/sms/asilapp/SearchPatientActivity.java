package it.uniba.di.sms.asilapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchPatientActivity extends AppCompatActivity {

    public static EditText editTextCode;
    private ImageView imageViewScanCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_patient);

        editTextCode = (EditText) findViewById(R.id.editTextCode);

        imageViewScanCode = (ImageView) findViewById(R.id.imageViewScanCode);

        imageViewScanCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchPatientActivity.this, ScanActivity.class);
                startActivity(intent);
            }
        });

    }
}
