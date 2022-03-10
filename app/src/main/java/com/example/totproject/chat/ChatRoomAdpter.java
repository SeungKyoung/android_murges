package com.example.totproject.chat;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
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
import com.example.totproject.common.statics.Logined;
import com.example.totproject.party.PartyMemberListDTO;


import java.util.List;

public class ChatRoomAdpter extends RecyclerView.Adapter<ChatRoomAdpter.MyViewHolder> {
    private List<ChatRoomDTO> mDataset;
    private List<PartyMemberListDTO> party_member_list;
    Context context;


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_my_msg , tv_your_date, tv_yourName, tv_your_msg, tv_my_date;
        public LinearLayout lin_yourchat, lin_mychat, lin_your_profile;
        public ImageView imgv_your_pic;
        public View rootView;

        public MyViewHolder(View v){
            super(v);

            // my chat setting
            lin_mychat = v.findViewById(R.id.lin_mychat);
            tv_my_msg = v.findViewById(R.id.tv_my_msg);
            tv_my_date = v.findViewById(R.id.tv_my_date);

            lin_yourchat = v.findViewById(R.id.lin_yourchat);
            lin_your_profile = v.findViewById(R.id.lin_your_profile);
            tv_yourName = v.findViewById(R.id.tv_yourName);
            tv_your_msg = v.findViewById(R.id.tv_your_msg);
            imgv_your_pic = v.findViewById(R.id.imgv_your_pic);
            tv_your_date = v.findViewById(R.id.tv_your_date);

            v.setClickable(true);
            v.setEnabled(true);
        }
    }

    public ChatRoomAdpter(List<ChatRoomDTO> mDataset, List<PartyMemberListDTO> party_member_list, Context context) {
        this.mDataset = mDataset;
        this.party_member_list = party_member_list;
        this.context = context;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.row_chat,parent , false);

        MyViewHolder vh = new MyViewHolder(v);
        return  vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ChatRoomDTO dto = mDataset.get(position);

        // dto에 프사세팅해주기
        for (int i=0;i<party_member_list.size();i++){
            if (dto.getNickname().equals(party_member_list.get(i).getMemberid())){
                dto.setPicture_filepath(party_member_list.get(i).getPicture_filepath());
            }

        }


        // 내채팅이라면
        if(dto.getNickname().equals(Logined.member_id)){

            holder.lin_mychat.setVisibility(View.VISIBLE);
            holder.lin_yourchat.setVisibility(View.GONE);
            holder.tv_my_msg.setText(dto.getMsg());
            holder.tv_my_date.setText(dto.getDate());

            //남의 채팅이라면
        }else{
            holder.lin_mychat.setVisibility(View.GONE);
            holder.lin_yourchat.setVisibility(View.VISIBLE);
            holder.tv_your_msg.setText(dto.getMsg());
            holder.tv_your_date.setText(dto.getDate());

            // 이전채팅한사람이 같다면 말풍선만 뜨도록 (프사생략)
            if (position != 0 && dto.getNickname().equals(mDataset.get(position-1).getNickname())){
                holder.lin_your_profile.setVisibility(View.GONE);

                // 이전채팅이 같지않다면 프사도 다시 뜨도록
            }else{
                holder.lin_your_profile.setVisibility(View.VISIBLE);
                if ( dto.getPicture_filepath() != null){
                    Glide.with(context).load(dto.getPicture_filepath()).into(holder.imgv_your_pic);
                }
                holder.tv_yourName.setText(dto.getNickname());
            }


        }


    }//onBindViewHolder()

    @Override
    public int getItemCount() {
        return mDataset==null ? 0: mDataset.size();
    }
    public ChatRoomDTO getChat(int position){
        return mDataset != null ? mDataset.get(position) : null;
    }
    public void addChat(ChatRoomDTO dto){
        mDataset.add(dto);
        notifyItemInserted(mDataset.size()-1);
    }


}
