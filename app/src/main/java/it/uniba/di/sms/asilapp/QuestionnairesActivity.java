package it.uniba.di.sms.asilapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class QuestionnairesActivity extends AppCompatActivity {
    private static final String TAG = "QuestionnairesActivity";
    private String uId;
    private String userClickedId;
    private DatabaseReference mUserReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaires);

        // Initialize FirebaseUser
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //Initialize variable to get extra value from other intent
        userClickedId = getIntent().getExtras().getString("user_clicked");
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
    }
}
