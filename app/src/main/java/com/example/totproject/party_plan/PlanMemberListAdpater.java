package com.example.totproject.party_plan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.totproject.R;
import com.example.totproject.party.PartyListDTO;
import com.example.totproject.party.PartyMemberListDTO;

import java.util.ArrayList;

public class PlanMemberListAdpater extends RecyclerView.Adapter<PlanMemberListAdpater.Viewholder> {
    Context context;
    ArrayList<PartyMemberListDTO> list;
    LayoutInflater inflater;
    PartyListDTO plDTO;
    int tabcode;



    public PlanMemberListAdpater(Context context, ArrayList<PartyMemberListDTO> list, PartyListDTO plDTO, int tabcode) {
        this.context = context;
        this.list = list;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.plDTO = plDTO;
        this.tabcode = tabcode;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.party_act_membermanage_item, parent , false);
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

    public class Viewholder extends RecyclerView.ViewHolder{
        ImageView imgv_member_pic;
        TextView tv_member_nick;
        CheckBox chk_member_invite;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            imgv_member_pic = itemView.findViewById(R.id.imgv_member_pic);
            tv_member_nick = itemView.findViewById(R.id.tv_member_nick);
            chk_member_invite = itemView.findViewById(R.id.chk_member_delete);

            // 플랜 생성에서 사용 (멤버 리스트 선택해서 초대할때 사용)
            if (tabcode ==1 ){
                chk_member_invite.setVisibility(View.VISIBLE);

                PlanCreatePlanActivity activity = (PlanCreatePlanActivity) context;
                activity.test(plDTO);
            }



        }
        //ItemView세팅되고 나서 list <-> item.xml 연결해서 세팅하는부분
        public void bind(@NonNull Viewholder holder, int position){
            //내용 바꾸기 처리
            if ( list.get(position).getPicture_filepath() != null){
                Glide.with(context).load(list.get(position).getPicture_filepath()).into(imgv_member_pic);
            }

            holder.tv_member_nick.setText( list.get(position).getMemberid() +"" );

            // 플랜 생성에서 사용 (멤버 리스트 선택해서 초대할때 사용)
            if (tabcode == 1){
                chk_member_invite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        PlanCreatePlanActivity activity = (PlanCreatePlanActivity) context;
                        if(isChecked){
                            activity.invite_list.add(new PartyMemberListDTO(list.get(position).getMemberid()));
                        }else{
                            for (int i = 0 ; i<activity.invite_list.size() ; i ++){
                                if(activity.invite_list.get(i).getMemberid().equals(list.get(position).getMemberid())){
                                    activity.invite_list.remove(i);
                                }
                            }
                        }
                    }
                });
            }



        }

    }




}//planListAdapter()
