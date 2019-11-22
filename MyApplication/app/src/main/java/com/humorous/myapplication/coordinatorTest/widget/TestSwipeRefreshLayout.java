package com.humorous.myapplication.coordinatorTest.widget;

import android.content.Context;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.humorous.myapplication.R;
import com.humorousz.commonutils.log.Logger;

/**
 * Created by zhangzhiquan on 2017/5/31.
 */

public class TestSwipeRefreshLayout extends SwipeRefreshLayout {
    float lastX,lastY;
    private View adapter;
    public TestSwipeRefreshLayout(Context context) {
        super(context);
        init();
    }

    public TestSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        adapter = findViewById(R.id.test_viewpager);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(adapter != null){
            Logger.d("MRZ",adapter.getScrollY());
        }
      return  super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean dispatch = false;
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastX = ev.getX();
                lastY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float curX = ev.getX();
                float curY = ev.getY();
                float dx = Math.abs(lastX - curX);
                float dy = Math.abs(lastY - curY);
                if(Float.compare(dx,dy)<0){
                    Logger.d("MRZ","dddddddd");
                }else {
                   return false;
                }
                lastX = ev.getX();
                lastY = ev.getY();
                break;
            case MotionEvent.ACTION_UP:
                lastX = ev.getX();
                lastY = ev.getY();
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
