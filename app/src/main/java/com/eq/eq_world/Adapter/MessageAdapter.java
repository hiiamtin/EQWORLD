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
import com.eq.eq_world.CampHomeActivity;

import com.eq.eq_world.Model.CampUser;
import com.eq.eq_world.Model.GroupAnnounce;
import com.eq.eq_world.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>{

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;

    private Context mContext;
    private List<GroupAnnounce> mChat;

    FirebaseUser fuser;

    public MessageAdapter(Context mContext, List<GroupAnnounce> mChat) {
        this.mContext = mContext;
        this.mChat = mChat;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==MSG_TYPE_RIGHT){
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right,parent,false);
            return new MessageAdapter.ViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left,parent,false);
            return new MessageAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GroupAnnounce chat = mChat.get(position);
        holder.show_message.setText(chat.getMessage());

        CampUser member = loadUserInfo(chat.getSender());
        holder.show_username.setText(member.getUsername());

        if(member.getImageURL().equals("default")){
            holder.profile_image.setImageResource(R.mipmap.ic_launcher);
        }
        else{
            Glide.with(mContext).load(member.getImageURL()).into(holder.profile_image);
        }

        holder.show_time.setText(timestamp(chat.getTime()));


    }

    @Override
    public int getItemCount(){
        return mChat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView show_message;
        public TextView show_username;
        public ImageView profile_image;
        public TextView show_time;

        public ViewHolder(View itemView){
            super(itemView);

            show_message = itemView.findViewById(R.id.show_message);
            show_username = itemView.findViewById(R.id.show_username);
            profile_image = itemView.findViewById(R.id.profile_image);
            show_time = itemView.findViewById(R.id.show_time);
        }
    }

    @Override
    public int getItemViewType(int position) {
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        if(mChat.get(position).getSender().equals(fuser.getUid())){
            return MSG_TYPE_RIGHT;
        }
        else {
            return MSG_TYPE_LEFT;
        }
    }

    private CampUser loadUserInfo(String uid){
        for (CampUser member : CampHomeActivity.memberList) {
            if(member.getId().equals(uid)){
                return member;
            }
        }
        return new CampUser("x","Unknown","default");
    }

    private String timestamp(long milli){
        SimpleDateFormat formatter= new SimpleDateFormat("E - HH:mm");
        Date date = new Date(milli);
        return (formatter.format(date));
    }

}