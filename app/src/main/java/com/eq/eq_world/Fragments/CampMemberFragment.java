package com.eq.eq_world.Fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.eq.eq_world.Adapter.MemberAdapter;
import com.eq.eq_world.AddMemberActivity;
import com.eq.eq_world.CampHomeActivity;
import com.eq.eq_world.GlobalStatus;

import com.eq.eq_world.R;



public class CampMemberFragment extends Fragment {

    private RecyclerView recyclerView;
    private MemberAdapter memberAdapter;
    private Button bt_addMember;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camp_member,container,false);

        // config admin control
        bt_addMember = view.findViewById(R.id.add_member);
        bt_addMember.setVisibility(View.GONE);
        if(!GlobalStatus.myRoleInThisCamp.equals("S")){
            bt_addMember.setVisibility(View.VISIBLE);
        }
        bt_addMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddMemberActivity.class);
                startActivity(intent);
            }
        });


        // show members
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        memberAdapter = new MemberAdapter(getContext(),CampHomeActivity.memberList);
        recyclerView.setAdapter(memberAdapter);

        return view;
    }


}
