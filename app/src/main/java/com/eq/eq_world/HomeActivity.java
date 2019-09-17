package com.eq.eq_world;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.eq.eq_world.Model.CampUser;
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

    Button logout, cChat, btFaq, btAct, btSong, btCamp;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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
                Intent intent = new Intent(HomeActivity.this,HomePageAvtivity.class);
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

        btCamp = findViewById(R.id.btn_camp);
        btCamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CampListActivity.class);
                startActivity(intent);
            }
        });

        //JUST DEBUGGING >>>>
        TextView textView = findViewById(R.id.textView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyAccountActivity.class);
                startActivity(intent);
            }
        });
        //JUST DEBUGGING <<<<
    }

    private void signOut(){
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

    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();

        final DatabaseReference dbRef = FirebaseDatabase.getInstance()
                .getReference("Users").child(mAuth.getCurrentUser().getUid());
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GlobalStatus.myAccount = dataSnapshot.getValue(CampUser.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
