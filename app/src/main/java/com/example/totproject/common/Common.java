package com.example.totproject.common;

import android.content.Context;
import android.content.Intent;

import com.example.totproject.main.MainActivity;

// 공통 코드 올릴 클래스 (스태틱으로 처리)
public class Common {

    // 메인 액티비티로 이동
    public static void goMain(Context context){
        Intent intent = new Intent(context , MainActivity.class);
        context.startActivity(intent);

    }//goMain()



}
