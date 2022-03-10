package com.example.totproject.main;

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
import com.example.totproject.common.CommonAsk;
import com.example.totproject.common.CommonAskParam;
import com.example.totproject.common.CommonMethod;
import com.example.totproject.common.VO.BoardCommonVO;
import com.example.totproject.common.statics.Logined;
import com.example.totproject.main.Adapter.MainTabAdapter_big;
import com.example.totproject.main.Adapter.MainTabAdapter_small_mbti;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class Fragment01MainTab extends Fragment {

/*mRecyclerView.setLayoutManager(new LinearLayoutManager(this));	// 세로

mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));	// 가로*/

    RecyclerView maintab_rv_recommend, maintab_rv_where, maintab_rv_tour;


    ArrayList<BoardCommonVO> list = new ArrayList<>();

    Context context;
    FragmentManager manager;

    public Fragment01MainTab(MainActivity context, FragmentManager manager) {
        this.context = context;
        this.manager = manager;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_frag_hometab, container, false);
        int cnt = 5;

        /*================================= 탑10추천 =================================*/
        {
            //  board_list@board_class=notice/view_cnt=10/
            //  list= dbBoardCall("android/cmh/board_list@class=vs/view_cnt="+cnt+"/");
            list = dbBoardCall("android/cmh/mbti/");
            maintab_rv_recommend = v.findViewById(R.id.maintab_rv_recommend);
            LinearLayoutManager lmanager = new LinearLayoutManager(
                    context, RecyclerView.HORIZONTAL, false);
            MainTabAdapter_small_mbti adapter = new MainTabAdapter_small_mbti(getContext(), list, manager);

            maintab_rv_recommend.setLayoutManager(lmanager);
            maintab_rv_recommend.setAdapter(adapter);
        }
        /*===========================================================================*/

        /*================================= 거리추천 =================================*/
        {
            list = dbBoardCall("android/cmh/board_list@board+class=review/view_cnt=" + cnt + "/");
            maintab_rv_where = v.findViewById(R.id.maintab_rv_where);
            LinearLayoutManager lmanager = new LinearLayoutManager(
                    context, RecyclerView.HORIZONTAL, false);
            MainTabAdapter_small_mbti adapter = new MainTabAdapter_small_mbti(getContext(), list, manager);

            maintab_rv_where.setLayoutManager(lmanager);
            maintab_rv_where.setAdapter(adapter);
        }
        /*===============================================================================*/

        /*=================================여행지리스트=================================*/
        {
            list = dbBoardCall("android/cmh/board_list@board+class=tour/view_cnt=" + cnt + "/");
            maintab_rv_tour = v.findViewById(R.id.maintab_rv_tour);
            LinearLayoutManager lmanager = new LinearLayoutManager(
                    context, RecyclerView.VERTICAL, false);
            MainTabAdapter_big adapter = new MainTabAdapter_big(getContext(), list, manager);

            maintab_rv_tour.setLayoutManager(lmanager);
            maintab_rv_tour.setAdapter(adapter);
        }
        /*==================================================================================*/

        return v;
    }

    CommonAsk commonAsk;
    Gson gson = new Gson();

    public ArrayList<BoardCommonVO> dbBoardCall(String mapping) {
        commonAsk = new CommonAsk(mapping);
        String lgcode = "a";
        if (Logined.member_id != null) {
            lgcode = Logined.member_id;
        }
        commonAsk.params.add(new CommonAskParam("member_id", lgcode));
        InputStream in = CommonMethod.excuteAsk(commonAsk);
        try {
            list = gson.fromJson(new InputStreamReader(in), new TypeToken<List<BoardCommonVO>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<BoardCommonVO> selectBoardList(BoardCommonVO vo) {
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
}