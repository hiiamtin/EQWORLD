package com.eq.eq_world;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.eq.eq_world.Adapter.ViewPagerAdapter;
import com.eq.eq_world.Fragments.CampAnnounceFragment;
import com.eq.eq_world.Fragments.CampMemberFragment;
import com.eq.eq_world.Fragments.CampScheduleFragment;
import com.eq.eq_world.Fragments.CampSettingFragment;
import com.google.android.material.tabs.TabLayout;

public class HomePageAvtivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_avtivity);

        TabLayout tabLayout = findViewById(R.id.home_tap_layout);
        ViewPager viewPager = findViewById(R.id.home_view_pager);
        ViewPagerAdapter vpa =
                new ViewPagerAdapter(getSupportFragmentManager());
        vpa.addFragments(new ListActActivity(), "Activity");
        vpa.addFragments(new SongActivity(), "Song");
        viewPager.setAdapter(vpa);
        viewPager.setOffscreenPageLimit(1);
        tabLayout.setupWithViewPager(viewPager);

    }
}
