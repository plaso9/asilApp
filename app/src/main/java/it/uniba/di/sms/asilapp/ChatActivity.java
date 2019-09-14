package it.uniba.di.sms.asilapp;


import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import it.uniba.di.sms.asilapp.adapter.MessageAdapter;
import it.uniba.di.sms.asilapp.models.Message;
import it.uniba.di.sms.asilapp.models.User;

public class ChatActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    //variable declaration
    private static final String TAG = "ChatActivity";
    private DatabaseReference mChatReference;
    private DatabaseReference mUserReference;
    private DrawerLayout drawer;
    private EditText mMessage;
    private ImageButton mSend;
    private ImageButton imgBtnLanguage;
    private List<Message> mMessagesList;
    private MenuItem nav_home;
    private MenuItem nav_info;
    private MenuItem nav_video;
    private MenuItem nav_addUser;
    private MenuItem nav_homeAdmin;
    private MenuItem nav_homeDoctor;
    private MenuItem nav_kitOpening;
    private MenuItem nav_readRatings;
    private MenuItem nav_personalData;
    private MenuItem nav_addAcceptance;
    private MenuItem nav_searchPatient;
    private MenuItem nav_medicalRecords;
    private MenuItem nav_questionnaires;
    private MenuItem nav_visitedPatient;
    private MenuItem nav_addRetrieveNecessities;
    private MessageAdapter messageAdapter;
    private NavigationView navigationView;
    private RecyclerView recyclerView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {    //Called when the activity is starting.
        super.onCreate(savedInstanceState);
        //Set the activity content from a layout resource.
        setContentView(R.layout.activity_chat);

        //Defined variable
        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        imgBtnLanguage = findViewById(R.id.imgBtnLanguage);
        mMessage = findViewById(R.id.message);
        mSend = findViewById(R.id.sendMessage);
        recyclerView = findViewById(R.id.chatList);

        //Set a Toolbar to act as the ActionBar for this Activity window.
        setSupportActionBar(toolbar);
        //Set a listener that will be notified when a menu item is selected.
        navigationView.setNavigationItemSelectedListener(this);

        //Create new ActionBarDraweToggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //Adds the specified listener to the list of listeners that will be notified of drawer events.
        drawer.addDrawerListener(toggle);
        //Synchronize the indicator with the state of the linked DrawerLayout after onRestoreInstanceState has occurred.
        toggle.syncState();

        //Get user role to hide some menu item
        getUserRole();

        //Used to know top avoid expensive layout operation
        recyclerView.setHasFixedSize(true);

        //Set a click listener on the imageButton objects
        mSend.setOnClickListener(mSend_listener);

        //Create new linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        //method to print item from bottom
        linearLayoutManager.setStackFromEnd(true);

        //Create layout manager, it is responsible for positioning item views within a RecyclerView
        recyclerView.setLayoutManager(linearLayoutManager);
        //Set image resource
        imgBtnLanguage.setImageResource(R.drawable.italy);
        Configuration config = getBaseContext().getResources().getConfiguration();
        if (config.locale.getLanguage().equals("en")) {
            //Set image
            imgBtnLanguage.setImageResource(R.drawable.lang);
        }

        //Set a click listener on the button object
        imgBtnLanguage.setOnClickListener(imgBtnLanguage_listener);
    }

    @Override
    protected void onStart(){  //Method called when the activity is started
        super.onStart();
        readMessages();
    }
    @Override
    public void onBackPressed() {   //Called when the activity has detected the user's press of the back key.
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
        // Initialize FirebaseUser
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        mUserReference = FirebaseDatabase.getInstance().getReference("user").child(user.getUid());

        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get User object and use the values to update the UI
                User user = dataSnapshot.getValue(User.class);
                int role = user.getRole();
                if (role == 2) {    //role 2 = User I
                    Intent intent = new Intent(ChatActivity.this, HomepageActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                } else if (role == 3) { //role 3 = Doctor
                    Intent intent = new Intent(ChatActivity.this, DoctorActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { //receive result from activity
            //Create new Intent
            Intent refresh = new Intent(this, ChatActivity.class);
            startActivity(refresh);
            this.finish();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {   //Called when an item in the navigation menu is selected.
        switch (item.getItemId()){
            case R.id.nav_homeDoctor:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_homeDoctorIntent = new Intent (ChatActivity.this, DoctorActivity.class);
                startActivity(nav_homeDoctorIntent);
                break;
            case R.id.nav_home:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_homeIntent = new Intent (ChatActivity.this, HomepageActivity.class);
                startActivity(nav_homeIntent);
                break;
            case R.id.nav_info:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_infoIntent = new Intent (ChatActivity.this, InformativeActivity.class);
                startActivity(nav_infoIntent);
                break;
            case R.id.nav_medicalRecords:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_medicalRecordsIntent = new Intent (ChatActivity.this, MedicalRecordsActivity.class);
                startActivity(nav_medicalRecordsIntent);
                break;
            case R.id.nav_personalData:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_personalDataIntent  = new Intent (ChatActivity.this, PersonalDataActivity.class);
                startActivity(nav_personalDataIntent);
                break;
            case R.id.nav_questionnaires:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_questionnairesIntent = new Intent (ChatActivity.this, QuestionnairesActivity.class);
                startActivity(nav_questionnairesIntent);
                break;
            case R.id.nav_search_patient:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_searchPatientIntent = new Intent (ChatActivity.this, SearchPatientActivity.class);
                startActivity(nav_searchPatientIntent);
                break;
            case R.id.nav_kit_opening:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_kitOpeningIntent = new Intent (ChatActivity.this, KitOpeningActivity.class);
                startActivity(nav_kitOpeningIntent);
                break;
            case R.id.nav_visited_patient:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_visitedPatientIntent = new Intent (ChatActivity.this, PatientListActivity.class);
                startActivity(nav_visitedPatientIntent);
                break;
            case R.id.nav_video:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_videoIntent = new Intent(ChatActivity.this, VideoActivity.class);
                startActivity(nav_videoIntent);
                break;
            case R.id.nav_homeAdmin:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_homeAdminIntent = new Intent (ChatActivity.this, AdminActivity.class);
                startActivity(nav_homeAdminIntent);
                break;
            case R.id.nav_add_user:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_addUserIntent = new Intent (ChatActivity.this, AddUserActivity.class);
                startActivity(nav_addUserIntent);
                break;
            case R.id.nav_add_new_acceptance:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_addAcceptanceIntent = new Intent (ChatActivity.this, AddAcceptanceActivity.class);
                startActivity(nav_addAcceptanceIntent);
                break;
            case R.id.nav_add_retrive_necessities:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_addFoodIntent = new Intent (ChatActivity.this, AddFoodActivity.class);
                startActivity(nav_addFoodIntent);
                break;
            case R.id.nav_read_ratings:
                drawer.closeDrawer(GravityCompat.START);
                //Create new Intent
                Intent nav_readRatingIntent = new Intent (ChatActivity.this, ReadRatingsActivity.class);
                startActivity(nav_readRatingIntent);
                break;
            case R.id.nav_logout:
                drawer.closeDrawer(GravityCompat.START);
                //Sign out function
                FirebaseAuth.getInstance().signOut();
                //Create new Intent
                Intent nav_logoutIntent = new Intent(ChatActivity.this, MainActivity.class);
                nav_logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(nav_logoutIntent);
                finish();
                break;
        }
        return true;
    }

    public void sendMessage(){
        // Initialize FirebaseUser
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        // Get userId Logged
        final String userId = user.getUid();
        final String message = mMessage.getText().toString();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = new Date();
        final String current_data = formatter.format(date);

        FirebaseDatabase.getInstance().getReference("user").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Get obj user
                User user_data = dataSnapshot.getValue(User.class);
                //New Constructor message
                Message messageObj = new Message(
                        user_data.name,
                        user_data.surname,
                        message,
                        current_data,
                        userId
                );
                //Adding value to DB
                FirebaseDatabase.getInstance().getReference("chat").push().setValue(messageObj).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            //Success message
                            //Toast.makeText(ChatActivity.this, "Addedd successfully", Toast.LENGTH_LONG).show();
                            mMessage.setText("");
                        } else {
                            //Failure message
                            Toast.makeText(ChatActivity.this, "Addedd failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Messages failed
                Log.w(TAG, "loadMessages:onCancelled", databaseError.toException());
                Toast.makeText(ChatActivity.this, "Failed to load messages.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void readMessages(){
        // Function to read messages
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String getCurrentTime = sdf.format(c.getTime());
        String time_start ="10:00";
        String time_end ="20:00";

        //Condition opening chat
        if (getCurrentTime .compareTo(time_end) > 0 || getCurrentTime .compareTo(time_start) < 0  ) {
            canSendMessage();
        }

        // ArrayList variable
        mMessagesList = new ArrayList<>();
        // Initialize Database Reference
        mChatReference = FirebaseDatabase.getInstance().getReference("chat");
        // Add value
        mChatReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Clear ArrayList
                mMessagesList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    // Get Obj message
                    Message message = snapshot.getValue(Message.class);
                    // Add obj to ArrayList
                    mMessagesList.add(message);

                    messageAdapter = new MessageAdapter(ChatActivity.this, mMessagesList);
                    //Called to associate adapter with the list
                    recyclerView.setAdapter(messageAdapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Messages failed
                Log.w(TAG, "loadMessages:onCancelled", databaseError.toException());
                Toast.makeText(ChatActivity.this, "Failed to load messages.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void canSendMessage(){
        final Toast toast = Toast.makeText(getApplicationContext(), "Chat closed, it's open from 10 am to 8 pm, retry later", Toast.LENGTH_LONG);
        toast.show();
        //Allows to send and process Message
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, 5000);
    }

    public void removeItemDoctor(){
        NavigationView navigationView = findViewById(R.id.nav_view);
        // get menu from navigationView
        Menu menu = navigationView.getMenu();
        // find MenuItem you want to change
        nav_video = menu.findItem(R.id.nav_video);
        nav_homeDoctor = menu.findItem(R.id.nav_homeDoctor);
        nav_kitOpening = menu.findItem(R.id.nav_kit_opening);
        nav_searchPatient = menu.findItem(R.id.nav_search_patient);
        nav_visitedPatient = menu.findItem(R.id.nav_visited_patient);
        //Set item visibility
        nav_video.setVisible(false);
        nav_homeDoctor.setVisible(false);
        nav_kitOpening.setVisible(false);
        nav_searchPatient.setVisible(false);
        nav_visitedPatient.setVisible(false);
    }

    public void removeItemUser(){
        NavigationView navigationView = findViewById(R.id.nav_view);
        // get menu from navigationView
        Menu menu = navigationView.getMenu();
        // find MenuItem you want to change
        nav_home = menu.findItem(R.id.nav_home);
        nav_info = menu.findItem(R.id.nav_info);
        nav_personalData = menu.findItem(R.id.nav_personalData);
        nav_medicalRecords = menu.findItem(R.id.nav_medicalRecords);
        nav_questionnaires = menu.findItem(R.id.nav_questionnaires);
        //Set item visibility
        nav_home.setVisible(false);
        nav_info.setVisible(false);
        nav_personalData.setVisible(false);
        nav_medicalRecords.setVisible(false);
        nav_questionnaires.setVisible(false);
    }

    public void removeItemAdmin(){
        navigationView = findViewById(R.id.nav_view);
        // get menu from navigationView
        Menu menu = navigationView.getMenu();
        // find MenuItem you want to change
        nav_addUser = menu.findItem(R.id.nav_add_user);
        nav_homeAdmin = menu.findItem(R.id.nav_homeAdmin);
        nav_readRatings = menu.findItem(R.id.nav_read_ratings);
        nav_addAcceptance = menu.findItem(R.id.nav_add_new_acceptance);
        nav_addRetrieveNecessities = menu.findItem(R.id.nav_add_retrive_necessities);
        //Set item visibility
        nav_addUser.setVisible(false);
        nav_homeAdmin.setVisible(false);
        nav_readRatings.setVisible(false);
        nav_addAcceptance.setVisible(false);
        nav_addRetrieveNecessities.setVisible(false);
    }

    public void getUserRole(){
        // Initialize FirebaseUser
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        mUserReference = FirebaseDatabase.getInstance().getReference("user").child(user.getUid());

        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get User object and use the values to update the UI
                User user = dataSnapshot.getValue(User.class);
                int role = user.getRole();
                if (role == 1) {    //role 1 = Admin
                    removeItemUser();
                    removeItemDoctor();
                }else if (role == 2) {    //role 2 = User
                    removeItemAdmin();
                    removeItemDoctor();
                } else if (role == 3) { //role 3 = Doctor
                    removeItemAdmin();
                    removeItemUser();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting User failed, log a message
                Log.w(TAG, "loadUser:onCancelled", databaseError.toException());
                Toast.makeText(ChatActivity.this, "Failed to load user.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        mUserReference.addListenerForSingleValueEvent(userListener);
    }

    public View.OnClickListener imgBtnLanguage_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Create new Intent
            Intent languageIntent = new Intent (ChatActivity.this,PopUpLanguageActivity.class);
            //Pass data between intents
            languageIntent.putExtra("callingActivity", "it.uniba.di.sms.asilapp.ChatActivity");
            startActivityForResult(languageIntent, 1);
        }
    };

    public View.OnClickListener mSend_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            String getCurrentTime = sdf.format(c.getTime());
            String time_start ="10:00";
            String time_end ="20:00";

            if (mMessage.getText().toString().equals("")){
                Toast.makeText(ChatActivity.this, "Can't send empty message", Toast.LENGTH_SHORT).show();
            } else {
                if (getCurrentTime .compareTo(time_end) > 0 || getCurrentTime .compareTo(time_start) < 0  ) {
                    canSendMessage();
                }else {
                    sendMessage();
                }
            }
        }
    };
}
