package com.eq.eq_world.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eq.eq_world.Model.DaySchedule;
import com.eq.eq_world.R;

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

        holder.show_date.setText(daySchedule.getDate());
        holder.show_event.setText(daySchedule.getWholeDay());
    }

    @Override
    public int getItemCount() {
        return mDay.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView show_date,show_event;

        public ViewHolder(View itemView){
            super(itemView);

            show_date = itemView.findViewById(R.id.show_date);
            show_event = itemView.findViewById(R.id.show_event);
        }
    }


}
