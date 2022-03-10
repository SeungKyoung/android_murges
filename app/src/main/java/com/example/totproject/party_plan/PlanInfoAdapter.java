package com.example.totproject.party_plan;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.totproject.R;

import java.util.ArrayList;

public class PlanInfoAdapter extends BaseExpandableListAdapter {


    LayoutInflater inflater;
    ArrayList<PlanInfoDTO> list;
    Context context;

    public PlanInfoAdapter(Context context, LayoutInflater inflater, ArrayList<PlanInfoDTO> list) {
        this.context = context;
        this.inflater = inflater;
        this.list = list;
    }



    // 부모 사이즈 = 현재 부모사이즈는 ArrayList의 사이즈 그대로를 주면됩니다.
    @Override
    public int getGroupCount() {
        return list.size();
    }
    //자식 사이즈 = 등록 된 답변이 있다면 부모↑ ListView에 자식 리스트뷰를 몇개 붙일건지
    //사이즈를 지정하는 부분.
    @Override
    public int getChildrenCount(int i) {

        return  list.get(i).getSubList().size();

    }

    // 그룹 오브젝트 ( 아이템을 선택했을때 return 받을 DTO)
    @Override
    public Object getGroup(int i) {
        return list.get(i);
    }
    // 자식 오브젝트 ( 자식 아이템을 선택했을때 return 받을 데이터 현재는 String값을
    // return 받게 해둠. )
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return list.get(groupPosition).getSubList().get(childPosition);
    }


    //포지션 부분 그냥 고정적으로 사용하면됨.
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    // groupPosition은 부모 인덱스 , childPosition은
    //부모에서 자식 인덱스 두개를 응용해서 사용도 가능함.
    // ex) 1번 group에서 3번째 자식을 선택한 경우 이벤트.. 등등 )
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    //true 그냥 놔두시면 됩니다.
    @Override
    public boolean hasStableIds() {
        return true;
    }


    //부모 아이템에 보여줄 xml을 연결하는 코드.
    //RecyclerView( getView 메소드랑 똑같음 )
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder viewHolder ;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.partyplan_frag_planinfo02_item , parent , false);
            viewHolder = new GroupViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (GroupViewHolder) convertView.getTag();
        }
        viewHolder.bind(groupPosition);
        return convertView;
    }



    //자식 아이템에 보여줄 xml을 연결하는 코드.
    //RecyclerView( getView 메소드랑 똑같음 )
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder viewHolder ;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.partyplan_frag_planinfo02_item_dayitem , parent , false);
            viewHolder = new ChildViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ChildViewHolder) convertView.getTag();
        }
        viewHolder.bind(groupPosition , childPosition);
        return convertView;
    }

    // 자식이 선택되었는지 안되었는지를 판단할수있는 메소드
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    //부모 위젯들을 하나의 클래스로 묶어놓을 ViewHolder
    //매번 화면이 전환되거나 스크롤이 위아래로 바뀔때마다 ConvertView에있는
    //위젯을 다시 찾는게아니라 미리 클래스를 지정해놓고 저장해놓고 사용하기 위함.
    public class GroupViewHolder {
        public TextView tv_partyplan_day;
        public Button btn_plandetail_update;

        public GroupViewHolder(View itemview) {
            tv_partyplan_day = itemview.findViewById(R.id.tv_partyplan_day);
            btn_plandetail_update = itemview.findViewById(R.id.btn_plandetail_update);


        }

        public void bind(int i) {
            tv_partyplan_day.setText(list.get(i).getPlandetail_date());

            btn_plandetail_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //@@@@@@@@@@@ 해당 날짜플랜 수정하는 액티비티 이동 만들어주기 @@@@@@@@@
                    Intent intent = new Intent(context, PlanUpdatePlanDetailActivity.class);
                    intent.putExtra("tabcode",1);
                    intent.putExtra("plan_sn",list.get(i).getPlan_sn());
                    intent.putExtra("palndetail_day",list.get(i).getPlandetail_date());
                    context.startActivity(intent);

                }
            });



        }//bind()
    }

    public class ChildViewHolder {
        public TextView tv_partyplan_time ,tv_partyplan_content,tv_partyplan_content_detail;
        public ChildViewHolder(View itemview) {
            tv_partyplan_time= itemview.findViewById(R.id.tv_partyplan_time);
            tv_partyplan_content= itemview.findViewById(R.id.tv_partyplan_content);
            tv_partyplan_content_detail= itemview.findViewById(R.id.tv_partyplan_content_detail);
        }
        public void bind(int i , int j) {
            tv_partyplan_time.setText(list.get(i).getSubList().get(j).getPlandetail_time());
            tv_partyplan_content.setText(list.get(i).getSubList().get(j).getPlandetail_content());
            tv_partyplan_content_detail.setText(list.get(i).getSubList().get(j).getPlandetail_content_detail());


        }
    }

}
