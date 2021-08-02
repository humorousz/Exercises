package com.humorusz.live.giftbox.normal;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.humorusz.live.giftbox.R;
import com.humorusz.live.giftbox.base.LiveGiftItem;
import com.humorusz.live.giftbox.base.LiveGiftPanelTabView;

/**
 * 礼物面板Item配置创建策略
 *
 * @author zhangzhiquan
 * @date 2021/2/4
 */
public class LiveNormalGiftTabViewStrategy implements LiveGiftPanelTabView.GiftItemViewStrategy {
  @Override
  public View onCreateItemView(int position, ViewGroup parent, LiveGiftItem item,
      boolean isSelected) {
    View mRoot = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.live_gift_panel_normal_item_view, parent, false);
    onUpdateItemView(position, mRoot, item,isSelected);
    return mRoot;
  }

  @Override
  public void onUpdateItemView(int position, View itemView, LiveGiftItem item, boolean isSelected) {
    TextView textView = itemView.findViewById(R.id.live_normal_gift_view);
    textView.setText(item.getName());
    if (isSelected) {
      textView.setBackgroundColor(Color.RED);
    } else {
      textView.setBackgroundColor(Color.TRANSPARENT);
    }
  }

  @Override
  public void onItemClick(int position, View itemView, LiveGiftItem item) {
    TextView textView = itemView.findViewById(R.id.live_normal_gift_view);
    textView.animate()
        .scaleX(2f)
        .scaleY(2f)
        .setDuration(300)
        .scaleX(1f)
        .scaleY(1f)
        .setDuration(300)
        .start();
  }
}
