package com.eq.eq_world.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eq.eq_world.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CampSettingFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camp_setting,container,false);

        //onCreate here

        return view;
    }

}
