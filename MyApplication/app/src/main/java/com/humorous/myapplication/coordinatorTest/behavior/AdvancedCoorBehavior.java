package com.humorous.myapplication.coordinatorTest.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.humorousz.commonutils.log.Logger;

/**
 * Created by zhangzhiquan on 2017/6/9.
 */

public class AdvancedCoorBehavior extends CoordinatorLayout.Behavior<View> {
    private static final String TAG = "AdvancedCoorBehavior";

    public AdvancedCoorBehavior(Context context, AttributeSet attr){
        super(context,attr);
    }
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        Logger.d(TAG,"onStartNestedScroll");
        Logger.d(TAG,"child:"+child.getClass().getSimpleName());
        Logger.d(TAG,"directTargetChild:"+directTargetChild.getClass().getSimpleName());
        Logger.d(TAG,"Target:"+target.getClass().getSimpleName());
        Logger.d(TAG,"nestedScrollAxes:"+nestedScrollAxes);
        Logger.d(TAG,"------------end-----------");
        final boolean started = (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;

        return started;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
//        Logger.d(TAG,"onNestedPreScroll");
//        Logger.d(TAG,"Target:"+target.getClass().getSimpleName());
//        Logger.d(TAG,"dx:"+dx);
//        Logger.d(TAG,"dy:"+dy);
//        Logger.d(TAG,"consumed[0]:"+consumed[0]);
//        Logger.d(TAG,"consumed[1]:"+consumed[1]);
//        Logger.d(TAG,"Child:"+child.getY());
//        Logger.d(TAG,"Child Height:"+child.getHeight());
//        Logger.d(TAG,"------------end-----------");

        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
    }

    private void scrollView(View child,int dy,int[] consumed){
        if(dy > 0){
            int y = (int) Math.abs(child.getY());
            int cdy = child.getHeight() - y;
            if(cdy > 0){
                int scrolly = dy > cdy ? cdy : dy;
                ViewCompat.offsetTopAndBottom(child,-scrolly);
                consumed[1] = dy > cdy ? dy - cdy : 0;
            }
        }

        if(dy < 0) {
            int y = (int) Math.abs(child.getY());
            if(y>0){
                int scrollY = Math.abs(dy) > y ? y : Math.abs(dy);
                ViewCompat.offsetTopAndBottom(child,scrollY);
                consumed[1] = Math.abs(dy) > y ? -y : -Math.abs(dy - y);
            }
        }
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
//        Logger.d(TAG,"onNestedScroll");
//        Logger.d(TAG,"Target:"+target.getClass().getSimpleName());
//        Logger.d(TAG,"dxConsumed:"+dxConsumed);
//        Logger.d(TAG,"dyConsumed:"+dyConsumed);
//        Logger.d(TAG,"dxUnconsumed:"+dxUnconsumed);
//        Logger.d(TAG,"dyUnconsumed:"+dyUnconsumed);
//        Logger.d(TAG,"------------end-----------");

    }

    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, View child, View target, float velocityX, float velocityY) {
//        Logger.d(TAG,"onNestedPreFling");
        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY);
    }

    @Override
    public boolean onNestedFling(CoordinatorLayout coordinatorLayout, View child, View target, float velocityX, float velocityY, boolean consumed) {
//        Logger.d(TAG,"onNestedFling");
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
    }

//    @Override
//    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
//        Logger.d(TAG,"layoutDependsOn");
//        return dependency instanceof FrameLayout;
//    }
//
//    @Override
//    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
//        Logger.d(TAG,"onDependentViewChanged");
//        return super.onDependentViewChanged(parent, child, dependency);
//    }
}
