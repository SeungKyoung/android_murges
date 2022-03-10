package com.example.totproject.party;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.andremion.floatingnavigationview.FloatingNavigationView;
import com.bumptech.glide.Glide;
import com.example.totproject.R;
import com.example.totproject.common.CommonMethod;
import com.example.totproject.common.VO.MemberDTO;
import com.example.totproject.common.statics.Logined;
import com.example.totproject.login.JoinActivity;
import com.example.totproject.login.LoginActivity;
import com.example.totproject.login.SplashActivity;
import com.example.totproject.login.TendencyActivity01;
import com.example.totproject.main.MainActivity;
import com.example.totproject.mainburgeractivity.MainBurger00Activity;
import com.example.totproject.whosepageactivity.WhosePage00Activity;
import com.google.android.material.navigation.NavigationView;

import java.util.Date;

public class PartyMainActivity extends AppCompatActivity {

   TextView tv_party_title;
    FloatingNavigationView nav_view;
  /*  String[] mainColors = {

            "#2BA0DA",
            "#885CD0",
            "#FF2E841B",
            "#232344",
            "#551122"
    };*/
    public int reqGcode = 1004;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.party_act_main); //이 엑스엠엘에서
        int tabcode = 0 ;
        tv_party_title = findViewById(R.id.tv_party_title); //다른 텍스트를 찾아놓고 바꿀려고함
        tv_party_title = findViewById(R.id.tv_party_title); //  방금 이거 컨트롤클릭했는데 오른쪽에꺼떴잖

        OpenpartyListFragment openparty_list_frag = new OpenpartyListFragment(PartyMainActivity.this);
        MypartyListFragment myparty_list_frag = new MypartyListFragment(PartyMainActivity.this);

        Intent party_intent = getIntent();

        tabcode = party_intent.getIntExtra("tabcode",0);

        if(tabcode == 1 || tabcode ==0 ){   //공개 파티목록 띄우기
            changeFrag(openparty_list_frag, "공개 파티 목록");
        }
        if(tabcode == 2){   //파티 만들기
            moveAct(PartyMainActivity.this,PartyCreateActivity.class);
        }
        if(tabcode == 3){   //내파티리스트 띄우기
            changeFrag(myparty_list_frag,"가입된 내 파티 목록");
        }




        /* ================================================================================================ */

        /* ==============================  ========================================= */

        /* ================================= 버거메뉴 findView ================================= */
        nav_view = findViewById(R.id.main_burger_view);
        /*nav_view.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(mainColors[0])));*/
        View nav_headerview = nav_view.getHeaderView(0);
        nav_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nav_view.open();
            }
        });
        /* ===================================================================================== */
        /* ========== 로그아웃 ===================================================== = ========== */

        LinearLayout main_burger_mbti;
        TextView main_burger_tv_logout = nav_headerview.findViewById(R.id.main_burger_tv_logout);


        main_burger_tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
                SharedPreferences.Editor spEditor = auto.edit();
                spEditor.clear();
                spEditor.commit();
                goSplash();
            }
        });

        // mbti 성향 입력창으로 이동
        main_burger_mbti = nav_headerview.findViewById(R.id.main_burger_mbti);
        if (Logined.member_id == null) {
            main_burger_mbti.setVisibility(View.GONE);
        }
        main_burger_mbti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangeActivity(TendencyActivity01.class);
            }
        });



        ImageView main_burger_imgv_circle;
        main_burger_imgv_circle= nav_headerview.findViewById(R.id.main_burger_imgv_circle);

        if (Logined.picture_filepath != null) {
            Glide.with(PartyMainActivity.this).load(Logined.picture_filepath).into(main_burger_imgv_circle);
            //Glide.with(context).load(list.get(position).getPicture_filepath()).into(board_user_reply_img_profile);
        }else {
            Glide.with(PartyMainActivity.this).load(R.drawable.logo_tot).into(main_burger_imgv_circle);
        }
        /* ========== = ===================================================== 로그아웃 ========== */

        main_burger_imgv_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDangerousPermissions();
                Intent imageIntent = new Intent();
                imageIntent.setType("image/*");
                imageIntent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(imageIntent,"사용할 앱 선택"), reqGcode);

            }
        });
        //android:visibility="invisible"

        LinearLayout afterLogin, main_burger_myboard, main_burger_myscrap, main_burger_myparty;
        main_burger_myboard = nav_headerview.findViewById(R.id.main_burger_myboard);
        main_burger_myscrap = nav_headerview.findViewById(R.id.main_burger_myscrap);
        main_burger_myparty = nav_headerview.findViewById(R.id.main_burger_myparty);

        main_burger_myboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goWhosePage(1);
                toastCheck();
            }
        });
        main_burger_myscrap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goWhosePage(2);
                toastCheck();
            }
        });
        main_burger_myparty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PartyMainActivity.this, PartyMainActivity.class);
                intent.putExtra("tabcode",3);
                startActivity(intent);
            }
        });


        TextView main_burger_tv_login = nav_headerview.findViewById(R.id.main_burger_tv_login);
        if (Logined.isLogined == true) { //로그인이 된 상태라면
            // main_burger_tv_login.setText("스태틱클래스.저장된이름");
            main_burger_tv_login.setText(Logined.member_id);
        }


        main_burger_tv_login.setOnClickListener(new View.OnClickListener() {    // 로그인 클릭했을떄
            @Override
            public void onClick(View v) {
                if (Logined.isLogined != true) {    // 로그인 클릭시 로그인이 안된 상태라면
                    /* =================================== 로그인하러가기 =================== */
                    Intent intent = new Intent(PartyMainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else { //로그인 된 상태라면
                    /* =================================== 이름누르는데 뭘함? =================== */
                }

            }
        });

        TextView main_burger_tv_join = nav_headerview.findViewById(R.id.main_burger_tv_join);

        main_burger_tv_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PartyMainActivity.this, JoinActivity.class);
                startActivity(intent);
                finish();
            }
        });
        afterLogin = nav_headerview.findViewById(R.id.main_burger_afterlogin);
        if(Logined.isLogined==true){    //로그인 된상태
            /* =================================== 버튼4개on 회원가입off 로그아웃on =================== */
            afterLogin.setVisibility(View.VISIBLE);
            main_burger_tv_join.setVisibility(View.GONE);
            main_burger_tv_logout.setVisibility(View.VISIBLE);
        }else{  //
            /* =================================== 로그인하러가기 =================== */

        }
        /* ===================================================================================== */


        /* =================================== 버거메뉴 =================================== */
        Menu nav_menu = nav_view.getMenu();
        FloatingNavigationView bottom_nav2;
        bottom_nav2 = findViewById(R.id.main_burger_view);
        bottom_nav2.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                int id = item.getItemId();
                String tabText = (String) item.getTitle();
                String title = item.getTitle().toString();
                if (id == R.id.mainnav_notice) {
                    ChangeActivity(MainBurger00Activity.class, 1, tabText);
                } else if (id == R.id.mainnav_customer) {
                    ChangeActivity(MainBurger00Activity.class, 2, tabText);
                } else if (id == R.id.mainnav_policy) {
                    ChangeActivity(MainBurger00Activity.class, 3, tabText);
                } else if (id == R.id.mainnav_version) {
                    Date date = new Date();
                    Toast.makeText(PartyMainActivity.this, "버전정보 확인 : " + date, Toast.LENGTH_LONG).show();
                }
                return true;
            }
        });


        /* ================================================================================================ */
    }//onCreate()

    public void changeFrag(Fragment frag, String title){
        getSupportFragmentManager().beginTransaction().replace(R.id.party_main_container, frag).addToBackStack(null).commit();
        tv_party_title.setText(title);
    }

    public void moveAct(Context context, Class classs){
        Intent intent = new Intent(context,classs);
        startActivity(intent);
        finish();
    }


    // 일단 작동안함
