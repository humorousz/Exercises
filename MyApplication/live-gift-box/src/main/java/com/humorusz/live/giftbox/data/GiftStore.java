package com.humorusz.live.giftbox.data;

import java.util.ArrayList;
import java.util.List;

import com.humorusz.live.giftbox.base.LiveGiftItem;

/**
 * @author zhangzhiquan
 * @date 2021/2/4
 */
public class GiftStore {
  static List<LiveGiftItem> mData;

  static void initData() {
    mData = new ArrayList<>(12);
    mData.add(new GiftInfo("奢华蛋糕", "cake.webp"));
    mData.add(new GiftInfo("跑车", "car.webp"));
    mData.add(new GiftInfo("新年快乐", "newyear.webp"));
    mData.add(new GiftInfo("人民币", "rmb.webp"));
    mData.add(new GiftInfo("鲜花", "flower.webp"));
    mData.add(new GiftInfo("城堡", "house.webp"));
    mData.add(new GiftInfo("邮轮", "ship.webp"));
    mData.add(new GiftInfo("心动", "animation_out.webp"));
    mData.add(new GiftInfo("香蕉", "banana.webp"));
    mData.add(new GiftInfo("星星 ", "star.webp"));
    mData.add(new GiftInfo("超跑 ", "newcar.webp"));


    mData.add(new GiftInfo("奢华蛋糕1", "cake.webp"));
    mData.add(new GiftInfo("跑车1", "car.webp"));
    mData.add(new GiftInfo("新年快乐1", "newyear.webp"));
    mData.add(new GiftInfo("人民币1", "rmb.webp"));
    mData.add(new GiftInfo("鲜花1", "flower.webp"));
    mData.add(new GiftInfo("城堡1", "house.webp"));
    mData.add(new GiftInfo("邮轮1", "ship.webp"));
    mData.add(new GiftInfo("心动1", "animation_out.webp"));
    mData.add(new GiftInfo("香蕉1", "banana.webp"));
    mData.add(new GiftInfo("星星1", "star.webp"));
    mData.add(new GiftInfo("超跑1", "newcar.webp"));


    mData.add(new GiftInfo("奢华蛋糕2", "cake.webp"));
    mData.add(new GiftInfo("跑车2", "car.webp"));
    mData.add(new GiftInfo("新年快乐2", "newyear.webp"));
    mData.add(new GiftInfo("人民币2", "rmb.webp"));
    mData.add(new GiftInfo("鲜花2", "flower.webp"));
    mData.add(new GiftInfo("城堡2", "house.webp"));
    mData.add(new GiftInfo("邮轮2", "ship.webp"));
    mData.add(new GiftInfo("心动2", "animation_out.webp"));
    mData.add(new GiftInfo("香蕉2", "banana.webp"));
    mData.add(new GiftInfo("星星2", "star.webp"));
    mData.add(new GiftInfo("超跑2", "newcar.webp"));
  }

  public static LiveGiftItem getGift(int position) {
    return mData.get(position);
  }

  public static List<LiveGiftItem> getData() {
    initData();
    return mData;
  }
}
