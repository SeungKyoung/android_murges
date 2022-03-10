package com.example.totproject.party_plan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
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

public class PlanUpdatePlanDetailActivity extends AppCompatActivity {
    RecyclerView plan_detail_list;

    CommonAsk commonAsk;
    Gson gson = new Gson();
    ArrayList<PlanInfoDTO> list = new ArrayList<>();
    ArrayList<PlanInfoDTO> delete_list = new ArrayList<>();
    ArrayList<PlanlistDTO> planlistDTO = new ArrayList<>();
    TextView tv_partyplan_title, tv_partyplan_detail_day;
    EditText edt_plandetail_time, edt_plandetail_content, edt_plandetail_content_detail;
    Button btn_create_plan_detail , btn_plandetailupdate_delete;
    int plan_sn = 1;
    int tabcode = 0;
    String plandetail_day = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.partyplan_act_updateplandetail);

        plan_detail_list = findViewById(R.id.plan_detail_list);
        tv_partyplan_detail_day = findViewById(R.id.tv_partyplan_detail_day);
        edt_plandetail_time = findViewById(R.id.edt_plandetail_time);
        edt_plandetail_content = findViewById(R.id.edt_plandetail_content);
        edt_plandetail_content_detail = findViewById(R.id.edt_plandetail_content_detail);
        btn_create_plan_detail = findViewById(R.id.btn_create_plan_detail);
        tv_partyplan_title = findViewById(R.id.tv_partyplan_title);
        btn_plandetailupdate_delete = findViewById(R.id.btn_plandetailupdate_delete);
        Intent getIntent = getIntent();

        // 수정에서 넘어옴
        PlanInfoDTO planInfoDTO = new PlanInfoDTO();

        tabcode = getIntent.getIntExtra("tabcode",-1);
        //익스팬더블뷰 에서 넘어 왔다면 해당 디테일 보여주기
        if (tabcode == 1){
            plan_sn = getIntent.getIntExtra("plan_sn", -1);
            plandetail_day = getIntent.getStringExtra("palndetail_day");
            planInfoDTO.setPlan_sn(plan_sn);
            planInfoDTO.setPlandetail_date(plandetail_day);

        // 업데이트 어댑터에서 넘어왔다면 수정하기
        }else if (tabcode == 2){
            planInfoDTO = (PlanInfoDTO) getIntent.getSerializableExtra("planInfoDTO");
            updatePlanInfo(planInfoDTO);
        }







        showPlanInfoDetail(planInfoDTO);

        tv_partyplan_title.setText(planlistDTO.get(0).getPlan_name());
        // @@ 타이틀 세팅(해당플랜 이름으로) 왜작동안댐?
