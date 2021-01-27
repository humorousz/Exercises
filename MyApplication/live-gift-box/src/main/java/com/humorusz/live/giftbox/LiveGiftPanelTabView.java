package com.humorusz.live.giftbox;

import android.view.View;

/**
 * 礼物面板每页对应的Item
 *
 * @author zhangzhiquan
 * @date 2021/1/27
 */
interface LiveGiftPanelTabView {
  /**
   * 获取Title
   *
   * @return
   */
  String getTitle();

  /**
   * 发送礼物
   *
   * @param giftItem 礼物item
   * @param count    礼物数量
   */
  void sendGift(LiveGiftItem giftItem, long count);

  /**
   * 得到选中的礼物
   *
   * @return
   */
  LiveGiftItem getSelectedGift();

  /**
   * 获取TabView
   * @return
   */
  View getTabView();
}
