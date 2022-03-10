package com.example.totproject.mainburgeractivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.totproject.R;
import com.example.totproject.category.Fragment02CategoryDetail;
import com.example.totproject.common.VO.BoardCommonVO;
import com.example.totproject.mainburgeractivity.Adapter.NoticeAdapter;


import java.util.ArrayList;

public class MainBurger01NoticeFg_backup extends Fragment {


    RecyclerView notice_rc_view;
    Context context;
    FragmentManager manager;
    public View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            BoardCommonVO vo = new BoardCommonVO();

            Fragment02CategoryDetail categoryDetail = new Fragment02CategoryDetail(0);
            getFragmentManager().beginTransaction().replace(R.id.cate_container, categoryDetail).commit();
            Bundle bundle = new Bundle();
            bundle.putString("sn", vo.getBoard_sn() + "");
            getParentFragmentManager().setFragmentResult("sn", bundle);

        }
    };

    public MainBurger01NoticeFg_backup(MainBurger00Activity mainBurger00Activity, FragmentManager manager) {
        this.context = mainBurger00Activity;
        this.manager = manager;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup v = (ViewGroup)
                inflater.inflate(R.layout.mainburger01_notice_fg, container, false);

        ArrayList<BoardCommonVO> list = new ArrayList<>();

        for (int i = 0; i < 500; i++) {
            BoardCommonVO vo = new BoardCommonVO();
            vo.setBoard_title(i + 1 + "번째 공지사항 제목");
            vo.setBoard_date_create("data");

            list.add(vo);
        }
        notice_rc_view = v.findViewById(R.id.notice_list);


        LinearLayoutManager lmanager = new LinearLayoutManager(
                context, RecyclerView.VERTICAL, false);
        NoticeAdapter adapter = new NoticeAdapter(getContext(), list, manager);

        notice_rc_view.setLayoutManager(lmanager);
        notice_rc_view.setAdapter(adapter);

        //manager.setOrientation(LinearLayoutManager.VERTICAL);


        return v;
    }


}

