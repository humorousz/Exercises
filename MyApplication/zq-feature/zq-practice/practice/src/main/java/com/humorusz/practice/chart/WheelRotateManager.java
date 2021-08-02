package com.humorusz.practice.chart;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.animation.DecelerateInterpolator;
import com.humorousz.commonutils.util.CollectionUtils;
import io.reactivex.annotations.NonNull;

/**
 * 转盘旋转Manager
 */
public class WheelRotateManager {
  private static final int ROTATION_COUNT = 5;
  private static final int ANIM_PLAY_TIME_MS = 5000;
  private WheelView mWheelView;

  public WheelRotateManager(@NonNull WheelView view) {
    mWheelView = view;
  }

  public void rotateToPosition(int position) {
    if (mWheelView == null
        || CollectionUtils.isEmpty(mWheelView.getDataList())
        || mWheelView.getDataList().size() <= position) {
      return;
    }
    mWheelView.setRotation(mWheelView.getRotation() % WheelView.CIRCLE_TOTAL_DEGREE);
    float degree = WheelView.CIRCLE_TOTAL_DEGREE - mWheelView.getItemDegree() * position;
    mWheelView.setPivotX(mWheelView.getWidth() >> 1);
    mWheelView.setPivotY(mWheelView.getHeight() >> 1);
    float currentRotation = mWheelView.getRotation();
    float target = WheelView.CIRCLE_TOTAL_DEGREE * ROTATION_COUNT + degree;
    ValueAnimator animator =
        ObjectAnimator.ofFloat(mWheelView, "rotation", currentRotation, target);
    animator.setDuration(ANIM_PLAY_TIME_MS);
    animator.setInterpolator(new DecelerateInterpolator());
    animator.start();
  }
}
