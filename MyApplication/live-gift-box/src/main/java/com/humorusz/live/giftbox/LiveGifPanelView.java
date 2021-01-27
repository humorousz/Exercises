package com.humorusz.live.giftbox;

import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.FrameLayout;
import android.widget.TabHost;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.widget.ViewPager2;

/**
 * 礼物面板View
 *
 * @author zhangzhiquan
 * @date 2021/1/27
 */
class LiveGifPanelView extends FrameLayout {
  private TabHost mTabHost;
  private GiftTabAdapter mAdapter;
  private ViewPager2 mGiftTabViewPager;
  private List<LiveGiftPanelTabView> mLiveGiftPanelTabViews;
  @NonNull
  private LiveGiftPanelTabViewFactory mLiveGiftPanelTabViewFactory;

  public LiveGifPanelView(@NonNull Context context) {
    super(context);
  }

  public LiveGifPanelView(@NonNull Context context,
      @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public LiveGifPanelView(@NonNull Context context, @Nullable AttributeSet attrs,
      int defStyleAttr) {
    super(context, attrs, defStyleAttr);

  }

  public void setLiveGiftPanelTabViewFactory(@NonNull LiveGiftPanelTabViewFactory factory) {
    mLiveGiftPanelTabViewFactory = factory;
  }

  public void setGiftPanelTabItems(List<LiveGiftPanelTabView> panelTabItems) {
    mGiftTabViewPager.setAdapter(mAdapter);
  }


  private class GiftTabAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
      return mLiveGiftPanelTabViews == null ? 0 : mLiveGiftPanelTabViews.size();
    }
  }
}
