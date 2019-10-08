package com.eq.eq_world;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.eq.eq_world.Adapter.CampAdapter;
import com.eq.eq_world.Model.Camp;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

public class CampListActivity extends AppCompatActivity {

    private List<Camp> campList;
    private CampAdapter campAdapter;
    private RecyclerView recyclerView;
    private DatabaseReference reference;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camp_list);

        /* Tintin's code
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Camps");
        setSupportActionBar(toolbar);
        */

        FloatingActionButton fab = findViewById(R.id.fab);
        if(GlobalStatus.myRole.equals("A")){
            fab.setVisibility(View.VISIBLE);
        }else{
            fab.setVisibility(View.GONE);
        }
        setupfab(fab);
        setupRecycleView();

        reference = FirebaseDatabase.getInstance().getReference("Camps");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                campList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Camp camp = new Camp(snapshot.getKey());
                    camp.setPassWord(snapshot.child("password").getValue().toString());
                    camp.setName(snapshot.child("name").getValue().toString());
                    camp.setDate(snapshot.child("date").getValue().toString());
                    camp.setImage(snapshot.child("image").getValue().toString());
                    camp.setLocation(snapshot.child("location").getValue().toString());
                    camp.setDetail(snapshot.child("detail").getValue().toString());
                    List<String> members = new ArrayList<>();
                    for (DataSnapshot s : snapshot.child("members").getChildren()) {
                        String memberID = s.getKey();
                        assert memberID != null;
                        members.add(memberID);
                    }
                    camp.setMembers(members);
                    campList.add(camp);
                }
                campAdapter = new CampAdapter(CampListActivity.this,campList);
                recyclerView.setAdapter(campAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ImageButton bt_back = findViewById(R.id.bt_back);
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void setupfab(FloatingActionButton fab){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent intent = new Intent(CampListActivity.this, CampCreateActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    private void setupRecycleView(){
        // RecyclerView Config
        recyclerView = findViewById(R.id.camp_recyclerview);
        //recyclerView.setHasFixedSize(true);
        campList = new ArrayList<>();
        Camp camp = new Camp("Camp id");
        camp.setName("Camp name");
        camp.setDate("เวลา:");
        campList.add(camp);
        campAdapter = new CampAdapter(this,campList);
        recyclerView.setAdapter(campAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


}
