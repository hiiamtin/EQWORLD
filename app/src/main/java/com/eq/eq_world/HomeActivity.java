package com.eq.eq_world;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    Button logout, cChat, btFaq, btAct, btSong;
    FirebaseUser firebaseUser;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();

        loadUserInfo(mAuth.getCurrentUser().getUid());

        logout = findViewById(R.id.out);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });

        cChat = findViewById(R.id.chat);
        cChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,CampHomeActivity.class);
                startActivity(intent);
            }
        });

        btAct = findViewById(R.id.btnlistact);
        btAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListActActivity.class);
                startActivity(intent);
            }
        });

        btFaq = findViewById(R.id.btnFaq);
        btFaq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FaqActivity.class);
                startActivity(intent);
            }
        });

        btSong = findViewById(R.id.btn_song);
        btSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SongActivity.class);
                startActivity(intent);
            }
        });


    }

    private void signOut(){
        String text="";
        List<? extends UserInfo> providerData = mAuth.getCurrentUser().getProviderData();
        for (UserInfo userInfo : providerData) {
            switch (userInfo.getProviderId()){
                case "facebook.com":
                    LoginManager.getInstance().logOut();
                    Toast.makeText(this, "facebook logout" ,Toast.LENGTH_SHORT).show();
            }
        }
        mAuth.signOut();


        // Google sign out
        GoogleSignInClient mGoogleSignInClient;
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("34452559215-h1fghpvh56e9kc68ej211qvriejfakb3.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mGoogleSignInClient.signOut();
        /*if(FacebookLoginActivity.tokenFB!=null){
            LoginManager.getInstance().logOut();
        }*/
        Intent goMain = new Intent(HomeActivity.this, MainActivity.class);
        startActivity(goMain);
        finish();

    }

    private void loadUserInfo(String uid){
        final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Users")
                .child(uid);
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GlobalStatus.currentUsername = dataSnapshot.child("username").getValue(String.class);
                GlobalStatus.currentImg = dataSnapshot.child("imageURL").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
