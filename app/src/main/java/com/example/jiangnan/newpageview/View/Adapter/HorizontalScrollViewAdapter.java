package com.example.jiangnan.newpageview.View.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.jiangnan.newpageview.R;
import com.example.jiangnan.newpageview.View.MyCircleImageView;

import java.util.List;

/**
 * Created by jiangnan on 2018/4/3.
 */

public class HorizontalScrollViewAdapter
{

    private Context mContext;
    private LayoutInflater mInflater;
    private List<Integer> mDatas;

    public HorizontalScrollViewAdapter(Context context, List<Integer> mDatas)
    {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mDatas = mDatas;
    }

    public int getCount()
    {
        return mDatas.size();
    }

    public Object getItem(int position)
    {
        return mDatas.get(position);
    }

    public long getItemId(int position)
    {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder = null;
        if (convertView == null)
        {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(
                    R.layout.item_horizontal_scrollview_my, parent, false);
            viewHolder.mImg = (MyCircleImageView) convertView
                    .findViewById(R.id.id_index_gallery_item_image);
            viewHolder.mText = (TextView) convertView
                    .findViewById(R.id.id_index_gallery_item_text);

            convertView.setTag(viewHolder);
        } else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mImg.setImageResource(mDatas.get(position));
        viewHolder.mText.setText(mContext.getString(R.string.xingtian));

        return convertView;
    }

    private class ViewHolder
    {
        MyCircleImageView mImg;
        TextView mText;
    }

}
