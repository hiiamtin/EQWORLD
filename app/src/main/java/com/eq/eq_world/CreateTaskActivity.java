package com.eq.eq_world;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;


public class CreateTaskActivity extends AppCompatActivity {

    private String campid;
    private EditText task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        Intent intent = getIntent();
        campid = intent.getStringExtra("camp");
        Toast.makeText(this,campid,Toast.LENGTH_SHORT).show();

    }
}
