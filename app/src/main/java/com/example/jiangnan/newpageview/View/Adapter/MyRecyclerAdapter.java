package com.example.jiangnan.newpageview.View.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jiangnan.newpageview.Data.MemberData;
import com.example.jiangnan.newpageview.R;
import com.example.jiangnan.newpageview.View.MyCircleImageView;
import com.example.jiangnan.newpageview.View.RoundRectImageView;

import java.util.ArrayList;

/**
 * Created by jiangnan on 2018/4/6.
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder>  {
    private ArrayList<MemberData> memberData;
    private Context mcontext;
    private static final String TAG = "MyRecyclerAdapter";

    public MyRecyclerAdapter(Context context , ArrayList<MemberData> Data){
        memberData = Data;
        mcontext = context;
        Log.d(TAG ,"长度=" + memberData.size());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewNickN;
        private RoundRectImageView MroundRectImageView;
        private LinearLayout memberLayout;

        public ViewHolder (View view){
            super(view);
            textViewNickN = (TextView) view.findViewById(R.id.id_index_gallery_item_text_rect);
            MroundRectImageView = (RoundRectImageView) view.findViewById(R.id.id_index_gallery_item_image_rect);
            memberLayout = (LinearLayout) view.findViewById(R.id.linear_id_rect);
        }
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textViewNickN.setText(memberData.get(position).nickName);

        holder.MroundRectImageView.setImageResource(memberData.get(position).iconId);

        holder.MroundRectImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RoundRectImageView view1 = (RoundRectImageView) view;
        }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_horizontal_scrollview_round_rect , parent , false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        Log.d(TAG , "返回长度=" + memberData.size());
        return memberData.size();
    }
}
