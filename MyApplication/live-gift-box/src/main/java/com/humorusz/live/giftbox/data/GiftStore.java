package com.humorusz.live.giftbox.data;

import java.util.ArrayList;
import java.util.List;

import com.humorusz.live.giftbox.base.LiveGiftItem;

/**
 * @author zhangzhiquan
 * @date 2021/2/4
 */
public class GiftStore {
  static List<LiveGiftItem> mDataPager1;
  static List<LiveGiftItem> mDataPager2;

  private static void initPage1() {
    if (mDataPager1 != null) {
      return;
    }
    mDataPager1 = new ArrayList<>(12);
    mDataPager1.add(new GiftInfo("奢华蛋糕", "cake.webp"));
    mDataPager1.add(new GiftInfo("跑车", "car.webp"));
    mDataPager1.add(new GiftInfo("新年快乐", "newyear.webp"));
    mDataPager1.add(new GiftInfo("人民币", "rmb.webp"));
    mDataPager1.add(new GiftInfo("鲜花", "flower.webp"));
    mDataPager1.add(new GiftInfo("城堡", "house.webp"));
    mDataPager1.add(new GiftInfo("邮轮", "ship.webp"));
    mDataPager1.add(new GiftInfo("心动", "animation_out.webp"));
    mDataPager1.add(new GiftInfo("香蕉", "banana.webp"));
    mDataPager1.add(new GiftInfo("星星 ", "star.webp"));
    mDataPager1.add(new GiftInfo("超跑 ", "newcar.webp"));


    mDataPager1.add(new GiftInfo("奢华蛋糕1", "cake.webp"));
    mDataPager1.add(new GiftInfo("跑车1", "car.webp"));
    mDataPager1.add(new GiftInfo("新年快乐1", "newyear.webp"));
    mDataPager1.add(new GiftInfo("人民币1", "rmb.webp"));
    mDataPager1.add(new GiftInfo("鲜花1", "flower.webp"));
    mDataPager1.add(new GiftInfo("城堡1", "house.webp"));
    mDataPager1.add(new GiftInfo("邮轮1", "ship.webp"));
    mDataPager1.add(new GiftInfo("心动1", "animation_out.webp"));
    mDataPager1.add(new GiftInfo("香蕉1", "banana.webp"));
    mDataPager1.add(new GiftInfo("星星1", "star.webp"));
    mDataPager1.add(new GiftInfo("超跑1", "newcar.webp"));
  }

  private static void initPage2() {
    if (mDataPager2 != null) {
      return;
    }
    mDataPager2 = new ArrayList<>(12);
    mDataPager2.add(new GiftInfo("奢华蛋糕22", "cake.webp"));
    mDataPager2.add(new GiftInfo("跑车22", "car.webp"));
    mDataPager2.add(new GiftInfo("新年快乐22", "newyear.webp"));
    mDataPager2.add(new GiftInfo("人民币22", "rmb.webp"));
    mDataPager2.add(new GiftInfo("鲜花22", "flower.webp"));
    mDataPager2.add(new GiftInfo("城堡22", "house.webp"));
    mDataPager2.add(new GiftInfo("邮轮22", "ship.webp"));
    mDataPager2.add(new GiftInfo("心动22", "animation_out.webp"));
    mDataPager2.add(new GiftInfo("香蕉22", "banana.webp"));
    mDataPager2.add(new GiftInfo("星星22", "star.webp"));
    mDataPager2.add(new GiftInfo("超跑22", "newcar.webp"));
  }

  public static LiveGiftItem getGift(int position) {
    return mDataPager1.get(position);
  }

  public static List<LiveGiftItem> getDataPage1() {
    initPage1();
    return mDataPager1;
  }


  public static List<LiveGiftItem> getDataPage2() {
    initPage2();
    return mDataPager2;
  }
}
