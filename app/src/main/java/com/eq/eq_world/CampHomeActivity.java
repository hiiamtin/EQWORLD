package com.eq.eq_world;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;


import android.app.ProgressDialog;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


import com.eq.eq_world.Adapter.CampAdapter;
import com.eq.eq_world.Adapter.ViewPagerAdapter;
import com.eq.eq_world.Fragments.CampAnnounceFragment;
import com.eq.eq_world.Fragments.CampMemberFragment;
import com.eq.eq_world.Fragments.CampScheduleFragment;
import com.eq.eq_world.Fragments.CampSettingFragment;
import com.eq.eq_world.Model.CampUser;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CampHomeActivity extends AppCompatActivity {

    public static List<String> mUsers;
    public static List<CampUser> memberList;
    private ProgressDialog loadingBar;
    private ImageButton bt_button;
    private String myUID;
    private TextView camp_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camp_home);

        GlobalStatus.currentCamp = CampAdapter.campid;

        myUID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        navigationTabConfig();

        camp_name = findViewById(R.id.camp_name);
        camp_name.setText(CampAdapter.campid);

        // loading bar config
        loadingBar = new ProgressDialog(this);
        loadingBar.setMessage("ถ้าโหลดนานเกินไป โปรดตรวจสอบการเชื่อมต่ออินเตอร์เน็ต");
        loadingBar.setTitle("Staff Home");
        loadingBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loadingBar.setCancelable(false);
        loadingBar.show();


        // back button config
        bt_button = findViewById(R.id.bt_back);
        bt_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        // load everything from database
        mUsers = new ArrayList<>();
        memberList = new ArrayList<>();

        readMemberList(CampAdapter.campid);

    }

    private void readMemberList(final String camp){

        final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long numOfMem = dataSnapshot.child("Camps").child(camp)
                        .child("members").getChildrenCount();
                mUsers.clear();
                while (mUsers.size()!=numOfMem) {
                    for (DataSnapshot snapshot : dataSnapshot.child("Camps")
                            .child(camp).child("members").getChildren()) {
                        String memberID = snapshot.getKey()+"@"+snapshot.getValue(String.class);
                        assert memberID != null;
                        mUsers.add(memberID);
                    }
                }
                memberList.clear();
                for (String memberID : mUsers){
                    String[] uid = memberID.split("@");
                    CampUser member = dataSnapshot.child("Users").child(uid[0])
                            .getValue(CampUser.class);
                    if(member!=null){
                        member.setRole(uid[1]);
                        memberList.add(member);
                        if(member.getId().equals(myUID)){
                            GlobalStatus.myRoleInThisCamp = member.getRole();
                        }
                    }
                }
                loadingBar.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void navigationTabConfig(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new CampAnnounceFragment());
        transaction.commit();

        ImageButton bt_chat, bt_schedule, bt_member, bt_setting;

        bt_chat = findViewById(R.id.bt_chat);
        bt_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new CampAnnounceFragment());
                transaction.commit();
            }
        });

        bt_schedule = findViewById(R.id.bt_schedule);
        bt_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new CampScheduleFragment());
                transaction.commit();
            }
        });

        bt_member = findViewById(R.id.bt_member);
        bt_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new CampMemberFragment());
                transaction.commit();
            }
        });

        bt_setting = findViewById(R.id.bt_setting);
        bt_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new CampSettingFragment());
                transaction.commit();
            }
        });
    }


}
