package com.humorusz.live.giftbox.data;

import com.humorusz.live.giftbox.base.LiveGiftItem;

/**
 * @author zhangzhiquan
 * @date 2021/2/4
 */
public class GiftInfo extends LiveGiftItem {
  private static String Path = "f3/";
  public String name;
  public String path;

  public GiftInfo(String name, String path) {
    this.name = name;
    this.path = Path + path;
  }

  @Override
  public String getName() {
    return name;
  }
}
