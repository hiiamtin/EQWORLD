package com.eq.eq_world;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.eq.eq_world.Adapter.ViewPagerAdapter;
import com.eq.eq_world.Fragments.CampAnnounceFragment;
import com.eq.eq_world.Fragments.CampMemberFragment;
import com.eq.eq_world.Fragments.CampScheduleFragment;
import com.eq.eq_world.Fragments.CampSettingFragment;
import com.google.android.material.tabs.TabLayout;

public class CampHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camp_home);

        // UI Process
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.view_pager);
        ViewPagerAdapter vpa =
                new ViewPagerAdapter(getSupportFragmentManager());
        vpa.addFragments(new CampAnnounceFragment(), "Announcements");
        vpa.addFragments(new CampScheduleFragment(), "Schedule");
        vpa.addFragments(new CampMemberFragment(), "Members");
        vpa.addFragments(new CampSettingFragment(), "Settings");

        viewPager.setAdapter(vpa);
        tabLayout.setupWithViewPager(viewPager);

    }
}
