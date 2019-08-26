package it.uniba.di.sms.asilapp;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class BylawActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //variable declaration
    private DrawerLayout drawer;
    private ImageButton imgBtnLanguage;
    private MenuItem nav_addUser;
    private MenuItem nav_homeAdmin;
    private MenuItem nav_homeDoctor;
    private MenuItem nav_kitOpening;
    private MenuItem nav_readRatings;
    private MenuItem nav_addAcceptance;
    private MenuItem nav_searchPatient;
    private MenuItem nav_visitedPatient;
    private MenuItem nav_addRetrieveNecessities;
    private NavigationView navigationView;
    private TextView textViewDownloadIntroduction;
    private TextView textViewDownloadConstitution;
    private TextView textViewDownloadNHS;
    private TextView textViewDownloadGoodLiving;
    private Toolbar toolbar;

    private Spinner spinnerSelectLanguage;
    private String[] languages = {"English", "Arabic", "French"};
    private String[] docTitles = {"Introduction to Italy", "Handbook of Good Living", "Italian Constitution", "National Health Service"};
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {    //Called when the activity is starting.
        super.onCreate(savedInstanceState);
        //Set the activity content from a layout resource.
        setContentView(R.layout.activity_bylaw);

        //Defined adapter
        ArrayAdapter<String> spin_adapter = new ArrayAdapter<>(BylawActivity.this, android.R.layout.simple_list_item_1, languages);

        //Defined variable
        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        textViewDownloadNHS = findViewById(R.id.textViewDownloadNHS);
        textViewDownloadGoodLiving = findViewById(R.id.textViewDownloadGoodLiving);
        textViewDownloadConstitution = findViewById(R.id.textViewDownloadItalianConstitution);
        textViewDownloadIntroduction = findViewById(R.id.textViewDownloadIntroductionToItaly);
        spinnerSelectLanguage = findViewById(R.id.spinnerLanguage);
        imgBtnLanguage = findViewById(R.id.imgBtnLanguage);

        //Set a Toolbar to act as the ActionBar for this Activity window.
        setSupportActionBar(toolbar);
        //Set a listener that will be notified when a menu item is selected.
        navigationView.setNavigationItemSelectedListener(this);
        // get menu from navigationView
        Menu menu = navigationView.getMenu();

        // find MenuItem you want to change
        nav_addUser = menu.findItem(R.id.nav_add_user);
        nav_homeAdmin = menu.findItem(R.id.nav_homeAdmin);
        nav_homeDoctor = menu.findItem(R.id.nav_homeDoctor);
        nav_kitOpening = menu.findItem(R.id.nav_kit_opening);
        nav_readRatings = menu.findItem(R.id.nav_read_ratings);
        nav_searchPatient = menu.findItem(R.id.nav_search_patient);
        nav_visitedPatient = menu.findItem(R.id.nav_visited_patient);
        nav_addAcceptance = menu.findItem(R.id.nav_add_new_acceptance);
        nav_addRetrieveNecessities = menu.findItem(R.id.nav_add_retrive_necessities);

        //Set item visibility
        nav_addUser.setVisible(false);
        nav_homeAdmin.setVisible(false);
        nav_homeDoctor.setVisible(false);
        nav_kitOpening.setVisible(false);
        nav_readRatings.setVisible(false);
        nav_searchPatient.setVisible(false);
        nav_visitedPatient.setVisible(false);
        nav_addAcceptance.setVisible(false);
        nav_addRetrieveNecessities.setVisible(false);

        //Set a click listener on the TextView objects
        textViewDownloadIntroduction.setOnClickListener(downloadIntroduction_listener);
        textViewDownloadConstitution.setOnClickListener(downloadConstitution_listener);
        textViewDownloadNHS.setOnClickListener(downloadNHS_listener);
        textViewDownloadGoodLiving.setOnClickListener(downloadGoodLiving_listener);
        //Set adapter to spinner
        spinnerSelectLanguage.setAdapter(spin_adapter);
        //Set a click listener on the ImageButton objects
        imgBtnLanguage.setOnClickListener(imgBtnLanguage_listener);

        //Create new ActionBarDraweToggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //Adds the specified listener to the list of listeners that will be notified of drawer events.
        drawer.addDrawerListener(toggle);
        //Synchronize the indicator with the state of the linked DrawerLayout after onRestoreInstanceState has occurred.
        toggle.syncState();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { //receive result from activity
        if (requestCode == 1) { // english
            if (resultCode == Activity.RESULT_CANCELED) {
                imgBtnLanguage.setImageResource(R.drawable.lang);
                Intent refresh = new Intent(this, BylawActivity.class);
                startActivity(refresh);
                this.finish();
            }
        }
        if (requestCode == 2) { //italian
            if (resultCode == Activity.RESULT_CANCELED) {
                imgBtnLanguage.setImageResource(R.drawable.italy);
                Intent refresh = new Intent(this, BylawActivity.class);
                startActivity(refresh);
                this.finish();
            }
        }
    }
    //Set on click listener
    public View.OnClickListener imgBtnLanguage_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Create new Intent
            Intent languageIntent = new Intent (BylawActivity.this,PopUpLanguageActivity.class);
            //Pass data between intents
            languageIntent.putExtra("callingActivity", "it.uniba.di.sms.asilapp.BylawActivity");
            startActivity(languageIntent);
        }
    };



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {   //Called when an item in the navigation menu is selected.
        Intent sens;
        switch (item.getItemId()) {
            case R.id.nav_home:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_homeIntent = new Intent (BylawActivity.this, HomepageActivity.class);
                startActivity(nav_homeIntent);
                break;
            case R.id.nav_info:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                sens = new Intent(BylawActivity.this, InformativeActivity.class);
                startActivity(sens);
                break;
            case R.id.nav_medicalRecords:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                sens = new Intent(BylawActivity.this, MedicalRecordsActivity.class);
                startActivity(sens);
                break;
            case R.id.nav_personalData:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                sens = new Intent(BylawActivity.this, PersonalDataActivity.class);
                startActivity(sens);
                break;
            case R.id.nav_questionnaires:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                sens = new Intent(BylawActivity.this, QuestionnairesActivity.class);
                startActivity(sens);
                break;
            case R.id.nav_logout:
                drawer.closeDrawer(GravityCompat.START);
                //Sign out function
                FirebaseAuth.getInstance().signOut();
                //Create new Intent
                sens = new Intent(BylawActivity.this, MainActivity.class);
                startActivity(sens);
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {   //Called when the activity has detected the user's press of the back key.
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public View.OnClickListener downloadIntroduction_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String lang = spinnerSelectLanguage.getSelectedItem().toString();
            index = 0;
            if (lang.equals("English")){
                Uri pdf = Uri.parse("https://firebasestorage.googleapis.com/v0/b/asilapp-1dd34.appspot.com/o/OIM%20inglese.pdf?alt=media&token=18ce6031-f204-4801-a75a-9318a24a3c32");
                DownloadData(pdf, view, index);
            } else if (lang.equals("Arabic")){
                Uri pdf = Uri.parse("https://firebasestorage.googleapis.com/v0/b/asilapp-1dd34.appspot.com/o/OIM%20Arabo%20ok.pdf?alt=media&token=08ce2143-90b2-4f8d-ba4a-ff6e9cf18a47");
                DownloadData(pdf, view, index);
            } else {
                Uri pdf = Uri.parse("https://firebasestorage.googleapis.com/v0/b/asilapp-1dd34.appspot.com/o/OIM%20francese.pdf?alt=media&token=d979d6c7-118c-4a20-97ee-8f40a6707161");
                DownloadData(pdf, view, index);
            }
        }
    };

    public View.OnClickListener downloadConstitution_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String lang = spinnerSelectLanguage.getSelectedItem().toString();
            index = 2;
            if (lang.equals("English")){
                Uri pdf = Uri.parse("https://firebasestorage.googleapis.com/v0/b/asilapp-1dd34.appspot.com/o/Costituzione%20Italiana_lingua%20inglese_2013.pdf?alt=media&token=806a1214-ac8f-4692-a687-5e2a9e8b014f");
                DownloadData(pdf, view, index);
            } else if (lang.equals("Arabic")){
                Uri pdf = Uri.parse("https://firebasestorage.googleapis.com/v0/b/asilapp-1dd34.appspot.com/o/Costituzione%20Italiana_lingua%20araba_2013.pdf?alt=media&token=8af9178d-4bb7-4d11-bf1b-eb89692af53d");
                DownloadData(pdf, view, index);
            } else {
                Uri pdf = Uri.parse("https://firebasestorage.googleapis.com/v0/b/asilapp-1dd34.appspot.com/o/Costituzione%20Italiana_lingua%20francese_2013.pdf?alt=media&token=9f377874-4528-47bc-a8fb-b2e640bbd601");
                DownloadData(pdf, view, index);
            }
        }
    };

    public  View.OnClickListener downloadNHS_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String lang = spinnerSelectLanguage.getSelectedItem().toString();
            index = 3;
            if (lang.equals("English")){
                Uri pdf = Uri.parse("https://firebasestorage.googleapis.com/v0/b/asilapp-1dd34.appspot.com/o/informaSaluteInglese.pdf?alt=media&token=58106d2d-d6f6-409c-ae87-d632fc6504b7");
                DownloadData(pdf, view, index);
            } else if (lang.equals("Arabic")){
                Uri pdf = Uri.parse("https://firebasestorage.googleapis.com/v0/b/asilapp-1dd34.appspot.com/o/InformaSaluteArabo.pdf?alt=media&token=d38b58a1-978b-4459-8626-c8f27bfe0c1f");
                DownloadData(pdf, view, index);
            } else {
                Uri pdf = Uri.parse("https://firebasestorage.googleapis.com/v0/b/asilapp-1dd34.appspot.com/o/InformaSaluteFrancese.pdf?alt=media&token=e10f674f-0892-495e-a0fa-d4db8fc5711e");
                DownloadData(pdf, view, index);
            }
        }
    };

    public View.OnClickListener downloadGoodLiving_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String lang = spinnerSelectLanguage.getSelectedItem().toString();
            index = 1;
            if (lang.equals("English")){
                Uri pdf = Uri.parse("https://firebasestorage.googleapis.com/v0/b/asilapp-1dd34.appspot.com/o/Prontuario_EN%20abitare.pdf?alt=media&token=bb25289d-eb48-4454-96e4-3005c657f0eb");
                DownloadData(pdf, view, index);
            } else if (lang.equals("Arabic")){
                Uri pdf = Uri.parse("https://firebasestorage.googleapis.com/v0/b/asilapp-1dd34.appspot.com/o/Prontuario_AR%20abitare.pdf?alt=media&token=7262c0dd-7189-4308-9199-625a54985d1d");
                DownloadData(pdf, view, index);
            } else {
                Uri pdf = Uri.parse("https://firebasestorage.googleapis.com/v0/b/asilapp-1dd34.appspot.com/o/Prontuario_FR%20abitare.pdf?alt=media&token=9c182f00-15ca-4caf-8aa7-004ff0572742");
                DownloadData(pdf, view, index);
            }
        }
    };

    private long DownloadData(Uri uri, View v, int index) {
        long downloadReference;
        // Create request for android download manager
        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        //Setting title of request
        request.setTitle(docTitles[index]);

        //Setting description of request
        request.setDescription("AsilApp");

        //Setting visibility
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        //Set the local destination for the downloaded file to a path
        //within the application's external files directory
        request.setDestinationInExternalFilesDir(BylawActivity.this,
                Environment.DIRECTORY_DOWNLOADS, "help.png");

        //Enqueue download and save into referenceId
        downloadReference = downloadManager.enqueue(request);

        Toast.makeText(this, getResources().getString(R.string.downloadSuccess),
                Toast.LENGTH_LONG).show();
        return downloadReference;

    }


}