//        selectPlan(plan_sn);
//        tv_partyplan_title.setText(planlistDTO.get(0).getPlan_name());


        tv_partyplan_detail_day.setText(list.get(0).getPlandetail_date());

        if(list != null){
            PlanUpdatePlanDetailAdapter planUpdateAdapter = new PlanUpdatePlanDetailAdapter(PlanUpdatePlanDetailActivity.this,list);
            LinearLayoutManager layoutManager = new LinearLayoutManager(
                    PlanUpdatePlanDetailActivity.this , RecyclerView.VERTICAL , false
            );
            plan_detail_list.setLayoutManager(layoutManager);
            plan_detail_list.setAdapter(planUpdateAdapter);
        }

        btn_create_plan_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlanInfoDTO newplanInfoDTO = new PlanInfoDTO(
                        plandetail_day,
                        edt_plandetail_time.getText()+"",
                        plan_sn,
                        edt_plandetail_content.getText()+"",
                        edt_plandetail_content_detail.getText()+""
                );
                if(newplanInfoDTO.getPlandetail_time().equals("") ){
                    Toast.makeText(PlanUpdatePlanDetailActivity.this, "방문 시간을 입력해주세요", Toast.LENGTH_SHORT).show();
                }else if(newplanInfoDTO.getPlandetail_content().equals("")){
                    Toast.makeText(PlanUpdatePlanDetailActivity.this, "방문지를 입력해주세요", Toast.LENGTH_SHORT).show();
                }else if(newplanInfoDTO.getPlandetail_content_detail().equals("")){
                    newplanInfoDTO.setPlandetail_content_detail("  ");
                    insertPlanDetail(newplanInfoDTO);
                    Intent intent = getIntent;
                    finish();
                    startActivity(intent);
                }else{
                    insertPlanDetail(newplanInfoDTO);
                    Intent intent = getIntent;
                    finish();
                    startActivity(intent);
                }




            }
        });







    }//onCreate()

    private void updatePlanInfo(PlanInfoDTO planInfoDTO) {

        commonAsk = new CommonAsk("android/party/planinfoupdate");
        commonAsk.params.add(new CommonAskParam("planInfoDTO",gson.toJson(planInfoDTO)));
        InputStream in = CommonMethod.excuteAsk(commonAsk);

    }//updateDetail()


    //해당하는 플랜 디테일 보여주기
    public void showPlanInfoDetail(PlanInfoDTO planInfoDTO){
        commonAsk = new CommonAsk("android/party/planinfodetail");
        commonAsk.params.add(new CommonAskParam("planInfoDTO",gson.toJson(planInfoDTO)));
        InputStream in = CommonMethod.excuteAsk(commonAsk);

        try {
            list = gson.fromJson(new InputStreamReader(in), new TypeToken<List<PlanInfoDTO>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//partyJoin()

    //해당하는 플랜디테일의 플랜 정보 가져오기
    public void selectPlan(int plan_sn){
        commonAsk = new CommonAsk("android/party/selectplan");
        commonAsk.params.add(new CommonAskParam("plan_sn",plan_sn+""));
        InputStream in = CommonMethod.excuteAsk(commonAsk);

        try {
            planlistDTO = gson.fromJson(new InputStreamReader(in), new TypeToken<List<PlanlistDTO>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//partyJoin()


    //플랜 디테일 추가
    public void insertPlanDetail(PlanInfoDTO newplanInfoDTO) {
        commonAsk = new CommonAsk("android/party/insertplandetail");
        commonAsk.params.add(new CommonAskParam("newplanInfoDTO",gson.toJson(newplanInfoDTO)));
        InputStream in = CommonMethod.excuteAsk(commonAsk);

//        try {
//            list = gson.fromJson(new InputStreamReader(in), new TypeToken<List<PlanInfoDTO>>() {
//            }.getType());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }



    }//insertPlanDetail()

    public void test(){


        // 체크박스 다나오게하는거
        for(int i  = 0 ; i< plan_detail_list.getChildCount(); i++){
            PlanUpdatePlanDetailAdapter.Viewholder viewholder = (PlanUpdatePlanDetailAdapter.Viewholder) plan_detail_list.findViewHolderForAdapterPosition(i);
            viewholder.chk_planudelete.setVisibility(View.VISIBLE);

        }


        btn_plandetailupdate_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(delete_list.size() == 0 ){
                    return;
                }
                deletePlanDetailList(delete_list);

                //새로고침 영역
                finish();//인텐트 종료
                overridePendingTransition(0, 0);//인텐트 효과 없애기
                Intent intent = getIntent(); //인텐트
                startActivity(intent); //액티비티 열기
                overridePendingTransition(0, 0);//인텐트 효과 없애기

            }
        });



    }

    // 체크박스 체크된 디테일리스트 삭제
    private void deletePlanDetailList(ArrayList<PlanInfoDTO> list) {
        commonAsk = new CommonAsk("android/party/deleteplandetaillist");// list = []  vo ={ }
        String data = gson.toJson(list);
        commonAsk.params.add(new CommonAskParam("list" , data));

        InputStream in = CommonMethod.excuteAsk(commonAsk);


    }

}