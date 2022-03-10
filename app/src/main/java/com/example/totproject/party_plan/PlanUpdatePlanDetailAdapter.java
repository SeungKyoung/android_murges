package com.example.totproject.party_plan;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.totproject.R;

import java.util.ArrayList;

public class PlanUpdatePlanDetailAdapter extends RecyclerView.Adapter<PlanUpdatePlanDetailAdapter.Viewholder>  {
    Context context;
    ArrayList<PlanInfoDTO> list;
    LayoutInflater inflater;
    boolean is_visible_rdo = false;
    public PlanUpdatePlanDetailAdapter(Context context, ArrayList<PlanInfoDTO> list) {
        this.context = context;
        this.list = list;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.partyplan_act_updateplandetail_item, parent , false);
        return new Viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.bind(holder,position);
        if(is_visible_rdo){
            holder.chk_planudelete.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        EditText edt_partyplan_time, edt_partyplan_content, edt_partyplan_content_detail;
        Button btn_plandetail_update, btn_plandetailupdate_delete;
        LinearLayout lin_longclick;
        CheckBox chk_planudelete;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            btn_plandetail_update = itemView.findViewById(R.id.btn_plandetail_update);
            edt_partyplan_time = itemView.findViewById(R.id.edt_partyplan_time);
            edt_partyplan_content = itemView.findViewById(R.id.edt_partyplan_content);
            edt_partyplan_content_detail = itemView.findViewById(R.id.edt_partyplan_content_detail);
            lin_longclick = itemView.findViewById(R.id.lin_longclick);
            chk_planudelete = itemView.findViewById(R.id.chk_planudelete);



            lin_longclick.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    PlanUpdatePlanDetailActivity activity = (PlanUpdatePlanDetailActivity) context;
                    btn_plandetail_update.setVisibility(View.INVISIBLE);
                    activity.btn_plandetailupdate_delete.setVisibility(View.VISIBLE);
                    activity.test();
                    is_visible_rdo=true;

                    chk_planudelete.setVisibility(View.VISIBLE);
                    //@@@btn_plandetailupdate_delete.setVisibility(View.VISIBLE);
                    return false;
                }
            });





        }
        //ItemView세팅되고 나서 list <-> item.xml 연결해서 세팅하는부분
        public void bind(@NonNull Viewholder holder, int position){
            //내용 바꾸기 처리
            //holder.imgv_plan.setImage
            holder.edt_partyplan_time.setText( list.get(position).getPlandetail_time() +"" );
            holder.edt_partyplan_content.setText( list.get(position).getPlandetail_content() +"" );
            holder.edt_partyplan_content_detail.setText( list.get(position).getPlandetail_content_detail() +"" );



            // 플랜디테일 아이템 수정
            holder.btn_plandetail_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PlanInfoDTO planInfoDTO = new PlanInfoDTO(
                            list.get(position).getPlandetail_date(),
                            holder.edt_partyplan_time.getText()+"",
                            list.get(position).getPlan_sn(),
                            holder.edt_partyplan_content.getText()+"",
                            holder.edt_partyplan_content_detail.getText()+""
                            );
                    planInfoDTO.setPlandetail_sn(list.get(position).getPlandetail_sn());

                    Intent intent = new Intent(context, PlanUpdatePlanDetailActivity.class);
                    intent.putExtra("tabcode" ,2);
                    intent.putExtra("planInfoDTO" ,planInfoDTO);

                    context.startActivity(intent);
                }
            });//setOnClickListener()

            chk_planudelete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    PlanUpdatePlanDetailActivity activity = (PlanUpdatePlanDetailActivity) context;
                    if(isChecked){
                        activity.delete_list.add(new PlanInfoDTO(list.get(position).getPlandetail_sn()));
                    }else{
                        for (int i = 0 ; i<activity.delete_list.size() ; i ++){
                            if(activity.delete_list.get(i).getPlandetail_sn() == list.get(position).getPlandetail_sn()){
                                activity.delete_list.remove(i);
                            }
                        }
                    }
                }
            });



        }

    }




}//planListAdapter()