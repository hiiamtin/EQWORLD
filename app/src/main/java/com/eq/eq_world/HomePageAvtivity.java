package com.eq.eq_world;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.eq.eq_world.Adapter.ViewPagerAdapter;
import com.eq.eq_world.Fragments.CampAnnounceFragment;
import com.eq.eq_world.Fragments.CampMemberFragment;
import com.eq.eq_world.Fragments.CampScheduleFragment;
import com.eq.eq_world.Fragments.CampSettingFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

public class HomePageAvtivity extends AppCompatActivity {

    private ImageButton bt_account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_avtivity);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.home_view_pager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPagerAdapter.addFragments(new ListActActivity(), "Activities");
        viewPagerAdapter.addFragments(new SongActivity(),"Songs");

        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

        bt_account = findViewById(R.id.bt_account);
        bt_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(GlobalStatus.isConnectedToNet(getApplicationContext())){
                    Intent intent = new Intent(getApplicationContext(),MyAccountActivity.class);
                    startActivity(intent);
                }
                else{
                    Snackbar.make(view,"โปรดเชื่อมต่ออินเตอร์เน็ต",Snackbar.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}
