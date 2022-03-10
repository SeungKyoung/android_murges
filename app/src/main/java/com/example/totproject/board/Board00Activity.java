package com.example.totproject.board;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.totproject.R;
import com.example.totproject.common.VO.BoardCommonVO;
import com.example.totproject.common.statics.ChangeView;

public class Board00Activity extends AppCompatActivity {
    int tabCode;
    TextView board_act_tv_title;
    int board_container_top, board_container_bottom;
//주석
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board00_act_main);
        board_act_tv_title = findViewById(R.id.board_act_tv_title);
        board_container_top = R.id.board_container_top;
        board_container_bottom = R.id.board_container_bottom;
        Intent getIntent = new Intent(getIntent());
        String tabText = getIntent.getStringExtra("tabText");
        if ("write".equals(tabText)) {
            Board00DetailFg board00_detail_fg = new Board00DetailFg(Board00Activity.this, getSupportFragmentManager(), "write");

           // Board01WriteFg board01WriteFg = new Board01WriteFg();
            ChangeView.viewFrament(board_container_top, board00_detail_fg, getSupportFragmentManager());
        } else if ("search".equals(tabText)) {
            Board01WriteFg board01SearchFg = new Board01WriteFg();
            ChangeView.viewFrament(board_container_top, board01SearchFg, getSupportFragmentManager());
        } else if ("detail".equals(tabText)) {
            BoardCommonVO vo = (BoardCommonVO) getIntent.getSerializableExtra("vo");
            Board00DetailFg board00_detail_fg = new Board00DetailFg(Board00Activity.this, getSupportFragmentManager(), vo);
            ChangeView.viewFrament(board_container_top, board00_detail_fg, getSupportFragmentManager());

        }

    }//oncreate()


    /* =================================== view변경 + 상단텍스트 변경 ===========-------======================== */


}