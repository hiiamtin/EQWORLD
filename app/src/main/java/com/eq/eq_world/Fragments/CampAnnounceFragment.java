package com.eq.eq_world.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;


import com.eq.eq_world.Adapter.CampAdapter;
import com.eq.eq_world.Adapter.MessageAdapter;
import com.eq.eq_world.GlobalStatus;

import com.eq.eq_world.Model.GroupAnnounce;
import com.eq.eq_world.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CampAnnounceFragment extends Fragment {

    private FirebaseUser fuser;
    private DatabaseReference reference;

    private ImageButton bt_send;
    private EditText text_send;

    private MessageAdapter messageAdapter;
    private List<GroupAnnounce> mchat;

    private RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camp_announce,container,false);

        // RecyclerView Config
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLM = new LinearLayoutManager(getContext());
        linearLM.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLM);

        //UI
        bt_send = view.findViewById(R.id.btn_send);
        text_send = view.findViewById(R.id.text_send);

        fuser = FirebaseAuth.getInstance().getCurrentUser();
        final String this_uid = fuser.getUid();
        readMessage(CampAdapter.campid);


        bt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = text_send.getText().toString().trim();
                if(!msg.equals("")){
                    if(GlobalStatus.isConnectedToNet(getContext())) {
                        sendMessage(this_uid, msg, CampAdapter.campid);
                        text_send.setText("");
                    }
                    else{
                        Snackbar.make(getView(),"โปรดตรวจสอบการเชื่อมต่ออินเตอร์เน็ต",Snackbar.LENGTH_LONG).show();
                    }
                }
                else text_send.setText("");
            }
        });

        reference = FirebaseDatabase.getInstance().getReference("Camps").child(CampAdapter.campid);


        return view;
    }

    private void sendMessage(String sender, String message, String camp){
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("sender",sender);
        hashMap.put("message",message);
        hashMap.put("time",ServerValue.TIMESTAMP);

        dbRef.child("Camps").child(camp).child("chats").push().setValue(hashMap);
    }

    private void readMessage(final String camp){
        mchat = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mchat.clear();
                for(DataSnapshot snapshot :
                        dataSnapshot.child("Camps").child(camp).child("chats").getChildren()){
                    GroupAnnounce chat = snapshot.getValue(GroupAnnounce.class);
                    mchat.add(chat);
                    messageAdapter = new MessageAdapter(getContext(),mchat);
                    recyclerView.setAdapter(messageAdapter);
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



}
