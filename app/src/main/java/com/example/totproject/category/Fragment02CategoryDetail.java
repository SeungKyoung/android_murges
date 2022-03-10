package com.example.totproject.category;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.totproject.R;
import com.example.totproject.common.CommonAsk;
import com.example.totproject.common.CommonAskParam;
import com.example.totproject.common.CommonMethod;
import com.example.totproject.common.VO.BoardCommonVO;
import com.example.totproject.common.VO.PictureVO;
import com.example.totproject.common.statics.Logined;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

//고장나면 02.11 오후 4:13분꺼 보면 됨

public class Fragment02CategoryDetail extends Fragment {
    RecyclerView recyclerView;
    ViewPager2 pager2;
    TextView category_detail_tv_reply;
    TextView category_detail_tv_content;    // 추가
    TextView category_detail_like_count;    // 추가
    TextView tv_reply_cnt;    // 추가
    ImageView like_ico;
    int boardSN;      // 추가
    Fragment02CategoryDetail frag;

    int reply_check = 0;

    public Fragment02CategoryDetail(int reply_check) {
        this.reply_check = reply_check;
    }

    ArrayList<ReplyVO> getList = new ArrayList<>();
    int reply_cnt = 0;

    BoardCommonVO vo = new BoardCommonVO();
    //   Context dCOmtext;




    //    Context context; ;FragmentManager manager; int getParamSn;
//    public Fragment02CategoryDetail(Context context, FragmentManager manager, int getParamSn) { //컨텍슽르르 메인에서부터 가져옴
//        this.context = context;
//        this.manager = manager;
    //       this.getParamSn = getParamSn;
    //   }
   /* @Override
    public void onAttach(Context context){
        super.onAttach(context);
        dCOmtext = context;
    }
    */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)
                inflater.inflate(R.layout.category_frag_detail, container, false);

        pager2 = rootView.findViewById(R.id.category_detail_pager);
        like_ico = rootView.findViewById(R.id.like_ico);
        category_detail_like_count = rootView.findViewById(R.id.category_detail_like_count);

        tv_reply_cnt = rootView.findViewById(R.id.tv_reply_cnt);



        // 추가
        category_detail_tv_content = rootView.findViewById(R.id.category_detail_tv_content);

        category_detail_tv_content.setText(vo.getBoard_content());  //추가끝

        category_detail_tv_reply = rootView.findViewById(R.id.category_detail_tv_reply);

        category_detail_tv_reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CategoryReplyActivity.class);
                intent.putExtra("sn", boardSN);
                startActivity(intent);


            }
        });
/*        LinearLayout category_detail_linear_likes;
        category_detail_linear_likes = rootView.findViewById(R.id.category_detail_linear_likes);
        category_detail_linear_likes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLike();
            }
        });*/
        like_ico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLike();
            }
        });


        recyclerView = rootView.findViewById(R.id.category_detail_recview);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        getReplyList();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getParentFragmentManager().setFragmentResultListener("sn", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String sn, @NonNull Bundle bundle) {
                boardSN = bundle.getInt("sn");
                detail();
                contentPictureList();
                getLikeCount();

                getReplyList();


                if(getList != null){
                    RecAdapter adapter = new RecAdapter(getContext(), getList, frag );
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);
                    reply_cnt = getList.size();
                }

                if(reply_check == 1){
                    reply_cnt +=1;
                }

                tv_reply_cnt.setText("+"+reply_cnt);




            }
        });
    }

    /* =============================================================== */
    CommonAsk commonAsk;
    Gson gson = new Gson();

    public void detail() {
        BoardCommonVO pvo = new BoardCommonVO();
        pvo.setBoard_sn(boardSN);
        pvo.setMember_id("1111");
        commonAsk = new CommonAsk("detail_categoryBoard");
        commonAsk.params.add(new CommonAskParam("category", gson.toJson(pvo)));
        InputStream in = CommonMethod.excuteAsk(commonAsk);
        try {
            this.vo = gson.fromJson(new InputStreamReader(in), BoardCommonVO.class);
            category_detail_tv_content.setText(vo.getBoard_content());
            setLikeIcon();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /* =============================================================== */
    public void getReplyList(){
        commonAsk = new CommonAsk("list_reviewpath");
        commonAsk.params.add(new CommonAskParam("board_sn", String.valueOf(boardSN)));
        InputStream in = CommonMethod.excuteAsk(commonAsk);
        getList = gson.fromJson(new InputStreamReader(in), new TypeToken<List<ReplyVO>>(){}.getType());

        try {
            RecAdapter adapter = new RecAdapter(getContext(),getList , this);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /* =============================================================== */
    public void contentPictureList() {
        commonAsk = new CommonAsk("list_picture");
        commonAsk.params.add(new CommonAskParam("board_sn", String.valueOf(boardSN)));
        InputStream in = CommonMethod.excuteAsk(commonAsk);
        try {
            ArrayList<PictureVO> pic_list = gson.fromJson(new InputStreamReader(in), new TypeToken<List<PictureVO>>() {
            }.getType());
            PagerAdapter adapter2 = new PagerAdapter(getActivity(), pic_list);
            pager2.setAdapter(adapter2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /* =============================================================== */
    public void getLikeCount() {
        commonAsk = new CommonAsk("category_like");
        commonAsk.params.add(new CommonAskParam("board_sn", String.valueOf(boardSN)));
        InputStream in = CommonMethod.excuteAsk(commonAsk);
        String result = getStringFromInputStream(in);
        try {
            category_detail_like_count.setText(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* =============================================================== */
    public void setLike() {
        BoardCommonVO pvo = new BoardCommonVO();
        pvo.setBoard_sn(boardSN);
        pvo.setFunction_like(vo.getFunction_like());
        pvo.setMember_id("1111");
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
            Glide.with(getContext()).load(R.drawable.like).into(like_ico);
        } else {
            Glide.with(getContext()).load(R.drawable.like_gray).into(like_ico);
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
}