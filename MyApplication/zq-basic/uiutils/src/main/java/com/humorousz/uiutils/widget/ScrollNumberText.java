package com.humorousz.uiutils.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import androidx.annotation.IntRange;

public class ScrollNumberText extends View {

  public static final String TAG = "ScrollNumber";
  /**
   * default animation velocity
   */
  public static final int DEFAULT_VELOCITY = 15;
  private final Paint mPaint;
  private final Rect mTextBounds = new Rect();
  /**
   * number to - number from
   */
  private int mDeltaNum;
  /**
   * the current showing number
   */
  private int mCurNum;
  /**
   * the next showing number
   */
  private int mNextNum;
  /**
   * the target number
   */
  private int mTargetNum;
  /**
   * number offset
   */
  private float mOffset;
  private Interpolator mInterpolator = new DecelerateInterpolator();
  private float mTextCenterX;
  private int mTextHeight;


  private int mVelocity = DEFAULT_VELOCITY;
  private final Runnable mScrollRunnable = new Runnable() {
    @Override
    public void run() {
      float x = (float) (1 - 1.0 * (mTargetNum - mCurNum) / mDeltaNum);
      mOffset -= mVelocity * 0.01f * (1 - mInterpolator.getInterpolation(x) + 0.1);
      invalidate();

      if (mOffset <= -1) {
        mOffset = 0;
        calNum(mCurNum + 1);
      }
    }
  };

  public ScrollNumberText(Context context) {
    this(context, null);
  }

  public ScrollNumberText(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public ScrollNumberText(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mPaint.setTextAlign(Paint.Align.CENTER);
  }

  public void setVelocity(@IntRange(from = 0, to = 1000) int velocity) {
    mVelocity = velocity;
  }

  public void setNumber(final int from, final int to, long delay) {
    if (from == to) {
      mCurNum = from;
      mTargetNum = to;
      invalidate();
      return;
    }
    postDelayed(new Runnable() {
      @Override
      public void run() {
        setFromNumber(from);
        setTargetNumber(to);
        mDeltaNum = to - from;
      }
    }, delay);
  }

  public void setTextSize(int textSize) {
    mPaint.setTextSize(textSize);
    measureTextHeight();
    requestLayout();
    invalidate();
  }

  public void setTextFont(Typeface typeface) {
    if (typeface == null) {
      throw new RuntimeException("please check your font!");
    }
    mPaint.setTypeface(typeface);
    requestLayout();
    invalidate();
  }

  public void setTextColor(int mTextColor) {
    mPaint.setColor(mTextColor);
    invalidate();
  }

  public void setInterpolator(Interpolator interpolator) {
    mInterpolator = interpolator;
  }

  private void measureTextHeight() {
    mPaint.getTextBounds(mCurNum + "", 0, 1, mTextBounds);
    mTextHeight = mTextBounds.height();
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    int width = measureWidth(widthMeasureSpec);
    int height = measureHeight(heightMeasureSpec);
    setMeasuredDimension(width, height);

    mTextCenterX = (getMeasuredWidth() - getPaddingLeft() - getPaddingRight()) >>> 1;
  }

  private int measureHeight(int measureSpec) {
    int mode = MeasureSpec.getMode(measureSpec);
    int val = MeasureSpec.getSize(measureSpec);
    int result = 0;
    switch (mode) {
      case MeasureSpec.EXACTLY:
        result = val;
        break;
      case MeasureSpec.AT_MOST:
      case MeasureSpec.UNSPECIFIED:
        mPaint.getTextBounds("0", 0, 1, mTextBounds);
        result = mTextBounds.height();
        break;
    }
    result = mode == MeasureSpec.AT_MOST ? Math.min(result, val) : result;
    return result + getPaddingTop() + getPaddingBottom();
  }

  private int measureWidth(int measureSpec) {
    int mode = MeasureSpec.getMode(measureSpec);
    int val = MeasureSpec.getSize(measureSpec);
    int result = 0;
    switch (mode) {
      case MeasureSpec.EXACTLY:
        result = val;
        break;
      case MeasureSpec.AT_MOST:
      case MeasureSpec.UNSPECIFIED:
        mPaint.getTextBounds("0", 0, 1, mTextBounds);
        result = mTextBounds.width();
        break;
    }
    result = mode == MeasureSpec.AT_MOST ? Math.min(result, val) : result;
    return result + getPaddingLeft() + getPaddingRight();
  }

  @Override
  protected void onDraw(Canvas canvas) {


    if (mCurNum != mTargetNum) {
      postDelayed(mScrollRunnable, 0);
    }
    canvas.translate(0, mOffset * getMeasuredHeight());
    drawSelf(canvas);
    drawNext(canvas);
  }

  private void setFromNumber(int number) {
    if (number < 0 || number > 9) {
      throw new RuntimeException("invalidate number , should in [0,9]");
    }
    calNum(number);
    mOffset = 0;
    invalidate();
  }

  private void calNum(int number) {
    number = number == -1 ? 9 : number;
    number = number == 10 ? 0 : number;
    mCurNum = number;
    mNextNum = number + 1 == 10 ? 0 : number + 1;
  }

  private void drawNext(Canvas canvas) {
    float y = (float) (getMeasuredHeight() * 1.5);
    canvas.drawText(mNextNum + "", mTextCenterX, y + mTextHeight / 2.0f, mPaint);
  }

  private void drawSelf(Canvas canvas) {
    int y = getMeasuredHeight() / 2;
    canvas.drawText(mCurNum + "", mTextCenterX, y + mTextHeight / 2.0f, mPaint);
  }


  public void setTargetNumber(int nextNum) {
    this.mTargetNum = nextNum;
    invalidate();
  }

  @Override
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    removeCallbacks(mScrollRunnable);
  }
}