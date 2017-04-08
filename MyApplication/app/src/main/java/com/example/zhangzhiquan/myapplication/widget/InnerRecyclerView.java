package com.example.zhangzhiquan.myapplication.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by zhangzhiquan on 2017/4/4.
 */

public class InnerRecyclerView extends RecyclerView{
    private int mLastX = 0;
    private int mLastY = 0;
    private boolean hasDispath = false;

    private OuterRecyclerView parent;

    public InnerRecyclerView(Context context) {
        super(context);
    }

    public InnerRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public InnerRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setParent(OuterRecyclerView parent) {
        this.parent = parent;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();

        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.e("MRZ","move 1");
                hasDispath = false;
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                if(isDispath(x,y)){
                    Log.e("MRZ","move 2");
//                getParent().requestDisallowInterceptTouchEvent(true);
                    hasDispath = true;
                }else {
                    Log.e("MRZ","move 3");
                    if(hasDispath){
                        getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                    }
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            default:
                break;
        }
        mLastX = x;
        mLastY = y;
        return super.dispatchTouchEvent(ev);
    }


    private boolean isDispath(int x,int y){
        float deltax = x - mLastX;
        float deltay = y - mLastY;
        if(Math.abs(deltax)>Math.abs(deltay)){
            return false;
        }

        LayoutManager manager = getLayoutManager();
        if(!(manager instanceof LinearLayoutManager)){
            return false;
        }
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) manager;
        if(linearLayoutManager.getOrientation() == OrientationHelper.HORIZONTAL){
            return false;
        }

        if(deltay < 0 && linearLayoutManager.findLastVisibleItemPosition() != linearLayoutManager.getItemCount() -1){
            return true;
        }
        if(deltay >0 && linearLayoutManager.findFirstVisibleItemPosition() != 0){
            return true;
        }
        Log.e("MRZ111","move ddddd");
        return false;
    }
}
