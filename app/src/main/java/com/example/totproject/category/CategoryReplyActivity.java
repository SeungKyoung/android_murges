package com.example.totproject.category;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.totproject.R;
import com.example.totproject.common.CommonAsk;
import com.example.totproject.common.CommonAskParam;
import com.example.totproject.common.CommonMethod;
import com.example.totproject.common.VO.BoardCommonVO;
import com.example.totproject.common.statics.Logined;
import com.example.totproject.main.MainActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CategoryReplyActivity extends AppCompatActivity {
    ImageView  category_img_back;
    EditText category_reply_edt;
    Button category_reply_submit;
    ArrayList<ReplyVO> list = new ArrayList<>();
    RecyclerView image_recyler;
    ReplyAdapter replyAdapter;
    final int MAXIMAGE = 3;
    int boardSN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_act_reply);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        category_img_back = findViewById(R.id.category_img_back);

        category_img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoryReplyActivity.this.finish();
            }
        });



        category_reply_edt = findViewById(R.id.category_reply_edt);

        Intent intent = getIntent();
        boardSN = intent.getIntExtra("sn",0);

        image_recyler = findViewById(R.id.image_recyler);
        replyAdapter = new ReplyAdapter(CategoryReplyActivity.this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(CategoryReplyActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        image_recyler.setLayoutManager(layoutManager);
        image_recyler.setAdapter(replyAdapter);

        String content = category_reply_edt.getText()+"";

        ReplyVO vo = new ReplyVO();
        vo.setBoard_sn(boardSN);
        vo.setReply_content(content);

        /*vo.setMember_id("ChaMinHwan");
        vo.setBoard_title("갱");
        vo.setBoard_content(content);*/

        // .load(): 가져올 주소를 적는 곳
        // .skipMemoryCache(true): 메모리에 캐싱하지 않으려면 true 로 주면 됨
        // .diskCacheStrategy(DiskCacheStrategy.NONE): 디스크에 캐싱하지 않으려면 NONE 으로 주면 됨
        // .into(reply_prev2): 이미지가 들어갈 ImageView 이름 써주면 됨
        /*Glide.with(this).load("https://blog.kakaocdn.net/dn/0mySg/btqCUccOGVk/nQ68nZiNKoIEGNJkooELF1/img.jpg").skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(reply_prev1);
        Glide.with(this).load(R.drawable.welcome).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(reply_prev2);
        Glide.with(this).load(R.drawable.welcome).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(reply_prev3);*/

        findViewById(R.id.category_reply_btn_img).setOnClickListener(v -> {
            if(replyAdapter.getItemCount() >= MAXIMAGE){
                Toast.makeText(CategoryReplyActivity.this,"최대 갯수는 3개입니다.",Toast.LENGTH_SHORT).show();
            }else{
                Intent imageIntent = new Intent();
                imageIntent.setType("image/*");
                imageIntent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(imageIntent, "사용할 앱 선택"), 0);
            }

        });

        category_reply_submit = findViewById(R.id.category_reply_submit);

        category_reply_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String edtValue = category_reply_edt.getText().toString().replaceAll(" ","");
                if(edtValue.length() == 0){
                    Toast.makeText(CategoryReplyActivity.this,"내용을 입력하세요.",Toast.LENGTH_SHORT).show();
                }else{
                    reply_insert();
                    finish();//인텐트 종료

                }
            }
        });
    }

    @SuppressLint("Range")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 0 && resultCode == RESULT_OK && data!=null) {
            Uri galleryUri = data.getData();
            String img_filepath = getPathFromURI(galleryUri);
            //img_filepath = galleryUri;
            replyAdapter.addImage(img_filepath);
        }




        /*Uri imageUri = data.getData();
        if (imageUri != null) {
            List uriPath = imageUri.getPathSegments();
            if (uriPath.size() > 0) {
                long imageId = Long.parseLong(uriPath.get(uriPath.size() - 1).split(":")[1]);
                Bitmap  imageBitmap = MediaStore.Images.Thumbnails.getThumbnail(
                        CategoryReplyActivity.this.getContentResolver(),imageId,MediaStore.Images.Thumbnails.MINI_KIND,null);
                if(imageBitmap != null && imageBitmap.getByteCount() > 0) {
                    mImageView.setImageBitmap(imageBitmap);
                }
            }
        }*/
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


    /**/
    CommonAsk commonAsk;
    Gson gson = new Gson();
    //CategoryVO vo = new CategoryVO();

    public ReplyVO reply_insert() {
        ReplyVO requestVO = new ReplyVO();
        ReplyVO responseVO = null;

        String category_reply_edt_text = category_reply_edt.getText().toString();

      /*  Bundle bundle = new Bundle();
        int replysn = bundle.getInt("sn");

        requestVO.setBoard_sn(replysn);*/
        requestVO.setReply_content(category_reply_edt_text);
        requestVO.setPicture_file_count(replyAdapter.getItemCount());
        requestVO.setMember_id(Logined.member_id);
        requestVO.setBoard_sn(boardSN);
        commonAsk = new CommonAsk("category_reply");

        commonAsk.params.add(new CommonAskParam("reply", gson.toJson(requestVO)));
        for (int i = 0; i < replyAdapter.getItemCount();i ++ ){
            commonAsk.fileParams.add(new CommonAskParam("file"+i, replyAdapter.getList().get(i)));
        }

        //파일은 안주므로 주석  commonAsk.fileParams.add(new CommonAskParam("file", img_filepath));
        InputStream in =  CommonMethod.excuteAsk(commonAsk);

       // String result = gson.fromJson(new InputStreamReader(in) , String.class);
        try {
            responseVO = gson.fromJson(new InputStreamReader(in), new TypeToken<List<ReplyVO>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseVO;
    }
    /**/

}