package com.example.totproject.party_plan;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.totproject.R;
import com.example.totproject.common.CommonAsk;
import com.example.totproject.common.CommonAskParam;
import com.example.totproject.common.CommonMethod;
import com.example.totproject.common.VO.MemberDTO;
import com.example.totproject.party.PartyListDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class PlanListFragment extends Fragment {
    RecyclerView planlist_item;
    Context context;
    int party_sn = 0 ;           // @@@@@ 추가로 작성자 처리 생각할 것

    ArrayList<PlanlistDTO> list = new ArrayList<>();
    CommonAsk commonAsk;
    Gson gson = new Gson();

    public PlanListFragment(Context context, int party_sn) {
        this.context = context;
        this.party_sn = party_sn;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.partyplan_frag_planlist, container, false);

        planlist_item = view.findViewById(R.id.planlist_item);


        showPlanList();


        if(list != null){
            PlanListAdapter planListAdapter = new PlanListAdapter(context,list);
            LinearLayoutManager layoutManager = new LinearLayoutManager(
                    context , RecyclerView.VERTICAL , false
            );

            planlist_item.setLayoutManager(layoutManager);
            planlist_item.setAdapter(planListAdapter);
        }else{
            Toast.makeText(context, "생성된 플랜이 없습니다(임시)", Toast.LENGTH_SHORT).show();
        }







        return view;
    }//onCreateView()


    private ArrayList<PlanlistDTO> showPlanList() {
        commonAsk = new CommonAsk("android/party/planlist");
        commonAsk.params.add(new CommonAskParam("party_sn", party_sn+""  ) );
        InputStream in = CommonMethod.excuteAsk(commonAsk);

        try {
            list = gson.fromJson(new InputStreamReader(in), new TypeToken<List<PlanlistDTO>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;

    }//showPlanList()

}