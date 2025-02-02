package com.ds.sgvp_login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    //Logout Function
    public void logout (View view) {
        FirebaseAuth.getInstance().signOut(); //logout the user
        startActivity(new Intent(getApplicationContext(),Login.class)); //sends back to login screen
        finish();
    }
}
