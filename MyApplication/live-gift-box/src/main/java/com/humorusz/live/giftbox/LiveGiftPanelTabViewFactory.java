package com.humorusz.live.giftbox;

import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;

/**
 * 创建Gift TabViewItem的工厂
 * @author zhangzhiquan
 * @date 2021/1/27
 */
interface LiveGiftPanelTabViewFactory {
  /**
   * 创建TabView
   * @param parent
   * @param viewType
   * @return
   */
  View createPanelTabView(@NonNull ViewGroup parent, int viewType);
}
