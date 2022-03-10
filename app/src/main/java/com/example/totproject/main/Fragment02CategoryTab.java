package com.example.totproject.main;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.totproject.R;
import com.example.totproject.category.CategoryMainActivity;


public class Fragment02CategoryTab extends Fragment {
LinearLayout category_tour, category_activity, category_festival;
ImageView imgv_categorymain;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView  = (ViewGroup)
                inflater.inflate(R.layout.main_frag_categorytab , container , false);

        category_tour =  rootView.findViewById(R.id.category_tour);
        category_activity = rootView.findViewById(R.id.category_activity);
        category_festival = rootView.findViewById(R.id.category_festival);
        imgv_categorymain = rootView.findViewById(R.id.imgv_categorymain);
        Glide.with(getContext()).load(R.drawable.travel_category).into(imgv_categorymain);
        imgv_categorymain.setColorFilter(Color.parseColor("#BDBDBD"), PorterDuff.Mode.MULTIPLY);
        category_tour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Fragment02CategoryGridView categoryGridView = new Fragment02CategoryGridView();
                //getFragmentManager().beginTransaction().replace(R.id.container, categoryGridView).commit();
                cateTab(1,"관광지");

            }
        });

        category_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent activity = new Intent(getActivity(), CategoryMainActivity.class);
                //startActivity(activity);
                cateTab(2,"액티비티");
            }
        });

        category_festival.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getFragmentManager().beginTransaction().replace(R.id.container, categoryGridView).commit();
                // /* tour랑 같은 레이아웃 구성이어서 java코드에서 소스 수정하기 */
                cateTab(2,"지역 축제");
            }
        });

        return rootView;

    }//onCreateView();


    public void cateTab(int tabcode,String tabText){



        Intent intent = new Intent(getActivity(), CategoryMainActivity.class);
        intent.putExtra("tabcode",tabcode);
        intent.putExtra("tabText",tabText);
        startActivity(intent);
     /*   getActivity().finish();*/

    }
    //Fragment02CategoryGridView categoryGridView = new Fragment02CategoryGridView();


}