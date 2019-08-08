package it.uniba.di.sms.asilapp;

import android.app.DownloadManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class BylawActivity extends AppCompatActivity {

    private TextView textViewDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bylaw);
        textViewDownload = findViewById(R.id.textViewDownload);
        textViewDownload.setOnClickListener(download_listener);

    }

    public View.OnClickListener download_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Uri pdf = Uri.parse("https://firebasestorage.googleapis.com/v0/b/asilapp-1dd34.appspot.com/o/ISCRIZIONE%20AL%20SERVIZIO%20SANITARIO%20NAZIONALE.pdf?alt=media&token=d12cd86a-06c1-4c3d-b6a0-2f9f9eb1fc01");
            DownloadData(pdf, view);
        }

    };
    private long DownloadData (Uri uri, View v) {

        long downloadReference;

        // Create request for android download manager
        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        //Setting title of request
        request.setTitle("ISCRIZIONE AL SERVIZIO SANITARIO NAZIONALE");

        //Setting description of request
        request.setDescription("AsilApp");

        //Set the local destination for the downloaded file to a path
        //within the application's external files directory
            request.setDestinationInExternalFilesDir(BylawActivity.this,
                    Environment.DIRECTORY_DOWNLOADS,"help.png");

        //Enqueue download and save into referenceId
        downloadReference = downloadManager.enqueue(request);
        Toast.makeText(this, "SUCCESS",
                Toast.LENGTH_LONG).show();
        return downloadReference;

    }


}
