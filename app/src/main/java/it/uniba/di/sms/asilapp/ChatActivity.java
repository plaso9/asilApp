package it.uniba.di.sms.asilapp;


import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

import it.uniba.di.sms.asilapp.models.Message;

public class ChatActivity extends AppCompatActivity {
    private static final String TAG = "ChatActivity";
    private EditText mMessage;
    private ImageButton mSend;
    private DatabaseReference mUserReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mMessage = findViewById(R.id.message);
        mSend = findViewById(R.id.sendMessage);

        mSend.setOnClickListener(mSend_listener);

    }

    public View.OnClickListener mSend_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (mMessage.getText().toString().equals("")){
                Toast.makeText(ChatActivity.this, "Can't send empty message", Toast.LENGTH_SHORT).show();
            } else {
                sendMessage();
            }
        }
    };

    public void sendMessage(){
        // Initialize FirebaseUser
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        // Get userId Logged
        String userId = user.getUid();

        String message = mMessage.getText().toString();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = new Date();
        String current_data = formatter.format(date);

        Message messageObj = new Message(
                userId,
                message,
                current_data
        );

        FirebaseDatabase.getInstance().getReference().child("chat").push().setValue(messageObj).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    //Success message
                    Toast.makeText(ChatActivity.this, "Addedd successfully", Toast.LENGTH_LONG).show();
                } else {
                    //Failure message
                    Toast.makeText(ChatActivity.this, "Addedd failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
