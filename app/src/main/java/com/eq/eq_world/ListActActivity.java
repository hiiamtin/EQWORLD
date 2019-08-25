package com.eq.eq_world;

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

import com.eq.eq_world.Adapter.ListActAdapter;
import com.eq.eq_world.Adapter.ListActItem;

import java.util.ArrayList;
import java.util.List;

public class ListActActivity extends AppCompatActivity {
    RecyclerView lARecyclerview;
    ListActAdapter lAAdapter;
    List<ListActItem> lAData;
    ConstraintLayout lALayout;
    EditText lASearchInput;
    CharSequence lAsearch = "";
    Button btBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        // let's make this activity on full screen
        setContentView(R.layout.activity_list_act);

        // ini view

        lALayout = findViewById(R.id.act_layout);
        lASearchInput = findViewById(R.id.search_input_act);
        lARecyclerview = findViewById(R.id.act_rv);
        lAData = new ArrayList<>();


        // load theme state


        // fill list with data
        // just for testing purpose i will fill the list with random data
        // you may get your data from an api / firebase or sqlite database ...
        lAData.add(new ListActItem("CE SMART CAMP 12th","จัดโดยคณะวิศวกรรมศาสตร์ ภาควิชาวิศวกรรมคอมพิวเตอร์ สถาบันเทคโนโลยีพระจอมเกล้าเจ้าคุณทหารลาดกระบัง จัดขึ้นระหว่างวันที่ 31 พฤษภาคม-5 มิถุนายน 2562 (ค่ายพักค้างคืน 6 วัน 5 คืน)\n" +
                        "ภายในค่ายจะมีการจัดอบรมเชิงปฏิบัติการเพื่อเสริมสร้างความรู้ ให้แก่น้องๆนักเรียนในระดับชั้นมัธยมศึกษาตอนปลาย และแนะนำแนวทางในภาควิชาวิศวกรรมคอมพิวเตอร์ พร้อมกิจกรรมสนุกสนานจากพี่ๆ โดยจัดอบรมในเรื่อง Internet of Things (IoT)"));
        lAData.add(new ListActItem("Biomedical Engineering Alternative Movement Camp ครั้งที่ 4 ","ค่ายที่พวกเราวิศวกรรมชีวการแพทย์ จากสถาบันเทคโนโลยีพระจอมเกล้าเจ้าคุณทหารลาดกระบัง ตั้งใจจะพาน้องๆ ไปทำความรู้จักว่า ภาคนี้เราเรียนอะไรกัน ทำอะไรกันบ้าง น่าสนใจแค่ไหน โดยมุ่งเน้นให้น้องๆ ได้ลงมือปฏิบัติจริงด้วยการจำลอง Lab ที่มีสอนกันในชั้นเรียน รวมถึงการทำ Mini Project สุดตื่นเต้นกับเพื่อนใหม่ๆ จากทั่วประเทศและพี่ๆ พร้อมทั้งได้ประกวดนำเสนอผลงานของน้องๆ โดยพี่ๆ มีรางวัลให้กับผู้ชนะด้วย! นอกเหนือจากกิจกรรมทางด้านวิชาการแล้ว ที่ขาดไม่ได้เลยคือกิจกรรมที่มาพร้อมความสนุกสนานที่พี่ๆ คัดสรรความสนุกมาให้น้องๆกันอย่างเต็มที่ และน้องๆ จะได้พบประสบการณ์ที่จะทำให้ทุกคนยิ้ม หัวเราะ ไปตลอดการเข้าค่ายสามวัน สองคืน ในร่มรั้วชงโคของเราอย่างแน่นอน อีกทั้งสัมผัสบรรยากาศภายในคณะวิศวกรรมศาสตร์ สถาบันเทคโนโลยีพระจอมเกล้าเจ้าคุณทหารลาดกระบัง ที่ใครหลายๆ คนใฝ่ฝัน"));
        lAData.add(new ListActItem("ค่าย SUPER ENGINEER 10 (ค่ายเจาะลึกด้านวิศวะ)","ค่าย SUPER ENGINEER รุ่นที่ 10 เจาะลึกด้านวิศวะกับทีมพี่ๆ จาก 4 มหาลัย (จุฬา/ลาดกระบัง/เกษตร/มหิดล) พบกัน 31 ส.ค-1 ก.ย. 62 WORKSHOP จัดเต็ม กับพี่ๆ วิศวะตัวจริง แนะแนว พร้อมรับใบประกาศนียบัตร"));
        lAData.add(new ListActItem("CEL Camp ครั้งที่ 9” ค่ายวิศวกรรมเคมี ลาดกระบัง","ให้น้องๆ ได้รู้จักวิศวกรรมเคมี และปิโตรเคมี ร่วมกันทำกิจกรรมที่ได้ทั้งความรู้ และความสนุกสนานจากพี่ๆวิศวฯลาดกระบัง พิเศษสุดๆ กับการเยี่ยมชมโรงงานอย่างใกล้ชิด และการทำแลปแบบฉบับวิศวฯเคมีแท้ๆ!!"));
        lAData.add(new ListActItem("FECamp12 ค่ายติวความถนัดทางวิศวกรรมศาสตร์ (PAT3)","เปิดรับสมัครแล้ว เตรียมตัวพบกับค่ายติวความถนัดทางวิศวกรรมศาสตร์ (PAT 3) โดยพี่ๆ นิสิตวิศวฯ จุฬาฯ ได้เลย !"));
        lAData.add(new ListActItem("CESCa #15 ค่ายเยาวชนวิศวกรรมคอมพิวเตอร์ ครั้งที่ 15","ห้ามพลาด! สำหรับน้อง ๆ ม.ปลาย สายวิทย์ ที่มีความสนใจด้านคอมพิวเตอร์ ค่ายเยาวชนวิศวกรรมคอมพิวเตอร์จะพาน้องๆ มาสัมผัสกับบรรยากาศและกิจกรรมด้านคอมพิวเตอร์ต่างๆ มากมาย มาลอง แล้วน้องจะหลงรัก"));


        // adapter ini and setup

        lAAdapter = new ListActAdapter(this,lAData);
        lARecyclerview.setAdapter(lAAdapter);
        lARecyclerview.setLayoutManager(new LinearLayoutManager(this));


        lASearchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                lAAdapter.getFilter().filter(s);
                lAsearch = s;


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btBack = findViewById(R.id.actback);
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }


}
