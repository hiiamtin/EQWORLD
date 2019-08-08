package com.eq.eq_world;


import android.app.ProgressDialog;
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
import com.google.firebase.database.DatabaseReference;
import com.rengwuxian.materialedittext.MaterialEditText;

public class RegisterActivity extends AppCompatActivity {

    MaterialEditText username, email, password;
    Button btn_reg;
    FirebaseAuth auth;
    DatabaseReference reference;
    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Progressbar Config
        loading = new ProgressDialog(RegisterActivity.this);
        loading.setMessage("Welcome to EQ World");
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        // UI Bind
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btn_reg = findViewById(R.id.btn_reg);

        // Firebase Config
        auth = FirebaseAuth.getInstance();

        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String txt_username = username.getText().toString();
                String txt_password = password.getText().toString();
                String txt_email = email.getText().toString();

                if(TextUtils.isEmpty(txt_username) || TextUtils.isEmpty(txt_password) || TextUtils.isEmpty(txt_email)){
                    Toast.makeText(RegisterActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                }
                else if(txt_password.length() < 6){
                    Toast.makeText(RegisterActivity.this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                }
                else{
                    register(txt_username,txt_email,txt_password);
                }
            }
        });
    }

    public void register(final String username, String email, String password) {

        loading.show();
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            Toast.makeText(RegisterActivity.this, "ลงทะเบียนแล้วจ้า", Toast.LENGTH_SHORT).show();
                            loading.dismiss();

                            // ---------------------------------- //
                            // WHAT TO DO WHEN SIGN UP COMPLETELY //
                            // ---------------------------------- //

                        }
                        else{
                            loading.dismiss();
                            Toast.makeText(RegisterActivity.this,"Please Try Again",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
