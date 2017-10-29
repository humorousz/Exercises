package com.humorous.myapplication.vectorDrawableTest;

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
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;


/**
 * Created by zhangzhiquan on 2017/10/29.
 */

public class AroundLightView extends View {
    private static final String TAG = "AroundLightView";
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

    PathMeasure measure ;
    Path path ;
    RectF rectF;

    Paint paint;
    float mAnimatorV;
    float endV;
    Path dst;
    private void init(){
        measure = new PathMeasure();
        path = new Path();
        rectF = new RectF(100,100,300,200);
        paint= new Paint();
        path.addRoundRect(rectF,50,50,Path.Direction.CW);
        measure.setPath(path,false);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);
        paint.setColor(Color.parseColor("#55ffffff"));
        mAnimatorV = 1.0f;
        endV = 80f;
        dst = new Path();
        final ValueAnimator animator = ObjectAnimator.ofFloat(1.0f,0.0f);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimatorV = (float) animator.getAnimatedValue();
                invalidate();
            }
        });

        final ValueAnimator animator2 = ObjectAnimator.ofFloat(0,80,0,0,80,0);
        animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                endV = (float) animation.getAnimatedValue();
            }
        });


        animator.setDuration(800);
        animator2.setDuration(800);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
//        animator2.setInterpolator(new AccelerateDeceleLirateInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator2.setRepeatCount(ValueAnimator.INFINITE);
        animator.setStartDelay(1000);
        animator2.setStartDelay(1000);
        postDelayed(new Runnable() {
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
        },1000);



    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float start = measure.getLength() * mAnimatorV;
        float end = start + endV;
        dst.reset();
        dst.lineTo(0,0);
        measure.getSegment(start, end, dst, true);
        canvas.rotate(180f,200,150);
        canvas.drawPath(dst,paint);

    }
}
