package com.humorusz.practice.nested.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Parcelable;
import androidx.core.view.NestedScrollingParent;
import androidx.core.view.NestedScrollingParentHelper;
import androidx.core.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.OverScroller;

import com.humorousz.commonutils.log.Logger;
import com.humorusz.practice.R;

/**
 * Created by zhangzhiquan on 2017/6/12.
 * 可以上滑悬停的LinearLayout，使用NestedScrolling方式实现
 */

public class NestedLinearLayout extends LinearLayout implements NestedScrollingParent {
    private static final String TAG = "NestedRelativeLayout";
    private NestedScrollingParentHelper mHelper;
    private View mTop;
    private View mContent;
    private View mSticky;
    private OverScroller mScroller;
    private ValueAnimator mOffsetAnimator;
    private Interpolator mInterpolator;

    public NestedLinearLayout(Context context) {
        this(context, null);
    }

    public NestedLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public NestedLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mHelper = new NestedScrollingParentHelper(this);
        mScroller = new OverScroller(context);
        mInterpolator = new DecelerateInterpolator();
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        if (mTop == null) {
            mTop = findViewById(R.id.nested_top);
        }
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL && mTop != null;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int axes) {
        super.onNestedScrollAccepted(child, target, axes);
        mHelper.onNestedScrollAccepted(child, target, axes);
    }

    @Override
    public void onStopNestedScroll(View child) {
        mHelper.onStopNestedScroll(child);
    }


    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {

        boolean hiddenTop = dy > 0 && getScrollY() < mTop.getHeight();
        boolean showTop = dy < 0 && getScrollY() > 0 && !ViewCompat.canScrollVertically(target, -1);
        if (hiddenTop || showTop) {
            scrollBy(0, dy);
            consumed[1] = dy;
        }
    }


    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        if (!consumed) {
            animateScroll(velocityY, computeDuration(0),true);
        } else {
            animateScroll(velocityY, computeDuration(velocityY));
        }
        return true;
    }

    /**
     * 根据速度计算滚动动画持续时间
     * @param velocityY
     * @return
     */
    private int computeDuration(float velocityY) {
        final int distance;
        if (velocityY > 0) {
            distance = Math.abs(mTop.getHeight() - getScrollY());
        } else {
            distance = Math.abs(mTop.getHeight() - (mTop.getHeight() - getScrollY()));
        }


        final int duration;
        velocityY = Math.abs(velocityY);
        if (velocityY > 0) {
            duration = 3 * Math.round(1000 * (distance / velocityY));
            Logger.d(TAG,"duration:"+duration);
        } else {
            final float distanceRatio = (float) distance / getHeight();
            duration = (int) ((distanceRatio + 1) * 150);
        }

        return duration;

    }


    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        //返回false表示不拦截
        return false;
    }

    @Override
    public void scrollTo(int x, int y) {
        if (y < 0) {
            y = 0;
        }
        if (y > mTop.getHeight()) {
            y = mTop.getHeight();
        }
        if (y != getScrollY()) {
            super.scrollTo(x, y);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        getChildAt(0).measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        //重新计算NestedScrolling高度，总高度-悬浮高度
        ViewGroup.LayoutParams params = mContent.getLayoutParams();
        params.height = getMeasuredHeight() - mSticky.getMeasuredHeight();
        setMeasuredDimension(getMeasuredWidth(), mTop.getMeasuredHeight() + mContent.getMeasuredHeight()
                + mSticky.getMeasuredHeight());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTop = findViewById(R.id.nested_top);
        mContent = findViewById(R.id.nested_content);
        mSticky = findViewById(R.id.nested_sticky);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(0, mScroller.getCurrY());
        }
    }

    public void fling(int velocityY) {
        mScroller.fling(0, getScrollY(), 0, velocityY, 0, 0, 0, mTop.getHeight());
        invalidate();
    }

    public void animateScroll(float velocityY,final int duration){
        animateScroll(velocityY,duration,false);
    }

    public void animateScroll(float velocityY, final int duration,boolean down) {
        final int currentOffset = getScrollY();
        final int topHeight = mTop.getHeight();
        if (mOffsetAnimator == null) {
            mOffsetAnimator = new ValueAnimator();
            mOffsetAnimator.setInterpolator(mInterpolator);
            mOffsetAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    if (animation.getAnimatedValue() instanceof Integer) {
                        scrollTo(0, (Integer) animation.getAnimatedValue());
                    }
                }
            });
        } else {
            mOffsetAnimator.cancel();
        }
        mOffsetAnimator.setDuration(Math.min(duration, 600));
        if (velocityY >= 0) {
            mOffsetAnimator.setIntValues(currentOffset, topHeight);
            mOffsetAnimator.start();
        } else {
            mOffsetAnimator.setIntValues(currentOffset, 0);
            if(down){
                mOffsetAnimator.start();
            }
        }
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(null);
        Logger.d(TAG,"onRestoreInstanceState");
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Logger.d(TAG,"onSaveInstanceState");
        return super.onSaveInstanceState();
    }
}
