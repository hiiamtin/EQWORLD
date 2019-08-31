package com.eq.eq_world.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eq.eq_world.CampHomeActivity;
import com.eq.eq_world.Model.Camp;
import com.eq.eq_world.R;

import java.util.List;

public class CampAdapter extends RecyclerView.Adapter<CampAdapter.ViewHolder> {

    public  static String campid;

    private Context context;
    private List<Camp> campList;
    private String m_Text;
    private AlertDialog.Builder builder;

    public CampAdapter(Context context, List<Camp> campList) {
        this.context = context;
        this.campList = campList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_camp,parent,false);
        final ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.relative_laout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //popup
                Popup(viewHolder);
            }
        });

        return viewHolder;
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

    private void Popup(final ViewHolder viewHolder){
        builder = new AlertDialog.Builder(context);
        builder.setTitle("กรุณาใส่ Password");
        //setup input
        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);
        //setup button
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text = input.getText().toString();
                String password = campList.get(viewHolder.getAdapterPosition()).getPassWord();
                if(0==password.compareTo(m_Text)){
                    campid = campList.get(viewHolder.getAdapterPosition()).getCampID();
                    Intent intent = new Intent(context, CampHomeActivity.class);
                    context.startActivity(intent);
                }
                else{
                    Toast.makeText(context,"Password worng",Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }
}
