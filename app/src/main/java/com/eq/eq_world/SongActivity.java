package com.eq.eq_world;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eq.eq_world.Adapter.SongAdapter;
import com.eq.eq_world.Model.SongItem;

import java.util.ArrayList;
import java.util.List;

public class SongActivity extends Fragment {
    SongAdapter sAdapter;
    RecyclerView sRecyclerview;
    List<SongItem> sData;
    ConstraintLayout sLayout;
    EditText sSearchInput;
    CharSequence ssearch = "";
    Button btBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_song,container,false);

        // ini view

        sLayout = view.findViewById(R.id.song_layout);
        sSearchInput = view.findViewById(R.id.search_input_song);
        sRecyclerview = view.findViewById(R.id.song_rv);
        sData = new ArrayList<>();

        sData.add(new SongItem("ฮิปโป","2S24-y0Ij3Y"));
        sData.add(new SongItem("นกกระยาง","BVwAVbKYYeM"));
        sData.add(new SongItem("สู้ๆ","vecSVX1QYbQ"));
        sData.add(new SongItem("ลิงกินกระท้อน","Nu2yQ1zYDYU"));
        sData.add(new SongItem("เก๊กฮสน","6eEZ7DJMzuk"));
        sData.add(new SongItem("ฮิปโป","2S24-y0Ij3Y"));
        sData.add(new SongItem("นกกระยาง","BVwAVbKYYeM"));
        sData.add(new SongItem("สู้ๆ","vecSVX1QYbQ"));
        sData.add(new SongItem("ลิงกินกระท้อน","Nu2yQ1zYDYU"));
        sData.add(new SongItem("เก๊กฮสน","6eEZ7DJMzuk"));

        sAdapter = new SongAdapter(view.getContext(),sData);
        sRecyclerview.setAdapter(sAdapter);
        sRecyclerview.setLayoutManager(new LinearLayoutManager(view.getContext()));

        sSearchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                sAdapter.getFilter().filter(s);
                ssearch = s;

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }


}
