package com.humorusz.practice.liveroom.gift;

import java.util.ArrayList;
import java.util.List;

import com.humorusz.practice.liveroom.controller.GiftAnimationController;
import com.humorusz.live.giftbox.base.LiveGiftPanelTabView;
import com.humorusz.live.giftbox.base.LiveNormalGiftTabView;
import com.humorusz.live.giftbox.normal.LiveNormalGiftDataSourceStrategy;
import com.humorusz.live.giftbox.normal.LiveNormalGiftDataSourceStrategy2;
import com.humorusz.live.giftbox.normal.LiveNormalGiftTabViewStrategy;

/**
 * @author zhangzhiquan
 * @date 2021/2/12
 */
public class LiveGiftPanelTabViewFactory {
  private GiftAnimationController mGiftAnimationController;

  public void setGiftAnimationController(GiftAnimationController controller) {
    mGiftAnimationController = controller;
  }

  public List<LiveGiftPanelTabView> createTabViews() {

    List<LiveGiftPanelTabView> res = new ArrayList<>();
    LiveGiftPanelTabView giftPanelTabView = new LiveNormalGiftTabView();
    giftPanelTabView.setGiftDataSourceStrategy(new LiveNormalGiftDataSourceStrategy());
    giftPanelTabView.setGiftItemViewStrategy(new LiveNormalGiftTabViewStrategy());
    giftPanelTabView.setGiftSendStrategy(new ZQSendGiftStrategy(mGiftAnimationController));


    LiveGiftPanelTabView giftPanelTabView2 = new LiveNormalGiftTabView();
    giftPanelTabView2.setGiftDataSourceStrategy(new LiveNormalGiftDataSourceStrategy2());
    giftPanelTabView2.setGiftItemViewStrategy(new LiveNormalGiftTabViewStrategy());

    res.add(giftPanelTabView);
    res.add(giftPanelTabView2);
    return res;
  }

  ;
}
