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
    private int mRoundRadius = 40;
    private float mPointX,mPointY;
    private float mTotalEndV;
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
        mPaint.setStrokeWidth(4);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mAnimatorV = 1.0f;
        mTotalEndV = 80f;
        mEndValue = mTotalEndV;
        mSubPath = new Path();
        final ValueAnimator animator = ObjectAnimator.ofFloat(1.0f,0.0f);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
//                mAnimatorV = (float) animator.getAnimatedValue();
                invalidate();
            }
        });
        final ValueAnimator animator2 = ObjectAnimator.ofFloat(0,mTotalEndV,0,0,mTotalEndV,0);
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
        post(new Runnable() {
            @Override
            public void run() {
                animator.start();
                animator2.start();
//                AnimatorSet set = new AnimatorSet();
//                set.setDuration(1000);
//                set.setInterpolator(new AccelerateDecelerateInterpolator());
//                set.playTogether(animator,animator2);
//                set.start();
            }
        });



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
                    , mRectFWidth - getPaddingLeft() - getPaddingRight()
                    , mRectFHeight - getPaddingTop() - getPaddingBottom());
            mParentPath = new Path();
            mParentPath.addRoundRect(mRectF,30,30,Path.Direction.CW);
            mPathMeasure = new PathMeasure();
            mPathMeasure.setPath(mParentPath,false);
            mPointX = (mRectF.right - mRectF.left)/2;
            mPointY = (mRectF.bottom - mRectF.top)/2;

        }
        float start = mPathMeasure.getLength() * mAnimatorV;
        float end = start + mEndValue;
        mSubPath.reset();
        mSubPath.lineTo(0,0);
        mPathMeasure.getSegment(start, end, mSubPath, true);
//        canvas.rotate(180f,mPointX,mPointY);
        canvas.drawPath(mParentPath,mPaint);

    }
}
