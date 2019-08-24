package it.uniba.di.sms.asilapp;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import it.uniba.di.sms.asilapp.models.Rating;

public class RatingActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private static final String TAG = "RatingActivity";
    private RatingBar mRatingCity;
    private RatingBar mRatingCenter;
    private RatingBar mRatingApp;
    private EditText mComment;
    private Button mSubmitRating;
    private Float avgRating;

    private ImageButton imgBtnLanguage;

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();





        mRatingCity = findViewById(R.id.ratingBarCity);
        mRatingCenter = findViewById(R.id.ratingBarCenter);
        mRatingApp = findViewById(R.id.ratingBarApp);
        mComment = findViewById(R.id.editTextComment);
        mSubmitRating = findViewById(R.id.buttonSubmit);

        mSubmitRating.setOnClickListener(mSubmitRating_listener);
        imgBtnLanguage = findViewById(R.id.imgBtnLanguage);
        imgBtnLanguage.setOnClickListener(imgBtnLanguage_listener);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) { // english

            if (resultCode == Activity.RESULT_CANCELED) {
                imgBtnLanguage.setImageResource(R.drawable.lang);
                Intent refresh = new Intent(this, RatingActivity.class);
                startActivity(refresh);
                this.finish();
            }


        }
        if (requestCode == 2) { //italian

            if (resultCode == Activity.RESULT_CANCELED) {
                imgBtnLanguage.setImageResource(R.drawable.italy);
                Intent refresh = new Intent(this, RatingActivity.class);
                startActivity(refresh);
                this.finish();
            }


        }
    }

    public View.OnClickListener imgBtnLanguage_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent languageIntent = new Intent (RatingActivity.this,PopUpLanguageActivity.class);
            languageIntent.putExtra("callingActivity", "it.uniba.di.sms.asilapp.RatingActivity");
            startActivity(languageIntent);
        }
    };


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent sens;
        switch (item.getItemId()){
            case R.id.nav_info:
                drawer.closeDrawer(GravityCompat.START);
                sens = new Intent (RatingActivity.this, InformativeActivity.class);
                startActivity(sens);
                break;
            case R.id.nav_medicalRecords:
                drawer.closeDrawer(GravityCompat.START);
                sens = new Intent (RatingActivity.this, MedicalRecordsActivity.class);
                startActivity(sens);
                break;
            case R.id.nav_personalData:
                drawer.closeDrawer(GravityCompat.START);
                sens = new Intent (RatingActivity.this, PersonalDataActivity.class);
                startActivity(sens);
                break;
            case R.id.nav_questionnaires:
                drawer.closeDrawer(GravityCompat.START);
                sens = new Intent (RatingActivity.this, QuestionnairesActivity.class);
                startActivity(sens);
                break;
            case R.id.nav_logout:
                drawer.closeDrawer(GravityCompat.START);
                FirebaseAuth.getInstance().signOut();
                sens = new Intent(RatingActivity.this, MainActivity.class);
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


    public View.OnClickListener mSubmitRating_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            addNewRating();
        }
    };

    public void addNewRating() {
        // Initialize FirebaseUser
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        // Get userId Logged
        String userId = user.getUid();

        //Set avg rating
        avgRating = (mRatingCity.getRating() + mRatingCenter.getRating() + mRatingApp.getRating()) / 3;

        String comment = mComment.getText().toString();

        //New Constructor
        Rating acceptance = new Rating(
                userId,
                avgRating,
                comment
        );

        //Adding value to DB
        FirebaseDatabase.getInstance().getReference().child("rating").push().setValue(acceptance).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    //success message
                    Toast.makeText(RatingActivity.this, "Addedd successfully", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    //failure message
                    Toast.makeText(RatingActivity.this, "Addedd failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
