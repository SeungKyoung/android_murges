package com.example.totproject.party;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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


public class SearchPartyFragment extends Fragment {
    Context context;
    String search_keyword;
    RecyclerView search_partylist_item;
    TextView tv_party_searchresult;

    ArrayList<PartyListDTO> list = new ArrayList<>();
    CommonAsk commonAsk;
    Gson gson = new Gson();

    public SearchPartyFragment(Context context, String search_keyword) {
        this.context = context;
        this.search_keyword = search_keyword;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.party_frag_search, container, false);

        search_partylist_item = view.findViewById(R.id.search_partylist_item);
        tv_party_searchresult = view.findViewById(R.id.tv_party_searchresult);

        tv_party_searchresult.setText("'"+search_keyword + "'에 대한 검색 결과입니다.");


        searchOpenPartylist(search_keyword);
        if (list != null){
            PartyListAdapter partyListAdapter = new PartyListAdapter(context,list,1);
            LinearLayoutManager layoutManager = new LinearLayoutManager(
                    context , RecyclerView.VERTICAL , false
            );
            search_partylist_item.setLayoutManager(layoutManager);
            search_partylist_item.setAdapter(partyListAdapter);
        }



        return view;
    }



    private ArrayList<PartyListDTO> searchOpenPartylist(String search_keyword) {
        commonAsk = new CommonAsk("android/party/searchopenpartylist");
        commonAsk.params.add(new CommonAskParam("search_keyword", search_keyword ));
        InputStream in = CommonMethod.excuteAsk(commonAsk);

        try {
            list = gson.fromJson(new InputStreamReader(in), new TypeToken<List<PartyListDTO>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;

    }//searchOpenPartylist()



}