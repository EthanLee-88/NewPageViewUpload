package com.example.jiangnan.newpageview.View.Adapter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jiangnan.newpageview.R;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by jiangnan on 2018/5/18.
 */

public class BlueToothRecyclerAdapter extends RecyclerView.Adapter<BlueToothRecyclerAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Map<String , Object>> blueToothList;
    private static final String TAG = "BlueToothRecyclerAdapter";

    public BlueToothRecyclerAdapter(Context context , ArrayList<Map<String , Object>> deviceList){
        mContext = context;
        blueToothList = deviceList;
        Log.d(TAG , "列表长度=" + blueToothList.size());
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        setOnItemClickListener = onItemClickListener;
    }

    private OnItemClickListener setOnItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(View view , int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView deviceName , deviceMac;
        private LinearLayout clickLinearLayout;

        public ViewHolder(View view){
            super(view);
            deviceName = view.findViewById(R.id.bluetooth_name);
            deviceMac = view.findViewById(R.id.bluetooth_mac);
            clickLinearLayout = view.findViewById(R.id.click_item);
            Log.d(TAG , "初始化");
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d(TAG , "设备名");
        if(blueToothList.get(position).get("deviseName") != null){
            holder.deviceName.setText(blueToothList.get(position).get("deviseName").toString());
        }else {
            holder.deviceName.setText("默认");
        }
        if(blueToothList.get(position).get("deviseMac") != null){
            holder.deviceMac.setText(blueToothList.get(position).get("deviseMac").toString());
        }else {
            holder.deviceMac.setText("默认");
        }

        if( blueToothList.get(position).get("bondState").toString() == "12"){
            holder.clickLinearLayout.setBackgroundResource(R.color.blue);
        }
        final int fPosition = position;
        holder.clickLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setOnItemClickListener.onItemClick(view , fPosition);
                Log.d(TAG , "点击位置=" + fPosition);
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_bluetooth_show , parent , false);
        Log.d(TAG , "建立布局");
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        Log.d(TAG , "初始化长度");
        return blueToothList.size();
    }
}
