package com.humorous.myapplication.shader.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.humorous.myapplication.R;
import com.humorousz.uiutils.helper.UIUtils;


/**
 * Created by zhangzhiquan on 2017/12/14.
 * @author zhangzhiquan
 */

public class TestShaderView extends View {
    private Paint mPaint;
    private Shader mShader;
    private ValueAnimator mTranslateAnimator;
    private float mTranslate = 0;

    public TestShaderView(Context context) {
        this(context,null);
    }

    public TestShaderView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public TestShaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        mTranslateAnimator = ValueAnimator.ofFloat(-getWidth()*2,0);
        mTranslateAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float v = (float) animation.getAnimatedValue();
                mTranslate = v;
                invalidate();
            }
        });
        mTranslateAnimator.setDuration(2000);
        mTranslateAnimator.setRepeatMode(ValueAnimator.RESTART);
        mTranslateAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mTranslateAnimator.start();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 设置shader
        if(mShader == null){
            mShader = new LinearGradient(0,0,getWidth(),0,
                    new int[] {Color.TRANSPARENT,Color.parseColor("#02000000"),Color.parseColor("#05000000"),Color.parseColor("#02000000"),Color.TRANSPARENT},
                    new float[]{0.1f,0.15f,0.2f,0.3f,0.4f},Shader.TileMode.REPEAT);
        }
        mPaint.setShader(mShader);
        Matrix matrix = new Matrix();
        matrix.setSkew(-0.5f,0);
        canvas.setMatrix(matrix);
        canvas.translate(mTranslate,0);
        canvas.drawColor(getResources().getColor(R.color.color_8cfd9500));
        canvas.drawRect(canvas.getWidth()*-2,0,canvas.getWidth()*4,canvas.getHeight(), mPaint);
    }
}
