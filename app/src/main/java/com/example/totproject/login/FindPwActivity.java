package com.example.totproject.login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.totproject.R;
import com.example.totproject.common.CommonAsk;
import com.example.totproject.common.CommonAskParam;
import com.example.totproject.common.CommonMethod;
import com.example.totproject.common.VO.MemberDTO;
import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

public class FindPwActivity extends AppCompatActivity {
    EditText edit_find_id, edit_find_tel;
    Button btn_find_pw, btn_cancel;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pw);

        edit_find_id = findViewById(R.id.edit_find_id);
        edit_find_tel = findViewById(R.id.edit_find_tel);
        btn_find_pw = findViewById(R.id.btn_find_pw);
        btn_cancel = findViewById(R.id.btn_cancel);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FindPwActivity.this, LoginActivity.class));
            }
        });

        btn_find_pw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MemberDTO dto = new MemberDTO();
                dto.setMember_id(edit_find_id.getText() + "");
                dto.setMember_tel(edit_find_tel.getText() + "");
                if (edit_find_id.getText().length() == 0 || edit_find_tel.getText().length() == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(FindPwActivity.this);
                    dialog = builder.setMessage("가입하신 아이디와 핸드폰 번호를 입력하세요").setPositiveButton("확인", null).create();
                    dialog.show();
                    edit_find_id.requestFocus();
                    return;
                }else {
                    dto = doFindPw(dto);
                    String member_pw = dto.getMember_pw();
                    AlertDialog.Builder builder = new AlertDialog.Builder(FindPwActivity.this);
                    dialog = builder.setMessage("고객님의 비밀번호 : " + member_pw).setPositiveButton("확인", null).create();
                    dialog.show();
                }

            }
        });

    }

    CommonAsk commonAsk;
    CommonMethod commonMethod = new CommonMethod();
    Gson gson = new Gson();
    MemberDTO vo = new MemberDTO();

    public MemberDTO doFindPw(MemberDTO dto) {
        commonAsk = new CommonAsk("find_pw");
        commonAsk.params.add(new CommonAskParam("dto", gson.toJson(dto)));
        try {
            InputStream in = commonMethod.excuteAsk(commonAsk);
            vo = gson.fromJson(new InputStreamReader(in), (Type) MemberDTO.class);

        }catch (Exception e) {
            e.printStackTrace();
        }
        return vo;
    }

}