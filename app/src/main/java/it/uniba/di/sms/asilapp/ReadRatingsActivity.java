package it.uniba.di.sms.asilapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

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

    private static final String TAG = "ReadRatingsActivity";
    private RecyclerView recyclerView;
    private DatabaseReference mDatabaseRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_ratings);

        //Initialize Database Reference
        mDatabaseRating = FirebaseDatabase.getInstance().getReference().child("rating");
        //Used to synchronize data
        mDatabaseRating.keepSynced(true);

        recyclerView = findViewById(R.id.ratingList);
        //Used to know size top avoid expensive layout operation
        recyclerView.setHasFixedSize(true);
        //Create layout manager, it is responsible for measuring and positioning item views within a RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart(){
        super.onStart();
        FirebaseRecyclerAdapter<Rating, RatinViewHolder> firebaseRecyclerAdapter = new
                FirebaseRecyclerAdapter<Rating, RatinViewHolder>(
                        Rating.class, R.layout.activity_rating_row, RatinViewHolder.class, mDatabaseRating
                ) {
                    @Override
                    protected void populateViewHolder(final RatinViewHolder viewHolder, final Rating model, final int position) {
                        DatabaseReference db = FirebaseDatabase.getInstance().getReference("user").child(model.getUser());
                        db.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                User user = dataSnapshot.getValue(User.class);
                                viewHolder.setName(user.getName()+" "+user.getSurname());
                                viewHolder.setAvgRating(roundTwoDecimals(model.getAvgRating()));
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                        viewHolder.itemView.setOnClickListener(new View.OnClickListener(){
                            @Override
                            public void onClick(View view){
                                //Get id of user clicked
                                String user_clicked = getRef(position).getKey();
                                //Create new Intent
                                Intent patientDetailIntent = new Intent(ReadRatingsActivity.this, PatientDetailActivity.class);
                                patientDetailIntent.putExtra("user_clicked", user_clicked);
                                startActivity(patientDetailIntent);
                            }
                        });
                    }
                };

        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public static class RatinViewHolder extends RecyclerView.ViewHolder{
        View mView;

        public RatinViewHolder(View itemView){
            super(itemView);
            mView = itemView;
        }

        public void setName(String name){
            TextView userName = mView.findViewById(R.id.text_userNameClicked);
            userName.setText(name);
        }

        public void setAvgRating(Float avgRating){
            TextView rating = mView.findViewById(R.id.userSurname);
            rating.setText(avgRating+"");
        }


    }

    public float roundTwoDecimals(float d)
    {
        DecimalFormat twoDForm = new DecimalFormat("#.##");

        return Float.valueOf(twoDForm.format(d));
    }
}
