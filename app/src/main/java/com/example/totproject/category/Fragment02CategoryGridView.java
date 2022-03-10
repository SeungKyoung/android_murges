package com.example.totproject.category;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.totproject.R;
import com.example.totproject.common.CommonAsk;
import com.example.totproject.common.CommonMethod;
import com.example.totproject.common.VO.BoardCommonVO;
import com.example.totproject.main.MainActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class Fragment02CategoryGridView extends Fragment {
    GridView gridView;
    int tabcode, paramSn;
    String headerText;
    ArrayList<BoardCommonVO> list = new ArrayList<>();
    int sorry_chaminhwan = 0; //메인탭으로부터 진입시 무한루프 방지

    public Fragment02CategoryGridView(int tabcode) {
        this.tabcode = tabcode;
    }

    public Fragment02CategoryGridView(int tabcode, int paramSn,String headerText) {
        this.tabcode = tabcode;
        this.paramSn = paramSn;
        this.headerText = headerText;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rooView = inflater.inflate(R.layout.category_frag_gridlist, container, false);


        /*SharedPreferences pref = getActivity().getSharedPreferences("Fragnebt02Tab", Context.MODE_PRIVATE);
        String result = pref.getString("tabText", "");*/


        if (tabcode == 1) {
            categoryList_tour();
        } else if (tabcode == 2) {
            categoryList_activity();
        } else if (tabcode == 3) {
            categoryList_festival();
        } else if (tabcode == 4) {

            if(sorry_chaminhwan ==0) {
                BoardCommonVO vo = new BoardCommonVO();
                vo.setBoard_sn(paramSn);
                Fragment02CategoryDetail categoryDetail = new Fragment02CategoryDetail(0);
                getFragmentManager().beginTransaction().replace(R.id.cate_container, categoryDetail).addToBackStack(null).commit();
                Bundle bundle = new Bundle();
                bundle.putInt("sn", vo.getBoard_sn());
                getParentFragmentManager().setFragmentResult("sn", bundle);
                sorry_chaminhwan ++;
            }else{
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);



            }

        }


        CategoryAdapter adapter = new CategoryAdapter(getContext(), list);
        gridView = rooView.findViewById(R.id.gridv);

        gridView.setAdapter(adapter);

/*        CommonAsk commonAsk = new CommonAsk("categrid");
        commonAsk.execute();*/


        return rooView;
    }

    /*public ArrayList<CategoryVO> list(){
        Gson gson = new Gson();
        CommonAsk commonAsk = new CommonAsk("category_list");
        InputStream in = CommonMethod.excuteAsk(commonAsk);

        try{



            list    = gson.fromJson(new InputStreamReader(in), new TypeToken< List<CategoryVO> >(){}.getType());
        }catch (Exception e){
            e.printStackTrace();
        }
        return  list;
    }*/

    public ArrayList<BoardCommonVO> categoryList_tour() {
        Gson gson = new Gson();
        CommonAsk commonAsk = new CommonAsk("categoryList_tour");
        InputStream in = CommonMethod.excuteAsk(commonAsk);

        try {


            list = gson.fromJson(new InputStreamReader(in), new TypeToken<List<BoardCommonVO>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<BoardCommonVO> categoryList_activity() {
        Gson gson = new Gson();
        CommonAsk commonAsk = new CommonAsk("categoryList_activity");
        InputStream in = CommonMethod.excuteAsk(commonAsk);

        try {


            list = gson.fromJson(new InputStreamReader(in), new TypeToken<List<BoardCommonVO>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<BoardCommonVO> categoryList_festival() {
        Gson gson = new Gson();
        CommonAsk commonAsk = new CommonAsk("categoryList_festival");
        InputStream in = CommonMethod.excuteAsk(commonAsk);

        try {


            list = gson.fromJson(new InputStreamReader(in), new TypeToken<List<BoardCommonVO>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    public class CategoryAdapter extends BaseAdapter {
        // ,  ↑ BackGround ( Context )==생성자
        ArrayList<BoardCommonVO> list;
        LayoutInflater inflater;

        //GridAdapter <= FragmentGrid.Class(java) 생성해보기
        public CategoryAdapter(Context context, ArrayList<BoardCommonVO> list) {
            this.list = list;
            //this.inflater = inflater;←만들어서 넘겨준거 세팅, ↓직접 만들기
            this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        //커스터마이징이 가장 많이 되는 부분
        //직접 GridView에 보여지는 Layout처리를 해야함. java<->xml
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            GridViewHolder viewHolder;
            if (convertView == null) {//아직 초기 디자인이 없는상태의 view라면
                convertView =            //layout        , parent , 제어권 false
                        inflater.inflate(R.layout.category_frag_gridlist_item_list, parent, false);
                viewHolder = new GridViewHolder();
                viewHolder.category_img_tour = convertView.findViewById(R.id.category_img_tour);
                viewHolder.category_tv_tourname = convertView.findViewById(R.id.category_tv_tourname);
                viewHolder.category_tv_like = convertView.findViewById(R.id.category_tv_like);
                viewHolder.category_tv_comment = convertView.findViewById(R.id.category_tv_comment);
                viewHolder.category_img_tour.setScaleType(ImageView.ScaleType.FIT_XY);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (GridViewHolder) convertView.getTag();
            }
            //if(list.get(position).getImgresId() == 1)

            //       viewHolder.category_img_tour.setImageResource(list.get(position).getPicture_filepath());
            viewHolder.category_tv_tourname.setText(list.get(position).getBoard_title());
            viewHolder.category_tv_comment.setText(String.valueOf(list.get(position).getBoard_cnt_reply()));
            viewHolder.board_sn = list.get(position).getBoard_sn();
            viewHolder.category_tv_like.setText(String.valueOf(list.get(position).getFunction_like()));
            Glide.with(getContext())
                    .load(list.get(position).getPicture_filepath())
                    .error(R.drawable.no_image)
                    .transition(DrawableTransitionOptions.withCrossFade(500))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(viewHolder.category_img_tour);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BoardCommonVO vo = new BoardCommonVO();
                    vo.setBoard_sn(list.get(position).getBoard_sn());
                    Fragment02CategoryDetail categoryDetail = new Fragment02CategoryDetail(0);
                    getFragmentManager().beginTransaction().replace(R.id.cate_container, categoryDetail).addToBackStack(null).commit();
                    Bundle bundle = new Bundle();
                    bundle.putInt("sn", vo.getBoard_sn());
                    getParentFragmentManager().setFragmentResult("sn", bundle);
                }
            });

            return convertView;
        }

        //ViewHolder <- 위젯들을 하나로 묶은 클래스를 만들고 사용.
        //Imagev1 , tv1 , tv2 (Class)
        public class GridViewHolder {
            public ImageView category_img_tour;
            public TextView category_tv_tourname, category_tv_like, category_tv_comment;
            int board_sn;
        }

    }

   /* public void boardSN(int sn){
        Intent intent = new Intent(getActivity(), Fragment02CategoryDetail.class);
        intent.putExtra("sn",sn);
        startActivity(intent);

finish();

    }*/



/*    @Override
    public void onClick(View v) {
        Fragment02CategoryDetail categoryDetail = new Fragment02CategoryDetail();
        getFragmentManager().beginTransaction().replace(R.id.container, categoryDetail).commit();
    }*/
}