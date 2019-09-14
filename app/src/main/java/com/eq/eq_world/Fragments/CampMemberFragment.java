package com.eq.eq_world.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eq.eq_world.Adapter.CampAdapter;
import com.eq.eq_world.Adapter.MemberAdapter;
import com.eq.eq_world.CampHomeActivity;
import com.eq.eq_world.GlobalStatus;

import com.eq.eq_world.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class CampMemberFragment extends Fragment {

    private RecyclerView recyclerView;
    private MemberAdapter memberAdapter;
    private Button bt_addMember, push_member;
    private LinearLayout add_bar;
    private boolean click;
    private RadioButton r_head, r_staff;
    private EditText txt_uid;
    private TextView txt_complete;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camp_member,container,false);


        // config admin control
        bt_addMember = view.findViewById(R.id.add_member);
        add_bar = view.findViewById(R.id.add_bar);
        click = false;
        bt_addMember.setVisibility(View.GONE);
        if(GlobalStatus.myRoleInThisCamp.equals("A")||GlobalStatus.myRoleInThisCamp.equals("H")){
            bt_addMember.setVisibility(View.VISIBLE);
        }
        toggleButton();


        // adding member
        r_head = view.findViewById(R.id.radio_head);
        r_staff = view.findViewById(R.id.radio_staff);
        push_member = view.findViewById(R.id.push_member);
        txt_uid = view.findViewById(R.id.txt_uid);
        txt_complete = view.findViewById(R.id.txt_complete);

        push_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txt_uid.getText().toString().trim().length()==28){
                    addMemberToCamp(txt_uid.getText().toString(), CampAdapter.campid);
                }
                else {
                    Toast.makeText(view.getContext(),"โปรดตรวจสอบ ID", Toast.LENGTH_LONG).show();
                }

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


    private void toggleButton(){
        bt_addMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click = !click;
                if(click){
                    add_bar.setVisibility(View.VISIBLE);
                    bt_addMember.setText("(x) ปิดหน้านี้");
                }
                else {
                    add_bar.setVisibility(View.GONE);
                    bt_addMember.setText("+ เพิ่มสมาชิกใหม่");
                    txt_complete.setVisibility(View.GONE);
                    txt_uid.setText("");
                }
            }
        });
    }

    @NonNull
    private String selectedRole(){
        if(r_head.isChecked()) return "H";
        else if(r_staff.isChecked()) return "S";
        else return "X";
    }

    private void addMemberToCamp(String id, String camp){
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Camps")
                .child(camp).child("members");
        if(selectedRole().equals("X")){
            Toast.makeText(getContext(),"โปรดเลือกตำแหน่ง", Toast.LENGTH_LONG).show();
        }
        else {
            dbRef.child(id).setValue(selectedRole());
            txt_complete.setText("เพิ่มสมาชิกสำเร็จ");
            txt_complete.setVisibility(View.VISIBLE);
        }

    }


}
