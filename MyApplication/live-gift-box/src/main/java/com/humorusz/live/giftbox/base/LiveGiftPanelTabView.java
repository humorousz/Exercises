package com.humorusz.live.giftbox.base;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;

import io.reactivex.Observable;

/**
 * 礼物面板每页对应的Item
 *
 * @author zhangzhiquan
 * @date 2021/1/27
 */
public interface LiveGiftPanelTabView {
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
  @Nullable
  LiveGiftItem getSelectedGift();

  /**
   * 获取TabView
   *
   * @return
   */
  void onCreateGiftPanelTabView(ViewGroup parent);

  /**
   * 礼物面板被销毁
   */
  void onGiftPanelDestroy();

  void setGiftItemViewStrategy(GiftItemViewStrategy giftItemViewStrategy);

  void setGiftDataSourceStrategy(GiftDataSourceStrategy giftDataSourceStrategy);

  /**
   * 创建礼物Item的策略
   */
  interface GiftItemViewStrategy {
    /**
     * 创建子View
     *
     * @param position
     * @param parent
     * @param item
     * @return
     */
    View onCreateItemView(int position, ViewGroup parent, LiveGiftItem item);

    /**
     * 更新子View
     *
     * @param position
     * @param itemView
     * @param item
     */
    void onUpdateItemView(int position, View itemView, LiveGiftItem item);

    /**
     * 子View被点击
     *
     * @param position 位置
     * @param itemView View
     * @param item     数据
     */
    void onItemClick(int position, View itemView, LiveGiftItem item);

  }

  /**
   * 获取礼物配置的策略
   */
  interface GiftDataSourceStrategy {
    /**
     * 获取
     *
     * @return
     */
    Observable<List<LiveGiftItem>> getGiftItemsObservable();
  }
}