//    public void moveAct(Context context, Class classs, int tabcode){
//        Intent intent = new Intent(context,classs);
//        intent.putExtra("tabcode",tabcode);
//        startActivity(intent);
//    }

    public void ChangeActivity(Class nextAct, int tabcode, String tabText) {
        Intent intent = new Intent(PartyMainActivity.this, nextAct);
        intent.putExtra("tabcode", tabcode);
        intent.putExtra("tabText", tabText);
        startActivity(intent);
        finish();
    }

    public void ChangeActivity(Class nextClass) {
        Intent intent = new Intent(PartyMainActivity.this, nextClass);
        startActivity(intent);
        finish();
    }

    public void goSplash() {
        Intent intent = new Intent(PartyMainActivity.this, SplashActivity.class);

        startActivity(intent);
        finish();
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


    public void goWhosePage(int tabCode){
        Intent intent = new Intent(PartyMainActivity.this, WhosePage00Activity.class);
        intent.putExtra("tabCode",tabCode);
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMember_id(Logined.member_id);
        memberDTO.setPicture_filepath(Logined.picture_filepath);
        intent.putExtra("memberDTO",memberDTO);
        startActivity(intent);}


    public void toastCheck(){
        Toast.makeText(this, "toastCheck", Toast.LENGTH_SHORT).show();
    }
    public void toastCheck(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}