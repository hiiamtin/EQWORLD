package com.eq.eq_world.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.eq.eq_world.Model.SongItem;
import com.eq.eq_world.R;
import com.eq.eq_world.YoutubeConfig;

import java.util.ArrayList;
import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SViewHolder> implements Filterable {


        Context mContext;
        List<SongItem> mData;
        List<SongItem> mDataFiltered;


        public SongAdapter(Context mContext, List<SongItem> mData) {
                this.mContext = mContext;
                this.mData = mData;
                this.mDataFiltered = mData;

        }

        @NonNull
        @Override
        public SViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                View layout;
                layout = LayoutInflater.from(mContext).inflate(R.layout.item_song, viewGroup, false);
                final SViewHolder sViewHolder = new SViewHolder(layout);

                sViewHolder.item_song.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                Intent intent = new Intent(mContext, YoutubeConfig.class);
                                intent.putExtra("link",mData.get(sViewHolder.getAdapterPosition()).getLinkyoutube());
                                mContext.startActivities(new Intent[]{intent});
                        }
                });
                return sViewHolder;

        }

        @Override
        public void onBindViewHolder(@NonNull SViewHolder SViewHolder, int position) {

                // bind data here

                // we apply animation to views here
                // first lets create an animation for user photo

                // lets create the animation for the whole card
                // first lets create a reference to it
                // you ca use the previous same animation like the following

                // but i want to use a different one so lets create it ..


                SViewHolder.songname.setText(mDataFiltered.get(position).getName());


        }

        @Override
        public int getItemCount() {
                return mDataFiltered.size();
        }

        @Override
        public Filter getFilter() {

                return new Filter() {
                        @Override
                        protected FilterResults performFiltering(CharSequence constraint) {

                                String Key = constraint.toString();
                                if (Key.isEmpty()) {

                                        mDataFiltered = mData;

                                } else {
                                        List<SongItem> lstFiltered = new ArrayList<>();
                                        for (SongItem row : mData) {

                                                if (row.getName().toLowerCase().contains(Key.toLowerCase())) {
                                                        lstFiltered.add(row);
                                                }

                                        }

                                        mDataFiltered = lstFiltered;

                                }


                                FilterResults filterResults = new FilterResults();
                                filterResults.values = mDataFiltered;
                                return filterResults;

                        }

                        @Override
                        protected void publishResults(CharSequence constraint, FilterResults results) {


                                mDataFiltered = (List<SongItem>) results.values;
                                notifyDataSetChanged();

                        }
                };


        }

        public class SViewHolder extends RecyclerView.ViewHolder {


                TextView songname;
                private ConstraintLayout item_song;

                public SViewHolder(@NonNull View itemView) {
                        super(itemView);
                        songname = itemView.findViewById(R.id.songname);
                        item_song = itemView.findViewById(R.id.item_song_id);

                }


        }
}
