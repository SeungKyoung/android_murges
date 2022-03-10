package com.example.totproject.login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.totproject.R;
import com.example.totproject.common.CommonAsk;
import com.example.totproject.common.CommonAskParam;
import com.example.totproject.common.CommonMethod;
import com.example.totproject.common.kwkCommonAsk;
import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;

public class FindIdActivity extends AppCompatActivity {

    EditText edit_tel;
    Button btn_find_id, btn_cancel;
    AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_id);

        edit_tel = findViewById(R.id.edit_tel);
        btn_find_id = findViewById(R.id.btn_find_id);
        btn_cancel = findViewById(R.id.btn_cancel);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FindIdActivity.this, LoginActivity.class));
            }
        });

        btn_find_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String member_tel = edit_tel.getText().toString()+"";
                if (member_tel.length() == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(FindIdActivity.this);
                    dialog = builder.setMessage("가입하신 핸드폰번호를 입력하세요").setPositiveButton("확인", null).create();
                    dialog.show();
                    edit_tel.requestFocus();
                    return;
                }else {
                   String getTel = doFindId(member_tel);
                    AlertDialog.Builder builder = new AlertDialog.Builder(FindIdActivity.this);
                    dialog = builder.setMessage("고객님의 아이디 : " + getTel).setPositiveButton("확인", null).create();
                    dialog.show();
                }


            }
        });

    }// oncreate

       CommonAsk commonAsk;
       CommonMethod commonMethod = new CommonMethod();
       Gson gson = new Gson();

       public String doFindId(String member_tel) {
           commonAsk = new CommonAsk("find_id");
           commonAsk.params.add(new CommonAskParam("tel", member_tel));
           String tel = null;
           try {
               InputStream in = commonMethod.excuteAsk(commonAsk);
                tel = gson.fromJson(new InputStreamReader(in), String.class);

           }catch (Exception e) {
               e.printStackTrace();
           }
            return tel;
       }

}