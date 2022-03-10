package com.example.totproject.party;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.totproject.R;
import com.example.totproject.common.CommonAsk;
import com.example.totproject.common.CommonAskParam;
import com.example.totproject.common.CommonMethod;

import com.google.gson.Gson;

import java.io.InputStream;

public class InviteMemberActivity extends AppCompatActivity {

    EditText edt_member_id;
    CommonAsk commonAsk;
    Gson gson = new Gson();
    Button btn_party_memberinvite;
    LinearLayout lin_invite_back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.party_act_memberinvite);
        edt_member_id = findViewById(R.id.edt_member_id);
        btn_party_memberinvite = findViewById(R.id.btn_party_memberinvite);
        lin_invite_back_btn = findViewById(R.id.lin_invite_back_btn);


        
        Intent getIntent = getIntent();
        PartyListDTO plDTO = (PartyListDTO) getIntent.getSerializableExtra("plDTO");
        String temp = plDTO.getMember_id();


        // 뒤로가기 버튼 클릭시      @@@ 이거왜 작동안함?????
        lin_invite_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InviteMemberActivity.this,MyPartyInfoActivity.class);
                intent.putExtra("party_dto",plDTO);
                startActivity(intent);
                finish();
            }
        });


        // 멤버 초대 버튼 클릭시
        btn_party_memberinvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plDTO.setMember_id(edt_member_id.getText() + "");
                invitePartyMember(plDTO);
                plDTO.setMember_id(temp);
                
                //성공시 초대로만 처리
                Intent intent = new Intent(InviteMemberActivity.this,MyPartyInfoActivity.class);
                intent.putExtra("party_dto",plDTO);
                startActivity(intent);
                finish();

            }
        });





    }//onCreate()

    public void invitePartyMember(PartyListDTO plDTO) {
        commonAsk = new CommonAsk("android/party/invitepartymember");
        commonAsk.params.add(new CommonAskParam("plDTO", gson.toJson(plDTO)));
        CommonMethod.excuteAsk(commonAsk);

    }
}