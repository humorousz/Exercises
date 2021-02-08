package com.humorusz.live.giftbox.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 直播礼物网格ViewPager
 *
 * @author zhangzhiquan
 * @date 2021/2/8
 */
class LiveGiftGridViewPager extends GridViewPager {
  private float mLastMotionX;
  /**
   * ID of the active pointer. This is used to retain consistency during
   * drags/flings if multiple pointers are used.
   */
  private int mActivePointerId = -1;

  public LiveGiftGridViewPager(Context context) {
    this(context, null);
  }

  public LiveGiftGridViewPager(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent event) {
    getParent().requestDisallowInterceptTouchEvent(true);
    return super.onInterceptTouchEvent(event);
  }

  @SuppressLint("ClickableViewAccessibility")
  @Override
  public boolean onTouchEvent(MotionEvent ev) {
    final int action = ev.getAction() & MotionEvent.ACTION_MASK;
    switch (action) {
      case MotionEvent.ACTION_DOWN: {
        getParent().requestDisallowInterceptTouchEvent(true);
        mActivePointerId = ev.getPointerId(0);
        final int pointerIndex = ev.findPointerIndex(mActivePointerId);
        mLastMotionX = ev.getX(pointerIndex);
        return true;
      }
      case MotionEvent.ACTION_MOVE: {
        final int pointerIndex = ev.findPointerIndex(mActivePointerId);
        final float x = ev.getX(pointerIndex);
        final float dx = x - mLastMotionX;
        mLastMotionX = x;
        if (shouldIgnore(dx)) {
          getParent().requestDisallowInterceptTouchEvent(false);
          return false;
        }
        break;
      }
      case MotionEvent.ACTION_UP: {
        mLastMotionX = 0;
        break;
      }
    }
    return super.onTouchEvent(ev);
  }

  private boolean shouldIgnore(float dx) {
    return (dx > 0 && getCurrentItem() == 0) || (dx < 0 && getCurrentItem() == getPageCount() - 1);
  }
}
