package com.example.totproject.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.totproject.R;
import com.example.totproject.common.CommonAsk;
import com.example.totproject.common.CommonAskParam;
import com.example.totproject.common.CommonMethod;
import com.example.totproject.common.kwkCommonAsk;
import com.example.totproject.common.statics.Logined;
import com.example.totproject.main.MainActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

public class TendencyActivity02 extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{
    RadioGroup mbti_buddy, mbti_family, mbti_price, mbti_sd, mbti_io;
    RadioButton mbti_buddy_rad1, mbti_buddy_rad2, mbti_buddy_rad3, mbti_buddy_rad4, mbti_buddy_rad5
                , mbti_family_rad1, mbti_family_rad2, mbti_family_rad3, mbti_family_rad4, mbti_family_rad5
                , mbti_price_rad1, mbti_price_rad2, mbti_price_rad3, mbti_price_rad4, mbti_price_rad5
                , mbti_sd_rad1, mbti_sd_rad2, mbti_sd_rad3, mbti_sd_rad4, mbti_sd_rad5
                , mbti_io_rad1, mbti_io_rad2, mbti_io_rad3, mbti_io_rad4, mbti_io_rad5;
    Button tend2_btn_skip, tend2_btn_join;
    TendDTO dto = new TendDTO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tendency_activity_02);

        mbti_buddy = findViewById(R.id.mbti_buddy);
        mbti_family = findViewById(R.id.mbti_family);
        mbti_price = findViewById(R.id.mbti_price);
        mbti_sd = findViewById(R.id.mbti_sd);
        mbti_io = findViewById(R.id.mbti_io);

        mbti_buddy_rad1 = findViewById(R.id.mbti_buddy_rad1);
        mbti_buddy_rad2 = findViewById(R.id.mbti_buddy_rad2);
        mbti_buddy_rad3 = findViewById(R.id.mbti_buddy_rad3);
        mbti_buddy_rad4 = findViewById(R.id.mbti_buddy_rad4);
        mbti_buddy_rad5 = findViewById(R.id.mbti_buddy_rad5);
        mbti_buddy_rad1.setTag(1);
        mbti_buddy_rad2.setTag(2);
        mbti_buddy_rad3.setTag(3);
        mbti_buddy_rad4.setTag(4);
        mbti_buddy_rad5.setTag(5);

        mbti_family_rad1 = findViewById(R.id.mbti_family_rad1);
        mbti_family_rad2 = findViewById(R.id.mbti_family_rad2);
        mbti_family_rad3 = findViewById(R.id.mbti_family_rad3);
        mbti_family_rad4 = findViewById(R.id.mbti_family_rad4);
        mbti_family_rad5 = findViewById(R.id.mbti_family_rad5);
        mbti_family_rad1.setTag(1);
        mbti_family_rad2.setTag(2);
        mbti_family_rad3.setTag(3);
        mbti_family_rad4.setTag(4);
        mbti_family_rad5.setTag(5);

        mbti_price_rad1 = findViewById(R.id.mbti_price_rad1);
        mbti_price_rad2 = findViewById(R.id.mbti_price_rad2);
        mbti_price_rad3 = findViewById(R.id.mbti_price_rad3);
        mbti_price_rad4 = findViewById(R.id.mbti_price_rad4);
        mbti_price_rad5 = findViewById(R.id.mbti_price_rad5);
        mbti_price_rad1.setTag(1);
        mbti_price_rad2.setTag(2);
        mbti_price_rad3.setTag(3);
        mbti_price_rad4.setTag(4);
        mbti_price_rad5.setTag(5);

        mbti_sd_rad1 = findViewById(R.id.mbti_sd_rad1);
        mbti_sd_rad2 = findViewById(R.id.mbti_sd_rad2);
        mbti_sd_rad3 = findViewById(R.id.mbti_sd_rad3);
        mbti_sd_rad4 = findViewById(R.id.mbti_sd_rad4);
        mbti_sd_rad5 = findViewById(R.id.mbti_sd_rad5);
        mbti_sd_rad1.setTag(1);
        mbti_sd_rad2.setTag(2);
        mbti_sd_rad3.setTag(3);
        mbti_sd_rad4.setTag(4);
        mbti_sd_rad5.setTag(5);

        mbti_io_rad1 = findViewById(R.id.mbti_io_rad1);
        mbti_io_rad2 = findViewById(R.id.mbti_io_rad2);
        mbti_io_rad3 = findViewById(R.id.mbti_io_rad3);
        mbti_io_rad4 = findViewById(R.id.mbti_io_rad4);
        mbti_io_rad5 = findViewById(R.id.mbti_io_rad5);
        mbti_io_rad1.setTag(1);
        mbti_io_rad2.setTag(2);
        mbti_io_rad3.setTag(3);
        mbti_io_rad4.setTag(4);
        mbti_io_rad5.setTag(5);


        //tend2_btn_skip = findViewById(R.id.tend2_btn_skip);
        tend2_btn_join = findViewById(R.id.tend2_btn_join);

        Intent intent = getIntent();
        dto = (TendDTO) intent.getSerializableExtra("TendDTO");
        mbti_buddy.setOnCheckedChangeListener(this);
        mbti_family.setOnCheckedChangeListener(this);
        mbti_price.setOnCheckedChangeListener(this);
        mbti_sd.setOnCheckedChangeListener(this);
        mbti_io.setOnCheckedChangeListener(this);

        /*tend2_btn_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TendencyActivity02.this, MainActivity.class);
                startActivity(intent);
            }
        });*/


        // 저장완료 후 메인이동 ( 조인인걸 메인으로 이동으로 수정함)                //저장버튼누를시 디비에 잘들어가는지 확인
        tend2_btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dto.setMember_id(Logined.member_id);
                int check = tendConnect(dto);
                if(check>=1){
                    Toast.makeText(TendencyActivity02.this, "성향 입력(수정) 성공", Toast.LENGTH_SHORT).show();
                    Intent intent_to_main = new Intent(TendencyActivity02.this,MainActivity.class);
                    startActivity(intent_to_main);
                }else{
                    Toast.makeText(TendencyActivity02.this, "성향 입력(수정) 실패", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }// oncreate()

    /*Intent intent = getIntent();
    TendDTO dto = (TendDTO) intent.getSerializableExtra("dto");*/

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        int id = radioGroup.getCheckedRadioButtonId();
        RadioButton rdo_btn =findViewById(radioGroup.getCheckedRadioButtonId()) ;



        if (radioGroup.getId() == R.id.mbti_buddy) {
            dto.setMbti_buddys((Integer) rdo_btn.getTag());
            Toast.makeText(TendencyActivity02.this, rdo_btn.getTag() + "", Toast.LENGTH_SHORT).show();
        } else if (radioGroup.getId() == R.id.mbti_family) {
            dto.setMbti_family((Integer) rdo_btn.getTag());
            Toast.makeText(TendencyActivity02.this, rdo_btn.getTag() + "", Toast.LENGTH_SHORT).show();
        } else if (radioGroup.getId() == R.id.mbti_price) {
            dto.setMbti_price((Integer) rdo_btn.getTag());
            Toast.makeText(TendencyActivity02.this, rdo_btn.getTag() + "", Toast.LENGTH_SHORT).show();
        } else if (radioGroup.getId() == R.id.mbti_sd) {
            dto.setMbti_sd((Integer) rdo_btn.getTag());
            Toast.makeText(TendencyActivity02.this, rdo_btn.getTag() + "", Toast.LENGTH_SHORT).show();
        } else if (radioGroup.getId() == R.id.mbti_io) {
            dto.setMbti_io((Integer) rdo_btn.getTag());
            Toast.makeText(TendencyActivity02.this, rdo_btn.getTag() + "", Toast.LENGTH_SHORT).show();
        }

    }


    CommonMethod commonMethod = new CommonMethod();
    kwkCommonAsk commonAsk;
    Gson gson = new Gson();
    public int tendConnect(TendDTO dto){
        int succ = 0;
        commonAsk = new kwkCommonAsk("tend_insert");
        String data = gson.toJson(dto);
        commonAsk.params.add(new CommonAskParam("vo",data));
        InputStream in = commonMethod.excuteAsk(commonAsk);
        try {

         succ   = gson.fromJson(new InputStreamReader(in), Integer.class);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return succ;
    }
}