package it.uniba.di.sms.asilapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import it.uniba.di.sms.asilapp.models.Pathology;

public class PathologyActivity extends AppCompatActivity {
    private static final String TAG = "PathologyActivity";

    private String pathology_id;
    private EditText mName;
    private EditText mDescription;
    private EditText mNutritional;
    private EditText mLifestyle;
    private EditText mMedicines;
    private DatabaseReference mPathologyReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pathology);

        //Initialize variable to get extra value from other intent
        if (getIntent().getExtras() != null) {
            pathology_id = getIntent().getExtras().getString("pathology_clicked");
        }

        // Initialize Database Reference
        mPathologyReference = FirebaseDatabase.getInstance().getReference("pathology").child(pathology_id);

        // Defined personal data variable
        mName = findViewById(R.id.edit_name_pathology);
        mDescription = findViewById(R.id.edit_description_pathology);
        mNutritional = findViewById(R.id.edit_nutritional_pathology);
        mLifestyle = findViewById(R.id.edit_lifestyle_pathology);
        mMedicines = findViewById(R.id.edit_medicines_pathology);

        ValueEventListener pathologyListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get User object and use the values to update the UI
                Pathology pathology = dataSnapshot.getValue(Pathology.class);
                mName.setText(pathology.name);
                mDescription.setText(pathology.description);
                mNutritional.setText(pathology.nutritional);
                mLifestyle.setText(pathology.lifestyle);
                mMedicines.setText(pathology.medicine);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Pathology failed, log a message
                Log.w(TAG, "loadPathology:onCancelled", databaseError.toException());
                Toast.makeText(PathologyActivity.this, "Failed to load pathology.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        mPathologyReference.addListenerForSingleValueEvent(pathologyListener);
    }
}
