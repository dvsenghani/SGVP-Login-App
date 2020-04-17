package com.ds.sgvp_login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    EditText mFullName, mEmail, mPassword;
    Button mRegisterBtn;
    TextView mLoginText;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFullName = findViewById(R.id.FullName);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.Password);
        mRegisterBtn = findViewById(R.id.RegisterBtn);
        mLoginText = findViewById(R.id.LoginText);

        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        //if user is logged in, don't display registration and send to login page
        if(fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class)); //if registered, user is redirected to main activity (Start page)
            finish();
        }

        // What happens when user click on register button
        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checks for errors before submitting data to firebase

                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Please Enter Email");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Enter Password");
                    return;
                }
                if(password.length() < 6) {
                    mPassword.setError("Please use more than 6 Char");
                    return;
                }

                //sets progressbar to visible if no error
                progressBar.setVisibility(View.VISIBLE);

                //Register a user to firebase
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
                    //addOnCompleteListener(new OnCompleteListener<AuthResult>() checks if its successful or not
                    {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Register.this, "Successfully Registered", Toast.LENGTH_SHORT).show(); //gives output to user if successful (Toast)
                            startActivity(new Intent(getApplicationContext(), MainActivity.class)); //if registered, user is redirected to main activity (Start page)

                        } else {
                            Toast.makeText(Register.this, "Error !!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show(); //throws error according to it

                        }

                    }
                });
            }
        });

        //function when user clicks already registered!!
        mLoginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });

    }
}
