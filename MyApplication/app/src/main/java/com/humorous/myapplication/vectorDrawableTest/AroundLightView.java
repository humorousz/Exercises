package com.humorous.myapplication.vectorDrawableTest;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;


/**
 * Created by zhangzhiquan on 2017/10/29.
 */

public class AroundLightView extends View {
    private static final int ANIM_TIME = 1000;
    private static final int DELAY_TIME = 1000;
    // 路径measure
    private PathMeasure mPathMeasure ;
    //整体路径
    private Path mParentPath ;
    //整体路径矩形
    private RectF mRectF;
    //画笔
    private Paint mPaint;
    //子路径的起始值
    private float mAnimatorV;
    private  float mEndValue;
    //子路径
    private Path mSubPath;

    private int mRectFWidth;
    private int mRectFHeight;
    private int mRoundRadius = 50;
    private float mPointX,mPointY;
    private float mTotalEndV;
    ValueAnimator animator;
    ValueAnimator animator2;
    public AroundLightView(Context context) {
        this(context,null);
    }

    public AroundLightView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AroundLightView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init(){
        mPaint= new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.WHITE);
        mAnimatorV = 1.0f;
        mTotalEndV = 100f;
        mEndValue = mTotalEndV;
        mSubPath = new Path();
        animator = ObjectAnimator.ofFloat(1.0f,0.0f);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimatorV = (float) animator.getAnimatedValue();
                invalidate();
            }
        });
        animator2 = ObjectAnimator.ofFloat(0,mTotalEndV,0,0,mTotalEndV,0);
        animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mEndValue = (float) animation.getAnimatedValue();
            }
        });
        animator.setDuration(ANIM_TIME);
        animator2.setDuration(ANIM_TIME);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator2.setRepeatCount(ValueAnimator.INFINITE);
        animator.setStartDelay(DELAY_TIME);
        animator2.setStartDelay(DELAY_TIME);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mParentPath == null){
            mRectFWidth = getMeasuredWidth();
            mRectFHeight = getMeasuredHeight();
            mRectF = new RectF(getPaddingLeft(), getPaddingTop()
                    , mRectFWidth - getPaddingRight()
                    , mRectFHeight - getPaddingBottom());
            mParentPath = new Path();
            mParentPath.addRoundRect(mRectF,mRoundRadius,mRoundRadius,Path.Direction.CW);
            mPathMeasure = new PathMeasure();
            mPathMeasure.setPath(mParentPath,false);
            mPointX = (getMeasuredWidth() - getPaddingLeft() - getPaddingRight()) / 2 + getPaddingLeft();
            mPointY = (getMeasuredHeight() - getPaddingTop() - getPaddingBottom()) / 2 + getPaddingBottom();

        }
        float start = mPathMeasure.getLength() * mAnimatorV;
        float end = start + mEndValue;
        mSubPath.reset();
        mSubPath.lineTo(0,0);
        mPathMeasure.getSegment(start, end, mSubPath, true);
        canvas.rotate(180f,mPointX,mPointY);
        canvas.drawPath(mSubPath,mPaint);
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        animator.start();
        animator2.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        animator.cancel();
        animator2.cancel();
    }
}
