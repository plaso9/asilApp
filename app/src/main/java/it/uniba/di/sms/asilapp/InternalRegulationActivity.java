package it.uniba.di.sms.asilapp;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class InternalRegulationActivity extends AppCompatActivity {
    private TextView textViewDownload;
    private ImageButton imgBtnLanguage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internal_regulation);
        imgBtnLanguage = findViewById(R.id.imgBtnLanguage);
        imgBtnLanguage.setOnClickListener(imgBtnLanguage_listener);
        textViewDownload = findViewById(R.id.textViewDownload);
        textViewDownload.setOnClickListener(download_listener);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) { // english

            if (resultCode == Activity.RESULT_CANCELED) {
                imgBtnLanguage.setImageResource(R.drawable.lang);
                Intent refresh = new Intent(this, InternalRegulationActivity.class);
                startActivity(refresh);
                this.finish();
            }


        }
        if (requestCode == 2) { //italian

            if (resultCode == Activity.RESULT_CANCELED) {
                imgBtnLanguage.setImageResource(R.drawable.italy);
                Intent refresh = new Intent(this, InternalRegulationActivity.class);
                startActivity(refresh);
                this.finish();
            }

        }
    }

    public View.OnClickListener imgBtnLanguage_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent languageIntent = new Intent (InternalRegulationActivity.this,PopUpLanguageActivity.class);
            languageIntent.putExtra("callingActivity", "it.uniba.di.sms.asilapp.InternalRegulationActivity");
            startActivity(languageIntent);
        }
    };


    public View.OnClickListener download_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Uri pdf = Uri.parse("https://firebasestorage.googleapis.com/v0/b/asilapp-1dd34.appspot.com/o/ISCRIZIONE%20AL%20SERVIZIO%20SANITARIO%20NAZIONALE.pdf?alt=media&token=d12cd86a-06c1-4c3d-b6a0-2f9f9eb1fc01");
            DownloadData(pdf, view);
        }

    };

    private long DownloadData(Uri uri, View v) {

        long downloadReference;

        // Create request for android download manager
        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        //Setting title of request
        request.setTitle("REGOLAMENTO CENTRI DI ACCOGLIENZA");

        //Setting description of request
        request.setDescription("AsilApp");

        //Setting visibility
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        //Set the local destination for the downloaded file to a path
        //within the application's external files directory
        request.setDestinationInExternalFilesDir(InternalRegulationActivity.this,
                Environment.DIRECTORY_DOWNLOADS, "help.png");

        //Enqueue download and save into referenceId
        downloadReference = downloadManager.enqueue(request);

        Toast.makeText(this, getResources().getString(R.string.downloadSuccess),
                Toast.LENGTH_LONG).show();
        return downloadReference;

    }
}
