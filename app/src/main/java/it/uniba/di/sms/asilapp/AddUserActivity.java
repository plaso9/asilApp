package it.uniba.di.sms.asilapp;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import it.uniba.di.sms.asilapp.models.User;

public class AddUserActivity extends AppCompatActivity {
    private String uId;
    private EditText editTextBirthday;
    private EditText editTextName;
    private EditText editTextSurname;
    private EditText editTextCell;
    private EditText editTextBirthPlace;
    private EditText editTextMail;
    private EditText editTextPassword;
    private EditText editTextGender;
    private Button submitButton;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        mAuth = FirebaseAuth.getInstance();

        editTextName = findViewById(R.id.editTextName);
        editTextSurname = findViewById(R.id.editTextSurname);
        editTextCell = findViewById(R.id.editTextCell);
        editTextBirthPlace = findViewById(R.id.editTextBirthPlace);
        editTextMail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextGender = findViewById(R.id.editTextGender);
        submitButton = findViewById(R.id.buttonSubmit);

        submitButton.setOnClickListener(submitButton_listener);

        editTextBirthday = findViewById(R.id.editTextBirthday);
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

    }

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
                    editTextGender.getText().toString().equals("")
            ){
                Toast.makeText(AddUserActivity.this, "No field should be empty", Toast.LENGTH_LONG).show();
            } else {
                registerUser();
            }
        }
    };

    private void registerUser(){
        final String name = editTextName.getText().toString().trim();
        final String surname = editTextSurname.getText().toString().trim();
        final String cell =  editTextCell.getText().toString().trim();
        final String birthPlace = editTextBirthPlace.getText().toString().trim();
        String eMail = editTextMail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        final String gender = editTextGender.getText().toString().trim();
        final String dateOfBirth = editTextBirthday.getText().toString().trim();

        mAuth.createUserWithEmailAndPassword(eMail,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            User user = new User(
                                    name,
                                    surname,
                                    dateOfBirth,
                                    birthPlace,
                                    cell,
                                    gender,
                                    1,
                                    2
                            );

                            FirebaseDatabase.getInstance().getReference("user")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(AddUserActivity.this, "Registration success", Toast.LENGTH_LONG).show();
                                    } else {
                                        //display a failure message
                                    }

                                }
                            });
                        } else {
                            Toast.makeText(AddUserActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

}
