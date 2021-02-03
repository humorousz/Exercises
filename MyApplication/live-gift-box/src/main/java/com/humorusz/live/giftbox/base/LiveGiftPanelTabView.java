package com.humorusz.live.giftbox.base;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

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
  LiveGiftItem getSelectedGift();

  /**
   * 获取TabView
   *
   * @return
   */
  void onCreateGiftPanelTabView(ViewGroup parent);

  void setGiftItemViewStrategy(GiftItemViewStrategy giftItemViewStrategy);

  void setGiftItemConfigStrategy(GiftItemConfigStrategy giftItemConfigStrategy);

  void setGiftDataSourceStrategy(LifecycleOwner owner,
      GiftDataSourceStrategy giftDataSourceStrategy);

  /**
   * 创建礼物Item的策略
   */
  interface GiftItemViewStrategy {
    View createItemView(LiveGiftItem item, int position);
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
     * 礼物列表数据的DataSource
     *
     * @return
     */
    LiveData<List<LiveGiftItem>> getGiftItemsDataSource();
  }
}
