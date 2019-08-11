package com.eq.eq_world;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.rengwuxian.materialedittext.MaterialEditText;


public class LoginActivity extends AppCompatActivity {

    MaterialEditText email,password;
    Button btn_login;
    FirebaseAuth auth;
    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_password);

        // Progressbar Config
        loading = new ProgressDialog(LoginActivity.this);
        loading.setMessage("Welcome back!");
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        // Firebase Config
        auth = FirebaseAuth.getInstance();

        // UI Bind
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btn_login = findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loading.show();
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();

                if(TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)){
                    Toast.makeText(LoginActivity.this, "All fields are required",Toast.LENGTH_SHORT).show();
                    loading.dismiss();

                }
                else{
                    auth.signInWithEmailAndPassword(txt_email,txt_password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){

                                        loading.dismiss();

                                        // -------------------- //
                                        // GO TO MAIN MENU HERE //
                                        Intent chatMenu = new Intent(LoginActivity.this,ChatListActivity.class);
                                        startActivity(chatMenu);
                                        finish();
                                        // -------------------- //

                                    }
                                    else{
                                        loading.dismiss();
                                        Toast.makeText(LoginActivity.this,"Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }

}
