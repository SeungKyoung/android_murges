package com.example.totproject.main.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.totproject.whosepageactivity.WhosePage00Activity;
import com.example.totproject.board.Board00Activity;
import com.example.totproject.common.VO.BoardCommonVO;
import com.example.totproject.common.VO.MemberDTO;
import com.example.totproject.common.statics.Logined;

import java.util.List;

public class BoardListAdapter extends RecyclerView.Adapter<BoardListAdapter.Viewholder> {
    //xml로 만들어놓은 아이템을 붙이기위한 LayoutInfler <- Context
    Context context;
    List<BoardCommonVO> list;
    LayoutInflater inflater;
    View.OnClickListener listener;
    FragmentManager manager;
    MemberDTO memberDTO = new MemberDTO();


    public BoardListAdapter(Context context, List<BoardCommonVO> list, FragmentManager manager) {
        this.context = context;
        this.list = list;
        this.manager = manager;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (null != listener)
            this.listener = listener;

    }//NoticeAdapter


    //화면을 인플레이트 시키는 작업을 하기.
    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.aaa_common_frag_main_frag_boardtab_item_boardlist_board_list_item, parent, false);
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
        ImageView boardlist_item_imgv_icon, boardlist_imgv_dialog_profile;
        TextView boardlist_item_tv_title, boardlist_item_tv_writer, boardlist_item_tv_read, boardlist_item_tv_readcnt,
                boardlist_item_tv_reply, boardlist_item_tv_replycnt, boardlist_item_tv_writedate; //xml에 있는 위젯들을 전역변수로 선언.
        int board_sn;
        LinearLayout board_user_list_item;

        ;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            boardlist_item_tv_title = itemView.findViewById(R.id.boardlist_item_tv_title);
            boardlist_item_tv_writer = itemView.findViewById(R.id.boardlist_item_tv_writer);
            boardlist_item_tv_readcnt = itemView.findViewById(R.id.boardlist_item_tv_readcnt);
            boardlist_item_tv_replycnt = itemView.findViewById(R.id.boardlist_item_tv_replycnt);
            boardlist_item_tv_writedate = itemView.findViewById(R.id.boardlist_item_tv_writedate);
            board_user_list_item = itemView.findViewById(R.id.board_user_list_item);
            boardlist_item_imgv_icon = itemView.findViewById(R.id.boardlist_item_imgv_icon);
        }

        //ItemView세팅되고 나서 list <-> item.xml 연결해서 세팅하는부분
        public void bind(@NonNull Viewholder holder, int position, FragmentManager manager) {
            /* ============================== 해당 홀더 board_sn ============================== */
            holder.board_sn = list.get(position).getBoard_sn();
            /* ====================================================================== */

            /* ============================== TextView 세팅 ============================== */

            holder.boardlist_item_tv_title.setText(list.get(position).getBoard_title() + "");
            holder.boardlist_item_tv_writer.setText(list.get(position).getMember_id() + "");

            holder.boardlist_item_tv_readcnt.setText(list.get(position).getBoard_read_cnt() + "");
            holder.boardlist_item_tv_replycnt.setText(list.get(position).getBoard_cnt_reply() + "");
            holder.boardlist_item_tv_writedate.setText(list.get(position).getBoard_date_create() + "");


            /* =============================== 게시물 판별 ================================= */
            if (list.get(position).getMember_id().equals(Logined.member_id)) {  // 나의글이면 백그라운드 (임의)
                //Glide.with(context).load(R.drawable.like).into(boardlist_item_imgv_icon);
                holder.board_user_list_item.setBackgroundResource(R.drawable.title_theme_background);
            } else {

                holder.board_user_list_item.setBackgroundResource(R.drawable.bottom_line);
            }

            if ("master".equals(list.get(position).getMember_grade())) {  // 관리자 글이면 왕관아이콘 (임의)
                Glide.with(context).load(R.drawable.icon_crown).into(boardlist_item_imgv_icon);
            } else if (list.get(position).getBoard_cnt_reply() > 10) {  //댓글이 10개보다 많으면 별 (임의)
                Glide.with(context).load(R.drawable.icon_star).into(boardlist_item_imgv_icon);
            } else {
                Glide.with(context).load(R.drawable.ic_launcher_foreground).into(boardlist_item_imgv_icon);
            }
            //아이콘 여러개 표시하려면 xml수정, 다른값(좋아요 수, 최근며칠간 댓글수 등) 참조하려면 mapper,VO수정 -> 말만하면 차민환이 바로가능
            //조건별 표시 변경 (관리자글이면 백그라운드 컬러 테마색 등)역시 가능 그외 말만하면 그냥 다 가능 내가 읽은게시물 표시하는것도 테이블만들면 가능


            /* ====================================================================== */

            /* ============================== 아이템 클릭시 ============================== */
            holder.board_user_list_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, Board00Activity.class);
                    intent.putExtra("vo", list.get(position));
                    intent.putExtra("tabText", "detail");
                    context.startActivity(intent);




               /*   Fragment03BoardTab_Detail_Act fragment03BoardTab_detailAct = new Fragment03BoardTab_Detail_Act(context, manager, list.get(position));
                    manager.beginTransaction().replace(R.id.main_container, fragment03BoardTab_detailAct).addToBackStack(null).commit();*/

                }
            });
            /* ====================================================================== */

            /* ============================== 아이템 길게누르면 ============================== */
            holder.board_user_list_item.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    showCustomDialog();
                    Toast.makeText(context, "길게눌렸음", Toast.LENGTH_SHORT).show();
                    return false;

                }
            });
            /* ====================================================================== */

        }

        /* ===================== 롱클릭시 팝업창 =================== */
        public void showCustomDialog() { //if(list.get(position).getMember_id == Logined.member_id){}
            AlertDialog.Builder builder = new AlertDialog.Builder(context,
                    R.style.AlertDialogTheme);

            View view = LayoutInflater.from(context).inflate(
                    R.layout.common_alert_yes_or_no_item,
                    (LinearLayout) itemView.findViewById(R.id.layout_alert)
            );
            //다이얼로그 텍스트 설정


            builder.setView(view);
            ((TextView) view.findViewById(R.id.texttitle)).setText("작성자 : " + list.get(getAdapterPosition()).getMember_id() + "");
            ((TextView) view.findViewById(R.id.textmessage)).setText("내용 미리보기\n" + list.get(getAdapterPosition()).getBoard_content() + "");
            ((TextView) view.findViewById(R.id.btn_dialog_yes)).setText("갤로그");
            ((TextView) view.findViewById(R.id.btn_dialog_no)).setText("삭제");

            /* =================================== 길게눌러서 프로필사진 보기 ===================================== */
            ImageView dialog_imgv;
            dialog_imgv = view.findViewById(R.id.dialog_imgv);
            if (list.get(getAdapterPosition()).getPicture_filepath() != null) { //길게눌러서 미리보기시 프로필사진 표시
                Glide.with(context).load(list.get(getAdapterPosition()).getPicture_filepath()).into(dialog_imgv); //일단 tot로고 표시되게 해놨음
            } else {
                Glide.with(context).load(R.drawable.logo_tot).into(dialog_imgv); //일단 tot로고 표시되게 해놨음
            }
            /* ==================================================================================== */


            AlertDialog alertDialog = builder.create();
            alertDialog.setCancelable(true);   //화면밖 터치시 다이얼로그 종료

            view.findViewById(R.id.btn_dialog_yes).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    Toast.makeText(context, "갤로그", Toast.LENGTH_SHORT).show();
                    memberDTO.setMember_id(list.get(getAdapterPosition()).getMember_id());
                    memberDTO.setPicture_filepath(list.get(getAdapterPosition()).getPicture_filepath());
                    Intent intent = new Intent(context, WhosePage00Activity.class);
                    intent.putExtra("memberDTO", memberDTO);
                    intent.putExtra("tabCode", 1);
                    context.startActivity(intent);
                }
            });
            view.findViewById(R.id.btn_dialog_no).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    Toast.makeText(context, "삭제삭제", Toast.LENGTH_SHORT).show();
                }
            });
            //다이얼로그 형태 지우기
            if (alertDialog.getWindow() != null) {
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }
            alertDialog.show();
        }//showCustomDialog()
        /* =============================================================== */

    }//ViewHolder
}
