package com.example.totproject.main.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.totproject.R;
import com.example.totproject.category.CategoryMainActivity;
import com.example.totproject.common.VO.BoardCommonVO;

import java.util.ArrayList;

public class MainTabAdapter_small_xy extends RecyclerView.Adapter<MainTabAdapter_small_xy.Viewholder> {
    //xml로 만들어놓은 아이템을 붙이기위한 LayoutInfler <- Context
    Context context;
    ArrayList<BoardCommonVO> list;
    LayoutInflater inflater;
    View.OnClickListener listener;
    FragmentManager manager;

    public MainTabAdapter_small_xy(Context context, ArrayList<BoardCommonVO> list) {
        this.context = context;
        this.list = list;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }//NoticeAdapter

    public MainTabAdapter_small_xy(Context context, ArrayList<BoardCommonVO> list, FragmentManager manager) {
        this.manager = manager;
        this.context = context;
        this.list = list;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (null != listener)
            this.listener = listener;

    }//NoticeAdapter


    //화면을 인플레이트 시키는 작업을 하기.
    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;

        if (list.size() > 0) {

            itemView = inflater.inflate(R.layout.main_frag_hometab_item_small_mbti, parent, false);
        } else {

            itemView = inflater.inflate(R.layout.empty_item, parent, false);
        }
        //1. ViewHolder holder = new ViewHolder(itemview);
        // return holder;
        return new Viewholder(itemView);
    }//onCreateViewHolder

    //4. 아이템이 ↑ 세팅되고 나서의 처리를 의미함↓
    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        if(list.size() > 0 ) {
            holder.bind(holder, position, manager);
        }
    }//onBindViewHolder

    //5.↓ 총 아이템의 갯수를 지정함.
    @Override
    public int getItemCount() {
        if(list.size() == 0){
            return 1;
        }

        return list.size();
    }//getItemCount


    //1. RecyclerView.ViewHolder 상속을 받은 클래스 ViewHolder를 만들어줌
    public class Viewholder extends RecyclerView.ViewHolder {
        ImageView hometab_small_img, hometab_small_img_like, hometab_small_img_comment;
        TextView hometab_small_tv_title, hometab_small_tv_like, hometab_small_tv_comment; //xml에 있는 위젯들을 전역변수로 선언.
        TextView hometab_small_tv_score;
        int board_sn;
        FrameLayout mbti_smaill_frame ;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            hometab_small_img = itemView.findViewById(R.id.hometab_small_img);
            hometab_small_img_like = itemView.findViewById(R.id.hometab_small_img_like);
            hometab_small_img_comment = itemView.findViewById(R.id.hometab_small_img_comment);

            hometab_small_tv_title = itemView.findViewById(R.id.hometab_small_tv_title);
            hometab_small_tv_like = itemView.findViewById(R.id.hometab_small_tv_like);
            hometab_small_tv_comment = itemView.findViewById(R.id.hometab_small_tv_comment);

            hometab_small_tv_score = itemView.findViewById(R.id.hometab_small_tv_score);
            mbti_smaill_frame = itemView.findViewById(R.id.mbti_smaill_frame);
        }

        //ItemView세팅되고 나서 list <-> item.xml 연결해서 세팅하는부분
        public void bind(@NonNull Viewholder holder, int position, FragmentManager manager) {
            //내용 바꾸기 처리
            hometab_small_img.setColorFilter(Color.parseColor("#BDBDBD"), PorterDuff.Mode.MULTIPLY);
            if ( list.get(position).getPicture_filepath() != null){
                //Glide.with(context).load(list.get(position).getPicture_filepath()).into(hometab_small_img);
            }
            holder.hometab_small_tv_title.setText(list.get(position).getBoard_title() + "");
            holder.hometab_small_tv_like.setText(list.get(position).getFunction_like()+"");
            holder.hometab_small_tv_comment.setText(list.get(position).getBoard_cnt_reply()+"");

            holder.board_sn = list.get(position).getBoard_sn();

            holder.hometab_small_tv_score.setText("추천 No." + (position + 1) + "\n 거리기준" + (100 - list.get(position).getMatchScore()) + "점");

            holder.hometab_small_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, CategoryMainActivity.class);
                    intent.putExtra("tabcode", 4);
                    intent.putExtra("paramSn", list.get(position).getBoard_sn());
                    intent.putExtra("tabText", "가까운 거리 여행지");

                    context.startActivity(intent);
        //            MainBurger01NoticeFgDetailFg MainBurger01NoticeFgDetailAct = new MainBurger01NoticeFgDetailFg(context, manager, holder.board_sn);
                  //  manager.beginTransaction().replace(R.id.mainburger_container, MainBurger01NoticeFgDetailAct).addToBackStack(null).commit();
                }
            });


        }
    }//ViewHolder
}
