package com.humorousz.home.home.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.humorousz.home.R;
import com.humorousz.home.config.router.Router;
import com.humorousz.home.home.model.HomeItemModel;

import java.util.List;

/**
 * Created by zhangzhiquan on 2017/6/5.
 */

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
    implements HomeHolder.OnItemClickListener {
  private static final String TAG = "HomeAdapter";
  private List<HomeItemModel> mList;
  private Context mContext;

  public HomeAdapter(Context context, List<HomeItemModel> list) {
    mList = list;
    mContext = context;
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.layout_home_fragment_item, parent, false);
    return new HomeHolder(view, this);
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    if (holder instanceof HomeHolder) {
      HomeHolder homeHolder = (HomeHolder) holder;
      homeHolder.bindData(mList.get(position).getTitle(), position);
    }
  }

  @Override
  public int getItemCount() {
    return mList == null ? 0 : mList.size();
  }


  @Override
  public void onItemClick(int position) {
    String link = mList.get(position).getLink();
    String title = mList.get(position).getTitle();
    Router.jumpTo(mContext, link, title);
  }
}
