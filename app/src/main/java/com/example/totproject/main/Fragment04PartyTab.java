package com.example.totproject.main;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.totproject.R;
import com.example.totproject.party.PartyCreateActivity;
import com.example.totproject.party.PartyMainActivity;


public class Fragment04PartyTab extends Fragment {
    ImageView imgv_partymain;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.main_frag_partytab, container, false);

        LinearLayout open_party_list = view.findViewById(R.id.open_party_list);
        LinearLayout create_party = view.findViewById(R.id.create_party);
        LinearLayout my_party_list = view.findViewById(R.id.my_party_list);
        ImageView imgv_partymain = view.findViewById(R.id.imgv_partymain);
        Glide.with(getContext()).load(R.drawable.travel_party).into(imgv_partymain);
        imgv_partymain.setColorFilter(Color.parseColor("#BDBDBD"), PorterDuff.Mode.MULTIPLY);


        open_party_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click_partytab(1);
            }
        });


        create_party.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), PartyCreateActivity.class);
                startActivity(intent);
            }
        });

        my_party_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click_partytab(3);
            }

        });







        return view;
    }


    public void click_partytab(int tabcode){
        Intent intent = new Intent(getActivity(), PartyMainActivity.class);
        intent.putExtra("tabcode",tabcode);
        startActivity(intent);


    }










}//Fragment04PartyTab