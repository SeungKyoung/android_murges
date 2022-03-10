package com.example.totproject.party;

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
import com.example.totproject.common.statics.Logined;

import java.util.ArrayList;


public class PartymemberManageAdapter extends RecyclerView.Adapter<PartymemberManageAdapter.Viewholder> {
    Context context;
    ArrayList<PartyMemberListDTO> list;
    LayoutInflater inflater;
    boolean is_visible_rdo = false;
    PartyListDTO plDTO;

    public PartymemberManageAdapter(PartyListDTO plDTO, Context context, ArrayList<PartyMemberListDTO> list) {
        this.plDTO = plDTO;
        this.context = context;
        this.list = list;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.party_act_membermanage_item , parent , false);
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
        CheckBox chk_member_delete;
        LinearLayout lin_membermanage_click,lin_chk_box;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            imgv_member_pic = itemView.findViewById(R.id.imgv_member_pic);
            tv_member_nick = itemView.findViewById(R.id.tv_member_nick);
            chk_member_delete = itemView.findViewById(R.id.chk_member_delete);
            lin_membermanage_click = itemView.findViewById(R.id.lin_membermanage_click);
            lin_chk_box = itemView.findViewById(R.id.lin_chk_box);



            if(Logined.member_id.equals(plDTO.getParty_leader())){
                lin_membermanage_click.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        PartyMemberManageActivity activity = (PartyMemberManageActivity) context;
                        lin_chk_box.setVisibility(View.VISIBLE);
                        activity.lin_party_memberdelete.setVisibility(View.VISIBLE);
                        activity.test(plDTO);
                        is_visible_rdo=true;


                        return false;
                    }
                });//setOnLongClickListener()
            }






        }



        //ItemView세팅되고 나서 list <-> item.xml 연결해서 세팅하는부분
        public void bind(@NonNull Viewholder holder, int position){
            //내용 바꾸기 처리
            if(list.get(position).getPicture_filepath() != null){
                Glide.with(context).load(list.get(position).getPicture_filepath()).into(imgv_member_pic);
            }

            holder.tv_member_nick.setText( list.get(position).getMemberid() +"" );

            chk_member_delete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    PartyMemberManageActivity activity = (PartyMemberManageActivity) context;
                    if(isChecked){
                        activity.delete_list.add(new PartyMemberListDTO(list.get(position).getMemberid()));
                    }else{
                        for (int i = 0 ; i<activity.delete_list.size() ; i ++){
                            if(activity.delete_list.get(i).getMemberid() == list.get(position).getMemberid()){      //@@@@@@@ 멤버아이디 스트링인데 eqaual 안쓰나?
                                activity.delete_list.remove(i);
                            }
                        }
                    }
                }
            });



        }//bind()

    }



}//PartyListAdapter()
