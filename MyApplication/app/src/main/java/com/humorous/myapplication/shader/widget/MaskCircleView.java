package com.humorous.myapplication.shader.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zhangzhiquan on 2017/12/17.
 */

public class MaskCircleView extends View {
    private Paint mPaint;
    private Shader mShader;
    private int[] mColors;
    private float[] mPositions;
    private ValueAnimator animator;
    private float precent;
    private Paint mTransparentPaint;
    public MaskCircleView(Context context) {
        this(context,null);
    }

    public MaskCircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public MaskCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mTransparentPaint = new Paint();
        mTransparentPaint.setColor(Color.parseColor("#3d000000"));
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        post(()->{
            animator = ValueAnimator.ofFloat(0, 1);
            animator.setDuration(1500);
            animator.setRepeatMode(ValueAnimator.RESTART);
            animator.setRepeatCount(ValueAnimator.INFINITE);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    precent = animation.getAnimatedFraction();
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

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mShader == null) {
            if (mColors == null) {
                mColors = new int[]{Color.TRANSPARENT, Color.parseColor("#02000000"), Color.parseColor("#000000"), Color.parseColor("#02000000"), Color.TRANSPARENT};
            }
            if (mPositions == null) {
                mPositions = new float[]{0.3f, 0.4f, 0.5f, 0.6f, 0.7f};
            }
            mShader = new LinearGradient(0, 0, getWidth(), getHeight(), mColors, mPositions, Shader.TileMode.REPEAT);
        }
        int id = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2, mTransparentPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        mPaint.setStrokeWidth(30);
        int startX = (int) (precent <= 0.5f ? precent * 2 * getWidth() : (precent - 0.5) * 2 * getWidth());
        int startY = precent > 0.5 ? getHeight() : 0;
        int endX = precent > 0.5f ? getWidth() : 0;
        int endY = (int) (precent <= 0.5f ? precent * 2 * getHeight() : (precent - 0.5) * 2 * getHeight());
        canvas.drawLine(startX, startY, endX, endY, mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(id);
    }
}
