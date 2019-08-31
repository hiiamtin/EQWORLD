package com.eq.eq_world;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.eq.eq_world.Adapter.CampAdapter;
import com.eq.eq_world.Model.Camp;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CampListActivity extends AppCompatActivity {

    private List<Camp> campList;
    private CampAdapter campAdapter;
    private RecyclerView recyclerView;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camp_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
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
                    camp.setCreator(snapshot.child("creator").getValue().toString());
                    camp.setImage(snapshot.child("image").getValue().toString());
                    camp.setLocation(snapshot.child("location").getValue().toString());
                    campList.add(camp);
                }
                campAdapter = new CampAdapter(CampListActivity.this,campList);
                recyclerView.setAdapter(campAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void setupfab(FloatingActionButton fab){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void setupRecycleView(){
        // RecyclerView Config
        recyclerView = findViewById(R.id.camp_recyclerview);
        //recyclerView.setHasFixedSize(true);
        campList = new ArrayList<>();
        Camp camp = new Camp("Camp name");
        camp.setName("Camp name");
        camp.setDate("เวลา:");
        campList.add(camp);
        campAdapter = new CampAdapter(this,campList);
        recyclerView.setAdapter(campAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
