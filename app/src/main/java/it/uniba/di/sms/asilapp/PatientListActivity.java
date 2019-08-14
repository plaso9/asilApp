package it.uniba.di.sms.asilapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import it.uniba.di.sms.asilapp.adapter.PatientAdapter;
import it.uniba.di.sms.asilapp.models.User;

public class PatientListActivity extends AppCompatActivity {
    private static final String TAG = "PatientListActivity";

    private DatabaseReference mUserReference;
    private PatientAdapter patientAdapter;
    private List<User> mUserList;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);

        recyclerView = (RecyclerView)findViewById(R.id.userList);
        //Used to know size top avoid expensive layout operation
        recyclerView.setHasFixedSize(true);
        //Create layout manager, it is responsible for measuring and positioning item views within a RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart(){
        super.onStart();
        readUserList();
    }

    public void readUserList(){
        //ArrayList variable
        mUserList = new ArrayList<>();
        //Initialize Database Reference
        mUserReference = FirebaseDatabase.getInstance().getReference("user");
        //Used to synchronize data
        mUserReference.keepSynced(true);
        //Add value
        mUserReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Clear ArrayList
                mUserList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    // Get obj user
                    User user = snapshot.getValue(User.class);
                    if(user.getRole() == 2){
                        // Add obj to ArrayList
                        mUserList.add(user);
                    }

                    patientAdapter = new PatientAdapter(PatientListActivity.this, mUserList);
                    recyclerView.setAdapter(patientAdapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting user failed
                Log.w(TAG, "loadUser:onCancelled", databaseError.toException());
                Toast.makeText(PatientListActivity.this, "Failed to load user.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
