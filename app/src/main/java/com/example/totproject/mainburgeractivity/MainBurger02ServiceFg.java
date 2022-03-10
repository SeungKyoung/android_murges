package com.example.totproject.mainburgeractivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.totproject.R;

public class MainBurger02ServiceFg extends Fragment {


    Button btn1, btn2, btn3, btn4;
    Context context;
    FragmentManager manager;
    String whatCase;

    public MainBurger02ServiceFg(Context context, FragmentManager manager) { //컨텍슽르르 메인에서부터 가져옴
        this.context = context;
        this.manager = manager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.mainburger02_service_fg, container, false);

        btn1 = v.findViewById(R.id.service_btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testToast();
                changeFragment(new MainBurger02ServiceFg01QnAFg(context,manager));
            }
        });

        btn2 = v.findViewById(R.id.service_btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testToast();
                whatCase = "OneOne";
                changeFragment(new MainBurger02ServiceFg0203OneOneEmailFg(context,manager,whatCase));
            }
        });

        btn3 = v.findViewById(R.id.service_btn3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testToast();
                whatCase = "Email";
                changeFragment(new MainBurger02ServiceFg0203OneOneEmailFg(context,manager,whatCase));
            }
        });

        btn4 = v.findViewById(R.id.service_btn4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testToast();
                changeFragment(new MainBurger02ServiceFg04TelFg());
            }
        });


        return v;

    }

    int i = 1;

    public void testToast() {
        Toast.makeText(context, "눌림" + i, Toast.LENGTH_SHORT).show();
        i++;
    }

    public void changeFragment(Fragment fragment) {
        manager.beginTransaction().replace(R.id.mainburger_container, fragment).addToBackStack(null).commit();
    }

}