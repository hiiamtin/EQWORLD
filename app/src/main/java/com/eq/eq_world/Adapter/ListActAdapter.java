package com.eq.eq_world.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eq.eq_world.R;

import java.util.ArrayList;
import java.util.List;

public class ListActAdapter extends RecyclerView.Adapter<ListActAdapter.LAViewHolder> implements Filterable {

    Context lAContext;
    List<ListActItem> lAData ;
    List<ListActItem> lADataFiltered ;

    public ListActAdapter(Context lAContext, List<ListActItem> lAData) {
        this.lAContext = lAContext;
        this.lAData = lAData;
        this.lADataFiltered = lAData;
    }

    @NonNull
    @Override
    public LAViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View lALayout;
        lALayout = LayoutInflater.from(lAContext).inflate(R.layout.item_act,viewGroup,false);
        return new LAViewHolder(lALayout);
    }

    @Override
    public void onBindViewHolder(@NonNull LAViewHolder LAViewHolder, int position) {

        // bind data here

        // lets create the animation for the whole card
        // first lets create a reference to it
        // you ca use the previous same animation like the following

        // but i want to use a different one so lets create it ..
        LAViewHolder.actcontainer.setAnimation(AnimationUtils.loadAnimation(lAContext,R.anim.fade_scale_animation));



        LAViewHolder.act_title.setText(lADataFiltered.get(position).getTitle());
        LAViewHolder.act_content.setText(lADataFiltered.get(position).getContent());



    }

    @Override
    public int getItemCount() {
        return lADataFiltered.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                String Key = constraint.toString();
                if (Key.isEmpty()) {

                    lADataFiltered = lAData ;

                }
                else {
                    List<ListActItem> lstFiltered = new ArrayList<>();
                    for (ListActItem row : lAData) {

                        if (row.getTitle().toLowerCase().contains(Key.toLowerCase())){
                            lstFiltered.add(row);
                        }

                    }

                    lADataFiltered = lstFiltered;

                }


                FilterResults filterResults = new FilterResults();
                filterResults.values= lADataFiltered;
                return filterResults;

            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {


                lADataFiltered = (List<ListActItem>) results.values;
                notifyDataSetChanged();

            }
        };




    }

    public class LAViewHolder extends RecyclerView.ViewHolder {



        TextView act_title,act_content;
        RelativeLayout actcontainer;





        public LAViewHolder(@NonNull View itemView) {
            super(itemView);
            actcontainer = itemView.findViewById(R.id.actcontainer);
            act_title = itemView.findViewById(R.id.act_title);
            act_content = itemView.findViewById(R.id.act_description);


        }




    }
}
