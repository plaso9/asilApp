package it.uniba.di.sms.asilapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
//import android.widget.VideoView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.List;
import java.util.Vector;
import it.uniba.di.sms.asilapp.models.User;
import it.uniba.di.sms.asilapp.models.YouTubeVideos;
public class VideoActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //Variable declaration
    private static final String TAG = "VideoActivity";
    private DatabaseReference mUserReference;
    private DrawerLayout drawer;
    private ImageButton imgBtnLanguage;
    private int mRole;
    private String uId;
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
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    Vector<YouTubeVideos> youTubeVideos = new Vector<YouTubeVideos>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set the activity content from a layout resource.
        setContentView(R.layout.activity_video);
        //Defined variables
        imgBtnLanguage = findViewById(R.id.imgBtnLanguage);
        recyclerView = findViewById(R.id.recyclerViewVideo);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        VideoAdapter videoAdapter = new VideoAdapter(youTubeVideos);

        recyclerView.setAdapter(videoAdapter);

        SharedPreferences prefs = getSharedPreferences("CommonPrefs",
                Activity.MODE_PRIVATE);
        imgBtnLanguage.setImageResource(R.drawable.italy);
            String language = prefs.getString("Language", "");
            if (language.equals("en")) {
                imgBtnLanguage.setImageResource(R.drawable.lang);
            }

            toolbar = findViewById(R.id.toolbar);
            drawer = findViewById(R.id.drawer_layout);
            NavigationView navigationView = findViewById(R.id.nav_view);
            //Set a listener that will be notified when a menu item is selected.
            navigationView.setNavigationItemSelectedListener(this);

            //Set a Toolbar to act as the ActionBar for this Activity window.
            setSupportActionBar(toolbar);
            //Create new ActionBarDraweToggle
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                    R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            //Adds the specified listener to the list of listeners that will be notified of drawer events.
            drawer.addDrawerListener(toggle);
            //Synchronize the indicator with the state of the linked DrawerLayout after onRestoreInstanceState has occurred.
            toggle.syncState();
            //Get user role to hide some menu item
            getUserRole();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            //Get userId
            uId = user.getUid();
            mUserReference = FirebaseDatabase.getInstance().getReference("user").child(uId);
            ValueEventListener userListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Get User object
                    User user = dataSnapshot.getValue(User.class);
                    mRole = user.getRole();
                    getRoleActivity(mRole);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Getting User Role failed, log a message
                    Log.w(TAG, "loadUser:onCancelled", databaseError.toException());
                    Toast.makeText(VideoActivity.this, "Failed to load user.",
                            Toast.LENGTH_SHORT).show();
                }
            };
            mUserReference.addValueEventListener(userListener);
            //Set a click listener on the button object
            imgBtnLanguage.setOnClickListener(imgBtnLanguage_listener);
        }
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (resultCode == Activity.RESULT_CANCELED) {
                Intent refresh = new Intent(this, VideoActivity.class);
                startActivity(refresh);
                this.finish();
            }
        }
        public View.OnClickListener imgBtnLanguage_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent languageIntent = new Intent(VideoActivity.this, PopUpLanguageActivity.class);
                languageIntent.putExtra("callingActivity", "it.uniba.di.sms.asilapp.VideoActivity");
                startActivity(languageIntent);
            }
        };
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Intent sens;
            switch (item.getItemId()) {
                case R.id.nav_homeDoctor:
                    drawer.closeDrawer(GravityCompat.START);
                    //Create new Intent
                    Intent nav_homeDoctorIntent = new Intent(VideoActivity.this, DoctorActivity.class);
                    startActivity(nav_homeDoctorIntent);
                    break;
                case R.id.nav_home:
                    drawer.closeDrawer(GravityCompat.START);
                    //Create new Intent
                    Intent nav_homeIntent = new Intent(VideoActivity.this, HomepageActivity.class);
                    startActivity(nav_homeIntent);
                    break;
                case R.id.nav_info:
                    drawer.closeDrawer(GravityCompat.START);
                    //Create new Intent
                    Intent nav_infoIntent = new Intent(VideoActivity.this, InformativeActivity.class);
                    startActivity(nav_infoIntent);
                    break;
                case R.id.nav_medicalRecords:
                    drawer.closeDrawer(GravityCompat.START);
                    //Create new Intent
                    Intent nav_medicalRecordsIntent = new Intent(VideoActivity.this, MedicalRecordsActivity.class);
                    startActivity(nav_medicalRecordsIntent);
                    break;
                case R.id.nav_personalData:
                    drawer.closeDrawer(GravityCompat.START);
                    //Create new Intent
                    Intent nav_personalDataIntent = new Intent(VideoActivity.this, PersonalDataActivity.class);
                    startActivity(nav_personalDataIntent);
                    break;
                case R.id.nav_questionnaires:
                    drawer.closeDrawer(GravityCompat.START);
                    //Create new Intent
                    Intent nav_questionnairesIntent = new Intent(VideoActivity.this, QuestionnairesActivity.class);
                    startActivity(nav_questionnairesIntent);
                    break;
                case R.id.nav_search_patient:
                    drawer.closeDrawer(GravityCompat.START);
                    //Create new Intent
                    Intent nav_searchPatientIntent = new Intent(VideoActivity.this, SearchPatientActivity.class);
                    startActivity(nav_searchPatientIntent);
                    break;
                case R.id.nav_kit_opening:
                    drawer.closeDrawer(GravityCompat.START);
                    //Create new Intent
                    Intent nav_kitOpeningIntent = new Intent(VideoActivity.this, KitOpeningActivity.class);
                    startActivity(nav_kitOpeningIntent);
                    break;
                case R.id.nav_visited_patient:
                    drawer.closeDrawer(GravityCompat.START);
                    //Create new Intent
                    Intent nav_visitedPatientIntent = new Intent(VideoActivity.this, PatientListActivity.class);
                    startActivity(nav_visitedPatientIntent);
                    break;
                case R.id.nav_video:
                    drawer.closeDrawer(GravityCompat.START);
                    //Create new Intent
                    Intent nav_videoIntent = new Intent(VideoActivity.this, VideoActivity.class);
                    startActivity(nav_videoIntent);
                    break;
                case R.id.nav_logout:
                    drawer.closeDrawer(GravityCompat.START);
                    //Sign out function
                    FirebaseAuth.getInstance().signOut();
                    //Create new Intent
                    Intent nav_logoutIntent = new Intent(VideoActivity.this, MainActivity.class);
                    nav_logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK |
                            Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(nav_logoutIntent);
                    finish();
                    break;
            }
            return true;
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
                    Intent intent = new Intent(VideoActivity.this, InformativeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                } else if (role == 3) { //role 3 = Doctor
                    Intent intent = new Intent(VideoActivity.this, DoctorActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
    }
        public void getRoleActivity(int role_id) {
            if (role_id == 1) {
                //Admin Role
            } else if (role_id == 2) {
                //User Role
                youTubeVideos.add(new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/eWEF1Zrmdow\" frameborder=\"0\" allowfullscreen></iframe>"));
                youTubeVideos.add(new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/KyJ71G2UxTQ\" frameborder=\"0\" allowfullscreen></iframe>"));
            } else if (role_id == 3) {
                //Doc Role
                youTubeVideos.add(new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/y8Rr39jKFKU\" frameborder=\"0\" allowfullscreen></iframe>"));
            }
        }
        public void removeItemDoctor() {
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
        public void removeItemUser() {
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
        public void removeItemAdmin() {
            NavigationView navigationView = findViewById(R.id.nav_view);
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
        public void getUserRole() {
            // Initialize FirebaseUser
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            mUserReference = FirebaseDatabase.getInstance().getReference("user").child(user.getUid());
            ValueEventListener userListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Get User object and use the values to update the UI
                    User user = dataSnapshot.getValue(User.class);
                    int role = user.getRole();
                    if (role == 2) {    //role 2 = User
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
                    Toast.makeText(VideoActivity.this, "Failed to load user.",
                            Toast.LENGTH_SHORT).show();
                }
            };
            mUserReference.addListenerForSingleValueEvent(userListener);
        }
        public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {
            List<YouTubeVideos> youtubeVideoList;
            public VideoAdapter() {
            }
            public VideoAdapter(List<YouTubeVideos> youtubeVideoList) {
                this.youtubeVideoList = youtubeVideoList;
            }
            @Override
            public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from( parent.getContext()).inflate(R.layout.video_view, parent, false);
                return new VideoViewHolder(view);
            }
            @Override
            public void onBindViewHolder(VideoViewHolder holder, int position) {
                holder.videoWeb.loadData( youtubeVideoList.get(position).getVideoUrl(), "text/html" , "utf-8" );
            }
            @Override
            public int getItemCount() {
                return youtubeVideoList.size();
            }
            public class VideoViewHolder extends RecyclerView.ViewHolder{
                WebView videoWeb;
                public VideoViewHolder(View itemView) {
                    super(itemView);
                    videoWeb = itemView.findViewById(R.id.videoWebView);
                    videoWeb.getSettings().setJavaScriptEnabled(true);
                    videoWeb.setWebChromeClient(new MyChrome() {
                    });
                }
            }
        }
        private class MyChrome extends WebChromeClient {
            private View mCustomView;
            private WebChromeClient.CustomViewCallback mCustomViewCallback;
            protected FrameLayout mFullscreenContainer;
            private int mOriginalOrientation;
            private int mOriginalSystemUiVisibility;
            MyChrome() {}
            public Bitmap getDefaultVideoPoster()
            {
                if (mCustomView == null) {
                    return null;
                }
                return BitmapFactory.decodeResource(getApplicationContext().getResources(), 2130837573);
            }
            public void onHideCustomView()
            {
                ((FrameLayout)getWindow().getDecorView()).removeView(this.mCustomView);
                this.mCustomView = null;
                getWindow().getDecorView().setSystemUiVisibility(this.mOriginalSystemUiVisibility);
                setRequestedOrientation(this.mOriginalOrientation);
                this.mCustomViewCallback.onCustomViewHidden();
                this.mCustomViewCallback = null;
            }
            public void onShowCustomView(View paramView, WebChromeClient.CustomViewCallback paramCustomViewCallback)
            {
                if (this.mCustomView != null)
                {
                    onHideCustomView();
                    return;
                }
                this.mCustomView = paramView;
                this.mOriginalSystemUiVisibility = getWindow().getDecorView().getSystemUiVisibility();
                this.mOriginalOrientation = getRequestedOrientation();
                this.mCustomViewCallback = paramCustomViewCallback;
                ((FrameLayout)getWindow().getDecorView()).addView(this.mCustomView, new FrameLayout.LayoutParams(-1, -1));
                getWindow().getDecorView().setSystemUiVisibility(3846);
            }
        }
    }