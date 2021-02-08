package com.humorusz.live.giftbox.normal;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.humorusz.live.giftbox.GridViewPager;
import com.humorusz.live.giftbox.R;
import com.humorusz.live.giftbox.base.LiveGiftItem;
import com.humorusz.live.giftbox.base.LiveGiftPanelTabView;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author zhangzhiquan
 * @date 2021/2/3
 */
public class LiveNormalGiftTabView implements LiveGiftPanelTabView {
  private GiftItemViewStrategy mGiftItemViewStrategy;
  private GiftDataSourceStrategy mGiftDataSourceStrategy;
  private CompositeDisposable mDisposable = new CompositeDisposable();
  private GridViewPager mViewPager;
  private Adapter mAdapter;

  @Override
  public String getTitle() {
    return "普通礼物";
  }

  @Override
  public void sendGift(LiveGiftItem giftItem, long count) {

  }

  @Override
  public LiveGiftItem getSelectedGift() {
    return mViewPager == null ? null : (LiveGiftItem) mAdapter.getItem(mViewPager.getCurrentItem());
  }

  @Override
  public void onCreateGiftPanelTabView(ViewGroup parent) {
    LayoutInflater.from(parent.getContext())
        .inflate(R.layout.live_gift_panel_item_view, parent);
    mViewPager = parent.findViewById(R.id.live_gift_panel_view_pager);
    initAdapter(mViewPager);
  }

  @Override
  public void onGiftPanelDestroy() {
    if (mDisposable != null && !mDisposable.isDisposed()) {
      mDisposable.dispose();
    }
  }

  @Override
  public void setGiftItemViewStrategy(GiftItemViewStrategy giftItemViewStrategy) {
    mGiftItemViewStrategy = giftItemViewStrategy;
  }

  @Override
  public void setGiftDataSourceStrategy(GiftDataSourceStrategy giftDataSourceStrategy) {
    mGiftDataSourceStrategy = giftDataSourceStrategy;
  }

  private void initAdapter(GridViewPager viewPager) {
    if (mAdapter == null) {
      mAdapter = new Adapter();
      viewPager.setAdapter(mAdapter);
    }
    mDisposable.add(mGiftDataSourceStrategy
        .getGiftItemsObservable()
        .subscribe(liveGiftItems -> {
          mAdapter.mLiveGiftItemList = liveGiftItems;
          viewPager.notifyDataSetChanged();
        }, throwable -> {

        }));
  }

  private class Adapter extends BaseAdapter {
    private List<LiveGiftItem> mLiveGiftItemList;

    @Override
    public int getCount() {
      return mLiveGiftItemList == null ? 0 : mLiveGiftItemList.size();
    }

    @Override
    public Object getItem(int position) {
      return mLiveGiftItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
      return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      View giftView = mGiftItemViewStrategy
          .onCreateItemView(position, parent, (LiveGiftItem) getItem(position));
      return giftView;
    }
  }
}
