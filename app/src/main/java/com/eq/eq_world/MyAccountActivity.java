package com.eq.eq_world;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.graphics.Bitmap;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.eq.eq_world.Model.CampUser;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.HashMap;
import java.util.List;

public class MyAccountActivity extends AppCompatActivity {

    ImageView imageViewResult;
    private FirebaseAuth mAuth;
    private String dbVer;

    private ProgressDialog loadingBar;
    private ImageView profile_image;

    StorageReference storageReference;
    private static final int IMAGE_REQUEST = 1;
    private Uri imageUri;
    private StorageTask uploadTask;

    private LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        showLoadingDialog();

        imageViewResult = findViewById(R.id.imageViewResult);
        setUpAllButton();

        //Check Version
        TextView version = findViewById(R.id.version);
        String strVer = "2019 - EQWorld ( Version: "+GlobalStatus.VERSION+" )";
        version.setText(strVer);

        //Users Profile Img
        storageReference = FirebaseStorage.getInstance().getReference("uploads");
        profile_image = findViewById(R.id.img_user);
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImage();
            }
        });

    }

    private void openImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMAGE_REQUEST);
    }

    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getApplicationContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private  void uploadImage(){

        showLoadingDialog();

        if (imageUri != null){
            final StorageReference fileRef = storageReference.child(GlobalStatus.myAccount.getId() +
                    "." + getFileExtension(imageUri));
            uploadTask = fileRef.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if(!task.isSuccessful()){
                        throw task.getException();
                    }

                    return fileRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()){
                        Uri downloadUri = task.getResult();
                        String mUri = downloadUri.toString();

                        DatabaseReference reference = FirebaseDatabase.getInstance()
                                .getReference("Users").child(GlobalStatus.myAccount.getId());
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("imageURL",mUri);
                        reference.updateChildren(map);

                        //////////////loadingBar.dismiss();

                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();
                        ///////////loadingBar.dismiss();
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    ///////loadingBar.dismiss();
                }
            });
        }
        else{
            Toast.makeText(getApplicationContext(),"No image selected",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null){
            imageUri = data.getData();

            if(uploadTask != null && uploadTask.isInProgress()){
                Toast.makeText(getApplicationContext(),"กำลังอัพโหลด",Toast.LENGTH_SHORT).show();
            }
            else{
                uploadImage();
            }
        }
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

        linearLayout = findViewById(R.id.button_layout);

        final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dbVer = dataSnapshot.child("App").child("version").getValue(String.class);

                GlobalStatus.myAccount = dataSnapshot
                        .child("Users")
                        .child(mAuth.getCurrentUser().getUid())
                        .getValue(CampUser.class);

                    try{
                        if(!dbVer.equals(GlobalStatus.VERSION)) showUpdateDialog();

                        String qr = "EQ@" + GlobalStatus.myAccount.getId() + "@"
                                + GlobalStatus.myAccount.getUsername();
                        Bitmap bmp = encodeAsBitmap(qr);
                        imageViewResult.setImageBitmap(bmp);

                        TextView show_user = findViewById(R.id.txt_user);
                        show_user.setText(GlobalStatus.myAccount.getUsername());

                        if(GlobalStatus.myAccount.getImageURL().equals("default")){
                            profile_image.setImageResource(R.drawable.uploadpic);
                        }
                        else{
                            Glide.with(getApplicationContext())
                                    .load(GlobalStatus.myAccount.getImageURL())
                                    .into(profile_image);
                        }
                        loadingBar.dismiss();
                        linearLayout.setVisibility(View.VISIBLE);

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

        GlobalStatus.clearUserData();

        finish();

    }

    private void showUpdateDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("กรุณาอัพเดทแอพเป็นเวอร์ชั่นล่าสุดก่อนใช้งาน");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showLoadingDialog(){
        // loading bar config
        loadingBar = new ProgressDialog(this);
        loadingBar.setMessage("ถ้าโหลดนานเกินไป โปรดตรวจสอบการเชื่อมต่ออินเตอร์เน็ต");
        loadingBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loadingBar.setCancelable(false);
        loadingBar.show();
    }

}
