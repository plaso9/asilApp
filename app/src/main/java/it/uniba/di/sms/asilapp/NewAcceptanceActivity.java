package it.uniba.di.sms.asilapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NewAcceptanceActivity extends AppCompatActivity {
    private Button selectFile;
    private int PICK_PDF_CODE =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_acceptance);

        selectFile = findViewById(R.id.btnCenterRegulation);
        selectFile.setOnClickListener(selectFile_listener);

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
}
