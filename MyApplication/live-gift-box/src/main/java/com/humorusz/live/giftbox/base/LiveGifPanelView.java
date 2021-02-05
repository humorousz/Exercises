package com.humorusz.live.giftbox.base;

import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TabHost;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.humorusz.live.giftbox.R;

/**
 * 礼物面板View
 *
 * @author zhangzhiquan
 * @date 2021/1/27
 */
public class LiveGifPanelView extends FrameLayout {
  private TabHost mTabHost;
  private GiftTabAdapter mAdapter;
  private ViewPager2 mGiftTabViewPager;
  private List<LiveGiftPanelTabView> mLiveGiftPanelTabViews;
  @Nullable
  private LiveGiftTabTitleCreateFactory mTabContentFactory;

  public LiveGifPanelView(@NonNull Context context) {
    this(context, null);
  }

  public LiveGifPanelView(@NonNull Context context,
      @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public LiveGifPanelView(@NonNull Context context, @Nullable AttributeSet attrs,
      int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    LayoutInflater.from(context).inflate(R.layout.live_gift_panel_view, this);
    initView();
  }

  private void initView() {
    mGiftTabViewPager = findViewById(R.id.live_gift_panel_view_pager);
    mTabHost = findViewById(R.id.live_gift_panel_view_tab_host);
    mAdapter = new GiftTabAdapter();
    mGiftTabViewPager.setAdapter(mAdapter);
  }

  public void setGiftPanelTabContentFactory(@Nullable LiveGiftTabTitleCreateFactory factory) {
    mTabContentFactory = factory;
  }

  public void setGiftPanelTabItems(@Nullable List<LiveGiftPanelTabView> panelTabItems) {
    mLiveGiftPanelTabViews = panelTabItems;
    mAdapter.notifyDataSetChanged();
  }

  private void setTabHost(@Nullable List<LiveGiftPanelTabView> panelTabItems) {
    if (mTabContentFactory == null || panelTabItems == null || panelTabItems.isEmpty()) {
      mTabHost.setVisibility(GONE);
      return;
    }
    for (final LiveGiftPanelTabView tabView : panelTabItems) {
      TabHost.TabSpec tabSpec = mTabHost.newTabSpec(tabView.getTitle());
      tabSpec.setContent(new TabHost.TabContentFactory() {
        @Override
        public View createTabContent(String tag) {
          return mTabContentFactory.createGiftTabView(tag, tabView);
        }
      });
      tabSpec.setIndicator(mTabContentFactory.createGiftTabIndicator(tabView));
      mTabHost.addTab(tabSpec);
    }
  }

  @Override
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    if (mLiveGiftPanelTabViews != null) {
      for (LiveGiftPanelTabView tabView : mLiveGiftPanelTabViews) {
        tabView.onGiftPanelDestroy();
      }
    }
  }

  private class GiftTabAdapter extends RecyclerView.Adapter<GiftTabViewHolder> {
    @NonNull
    @Override
    public GiftTabViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      FrameLayout frameLayout = new FrameLayout(parent.getContext());
      frameLayout.setLayoutParams(new ViewGroup.LayoutParams(
          ViewGroup.LayoutParams.MATCH_PARENT,
          ViewGroup.LayoutParams.MATCH_PARENT));
      GiftTabViewHolder holder = new GiftTabViewHolder(frameLayout);
      holder.setViewGroup(frameLayout);
      return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull GiftTabViewHolder holder, int position) {
      if (mLiveGiftPanelTabViews == null || mLiveGiftPanelTabViews.isEmpty()) {
        return;
      }
      LiveGiftPanelTabView tabView = mLiveGiftPanelTabViews.get(position);
      tabView.onCreateGiftPanelTabView(holder.getViewGroup());
    }

    @Override
    public int getItemCount() {
      return mLiveGiftPanelTabViews == null ? 0 : mLiveGiftPanelTabViews.size();
    }
  }


  /**
   * 礼物面板每个Tab的Holder
   */
  private static class GiftTabViewHolder extends RecyclerView.ViewHolder {
    private ViewGroup mViewGroup;

    public GiftTabViewHolder(@NonNull View itemView) {
      super(itemView);
    }

    public void setViewGroup(ViewGroup viewGroup) {
      mViewGroup = viewGroup;
    }

    public ViewGroup getViewGroup() {
      return mViewGroup;
    }
  }
}
