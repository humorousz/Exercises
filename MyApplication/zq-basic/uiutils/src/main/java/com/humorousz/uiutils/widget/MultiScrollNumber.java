package com.humorousz.uiutils.widget;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import androidx.annotation.IntRange;

import com.humorousz.uiutils.R;

public class MultiScrollNumber extends LinearLayout {
  private final Context mContext;
  private final List<Integer> mTargetNumbers = new ArrayList<>();
  private final List<Integer> mPrimaryNumbers = new ArrayList<>();
  private final List<ScrollNumberText> mScrollNumbers = new ArrayList<>();
  private final int mTextSize;
  private final int mTextColor;
  private Typeface mFontFileName;
  private int mVelocity = 5;

  public MultiScrollNumber(Context context) {
    this(context, null);
  }

  public MultiScrollNumber(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public MultiScrollNumber(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    mContext = context;

    TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.MultiScrollNumber);
    mTextSize =
        typedArray.getDimensionPixelOffset(R.styleable.MultiScrollNumber_android_textSize, 0);
    mTextColor =
        typedArray.getColor(R.styleable.MultiScrollNumber_android_textColor, Color.TRANSPARENT);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      mFontFileName = typedArray.getFont(R.styleable.MultiScrollNumber_android_font);
    }
    typedArray.recycle();
    setOrientation(HORIZONTAL);
    setGravity(Gravity.CENTER);
  }

  public void setNumber(int val) {
    setNumber(val, val, true);
  }

  public void setNumber(int from, int to) {
    setNumber(from, to, false);
  }

  public void setNumber(int from, int to, boolean force) {
    if (to < from) {
      from = 0;
    }
    resetNumber();
    // operate to
    int number = to, count = 0;
    while (number > 0) {
      int i = number % 10;
      mTargetNumbers.add(i);
      number /= 10;
      count++;
    }
    // operate from
    number = from;
    while (count > 0) {
      int i = number % 10;
      mPrimaryNumbers.add(i);
      number /= 10;
      count--;
    }

    if (mTargetNumbers.size() != mScrollNumbers.size() || force) {
      clearView();
      for (int i = mTargetNumbers.size() - 1; i >= 0; i--) {
        ScrollNumberText scrollNumber = new ScrollNumberText(mContext);
        scrollNumber.setTextColor(mTextColor);
        scrollNumber.setTextSize(mTextSize);
        if (mFontFileName != null) {
          scrollNumber.setTextFont(mFontFileName);
        }
        int pre = mPrimaryNumbers.size() > i ? mPrimaryNumbers.get(i) : 0;
        int v = Math.abs(mTargetNumbers.get(i) - pre);
        scrollNumber.setVelocity(mVelocity * v);
        scrollNumber.setNumber(pre, mTargetNumbers.get(i), 0);
        mScrollNumbers.add(scrollNumber);
        addView(scrollNumber);
      }
    } else {
      for (int i = mTargetNumbers.size() - 1; i >= 0; i--) {
        ScrollNumberText scrollNumberText = mScrollNumbers.get(mScrollNumbers.size() - i - 1);
        int pre = mPrimaryNumbers.size() > i ? mPrimaryNumbers.get(i) : 0;
        int v = Math.abs(mTargetNumbers.get(i) - pre);
        scrollNumberText.setVelocity(mVelocity * v);
        scrollNumberText.setNumber(pre, mTargetNumbers.get(i), 0);
      }
    }
  }

  public void setInterpolator(Interpolator interpolator) {
    if (interpolator == null) {
      throw new IllegalArgumentException("interpolator couldn't be null");
    }
    for (ScrollNumberText s : mScrollNumbers) {
      s.setInterpolator(interpolator);
    }
  }

  public void setScrollVelocity(@IntRange(from = 0, to = 1000) int velocity) {
    mVelocity = velocity;
    for (ScrollNumberText s : mScrollNumbers) {
      s.setVelocity(velocity);
    }
  }

  private void resetNumber() {
    mTargetNumbers.clear();
    mPrimaryNumbers.clear();
  }

  private void clearView() {
    mScrollNumbers.clear();
    removeAllViews();
  }


  @Override
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    resetNumber();
    clearView();
  }
}