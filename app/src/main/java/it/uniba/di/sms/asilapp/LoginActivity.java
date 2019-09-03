package it.uniba.di.sms.asilapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

import it.uniba.di.sms.asilapp.models.User;

public class LoginActivity extends AppCompatActivity {
    //Variable declaration
    private static final String TAG = "LoginActivity";
    private Button loginButton;
    private DatabaseReference mUserReference;
    private EditText mEmailView;
    private EditText mPasswordView;
    private FirebaseAuth mAuth;
    private String uId;
    private int mRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {    //Called when the activity is starting.
        super.onCreate(savedInstanceState);
        //Set the activity content from a layout resource.
        setContentView(R.layout.activity_login);
        SharedPreferences prefs = getSharedPreferences("CommonPrefs",
                Activity.MODE_PRIVATE);

        String language = prefs.getString("Language", "");
        if (language.equals("en")) {
            Configuration config = getBaseContext().getResources().getConfiguration();

                Locale locale = new Locale("en");
                Locale.setDefault(locale);
                Configuration conf = new Configuration(config);
                conf.locale = locale;
                getBaseContext().getResources().updateConfiguration(conf, getBaseContext().getResources().getDisplayMetrics());

            }
        //Defined variable
        mEmailView = (EditText) findViewById(R.id.input_email);
        mPasswordView = (EditText) findViewById(R.id.input_password);
        loginButton = (Button) findViewById(R.id.btn_login);

        //Returns an instance of this class corresponding to the default FirebaseApp instance.
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {   //Called when the activity had been stopped.
        super.onStart();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmailView.getText().toString();
                final String psw = mPasswordView.getText().toString();
                final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                        R.style.AppTheme_Dark_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Authenticating...");
                progressDialog.show();

                if (email.isEmpty() || psw.isEmpty()) {
                    Toast.makeText(getBaseContext(), "Login Failed, empty fields", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    return;
                } else {
                    //Tries to sign in a user with the given email address and password
                    mAuth.signInWithEmailAndPassword(email, psw).
                            addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        onLoginSuccess();
                                        progressDialog.dismiss();
                                    } else {
                                        onLoginFailed();
                                        progressDialog.dismiss();
                                        return;
                                    }
                                }
                            });
                }
            }
        });
    }

    //Get role id to load correct homepage
    public void getRoleActivity(int role_id) {
        if (role_id == 1) { //Admin Role
            //Create new Intent
            Intent adminIntent = new Intent(LoginActivity.this, AdminActivity.class);
            startActivity(adminIntent);
            finish();
        } else if (role_id == 2) {  //User Role
            //Create new Intent
            Intent userIntent = new Intent(LoginActivity.this, HomepageActivity.class);
            startActivity(userIntent);
            finish();
        } else if (role_id == 3) {  //Doctor Role
            //Create new Intent
            Intent doctorIntent= new Intent(LoginActivity.this, DoctorActivity.class);
            startActivity(doctorIntent);
            finish();
        }
    }

    public void onLoginSuccess() {
        //Get Current User
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //Get userId
        uId = user.getUid();
        //Initialize Database Reference
        mUserReference = FirebaseDatabase.getInstance().getReference()
                .child("user").child(uId);

        ValueEventListener userListener = new ValueEventListener() {
            @Override
            //called with a snapshot of the data at this location
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get User object
                User user = dataSnapshot.getValue(User.class);
                mRole = user.getRole();
                getRoleActivity(mRole);
            }
            @Override
            //triggered in the event that this listener either failed at the server, or is removed as a result of the security and Firebase rules.
            public void onCancelled(DatabaseError databaseError) {
                // Getting User Role failed, log a message
                Log.w(TAG, "loadUser:onCancelled", databaseError.toException());
                Toast.makeText(LoginActivity.this, "Failed to load user.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        mUserReference.addValueEventListener(userListener);
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login Failed", Toast.LENGTH_LONG).show();
    }
}
