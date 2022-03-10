package com.example.totproject.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.totproject.R;
import com.example.totproject.common.CommonAsk;
import com.example.totproject.common.CommonAskParam;
import com.example.totproject.common.CommonMethod;
import com.example.totproject.common.VO.BoardCommonVO;
import com.example.totproject.main.Adapter.MainTabAdapter_big;

import com.example.totproject.main.Adapter.MainTabAdapter_small_mbti;
import com.example.totproject.main.Adapter.MainTabAdapter_small_xy;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class Fragment01HomeTab extends Fragment {

/*mRecyclerView.setLayoutManager(new LinearLayoutManager(this));	// 세로

mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));	// 가로*/

    RecyclerView maintab_rv_recommend,  maintab_rv_tour;
    ViewPager2 maintab_rv_where ;
    SwipeRefreshLayout swipe_layout;

    ArrayList<BoardCommonVO> list = new ArrayList<>();
    BoardCommonVO vo = new BoardCommonVO();

    Context context;
    FragmentManager manager;

    public Fragment01HomeTab(MainActivity context, FragmentManager manager) {
        this.context = context;
        this.manager = manager;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_frag_hometab, container, false);
        /* ============================== 새로고침 ============================== 기존 버튼==>스와이프로수정03/04김영문 */
  /*      swipe_layout = v.findViewById(R.id.swipe_layout);
        swipe_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                MainActivity activity = (MainActivity) context;
                Intent intent = activity.getIntent();
                startActivity(activity.getIntent());
                activity.finish();
                ActivityCompat.finishAffinity(activity);
                swipe_layout.setRefreshing(false);
            }
        });*/
        /* ==================== 표시할항목수 ====================*/
        int cnt = 10;
        vo.setList_cnt_many(cnt);
        /* ======================================================*/

        // 맵핑 ex) board_list@board_class=notice/view_cnt=10/

        /* ==================== 추천 ====================*/
        {
            // list= dbBoardCall("android/cmh/board_list@class=vs/view_cnt="+cnt+"/");
            list = dbBoardCall("android/cmh/mbti_mbti/");
            maintab_rv_recommend = v.findViewById(R.id.maintab_rv_recommend);
            LinearLayoutManager lmanager = new LinearLayoutManager(
                    context, RecyclerView.HORIZONTAL, false);
            MainTabAdapter_small_mbti adapter = new MainTabAdapter_small_mbti(getContext(), list, manager);

            maintab_rv_recommend.setLayoutManager(lmanager);
            maintab_rv_recommend.setAdapter(adapter);
        }
        /* ==============================================*/

        /* ==================== 거리 ====================*/
        {
            // list = dbBoardCall("android/cmh/board_list@board+class=activity/view_cnt=" + cnt + "/");
            vo.setBoard_class("activity");
            // list = selectBoardList(vo);
            list = dbBoardCall("android/cmh/mbti_xy/");
            maintab_rv_where = v.findViewById(R.id.maintab_rv_where);

            LinearLayoutManager lmanager = new LinearLayoutManager(
                    context, RecyclerView.HORIZONTAL, false);
            MainTabAdapter_small_xy adapter = new MainTabAdapter_small_xy(getContext(), list, manager);
           // maintab_rv_where.set(lmanager);

            maintab_rv_where.setAdapter(adapter);
            setSlide(maintab_rv_where);
        }
        /* ==============================================*/

        /* ==================== 여행지 ====================*/
        {
            // list = dbBoardCall("android/cmh/board_list@board_class=tour/view_cnt=" + cnt + "/");
            vo.setBoard_class("tour");
            list = selectBoardList(vo);

            maintab_rv_tour = v.findViewById(R.id.maintab_rv_tour);
            LinearLayoutManager lmanager = new LinearLayoutManager(
                    context, RecyclerView.VERTICAL, false);
            MainTabAdapter_big adapter = new MainTabAdapter_big(getContext(), list, manager);

            maintab_rv_tour.setLayoutManager(lmanager);
            maintab_rv_tour.setAdapter(adapter);
        }
        /* ================================================*/

        return v;
    }

    CommonAsk commonAsk;
    Gson gson = new Gson();

    public ArrayList<BoardCommonVO> dbBoardCall(String mapping) {
        commonAsk = new CommonAsk(mapping);
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

    public void setSlide(ViewPager2 pager2) {
        pager2.setClipToPadding(false);
        pager2.setClipChildren(false);
        pager2.setOffscreenPageLimit(3);
        pager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });
        pager2.setPageTransformer(compositePageTransformer);
    }

}