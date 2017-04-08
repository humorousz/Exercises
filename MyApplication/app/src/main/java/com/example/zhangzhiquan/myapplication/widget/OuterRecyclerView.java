package com.example.zhangzhiquan.myapplication.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by zhangzhiquan on 2017/4/4.
 */

public class OuterRecyclerView extends RecyclerView {

    public OuterRecyclerView(Context context) {
        this(context,null);
    }

    public OuterRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public OuterRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("MRZ","outer dispatch");
        return super.dispatchTouchEvent(ev);
    }


}
