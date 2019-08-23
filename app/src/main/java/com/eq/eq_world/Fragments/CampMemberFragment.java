package com.eq.eq_world.Fragments;


import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eq.eq_world.Adapter.MemberAdapter;
import com.eq.eq_world.CampHomeActivity;
import com.eq.eq_world.Model.CampUser;
import com.eq.eq_world.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class CampMemberFragment extends Fragment {

    private RecyclerView recyclerView;
    private MemberAdapter memberAdapter;

    boolean gotResult = false;
    Timer timer = new Timer();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camp_member,container,false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        memberAdapter = new MemberAdapter(getContext(),CampHomeActivity.memberList);
        recyclerView.setAdapter(memberAdapter);


        return view;
    }






}
