package com.example.totproject.mainburgeractivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.totproject.R;

public class MainBurger00Activity extends AppCompatActivity {
    int tabcode;
    TextView mainburger_menu_maintext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainburger00_activity);
        mainburger_menu_maintext = findViewById(R.id.mainburger_menu_maintext);
        Intent getIntent = getIntent();

        mainburger_menu_maintext.setText(getIntent().getStringExtra("tabText"));


        tabcode = getIntent.getIntExtra("tabcode", 0);
        FragmentManager manager = getSupportFragmentManager();
        if (tabcode == 1) {
            MainBurger01NoticeFg mainBurger01NoticeFg = new MainBurger01NoticeFg(MainBurger00Activity.this,manager);
            getSupportFragmentManager().beginTransaction().replace(R.id.mainburger_container, mainBurger01NoticeFg).commit();

            // 공지사항 출력연계

        } else if (tabcode == 2) {
            MainBurger02ServiceFg MainBurger02ServiceFg = new MainBurger02ServiceFg(MainBurger00Activity.this,manager);
            getSupportFragmentManager().beginTransaction().replace(R.id.mainburger_container, MainBurger02ServiceFg).commit();


            // 고객센터 출력연계

        } else if (tabcode == 3) {
            MainBurger03ManualFg MainBurger03ManuallFg = new MainBurger03ManualFg(MainBurger00Activity.this,manager);
            getSupportFragmentManager().beginTransaction().replace(R.id.mainburger_container, MainBurger03ManuallFg).commit();
            // 이용약관 출력연계

        }
    }
}