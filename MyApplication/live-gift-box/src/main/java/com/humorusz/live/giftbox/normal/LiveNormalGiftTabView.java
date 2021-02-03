package com.humorusz.live.giftbox.normal;

import java.util.List;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.humorusz.live.giftbox.R;
import com.humorusz.live.giftbox.base.LiveGiftItem;
import com.humorusz.live.giftbox.base.LiveGiftPanelTabView;

/**
 * @author zhangzhiquan
 * @date 2021/2/3
 */
class LiveNormalGiftTabView implements LiveGiftPanelTabView {
  private GiftItemViewStrategy mGiftItemViewStrategy;
  private GiftItemConfigStrategy mGiftItemConfigStrategy;
  private GiftDataSourceStrategy mGiftDataSourceStrategy;
  private Observer<List<LiveGiftItem>> mListObserver;
  private LifecycleOwner mLifecycleOwner;
  private LiveNormalGiftAdapter mLiveNormalGiftAdapter;
  private LiveData<List<LiveGiftItem>> mListLiveData;

  @Override
  public String getTitle() {
    return "普通礼物";
  }

  @Override
  public void sendGift(LiveGiftItem giftItem, long count) {

  }

  @Override
  public LiveGiftItem getSelectedGift() {
    return null;
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
  public void setGiftItemViewStrategy(GiftItemViewStrategy giftItemViewStrategy) {
    mGiftItemViewStrategy = giftItemViewStrategy;
  }

  @Override
  public void setGiftItemConfigStrategy(GiftItemConfigStrategy giftItemConfigStrategy) {
    mGiftItemConfigStrategy = giftItemConfigStrategy;
  }

  @Override
  public void setGiftDataSourceStrategy(
      LifecycleOwner owner,
      GiftDataSourceStrategy giftDataSourceStrategy) {
    mGiftDataSourceStrategy = giftDataSourceStrategy;
    mLifecycleOwner = owner;
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

  private void initAdapter(RecyclerView recyclerView) {
    if (mListObserver == null) {
      mListObserver = new Observer<List<LiveGiftItem>>() {
        @Override
        public void onChanged(List<LiveGiftItem> liveGiftItems) {

        }
      };
    }
    if(mLiveNormalGiftAdapter == null){
      mLiveNormalGiftAdapter = new LiveNormalGiftAdapter();
    }
    mListLiveData = mGiftDataSourceStrategy.getGiftItemsDataSource();
    mListLiveData.observe(mLifecycleOwner, mListObserver);
    mLiveNormalGiftAdapter.notifyDataSetChanged();

  }

  private static class LiveNormalGiftAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private LiveData<List<LiveGiftItem>> mListLiveData;
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
      return 0;
    }
  }
}
