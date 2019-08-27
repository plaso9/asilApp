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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;

import it.uniba.di.sms.asilapp.models.Rating;
import it.uniba.di.sms.asilapp.models.User;

public class ReadRatingsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //variable declaration
    private static final String TAG = "ReadRatingsActivity";
    private DatabaseReference mDatabaseRating;
    private RecyclerView recyclerView;
    private DrawerLayout drawer;
    private ImageButton imgBtnLanguage;

    private MenuItem nav_home;
    private MenuItem nav_info;
    private MenuItem nav_video;
    private MenuItem nav_homeDoctor;
    private MenuItem nav_kitOpening;
    private MenuItem nav_personalData;
    private MenuItem nav_searchPatient;
    private MenuItem nav_medicalRecords;
    private MenuItem nav_questionnaires;
    private MenuItem nav_visitedPatient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {    //Called when the activity is starting.
        super.onCreate(savedInstanceState);
        //Set the activity content from a layout resource.
        setContentView(R.layout.activity_read_ratings);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // get menu from navigationView
        Menu menu = navigationView.getMenu();

        // find MenuItem you want to change
        nav_home = menu.findItem(R.id.nav_home);
        nav_info = menu.findItem(R.id.nav_info);
        nav_video = menu.findItem(R.id.nav_video);
        nav_homeDoctor = menu.findItem(R.id.nav_homeDoctor);
        nav_kitOpening = menu.findItem(R.id.nav_kit_opening);
        nav_personalData = menu.findItem(R.id.nav_personalData);
        nav_searchPatient = menu.findItem(R.id.nav_search_patient);
        nav_medicalRecords = menu.findItem(R.id.nav_medicalRecords);
        nav_questionnaires = menu.findItem(R.id.nav_questionnaires);
        nav_visitedPatient = menu.findItem(R.id.nav_visited_patient);

        //Set item visibility
        nav_home.setVisible(false);
        nav_info.setVisible(false);
        nav_video.setVisible(false);
        nav_homeDoctor.setVisible(false);
        nav_kitOpening.setVisible(false);
        nav_personalData.setVisible(false);
        nav_searchPatient.setVisible(false);
        nav_medicalRecords.setVisible(false);
        nav_questionnaires.setVisible(false);
        nav_visitedPatient.setVisible(false);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //Defined variable
        recyclerView = findViewById(R.id.ratingList);
        imgBtnLanguage = findViewById(R.id.imgBtnLanguage);
        imgBtnLanguage.setOnClickListener(imgBtnLanguage_listener);

        //Initialize Database Reference
        mDatabaseRating = FirebaseDatabase.getInstance().getReference("rating");
        //Used to synchronize data
        mDatabaseRating.keepSynced(true);

        //Used to know size top avoid expensive layout operation
        recyclerView.setHasFixedSize(true);
        //Create layout manager, it is responsible for measuring and positioning item views within a RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) { // english
            if (resultCode == Activity.RESULT_CANCELED) {
                imgBtnLanguage.setImageResource(R.drawable.lang);
                Intent refresh = new Intent(this, ReadRatingsActivity.class);
                startActivity(refresh);
                this.finish();
            }
        }
        if (requestCode == 2) { //italian
            if (resultCode == Activity.RESULT_CANCELED) {
                imgBtnLanguage.setImageResource(R.drawable.italy);
                Intent refresh = new Intent(this, ReadRatingsActivity.class);
                startActivity(refresh);
                this.finish();
            }
        }
    }

    public View.OnClickListener imgBtnLanguage_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent languageIntent = new Intent(ReadRatingsActivity.this, PopUpLanguageActivity.class);
            languageIntent.putExtra("callingActivity", "it.uniba.di.sms.asilapp.ReadRatingsActivity");
            startActivity(languageIntent);
        }
    };

    @Override
    protected void onStart() {   //Called when the activity had been stopped,
        super.onStart();
        //Let's create new ViewHolder
        FirebaseRecyclerAdapter<Rating, RatingViewHolder> firebaseRecyclerAdapter = new
                FirebaseRecyclerAdapter<Rating, RatingViewHolder>(
                        Rating.class, R.layout.activity_rating_row, RatingViewHolder.class, mDatabaseRating
                ) {
                    @Override
                    //It's used to populate new ViewHolder
                    protected void populateViewHolder(final RatingViewHolder viewHolder, final Rating model, final int position) {
                        //Initialize Database Reference
                        DatabaseReference db = FirebaseDatabase.getInstance().getReference("user").child(model.getUser());
                        //Retrieve data
                        db.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            //called with a snapshot of the data at this location
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                //Get Value
                                User user = dataSnapshot.getValue(User.class);
                                //Set Value
                                viewHolder.setName(user.getName() + " " + user.getSurname());
                                viewHolder.setAvgRating(roundTwoDecimals(model.getAvgRating()));
                                viewHolder.setComment(model.getComment());
                            }

                            @Override
                            //triggered in the event that this listener either failed at the server, or is removed as a result of the security and Firebase rules.
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                // Getting user failed
                                Log.w(TAG, "loadUser:onCancelled", databaseError.toException());
                                Toast.makeText(ReadRatingsActivity.this, "Failed to load user.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                };
        //Called to associate adapter with the list
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_homeAdmin:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_homeDoctorIntent = new Intent(ReadRatingsActivity.this, AdminActivity.class);
                startActivity(nav_homeDoctorIntent);
                break;
            case R.id.nav_add_user:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_addUserIntent = new Intent(ReadRatingsActivity.this, AddUserActivity.class);
                startActivity(nav_addUserIntent);
                break;
            case R.id.nav_add_new_acceptance:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_addAcceptanceIntent = new Intent(ReadRatingsActivity.this, AddAcceptanceActivity.class);
                startActivity(nav_addAcceptanceIntent);
                break;
            case R.id.nav_add_retrive_necessities:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_addFoodIntent = new Intent(ReadRatingsActivity.this, AddFoodActivity.class);
                startActivity(nav_addFoodIntent);
                break;
            case R.id.nav_read_ratings:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_readRatingIntent = new Intent(ReadRatingsActivity.this, ReadRatingsActivity.class);
                startActivity(nav_readRatingIntent);
                break;
            case R.id.nav_logout:
                drawer.closeDrawer(GravityCompat.START);
                //Sign out function
                FirebaseAuth.getInstance().signOut();
                //Create new Intent
                Intent nav_logoutIntent = new Intent(ReadRatingsActivity.this, MainActivity.class);
                startActivity(nav_logoutIntent);
                finish();
                break;
        }
        return true;
    }

    //Created ViewHolder Class
    public static class RatingViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public RatingViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        //Set functions
        public void setName(String name) {
            TextView userName = mView.findViewById(R.id.text_userNameClicked);
            userName.setText(name);
        }

        public void setAvgRating(Float avgRating) {
            TextView rating = mView.findViewById(R.id.userSurname);
            rating.setText(avgRating + "");
        }

        public void setComment(String comment) {
            TextView userComment = mView.findViewById(R.id.userComment);
            userComment.setText(comment);
        }


    }

    //Function to round avg rating
    public float roundTwoDecimals(float d) {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return Float.valueOf(twoDForm.format(d));
    }
}
