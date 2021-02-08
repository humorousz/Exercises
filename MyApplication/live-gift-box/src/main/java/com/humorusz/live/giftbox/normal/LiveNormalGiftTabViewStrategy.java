package com.humorusz.live.giftbox.normal;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

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
  public View onCreateItemView(int position, ViewGroup parent, LiveGiftItem item) {
    TextView textView = new TextView(parent.getContext());
    textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT));
    textView.setTextSize(15);
    textView.setPadding(40, 40, 40, 40);
    textView.setTextColor(Color.RED);
    textView.setGravity(Gravity.CENTER);
    textView.setText(item.getName());
    return textView;
  }
}
