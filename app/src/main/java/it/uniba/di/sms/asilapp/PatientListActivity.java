package it.uniba.di.sms.asilapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import it.uniba.di.sms.asilapp.models.User;

public class PatientListActivity extends AppCompatActivity {
    private static final String TAG = "PatientListActivity";
    private RecyclerView recyclerView;
    private DatabaseReference mDatabaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);

        // Initialize Database Reference
        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("user");
        //Used to synchronize data
        mDatabaseUser.keepSynced(true);

        recyclerView = (RecyclerView)findViewById(R.id.userList);
        //Used to know size top avoid expensive layout operation
        recyclerView.setHasFixedSize(true);
        //Create layoutmanager, it is responsible for measuring and positioning item views within a RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart(){
        super.onStart();
        FirebaseRecyclerAdapter<User,UserViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<User,UserViewHolder>
                (User.class, R.layout.activity_patient_row, UserViewHolder.class, mDatabaseUser){
            @Override
            protected void populateViewHolder(UserViewHolder viewHolder, User model, final int position){
                //Set value in viewHolder
                viewHolder.setName(model.getName());
                viewHolder.setSurname(model.getSurname());
                viewHolder.setDate_of_birth(model.getDate_of_birth());
                //Set on click listener to get id of user clicked
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Get id of user clicked
                        String user_clicked = getRef(position).getKey();
                        //Create new intent
                        Intent patientDetailIntent = new Intent(PatientListActivity.this, PatientDetailActivity.class);
                        //pass parameter to intent
                        patientDetailIntent.putExtra("user_clicked", user_clicked);
                        startActivity(patientDetailIntent);
                    }
                });
            }


        };
        //Used to associate an adapter with the list
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    //Define class UserViewHolder
    public static class UserViewHolder extends RecyclerView.ViewHolder {
        View mView;
        public UserViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

        }
        //Set value methods
        public void setName(String name){
            TextView userName = (TextView)mView.findViewById(R.id.text_userNameClicked);
            userName.setText(name);
        }
        public void setSurname(String surname){
            TextView userSurname = (TextView)mView.findViewById(R.id.userSurname);
            userSurname.setText(surname);
        }
        public void setDate_of_birth(String date_of_birth){
            TextView userBirth = (TextView)mView.findViewById(R.id.userBirth);
            userBirth.setText(date_of_birth);
        }
    }
}
