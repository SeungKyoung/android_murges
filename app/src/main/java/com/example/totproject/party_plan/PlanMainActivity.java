package com.example.totproject.party_plan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.totproject.R;
import com.example.totproject.party.PartyListDTO;

public class PlanMainActivity extends AppCompatActivity {
    int tabcode = 0;
    TextView tv_planmain_title;
    Button btn_planmain_create,btn_planmain_update;
    String title_name;

    PlanListFragment   plan_list_frag     ;
    PlanInfo02Fragment plan_info_frag02 ;


    // 여기 들어오다 꺼짐
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.partyplan_act_main);

        tv_planmain_title = findViewById(R.id.tv_planmain_title);
        btn_planmain_create = findViewById(R.id.btn_planmain_create);
        btn_planmain_update = findViewById(R.id.btn_planmain_update);

        btn_planmain_create.setVisibility(View.VISIBLE);
        btn_planmain_update.setVisibility(View.GONE);



        // 내파티 INFO에서 넘어옴 (파티정보)
        Intent get_intent = getIntent();
        PartyListDTO plDTO = (PartyListDTO) get_intent.getSerializableExtra("plDTO");



        tabcode = get_intent.getIntExtra("tabcode",0);

        plan_list_frag   = new PlanListFragment(PlanMainActivity.this, plDTO.getParty_sn());
        plan_info_frag02 = new PlanInfo02Fragment(PlanMainActivity.this);

        title_name = plDTO.getParty_name()+" 플랜 목록";

        // 프레그 전환
        changePlanFrag(plan_list_frag, title_name);



        //
        btn_planmain_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlanMainActivity.this, PlanCreatePlanActivity.class);
                intent.putExtra("plDTO",plDTO);
                startActivity(intent);
                finish();
            }
        });



    }//onCreate()


    // 플랜 체인지 프레그
    public void changePlanFrag(Fragment frag, String title_name ){
        getSupportFragmentManager().beginTransaction().replace(R.id.party_plan_container, frag).addToBackStack(null).commit();
        /*tv_planmain_title.setText(title_name);*/

    }



//    public void changePlanFrag(int tabcode, String title_name ){    //
//        if(tabcode == 1 || tabcode == 0){
//            getSupportFragmentManager().beginTransaction().replace(R.id.party_plan_container, plan_list_frag).commit();
//        //}else if(tabcode ==2){
//          //  getSupportFragmentManager().beginTransaction().replace(R.id.party_plan_container, plan_info_frag01).commit();
//        }else if(tabcode ==3){
//            getSupportFragmentManager().beginTransaction().replace(R.id.party_plan_container, plan_info_frag02).commit();
//        }
//
//        tv_planmain_title.setText(title_name);
//
//    }

    // 해당하는 플랜 보여주기 (이동)
    public void changePlanFrag(PlanlistDTO planDTO){
        PlanInfo01Fragment plan_info_frag01 = new PlanInfo01Fragment(PlanMainActivity.this, planDTO);
        getSupportFragmentManager().beginTransaction().replace(R.id.party_plan_container, plan_info_frag01).addToBackStack(null).commit();

        tv_planmain_title.setText(planDTO.getPlan_name());

    }






    @Override
    protected void onRestart() {
        super.onRestart();
        if(tabcode ==3){

            // getSupportFragmentManager().beginTransaction().replace(R.id.party_plan_container, plan_info_frag02).commit();
        }
    }
}