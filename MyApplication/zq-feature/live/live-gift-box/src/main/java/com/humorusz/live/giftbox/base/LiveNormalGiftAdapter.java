package com.humorusz.live.giftbox.base;

import java.util.List;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.humorusz.live.giftbox.R;

/**
 * Description:
 * <p>
 * author：zhangzhiquan
 * Date: 2022/7/21
 */
public class LiveNormalGiftAdapter extends RecyclerView.Adapter<LiveNormalGiftAdapter.ViewHolder> {
  public List<LiveGiftItem> mLiveGiftItemList;
  public View mSelectedView;
  public int mSelectedPosition = 0;
  public LiveGiftPanelTabView.OnGiftItemClickListener mOnGiftItemClickListener;

  private int ss = 0;

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    ss++;
    Log.d("MRRRR", "onBindViewHolder :" + ss);
    return new ViewHolder(LayoutInflater.from(parent.getContext())
        .inflate(R.layout.live_gift_panel_normal_item_view, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    LiveGiftItem item = mLiveGiftItemList.get(holder.getAdapterPosition());
    holder.textView.setText(item.getName());
    if (mSelectedPosition == holder.getAdapterPosition()) {
      holder.itemView.setBackgroundColor(Color.RED);
      if(mSelectedView == null){
        mSelectedView = holder.itemView;
      }
    } else {
      holder.itemView.setBackgroundColor(Color.TRANSPARENT);
    }
    holder.itemView.setOnClickListener(v -> {
      if (mSelectedPosition == position) {
        return;
      }
      if (mSelectedView != null) {
        mSelectedView.setBackgroundColor(Color.TRANSPARENT);
      }
      holder.itemView.setBackgroundColor(Color.RED);
      mSelectedView = holder.itemView;
      mSelectedPosition = holder.getAdapterPosition();
      if (mOnGiftItemClickListener != null) {
        mOnGiftItemClickListener.onGiftItemClick(holder.getAdapterPosition(), item);
      }
    });
  }

  @Override
  public int getItemCount() {
    return mLiveGiftItemList == null ? 0 : mLiveGiftItemList.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    TextView textView;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      textView = itemView.findViewById(R.id.live_normal_gift_view);
    }
  }
}
