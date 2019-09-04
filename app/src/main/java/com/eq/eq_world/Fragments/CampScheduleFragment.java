package com.eq.eq_world.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eq.eq_world.Adapter.DayScheduleAdapter;
import com.eq.eq_world.Model.DaySchedule;
import com.eq.eq_world.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CampScheduleFragment extends Fragment {

    private RecyclerView recyclerView;
    private DayScheduleAdapter dayScheduleAdapter;
    private List<DaySchedule> eventList;
    private DatabaseReference dbRef;
    private boolean done = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camp_schedule,container,false);

        // RecyclerView Config
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLM = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLM);

        readSchedule("Camp name1");

        return view;
    }

    private boolean readSchedule(final String camp){
        eventList = new ArrayList<>();
        dbRef = FirebaseDatabase.getInstance().getReference("Camps")
                .child(camp).child("schedule");

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                eventList.clear();
                for (DataSnapshot snapdate : dataSnapshot.getChildren()) {
                    DaySchedule daySchedule = new DaySchedule();
                    HashMap<String,String> hashMap = new HashMap<>();
                    String day = snapdate.getKey();
                    daySchedule.setDate(day);
                    for (DataSnapshot snaptime : dataSnapshot.child(day).getChildren()) {
                        hashMap.put(snaptime.getKey(),snaptime.getValue(String.class));
                    }
                    daySchedule.events = hashMap;
                    eventList.add(daySchedule);
                }

                dayScheduleAdapter = new DayScheduleAdapter(getContext(),eventList);
                recyclerView.setAdapter(dayScheduleAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return done;
    }

}

