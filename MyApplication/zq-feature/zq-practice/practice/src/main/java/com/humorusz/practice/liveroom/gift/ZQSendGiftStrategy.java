package com.humorusz.practice.liveroom.gift;

import com.humorusz.practice.liveroom.controller.GiftAnimationController;
import com.humorusz.live.giftbox.base.LiveGiftItem;
import com.humorusz.live.giftbox.base.LiveGiftPanelTabView;
import com.humorusz.live.giftbox.data.GiftInfo;

/**
 * @author zhangzhiquan
 * @date 2021/2/12
 */
public class ZQSendGiftStrategy implements LiveGiftPanelTabView.GiftSendStrategy {
  private GiftAnimationController mGiftAnimationController;

  public ZQSendGiftStrategy(GiftAnimationController giftAnimationController) {
    mGiftAnimationController = giftAnimationController;
  }

  @Override
  public void sendGift(LiveGiftItem giftItem, long count) {
    if (giftItem instanceof GiftInfo) {
      GiftInfo giftInfo = (GiftInfo) giftItem;
      mGiftAnimationController.addTask(giftInfo.path, giftInfo.name);
    }
  }
}
