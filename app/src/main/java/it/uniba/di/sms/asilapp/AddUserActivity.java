package it.uniba.di.sms.asilapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import it.uniba.di.sms.asilapp.models.Acceptance;
import it.uniba.di.sms.asilapp.models.User;

public class AddUserActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    //Variable declaration
    private static final String TAG = "AddUserActivity";

    private ImageButton imgBtnLanguage;
    private Button submitButton;
    private DatabaseReference acceptanceRef;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private EditText editTextCell;
    private EditText editTextMail;
    private EditText editTextName;
    private EditText editTextNation;
    private EditText editTextSurname;
    private EditText editTextBirthday;
    private EditText editTextPassword;
    private EditText editTextBirthPlace;
    private FirebaseAuth mAuth;
    private Spinner spinnerRole;
    private Spinner spinnerGender;
    private Spinner spinnerAcceptance;
    private String id;
    private String gender[] = {"M", "F"};
    private String role[] = {"Admin", "User", "Doctor"};

    private DrawerLayout drawer;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set the activity content from a layout resource.
        setContentView(R.layout.activity_add_user);

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
        editTextName = findViewById(R.id.editTextName);
        editTextCell = findViewById(R.id.editTextCell);
        editTextMail = findViewById(R.id.editTextEmail);
        editTextNation = findViewById(R.id.editTextNation);
        editTextSurname = findViewById(R.id.editTextSurname);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextBirthday = findViewById(R.id.editTextBirthday);
        editTextBirthPlace = findViewById(R.id.editTextBirthPlace);
        spinnerRole = findViewById(R.id.spinnerRole);
        spinnerGender = findViewById(R.id.spinnerGender);
        spinnerAcceptance = findViewById(R.id.spinnerAcceptnace);
        submitButton = findViewById(R.id.buttonSubmit);
        imgBtnLanguage = findViewById(R.id.imgBtnLanguage);

        mAuth = FirebaseAuth.getInstance();

        //Set a click listener on the button object
        submitButton.setOnClickListener(submitButton_listener);
        imgBtnLanguage.setOnClickListener(imgBtnLanguage_listener);

        //Set a click listener on the editText object
        editTextBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AddUserActivity.this, android.R.style.Theme_DeviceDefault_Light_Dialog, dateSetListener, year
                        ,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                dialog.show();
            }
        });
        dateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = day + "/" + month + "/" + year;
                editTextBirthday.setText(date);
            }
        };


        //Spinner for gender
        //ArrayAdapter is a BaseAdapter that is backed by an array of arbitrary objects
        ArrayAdapter<String> spin_adapter = new ArrayAdapter<String>(AddUserActivity.this, android.R.layout.simple_list_item_1, gender);
        // setting adapters to spinners
        spinnerGender.setAdapter(spin_adapter);

        //Spinner for role
        //ArrayAdapter
        ArrayAdapter<String> spinRole_adapter = new ArrayAdapter<String>(AddUserActivity.this, android.R.layout.simple_list_item_1, role);
        spinnerRole.setAdapter(spinRole_adapter);

        //Initialize DB to get acceptance reference
        acceptanceRef = FirebaseDatabase.getInstance().getReference("acceptance");
        acceptanceRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<Acceptance> acceptances = new ArrayList<>();
                for(DataSnapshot acceptanceSnapShot:dataSnapshot.getChildren())
                {
                    acceptances.add(acceptanceSnapShot.getValue(Acceptance.class));
                }
                //Get all names of acceptance
                final List<String> name_list = new ArrayList<>();
                for(Acceptance acceptance: acceptances){
                    name_list.add(acceptance.getName());
                }
                //Create adapter and set for spinner
                ArrayAdapter<String> stringArrayAdapter;
                stringArrayAdapter = new ArrayAdapter<>(AddUserActivity.this, android.R.layout.simple_list_item_1, name_list);
                spinnerAcceptance.setAdapter(stringArrayAdapter);

                //Method to retrieve the id code of the item selected in the spinner
                spinnerAcceptance.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        id = acceptances.get(i).getId();
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting acceptance failed
                Log.w(TAG, "loadAcceptance:onCancelled", databaseError.toException());
                Toast.makeText(AddUserActivity.this, "Failed to load acceptance.",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) { // english
            if (resultCode == Activity.RESULT_CANCELED) {
                imgBtnLanguage.setImageResource(R.drawable.lang);
                Intent refresh = new Intent(this, AddUserActivity.class);
                startActivity(refresh);
                this.finish();
            }
        }
        if (requestCode == 2) { //italian
            if (resultCode == Activity.RESULT_CANCELED) {
                imgBtnLanguage.setImageResource(R.drawable.italy);
                Intent refresh = new Intent(this, AddUserActivity.class);
                startActivity(refresh);
                this.finish();
            }
        }
    }

    public View.OnClickListener imgBtnLanguage_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent change_languageIntent = new Intent (AddUserActivity.this,PopUpLanguageActivity.class);
            change_languageIntent.putExtra("callingActivity", "it.uniba.di.sms.asilapp.AddUserActivity");
            startActivity(change_languageIntent);
        }
    };

    public View.OnClickListener submitButton_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (editTextName.getText().toString().equals("") ||
                    editTextSurname.getText().toString().equals("") ||
                    editTextCell.getText().toString().equals("") ||
                    editTextBirthPlace.getText().toString().equals("") ||
                    editTextBirthday.getText().toString().equals("") ||
                    editTextMail.getText().toString().equals("") ||
                    editTextPassword.getText().toString().equals("") ||
                    editTextNation.getText().toString().equals("")
            ){
                Toast.makeText(AddUserActivity.this, "No field should be empty", Toast.LENGTH_LONG).show();
            } else {
                registerUser();
            }
        }
    };

    //Methods that let the admin to register a new user
    private void registerUser(){
        final String name = editTextName.getText().toString().trim();
        final String surname = editTextSurname.getText().toString().trim();
        final String cell =  editTextCell.getText().toString().trim();
        final String birthPlace = editTextBirthPlace.getText().toString().trim();
        final String eMail = editTextMail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        final String gender = spinnerGender.getSelectedItem().toString().trim();
        final String dateOfBirth = editTextBirthday.getText().toString().trim();
        final String role = spinnerGender.getSelectedItem().toString().trim();
        final String nation = editTextNation.getText().toString();
        final int int_role;

        if (role.equalsIgnoreCase("doctor")){
            int_role = 3;
        } else if (role.equalsIgnoreCase("admin")){
            int_role = 1;
        } else
            int_role = 2;

        //Method provided by Firebase to create a new user with username and password
        mAuth.createUserWithEmailAndPassword(eMail,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            User user = new User(
                                    mAuth.getUid(),
                                    name,
                                    surname,
                                    dateOfBirth,
                                    birthPlace,
                                    cell,
                                    gender,
                                    id,
                                    int_role,
                                    eMail,
                                    nation
                            );

                            //The object user is entered in the DB
                            FirebaseDatabase.getInstance().getReference("user")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(AddUserActivity.this, "Registration success", Toast.LENGTH_LONG).show();
                                        finish();
                                    } else {
                                        //failure message
                                        Toast.makeText(AddUserActivity.this, "Addedd failed", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(AddUserActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_homeAdmin:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_homeDoctorIntent = new Intent (AddUserActivity.this, AdminActivity.class);
                startActivity(nav_homeDoctorIntent);
                break;
            case R.id.nav_add_user:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_addUserIntent = new Intent (AddUserActivity.this, AddUserActivity.class);
                startActivity(nav_addUserIntent);
                break;
            case R.id.nav_add_new_acceptance:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_addAcceptanceIntent = new Intent (AddUserActivity.this, AddAcceptanceActivity.class);
                startActivity(nav_addAcceptanceIntent);
                break;
            case R.id.nav_add_retrive_necessities:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_addFoodIntent = new Intent (AddUserActivity.this, AddFoodActivity.class);
                startActivity(nav_addFoodIntent);
                break;
            case R.id.nav_read_ratings:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_readRatingIntent = new Intent (AddUserActivity.this, ReadRatingsActivity.class);
                startActivity(nav_readRatingIntent);
                break;
            case R.id.nav_logout:
                drawer.closeDrawer(GravityCompat.START);
                //Sign out function
                FirebaseAuth.getInstance().signOut();
                //Create new Intent
                Intent nav_logoutIntent = new Intent(AddUserActivity.this, MainActivity.class);
                startActivity(nav_logoutIntent);
                finish();
                break;
        }
        return true;
    }
}
