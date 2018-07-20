package com.example.jiangnan.newpageview.UI;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jiangnan.newpageview.Common.ServiceUUID;
import com.example.jiangnan.newpageview.Data.MemberData;
import com.example.jiangnan.newpageview.R;
import com.example.jiangnan.newpageview.View.Adapter.HorizontalScrollViewAdapter;
import com.example.jiangnan.newpageview.View.Adapter.MyRecyclerAdapter;
import com.example.jiangnan.newpageview.View.CircleImageView;
import com.example.jiangnan.newpageview.View.MyCircleImageView;
import com.example.jiangnan.newpageview.View.MyHorizontalScrollView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jiangnan on 2017/12/7.
 */

public class FragmentOneHorizontalScroView extends Fragment{

    private static final String TAG = "FragmentOne";
    private MyHorizontalScrollView mMyHorizontalScrollView;
    private LinearLayout mlinearLayout;
    private HorizontalScrollViewAdapter mHorizontalScrollViewAdapter;
    private HorizontalScrollView mhorizontalScrollView;
    private LinearLayout slinearLayout;
    private LayoutInflater mlayoutInflater;
    private List<Integer> mDatas;
    private RecyclerView mrecyclerView;
    private MyRecyclerAdapter MmyRecyclerAdapter;

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_one , container , false);
        intRes(view);
        return view;
    }

    private void intRes(View view){
        /*******************横向滑动组件，使用自定义horizontalScrollView实现滑动过程中内存回收*****************/
        mMyHorizontalScrollView = (MyHorizontalScrollView) view.findViewById(R.id.id_horizontalScrollView);
        mlinearLayout = (LinearLayout) view.findViewById(R.id.id_gallery);
        horizontalScrollViewByMySelf();
        /*******************横向滑动组件，使用系统horizontalScrollView实现滑动无法回收内存*****************/
        mhorizontalScrollView = (HorizontalScrollView) view.findViewById(R.id.horizontal_view);
        slinearLayout = (LinearLayout) view.findViewById(R.id.content_scroll);
        horizontalScrollViewBySystem();
        /*******************横向滑动组件，使用RecyclerView实现横向滑动****************************/
        mrecyclerView = (RecyclerView) view.findViewById(R.id.horizontal_recyclerview);
        LinearLayoutManager mlinearLayoutManager = new LinearLayoutManager(getActivity());
        mlinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mrecyclerView.setLayoutManager(mlinearLayoutManager);
        horizontalScrollViewByRecyclerView();
        /*********************************************************/
    }
    private void horizontalScrollViewByMySelf(){
        mDatas = new ArrayList<Integer>(Arrays.asList(
                R.drawable.my_bike, R.drawable.my_bike,
                R.drawable.my_bike, R.drawable.my_bike,
                R.drawable.my_bike, R.drawable.my_bike,
                R.drawable.my_bike, R.drawable.my_bike,
                R.drawable.my_bike, R.drawable.my_bike,
                R.drawable.my_bike, R.drawable.my_bike,
                R.drawable.my_bike, R.drawable.my_bike,
                R.drawable.my_bike, R.drawable.my_bike,
                R.drawable.my_bike, R.drawable.my_bike,
                R.drawable.my_bike));
        mHorizontalScrollViewAdapter = new HorizontalScrollViewAdapter(getActivity() , mDatas);
        mMyHorizontalScrollView.initDatas(mHorizontalScrollViewAdapter);
    }
    private void horizontalScrollViewBySystem(){
        mlayoutInflater = LayoutInflater.from(getActivity());
        for(int i = 0 ; i < mDatas.size() ; i ++ ){
            View view = mlayoutInflater.inflate(R.layout.item_horizontal_scrollview_my,
                    slinearLayout, false);
            MyCircleImageView imageView = (MyCircleImageView) view.findViewById(R.id.id_index_gallery_item_image);
            imageView.setImageResource(mDatas.get(i));
            TextView mtextView = (TextView) view.findViewById(R.id.id_index_gallery_item_text);
            mtextView.setText(getString(R.string.app_name));

            slinearLayout.addView(view);
        }
    }
    private void horizontalScrollViewByRecyclerView(){
        ArrayList<MemberData> memberData = new ArrayList<MemberData>();
        MemberData member = new MemberData();
        for (int i = 0 ; i <= 16 ; i ++){
            member.iconId = R.drawable.sea;
            member.nickName = getString(R.string.yellowflowerland);
            memberData.add(member);
        }
        MmyRecyclerAdapter = new MyRecyclerAdapter(getActivity() , memberData);
        mrecyclerView.setAdapter(MmyRecyclerAdapter);
//        Glide.with(getActivity()).load("").placeholder().into();
    }
}
