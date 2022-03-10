package com.example.totproject.party;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.totproject.R;
import com.example.totproject.common.CommonAsk;
import com.example.totproject.common.CommonAskParam;
import com.example.totproject.common.CommonMethod;
import com.example.totproject.common.statics.Logined;
import com.example.totproject.login.JoinActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;


public class PartyCreateActivity extends Activity {
    EditText edt_party_name;
    EditText edt_party_detail;
    CheckBox checkbox_party_private;
    LinearLayout lin_display_tags, lin_party_back, lin_temp_checkbox;
    EditText edt_party_tag;
    TextView tv_party_tag1,tv_party_tag2,tv_party_tag3, tv_party_check_partyname;
    Button btn_create_party;
    Button btn_party_tag;
    Button btn_checkid, btn_create_party_add;
    ImageView imgv_party_pic ;


    int can_pass = 2;   //2일시 중복체크상태, 1일시 이름 중복된 상태, 0일시 이름 (중복아님 파티가입가능)

    String party_pic = "0001";
    String party_name, party_detail, party_private, party_tag1, party_tag2, party_tag3;
    ArrayList tags = new ArrayList();

    CommonAsk commonAsk;
    Gson gson = new Gson();
    ArrayList<PartyListDTO> list = new ArrayList<>();

    public int reqGcode = 1004;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.party_act_createparty);

        btn_checkid = findViewById(R.id.btn_checkid);
        edt_party_name = findViewById(R.id.edt_party_name);
        edt_party_detail = findViewById(R.id.edt_party_detail);
        lin_display_tags = findViewById(R.id.lin_display_tags);
        checkbox_party_private = findViewById(R.id.checkbox_party_private);
        edt_party_tag = findViewById(R.id.edt_party_tag);
        btn_create_party = findViewById(R.id.btn_create_party);
        btn_party_tag = findViewById(R.id.btn_party_tag);
        tv_party_tag1 = findViewById(R.id.tv_party_tag1);
        tv_party_tag2 = findViewById(R.id.tv_party_tag2);
        tv_party_tag3 = findViewById(R.id.tv_party_tag3);
        lin_party_back = findViewById(R.id.lin_party_back);
        btn_create_party_add = findViewById(R.id.btn_create_party_add);
        imgv_party_pic = findViewById(R.id.imgv_party_pic);
        tv_party_check_partyname = findViewById(R.id.tv_party_check_partyname);
        lin_temp_checkbox = findViewById(R.id.lin_temp_checkbox);




        
        //뒤로가기 버튼
        lin_party_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PartyCreateActivity.this, PartyMainActivity.class);
                intent.putExtra("tabcode",1);
                startActivity(intent);
                finish();
            }
        });

        //중복체크 버튼
        btn_checkid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String check = checkPartyName(edt_party_name.getText()+"");

                Toast.makeText(PartyCreateActivity.this, check, Toast.LENGTH_SHORT).show();

                if(check.equals("0")){
                    tv_party_check_partyname.setVisibility(View.VISIBLE);
                    tv_party_check_partyname.setText(" 사용 가능한 파티이름 입니다. ");
                    tv_party_check_partyname.setTextColor(Color.parseColor("#00FF00"));
                    can_pass = 0;
                }else{
                    tv_party_check_partyname.setVisibility(View.VISIBLE);
                    tv_party_check_partyname.setText(" 중복된 파티이름 입니다. 다시 입력해주세요.");
                    tv_party_check_partyname.setTextColor(Color.parseColor("#FF0000"));
                    can_pass = 1;
                }

            }
        });

        //파티 공개여부 체크박스
        checkbox_party_private.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkbox_party_private.isChecked()){
                    lin_display_tags.setVisibility(View.VISIBLE);
                    lin_temp_checkbox.setVisibility(View.GONE);
                }else{
                    lin_display_tags.setVisibility(View.GONE);
                    lin_temp_checkbox.setVisibility(View.VISIBLE);
                }

            }
        });



        // 태그추가 버튼
        btn_party_tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertTags();

            }
        });

        // 태그1 삭제버튼
        tv_party_tag1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv_party_tag1.getText()+"" != null){
                    deleteTags(0);
                }
            }
        });

        // 태그2 삭제버튼
        tv_party_tag2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv_party_tag2.getText()+"" != null){
                    deleteTags(1);
                }
            }
        });

        // 태그3 삭제버튼
        tv_party_tag3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv_party_tag3.getText()+"" != null){
                    deleteTags(2);
                }
            }
        });


        // 사진 불러오기
        btn_create_party_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //party_pic
                checkDangerousPermissions();
                Intent imageIntent = new Intent();
                imageIntent.setType("image/*");
                imageIntent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(imageIntent,"사용할 앱 선택"), reqGcode);


            }
        });







        // 파티생성버튼 클릭시
        btn_create_party.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (can_pass == 2){
                    Toast.makeText(PartyCreateActivity.this, "파티이름 중복체크를 해주세요", Toast.LENGTH_SHORT).show();
                }else if(can_pass == 1){
                    Toast.makeText(PartyCreateActivity.this, "파티이름 중복체크를 다시 해주세요", Toast.LENGTH_SHORT).show();
                }else {
                    insertCreateparty();
                }
            }
        });




    }//onCreate()



    // 사진 픽업 메소드
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1004){

            Toast.makeText(PartyCreateActivity.this, "이미지 가져오기 성공.", Toast.LENGTH_SHORT).show();
            Uri galleryUri = data.getData();
            party_pic = getPathFromURI(galleryUri);
            //img_filepath = galleryUri;
           Glide.with(PartyCreateActivity.this).load(galleryUri).into(imgv_party_pic);

        }
    }


