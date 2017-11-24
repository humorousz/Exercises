package com.humorous.myapplication.liveroom.weidget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.humorous.myapplication.R;

/**
 * Created by zhangzhiquan on 2017/11/22.
 */

public class BreathingLampView extends View {
    private Paint mPaint;
    private Context mContext;
    private float mSmallRadius;
    private float mBigRadius;
    private float mCurRadius;
    private float mHollowRadius;
    private int mCircleColor;
    private ValueAnimator mAlphaAnimator,mRadiusAnimator;
    private AnimatorSet mAnimatorSet;
    private int mDuration;
    private int mRepeatCount;
    public BreathingLampView(Context context) {
        this(context,null);
    }

    public BreathingLampView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public BreathingLampView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initAttr(attrs,defStyleAttr);
        mPaint = new Paint();
        mPaint.setColor(mCircleColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(0);
        mCurRadius = mSmallRadius;
    }

    private void initAttr(AttributeSet attrs,int defStyleAttr){
        TypedArray a  = mContext.obtainStyledAttributes(attrs, R.styleable.BreathingLampView,defStyleAttr,0);
        mSmallRadius = a.getDimension(R.styleable.BreathingLampView_smallRadius,0);
        mBigRadius = a.getDimension(R.styleable.BreathingLampView_bigRadius,0);
        mHollowRadius = a.getDimension(R.styleable.BreathingLampView_hollowRadius,0);
        mCircleColor = a.getColor(R.styleable.BreathingLampView_circleColor,Color.YELLOW);
        mDuration = a.getInt(R.styleable.BreathingLampView_animationTime,500);
        mRepeatCount = a.getInt(R.styleable.BreathingLampView_repeatCount,3);
        a.recycle();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(getWidth()/2,getHeight()/2,mCurRadius,mPaint);
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAnim();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if(mRadiusAnimator != null && mRadiusAnimator.isRunning()){
            mRadiusAnimator.cancel();
        }
        if(mAlphaAnimator != null && mAlphaAnimator.isRunning()){
            mAlphaAnimator.cancel();
        }
        if( mAnimatorSet != null && mAnimatorSet.isRunning()){
            mAnimatorSet.cancel();
        }
        mAnimatorSet = null;
        mAlphaAnimator = null;
        mRadiusAnimator = null;
    }

    public void startAnim(){
        setVisibility(VISIBLE);
        mAlphaAnimator = ValueAnimator.ofFloat(1,0.1f,0.1f,1);
        mAlphaAnimator.setRepeatCount(mRepeatCount);
        mAlphaAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                setAlpha((Float) valueAnimator.getAnimatedValue());
            }
        });
        mRadiusAnimator = ValueAnimator.ofFloat(mSmallRadius,mBigRadius,mBigRadius,mSmallRadius);
        mRadiusAnimator.setRepeatCount(mRepeatCount);
        mRadiusAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mCurRadius = (float) valueAnimator.getAnimatedValue();
                mPaint.setStrokeWidth(mCurRadius - mHollowRadius);
                invalidate();
            }
        });
        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.setDuration(mDuration);
        mAnimatorSet.playTogether(mAlphaAnimator,mRadiusAnimator);
        mAnimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                setVisibility(GONE);
            }
        });
        mAnimatorSet.start();
    }
}
