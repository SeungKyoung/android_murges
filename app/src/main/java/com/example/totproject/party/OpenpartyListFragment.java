package com.example.totproject.party;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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


public class OpenpartyListFragment extends Fragment {
    RecyclerView openpartylist_item;
    Context context;
    ArrayList<PartyListDTO> list = new ArrayList<>();
    CommonAsk commonAsk;
    Gson gson = new Gson();
    Button btn_party_search;
    EditText edt_search_keyword;
    PartyMainActivity partyMainActivity = new PartyMainActivity();
    TextView tv_openparty_tag1, tv_openparty_tag2, tv_openparty_tag3, tv_openparty_tag4, tv_openparty_tag5, tv_openparty_tag6;

    public OpenpartyListFragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.party_frag_openpartylist, container, false);

        openpartylist_item = view.findViewById(R.id.openpartylist_item);
        btn_party_search = view.findViewById(R.id.btn_party_search);
        edt_search_keyword = view.findViewById(R.id.edt_search_keyword);
        tv_openparty_tag1 = view.findViewById(R.id.tv_openparty_tag1);
        tv_openparty_tag2 = view.findViewById(R.id.tv_openparty_tag2);
        tv_openparty_tag3 = view.findViewById(R.id.tv_openparty_tag3);
        tv_openparty_tag4 = view.findViewById(R.id.tv_openparty_tag4);
        tv_openparty_tag5 = view.findViewById(R.id.tv_openparty_tag5);
        tv_openparty_tag6 = view.findViewById(R.id.tv_openparty_tag6);

        partyMainActivity = (PartyMainActivity) getActivity();


        showOpenPartylist();
        if(list != null){
            PartyListAdapter partyListAdapter = new PartyListAdapter(context,list,1);
            LinearLayoutManager layoutManager = new LinearLayoutManager(
                    context , RecyclerView.VERTICAL , false
            );
            openpartylist_item.setLayoutManager(layoutManager);
            openpartylist_item.setAdapter(partyListAdapter);
        }

        // 해쉬태그 검색
        tv_openparty_tag1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchKeyword(partyMainActivity,tv_openparty_tag1.getText()+"");
            }
        });
        tv_openparty_tag2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchKeyword(partyMainActivity,tv_openparty_tag2.getText()+"");
            }
        });
        tv_openparty_tag3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchKeyword(partyMainActivity,tv_openparty_tag3.getText()+"");
            }
        });
        tv_openparty_tag4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchKeyword(partyMainActivity,tv_openparty_tag4.getText()+"");
            }
        });
        tv_openparty_tag5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchKeyword(partyMainActivity,tv_openparty_tag5.getText()+"");
            }
        });
        tv_openparty_tag6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchKeyword(partyMainActivity,tv_openparty_tag6.getText()+"");
            }
        });




        //검색 버튼
        btn_party_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchKeyword(partyMainActivity,edt_search_keyword.getText()+"");
            }
        });








        return view;
    }//onCreateView()

    private void searchKeyword(PartyMainActivity partyMainActivity, String search_keyword) {
        SearchPartyFragment searchPartyFrag= new SearchPartyFragment(getActivity(),search_keyword);
        partyMainActivity.changeFrag(searchPartyFrag,"검색 결과");
    }


    private ArrayList<PartyListDTO> showOpenPartylist() {
        commonAsk = new CommonAsk("android/party/openpartylist");
        InputStream in = CommonMethod.excuteAsk(commonAsk);

        try {
            list = gson.fromJson(new InputStreamReader(in), new TypeToken<List<PartyListDTO>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;

    }//showOpenPartylist()







}