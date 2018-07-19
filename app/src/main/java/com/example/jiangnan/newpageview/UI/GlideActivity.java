package com.example.jiangnan.newpageview.UI;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.jiangnan.newpageview.R;
import com.example.jiangnan.newpageview.View.MyCircleImageView;

/**
 * Created by jiangnan on 2018/4/14.
 */

public class GlideActivity extends AppCompatActivity {
    private ImageView mimageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);
        intRes();
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }

    private void intRes(){
        mimageView = findViewById(R.id.image_glide);
        GlideModule();
    }
    private void GlideModule(){
        String url = "http://img1.dzwww.com:8080/tupian_pl/20150813/16/7858995348613407436.jpg";
        String gifUrl = "http://i2.mhimg.com/M00/0E/AE/CgAAilTPWJ2Aa_EIACcMxiZi5xE299.gif";
        Glide.with(this).load(url).placeholder(R.drawable.my_bike).into(mimageView);
    }
}
