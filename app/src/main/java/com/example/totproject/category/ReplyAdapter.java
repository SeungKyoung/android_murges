package com.example.totproject.category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.totproject.R;

import java.util.LinkedList;

public class ReplyAdapter extends RecyclerView.Adapter<ReplyAdapter.ViewHolder> {
    Context context; // <- foreGround  == frontGround
                     // ,  ↑ BackGround ( Context )==생성자
    LinkedList<String> list;
    LayoutInflater inflater;
    //GridAdapter <= FragmentGrid.Class(java) 생성해보기
    public ReplyAdapter(Context context) {
        this.context = context;
        //this.inflater = inflater;←만들어서 넘겨준거 세팅, ↓직접 만들기
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.list = new LinkedList<>();
    }

    public void addImage(String path){
        list.add(path);
        ReplyAdapter.this.notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = inflater.inflate(R.layout.category_act_reply_img_item , parent , false );
        //1. ViewHolder holder = new ViewHolder(itemview);
        // return holder;
        return new ViewHolder(itemview);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position)).override(100,100).into(holder.category_reply_img);
        final int ps = position;
        holder.category_reply_img_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(ps);
                ReplyAdapter.this.notifyDataSetChanged();
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView category_reply_img, category_reply_img_cancel;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            category_reply_img = itemView.findViewById(R.id.category_reply_img);
            category_reply_img_cancel = itemView.findViewById(R.id.category_reply_img_cancel);

        }
    }


    public LinkedList<String> getList(){
        return list;
    }

}
