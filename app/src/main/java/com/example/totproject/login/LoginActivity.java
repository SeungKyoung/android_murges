package com.example.totproject.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.totproject.R;
import com.example.totproject.common.CommonAsk;
import com.example.totproject.common.CommonMethod;
import com.example.totproject.common.VO.MemberDTO;
import com.google.gson.Gson;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.common.KakaoSdk;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.Account;
import com.kakao.sdk.user.model.Profile;
import com.navercorp.nid.oauth.OAuthLoginCallback;
import com.navercorp.nid.oauth.view.NidOAuthLoginButton;
import com.nhn.android.naverlogin.OAuthLogin;

import java.io.InputStream;
import java.io.InputStreamReader;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class LoginActivity extends AppCompatActivity {
    Button login_btn_login;
    EditText edit_login_id, edit_login_pw;
    TextView text_login_join, search_id, search_pw;
    Button kakaologin;
    OAuthLogin authLogin ;
    NidOAuthLoginButton naverlogin;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_act_login);
        login_btn_login = findViewById(R.id.login_btn_login);
        text_login_join =findViewById(R.id.text_login_join);
        edit_login_id = findViewById(R.id.edit_login_id);
        edit_login_pw = findViewById(R.id.edit_login_pw);
        search_id = findViewById(R.id.search_id);
        search_pw = findViewById(R.id.search_pw);
        kakaologin = findViewById(R.id.kakaologin);

        // Naver 로그인
        authLogin = OAuthLogin.getInstance();
        authLogin.showDevelopersLog(true);
        authLogin.init(
                LoginActivity.this,
                "uGpmI5HP4456GOdaotwq",
                "ElhUyTFWhH",
                "ToT"
        );
        naverlogin = findViewById(R.id.naverlogin);
        naverlogin.setOAuthLoginCallback(new OAuthLoginCallback() {
            @Override
            public void onSuccess() {
                String accesToken = authLogin.getAccessToken(LoginActivity.this);
                String refreshToken = authLogin.getRefreshToken(LoginActivity.this);
                Toast.makeText(LoginActivity.this, "네이버로그인 성공", Toast.LENGTH_SHORT).show();
                goSplash();

            }

            @Override
            public void onFailure(int i, @NonNull String s) {

            }

            @Override
            public void onError(int i, @NonNull String s) {

            }
        });


        // Kakao SDK 초기화
        KakaoSdk.init(this, "77f34c0e0e72631cebb2c001a7e0257a");

        // 로그인
        login_btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MemberDTO dto = new MemberDTO();
                dto.setMember_id(edit_login_id.getText() + "");
                dto.setMember_pw(edit_login_pw.getText() + "");

                if(edit_login_id.getText().length() <= 0 || edit_login_pw.getText().length() <= 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    dialog = builder.setMessage("아이디나 비번을 입력하세요").setPositiveButton("확인", null).create();
                    dialog.show();
                }else {
                    dto = loginTry(dto);
                    if(dto != null){
                        Toast.makeText(LoginActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();

                        goSplash(dto);
                    }else{
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(LoginActivity.this);
                        dialog = builder1.setMessage("아이디나 비번을 확인하세요").setPositiveButton("확인", null).create();
                        dialog.show();
                        edit_login_id.setText("");
                        edit_login_pw.setText("");
                        edit_login_id.requestFocus();
                    }
                }
            }
        });

/*        // 아이디 찾기
        search_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, FindIdActivity.class);
                startActivity(intent);
            }
        });

        // 비밀번호 찾기
        search_pw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, FindPwActivity.class);
                startActivity(intent);

            }
        });*/


        // 회원가입
        text_login_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, TendencyActivity01.class);
                startActivity(intent);
                finish();
            }
        });


        // 로그인 공통 callback 구성

        //https://developers.kakao.com/docs/latest/ko/kakaologin/android
        //kotlin ↓ 자바로 바꿈
        Function2<OAuthToken , Throwable , Unit> callback = new Function2<OAuthToken, Throwable, Unit>() {
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                if(oAuthToken != null){
                    Toast.makeText(LoginActivity.this, "정보를 받아옴", Toast.LENGTH_SHORT).show();
                    getKakaoinfo();
                    goSplash();
                }
                if(throwable != null){
                    Toast.makeText(LoginActivity.this, "뭔가 오류가남.", Toast.LENGTH_SHORT).show();
                }

                return null;
            }
        };


        // 카카오 로그인
        kakaologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
                if( UserApiClient.getInstance().isKakaoTalkLoginAvailable(LoginActivity.this)) {
                    Toast.makeText(LoginActivity.this, "카카오톡 설치됨", Toast.LENGTH_SHORT).show();
                    UserApiClient.getInstance().loginWithKakaoTalk(LoginActivity.this,callback);
                }else {
                    Toast.makeText(LoginActivity.this, "카카오톡 설치 안됨", Toast.LENGTH_SHORT).show();
                    UserApiClient.getInstance().loginWithKakaoAccount(LoginActivity.this,callback);
                }

            }
        });

    }// onCreate()

    public void getKakaoinfo() {
        UserApiClient.getInstance().me( (user, throwable) -> {
            if(throwable != null){
                //오류가 났을때 어떤 오류인지 알아볼수가 있음 . KOE + 숫자
            }else{
                // [ {  }  ] json 구조처럼 바로 데이터가 있는게 아니라 Account라는 키로 한칸을 들어가고
                //여기안에서 또 profile이라는 칸으로 또 이동 .
                Account kakaoAcount = user.getKakaoAccount();
                if(kakaoAcount != null){
                    Profile profile = kakaoAcount.getProfile();
                    if(profile != null){
                        Toast.makeText(LoginActivity.this, profile.getNickname()+"님 환영", Toast.LENGTH_SHORT).show();
                        goSplash();
                    }
                }
            }

            return  null;
        }) ;
    } //getKakaoinfo()

    public void goSplash() {
        Intent intent = new Intent(LoginActivity.this,SplashActivity.class);

        startActivity(intent);
        finish();
    } //
    public void goSplash(MemberDTO dto) {
        Intent intent = new Intent(LoginActivity.this,SplashActivity.class);
intent.putExtra("dto",dto);
        startActivity(intent);
        finish();
    } //

    CommonAsk service;
    Gson gson = new Gson();
    public MemberDTO loginTry(MemberDTO dto) {
        service = new CommonAsk("login");
        service.addParams("dto" , gson.toJson(dto));

        InputStream in = CommonMethod.excuteAsk(service);
        try {

            dto = gson.fromJson(new InputStreamReader(in), MemberDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dto;
    }//loginTry()


}