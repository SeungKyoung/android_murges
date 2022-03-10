package com.example.totproject.mainburgeractivity;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.totproject.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainBurger03ManualFg extends Fragment {
    BottomNavigationView manualNav;
    TextView title, content;

    Context context;
    FragmentManager manager;


    public MainBurger03ManualFg(Context context, FragmentManager manager) { //컨텍슽르르 메인에서부터 가져옴
        this.context = context;
        this.manager = manager;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.mainburger03_manual_fg, container, false);
        manualNav = v.findViewById(R.id.nav_manualnav);

        title = v.findViewById(R.id.manual_tv_title);
        content = v.findViewById(R.id.manual_tv_content);


        title.setText("trip or travel 이용약관");
        content.setText("이용약관 \n이이용용약약관관");

        manualNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.manualnav_left) {
                    title.setText("trip or travel 이용약관");
                    content.setText("이용약관 \n이이용용약약관관");

                    return true;
                } else if (item.getItemId() == R.id.manualnav_right) {
                    title.setText("trip or travel 정책");
                    content.setText("정책 \n정정책책정정책책");

                    return true;
                }

                return false;
            }
        });


        return v;
    }


}