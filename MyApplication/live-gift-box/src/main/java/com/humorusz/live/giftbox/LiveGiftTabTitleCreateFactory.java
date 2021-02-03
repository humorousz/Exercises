package com.humorusz.live.giftbox;

import android.view.View;

/**
 * 创建礼物TabTitle
 * @author zhangzhiquan
 * @date 2021/2/3
 */
interface LiveGiftTabTitleCreateFactory {
  /**
   * TabTitleView创建
   * @param tag tab tag
   * @param tabView tabView 数据结构
   * @return Tabview
   */
  View createGiftTabView(String tag, LiveGiftPanelTabView tabView);

  /**
   * 指示器创建
   * @param tabView tabView 数据结构
   * @return 指示器View
   */
  View createGiftTabIndicator(LiveGiftPanelTabView tabView);
}
