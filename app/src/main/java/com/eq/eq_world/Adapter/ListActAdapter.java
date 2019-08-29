package com.eq.eq_world.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.eq.eq_world.R;

import java.util.ArrayList;
import java.util.List;

public class ListActAdapter extends RecyclerView.Adapter<ListActAdapter.LAViewHolder> implements Filterable {

    Context lAContext;
    List<ListActItem> lAData ;
    List<ListActItem> lADataFiltered ;
    Dialog lADialog;

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
        final LAViewHolder lAViewHolder = new LAViewHolder(lALayout);

        //Dialog ini

        lADialog = new Dialog(lAContext);
        lADialog.setContentView(R.layout.dialog_act);

        lAViewHolder.item_act.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView dialog_name = lADialog.findViewById(R.id.dialog_name);
                TextView dialog_type =  lADialog.findViewById(R.id.dialog_type);
                TextView dialog_number = lADialog.findViewById(R.id.dialog_number);
                TextView dialog_description = lADialog.findViewById(R.id.dialog_description);

                dialog_name.setText(lAData.get(lAViewHolder.getAdapterPosition()).getTitle());
                dialog_type.setText(lAData.get(lAViewHolder.getAdapterPosition()).getType());
                dialog_number.setText(lAData.get(lAViewHolder.getAdapterPosition()).getNumber());
                dialog_description.setText(lAData.get(lAViewHolder.getAdapterPosition()).getDescription());

                dialog_description.setMovementMethod(new ScrollingMovementMethod());
                lADialog.show();
            }
        });


        return lAViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LAViewHolder LAViewHolder, int position) {

        // bind data here

        // lets create the animation for the whole card
        // first lets create a reference to it
        // you ca use the previous same animation like the following

        // but i want to use a different one so lets create it ..
        //LAViewHolder.actcontainer.setAnimation(AnimationUtils.loadAnimation(lAContext,R.anim.fade_scale_animation));



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



        TextView act_title,act_content,dialog_name,dialog_description,dialog_type,dialog_number;
        RelativeLayout actcontainer;
        private ConstraintLayout item_act;


        public LAViewHolder(@NonNull View itemView) {
            super(itemView);
            dialog_name = itemView.findViewById(R.id.dialog_name);
            dialog_type = itemView.findViewById(R.id.dialog_type);
            dialog_number = itemView.findViewById(R.id.dialog_number);
            dialog_description = itemView.findViewById(R.id.dialog_description);
            actcontainer = itemView.findViewById(R.id.actcontainer);
            act_title = itemView.findViewById(R.id.act_title);
            act_content = itemView.findViewById(R.id.act_description);
            item_act = itemView.findViewById(R.id.item_act_id);

        }




    }
}
