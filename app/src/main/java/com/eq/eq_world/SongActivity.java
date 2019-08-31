package com.eq.eq_world;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eq.eq_world.Adapter.SongAdapter;
import com.eq.eq_world.Model.SongItem;

import java.util.ArrayList;
import java.util.List;

public class SongActivity extends AppCompatActivity {
    SongAdapter sAdapter;
    RecyclerView sRecyclerview;
    List<SongItem> sData;
    ConstraintLayout sLayout;
    EditText sSearchInput;
    CharSequence ssearch = "";
    Button btBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);

        // ini view

        sLayout = findViewById(R.id.song_layout);
        sSearchInput = findViewById(R.id.search_input_song);
        sRecyclerview = findViewById(R.id.song_rv);
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

        sAdapter = new SongAdapter(this,sData);
        sRecyclerview.setAdapter(sAdapter);
        sRecyclerview.setLayoutManager(new LinearLayoutManager(this));


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

        btBack = findViewById(R.id.songback);
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }


}
