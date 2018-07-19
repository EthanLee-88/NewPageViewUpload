package com.example.jiangnan.newpageview.UI;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothServerSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.jiangnan.newpageview.Common.ServiceUUID;
import com.example.jiangnan.newpageview.R;
import com.example.jiangnan.newpageview.View.ACirclePointView;
import com.example.jiangnan.newpageview.View.Adapter.BlueToothRecyclerAdapter;
import com.example.jiangnan.newpageview.View.MyCircleImageView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by jiangnan on 2017/12/7.
 */

public class FragmentTwoBlueTooth extends Fragment {
    private ACirclePointView maCirclePointView;
    private Button chectButton , openBluetooth , stopSeach;
    private boolean isCheck = true;
    private MainActivity MmainActivity;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothManager mBluetoothManager = null;
    private Toast mToast;
    private RecyclerView mRecyclerView;
    private ArrayList<Map<String , Object>> deviceList;
    private TextView show;
    private BlueToothRecyclerAdapter mBlueToothRecyclerAdapter;
    private Handler handler;
    private boolean connectState = false;

    public static final UUID notificationDesUUID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");

    private static final UUID serviceOne = UUID.fromString("00001800-0000-1000-8000-00805f9b34fb");
    private static final UUID service_two = UUID.fromString("00001801-0000-1000-8000-00805f9b34fb");
    private static final UUID service_three = UUID.fromString("0000fff0-0000-1000-8000-00805f9b34fb");

    private static final UUID characteristicOneForSone = UUID.fromString("00002a00-0000-1000-8000-00805f9b34fb");
    private static final UUID characteristic_two_for_Sone = UUID.fromString("00002a01-0000-1000-8000-00805f9b34fb");
    private static final UUID characteristic_three_for_Sone = UUID.fromString("00002a04-0000-1000-8000-00805f9b34fb");

    private static final UUID characteristic_one_for_Sthree = UUID.fromString("0000fff7-0000-1000-8000-00805f9b34fb");
    private static final UUID characteristic_two_for_Sthree = UUID.fromString("0000fff6-0000-1000-8000-00805f9b34fb");

    private static final UUID Service_test = UUID.fromString("00001801-0000-1000-8000-00805f9b34fb");
    private static final UUID characteristic_test = UUID.fromString("00002a05-0000-1000-8000-00805f9b34fb");

    public static final byte[] VIBRATION_WITHOUT_LED = new byte []{ 1,1 };//

    private static final String TAG = "FragmentTwoRadioButton";

