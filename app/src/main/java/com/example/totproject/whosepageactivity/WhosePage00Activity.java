package com.example.totproject.whosepageactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.totproject.R;
import com.example.totproject.common.VO.BoardCommonVO;
import com.example.totproject.common.VO.MemberDTO;
import com.example.totproject.common.statics.ChangeView;
import com.example.totproject.main.Fragment03BoardTab;

import java.util.List;

public class WhosePage00Activity extends AppCompatActivity {
    ImageView whosepage_imgv_profile;
    TextView whosepage_tv_member_id, whosepage_tv_text, whosepage_tv_board_cnt;
    MemberDTO memberDTO = new MemberDTO();
    String member_id, cnt_board_list;


    /* ===================================== Contructor ================================== */
    public WhosePage00Activity() {
    }

    public WhosePage00Activity(MemberDTO memberDTO, List<BoardCommonVO> list) {
        this.memberDTO = memberDTO;
        this.cnt_board_list = list.size() + "";

        //   whosepage_tv_member_id.setText(member_id);
        //  whosepage_tv_board_cnt.setText(cnt_board_list);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.whosepage00_act);

        whosepage_tv_member_id = findViewById(R.id.whosepage_tv_member_id);
        whosepage_tv_text = findViewById(R.id.whosepage_tv_text);
        whosepage_tv_board_cnt = findViewById(R.id.whosepage_tv_board_cnt);
        whosepage_imgv_profile = findViewById(R.id.whosepage_imgv_profile);

    }//onCreate


    @Override
    protected void onStart() {
        super.onStart();
        Intent getIntent = getIntent();
        int tabCode = getIntent.getIntExtra("tabCode", 0);
        memberDTO = (MemberDTO) getIntent.getSerializableExtra("memberDTO");
        /*String tabText = getIntent.getStringExtra("fromWhere");
         */
        whosepage_imgv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (memberDTO.getPicture_filepath() == null) {
                    Toast.makeText(WhosePage00Activity.this, "null임", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(WhosePage00Activity.this, "filepath 있음", Toast.LENGTH_SHORT).show();
                }
            }
        });


        if (tabCode == 1) {
            Fragment03BoardTab fragment03BoardTab = new Fragment03BoardTab(WhosePage00Activity.this, getSupportFragmentManager(), "whosePage_write", memberDTO);
            Toast.makeText(WhosePage00Activity.this, "작성 게시물확인", Toast.LENGTH_SHORT).show();
            whosepage_tv_text.setText("작성한 게시물 수 : ");
            ChangeView.viewFrament(R.id.whosepage_container, fragment03BoardTab, getSupportFragmentManager());
        } else if (tabCode == 2) {
            Toast.makeText(WhosePage00Activity.this, "좋아요 게시물확인", Toast.LENGTH_SHORT).show();
            whosepage_tv_text.setText("좋아요 게시물 수 : ");
            Fragment03BoardTab fragment03BoardTab = new Fragment03BoardTab(WhosePage00Activity.this, getSupportFragmentManager(), "whosePage_likes", memberDTO);
            ChangeView.viewFrament(R.id.whosepage_container, fragment03BoardTab, getSupportFragmentManager());
        } else if (tabCode == 3) {
            Toast.makeText(WhosePage00Activity.this, "탭코드3", Toast.LENGTH_SHORT).show();
        }

    }

   /* @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onPause() {
        super.onPause();

    }*/

    /* =============================== 메소드 ============================= */
    public void setActTexts(MemberDTO dto, List<BoardCommonVO> list) { //생성자만으로 안되면 이거 실행해야함
        if (dto.getPicture_filepath() != null) {
            Glide.with(this).load(dto.getPicture_filepath()).into(whosepage_imgv_profile);
        } else {
            Glide.with(this).load(R.drawable.logo_tot).into(whosepage_imgv_profile);
        }
        whosepage_tv_member_id.setText(dto.getMember_id());
        whosepage_tv_board_cnt.setText(list.size() + "");
    }


}