package it.uniba.di.sms.asilapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import it.uniba.di.sms.asilapp.models.User;

public class PatientListActivity extends AppCompatActivity {
    private static final String TAG = "PatientListActivity";

    //Variable
    ListView listView;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    User user;
    String displayName;
    DatabaseReference mUserReferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);

        listView = findViewById(R.id.userList);
        mUserReferences = FirebaseDatabase.getInstance().getReference().child("user");

        user = new User();
        list = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);

        mUserReferences.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get User list from object
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    user = ds.getValue(User.class);
                    displayName = user.getName() + " " + user.getSurname();
                    list.add(displayName);
                }
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting User failed, log a message
                Log.w(TAG, "loadUser:onCancelled", databaseError.toException());
                Toast.makeText(PatientListActivity.this, "Failed to load user list.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

}
