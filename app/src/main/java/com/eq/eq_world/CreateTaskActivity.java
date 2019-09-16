package com.eq.eq_world;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.eq.eq_world.Fragments.SelectDateFragment;
import com.eq.eq_world.Fragments.SelectTimeFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class CreateTaskActivity extends AppCompatActivity {

    private String campid;
    private EditText t_task;

    private Button bt_date,bt_time,bt_save;
    private ImageButton bt_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        Intent intent = getIntent();
        campid = intent.getStringExtra("camp");

        bt_back = findViewById(R.id.bt_back);
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        bt_date = findViewById(R.id.bt_date);
        bt_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dateFrag = new SelectDateFragment();
                ((SelectDateFragment) dateFrag).buttonToShow(bt_date);
                dateFrag.show(getSupportFragmentManager(),"DatePicker");
            }
        });

        bt_time = findViewById(R.id.bt_time);
        bt_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timeFrag = new SelectTimeFragment();
                ((SelectTimeFragment) timeFrag).buttonToShow(bt_time);
                timeFrag.show(getSupportFragmentManager(),"TimePicker");
            }
        });

        t_task = findViewById(R.id.c_title);
        bt_save = findViewById(R.id.bt_save);
        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = t_task.getText().toString();
                pushNewTask(title,bt_date.getText().toString(),bt_time.getText().toString());
            }
        });
    }

    private void pushNewTask(String task, String date, String time){
        if(task.trim().equals("")){
            Toast.makeText(this,"กรุณาระบุชื่อกิจกรรม",Toast.LENGTH_SHORT).show();
        }
        else if(date.equals("ระบุวันที่")){
            Toast.makeText(this,"กรุณาระบุวันที่",Toast.LENGTH_SHORT).show();
        }
        else if(time.equals("ระบุเวลา")){
            Toast.makeText(this,"กรุณาระบุเวลา",Toast.LENGTH_SHORT).show();
        }
        else{
            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("Camps")
                    .child(campid).child("schedule").child(date).child(time);
            dbRef.setValue(task);
            Toast.makeText(this, "บันทึกกิจกรรมเรียบร้อย",Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
