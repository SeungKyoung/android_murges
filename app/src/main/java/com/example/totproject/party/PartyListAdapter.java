package com.example.totproject.party;

import android.content.Context;
import android.content.Intent;
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

public class PartyListAdapter extends RecyclerView.Adapter<PartyListAdapter.Viewholder> {
    Context context;
    ArrayList<PartyListDTO> list;
    LayoutInflater inflater;
    int tabcode = 0 ;

    public PartyListAdapter(Context context, ArrayList<PartyListDTO> list, int tabcode) {
        this.context = context;
        this.list = list;
        this.tabcode = tabcode;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.party_frag_openpartylist_item , parent , false);
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
        ImageView imgv_party;
        TextView tv_party_name , tv_party_detail, tv_party_tag1, tv_party_tag2, tv_party_tag3;
        LinearLayout lin_party_click;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            imgv_party = itemView.findViewById(R.id.imgv_party);
            tv_party_name = itemView.findViewById(R.id.tv_party_name);
            tv_party_detail = itemView.findViewById(R.id.tv_party_detail);
            lin_party_click =itemView.findViewById(R.id.lin_party_click);
            tv_party_tag1 = itemView.findViewById(R.id.tv_party_tag1);
            tv_party_tag2 = itemView.findViewById(R.id.tv_party_tag2);
            tv_party_tag3 = itemView.findViewById(R.id.tv_party_tag3);

        }
        //ItemView세팅되고 나서 list <-> item.xml 연결해서 세팅하는부분
        public void bind(@NonNull Viewholder holder, int position){
            //내용 바꾸기 처리
            if ( list.get(position).getPicture_filepath() != null){
                Glide.with(context).load(list.get(position).getPicture_filepath()).into(imgv_party);
            }
            holder.tv_party_name.setText( list.get(position).getParty_name() +"" );
            if(list.get(position).getParty_detail() != null){
                holder.tv_party_detail.setText( list.get(position).getParty_detail() +"" );
            }else{
                holder.tv_party_detail.setText( "파티 설명이 없습니다." );
            }


            if(list.get(position).getParty_tag1() != null){
                holder.tv_party_tag1.setText( list.get(position).getParty_tag1() +"" );
            }else {
                holder.tv_party_tag1.setVisibility(View.GONE);
            }
            if(list.get(position).getParty_tag2() != null){
                holder.tv_party_tag2.setText( list.get(position).getParty_tag2() +"" );
            }else {
                holder.tv_party_tag2.setVisibility(View.GONE);
            }

            if(list.get(position).getParty_tag3() != null){
                holder.tv_party_tag3.setText( list.get(position).getParty_tag3() +"" );
            }else {
                holder.tv_party_tag3.setVisibility(View.GONE);
            }

            holder.lin_party_click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(tabcode == 1){   //탭코드 1일시 해당파티조인(디테일) 이동
                        Intent intent = new Intent(context,PartyJoinActivity.class);
                        intent.putExtra("party_sn" , list.get(position).getParty_sn());
                        context.startActivity(intent);
                    }else{
                        // 탭코드가 1이 아닐시 해당하는 파티(내파티) 이동
                        Intent intent = new Intent(context,MyPartyInfoActivity.class);
                        intent.putExtra("party_dto" , list.get(position));
                        context.startActivity(intent);
                    }


                }
            });

        }

    }



}//PartyListAdapter()
