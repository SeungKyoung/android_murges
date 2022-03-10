package com.example.totproject.mainburgeractivity.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.totproject.R;
import com.example.totproject.common.VO.BoardCommonVO;

import java.util.ArrayList;

public class QnaAdapter extends RecyclerView.Adapter<QnaAdapter.Viewholder> {
    //xml로 만들어놓은 아이템을 붙이기위한 LayoutInfler <- Context
    Context context;
    ArrayList<BoardCommonVO> list;
    LayoutInflater inflater;
    View.OnClickListener listener;
    FragmentManager manager;

    public QnaAdapter(Context context, ArrayList<BoardCommonVO> list) {
        this.context = context;
        this.list = list;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }//NoticeAdapter

    public QnaAdapter(Context context, ArrayList<BoardCommonVO> list, FragmentManager manager) {
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
        View itemView = inflater.inflate(R.layout.mainburger02_service_fg_01_qna_item, parent, false);
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
      //  ImageView hometab_small_img, hometab_small_img_like, hometab_small_img_comment;
        TextView qna_tv_question, hometab_small_tv_like, qna_tv_answer; //xml에 있는 위젯들을 전역변수로 선언.
        int board_sn;
        Button qna_btn_click;
        Button qna_btn_unnamed01, qna_btn_unnamed02;
        LinearLayout qna_linear_answerzone;
        int isView = 0;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            // hometab_small_img = itemView.findViewById(R.id.hometab_small_img);

            qna_tv_question = itemView.findViewById(R.id.qna_tv_question);
            qna_tv_answer = itemView.findViewById(R.id.qna_tv_answer);
            qna_linear_answerzone = itemView.findViewById(R.id.qna_linear_answerzone);

            /* =============== 답안 보기/숨기기 버튼 =============== */
            qna_btn_click = itemView.findViewById(R.id.qna_btn_click);
            /* =============== 답안 보기/숨기기 버튼 =============== */

            /* =============== 관리자용 버튼 =============== */
            qna_btn_unnamed01 = itemView.findViewById(R.id.qna_btn_unnamed01);
            qna_btn_unnamed02 = itemView.findViewById(R.id.qna_btn_unnamed02);
            /* =============== 관리자용 버튼 =============== */
        }

        //ItemView세팅되고 나서 list <-> item.xml 연결해서 세팅하는부분
        public void bind(@NonNull Viewholder holder, int position, FragmentManager manager) {

            /* =============== 관리자용 버튼 숨기기 =============== */
   //         qna_btn_unnamed01.setVisibility(View.GONE);
    //        qna_btn_unnamed02.setVisibility(View.GONE);
            /* =============== 관리자용 버튼 숨기기 =============== */

            /* =============== 답변부분 숨기기 =============== */
   //         qna_linear_answerzone.setVisibility(View.GONE);
            /* =============== 답변부분 숨기기 =============== */

            holder.qna_tv_question.setText(list.get(position).getBoard_title() + "");
            holder.qna_tv_answer.setText(list.get(position).getBoard_content() + "");
            holder.board_sn = list.get(position).getBoard_sn();

            qna_linear_answerzone.setVisibility(View.GONE);          // view 삭제
            holder.qna_btn_click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isView == 1) {
                        qna_linear_answerzone.setVisibility(View.GONE);          // view 삭제
                        isView = 0;
                    } else {
                        qna_linear_answerzone.setVisibility(View.VISIBLE);      // view 보이기
                        isView = 1;
                    }
                }
            });

            /* =============== 관리자모드 =============== */
            /* =============== 관리자모드 =============== */



        }
    }//ViewHolder
}
