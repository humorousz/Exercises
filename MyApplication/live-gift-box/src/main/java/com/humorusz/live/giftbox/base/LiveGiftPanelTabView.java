package com.humorusz.live.giftbox.base;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

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

  void setGiftItemConfigStrategy(GiftItemConfigStrategy giftItemConfigStrategy);

  void setGiftDataSourceStrategy(GiftDataSourceStrategy giftDataSourceStrategy);

  /**
   * 创建礼物Item的策略
   */
  interface GiftItemViewStrategy {
    View createItemView(LiveGiftItem item, int position);

    <T extends RecyclerView.ViewHolder> T createViewHolder(ViewGroup parent);

    <T extends RecyclerView.ViewHolder> void onBindViewHolder(
        T viewHolder,
        LiveGiftItem item,
        int position);
  }

  /**
   * 礼物列表配置策略
   */
  interface GiftItemConfigStrategy {
    int getSpanCount();

    @RecyclerView.Orientation
    int getOrientation();
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