    private Object o = 789;
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mReceiver);
        gattClose();
        stopDiscovery();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        MmainActivity = (MainActivity) context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        MmainActivity = (MainActivity) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_two , container , false);
        intRes(view);
        return view;
    }

    @Override
    public String toString() {
        return "烟雨江南";
    }

    private void intRes(View view){
        /****************************显示密码位数的小圆圈*************************/
        maCirclePointView = (ACirclePointView) view.findViewById(R.id.chect_point);
        chectButton = (Button) view.findViewById(R.id.chect_contolt);
        openBluetooth = view.findViewById(R.id.bluetooth);
        stopSeach = view.findViewById(R.id.stop_seach);
        mRecyclerView = view.findViewById(R.id.bluetooth_devise_show);
        show = view.findViewById(R.id.show_text);

        chectPoint();
        myBluetooth();

        ServiceUUID serviceUUID = new ServiceUUID();

    }
    private void toastShow(String content){
        if(mToast == null){
            mToast = Toast.makeText(getActivity() ,content , Toast.LENGTH_SHORT);
        }else {
            mToast.setText(content);
        }
        mToast.show();
    }
    private void chectPoint(){
        maCirclePointView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity() , GlideActivity.class);
                startActivity(intent);
            }
        });

        chectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                maCirclePointView.setChecked(isCheck);
                isCheck = !isCheck;
                Log.d(TAG , "碧云天" + o.toString());
            }
        });
    }

    private void myBluetooth(){
        mBluetoothManager = (BluetoothManager) getContext().getSystemService(getContext().BLUETOOTH_SERVICE);
        mBluetoothAdapter = mBluetoothManager.getAdapter();
        deviceList = new ArrayList<Map<String, Object>>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mBlueToothRecyclerAdapter = new BlueToothRecyclerAdapter(getActivity() , deviceList);
        mRecyclerView.setAdapter(mBlueToothRecyclerAdapter);

        handler = new Handler(Looper.getMainLooper());

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        getActivity().registerReceiver(mReceiver , filter);    /*********扫描设备回调*********/
        setBluetoothOnClick();
    }
    private void setBluetoothOnClick(){
        openBluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gattClose();
                stopDiscovery();
                openBluetooth();
            }
        });

        stopSeach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopDiscovery();
            }
        });
    }
    private void openBluetooth(){
        if((mBluetoothAdapter == null) || (!mBluetoothAdapter.isEnabled())){
            Intent bluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(bluetoothIntent , 1);
        }else {
            toastShow("蓝牙已打开");
            seachDevise();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == 0){
                toastShow("打开蓝牙被拒绝");
            }else if(resultCode == -1){
                seachDevise();
                toastShow("打开蓝牙成功");
            }
        }
    }

    private void seachDevise(){
        if((mBluetoothAdapter == null) || (!mBluetoothAdapter.isEnabled())){
            openBluetooth();
        }else {
            if(mBluetoothAdapter.isDiscovering()){
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            mBluetoothAdapter.cancelDiscovery();
                        } catch (Exception e) {
                            // TODO: handle exception
                            e.printStackTrace();
                        }
                    }
                }, 1000);
            }

            mBluetoothAdapter.startDiscovery();

            deviceList.clear();
            mBlueToothRecyclerAdapter.notifyDataSetChanged();
        }
    }
    private void stopDiscovery(){
        if(mBluetoothAdapter.isDiscovering()){
            mBluetoothAdapter.cancelDiscovery();
        }
    }
    private void gattClose(){
        if(connectState){
            mGatt.disconnect();
            mGatt.close();
            connectState = false;
        }
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(BluetoothDevice.ACTION_FOUND.equals(action)){
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Log.d("onReceive" , "设备名称=" + device.getName() +
                        "\t\t\t设备地址=" + device.getAddress());
                saveBluetoothDevise(device.getName() , device.getAddress() , device.getBondState());
                if(device.getName() != null){
//                    if(device.getName().equals("MI Band 2")){
//                        // 设备名称=MI Band 2			设备地址=C0:FA:53:0D:AB:EC
//                        stopDiscovery();
//                        bondDevise(device);
//                    }
                }
            }else if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)){
                toastShow("搜索完成");
                Log.d("finish" , "搜索结束，取消扫描");
                stopDiscovery();
            }
        }
    };
    private void bondDevise(BluetoothDevice device){
        BluetoothDevice bluetoothDevice = mBluetoothAdapter.getRemoteDevice(device.getAddress());
        Log.d("device" , "绑定状态=" + device.getBondState());
        if(device.getBondState() == BluetoothDevice.BOND_NONE){
            try{
//                device.createBond();
                bondDevise(device);

//                Method method = BluetoothDevice.class.getMethod("createBond");
//                boolean result = (Boolean) method.invoke(bluetoothDevice);
                Log.d("connectDevise" , "绑定结果=" + device.getBondState());
                Log.d("connectDevise" , "小米设备地址=" + device.getName() + "小米\t" + device.getAddress());
            }catch (Exception e){
            }
        }else if(device.getBondState()  == BluetoothDevice.BOND_BONDED){
            connect(device);
        }
    }

    private BluetoothGatt mGatt = null;
    private Handler mHandler = new Handler();
    public void connect(BluetoothDevice device) {
        if(device != null) {
            mGatt = device.connectGatt(getActivity(), true, mGattCallback);
            connectState = true;
        }
    }
    private BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            super.onConnectionStateChange(gatt, status, newState);
            Log.v(TAG, "Connection State Changed: " + (newState == BluetoothProfile.STATE_CONNECTED ? "Connected" : "Disconnected"));
            if(newState == BluetoothProfile.STATE_CONNECTED) {
                Log.d(TAG , "已连接");
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            // TODO Auto-generated method stub
//                            Log.i(TAG, "Attempting to start service discovery:"
//                                    + mBluetoothGatt.discoverServices());
                            mGatt.discoverServices();
                            Log.d(TAG , "扫描服务");
                        } catch (Exception e) {
                            // TODO: handle exception
                            e.printStackTrace();
                        }
                    }
                }, 500);
            } else if(newState == BluetoothGatt.STATE_DISCONNECTED) {
                Log.d(TAG , "写断开连接");
                mGatt.close();
                connectState = false;
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            super.onServicesDiscovered(gatt, status);
            Log.d(TAG , "发现服务状态=" + status);
          if(status == BluetoothGatt.GATT_SUCCESS){
              Log.d(TAG , "发现服务");
              getSupportedGattServices();
          }
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);
            Log.d(TAG , "写返回状态"+ status + "\n返回成功码" + BluetoothGatt.GATT_SUCCESS);
//            gatt.disconnect();
        }
    };
    public List<BluetoothGattService> getSupportedGattServices() {
        if (mGatt == null) return null;
        for(BluetoothGattService service : mGatt.getServices()){
            Log.d(TAG , "服务UUID=" + service.getUuid().toString());
                for(BluetoothGattCharacteristic characteristic : service.getCharacteristics()){
                    Log.d(TAG , "特征值UUID=" + characteristic.getUuid());

                   if(characteristic.getUuid().toString().equals("00002a06-0000-1000-8000-00805f9b34fb")){
                       Log.d(TAG , "写震动特征值");
                       characteristic.setValue(VIBRATION_WITHOUT_LED);
                       mGatt.writeCharacteristic(characteristic);
                       break;
                   }
                }
        }

//        BluetoothGattCharacteristic characteristic = mGatt.getService(Service_test).getCharacteristic(characteristic_test);
//        characteristic.setValue(VIBRATION_WITHOUT_LED);
//        mGatt.writeCharacteristic(characteristic);

        return mGatt.getServices();
    }
    private void saveBluetoothDevise(String deviseName , String deviseMac , int bondState){
             Map<String , Object> device = new HashMap<String , Object>();
             device.put("deviseName" , deviseName);
             device.put("deviseMac" , deviseMac);
             device.put("bondState" , bondState);
             deviceList.add(device);
        showText();
    }
    private void showText(){
//            mBluetoothAdapter.cancelDiscovery();
            mBlueToothRecyclerAdapter.notifyDataSetChanged();
            mBlueToothRecyclerAdapter.setOnItemClickListener(new BlueToothRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Log.d("onItemClick" , "返回位置=" + position);
                }
            });
    }

//    http://bendi.news.163.com/guangdong/special/robot/?from=groupmessage%28

}
