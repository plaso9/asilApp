package it.uniba.di.sms.asilapp;


import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import java.util.Date;
import java.util.List;

import it.uniba.di.sms.asilapp.adapter.MessageAdapter;
import it.uniba.di.sms.asilapp.models.Message;

public class ChatActivity extends AppCompatActivity {
    private static final String TAG = "ChatActivity";
    
    private EditText mMessage;
    private ImageButton mSend;
    private DatabaseReference mChatReference;

    MessageAdapter messageAdapter;
    List<Message> mMessagesList;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mMessage = findViewById(R.id.message);
        mSend = findViewById(R.id.sendMessage);
        recyclerView = findViewById(R.id.chatList);

        //Used to know top avoid expensive layout operation
        recyclerView.setHasFixedSize(true);

        mSend.setOnClickListener(mSend_listener);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected void onStart(){
        super.onStart();
        readMessages();
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
                    //Toast.makeText(ChatActivity.this, "Addedd successfully", Toast.LENGTH_LONG).show();
                } else {
                    //Failure message
                    Toast.makeText(ChatActivity.this, "Addedd failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    // Function to read messages
    private void readMessages(){
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
}
