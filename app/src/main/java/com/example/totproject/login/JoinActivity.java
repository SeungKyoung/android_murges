package com.example.totproject.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.totproject.R;
import com.example.totproject.common.CommonAskParam;
import com.example.totproject.common.CommonMethod;
import com.example.totproject.common.VO.MemberDTO;
import com.example.totproject.common.kwkCommonAsk;
import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;

public class JoinActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    EditText join_id, join_pw, join_pw_confirm, join_name, join_nick, join_email, join_tel;
    TextView join_next;
    RadioGroup join_gender;
    RadioButton join_gender_f, join_gender_m;
    Button join_id_confirm, join_nick_confirm;
    AlertDialog dialog;
    boolean validate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_act_join);

        join_id = findViewById(R.id.join_id);
        join_id_confirm = findViewById(R.id.join_id_confirm);
        join_pw = findViewById(R.id.join_pw);
        join_pw_confirm = findViewById(R.id.join_pw_confirm);
        join_tel = findViewById(R.id.join_tell);
        join_name = findViewById(R.id.join_name);
        join_nick = findViewById(R.id.join_nick);
        join_nick_confirm = findViewById(R.id.join_nick_confirm);

        join_gender = findViewById(R.id.join_gender);
        join_gender_m = findViewById(R.id.join_gender_m);
        join_gender_f = findViewById(R.id.join_gender_f);
        join_gender_f.setTag(1);
        join_gender_m.setTag(2);

        join_email = findViewById(R.id.join_email);
        join_next = findViewById(R.id.join_next);

        // 아이디 유효성
        join_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = s.toString();
                if (text.length() < 5) {
                    join_id.setTextColor(Color.RED);
                } else if (text.contains("!,@,#,%,&,*")) {
                    join_id.setTextColor(Color.RED);
                } else {
                    join_id.setTextColor(Color.GREEN);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        // 아이디 중복확인
        join_id_confirm.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                int id_chk = -1;
                String member_id = join_id.getText().toString() + "";
                id_chk = joinIdConfirm(member_id);

                if (id_chk >= 1) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                    dialog = builder.setMessage("사용할 수 없는 이이디입니다").setPositiveButton("확인", null).create();
                    dialog.show();
                } else if (id_chk == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                    dialog = builder.setMessage("사용 가능한 아이디입니다").setPositiveButton("확인", null).create();
                    dialog.show();
                }

            }
        });// join_id_confirm()


        // 닉네임 중복확인
        join_nick_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int nick_chk = -1;
                String member_nick = join_nick.getText().toString() + "";
                nick_chk = joinNickConfirm(member_nick);

                if (nick_chk >= 1) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                    dialog = builder.setMessage("사용할 수 없는 닉네임입니다").setPositiveButton("확인", null).create();
                    dialog.show();
                } else if (nick_chk == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                    dialog = builder.setMessage("사용 가능한 닉네임입니다").setPositiveButton("확인", null).create();
                    dialog.show();
                }
            }
        });


        // 비밀번호 유효성
        join_pw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = s.toString();
                if (text.length() > 4) {
                    join_pw.setTextColor(Color.GREEN);
                } else {
                    join_pw.setTextColor(Color.RED);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });


        // 비밀번호 확인
        join_pw_confirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (join_pw_confirm.getText().toString().equals(join_pw.getText().toString())) {
                    join_pw_confirm.setTextColor(Color.GREEN);
                    AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                    dialog = builder.setMessage("비밀번호가 일치합니다").setPositiveButton("확인", null).create();
                    dialog.show();
                } else {
                    join_pw_confirm.setTextColor(Color.RED);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        join_gender.setOnCheckedChangeListener(this);

        join_next.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                if (join_id.getText().length() < 1 || join_pw.getText().length() < 1
                        || join_name.getText().length() < 1 || join_nick.getText().length() < 1
                        || join_tel.getText().length() < 1 || join_pw_confirm.getText().length() < 1) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                    dialog = builder.setMessage("아이디, 비번, 이름, 닉네임,전화번호는  필수 입력값압니다").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                } else {

                    MemberDTO dto = new MemberDTO();


                    dto.setMember_id(join_id.getText() + "");
                    dto.setMember_pw(join_pw.getText() + "");
                    dto.setMember_name(join_name.getText() + "");
                    dto.setMember_nick(join_nick.getText() + "");
                    if(join_gender.getCheckedRadioButtonId() == 2131296572) {
                        dto.setMember_gender("여성");
                    }else if (join_gender.getCheckedRadioButtonId() == 2131296573) {
                        dto.setMember_gender("남성");
                    }
                    dto.setMember_tel(join_tel.getText() + "");
                    dto.setMember_email(join_email.getText() + "");

                    joinConnect(dto);


                    Intent intent = new Intent(JoinActivity.this, TendencyActivity01.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    } // onCreate()

    CommonMethod commonMethod = new CommonMethod();
    Gson gson = new Gson();
    kwkCommonAsk commonAsk;

    public void joinConnect(MemberDTO dto) {
        commonAsk = new kwkCommonAsk("join");
        String data = gson.toJson(dto);
        commonAsk.params.add(new CommonAskParam("vo", data));

        try {
            InputStream in = commonMethod.excuteAsk(commonAsk);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//joinConnect()

    private int joinIdConfirm(String member_id) {
        commonAsk = new kwkCommonAsk("id_check");
        commonAsk.params.add(new CommonAskParam("id", member_id));
        int id_chk = 0;

        try {
            InputStream in = CommonMethod.excuteAsk(commonAsk);
            id_chk = gson.fromJson(new InputStreamReader(in), Integer.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id_chk;
    }// joinIdConfirm()

    private int joinNickConfirm(String member_nick) {
        commonAsk = new kwkCommonAsk("nick_check");
        commonAsk.params.add(new CommonAskParam("nick", member_nick));
        int nick_chk = 0;

        try {
            InputStream in = CommonMethod.excuteAsk(commonAsk);
            nick_chk = gson.fromJson(new InputStreamReader(in), Integer.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nick_chk;
    }//joinNickConfirm ()

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        //int id = radioGroup.getCheckedRadioButtonId();
        RadioButton rdo_btn = findViewById(radioGroup.getCheckedRadioButtonId());
        MemberDTO dto = new MemberDTO();

        if (radioGroup.getId() == R.id.mbti_tour) {
            dto.setMember_gender((String) rdo_btn.getTag());
            Toast.makeText(JoinActivity.this, rdo_btn.getTag() + "", Toast.LENGTH_SHORT).show();
        }
    }
}