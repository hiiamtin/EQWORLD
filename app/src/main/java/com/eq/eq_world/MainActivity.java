package com.eq.eq_world;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {
    Button btSignin,btSignup;
    FirebaseUser firebaseUser;

    View view;
    ImageView logoEQ;
    RelativeLayout layout1,layout2;
    ImageButton btnGoogle,btnFacebook;

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            layout1.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.VISIBLE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logoEQ = findViewById(R.id.logo);
        //Animation aniRotate =  AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_clockwise);
        //logoEQ.startAnimation(aniRotate);
        //Animation aniSlide = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide);
        //logoEQ.startAnimation(aniSlide);
        layout1 = findViewById(R.id.reLayout);
        layout2 = findViewById(R.id.rellay_welcome);

        handler.postDelayed(runnable,1500);
        //view.setBackgroundResource(R.color.white);

        autoLogin();

        btSignin = findViewById(R.id.btnSignIn);
        btSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btSignup = findViewById(R.id.btnSignUp);
        btSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnGoogle = (ImageButton) findViewById(R.id.btnGoogle);
        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GoogleSignInActivity.class);
                startActivity(intent);
            }
        });

        btnFacebook = (ImageButton) findViewById(R.id.btnFacebook);
        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FacebookLoginActivity.class);
                startActivity(intent);
            }
        });

    }

    public void autoLogin(){
        // HAVE LOGGED IN?
        // GO TO MAIN MENU AUTOMATICALLY
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser != null){
            Intent chatMenu = new Intent(this,HomePageAvtivity.class);
            startActivity(chatMenu);
        }
    }

}
