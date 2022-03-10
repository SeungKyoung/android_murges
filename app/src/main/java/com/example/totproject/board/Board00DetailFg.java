package com.example.totproject.board;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.totproject.R;
import com.example.totproject.common.CommonAsk;
import com.example.totproject.common.CommonAskParam;
import com.example.totproject.common.CommonMethod;
import com.example.totproject.common.VO.BoardCommonVO;
import com.example.totproject.common.statics.Logined;
import com.example.totproject.board.Adapter.ReplyAdapter;
import com.example.totproject.board.VO.ReplyVO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class Board00DetailFg extends Fragment {

    LinearLayout board_user_detail_linear_update, board_user_detail_linear_delete, board_user_detail_linear_like;
    LinearLayout board_user_detail_layout_replybar;
    RelativeLayout board_user_detail_layout_ismine;

    ImageView board_user_detail_imgv_profile, board_user_detail_img_reply_submit, board_user_detail_img_like;
    TextView board_user_detail_tv_member_id, board_user_detail_tv_replycnt, board_user_detail_tv_like_cnt;
    EditText board_user_detail_edt_board_title, board_user_detail_edt_board_content;
    Button board_act_btn_left, board_act_btn_right;
    EditText board_user_detail_edt_reply_input;

    String whatCase;
    int succ;
    int boardSN;
    int paramSn;

    RecyclerView Board_User_Reply_RcView;
    BoardCommonVO vo = new BoardCommonVO();
    ReplyVO replyVO = new ReplyVO();
    List<ReplyVO> list = new ArrayList<>();

    Context context;
    FragmentManager manager;

    /* ==================================== * from tbl_board ===================================== */
    public Board00DetailFg() {
    }

    public Board00DetailFg(Context context, FragmentManager manager, BoardCommonVO vo) {
        this.context = context;
        this.manager = manager;
        this.vo = vo;
    }

    public Board00DetailFg(Context context, FragmentManager manager, String whatCase) {
        this.context = context;
        this.manager = manager;
        this.whatCase = whatCase;
        if (whatCase.equals("write")) {
            this.vo.setMember_id(Logined.member_id);
            this.vo.setBoard_class("user");
        }
    }
    /* =========================================================================================== */

    /* =========================================================================================== */


    /*    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.zzz_boardtab_frag_detail);
            Intent getinIntent = new Intent(getIntent());
            vo = (BoardCommonVO) getinIntent.getSerializableExtra("vo");*/
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.boardtab_frag_detail, container, false);
        String pageMode = null;
        /* ================ 페이지모드 구분 ( 글상세보기 / 글쓰기 / 수정) ================= */
        if (whatCase == null) {
            pageMode = "detailView";
        } else if (whatCase.equals("write")) {
            pageMode = "write";
        } else if (whatCase.equals("update")) {
            pageMode = "update";
        } else {
            pageMode = whatCase;
        }



        /* ========================================================================================================== */

        /* ================================= board_detail 공통 findview ========================================= */
        board_user_detail_imgv_profile = v.findViewById(R.id.board_user_detail_imgv_profile);
        board_user_detail_edt_board_title = v.findViewById(R.id.board_user_detail_edt_board_title);
        board_user_detail_edt_board_content = v.findViewById(R.id.board_user_detail_edt_board_content);
        board_user_detail_tv_replycnt = v.findViewById(R.id.board_user_detail_tv_replycnt);
        board_user_detail_tv_member_id = v.findViewById(R.id.board_user_detail_tv_member_id);
        board_user_detail_tv_member_id.setText(vo.getMember_id());
        board_act_btn_left = getActivity().findViewById(R.id.board_act_btn_left);
        board_act_btn_right = getActivity().findViewById(R.id.board_act_btn_right);
        board_user_detail_layout_replybar = v.findViewById(R.id.board_user_detail_layout_replybar);

        if (pageMode.equals("detailView")) {/* ================= 디테일뷰모드 ================= */
            /* ================================= board_content 세팅 ========================================= */
            Glide.with(Board00DetailFg.this).load(vo.getPicture_filepath()).into(board_user_detail_imgv_profile);
            board_user_detail_edt_board_title.isEnabled();
            board_user_detail_edt_board_content.isEnabled();

            board_user_detail_edt_board_title.setText(vo.getBoard_title() + "");
            board_user_detail_edt_board_content.setText(vo.getBoard_content() + "");
            board_user_detail_tv_replycnt.setText(vo.getBoard_cnt_reply() + "");
            boardSN = vo.getBoard_sn();
            detail();
            /* ====================================================================================== */

            /* ================================= 내 게시물일 경우에만 수정/삭제 세팅 ========================================= */
            if (vo.getMember_id().equals(Logined.member_id)) {
                board_user_detail_layout_ismine = v.findViewById(R.id.board_user_detail_layout_ismine);
                board_user_detail_linear_update = v.findViewById(R.id.board_user_detail_linear_update);
                board_user_detail_linear_delete = v.findViewById(R.id.board_user_detail_linear_delete);
                board_user_detail_layout_ismine.setVisibility(View.VISIBLE);



                board_user_detail_linear_update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(context, "수정 눌림", Toast.LENGTH_SHORT).show();
                        writeMode();


                    }
                });
                board_user_detail_linear_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        succ = board_delete(boardSN);
                        if (succ > 0) {    //삭제성공
                            Toast.makeText(context, "삭제성공", Toast.LENGTH_SHORT).show();
                            getActivity().finish();
                        } else {  //삭제실패
                            Toast.makeText(context, "잠시 후 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                board_act_btn_left.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(context, "눌렸음", Toast.LENGTH_SHORT).show();
                    }
                });
                board_act_btn_right.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(context, "눌렸음", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            /* ===================================== DB댓글조회 및 어댑터 ============================== */
            Board_User_Reply_RcView = v.findViewById(R.id.board_useR_detail_recycler_detail);
            selectReplys();
            /* ====================================================================================== */


            /* ===================================== 하단 액션바 ============================== */
            board_user_detail_linear_like = v.findViewById(R.id.board_user_detail_linear_like);//좋아요 구역
            board_user_detail_edt_reply_input = v.findViewById(R.id.board_user_detail_edt_reply_input);//댓글입력
            board_user_detail_img_reply_submit = v.findViewById(R.id.board_user_detail_img_reply_submit);//댓글게시
            board_user_detail_tv_like_cnt = v.findViewById(R.id.board_user_detail_tv_like_cnt);//좋아요 cnt
            board_user_detail_img_like = v.findViewById(R.id.board_user_detail_img_like);// 좋아요 버튼

            board_user_detail_img_reply_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String inputReplyContent = board_user_detail_edt_reply_input.getText() + "";

                    /* == tbl_reply 기입할 정보 set == */
                    replyVO.setReply_content(inputReplyContent);
                    replyVO.setBoard_sn(boardSN);
                    replyVO.setMember_id(Logined.member_id);

                    succ = insertReply(replyVO);
                    if (succ > 0) {
                        Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
                        board_user_detail_edt_reply_input.setText("");
                    } else {
                        Toast.makeText(getActivity(), "댓글등록 실패", Toast.LENGTH_SHORT).show();
                    }
                    selectReplys();
                }
            });
            getLikeCount();

            board_user_detail_img_like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int likes = Integer.parseInt(board_user_detail_tv_like_cnt.getText() + "");
                    setLike();
                }
            });
            setLikeIcon();

            /* ====================================================================================== */
        } else if (pageMode.equals("write")) { /* ================= 글쓰기모드 ================= */
            Glide.with(Board00DetailFg.this).load(Logined.picture_filepath).into(board_user_detail_imgv_profile);
                /*        a01.setVisibility(View.GONE);          // view 삭제
        a01.setVisibility(View.INVISIBLE);   // view 그대로 숨기기
        a01.setVisibility(View.VISIBLE);      // view 보이기*/
            /* ========================== 프레임 레이아웃 교체 ============================= */
               //레이아웃선언
            //버튼선언

            viewTopBtns();



            /* ========================================================================== */
            board_act_btn_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity(), "확인눌림", Toast.LENGTH_SHORT).show();
                    vo.setBoard_title(board_user_detail_edt_board_title.getText() + "");
                    vo.setBoard_title(board_user_detail_edt_board_content.getText() + "");
                    int succ = board_insert(vo);
                    if (succ > 0) {
                        Toast.makeText(getActivity(), "등록성공", Toast.LENGTH_SHORT).show();
                        manager.popBackStack();
                    } else {
                        Toast.makeText(getActivity(), "등록실패", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            board_act_btn_left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity(), "취소눌림", Toast.LENGTH_SHORT).show();
                }
            });



        }
        return v;
    }//onCreateView

    @Override
    public void onResume() {
        super.onResume();
    }

    /* ===================================== db조회============================================ */
    CommonAsk commonAsk;
    Gson gson = new Gson();

    public void detail() {
        BoardCommonVO pvo = new BoardCommonVO();
        pvo.setBoard_sn(boardSN);
        pvo.setMember_id(Logined.member_id);
        commonAsk = new CommonAsk("detail_categoryBoard");
        commonAsk.params.add(new CommonAskParam("category", gson.toJson(pvo)));
        InputStream in = CommonMethod.excuteAsk(commonAsk);
        try {
            this.vo = gson.fromJson(new InputStreamReader(in), BoardCommonVO.class);
            board_user_detail_edt_board_content.setText(vo.getBoard_content() + "");
            setLikeIcon();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<ReplyVO> selectReply(int board_sn) {
        commonAsk = new CommonAsk("android/cmh/reply_select/");
        commonAsk.params.add(new CommonAskParam("paramSn", board_sn + ""));
        InputStream in = CommonMethod.excuteAsk(commonAsk);
        try {
            list = gson.fromJson(new InputStreamReader(in), new TypeToken<List<ReplyVO>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /* =================================== DB insert========================================== */
    public int board_insert(BoardCommonVO vo) {
        int succ = 0;
        commonAsk = new CommonAsk("android/cmh/board_insert");
        commonAsk.params.add(new CommonAskParam("vo", gson.toJson(vo)));
        //파일은 안주므로 주석  commonAsk.fileParams.add(new CommonAskParam("file", img_filepath));
        InputStream in = CommonMethod.excuteAsk(commonAsk);

        try {
            succ = gson.fromJson(new InputStreamReader(in), Integer.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return succ;
    }

    /* =============================================================== */
    public void getLikeCount() {
        commonAsk = new CommonAsk("category_like");
        commonAsk.params.add(new CommonAskParam("board_sn", String.valueOf(boardSN)));
        InputStream in = CommonMethod.excuteAsk(commonAsk);
        String result = getStringFromInputStream(in);
        try {
            board_user_detail_tv_like_cnt.setText(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* =============================================================== */
    public void setLike() {
        BoardCommonVO pvo = new BoardCommonVO();
        pvo.setBoard_sn(boardSN);
        pvo.setFunction_like(vo.getFunction_like());
        pvo.setMember_id(Logined.member_id);
        commonAsk = new CommonAsk("set_like");
        commonAsk.params.add(new CommonAskParam("category", gson.toJson(pvo)));
        InputStream in = CommonMethod.excuteAsk(commonAsk);
        String result = getStringFromInputStream(in);

        if (vo.getFunction_like() > 0) {
            vo.setFunction_like(0);
        } else {
            vo.setFunction_like(1);
        }
        setLikeIcon();
    }

    /* =============================================================== */
    public void setLikeIcon() {
        if (vo.getFunction_like() > 0) {
            Glide.with(Board00DetailFg.this).load(R.drawable.like).into(board_user_detail_img_like);
        } else {
            Glide.with(Board00DetailFg.this).load(R.drawable.like_gray).into(board_user_detail_img_like);
        }
        getLikeCount();
    }

    /* =============================================================== */
    private String getStringFromInputStream(InputStream is) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
    /* =============================================================== */

    /* =============================== 댓글 insert ======================= */
    public int insertReply(ReplyVO replyVO) { //insert into tbl_reply
        succ = 0;
        commonAsk = new CommonAsk("android/cmh/reply_insert/");
        commonAsk.params.add(new CommonAskParam("replyVO", gson.toJson(replyVO)));
        InputStream in = CommonMethod.excuteAsk(commonAsk);
        try {
            succ = gson.fromJson(new InputStreamReader(in), Integer.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return succ;
    }
    /* =============================================================== */

    public void selectReplys() {
        selectReply(boardSN);
        LinearLayoutManager lmanager = new LinearLayoutManager(
                getActivity(), RecyclerView.VERTICAL, false);
        ReplyAdapter adapter = new ReplyAdapter(getActivity(), list);
        Board_User_Reply_RcView.setLayoutManager(lmanager);
        Board_User_Reply_RcView.setAdapter(adapter);
    }

    public int board_delete(int boardSN) {
        succ = 0;
        commonAsk = new CommonAsk("android/cmh/board_delete/");
        commonAsk.params.add(new CommonAskParam("boardSN", boardSN + ""));
        InputStream in = CommonMethod.excuteAsk(commonAsk);
        try {
            succ = gson.fromJson(new InputStreamReader(in), Integer.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return succ;
    }

    public int board_update() {
        succ = 0;

        return succ;
    }


    /* ===================== setVisibility =========================== */
    public void viewTopBtns() {
        board_act_btn_left.setVisibility(View.VISIBLE);
        board_act_btn_right.setVisibility(View.VISIBLE);
    }
    public void viewDetailFunctions(){
        board_user_detail_layout_ismine.setVisibility(View.VISIBLE);
    }

    public void viewBottomBar() {
        board_user_detail_layout_replybar.setVisibility(View.VISIBLE);
    }

    public void hideTopBtns() {
        board_act_btn_left.setVisibility(View.GONE);
        board_act_btn_right.setVisibility(View.GONE);
    }
    public void hideDetailFunctions(){
        board_user_detail_layout_ismine.setVisibility(View.GONE);

    }

    public void hideBottomBar() {
        board_user_detail_layout_replybar.setVisibility(View.GONE);

    }

    public void writeMode() {
        viewTopBtns();
        hideBottomBar();
        hideDetailFunctions();
        board_user_detail_edt_board_content.setEnabled(true);
        board_user_detail_edt_board_content.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

    }
    public void viewMode(){
        hideTopBtns();
        viewBottomBar();
        viewDetailFunctions();
        board_user_detail_edt_board_content.setEnabled(false);
        InputMethodManager immhide = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        immhide.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

}