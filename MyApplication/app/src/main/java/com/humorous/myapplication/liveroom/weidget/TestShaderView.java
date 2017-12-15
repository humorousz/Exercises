package com.humorous.myapplication.liveroom.weidget;

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

import com.humorousz.uiutils.helper.UIUtils;


/**
 * Created by zhangzhiquan on 2017/12/14.
 * @author zhangzhiquan
 */

public class TestShaderView extends View {
    private Paint mPaint;
    private Shader mShader;
    private ValueAnimator mTranslateAnimator;
    private float mTranslate = 1000;

    public TestShaderView(Context context) {
        this(context,null);
    }

    public TestShaderView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public TestShaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mPaint = new Paint();
        mTranslateAnimator = ValueAnimator.ofFloat(0, UIUtils.dip2px(250));
        mTranslateAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float v = (float) animation.getAnimatedValue();
                mTranslate = v;
                invalidate();
            }
        });
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mTranslateAnimator.setInterpolator(new DecelerateInterpolator());
        mTranslateAnimator.setDuration(500);
        mTranslateAnimator.setRepeatCount(5);
        mTranslateAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 设置shader
        if(mShader == null){
            mShader = new LinearGradient(0,0,canvas.getWidth()/4, canvas.getHeight() ,
                    new int[] {Color.parseColor("#22000000"),Color.parseColor("#55000000"),Color.parseColor("#22000000")},
                    null,Shader.TileMode.REPEAT);
        }
        mPaint.setShader(mShader);
        Matrix matrix = new Matrix();
//        matrix.setSkew(-0.5f,0,canvas.getWidth()/2,canvas.getHeight()/2);
//        canvas.translate(mTranslate,0);
        matrix.setTranslate(0,mTranslate);
        canvas.setMatrix(matrix);
        canvas.drawRect(0,-2000, canvas.getWidth(), canvas.getHeight()*10, mPaint);
    }
}
