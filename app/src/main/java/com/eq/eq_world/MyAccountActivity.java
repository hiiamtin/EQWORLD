package com.eq.eq_world;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.Bitmap;
import android.widget.Toast;

import com.eq.eq_world.Model.CampUser;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.List;

public class MyAccountActivity extends AppCompatActivity {

    ImageView imageViewResult;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        imageViewResult = findViewById(R.id.imageViewResult);
        setUpAllButton();

    }

    Bitmap encodeAsBitmap(String str) throws WriterException {
        BitMatrix result;
        final int WHITE = 0xFFFFFFFF;
        final int BLUE = 0xFF118BD1;
        try {
            result = new MultiFormatWriter()
                    .encode(str, BarcodeFormat.QR_CODE, 400, 400, null);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            int offset = y * w;
            for (int x = 0; x < w; x++) {
                pixels[offset + x] = result.get(x, y) ? BLUE : WHITE;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, 400, 0, 0, w, h);
        return bitmap;
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
                    try{
                        String qr = "EQ@" + GlobalStatus.myAccount.getId() + "@"
                                + GlobalStatus.myAccount.getUsername();
                        Bitmap bmp = encodeAsBitmap(qr);
                        imageViewResult.setImageBitmap(bmp);

                        TextView show_user = findViewById(R.id.txt_user);
                        show_user.setText(GlobalStatus.myAccount.getUsername());

                        ImageView profile_image = findViewById(R.id.img_user);
                        profile_image.setImageResource(R.mipmap.ic_launcher);

                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void setUpAllButton(){
        ImageButton imageButton = findViewById(R.id.bt_back);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button bt_staff = findViewById(R.id.bt_staff);
        bt_staff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),CampListActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button bt_faq = findViewById(R.id.bt_faq);
        bt_faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),FaqActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button bt_out = findViewById(R.id.bt_out);
        bt_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),CampListActivity.class);
                startActivity(intent);
                signOut();
                finish();
            }
        });
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
        Intent goMain = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(goMain);
        finish();

    }



}
