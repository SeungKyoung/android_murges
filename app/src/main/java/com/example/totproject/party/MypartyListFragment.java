package com.example.totproject.party;

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
import com.example.totproject.common.statics.Logined;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class MypartyListFragment extends Fragment {
    RecyclerView mypartylist_item;
    Context context;
    ArrayList<PartyListDTO> list = new ArrayList<>();
    CommonAsk commonAsk;
    Gson gson = new Gson();



    public MypartyListFragment(Context context) {
        this.context = context;
    }
//저 만져도 대나용
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.party_frag_mypartylist, container, false);

        mypartylist_item = view.findViewById(R.id.mypartylist_item);

        // 내파티리스트 보여주기
        if(Logined.member_id != null){
            showMyPartylist();
        }




        // 가입된 파티가 있는지 없는지
        if (list != null){
            PartyListAdapter partyListAdapter = new PartyListAdapter(context,list,3);
            LinearLayoutManager layoutManager = new LinearLayoutManager(
                    context , RecyclerView.VERTICAL , false
            );
            mypartylist_item.setLayoutManager(layoutManager);
            mypartylist_item.setAdapter(partyListAdapter);
        }else{
            Toast.makeText(getActivity(), "가입된 파티가 없습니다 (임시)", Toast.LENGTH_SHORT).show();
        }


        return view;
    }//onCreateView()


    public ArrayList<PartyListDTO> showMyPartylist(){
        commonAsk = new CommonAsk("android/party/mypartylist");
        commonAsk.params.add(new CommonAskParam("member_id", Logined.member_id));
        InputStream in = CommonMethod.excuteAsk(commonAsk);

        try {
            list = gson.fromJson(new InputStreamReader(in), new TypeToken<List<PartyListDTO>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;

    }//showMyPartylist()















}