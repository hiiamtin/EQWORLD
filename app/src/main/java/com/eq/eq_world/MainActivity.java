package com.eq.eq_world;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {
    Button btFaq;
    Button btSignin,btSignup;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // if you wanna try Camp Home Activity5
        // Delete -> //*
        //* Intent intent = new Intent(this,CampHomeActivity.class);
        //* startActivity(intent);

        autoLogin();

        btSignin = findViewById(R.id.btnSignIn);
        btSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        btSignup = findViewById(R.id.btnSignUp);
        btSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });


        btFaq = findViewById(R.id.btnFaq);
        btFaq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,FaqActivity.class);
                startActivity(intent);
            }
        });

    }

    public void autoLogin(){
        // HAVE LOGGED IN?
        // GO TO MAIN MENU AUTOMATICALLY

    }

}
