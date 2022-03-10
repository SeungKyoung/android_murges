package com.example.totproject.party_plan;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.totproject.R;
import com.example.totproject.common.CommonAsk;
import com.example.totproject.common.CommonAskParam;
import com.example.totproject.common.CommonMethod;
import com.example.totproject.common.DatePickerActivity;
import com.example.totproject.common.statics.Logined;
import com.example.totproject.party.PartyListDTO;
import com.example.totproject.party.PartyMemberListDTO;
import com.example.totproject.party.PartyMemberManageActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PlanUpdatePlanActivity extends AppCompatActivity {
    RecyclerView rec_memberlist;
    ArrayList<PartyMemberListDTO> plan_member_list = new ArrayList<>();
    ArrayList<PartyMemberListDTO> insert_member_list = new ArrayList<>();
    ArrayList<PartyListDTO> list = new ArrayList<>();
    PartyListDTO plDTO = new PartyListDTO();
    PlanlistDTO planlistDTO ;



    final int DIALOG_REQ = 1000;
    final int DIALOG_REQ2 = 1001;
    EditText edt_invite_member, edt_plan_name, edt_plan_location, edt_plan_startpoint, edt_plan_hotel, edt_plan_cost,edt_plan_starttime, edt_plan_endtime;
    TextView tv_plan_startdate, tv_plan_enddate;
    Button btn_plan_update, btn_plan_invite;

    String leader_pic = null;

    String start_date;
    String end_date;

    int diffDayss = -1;
    int checkdetail = -1; // 기존 플랜디테일데이즈가 있는지 여부    1이면 있었음 0이면 없음(데이즈 세팅예정)


    int party_sn, plan_sn;


    CommonAsk commonAsk;
    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plan_act_updateplan);

        rec_memberlist = findViewById(R.id.rec_memberlist);

        edt_plan_name = findViewById(R.id.edt_plan_name);
        tv_plan_startdate = findViewById(R.id.tv_createplan_startdate);
        tv_plan_enddate = findViewById(R.id.tv_plan_enddate);
        edt_plan_location = findViewById(R.id.edt_plan_location);
        edt_plan_startpoint = findViewById(R.id.edt_plan_startpoint);
        edt_plan_hotel = findViewById(R.id.edt_plan_hotel);
        edt_plan_cost = findViewById(R.id.edt_plan_cost);
        btn_plan_update = findViewById(R.id.btn_plan_update);
        edt_plan_starttime =findViewById(R.id.edt_plan_starttime);
        edt_plan_endtime =findViewById(R.id.edt_plan_endtime);

        edt_invite_member =findViewById(R.id.edt_invite_member);
        btn_plan_invite =findViewById(R.id.btn_plan_invite);

        Intent get_intent = getIntent();
        planlistDTO = (PlanlistDTO) get_intent.getSerializableExtra("planlistDTO");

        edt_plan_name.setText(planlistDTO.getPlan_name());
        tv_plan_startdate.setText(planlistDTO.getPlan_startdate());
        edt_plan_starttime.setText(planlistDTO.getPlan_starttime());
        tv_plan_enddate.setText(planlistDTO.getPlan_enddate());
        edt_plan_endtime.setText(planlistDTO.getPlan_endtime());
        edt_plan_location.setText(planlistDTO.getPlan_location());
        edt_plan_startpoint.setText(planlistDTO.getPlan_startpoint());
        edt_plan_hotel.setText(planlistDTO.getPlan_hotel());
        edt_plan_cost.setText(planlistDTO.getPlan_cost());

        // 디테일 기존 Days가 추가되어 있다면 1, 없다면 0
        if (planlistDTO.getPlan_startdate() == null || planlistDTO.getPlan_enddate() == null){
            checkdetail = 0;
        }else{
            checkdetail = 1;
        }

        party_sn = planlistDTO.getParty_sn();
        plan_sn = planlistDTO.getPlan_sn();
        
        // 파티 조회해오기
        partyDetail(party_sn);
        plDTO = list.get(0);


        // 파티 멤버리스트 조회해오기
        planJoinMemberList(plan_sn);

        // 어댑터 세팅영역
        PlanMemberListAdpater adapter = new PlanMemberListAdpater(PlanUpdatePlanActivity.this,plan_member_list,plDTO,2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                PlanUpdatePlanActivity.this , RecyclerView.VERTICAL , false
        );
        rec_memberlist.setLayoutManager(layoutManager);
        rec_memberlist.setAdapter(adapter);


        // datepicker 영역
        // 출발날짜 데이트피커로 세팅
        tv_plan_startdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkdetail == 1){
                    showCustomDialog(DIALOG_REQ);
                }else{
                    Intent intent = new Intent(PlanUpdatePlanActivity.this, DatePickerActivity.class);
                    startActivityForResult(intent ,DIALOG_REQ );
                }





            }
        });

        // 도착날짜 데이트피커로 세팅
        tv_plan_enddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkdetail == 1){
                    showCustomDialog(DIALOG_REQ2);
                }else{
                    Intent intent = new Intent(PlanUpdatePlanActivity.this, DatePickerActivity.class);
                    startActivityForResult(intent ,DIALOG_REQ2 );
                }
            }
        });


        // 멤버 초대 버튼클릭시 해당파티 멤버에서 조회해서 멤버리스트에 추가
        btn_plan_invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String invite_id = edt_invite_member.getText()+"";
                if(addInviteMember(invite_id) == 1){
                    Toast.makeText(PlanUpdatePlanActivity.this, "멤버를 추가하였습니다.", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(PlanUpdatePlanActivity.this, "파티내 해당하는 멤버가 없습니다.", Toast.LENGTH_SHORT).show();
                }

                // 어댑터 새로고침
                PlanMemberListAdpater adapter = new PlanMemberListAdpater(PlanUpdatePlanActivity.this,plan_member_list,plDTO,2);
                LinearLayoutManager layoutManager = new LinearLayoutManager(
                        PlanUpdatePlanActivity.this , RecyclerView.VERTICAL , false
                );
                rec_memberlist.setLayoutManager(layoutManager);
                rec_memberlist.setAdapter(adapter);
                
            }
        });







        // 최종 수정사항 저장
        btn_plan_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start_date = tv_plan_startdate.getText()+"";
                end_date = tv_plan_enddate.getText()+"";


                PlanlistDTO planDTO = new PlanlistDTO(
                        planlistDTO.getPlan_sn(),
                        leader_pic,
                        party_sn,
                        edt_plan_name.getText()+"",
                        Logined.member_id,
                        start_date,
                        end_date,
                        edt_plan_location.getText()+"",
                        edt_plan_startpoint.getText()+"",
                        edt_plan_hotel.getText()+"",
                        edt_plan_cost.getText()+"",
                        Logined.member_id,
                        edt_plan_starttime.getText()+"",
                        edt_plan_endtime.getText()+""
                );
                
                // 추가된 멤버 플랜에 추가
                if (insert_member_list != null){
                    insertPlanMember();
                }


                // 플랜 수정 저장 (업데이트, 추가된 멤버도 포함)
                updatePlan(planDTO);


                // 출발일 도착일 날짜 차이구하기 (플랜 디테일 Days 추가위함)
                if(start_date != null && end_date != null){
                    Date start_date2 = null;
                    Date end_date2= null;
                    try {
                        start_date2 = new SimpleDateFormat("yyyy-MM-dd").parse(start_date);
                        end_date2 = new SimpleDateFormat("yyyy-MM-dd").parse(end_date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Calendar start_date3 = Calendar.getInstance();
                    Calendar end_date3 = Calendar.getInstance();
                    start_date3.setTime(start_date2); //특정 일자
                    end_date3.setTime(end_date2); //특정 일자

                    long diffSec = (end_date3.getTimeInMillis() - start_date3.getTimeInMillis()) / 1000;
                    long diffDays = diffSec / (24*60*60); //일자수 차이


                    diffDayss = (int) (diffDays+1); // +1 시켜줘서 Days를 세팅

                }

                if (checkdetail == 0){  //기존 플랜디테일이 없었다면 플랜디테일데이즈 세팅
                    insertPlanDays2(diffDayss,0);
                }else if (checkdetail ==2 ){    // 기존 플랜 디테일이 있었는데 날짜를 수정했다면 삭제하고 다시 인설트
                    insertPlanDays2(diffDayss, 1); //추가
                }

                Intent intent = new Intent(PlanUpdatePlanActivity.this,PlanMainActivity.class);
                intent.putExtra("plDTO",plDTO);
                intent.putExtra("tabcode",0);
                startActivity(intent);
                finish();


            }
        });





    }//onCreate()


    // 플랜에 멤버 추가 메소드
    private void insertPlanMember() {
        commonAsk = new CommonAsk("android/party/insertPlanMember");
        String data = gson.toJson(insert_member_list);
        commonAsk.params.add(new CommonAskParam("insert_member_list" , data));

        String data2 = gson.toJson(planlistDTO);
        commonAsk.params.add(new CommonAskParam("planlistDTO" , data2));



        InputStream in = CommonMethod.excuteAsk(commonAsk);
    }

    // 초대 멤버이름 추가
    private int addInviteMember(String invite_id) {
        int succ = 0;
        for (int i = 0; i<list.size(); i++){
            if(invite_id.equals(list.get(i).getMember_id() ) ){
                plan_member_list.add(new PartyMemberListDTO(list.get(i).getMember_id(), null));
                insert_member_list.add(new PartyMemberListDTO(list.get(i).getMember_id(), null));
                succ = 1;
            }
        }
        return succ;
    }


    private void insertPlanDays2(int diffDayss, int delete_code) {
        commonAsk = new CommonAsk("android/party/insertPlanDays2");
        commonAsk.params.add(new CommonAskParam("plan_sn",planlistDTO.getPlan_sn()+""));
        commonAsk.params.add(new CommonAskParam("diffDayss" ,  diffDayss+"" ));
        commonAsk.params.add(new CommonAskParam("delete_code" ,  delete_code+"" ));

        InputStream in = CommonMethod.excuteAsk(commonAsk);
    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 출발 날짜 세팅
        if (requestCode == DIALOG_REQ && resultCode == RESULT_OK) {
            try {
                start_date = data.getStringExtra("date");
                tv_plan_startdate.setText(start_date);

            }catch (Exception e){
                e.printStackTrace();
            }
            //도착 날짜 세팅
        }else if(requestCode == DIALOG_REQ2 && resultCode == RESULT_OK){
            try {
                end_date = (data.getStringExtra("date"));
                tv_plan_enddate.setText(end_date);

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    // 팝업중 다른곳 터치시 안닫힘
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if( event.getAction() == MotionEvent.ACTION_OUTSIDE ) {
            return false;
        }
        return true;
    }



    //해당 파티 정보 조회
    public ArrayList<PartyListDTO> partyDetail(int party_sn){
        commonAsk = new CommonAsk("android/party/partydetail");
        commonAsk.params.add(new CommonAskParam("party_sn",party_sn+""));
        InputStream in = CommonMethod.excuteAsk(commonAsk);

        try {
            list = gson.fromJson(new InputStreamReader(in), new TypeToken<List<PartyListDTO>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;

    }//partyJoin()


    //해당 플랜의 멤버리스트 조회
    public ArrayList<PartyMemberListDTO> planJoinMemberList(int plan_sn) {
        commonAsk = new CommonAsk("android/party/planJoinMemberList");
        commonAsk.params.add(new CommonAskParam("plan_sn",plan_sn+""));

        InputStream in = CommonMethod.excuteAsk(commonAsk);

        // 리스트로 리턴 처리해줄것

        try {
            plan_member_list = gson.fromJson(new InputStreamReader(in), new TypeToken<List<PartyMemberListDTO>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return plan_member_list;
    }


    // 플랜정보 수정 저장
    public void updatePlan(PlanlistDTO dto) {

        commonAsk = new CommonAsk("android/party/updatePlan");
        commonAsk.params.add(new CommonAskParam("dto",gson.toJson(dto)));

        InputStream in = CommonMethod.excuteAsk(commonAsk);

    }


    public void showCustomDialog(int req_code) {
        AlertDialog.Builder builder = new AlertDialog.Builder(PlanUpdatePlanActivity.this,
                R.style.AlertDialogTheme);

        View view= LayoutInflater.from(PlanUpdatePlanActivity.this).inflate(
                R.layout.common_alert_yes_or_no_item,
                (LinearLayout)findViewById(R.id.layout_alert)
        );

        //다이얼로그 텍스트 설정
        builder.setView(view);
        ((TextView)view.findViewById(R.id.texttitle)).setText("※ 주의");
        ((TextView)view.findViewById(R.id.textmessage)).setText("이미 설정된 날짜가 있습니다. \n날짜 수정시 세부일정이 초기화 됩니다.\n계속 하시겠습니까? ");
        ((TextView)view.findViewById(R.id.btn_dialog_yes)).setText("네");
        ((TextView)view.findViewById(R.id.btn_dialog_no)).setText("아니요");

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);   //화면밖 터치시 다이얼로그 종료방지

        //네 클릭시
        view.findViewById(R.id.btn_dialog_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();  //알럿창 닫기
                if(checkdetail == 1){   //기존 플랜디테일 데이즈가 있었는데 날짜를 수정했다면
                    checkdetail =2;
                }
                Intent intent = new Intent(PlanUpdatePlanActivity.this, DatePickerActivity.class);
                startActivityForResult(intent ,req_code );


            }
            // ~ 해당멤버 삭제영역

        });

        //아니요 클릭시
        view.findViewById(R.id.btn_dialog_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();  //알럿창 닫기
            }
        });

        //다이얼로그 형태 지우기
        if(alertDialog.getWindow() != null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        alertDialog.show();



    }//showCustomDialog()



}//PlanUpdatePlanActivity()