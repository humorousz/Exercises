package com.humorusz.practice.chart;

/**
 * 轮盘点击事件回调
 */
public interface OnWheelDataClickListener {
  /**
   * 点击轮盘上的item
   *
   * @param position 点击的position
   * @param data     点击的data
   */
  void onWheelDataClick(int position, WheelData data);
}
