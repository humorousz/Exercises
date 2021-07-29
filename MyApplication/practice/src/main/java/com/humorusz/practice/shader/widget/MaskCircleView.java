package com.humorusz.practice.shader.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;


/**
 * Created by zhangzhiquan on 2017/12/17.
 */

public class MaskCircleView extends View {
    private static final String TAG = "MaskCircleView";
    private Paint mPaint;
    private Shader mShader;
    private int[] mColors;
    private float[] mPositions;
    private ValueAnimator animator;
    private float mCurrentPos=0;
    public MaskCircleView(Context context) {
        this(context,null);
    }

    public MaskCircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public MaskCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        post(()->{
            animator = ValueAnimator.ofFloat(-getWidth(),getWidth());
            animator.setDuration(3000);
            animator.setRepeatMode(ValueAnimator.RESTART);
            animator.setRepeatCount(ValueAnimator.INFINITE);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mCurrentPos = (float) animation.getAnimatedValue();
                    invalidate();
                }
            });
            animator.start();
        });
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if(animator != null ){
            animator.cancel();
        }
    }
    Matrix matrix = new Matrix();
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mShader == null) {
            if (mColors == null) {
                mColors = new int[]{Color.TRANSPARENT,Color.parseColor("#04DAA520"), Color.parseColor("#55DAA520"), Color.parseColor("#04DAA520"), Color.TRANSPARENT};
            }
            if (mPositions == null) {
                mPositions = new float[]{0.3f, 0.4f, 0.5f, 0.6f, 0.7f};
            }
            mShader = new LinearGradient(0, getHeight()/2, getWidth(), getHeight()/2, mColors, mPositions, Shader.TileMode.CLAMP);
            matrix.setTranslate(-getWidth(),0);
        }else {
            matrix.setTranslate(mCurrentPos,0);
        }
        mShader.setLocalMatrix(matrix);
        mPaint.setShader(mShader);
        canvas.rotate(-30,getWidth()/2,getHeight()/2);
        canvas.drawCircle(getWidth()/2,getHeight()/2,getWidth()/2,mPaint);
    }
}
