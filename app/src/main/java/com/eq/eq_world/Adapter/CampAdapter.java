package com.eq.eq_world.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eq.eq_world.Model.Camp;
import com.eq.eq_world.R;

import java.util.List;

public class CampAdapter extends RecyclerView.Adapter<CampAdapter.ViewHolder> {

    private Context context;
    private List<Camp> campList;

    public CampAdapter(Context context, List<Camp> campList) {
        this.context = context;
        this.campList = campList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_camp,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.relative_laout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return new CampAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(campList.get(position).getName());
        holder.descriptions.setText(campList.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return campList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView title,descriptions;
        public RelativeLayout relative_laout;

        public ViewHolder(View itemView){
            super(itemView);

            title = itemView.findViewById(R.id.item_camp_title);
            descriptions = itemView.findViewById(R.id.item_camp_description);
            relative_laout = itemView.findViewById(R.id.item_camp_relativelayout);
        }
    }
}
