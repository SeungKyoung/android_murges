package com.example.totproject.party;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.totproject.R;
import com.example.totproject.common.CommonAsk;
import com.example.totproject.common.CommonAskParam;
import com.example.totproject.common.CommonMethod;
import com.example.totproject.common.statics.Logined;
import com.google.gson.Gson;

import java.io.InputStream;
import java.util.ArrayList;

public class UpdateMyPartyActivity extends AppCompatActivity {
    TextView tv_party_name, tv_party_tag1,tv_party_tag2,tv_party_tag3;
    EditText edt_party_detail, edt_party_tag;
    CheckBox checkbox_party_private;
    Button btn_party_update, btn_party_tag, btn_create_party_add;
    LinearLayout lin_display_tags, lin_temp_checkbox;
    ImageView imgv_party_pic;

    public int reqGcode = 1004;
    int party_sn;
    String party_pic, new_pic = "n";
    String party_name, party_detail, party_private, party_tag1, party_tag2, party_tag3;

    ArrayList tags = new ArrayList();
    Gson gson = new Gson();
    ArrayList<PartyListDTO> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.party_act_update_myparty);

        tv_party_name = findViewById(R.id.tv_party_name);
        edt_party_detail = findViewById(R.id.edt_party_detail);
        checkbox_party_private = findViewById(R.id.checkbox_party_private);
        btn_party_update = findViewById(R.id.btn_party_update);

        lin_display_tags = findViewById(R.id.lin_display_tags);
        edt_party_tag = findViewById(R.id.edt_party_tag);
        btn_party_tag = findViewById(R.id.btn_party_tag);
        tv_party_tag1 = findViewById(R.id.tv_party_tag1);
        tv_party_tag2 = findViewById(R.id.tv_party_tag2);
        tv_party_tag3 = findViewById(R.id.tv_party_tag3);
        imgv_party_pic = findViewById(R.id.imgv_party_pic);
        btn_create_party_add = findViewById(R.id.btn_create_party_add);
        lin_temp_checkbox = findViewById(R.id.lin_temp_checkbox);



        Intent getIntent = getIntent();
        PartyListDTO plDTO = (PartyListDTO) getIntent.getSerializableExtra("plDTO");

        // 내파티 정보 보여주기 세팅

        tv_party_name.setText(plDTO.getParty_name());
        edt_party_detail.setText(plDTO.getParty_detail());

        if(plDTO.getParty_private().equals("y")){
            checkbox_party_private.setChecked(true);
            lin_temp_checkbox.setVisibility(View.GONE);
            lin_display_tags.setVisibility(View.VISIBLE);
            tv_party_tag1.setText(plDTO.getParty_tag1());
            tv_party_tag2.setText(plDTO.getParty_tag2());
            tv_party_tag3.setText(plDTO.getParty_tag3());
        }else{
            lin_temp_checkbox.setVisibility(View.VISIBLE);
            checkbox_party_private.setChecked(false);
            lin_display_tags.setVisibility(View.GONE);
        }

        //파티 공개여부 체크박스
        checkbox_party_private.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkbox_party_private.isChecked()){
                    lin_temp_checkbox.setVisibility(View.GONE);
                    lin_display_tags.setVisibility(View.VISIBLE);
                }else{
                    lin_display_tags.setVisibility(View.GONE);
                    lin_temp_checkbox.setVisibility(View.VISIBLE);
                }

            }
        });

        // 사진 세팅
        if (plDTO.getPicture_filepath() != null){
            Glide.with(UpdateMyPartyActivity.this).load(plDTO.getPicture_filepath()).into(imgv_party_pic);
        }

        addTags(plDTO.getParty_tag1());
        addTags(plDTO.getParty_tag2());
        addTags(plDTO.getParty_tag3());







        // 파티정보 수정영역



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


        // 사진 추가 버튼클릭시
        btn_create_party_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkDangerousPermissions();
                Intent imageIntent = new Intent();
                imageIntent.setType("image/*");
                imageIntent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(imageIntent,"사용할 앱 선택"), reqGcode);

            }
        });



        // 수정 저장 버튼 클릭시
        btn_party_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                party_sn = plDTO.getParty_sn();
                party_detail = edt_party_detail.getText() + "";

                if(checkbox_party_private.isChecked()){
                    party_private = "y";
                    party_tag1 = tv_party_tag1.getText()+"";
                    party_tag2 = tv_party_tag2.getText()+"";
                    party_tag3 = tv_party_tag3.getText()+"";
                }else{
                    party_private = "n";
                    party_tag1 = null;
                    party_tag2 = null;
                    party_tag3 = null;
                }
                
                if(party_pic != null){
                    new_pic = "y";
                }


                PartyListDTO dto = new PartyListDTO(party_sn,party_pic,party_private, Logined.member_id,plDTO.getParty_name(),edt_party_detail.getText()+"", party_tag1,party_tag2,party_tag3, Logined.member_id);
                updateParty(dto);
                int aaaa = 0 ;
                Intent intent = new Intent();
                intent.putExtra("party_dto",dto);
                setResult(RESULT_OK,intent);
                finish();
            }
        });






    }//onCreate()

    // 사진 픽업 메소드
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1004){

            Toast.makeText(UpdateMyPartyActivity.this, "이미지 가져오기 성공.", Toast.LENGTH_SHORT).show();
            Uri galleryUri = data.getData();
            party_pic = getPathFromURI(galleryUri);
            //img_filepath = galleryUri;
            Glide.with(UpdateMyPartyActivity.this).load(galleryUri).into(imgv_party_pic);

        }
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
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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




    // 파티 업데이트 디비 전송
    private void updateParty(PartyListDTO dto) {
        CommonAsk commonAsk = new CommonAsk("android/party/updateparty");
        commonAsk.params.add(new CommonAskParam("dto", gson.toJson(dto)) );
        commonAsk.params.add(new CommonAskParam("new_pic", new_pic ));

        // 새로 추가한 사진이 있다면
        if(party_pic != null){
            commonAsk.fileParams.add(new CommonAskParam("party_pic",party_pic));
        }

        InputStream in = CommonMethod.excuteAsk(commonAsk);
    }

    private void addTags(String party_tag) {
        if (!party_tag.equals(null)){
            tags.add(party_tag);
        }
    }


    // 태그 추가
    public void insertTags(){

        if (edt_party_tag.getText()+""!= null && tags.size() <3){
            String party_tag = edt_party_tag.getText()+"";
            tags.add(party_tag);
            setTags();      //태그 1~3 리스트로 세팅
            edt_party_tag.setText("");
        }else{
            Toast.makeText(UpdateMyPartyActivity.this, "파티 태그는 최대 3개", Toast.LENGTH_SHORT).show();
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


}