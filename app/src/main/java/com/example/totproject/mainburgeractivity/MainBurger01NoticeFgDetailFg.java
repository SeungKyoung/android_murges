package com.example.totproject.mainburgeractivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.totproject.R;
import com.example.totproject.common.CommonAsk;
import com.example.totproject.common.CommonAskParam;
import com.example.totproject.common.CommonMethod;
import com.example.totproject.common.VO.BoardCommonVO;
import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;


public class MainBurger01NoticeFgDetailFg extends Fragment {

    TextView notice_detail_title, notice_detail_content;
    TextView notice_detail_writedate;
    
      BoardCommonVO vo = new   BoardCommonVO();

    Context context;
    FragmentManager manager;
    int paramSn;

    public MainBurger01NoticeFgDetailFg(Context context, FragmentManager manager, int paramSn) { //컨텍슽르르 메인에서부터 가져옴
        this.context = context;
        this.manager = manager;
        this.paramSn = paramSn;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.mainburger01_notice_fg_detail_fg, container, false);

        notice_detail_title = v.findViewById(R.id.notice_detail_title);
        notice_detail_content = v.findViewById(R.id.notice_detail_content);
        notice_detail_writedate = v.findViewById(R.id.notice_detail_writedate);

vo = detail(paramSn);

notice_detail_title.setText(vo.getBoard_title());
        notice_detail_content.setText(vo.getBoard_content());
        notice_detail_writedate.setText(vo.getBoard_date_create()+"");






        return v;

    }
    CommonAsk commonAsk;
    Gson gson = new Gson();

    public   BoardCommonVO detail(int paramSn) {
        commonAsk = new CommonAsk("android/cmh/board_detail@notice/");
        commonAsk.params.add(new CommonAskParam("paramSn", paramSn+""));
        InputStream in = CommonMethod.excuteAsk(commonAsk);

        try {
            vo = gson.fromJson(new InputStreamReader(in),   BoardCommonVO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vo;
    }
}