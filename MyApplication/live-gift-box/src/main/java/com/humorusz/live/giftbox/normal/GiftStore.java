package com.humorusz.live.giftbox.normal;

import java.util.ArrayList;
import java.util.List;

import com.humorusz.live.giftbox.base.LiveGiftItem;

/**
 * @author zhangzhiquan
 * @date 2021/2/4
 */
class GiftStore {
  static List<LiveGiftItem> mData;

  static void initData(){
      mData = new ArrayList<>(12);
      mData.add(new GiftInfo("奢华蛋糕","cake.webp"));
      mData.add(new GiftInfo("跑车","car.webp"));
      mData.add(new GiftInfo("新年快乐","newyear.webp"));
      mData.add(new GiftInfo("人民币","rmb.webp"));
      mData.add(new GiftInfo("鲜花","flower.webp"));
      mData.add(new GiftInfo("城堡","house.webp"));
      mData.add(new GiftInfo("邮轮","ship.webp"));
      mData.add(new GiftInfo("心动","animation_out.webp"));
      mData.add(new GiftInfo("香蕉","banana.webp"));
      mData.add(new GiftInfo("星星 ","star.webp"));
      mData.add(new GiftInfo("超跑 ","newcar.webp"));


    mData.add(new GiftInfo("奢华蛋糕","cake.webp"));
    mData.add(new GiftInfo("跑车","car.webp"));
    mData.add(new GiftInfo("新年快乐","newyear.webp"));
    mData.add(new GiftInfo("人民币","rmb.webp"));
    mData.add(new GiftInfo("鲜花","flower.webp"));
    mData.add(new GiftInfo("城堡","house.webp"));
    mData.add(new GiftInfo("邮轮","ship.webp"));
    mData.add(new GiftInfo("心动","animation_out.webp"));
    mData.add(new GiftInfo("香蕉","banana.webp"));
    mData.add(new GiftInfo("星星 ","star.webp"));
    mData.add(new GiftInfo("超跑 ","newcar.webp"));


    mData.add(new GiftInfo("奢华蛋糕","cake.webp"));
    mData.add(new GiftInfo("跑车","car.webp"));
    mData.add(new GiftInfo("新年快乐","newyear.webp"));
    mData.add(new GiftInfo("人民币","rmb.webp"));
    mData.add(new GiftInfo("鲜花","flower.webp"));
    mData.add(new GiftInfo("城堡","house.webp"));
    mData.add(new GiftInfo("邮轮","ship.webp"));
    mData.add(new GiftInfo("心动","animation_out.webp"));
    mData.add(new GiftInfo("香蕉","banana.webp"));
    mData.add(new GiftInfo("星星 ","star.webp"));
    mData.add(new GiftInfo("超跑 ","newcar.webp"));
  }

  public static LiveGiftItem getGift(int position){
    return mData.get(position);
  }

  public static List<LiveGiftItem> getData(){
    initData();
    return mData;
  }
}
