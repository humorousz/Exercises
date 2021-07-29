package com.humorusz.practice.shader.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;

import androidx.appcompat.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * @author zhangzhiquan
 * @date 2018/04/17
 */
public class PictureScrollView extends AppCompatImageView implements ValueAnimator.AnimatorUpdateListener{
    private ValueAnimator mAnimator;
    private int mDuration = 1500;
    private float mCurrentValue=0;
    public PictureScrollView(Context context) {
        this(context,null);
    }

    public PictureScrollView(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public PictureScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mAnimator = ValueAnimator.ofFloat(0,1);
        mAnimator.addUpdateListener(this);
        mAnimator.setDuration(mDuration);
        mAnimator.setRepeatCount(5);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.clipRect(0,0,getWidth()*mCurrentValue,getHeight());
        super.onDraw(canvas);
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        mCurrentValue = (float) animation.getAnimatedValue();
        postInvalidate();
    }
}
