package com.example.totproject.board.Adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.totproject.R;
import com.example.totproject.board.VO.ReplyVO;
import com.example.totproject.common.CommonAsk;
import com.example.totproject.common.statics.Logined;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ReplyAdapter extends RecyclerView.Adapter<ReplyAdapter.Viewholder> {
    //xml로 만들어놓은 아이템을 붙이기위한 LayoutInfler <- Context
    Context context;
    List<ReplyVO> list;
    LayoutInflater inflater;
    View.OnClickListener listener;
    FragmentManager manager;

    public ReplyAdapter(Context context, ArrayList<ReplyVO> list) {
        this.context = context;
        this.list = list;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }//NoticeAdapter

    public ReplyAdapter(Context context, List<ReplyVO> list, FragmentManager manager) {
        this.manager = manager;
        this.context = context;
        this.list = list;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (null != listener)
            this.listener = listener;

    }//NoticeAdapter

    public ReplyAdapter(Context context, List<ReplyVO> list) {
        //       this.manager = manager;
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
            itemView = inflater.inflate(R.layout.boardtab_frag_detail_item_reply, parent, false);
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
        if (list.size() > 0) {
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
        TextView board_user_reply_member_id, board_user_reply_writedate;
        EditText board_user_reply_reply_content;
        ImageView board_user_reply_img_profile;
        int board_sn;
        LinearLayout board_user_list_item;

        ;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            board_user_reply_member_id = itemView.findViewById(R.id.board_user_reply_member_id);
            board_user_reply_reply_content = itemView.findViewById(R.id.board_user_reply_reply_content);
            board_user_reply_writedate = itemView.findViewById(R.id.board_user_reply_writedate);
            board_user_reply_img_profile = itemView.findViewById(R.id.board_user_reply_imgv_profile);


            board_user_list_item = itemView.findViewById(R.id.board_user_list_item);
        }

        //ItemView세팅되고 나서 list <-> item.xml 연결해서 세팅하는부분
        public void bind(@NonNull Viewholder holder, int position, FragmentManager manager) {
            /* ============================== 해당 홀더 board_sn ============================== */
            holder.board_sn = list.get(position).getReply_sn();
            /* ====================================================================== */

            /* ============================== TextView 세팅 ============================== */

            // holder.board_user_reply_img_profile.setText(list.get(position).getPicture_filepath() + "");
            holder.board_user_reply_member_id.setText(list.get(position).getMember_id() + "");
            holder.board_user_reply_reply_content.setText(list.get(position).getReply_content() + "");
            holder.board_user_reply_writedate.setText(list.get(position).getReply_writedate() + "");


            if (list.get(position).getPicture_filepath() != null) {
                Glide.with(context).load(list.get(position).getPicture_filepath()).into(board_user_reply_img_profile);
                //Glide.with(context).load(list.get(position).getPicture_filepath()).into(board_user_reply_img_profile);
            } else {
                Glide.with(context).load(R.drawable.logo_tot).into(board_user_reply_img_profile);
            }
            /* ====================================================================== */

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (Logined.member_id.equals(list.get(position).getMember_id()) || Logined.member_grade.equals("master")) {
                        showCustomDialog();
                    } else {
                        Toast.makeText(context, "권한없음", Toast.LENGTH_SHORT).show();
                    }
                    return false;
                }
            });

        }

        public void showCustomDialog() { //if(list.get(position).getMember_id == Logined.member_id){}
            AlertDialog.Builder builder = new AlertDialog.Builder(context,
                    R.style.AlertDialogTheme);

            View view = LayoutInflater.from(context).inflate(
                    R.layout.common_alert_yes_or_no_item,
                    (LinearLayout) itemView.findViewById(R.id.layout_alert)
            );
            //다이얼로그 텍스트 설정
            builder.setView(view);
            ((TextView) view.findViewById(R.id.texttitle)).setText(" 댓글 수정 ");
            ((TextView) view.findViewById(R.id.textmessage)).setText("댓글 수정");
            ((TextView) view.findViewById(R.id.btn_dialog_yes)).setText("수정");
            ((TextView) view.findViewById(R.id.btn_dialog_no)).setText("삭제");

            AlertDialog alertDialog = builder.create();
            alertDialog.setCancelable(true);   //화면밖 터치시 다이얼로그 종료

            view.findViewById(R.id.btn_dialog_yes).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    Toast.makeText(context, "새로고침해야함", Toast.LENGTH_SHORT).show();
                    succ = updateReply(); //댓글수정
                    succCheck(succ, "수정");
                }
            });
            view.findViewById(R.id.btn_dialog_no).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    Toast.makeText(context, "새로고침해야함", Toast.LENGTH_SHORT).show();
                    succ = deleteReply(); //댓글삭제
                    succCheck(succ, "삭제");
                }
            });
            //다이얼로그 형태 지우기
            if (alertDialog.getWindow() != null) {
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }
            alertDialog.show();
        }//showCustomDialog()
    }//ViewHolder

    int succ;

    CommonAsk CommonAsk;
    Gson gson = new Gson();

    public int updateReply() {
        int succ = 0;

        return succ;
    }

    public int deleteReply() {
        int succ = 0;

        return succ;
    }

    public void succCheck(int succ, String whatCase) {
        if (succ > 0) {
            Toast.makeText(context, whatCase + " 되었습니다.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "잠시후 다시 시도해 주세요", Toast.LENGTH_SHORT).show();
        }
    }
}
