package com.eq.eq_world.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.eq.eq_world.Model.CampUser;
import com.eq.eq_world.R;

import java.util.List;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.ViewHolder>{
    private Context mContext;
    private List<CampUser> mUsers;

    public MemberAdapter(Context mContext, List<CampUser> mUsers) {
        this.mContext = mContext;
        this.mUsers = mUsers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item,parent,false);
        return new MemberAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final CampUser user = mUsers.get(position);

        // Show Name
        holder.username.setText(user.getUsername());

        // Show Role
        if(user.getRole().equals("A")){
            holder.role.setText("Admin");
        }
        else if(user.getRole().equals("H")){
            holder.role.setText("Head Staff");
        }
        else{
            holder.role.setText("Staff");
        }

        // Show Image
        if(user.getImageURL().equals("default")){
            holder.profile_image.setImageResource(R.mipmap.ic_launcher);
        }
        else {
            Glide.with(mContext).load(user.getImageURL()).into(holder.profile_image);
        }
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView username,role;
        public ImageView profile_image;

        public ViewHolder(View itemView){
            super(itemView);

            username = itemView.findViewById(R.id.username);
            role = itemView.findViewById(R.id.role);
            profile_image = itemView.findViewById(R.id.profile_image);
        }
    }

}
