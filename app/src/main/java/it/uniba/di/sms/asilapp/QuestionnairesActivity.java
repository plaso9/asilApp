package it.uniba.di.sms.asilapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import it.uniba.di.sms.asilapp.models.Questionnaires;
import it.uniba.di.sms.asilapp.models.User;

public class QuestionnairesActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private static final String TAG = "QuestionnairesActivity";

    private String uId;
    private String URI1="null", URI2="null", URI3="null", URI4="null";
    private String userClickedId;
    private DatabaseReference mUserReference;
    private DrawerLayout drawer;
    private Button btnSf12;
    private Button btnHabits;
    private Button btnDemo;
    private Button btnQuality;
    private DatabaseReference mQuestionnaires;
    private int mRole;
    private ImageButton imgBtnLanguage;

    private MenuItem nav_addUser;
    private MenuItem nav_kitOpening;
    private MenuItem nav_readRatings;
    private MenuItem nav_addAcceptance;
    private MenuItem nav_searchPatient;
    private MenuItem nav_visitedPatient;
    private MenuItem nav_addRetrieveNecessities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaires);

        btnSf12 =(Button) findViewById(R.id.btnSf12);
        btnSf12.setOnClickListener(btnSf12_listener);

        btnHabits =(Button) findViewById(R.id.btnHabits);
        btnHabits.setOnClickListener(btnHabits_listener);

        btnDemo =(Button) findViewById(R.id.btnDemo);
        btnDemo.setOnClickListener(btnDemo_listener);

        btnQuality =(Button) findViewById(R.id.btnQuality);
        btnQuality.setOnClickListener(btnQuality_listener);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // get menu from navigationView
        Menu menu = navigationView.getMenu();

        // find MenuItem you want to change
        nav_addUser = menu.findItem(R.id.nav_add_user);
        nav_kitOpening = menu.findItem(R.id.nav_kit_opening);
        nav_readRatings = menu.findItem(R.id.nav_read_ratings);
        nav_searchPatient = menu.findItem(R.id.nav_search_patient);
        nav_visitedPatient = menu.findItem(R.id.nav_visited_patient);
        nav_addAcceptance = menu.findItem(R.id.nav_add_new_acceptance);
        nav_addRetrieveNecessities = menu.findItem(R.id.nav_add_retrive_necessities);

        //Set item visibility
        nav_addUser.setVisible(false);
        nav_kitOpening.setVisible(false);
        nav_readRatings.setVisible(false);
        nav_searchPatient.setVisible(false);
        nav_visitedPatient.setVisible(false);
        nav_addAcceptance.setVisible(false);
        nav_addRetrieveNecessities.setVisible(false);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        // Initialize FirebaseUser
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //Initialize variable to get extra value from other intent
        if (getIntent().getExtras() != null) {
            userClickedId = getIntent().getExtras().getString("user_clicked");
        }
        //Condition
        if (userClickedId != null) {
            uId = userClickedId;
        } else {
            // Get userId Logged
            uId = user.getUid();
        }
        // Initialize Database Reference
        mUserReference = FirebaseDatabase.getInstance().getReference()
                .child("user").child(uId);

        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get User object
                User user = dataSnapshot.getValue(User.class);
                mRole = user.getRole();
                getRoleActivity(mRole);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting User Role failed, log a message
                Log.w(TAG, "loadUser:onCancelled", databaseError.toException());
                Toast.makeText(QuestionnairesActivity.this, "Failed to load user.",
                        Toast.LENGTH_SHORT).show();
            }
        };

        mUserReference.addValueEventListener(userListener);
        mQuestionnaires = FirebaseDatabase.getInstance().getReference("questionnaires").child("1");
        mQuestionnaires.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Questionnaires questionnaires = dataSnapshot.getValue(Questionnaires.class);
                URI1 = String.valueOf(questionnaires.uri);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting questionnaires failed
                Log.w(TAG, "loadQuestionnaires:onCancelled", databaseError.toException());
                Toast.makeText(QuestionnairesActivity.this, "Failed to load questionnaires.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        mQuestionnaires = FirebaseDatabase.getInstance().getReference("questionnaires").child("2");
        mQuestionnaires.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Questionnaires questionnaires = dataSnapshot.getValue(Questionnaires.class);
                URI2 = String.valueOf(questionnaires.uri);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting questionnaires failed
                Log.w(TAG, "loadQuestionnaires:onCancelled", databaseError.toException());
                Toast.makeText(QuestionnairesActivity.this, "Failed to load questionnaires.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        mQuestionnaires = FirebaseDatabase.getInstance().getReference("questionnaires").child("3");
        mQuestionnaires.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Questionnaires questionnaires = dataSnapshot.getValue(Questionnaires.class);
                URI3 = String.valueOf(questionnaires.uri);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting questionnaires failed
                Log.w(TAG, "loadQuestionnaires:onCancelled", databaseError.toException());
                Toast.makeText(QuestionnairesActivity.this, "Failed to load questionnaires.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        mQuestionnaires = FirebaseDatabase.getInstance().getReference("questionnaires").child("4");
        mQuestionnaires.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Questionnaires questionnaires = dataSnapshot.getValue(Questionnaires.class);
                URI4 = String.valueOf(questionnaires.uri);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting questionnaires failed
                Log.w(TAG, "loadQuestionnaires:onCancelled", databaseError.toException());
                Toast.makeText(QuestionnairesActivity.this, "Failed to load questionnaires.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        imgBtnLanguage = findViewById(R.id.imgBtnLanguage);
        imgBtnLanguage.setOnClickListener(imgBtnLanguage_listener);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) { // english

            if (resultCode == Activity.RESULT_CANCELED) {
                imgBtnLanguage.setImageResource(R.drawable.lang);
                Intent refresh = new Intent(this, QuestionnairesActivity.class);
                startActivity(refresh);
                this.finish();
            }


        }
        if (requestCode == 2) { //italian

            if (resultCode == Activity.RESULT_CANCELED) {
                imgBtnLanguage.setImageResource(R.drawable.italy);
                Intent refresh = new Intent(this, QuestionnairesActivity.class);
                startActivity(refresh);
                this.finish();
            }

        }
    }

    public View.OnClickListener imgBtnLanguage_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent languageIntent = new Intent (QuestionnairesActivity.this,PopUpLanguageActivity.class);
            languageIntent.putExtra("callingActivity", "it.uniba.di.sms.asilapp.QuestionnairesActivity");
            startActivity(languageIntent);
        }
    };



    public View.OnClickListener btnSf12_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(URI1));
            startActivity(i);

        }
    };

    public View.OnClickListener btnHabits_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(URI2));
            startActivity(i);

        }
    };

    public View.OnClickListener btnDemo_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(URI3));
            startActivity(i);

        }
    };

    public View.OnClickListener btnQuality_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(URI4));
            startActivity(i);

        }
    };


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent sens;
        switch (item.getItemId()){
            case R.id.nav_info:
                drawer.closeDrawer(GravityCompat.START);
                sens = new Intent (QuestionnairesActivity.this, InformativeActivity.class);
                startActivity(sens);
                break;
            case R.id.nav_medicalRecords:
                drawer.closeDrawer(GravityCompat.START);
                sens = new Intent (QuestionnairesActivity.this, MedicalRecordsActivity.class);
                startActivity(sens);
                break;
            case R.id.nav_personalData:
                drawer.closeDrawer(GravityCompat.START);
                sens = new Intent (QuestionnairesActivity.this, PersonalDataActivity.class);
                startActivity(sens);
                break;
            case R.id.nav_questionnaires:
                drawer.closeDrawer(GravityCompat.START);
                sens = new Intent (QuestionnairesActivity.this, QuestionnairesActivity.class);
                startActivity(sens);
                break;
            case R.id.nav_logout:
                drawer.closeDrawer(GravityCompat.START);
                FirebaseAuth.getInstance().signOut();
                sens = new Intent(QuestionnairesActivity.this, MainActivity.class);
                startActivity(sens);
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }



    public void getRoleActivity(int role_id) {
        if (role_id == 1) {
            //Admin Role



        } else if (role_id == 2) {
            //User Role




        } else if (role_id == 3) {
            btnSf12.setText(R.string.readAnswers);
            btnHabits.setText(R.string.readAnswers);
            btnQuality.setText(R.string.readAnswers);
            btnDemo.setText(R.string.readAnswers);
            URI1 = "https://docs.google.com/forms/d/1wqZ8QBlmsvdBhkEgpl0RTJ8rtlG_is7EISbfPTn7MqY/edit#responses";
            URI2 = "https://docs.google.com/forms/d/1F-6byb6dDpGzT2wq5s2vPKflw6pq1u7PCKn4nf5lD_I/edit#responses";
            URI3 ="https://docs.google.com/forms/d/1_yNp6zPo-7ipKg1q2UIg7TP6fuwQD4rchTDJqa7FfQI/edit#responses";
            URI4 = "https://docs.google.com/forms/d/1x7PXgG_dvIDqGFIquZF3s_rZY-RmCNoOoLcnr2yqZIM/edit#responses";






        }
    }
}
