package it.uniba.di.sms.asilapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText mEmailView;
    private EditText mPasswordView;
    private Button loginButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailView = (EditText) findViewById(R.id.input_email);
        mPasswordView = (EditText) findViewById(R.id.input_password);
        loginButton = (Button) findViewById(R.id.btn_login); //Login Button

        mAuth = FirebaseAuth.getInstance(); //Returns an instance of this class corresponding to the default FirebaseApp instance.

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmailView.getText().toString();
                final String psw = mPasswordView.getText().toString();

                mAuth.signInWithEmailAndPassword(email, psw). //Tries to sign in a user with the given email address and password
                        addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>(){
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task){
                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(LoginActivity.this, HomepageActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    onLoginFailed();
                                    return;
                                }
                            }
                        });
            }
        });
    }

    public void onLoginFailed(){
        Toast.makeText(getBaseContext(), "Login Failed", Toast.LENGTH_LONG).show();
    }
}
