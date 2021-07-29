package com.humorusz.practice.liveroom.weidget;


import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatSeekBar;

public class MySeekBar extends AppCompatSeekBar {
  public MySeekBar(Context context) {
    super(context);
  }

  public MySeekBar(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public MySeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    return false;
  }

  @Override
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    setPadding(0, 0, 0, 0);
  }

}
