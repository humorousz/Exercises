package com.humorous.myapplication.coordinatorTest.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;

import com.humorousz.commonutils.log.Logger;

/**
 * Created by zhangzhiquan on 2017/6/18.
 */

public class TestRecyclerView extends RecyclerView {
    public TestRecyclerView(Context context) {
        super(context);
    }

    public TestRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    float lastX,lastY;
    boolean isFirst;
    boolean interceptMove;
    private VelocityTracker vTracker = null;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        final int action = ev.getAction() & MotionEventCompat.ACTION_MASK;
        boolean intercept = false;
        switch (action){
            case MotionEvent.ACTION_DOWN:
                lastX = ev.getX();
                lastY = ev.getY();
                intercept = false;
                isFirst = true;
                interceptMove = true;
                if(vTracker == null){
                    vTracker = VelocityTracker.obtain();
                }else {
                    vTracker.clear();
                }
                vTracker.addMovement(ev);
                break;
            case MotionEvent.ACTION_MOVE:
                float curX = ev.getX();
                float curY = ev.getY();
                float diffX = Math.abs(curX - lastX);
                float diffY = Math.abs(curY - lastY);
                vTracker.addMovement(ev);
                vTracker.computeCurrentVelocity(1000);
                if(Math.abs(vTracker.getXVelocity()) < Math.abs(vTracker.getYVelocity())){
                    if(isFirst){
                        isFirst = false;
                        interceptMove = true;
                    }
                    getParent().requestDisallowInterceptTouchEvent(interceptMove);
                }else {
                    if(isFirst){
                        isFirst = false;
                        interceptMove = false;
                    }
                    getParent().requestDisallowInterceptTouchEvent(interceptMove);
                }
                Logger.d("MRZQ","interceptMove:"+interceptMove);
                lastX = ev.getX();
                lastY = ev.getY();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        return super.onInterceptTouchEvent(e);
    }
}
