package com.eq.eq_world;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.Bitmap;

import com.eq.eq_world.Model.CampUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class MyAccountActivity extends AppCompatActivity {

    ImageView imageViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        imageViewResult = findViewById(R.id.imageViewResult);
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
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

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
                        TextView textdebug = findViewById(R.id.textdebug);
                        textdebug.setText(qr);
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


}
