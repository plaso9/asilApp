package it.uniba.di.sms.asilapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;

import it.uniba.di.sms.asilapp.models.Rating;
import it.uniba.di.sms.asilapp.models.User;

public class ReadRatingsActivity extends AppCompatActivity {
    //variable declaration
    private static final String TAG = "ReadRatingsActivity";
    private DatabaseReference mDatabaseRating;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {    //Called when the activity is starting.
        super.onCreate(savedInstanceState);
        //Set the activity content from a layout resource.
        setContentView(R.layout.activity_read_ratings);

        //Defined variable
        recyclerView = findViewById(R.id.ratingList);

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
    protected void onStart(){   //Called when the activity had been stopped,
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
                                viewHolder.setName(user.getName()+" "+user.getSurname());
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

                        //Set a click listener on the item of ViewHolder objects
                        viewHolder.itemView.setOnClickListener(new View.OnClickListener(){
                            @Override
                            public void onClick(View view){
                                //Get id of user clicked
                                String user_clicked = getRef(position).getKey();
                                //Create new Intent
                                Intent patientDetailIntent = new Intent(ReadRatingsActivity.this, PatientDetailActivity.class);
                                //Pass data between intents
                                patientDetailIntent.putExtra("user_clicked", user_clicked);
                                startActivity(patientDetailIntent);
                            }
                        });
                    }
                };
        //Called to associate adapter with the list
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    //Created ViewHolder Class
    public static class RatingViewHolder extends RecyclerView.ViewHolder{
        View mView;

        public RatingViewHolder(View itemView){
            super(itemView);
            mView = itemView;
        }

        //Set functions
        public void setName(String name){
            TextView userName = mView.findViewById(R.id.text_userNameClicked);
            userName.setText(name);
        }

        public void setAvgRating(Float avgRating){
            TextView rating = mView.findViewById(R.id.userSurname);
            rating.setText(avgRating+"");
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
