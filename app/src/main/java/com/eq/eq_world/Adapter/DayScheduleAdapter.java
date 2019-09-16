package com.eq.eq_world.Adapter;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eq.eq_world.CampHomeActivity;
import com.eq.eq_world.CreateTaskActivity;
import com.eq.eq_world.GlobalStatus;
import com.eq.eq_world.Model.DaySchedule;
import com.eq.eq_world.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class DayScheduleAdapter extends RecyclerView.Adapter<DayScheduleAdapter.ViewHolder>{
    private Context mContext;
    private List<DaySchedule> mDay;

    public DayScheduleAdapter(Context mContext, List<DaySchedule> mDay) {
        this.mContext = mContext;
        this.mDay = mDay;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.day_item,parent,false);
        return new DayScheduleAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final DaySchedule daySchedule = mDay.get(position);

        holder.show_time.setText(daySchedule.getTime());

        if(daySchedule.getObjective().equals("date")){
            holder.event_box.setBackgroundResource(R.color.colorPrimary);
        }
        else if(daySchedule.getObjective().equals("new")){
            holder.event_box.setBackgroundResource(R.color.colorFocus);
            holder.show_time.setTextColor(Color.WHITE);
            holder.event_box.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, CreateTaskActivity.class);
                    intent.putExtra("camp",CampAdapter.campid);
                    mContext.startActivity(intent);
                }
            });

            holder.event_box.setVisibility(View.GONE);
            if(!GlobalStatus.myRoleInThisCamp.equals("S")){
                holder.event_box.setVisibility(View.VISIBLE);
            }
        }
        else{
            holder.show_obj.setText(daySchedule.getObjective());
            holder.event_box.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    popupRemove(daySchedule);
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDay.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView show_time,show_obj;
        public LinearLayout event_box;

        public ViewHolder(View itemView){
            super(itemView);

            show_time = itemView.findViewById(R.id.show_time);
            show_obj = itemView.findViewById(R.id.show_obj);
            event_box = itemView.findViewById(R.id.event_box);
        }
    }

    private void popupRemove(final DaySchedule info){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("ต้องการลบกิจกรรมนี้ใช่หรือไม่?");
        builder.setMessage(info.getObjective()+"\nเวลา "+info.getTime());
        builder.setPositiveButton("ยกเลิก",null);
        builder.setNegativeButton("ตกลง", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DatabaseReference dbref = FirebaseDatabase.getInstance().getReference();
                dbref.child("Camps").child(CampAdapter.campid).child("schedule")
                        .child(info.getDate()).child(info.getTime()).removeValue();
                Toast.makeText(mContext,"ลบกิจกรรมเรียบร้อยแล้ว",Toast.LENGTH_SHORT).show();
            }
        });

        builder.show();
    }

}
