package it.uniba.di.sms.asilapp;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class InternalRegulationActivity extends AppCompatActivity {
    //Variable declaration
    private TextView textViewDownloadIntroduction;
    private TextView textViewDownloadConstitution;
    private TextView textViewDownloadNHS;
    private TextView textViewDownloadGoodLiving;

    private ImageButton imgBtnLanguage;
    private Spinner spinnerSelectLanguage;
    private String[] languages = {"English", "Arabic", "French"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internal_regulation);

        ArrayAdapter<String> spin_adapter = new ArrayAdapter<>(InternalRegulationActivity.this, android.R.layout.simple_list_item_1, languages);

        imgBtnLanguage = findViewById(R.id.imgBtnLanguage);
        textViewDownloadNHS = findViewById(R.id.textViewDownloadNHS);
        textViewDownloadGoodLiving = findViewById(R.id.textViewDownloadGoodLiving);
        textViewDownloadConstitution = findViewById(R.id.textViewDownloadItalianConstitution);
        textViewDownloadIntroduction = findViewById(R.id.textViewDownloadIntroductionToItaly);
        spinnerSelectLanguage = findViewById(R.id.spinnerLanguage);
        //Set a click listener on the button object
        imgBtnLanguage.setOnClickListener(imgBtnLanguage_listener);
        //Set a click listener on the textView object
        textViewDownloadIntroduction.setOnClickListener(downloadIntroduction_listener);
        textViewDownloadConstitution.setOnClickListener(downloadConstitution_listener);
        textViewDownloadNHS.setOnClickListener(downloadNHS_listener);
        textViewDownloadGoodLiving.setOnClickListener(downloadGoodLiving_listener);
        //Set adapter to spinner
        spinnerSelectLanguage.setAdapter(spin_adapter);
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


    public View.OnClickListener downloadIntroduction_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String lang = spinnerSelectLanguage.getSelectedItem().toString();
            if (lang.equals("English")){
                Uri pdf = Uri.parse("https://firebasestorage.googleapis.com/v0/b/asilapp-1dd34.appspot.com/o/OIM%20inglese.pdf?alt=media&token=18ce6031-f204-4801-a75a-9318a24a3c32");
                DownloadData(pdf, view);
            } else if (lang.equals("Arabic")){
                Uri pdf = Uri.parse("https://firebasestorage.googleapis.com/v0/b/asilapp-1dd34.appspot.com/o/OIM%20Arabo%20ok.pdf?alt=media&token=08ce2143-90b2-4f8d-ba4a-ff6e9cf18a47");
                DownloadData(pdf, view);
            } else {
                Uri pdf = Uri.parse("https://firebasestorage.googleapis.com/v0/b/asilapp-1dd34.appspot.com/o/OIM%20francese.pdf?alt=media&token=d979d6c7-118c-4a20-97ee-8f40a6707161");
                DownloadData(pdf, view);
            }
        }
    };

    public View.OnClickListener downloadConstitution_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String lang = spinnerSelectLanguage.getSelectedItem().toString();
            if (lang.equals("English")){
                Uri pdf = Uri.parse("https://firebasestorage.googleapis.com/v0/b/asilapp-1dd34.appspot.com/o/Costituzione%20Italiana_lingua%20inglese_2013.pdf?alt=media&token=806a1214-ac8f-4692-a687-5e2a9e8b014f");
                DownloadData(pdf, view);
            } else if (lang.equals("Arabic")){
                Uri pdf = Uri.parse("https://firebasestorage.googleapis.com/v0/b/asilapp-1dd34.appspot.com/o/Costituzione%20Italiana_lingua%20araba_2013.pdf?alt=media&token=8af9178d-4bb7-4d11-bf1b-eb89692af53d");
                DownloadData(pdf, view);
            } else {
                Uri pdf = Uri.parse("https://firebasestorage.googleapis.com/v0/b/asilapp-1dd34.appspot.com/o/Costituzione%20Italiana_lingua%20francese_2013.pdf?alt=media&token=9f377874-4528-47bc-a8fb-b2e640bbd601");
                DownloadData(pdf, view);
            }
        }
    };

    public  View.OnClickListener downloadNHS_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String lang = spinnerSelectLanguage.getSelectedItem().toString();
            if (lang.equals("English")){
                Uri pdf = Uri.parse("https://firebasestorage.googleapis.com/v0/b/asilapp-1dd34.appspot.com/o/informaSaluteInglese.pdf?alt=media&token=58106d2d-d6f6-409c-ae87-d632fc6504b7");
                DownloadData(pdf, view);
            } else if (lang.equals("Arabic")){
                Uri pdf = Uri.parse("https://firebasestorage.googleapis.com/v0/b/asilapp-1dd34.appspot.com/o/InformaSaluteArabo.pdf?alt=media&token=d38b58a1-978b-4459-8626-c8f27bfe0c1f");
                DownloadData(pdf, view);
            } else {
                Uri pdf = Uri.parse("https://firebasestorage.googleapis.com/v0/b/asilapp-1dd34.appspot.com/o/InformaSaluteFrancese.pdf?alt=media&token=e10f674f-0892-495e-a0fa-d4db8fc5711e");
                DownloadData(pdf, view);
            }
        }
    };

    public View.OnClickListener downloadGoodLiving_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String lang = spinnerSelectLanguage.getSelectedItem().toString();
            if (lang.equals("English")){
                Uri pdf = Uri.parse("https://firebasestorage.googleapis.com/v0/b/asilapp-1dd34.appspot.com/o/Prontuario_EN%20abitare.pdf?alt=media&token=bb25289d-eb48-4454-96e4-3005c657f0eb");
                DownloadData(pdf, view);
            } else if (lang.equals("Arabic")){
                Uri pdf = Uri.parse("https://firebasestorage.googleapis.com/v0/b/asilapp-1dd34.appspot.com/o/Prontuario_AR%20abitare.pdf?alt=media&token=7262c0dd-7189-4308-9199-625a54985d1d");
                DownloadData(pdf, view);
            } else {
                Uri pdf = Uri.parse("https://firebasestorage.googleapis.com/v0/b/asilapp-1dd34.appspot.com/o/Prontuario_FR%20abitare.pdf?alt=media&token=9c182f00-15ca-4caf-8aa7-004ff0572742");
                DownloadData(pdf, view);
            }
        }
    };

    private long DownloadData(Uri uri, View v) {

        long downloadReference;

        // Create request for android download manager
        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        //Setting title of request
        request.setTitle("ASILAPP");

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
