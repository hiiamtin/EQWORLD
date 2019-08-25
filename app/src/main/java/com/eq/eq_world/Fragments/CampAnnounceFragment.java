package com.eq.eq_world.Fragments;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.eq.eq_world.Adapter.MessageAdapter;
import com.eq.eq_world.GlobalStatus;
import com.eq.eq_world.Model.CampUser;
import com.eq.eq_world.Model.GroupAnnounce;
import com.eq.eq_world.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CampAnnounceFragment extends Fragment {

    FirebaseUser fuser;
    DatabaseReference reference;

    ImageButton bt_send;
    EditText text_send;

    MessageAdapter messageAdapter;
    List<GroupAnnounce> mchat;

    RecyclerView recyclerView;



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

        //intent = getIntent();
        //Virtual User
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        final String temp_campName = "Camp name1";
        final String temp_uid = fuser.getUid();
        final String temp_img = "default";

        readMessage(temp_campName);


        bt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = text_send.getText().toString();
                if(!msg.equals("")){
                    sendMessage(temp_uid,msg,temp_campName,temp_img);
                }
                text_send.setText("");

            }
        });

        reference = FirebaseDatabase.getInstance().getReference("Camps").child(temp_campName);


        return view;
    }

    private void sendMessage(String sender, String message, String camp, String image){
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("sender",sender);
        hashMap.put("message",message);
        hashMap.put("image",image);

        if(GlobalStatus.isConnectedToNet(getContext())){
            dbRef.child("Camps").child(camp).child("chats").push().setValue(hashMap);
        }
        else {
            Snackbar.make(getView(),"โปรดตรวจสอบอินเตอร์เน็ต",Snackbar.LENGTH_SHORT).show();
        }


    }

    private void readMessage(final String camp){
        mchat = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Camps")
                .child(camp).child("chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mchat.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    GroupAnnounce chat = snapshot.getValue(GroupAnnounce.class);
                    mchat.add(chat);

                    String img = chat.getImage();


                    messageAdapter = new MessageAdapter(getContext(),mchat,img);
                    recyclerView.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



}
