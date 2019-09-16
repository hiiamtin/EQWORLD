package com.eq.eq_world;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.eq.eq_world.Adapter.CampAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class AddMemberActivity extends AppCompatActivity {

    private Button push_member, bt_scan;
    private ImageButton bt_back;
    private RadioButton r_head, r_staff;
    private String productId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);

        // adding member
        r_head = findViewById(R.id.radio_head);
        r_staff = findViewById(R.id.radio_staff);
        push_member = findViewById(R.id.push_member);

        bt_scan = findViewById(R.id.bt_scan);
        bt_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator intentIntegrator
                        = new IntentIntegrator(AddMemberActivity.this);
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                intentIntegrator.setCameraId(0);
                intentIntegrator.initiateScan();
            }
        });

        push_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(productId != null){
                    addMemberToCamp(productId, CampAdapter.campid);
                }
            }
        });

        bt_back = findViewById(R.id.bt_back);
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @NonNull
    private String selectedRole(){
        if(r_head.isChecked()) return "H";
        else if(r_staff.isChecked()) return "S";
        else return "X";
    }

    private void addMemberToCamp(String id, String camp){
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Camps")
                .child(camp).child("members");
        if(selectedRole().equals("X")){
            Toast.makeText(this,"โปรดเลือกตำแหน่ง", Toast.LENGTH_LONG).show();
        }
        else {
            dbRef.child(id).setValue(selectedRole());
            Toast.makeText(this,"เพิ่มสมาชิกสำเร็จแล้ว",Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            productId = intentResult.getContents();
            bt_scan.setText(productId);
        }
    }
}
