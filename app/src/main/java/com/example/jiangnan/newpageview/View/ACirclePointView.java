package com.example.jiangnan.newpageview.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Scroller;

import com.example.jiangnan.newpageview.R;

/**
 * Created by jiangnan on 2018/3/17.
 */

public class ACirclePointView extends View {
    private Paint mpaint;
    private int color;
    private Context mContext;
    Scroller mScroller;

    public ACirclePointView(Context context){
        super(context);
        mContext = context;
        init();
    }
    public ACirclePointView(Context context , AttributeSet attributeSet){
        super(context , attributeSet);
        mContext = context;
        init();
    }
    public ACirclePointView(Context context , AttributeSet attributeSet , int defStyleAtts){
        super(context , attributeSet , defStyleAtts);
        mContext = context;
        init();
    }
    private void init(){
        color = R.color.cheng;
        mpaint = new Paint();
        mScroller = new Scroller(mContext);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int heith = getHeight();
        Log.d("View" , "上=" + getTop() + "下=" + getBottom() + "\n左=" + getLeft() + "右=" + getRight());
        Log.d("View" , "宽=" + getWidth() + "高=" + getHeight());

        int radio = Math.min(width , heith);
        mpaint.setColor(ContextCompat.getColor(mContext, color));

        mpaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(width / 2, heith / 2 , radio / 2 , mpaint);//半径等于宽和高的一半
    }
    public void setChecked(boolean checked){
        if(checked){
            color = R.color.cheng;
            invalidate();
        }else {
            color = R.color.color_null;
            invalidate();
        }
    }
    public void smoothScrollTo(int desX , int desY){
        int scrollX = getScrollX();
        int scrollY = getScrollY();

        int deTaX = desX - scrollX;
        int deTaY = desY - scrollY;
        mScroller.startScroll(scrollX , scrollY , deTaX , deTaY , 3000);
        invalidate();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX() , mScroller.getCurrY());
            postInvalidate();
        }
    }
}
