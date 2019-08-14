package it.uniba.di.sms.asilapp;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import com.google.firebase.database.FirebaseDatabase;

public class VideoActivity extends AppCompatActivity {
    private VideoView firstVideo;
    private MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        mediaController= new MediaController(this);
        mediaController.setAnchorView(firstVideo);
        firstVideo = findViewById(R.id.videoViewFirstVideo);
        firstVideo.setMediaController(mediaController);
        //String video URL
        String string = "https://firebasestorage.googleapis.com/v0/b/asilapp-1dd34.appspot.com/o/Countdown%20-%202637.mp4?alt=media&token=25ec316a-385a-430f-871e-a10ae4ea4c10";
        Uri uri = Uri.parse(string);

        firstVideo.setVideoURI(uri);
        firstVideo.requestFocus();
        firstVideo.start();
    }
}
