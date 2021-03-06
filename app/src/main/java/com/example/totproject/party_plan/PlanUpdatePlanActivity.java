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
    int checkdetail = -1; // ?????? ??????????????????????????? ????????? ??????    1?????? ????????? 0?????? ??????(????????? ????????????)


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

        // ????????? ?????? Days??? ???????????? ????????? 1, ????????? 0
        if (planlistDTO.getPlan_startdate() == null || planlistDTO.getPlan_enddate() == null){
            checkdetail = 0;
        }else{
            checkdetail = 1;
        }

        party_sn = planlistDTO.getParty_sn();
        plan_sn = planlistDTO.getPlan_sn();
        
        // ?????? ???????????????
        partyDetail(party_sn);
        plDTO = list.get(0);


        // ?????? ??????????????? ???????????????
        planJoinMemberList(plan_sn);

        // ????????? ????????????
        PlanMemberListAdpater adapter = new PlanMemberListAdpater(PlanUpdatePlanActivity.this,plan_member_list,plDTO,2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                PlanUpdatePlanActivity.this , RecyclerView.VERTICAL , false
        );
        rec_memberlist.setLayoutManager(layoutManager);
        rec_memberlist.setAdapter(adapter);


        // datepicker ??????
        // ???????????? ?????????????????? ??????
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

        // ???????????? ?????????????????? ??????
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


        // ?????? ?????? ??????????????? ???????????? ???????????? ???????????? ?????????????????? ??????
        btn_plan_invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String invite_id = edt_invite_member.getText()+"";
                if(addInviteMember(invite_id) == 1){
                    Toast.makeText(PlanUpdatePlanActivity.this, "????????? ?????????????????????.", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(PlanUpdatePlanActivity.this, "????????? ???????????? ????????? ????????????.", Toast.LENGTH_SHORT).show();
                }

                // ????????? ????????????
                PlanMemberListAdpater adapter = new PlanMemberListAdpater(PlanUpdatePlanActivity.this,plan_member_list,plDTO,2);
                LinearLayoutManager layoutManager = new LinearLayoutManager(
                        PlanUpdatePlanActivity.this , RecyclerView.VERTICAL , false
                );
                rec_memberlist.setLayoutManager(layoutManager);
                rec_memberlist.setAdapter(adapter);
                
            }
        });







        // ?????? ???????????? ??????
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
                
                // ????????? ?????? ????????? ??????
                if (insert_member_list != null){
                    insertPlanMember();
                }


                // ?????? ?????? ?????? (????????????, ????????? ????????? ??????)
                updatePlan(planDTO);


                // ????????? ????????? ?????? ??????????????? (?????? ????????? Days ????????????)
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
                    start_date3.setTime(start_date2); //?????? ??????
                    end_date3.setTime(end_date2); //?????? ??????

                    long diffSec = (end_date3.getTimeInMillis() - start_date3.getTimeInMillis()) / 1000;
                    long diffDays = diffSec / (24*60*60); //????????? ??????


                    diffDayss = (int) (diffDays+1); // +1 ???????????? Days??? ??????

                }

                if (checkdetail == 0){  //?????? ?????????????????? ???????????? ???????????????????????? ??????
                    insertPlanDays2(diffDayss,0);
                }else if (checkdetail ==2 ){    // ?????? ?????? ???????????? ???????????? ????????? ??????????????? ???????????? ?????? ?????????
                    insertPlanDays2(diffDayss, 1); //??????
                }

                Intent intent = new Intent(PlanUpdatePlanActivity.this,PlanMainActivity.class);
                intent.putExtra("plDTO",plDTO);
                intent.putExtra("tabcode",0);
                startActivity(intent);
                finish();


            }
        });





    }//onCreate()


    // ????????? ?????? ?????? ?????????
    private void insertPlanMember() {
        commonAsk = new CommonAsk("android/party/insertPlanMember");
        String data = gson.toJson(insert_member_list);
        commonAsk.params.add(new CommonAskParam("insert_member_list" , data));

        String data2 = gson.toJson(planlistDTO);
        commonAsk.params.add(new CommonAskParam("planlistDTO" , data2));



        InputStream in = CommonMethod.excuteAsk(commonAsk);
    }

    // ?????? ???????????? ??????
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
        // ?????? ?????? ??????
        if (requestCode == DIALOG_REQ && resultCode == RESULT_OK) {
            try {
                start_date = data.getStringExtra("date");
                tv_plan_startdate.setText(start_date);

            }catch (Exception e){
                e.printStackTrace();
            }
            //?????? ?????? ??????
        }else if(requestCode == DIALOG_REQ2 && resultCode == RESULT_OK){
            try {
                end_date = (data.getStringExtra("date"));
                tv_plan_enddate.setText(end_date);

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    // ????????? ????????? ????????? ?????????
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if( event.getAction() == MotionEvent.ACTION_OUTSIDE ) {
            return false;
        }
        return true;
    }



    //?????? ?????? ?????? ??????
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


    //?????? ????????? ??????????????? ??????
    public ArrayList<PartyMemberListDTO> planJoinMemberList(int plan_sn) {
        commonAsk = new CommonAsk("android/party/planJoinMemberList");
        commonAsk.params.add(new CommonAskParam("plan_sn",plan_sn+""));

        InputStream in = CommonMethod.excuteAsk(commonAsk);

        // ???????????? ?????? ???????????????

        try {
            plan_member_list = gson.fromJson(new InputStreamReader(in), new TypeToken<List<PartyMemberListDTO>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return plan_member_list;
    }


    // ???????????? ?????? ??????
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

        //??????????????? ????????? ??????
        builder.setView(view);
        ((TextView)view.findViewById(R.id.texttitle)).setText("??? ??????");
        ((TextView)view.findViewById(R.id.textmessage)).setText("?????? ????????? ????????? ????????????. \n?????? ????????? ??????????????? ????????? ?????????.\n?????? ??????????????????? ");
        ((TextView)view.findViewById(R.id.btn_dialog_yes)).setText("???");
        ((TextView)view.findViewById(R.id.btn_dialog_no)).setText("?????????");

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);   //????????? ????????? ??????????????? ????????????

        //??? ?????????
        view.findViewById(R.id.btn_dialog_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();  //????????? ??????
                if(checkdetail == 1){   //?????? ??????????????? ???????????? ???????????? ????????? ???????????????
                    checkdetail =2;
                }
                Intent intent = new Intent(PlanUpdatePlanActivity.this, DatePickerActivity.class);
                startActivityForResult(intent ,req_code );


            }
            // ~ ???????????? ????????????

        });

        //????????? ?????????
        view.findViewById(R.id.btn_dialog_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();  //????????? ??????
            }
        });

        //??????????????? ?????? ?????????
        if(alertDialog.getWindow() != null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        alertDialog.show();



    }//showCustomDialog()



}//PlanUpdatePlanActivity()