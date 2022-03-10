package com.example.totproject.party_plan;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.totproject.R;
import com.example.totproject.common.CommonAsk;
import com.example.totproject.common.CommonAskParam;
import com.example.totproject.common.CommonMethod;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class PlanInfo02Fragment extends Fragment {

    ExpandableListView expdListView;
    PlanlistDTO planDTO;
    Context context;
    CommonAsk commonAsk;
    Gson gson = new Gson();
    ArrayList<PlanInfoDTO> list = new ArrayList<>();


    public PlanInfo02Fragment(Context context) { //컨텍슽르르 메인에서부터 가져옴
        this.context = context;
    }

    public PlanInfo02Fragment(Context context, PlanlistDTO planDTO) { //컨텍슽르르 메인에서부터 가져옴
        this.context = context;
        this.planDTO = planDTO;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.partyplan_frag_planinfo02, container, false);

        // 디비 연결 후 리스트 리턴
        showPlanInfo(planDTO.getPlan_sn());

        ArrayList<PlanInfoDTO> item_list = new ArrayList<>();
        String plan_dtl = "";
        String old_plan_dtl = "";
        int flag = 0 ;

        // @@@ 아마도 익스펜더블 뷰 세팅 + 추가 작업?
        if(list != null){
            for(int i = 0 ; i < list.size(); i++){
                plan_dtl = list.get(i).getPlandetail_date();
                if(!plan_dtl.equals(old_plan_dtl)){
                    old_plan_dtl = plan_dtl;
                    item_list.add(list.get(i));
                    item_list.get(flag).setSubList();

                    for(int j = 0 ; j < list.size(); j++){
                        if(item_list.get(flag).getPlandetail_date().equals(list.get(j).getPlandetail_date())){
                            try{
                                item_list.get(flag).getSubList().add(list.get(j));

                            }catch (Exception e){
                                String aaa = "";
                            }
                        }
                    }
                    flag ++ ;
                }
            }
        }



        expdListView = view.findViewById(R.id.expdListView);
        if (list != null){
            PlanInfoAdapter adapter = new PlanInfoAdapter(getContext() , inflater, item_list);
            expdListView.setAdapter(adapter);
        }else if(list.size() == 0){
            Toast.makeText(getActivity(), "파티 플랜이 없습니다 (임시)", Toast.LENGTH_SHORT).show();
        }






        return view;
    }//onCreateView()

    private ArrayList<PlanInfoDTO> showPlanInfo(int plan_sn) {
        commonAsk = new CommonAsk("android/party/showplaninfo");
        commonAsk.params.add(new CommonAskParam("plan_sn", plan_sn+""));
        InputStream in = CommonMethod.excuteAsk(commonAsk);

        try {
            list = gson.fromJson(new InputStreamReader(in), new TypeToken<List<PlanInfoDTO>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }









}