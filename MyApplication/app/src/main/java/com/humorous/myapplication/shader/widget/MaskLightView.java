package com.humorous.myapplication.shader.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

/**
 * Created by zhangzhiquan on 2017/12/17.
 * @author zhangzhiquan
 */

public class MaskLightView extends View {
    private Paint mPaint;
    private Shader mShader;
    private ValueAnimator mTranslateAnimator;
    private float mTranslate = 0;
    private int[] mColors;
    private float[] mPositions;
    public MaskLightView(Context context) {
        this(context,null);
    }

    public MaskLightView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public MaskLightView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        post(new Runnable() {
            @Override
            public void run() {
                init();
            }
        });
    }

    private void init(){
        mTranslateAnimator = ValueAnimator.ofFloat(-getWidth(),getWidth());
        mTranslateAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float v = (float) animation.getAnimatedValue();
                mTranslate = v;
                invalidate();
            }
        });
        mTranslateAnimator.setDuration(1500);
        mTranslateAnimator.setInterpolator(new AccelerateInterpolator());
        mTranslateAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mTranslateAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 设置shader
        if (mShader == null) {
            mColors = new int[]{Color.TRANSPARENT, Color.parseColor("#02ffffff"), Color.parseColor("#22ffffff"), Color.parseColor("#02ffffff"), Color.TRANSPARENT};
            mPositions = new float[]{0.3f, 0.4f, 0.5f, 0.6f, 0.7f};
            mShader = new LinearGradient(0, 0, getWidth(), getHeight(), mColors, mPositions, Shader.TileMode.REPEAT);
            mPaint.setShader(mShader);
        }
        canvas.translate(mTranslate,0);
        canvas.drawRect(0,0,getWidth(),canvas.getHeight(), mPaint);
    }
}
