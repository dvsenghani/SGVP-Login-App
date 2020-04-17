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

import org.w3c.dom.Text;

public class Login extends AppCompatActivity {

    EditText mEmail, mPassword;
    Button mLoginBtn;
    TextView mCreateBtn;
    ProgressBar progressBar;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.Password);
        progressBar = findViewById(R.id.progressBar2);
        mLoginBtn = findViewById(R.id.LoginBtn);
        mCreateBtn = findViewById(R.id.CreateAccount);
        fAuth = FirebaseAuth.getInstance();

        //what happens when user clicks login
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
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

                //Authenticate the user

                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this, "Successfully Logged in", Toast.LENGTH_SHORT).show(); //gives output to user if successful (Toast)
                            startActivity(new Intent(getApplicationContext(), MainActivity.class)); //if registered, user is redirected to main activity (Start page)

                        } else {
                            Toast.makeText(Login.this, "Error !!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show(); //throws error according to it

                        }
                    }
                });
            }
        });

        //If user don't have account and clicks register account
        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });

    }


}
