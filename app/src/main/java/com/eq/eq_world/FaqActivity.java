package com.eq.eq_world;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.eq.eq_world.Adapter.FaqAdapter;
import com.eq.eq_world.Adapter.FaqItem;

import java.util.ArrayList;
import java.util.List;

public class FaqActivity extends AppCompatActivity {

    RecyclerView NewsRecyclerview;
    FaqAdapter newsAdapter;
    List<FaqItem> mData;
    ConstraintLayout rootLayout;
    EditText searchInput;
    CharSequence search = "";
    Button btBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        // let's make this activity on full screen
        setContentView(R.layout.activity_faq_main);

        // ini view

        rootLayout = findViewById(R.id.root_layout);
        searchInput = findViewById(R.id.search_input);
        NewsRecyclerview = findViewById(R.id.news_rv);
        mData = new ArrayList<>();

        // load theme state


        // fill list news with data
        // just for testing purpose i will fill the news list with random data
        // you may get your data from an api / firebase or sqlite database ...
        mData.add(new FaqItem("OnePlus 6T Camera Review:", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.", R.drawable.faq));
        mData.add(new FaqItem("I love Programming And Design", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,", R.drawable.faq));
        mData.add(new FaqItem("My first trip to Thailand story ", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.", R.drawable.faq));
        mData.add(new FaqItem("After Facebook Messenger, Viber now gets...", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s,", R.drawable.faq));
        mData.add(new FaqItem("Isometric Design Grid Concept", "lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit", R.drawable.faq));
        mData.add(new FaqItem("Android R Design Concept 4K", "lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit lorem ipsum dolor sit ", R.drawable.faq));
        mData.add(new FaqItem("เบี้ยประกันที่จะคืนทุกๆ 3 ปี จะคืนค่าเบี้ยทั้งหมดหรือไม่", "กรณีคุณไม่มีประวัติการเคลมครบทุก 3 ปี คุณจะได้รับเบี้ยประกันที่คุณชำระมาในปีกรมธรรม์ที่ 3 คืนค่ะ", R.drawable.faq));
        mData.add(new FaqItem("เบี้ยประกันคงที่หรือไม่", "ชำระเบี้ยประกันคงที่ตลอดอายุสัญญาค่ะ", R.drawable.faq));
        mData.add(new FaqItem("กรณีประสบอุบัติเหตุจากการขับขี่หรือโดยสารรถจักรยานยนต์ โครงการนี้คุ้มครองหรือไม่", "ไม่ว่าคุณจะประสบอุบัติเหตุจากการลื่นล้ม จากการขับรถยนต์ หรือจากการขับขี่รถจักรยานยนต์จนต้องนอนพักรักษาตัวในโรงพยาบาล เราก็ให้ความคุ้มครองค่ะ เพราะโครงการนี้พร้อมดูแลคุณเมื่อได้รับบาดเจ็บ คุณจะได้รับผลประโยชน์รายวัน กรณีเข้าพักรักษาตัวในโรงพยาบาล ตามแผนประกันที่คุณเลือก หรือหากคุณเกิดต้องเข้ารับการรักษาตัวในห้องไอซียู หรือผ่าตัดจากอุบัติเหตุ คุณจะได้รับผลประโยชน์รายวันเป็น 2 เท่า", R.drawable.faq));
        mData.add(new FaqItem("เบี้ยประกันของโครงการนี้ สามารถนำไปลดหย่อนภาษีได้หรือไม่", "เฉพาะเบี้ยประกันชีวิตของโครงการนี้เท่านั้นที่ สามารถ นำไปใช้สิทธิหักลดหย่อนภาษีเงินได้บุคคลธรรมดาตามประกาศอธิบดีกรมสรรพากรเกี่ยวกับภาษีเงินได้ ฉบับที่ 172 ซึ่งประกาศ ณ วันที่ 30 ธ.ค.51", R.drawable.faq));
        mData.add(new FaqItem("กุ๊กๆeieizahaha", "try to search", R.drawable.faq));


        // adapter ini and setup

        newsAdapter = new FaqAdapter(this, mData);
        NewsRecyclerview.setAdapter(newsAdapter);
        NewsRecyclerview.setLayoutManager(new LinearLayoutManager(this));


        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                newsAdapter.getFilter().filter(s);
                search = s;


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btBack = findViewById(R.id.btnback);
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }


}
