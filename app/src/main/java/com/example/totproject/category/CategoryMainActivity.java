package com.example.totproject.category;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.totproject.R;

public class CategoryMainActivity extends AppCompatActivity {
    //GridView gridView;
    int tabcode, paramSn;
    TextView category_main_title;
    ImageView category_img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_act_main_new);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }



        category_img_back = findViewById(R.id.category_img_back);


        category_main_title = findViewById(R.id.category_main_title);
        Intent intent = getIntent();
        tabcode = intent.getIntExtra("tabcode" ,  0);
        paramSn = intent.getIntExtra("paramSn",0);
       String aa = intent.getStringExtra("tabText");
        category_main_title.setText(getIntent().getStringExtra("tabText"));

        Fragment02CategoryGridView categoryGridView = new Fragment02CategoryGridView(tabcode);
        Bundle bundle = new Bundle();
        bundle.putInt("tabcode", tabcode);

        if(tabcode == 1){
            getSupportFragmentManager().beginTransaction().replace(R.id.cate_container, categoryGridView).commit();
            // 관광지시 출력연계

        }else if (tabcode ==2){
            getSupportFragmentManager().beginTransaction().replace(R.id.cate_container, categoryGridView).commit();
            // 액티비티시 출력연계

        }else if (tabcode ==3){
            getSupportFragmentManager().beginTransaction().replace(R.id.cate_container, categoryGridView).commit();
            // 지역축제시 출력연계

        }else if (tabcode ==4){
            Fragment02CategoryGridView categoryGridView2 = new Fragment02CategoryGridView(tabcode,paramSn,category_main_title.getText()+"");
            getSupportFragmentManager().beginTransaction().replace(R.id.cate_container, categoryGridView2).commit();
        }

        category_img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoryMainActivity.super.onBackPressed();
            }
        });



        /*category_img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment02CategoryTab categoryTab = new Fragment02CategoryTab();
                getSupportFragmentManager().beginTransaction().replace(R.id.cate_container, categoryTab).commit();
               *//* Fragment02CategoryTab categoryTab = new Fragment02CategoryTab();
                Intent intent1 = new Intent(CategoryMainActivity.this, categoryTab.getActivity().getClass());
                startActivity(intent1);*//*
            }
        });*/

        /*if(category_main_title.equals("관광지")){
            category_img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment02CategoryTab categoryTab = new Fragment02CategoryTab();
                getSupportFragmentManager().beginTransaction().replace(R.id.cate_container, categoryTab).commit();
                }
            });
        }*/

        /*ArrayList<GridDTO> list = new ArrayList<>();

        for(int i=0; i < 10; i++){
            GridDTO dto = new GridDTO();
            dto.setBoard_title("제목"+i);
            dto.setBoard_content("내용"+i);
            dto.setBoard_read_cnt(i);
            dto.setBoard_reviewepath(i);


            list.add(dto);
        }
        GridAdapter adapter = new GridAdapter(CategoryMainActivity.this,list);
        gridView = findViewById(R.id.gridv);

        gridView.setAdapter(adapter);*/
    }

    public void changFrag(Fragment frag){
        getSupportFragmentManager().beginTransaction().replace(R.id.cate_container, frag).commit();
    }




}