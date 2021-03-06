package com.humorusz.live.giftbox.base;

/**
 * 直播礼物信息
 *
 * @author zhangzhiquan
 * @date 2021/1/27
 */
public abstract class LiveGiftItem {
  public abstract boolean isSelected();

  public abstract void setSelected(boolean isSelected);

  public abstract String getName();
}
