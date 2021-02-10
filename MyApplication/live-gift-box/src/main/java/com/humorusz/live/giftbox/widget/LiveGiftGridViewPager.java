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
  /**
   * 是否已经拦截了Touch事件
   * 如果在第一页面，向左滑动不松手，在向右滑动，理论上不应该不拦截touchEvent
   */
  private boolean mHasInterceptTouchEvent = false;

  public LiveGiftGridViewPager(Context context) {
    this(context, null);
  }

  public LiveGiftGridViewPager(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent event) {
    getParent().requestDisallowInterceptTouchEvent(true);
    final int action = event.getAction() & MotionEvent.ACTION_MASK;
    if (action == MotionEvent.ACTION_DOWN) {
      mActivePointerId = event.getPointerId(0);
      final int pointerIndex = event.findPointerIndex(mActivePointerId);
      mActivePointerId = event.getPointerId(pointerIndex);
      mLastMotionX = event.getX(mActivePointerId);
    }
    return super.onInterceptTouchEvent(event);
  }

  @SuppressLint("ClickableViewAccessibility")
  @Override
  public boolean onTouchEvent(MotionEvent ev) {
    final int action = ev.getAction() & MotionEvent.ACTION_MASK;
    switch (action) {
      case MotionEvent.ACTION_MOVE: {
        final int pointerIndex = ev.findPointerIndex(mActivePointerId);
        final float x = ev.getX(pointerIndex);
        final float dx = x - mLastMotionX;
        mLastMotionX = x;
        /**
         * 除了滑动方向，还要考虑是否已经处理了Touch事件，如果处理了，就不考虑滑动方向了，否则将会发生跳动
         */
        if (shouldIgnore(dx) && !mHasInterceptTouchEvent) {
          getParent().requestDisallowInterceptTouchEvent(false);
        } else {
          getParent().requestDisallowInterceptTouchEvent(true);
          mHasInterceptTouchEvent = true;
        }
        break;
      }
      case MotionEvent.ACTION_UP: {
        mLastMotionX = 0;
        mHasInterceptTouchEvent = false;
        break;
      }
    }
    return super.onTouchEvent(ev);
  }

  private boolean shouldIgnore(float dx) {
    return (dx > 0 && getCurrentItem() == 0) || (dx < 0 && getCurrentItem() == getPageCount() - 1);
  }
}