String chaminhwan = "asdf"  ;



    // 태그 추가
    public void insertTags(){

        if (edt_party_tag.getText()+""!= null && tags.size() <3){
            String party_tag = edt_party_tag.getText()+"";
            tags.add(party_tag);
            setTags();      //태그 1~3 리스트로 세팅
            edt_party_tag.setText("");
        }else{
            Toast.makeText(PartyCreateActivity.this, "파티 태그는 최대 3개", Toast.LENGTH_SHORT).show();
            edt_party_tag.setText("");
        }


    }//insertTags()


    //TextView로 태그 세팅하기
    public void setTags(){
        if(tags.size() == 1){
            tv_party_tag1.setText("#"+tags.get(0));
            tv_party_tag2.setText("");
            tv_party_tag3.setText("");
        }else if(tags.size() == 2){
            tv_party_tag1.setText("#"+tags.get(0));
            tv_party_tag2.setText("#"+tags.get(1));
            tv_party_tag3.setText("");
        }else if(tags.size() == 3) {
            tv_party_tag1.setText("#"+tags.get(0));
            tv_party_tag2.setText("#"+tags.get(1));
            tv_party_tag3.setText("#"+tags.get(2));
        }else{
            tv_party_tag1.setText("");
            tv_party_tag2.setText("");
            tv_party_tag3.setText("");
        }


    }//setTags()


    //해당태그 클릭시 삭제
    public void deleteTags(int i){
        tags.remove(i);
        setTags();
    }

    public void insertCreateparty(){
        party_name = edt_party_name.getText()+"";
        party_detail = edt_party_detail.getText()+"";

        if(checkbox_party_private.isChecked()){
            party_private = "y";
        }else{
            party_private = "n";
        }
        party_tag1 = tv_party_tag1.getText()+"";
        party_tag2 = tv_party_tag2.getText()+"";
        party_tag3 = tv_party_tag3.getText()+"";



        PartyListDTO dto = new PartyListDTO(0,party_pic,party_private, Logined.member_id,party_name,party_detail,party_tag1,party_tag2,party_tag3, Logined.member_id);
        insertParty(dto);

        Intent intent = new Intent(PartyCreateActivity.this, PartyMainActivity.class);
        intent.putExtra("tabcode",3);
        startActivity(intent);
        finish();


    }//saveCreateParty()

    public String checkPartyName(String party_name) {
        String check = "1";
        commonAsk = new CommonAsk("android/party/checkpartyname");
        commonAsk.params.add(new CommonAskParam("party_name",party_name));

        InputStream in = CommonMethod.excuteAsk(commonAsk);

        try {
            check = gson.fromJson(new InputStreamReader(in), new TypeToken<String>() {
            }.getType());            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return check;
    }




    public void insertParty(PartyListDTO dto) {
        commonAsk = new CommonAsk("android/party/insertParty");
        commonAsk.params.add(new CommonAskParam("dto",gson.toJson(dto)));
        commonAsk.fileParams.add(new CommonAskParam("party_pic",party_pic));

        InputStream in = CommonMethod.excuteAsk(commonAsk);
        Toast.makeText(PartyCreateActivity.this, "파티 생성완료 ( 임시)", Toast.LENGTH_SHORT).show();
    }


    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cusor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cusor.moveToFirst()) {
            int column_index = cusor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cusor.getString(column_index);
        }
        return res;
    }



    private void checkDangerousPermissions() {
        String[] permissions = {
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_MEDIA_LOCATION,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "권한 있음", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "권한 없음", Toast.LENGTH_LONG).show();

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                Toast.makeText(this, "권한 설명 필요함.", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this, permissions, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, permissions[i] + " 권한이 승인됨.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, permissions[i] + " 권한이 승인되지 않음.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

}
