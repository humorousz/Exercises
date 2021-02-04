package com.humorusz.live.giftbox.normal;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.humorusz.live.giftbox.R;
import com.humorusz.live.giftbox.base.LiveGiftItem;
import com.humorusz.live.giftbox.base.LiveGiftPanelTabView;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author zhangzhiquan
 * @date 2021/2/3
 */
class LiveNormalGiftTabView implements LiveGiftPanelTabView {
  private GiftItemViewStrategy mGiftItemViewStrategy;
  private GiftItemConfigStrategy mGiftItemConfigStrategy;
  private GiftDataSourceStrategy mGiftDataSourceStrategy;
  private LiveNormalGiftAdapter mLiveNormalGiftAdapter;
  private CompositeDisposable mDisposable = new CompositeDisposable();

  @Override
  public String getTitle() {
    return "普通礼物";
  }

  @Override
  public void sendGift(LiveGiftItem giftItem, long count) {

  }

  @Override
  public LiveGiftItem getSelectedGift() {
    return mLiveNormalGiftAdapter == null ? null : mLiveNormalGiftAdapter.getCurrentSelected();
  }

  @Override
  public void onCreateGiftPanelTabView(ViewGroup parent) {
    LayoutInflater.from(parent.getContext())
        .inflate(R.layout.live_gift_panel_item_view, parent);
    RecyclerView recyclerView = parent.findViewById(R.id.live_gift_panel_view_recycler_view);
    initLayoutManager(recyclerView);
    initAdapter(recyclerView);
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
  public void setGiftItemConfigStrategy(GiftItemConfigStrategy giftItemConfigStrategy) {
    mGiftItemConfigStrategy = giftItemConfigStrategy;
  }

  @Override
  public void setGiftDataSourceStrategy(GiftDataSourceStrategy giftDataSourceStrategy) {
    mGiftDataSourceStrategy = giftDataSourceStrategy;
  }


  private void initLayoutManager(RecyclerView recyclerView) {
    if (mGiftItemConfigStrategy == null) {
      throw new IllegalArgumentException(
          "must set mGiftItemConfigStrategy at setGiftItemConfigStrategy method");
    }
    if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
      GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
      gridLayoutManager.setOrientation(mGiftItemConfigStrategy.getOrientation());
      gridLayoutManager.setSpanCount(mGiftItemConfigStrategy.getSpanCount());
    }
  }

  private void initAdapter(final RecyclerView recyclerView) {
    if (mLiveNormalGiftAdapter == null) {
      mLiveNormalGiftAdapter = new LiveNormalGiftAdapter();
      recyclerView.setAdapter(mLiveNormalGiftAdapter);
    }
    mDisposable.add(mGiftDataSourceStrategy
        .getGiftItemsObservable()
        .subscribe(liveGiftItems -> {
          mLiveNormalGiftAdapter.setLiveGiftItems(liveGiftItems);
          mLiveNormalGiftAdapter.notifyDataSetChanged();
        }, throwable -> {

        }));
  }

  private class LiveNormalGiftAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<LiveGiftItem> mLiveGiftItems;
    private int mCurrentSelectedPosition = -1;

    public void setLiveGiftItems(List<LiveGiftItem> liveGiftItems) {
      mLiveGiftItems = liveGiftItems;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      return mGiftItemViewStrategy.createViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
      mGiftItemViewStrategy.onBindViewHolder(holder, mLiveGiftItems.get(position), position);
      holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          LiveGiftItem lastSelected = getGiftItem(mCurrentSelectedPosition);
          LiveGiftItem current = getGiftItem(position);
          if (lastSelected != null) {
            lastSelected.setSelected(false);
          }
          if (current != null) {
            current.setSelected(true);
          }
          mLiveNormalGiftAdapter.notifyItemChanged(mCurrentSelectedPosition);
          mLiveNormalGiftAdapter.notifyItemChanged(position);
          mCurrentSelectedPosition = position;
        }
      });
    }

    @Override
    public int getItemCount() {
      return mLiveGiftItems == null ? 0 : mLiveGiftItems.size();
    }

    @Nullable
    private LiveGiftItem getGiftItem(int position) {
      if (mLiveGiftItems == null || position < 0 || position >= mLiveGiftItems.size()) {
        return null;
      }
      return mLiveGiftItems.get(position);
    }

    public LiveGiftItem getCurrentSelected() {
      return getGiftItem(mCurrentSelectedPosition);
    }
  }
}
