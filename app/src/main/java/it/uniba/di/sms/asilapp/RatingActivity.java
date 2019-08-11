package it.uniba.di.sms.asilapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import it.uniba.di.sms.asilapp.models.Rating;

public class RatingActivity extends AppCompatActivity {
    private static final String TAG = "RatingActivity";
    private RatingBar mRatingCity;
    private RatingBar mRatingCenter;
    private RatingBar mRatingApp;
    private Button mSubmitRating;
    private Float avgRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        mRatingCity = findViewById(R.id.ratingBarCity);
        mRatingCenter = findViewById(R.id.ratingBarCenter);
        mRatingApp = findViewById(R.id.ratingBarApp);
        mSubmitRating = findViewById(R.id.buttonSubmit);

        mSubmitRating.setOnClickListener(mSubmitRating_listener);
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

        //New Constructor
        Rating acceptance = new Rating(
                userId,
                avgRating
        );

        //Adding value to DB
        FirebaseDatabase.getInstance().getReference().child("rating").push().setValue(acceptance).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    //success message
                    Toast.makeText(RatingActivity.this, "Addedd successfully", Toast.LENGTH_LONG).show();
                    goToPreviousIntent();
                } else {
                    //failure message
                    Toast.makeText(RatingActivity.this, "Addedd failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void goToPreviousIntent(){
        Intent homepageIntent = new Intent (RatingActivity.this,HomepageActivity.class);
        startActivity(homepageIntent);
    }

}
