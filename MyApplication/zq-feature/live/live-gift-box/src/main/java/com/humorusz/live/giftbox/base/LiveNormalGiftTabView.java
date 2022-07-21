package com.humorusz.live.giftbox.base;

import java.util.List;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.recyclerview.widget.RecyclerView;

import com.humorousz.uiutils.widget.PagerGridLayoutManager;
import com.humorusz.live.giftbox.R;
import com.humorusz.live.giftbox.widget.GridViewPager;

import io.reactivex.disposables.CompositeDisposable;

/**
 * 普通礼物面板
 *
 * @author zhangzhiquan
 * @date 2021/2/3
 */
public class LiveNormalGiftTabView implements LiveGiftPanelTabView {
  private GiftDataSourceStrategy mGiftDataSourceStrategy;
  private GiftSendStrategy mGiftSendStrategy;
  private OnGiftItemClickListener mOnGiftItemClickListener;
  private CompositeDisposable mDisposable = new CompositeDisposable();
  private RecyclerView mRV;
  private LiveNormalGiftAdapter mLiveNormalGiftAdapter;
  private int mSelectedPosition = -1;

  private GiftDefaultSelectedStrategy mDefaultSelectedStrategy = currentSize -> 0;

  @Override
  public String getTitle() {
    return "普通礼物";
  }

  @Override
  public void sendGift(LiveGiftItem giftItem, long count) {
    if (mGiftSendStrategy != null) {
      mGiftSendStrategy.sendGift(giftItem, count);
    }
  }

  @Override
  public LiveGiftItem getSelectedGift() {
    return mLiveNormalGiftAdapter.mLiveGiftItemList.get(mLiveNormalGiftAdapter.mSelectedPosition);
  }

  @Override
  public void onCreateGiftPanelTabView(ViewGroup parent) {
    LayoutInflater.from(parent.getContext())
        .inflate(R.layout.live_gift_panel_item_view, parent);
    mRV = parent.findViewById(R.id.live_gift_panel_rv);
    initRv(mRV);
  }

  @Override
  public void onGiftPanelDestroy() {
    if (mDisposable != null && !mDisposable.isDisposed()) {
      mDisposable.dispose();
    }
  }

  @Override
  public void setGiftItemViewStrategy(GiftItemViewStrategy giftItemViewStrategy) {
  }

  @Override
  public void setGiftDataSourceStrategy(GiftDataSourceStrategy giftDataSourceStrategy) {
    mGiftDataSourceStrategy = giftDataSourceStrategy;
  }

  @Override
  public void setGiftDefaultSelectedStrategy(
      GiftDefaultSelectedStrategy giftDefaultSelectedStrategy) {
    mDefaultSelectedStrategy = giftDefaultSelectedStrategy;
  }

  @Override
  public void setGiftSendStrategy(GiftSendStrategy giftSendStrategy) {
    mGiftSendStrategy = giftSendStrategy;
  }

  @Override
  public void setOnGiftItemClickListener(OnGiftItemClickListener listener) {
    mOnGiftItemClickListener = listener;
  }

  @SuppressLint("NotifyDataSetChanged")
  private void initRv(RecyclerView recyclerView) {
    PagerGridLayoutManager pagerGridLayoutManager = new PagerGridLayoutManager(2,4);
    recyclerView.setLayoutManager(pagerGridLayoutManager);
    if (mLiveNormalGiftAdapter == null) {
      mLiveNormalGiftAdapter = new LiveNormalGiftAdapter();
      recyclerView.setAdapter(mLiveNormalGiftAdapter);
      mLiveNormalGiftAdapter.mOnGiftItemClickListener = mOnGiftItemClickListener;
    }
    mDisposable.add(mGiftDataSourceStrategy
        .getGiftItemsObservable()
        .subscribe(liveGiftItems -> {
          mLiveNormalGiftAdapter.mLiveGiftItemList = liveGiftItems;
          if (mDefaultSelectedStrategy != null) {
            mSelectedPosition =
                mDefaultSelectedStrategy.getDefaultSelectedPosition(liveGiftItems.size());
            mLiveNormalGiftAdapter.mSelectedPosition = mSelectedPosition;
          }
          mLiveNormalGiftAdapter.notifyDataSetChanged();
        }, throwable -> {

        }));
  }
}
