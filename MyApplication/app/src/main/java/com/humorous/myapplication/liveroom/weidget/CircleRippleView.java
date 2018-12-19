package com.humorous.myapplication.liveroom.weidget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.humorous.myapplication.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by zhangzhiquan on 2017/11/22.
 */

public class CircleRippleView extends View {
  private Context mContext;
  private int mCircleColor;
  private Paint mPaint;
  private float mMinRadius;
  private float mMaxRadius;
  private long mEnlargedDuration = 1000;
  private long mCreateCircleSpeed = 500;
  private List<Circle> mCircleList = new ArrayList<>();
  private boolean mInterrupt;
  private long mLastCreateTime;
  private Interpolator mInterpolator = new LinearInterpolator();
  private Runnable mCreateCircleRunnable = new Runnable() {
    @Override
    public void run() {
      if (!mInterrupt) {
        createCircle();
        postDelayed(mCreateCircleRunnable, mCreateCircleSpeed);
      }
    }
  };

  public CircleRippleView(Context context) {
    this(context, null);
  }

  public CircleRippleView(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, -1);
  }

  public CircleRippleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    mContext = context;
    initAttr(attrs, defStyleAttr);
    mPaint = new Paint();
    mPaint.setAntiAlias(true);
    mPaint.setColor(mCircleColor);
  }

  private void createCircle() {
    long currentTime = System.currentTimeMillis();
    if (currentTime - mLastCreateTime < mCreateCircleSpeed) {
      return;
    }
    Circle circle = new Circle();
    mCircleList.add(circle);
    invalidate();
    mLastCreateTime = currentTime;
  }

  private void initAttr(AttributeSet attrs, int defStyleAttr) {
    TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.BreathingLampView, defStyleAttr, 0);
    mMinRadius = a.getDimension(R.styleable.BreathingLampView_smallRadius, 0) / 2;
    mMaxRadius = a.getDimension(R.styleable.BreathingLampView_bigRadius, 0) / 2;
    mCircleColor = a.getColor(R.styleable.BreathingLampView_circleColor, Color.YELLOW);
    a.recycle();
  }

  @Override
  protected void onDraw(Canvas canvas) {
    Iterator<Circle> iterator = mCircleList.iterator();
    while (iterator.hasNext()) {
      Circle circle = iterator.next();
      float radius = circle.getCurrentRadius();
      if (System.currentTimeMillis() - circle.mCreateTime < mEnlargedDuration) {
        mPaint.setAlpha(circle.getAlpha());
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius, mPaint);
      } else {
        iterator.remove();
      }
    }
    if (mCircleList.size() > 0) {
      invalidate();
    }
  }


  @Override
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    startAnim();
  }

  @Override
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    stopAnim();
  }


  public void setInterpolator(Interpolator interpolator) {
    mInterpolator = interpolator;
    if (mInterpolator == null) {
      mInterpolator = new LinearInterpolator();
    }
  }

  public void startAnim() {
    setVisibility(VISIBLE);
    mInterrupt = false;
    post(mCreateCircleRunnable);
  }

  private void stopAnim() {
    mInterrupt = true;
  }

  private class Circle {
    private long mCreateTime;

    Circle() {
      mCreateTime = System.currentTimeMillis();
    }

    int getAlpha() {
      float percent = (getCurrentRadius() - mMinRadius) / (mMaxRadius - mMinRadius);
      return (int) (255 - mInterpolator.getInterpolation(percent) * 255);
    }

    float getCurrentRadius() {
      float percent = (System.currentTimeMillis() - mCreateTime) * 1.0f / mEnlargedDuration;
      return mMinRadius + mInterpolator.getInterpolation(percent) * (mMaxRadius - mMinRadius);
    }
  }
}
