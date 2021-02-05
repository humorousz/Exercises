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
 * @author zhangzhiquan
 * @date 2021/2/4
 */
public class LiveNormalGiftTabViewStrategy implements LiveGiftPanelTabView.GiftItemViewStrategy {
  @Override
  public int getSpanCount() {
    return 4;
  }

  @Override
  public int getOrientation() {
    return RecyclerView.HORIZONTAL;
  }

  @Override
  public <T extends RecyclerView.ViewHolder> void onBindViewHolder(
      T viewHolder,
      LiveGiftItem item,
      int position) {
    ViewHolder holder = (ViewHolder) viewHolder;
    TextView textView = holder.mText;
    textView.setText(item.getName());

  }

  @Override
  public ViewHolder createViewHolder(ViewGroup parent) {
    TextView textView = new TextView(parent.getContext());
    textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    textView.setTextSize(15);
    textView.setPadding(40,40,40,40);
    textView.setTextColor(Color.RED);
    textView.setGravity(Gravity.CENTER);
    return new ViewHolder(textView);
  }


  private class ViewHolder extends RecyclerView.ViewHolder{
    TextView mText;
    public ViewHolder(View itemView) {
      super(itemView);
      mText = (TextView) itemView;
    }
  }
}
