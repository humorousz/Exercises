package com.humorous.myapplication.chart;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;

import com.humorousz.commonutils.util.CollectionUtils;

import io.reactivex.annotations.NonNull;

/**
 * 转盘旋转Manager
 */
public class WheelRotateManager {
  private WheelView mWheelView;

  public WheelRotateManager(@NonNull WheelView view) {
    mWheelView = view;
  }

  public void roateToPosition(int position) {
    if (mWheelView == null || CollectionUtils.isEmpty(mWheelView.getDataList()) || mWheelView.getDataList().size() <= position) {
      return;
    }
    mWheelView.setRotation(mWheelView.getRotation() % 360);
    float degree = 360.f - mWheelView.getItemDegree() * position;
    mWheelView.setPivotX(mWheelView.getWidth() >> 1);
    mWheelView.setPivotY(mWheelView.getHeight() >> 1);
    float currentRotation = mWheelView.getRotation();
    ValueAnimator animator = ObjectAnimator.ofFloat(mWheelView, "rotation", currentRotation, 360 * 5 + degree);
    animator.setDuration(5000);
    animator.start();
  }
}
