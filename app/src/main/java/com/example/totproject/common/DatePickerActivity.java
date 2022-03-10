package com.example.totproject.common;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialog;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.totproject.R;
import com.example.totproject.party_plan.PlanCreatePlanActivity;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class DatePickerActivity extends AppCompatActivity {
    DatePicker datePicker;

    int now_year, now_month, now_day;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature( Window.FEATURE_NO_TITLE );
        setContentView(R.layout.datepicker_act);

        datePicker = findViewById(R.id.dataPicker);


        // 데이트 피커로 정보 date에 담음
        SimpleDateFormat sdf_year = new SimpleDateFormat("yyyy");
        SimpleDateFormat sdf_month = new SimpleDateFormat("MM");
        SimpleDateFormat sdf_day = new SimpleDateFormat("dd");
        Date now = new Date();
        now_year = Integer.parseInt(sdf_year.format(now));
        now_month = Integer.parseInt(sdf_month.format(now)) -1;     // 한달 마이너스 되는 버그있음 (코드로 수정해논 것)
        now_day = Integer.parseInt(sdf_day.format(now));




        datePicker.init(now_year, now_month, now_day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String str_monthOfYear = "";
                String str_dayOfMonth = "";
                if (monthOfYear <= 9){
                    monthOfYear++;
                    str_monthOfYear = "0"+monthOfYear;
                }else{
                    monthOfYear++;
                    str_monthOfYear = ""+monthOfYear;
                }

                if (dayOfMonth <= 9){
                    str_dayOfMonth = "0"+dayOfMonth;
                }else{
                    str_dayOfMonth = ""+dayOfMonth;
                }


                String date = year + "-" + str_monthOfYear + "-" + str_dayOfMonth;
                Toast.makeText(DatePickerActivity.this, date, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(DatePickerActivity.this , PlanCreatePlanActivity.class);//
                intent.putExtra("date", date+"");

                setResult(RESULT_OK,intent);
                finish();




            }
        });











    }
}