package com.humorous.myapplication.vectorDrawableTest;

import android.animation.Animator;
import android.animation.AnimatorSet;
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

import com.humorousz.uiutils.helper.UIUtils;


/**
 * Created by zhangzhiquan on 2017/10/29.
 */

public class AroundLightView extends View implements Animator.AnimatorListener {
    private static final int ANIM_TIME = 600;
    private static final int DELAY_TIME = 400;
    private static final int COUNT = 2;
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

    private float lastStart;

    private int mRectFWidth;
    private int mRectFHeight;
    private int mRoundRadius = 50;
    private float mPointX,mPointY;
    private float mTotalEndV;
    private ValueAnimator animator;
    private ValueAnimator animator2;
    private AnimatorSet mAnimatorSet;
    private int step = 0;
    private boolean isRemove = false;
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
        mTotalEndV = UIUtils.dip2px(getContext(),30);
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
        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.playTogether(animator,animator2);
        mAnimatorSet.addListener(this);
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
        if(end != start && end <= lastStart)
            end = lastStart ;
        lastStart = start;
        mSubPath.reset();
        mSubPath.lineTo(0,0);
        mPathMeasure.getSegment(start, end, mSubPath, true);
        canvas.rotate(180f,mPointX,mPointY);
        canvas.drawPath(mSubPath,mPaint);
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mAnimatorSet.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        isRemove = true;
        mAnimatorSet.cancel();
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        if(step < COUNT && !isRemove){
            step++;
            mAnimatorSet.playTogether(animator,animator2);
            mAnimatorSet.setStartDelay(DELAY_TIME);
            mAnimatorSet.start();
        }
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }
}
