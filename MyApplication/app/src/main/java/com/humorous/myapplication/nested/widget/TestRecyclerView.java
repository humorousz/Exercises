package com.humorous.myapplication.nested.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

import com.humorousz.commonutils.log.Logger;

/**
 * Created by zhangzhiquan on 2017/6/14.
 */

public class TestRecyclerView extends RecyclerView {
    private static final String TAG = "TestRecyclerView";
    public TestRecyclerView(Context context) {
        super(context);
    }

    public TestRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    public boolean fling(final int velocityX, final int velocityY) {
        final boolean fling = super.fling(velocityX,velocityY);
        int time = Math.abs(velocityY) / 4000 ;
        time = time > 4 ? 4 : time;
        Logger.d(TAG,"time:"+time);
        getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(fling && isTop()){
                    startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL);
                    dispatchNestedFling(velocityX,velocityY,false);
                    stopNestedScroll();
                }

            }
        },time * 100);
        return fling;
    }



    private boolean isTop(){
        LayoutManager manager = getLayoutManager();
        if(manager instanceof StaggeredGridLayoutManager){
            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) manager;
            int[] pos = staggeredGridLayoutManager.findFirstCompletelyVisibleItemPositions(null);
            if(pos.length != 0){
                Logger.d(TAG,"pos0:"+pos[0]+" pos1:"+pos[1]);
                return pos[0] == 0 || pos[1] == 0;
            }
        }
        return false;
    }




}
