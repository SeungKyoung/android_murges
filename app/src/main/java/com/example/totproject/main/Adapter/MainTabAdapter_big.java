package com.example.totproject.main.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.totproject.R;
import com.example.totproject.common.VO.BoardCommonVO;
import com.example.totproject.mainburgeractivity.MainBurger01NoticeFgDetailFg;

import java.util.ArrayList;

public class MainTabAdapter_big extends RecyclerView.Adapter<MainTabAdapter_big.Viewholder> {
    //xml로 만들어놓은 아이템을 붙이기위한 LayoutInfler <- Context
    Context context;
    ArrayList<BoardCommonVO> list;
    LayoutInflater inflater;
    View.OnClickListener listener;
    FragmentManager manager;

    public MainTabAdapter_big(Context context, ArrayList<BoardCommonVO> list) {
        this.context = context;
        this.list = list;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }//NoticeAdapter

    public MainTabAdapter_big(Context context, ArrayList<BoardCommonVO> list, FragmentManager manager) {
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
        View itemView = inflater.inflate(R.layout.main_frag_hometab_item_big, parent, false);
        //1. ViewHolder holder = new ViewHolder(itemview);
        // return holder;
        return new Viewholder(itemView);
    }//onCreateViewHolder

    //4. 아이템이 ↑ 세팅되고 나서의 처리를 의미함↓
    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.bind(holder, position, manager);
    }//onBindViewHolder

    //5.↓ 총 아이템의 갯수를 지정함.
    @Override
    public int getItemCount() {
        return list.size();
    }//getItemCount


    //1. RecyclerView.ViewHolder 상속을 받은 클래스 ViewHolder를 만들어줌
    public class Viewholder extends RecyclerView.ViewHolder {
        ImageView hometab_img, hometab_img_like, hometab_img_comment;
        TextView hometab_tv_title, hometab_tv_like, hometab_tv_comment; //xml에 있는 위젯들을 전역변수로 선언.
        int board_sn;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            hometab_img = itemView.findViewById(R.id.hometab_big_img);
   //         hometab_img_like = itemView.findViewById(R.id.hometab_img_like);
     //       hometab_img_comment = itemView.findViewById(R.id.hometab_img_comment);

            hometab_tv_title = itemView.findViewById(R.id.hometab_big_title);
//            hometab_tv_like = itemView.findViewById(R.id.hometab_tv_like);
 //           hometab_tv_comment = itemView.findViewById(R.id.hometab_tv_comment);
        }

        //ItemView세팅되고 나서 list <-> item.xml 연결해서 세팅하는부분
        public void bind(@NonNull Viewholder holder, int position, FragmentManager manager) {
            //내용 바꾸기 처리
            if ( list.get(position).getPicture_filepath() != null){
                Glide.with(context).load(list.get(position).getPicture_filepath()).into(hometab_img);
            }
            holder.hometab_tv_title.setText(list.get(position).getBoard_title() + "");

 //           holder.hometab_tv_like.setText(list.get(position).getBoard_cnt_like()+"");
 //           holder.hometab_tv_comment.setText(list.get(position).getBoard_cnt_reply()+"");

            holder.board_sn = list.get(position).getBoard_sn();

            holder.hometab_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

             /*       Intent intent = new Intent(context, MainBurger01NoticeFgDetailAct.class);
                    intent.putExtra("vo", list.get(position));
                    context.startActivity(intent);*/
                    MainBurger01NoticeFgDetailFg MainBurger01NoticeFgDetailAct = new MainBurger01NoticeFgDetailFg(context, manager, holder.board_sn);
                    manager.beginTransaction().replace(R.id.mainburger_container, MainBurger01NoticeFgDetailAct).addToBackStack(null).commit();

                }
            });


        }
    }//ViewHolder
}
