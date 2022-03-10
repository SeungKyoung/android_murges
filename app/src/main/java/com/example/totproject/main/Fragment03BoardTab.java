package com.example.totproject.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.totproject.R;
import com.example.totproject.whosepageactivity.WhosePage00Activity;
import com.example.totproject.board.Board00Activity;
import com.example.totproject.common.CommonAsk;
import com.example.totproject.common.CommonAskParam;
import com.example.totproject.common.CommonMethod;
import com.example.totproject.common.VO.BoardCommonVO;
import com.example.totproject.common.VO.MemberDTO;
import com.example.totproject.common.statics.ChangeView;
import com.example.totproject.common.statics.Logined;
import com.example.totproject.main.Adapter.BoardListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Fragment03BoardTab extends Fragment {

    private FloatingActionButton boardtab_fab_main, boardtab_fab_mypage, boardtab_fab_write, boardtab_fab_search;
    private Animation fab_open, fab_close, rotate_clockwise, rotate_anticlockwise;
    private Boolean isFabOpen = false;
    private Object FragmentBoardSearch;
    List<BoardCommonVO> list = new ArrayList<>();
    BoardCommonVO vo = new BoardCommonVO();
    RecyclerView Board_User_RcView;
    MemberDTO memberDTO = new MemberDTO();
    Context context;
    FragmentManager manager;
    TextView whosepage_tv_member_id;
String whose_member_id;
    public Fragment03BoardTab(Context context, FragmentManager manager) {
        this.context = context;
        this.manager = manager;
    }

    String whatCase;

    public Fragment03BoardTab(Context context, FragmentManager manager, String whatCase,MemberDTO memberDTO) {
        this.context = context;
        this.manager = manager;
        this.whatCase = whatCase;
        this.memberDTO = memberDTO;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.main_frag_boardtab, container, false);



        /* ====================================================================================== */
        vo.setBoard_class("notice");
        vo.setList_cnt_many(999);
        /* ====================================================================================== */
        if (whatCase == null) {
            list = selectBoardList(vo);
        } else {
            WhosePage00Activity AT = (WhosePage00Activity) context;

            if (whatCase.equals("whosePage_write")) {
                list = whoseList(memberDTO, whatCase);
            } else if (whatCase.equals("whosePage_likes")) {
                list = whoseList(memberDTO, whatCase);
            }
            AT.setActTexts(memberDTO, list);
        }

        Board_User_RcView = v.findViewById(R.id.boardtab_list_recycler);

        LinearLayoutManager lmanager = new LinearLayoutManager(
                context, RecyclerView.VERTICAL, false);
        BoardListAdapter adapter = new BoardListAdapter(context, list, manager);

        Board_User_RcView.setLayoutManager(lmanager);
        Board_User_RcView.setAdapter(adapter);
        /* ====================================================================================== */


        if (whatCase == null) {  // 재사용할려고 if()만듬
            /* ===================================== fab 버튼 ============================================ */
            boardtab_fab_search = v.findViewById(R.id.boardtab_fab_search);
            boardtab_fab_write = v.findViewById(R.id.boardtab_fab_write);
            boardtab_fab_mypage = v.findViewById(R.id.boardtab_fab_mypage);
            boardtab_fab_main = v.findViewById(R.id.boardtab_fab_main);
            boardtab_fab_search.setVisibility(View.VISIBLE);
            boardtab_fab_write.setVisibility(View.VISIBLE);
            boardtab_fab_mypage.setVisibility(View.VISIBLE);
            boardtab_fab_main.setVisibility(View.VISIBLE);

            fab_open = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fab_open);
            fab_close = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fab_close);

            boardtab_fab_write.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "팹 1", Toast.LENGTH_SHORT).show();
                    ChangeView.changeActivity(getActivity(), Board00Activity.class, "tabText", "write");
                }
            });
            boardtab_fab_search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "팹 2", Toast.LENGTH_SHORT).show();
                    ChangeView.changeActivity(getActivity(), Board00Activity.class, "tabText", "search");
                }
            });
            boardtab_fab_mypage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "팹 3", Toast.LENGTH_SHORT).show();
                    memberDTO.setMember_id(Logined.member_id);
                    memberDTO.setPicture_filepath(Logined.picture_filepath);
                    Intent intent = new Intent(context, WhosePage00Activity.class);
                    intent.putExtra("memberDTO", memberDTO);
                    intent.putExtra("tabCode", 1);
                    context.startActivity(intent);
                }
            });



            /* ============================ + 버튼ㄴ ====================================== */
            boardtab_fab_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isFabOpen) {
                        showfabMenu();
                    } else {
                        closefabMenu();
                    }
                }
            });
        }
        /* ========================================================================================= */

        return v;
    }//oncreate()

    /* ========================================메소드============================================ */


    /* ===================================== fab 버튼 ============================================ */
    private void showfabMenu() {
        isFabOpen = true;
        boardtab_fab_mypage.animate().translationY(-getResources().getDimension(R.dimen.up_1state));
        boardtab_fab_search.animate().translationY(-getResources().getDimension(R.dimen.up_2state));
        boardtab_fab_write.animate().translationY(-getResources().getDimension(R.dimen.up_3state));

    }

    private void closefabMenu() {
        isFabOpen = false;
        boardtab_fab_mypage.animate().translationY(0);
        boardtab_fab_search.animate().translationY(0);
        boardtab_fab_write.animate().translationY(0);

    }

    /* ===================================== db조회ㅜ============================================ */
    CommonAsk commonAsk;
    Gson gson = new Gson();

    public List<BoardCommonVO> selectBoardList(BoardCommonVO vo) {
        commonAsk = new CommonAsk("android/cmh/board_list");
        commonAsk.params.add(new CommonAskParam("vo", gson.toJson(vo)));
        InputStream in = CommonMethod.excuteAsk(commonAsk);
        try {
            list = gson.fromJson(new InputStreamReader(in), new TypeToken<List<BoardCommonVO>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<BoardCommonVO> whoseList(MemberDTO memberDTO, String whatCase) {
        list = new ArrayList<>();
        commonAsk = new CommonAsk("android/cmh/" + whatCase + "/");
        commonAsk.params.add(new CommonAskParam("vo", gson.toJson(memberDTO)));
        InputStream in = CommonMethod.excuteAsk(commonAsk);
        try {
            list = gson.fromJson(new InputStreamReader(in), new TypeToken<List<BoardCommonVO>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    /* ========================================================================================= */
}