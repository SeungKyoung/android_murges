package com.example.totproject.party_plan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.totproject.R;

import java.util.ArrayList;

public class PlanListAdapter extends RecyclerView.Adapter<PlanListAdapter.Viewholder> {
    Context context;
    ArrayList<PlanlistDTO> list;
    LayoutInflater inflater;


    public PlanListAdapter(Context context, ArrayList<PlanlistDTO> list) {
        this.context = context;
        this.list = list;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.partyplan_frag_planlist_item, parent , false);
        return new Viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.bind(holder,position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        ImageView leader_pic;
        TextView tv_plan_writer, tv_plan_name, tv_startdate, tv_starttime;
        LinearLayout lin_plan_click;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            leader_pic = itemView.findViewById(R.id.leader_pic);
            lin_plan_click = itemView.findViewById(R.id.lin_plan_click);
            tv_plan_writer = itemView.findViewById(R.id.tv_plan_writer);
            tv_plan_name = itemView.findViewById(R.id.tv_plan_name);
            tv_startdate = itemView.findViewById(R.id.tv_startdate);
            tv_starttime = itemView.findViewById(R.id.tv_starttime);

        }
        //ItemView세팅되고 나서 list <-> item.xml 연결해서 세팅하는부분
        public void bind(@NonNull Viewholder holder, int position){
            //내용 바꾸기 처리

            if ( list.get(position).getPicture_filepath() != null){
                Glide.with(context).load(list.get(position).getPicture_filepath()).into(leader_pic);
            }
            holder.tv_plan_writer.setText( list.get(position).getPlan_writer() +"" );
            holder.tv_plan_name.setText( list.get(position).getPlan_name() +"" );
            holder.tv_startdate.setText( list.get(position).getPlan_startdate() +"" );
            holder.tv_starttime.setText( list.get(position).getPlan_starttime() +"" );

            // 해당 플랜 클릭시
            holder.lin_plan_click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PlanMainActivity activity = (PlanMainActivity) context;
                    PlanlistDTO planDTO = (PlanlistDTO) list.get(position);
                    activity.changePlanFrag(planDTO);

                }
            });

        }

    }




}//planListAdapter()
