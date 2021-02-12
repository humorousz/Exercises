package com.humorous.myapplication.liveroom.gift;

import java.util.Arrays;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.humorous.myapplication.R;
import com.humorusz.live.giftbox.base.LiveGifPanelView;
import com.humorusz.live.giftbox.base.LiveGiftPanelTabView;
import com.humorusz.live.giftbox.base.LiveNormalGiftTabView;
import com.humorusz.live.giftbox.normal.LiveNormalGiftDataSourceStrategy;
import com.humorusz.live.giftbox.normal.LiveNormalGiftDataSourceStrategy2;
import com.humorusz.live.giftbox.normal.LiveNormalGiftTabViewStrategy;

/**
 * 礼物面板
 *
 * @author zhangzhiquan
 * @date 2021/2/11
 */
public class LiveGiftPanelDialog extends DialogFragment {
  private View mEmptyView;
  private ViewGroup mGiftPanelContainer;
  public static LiveGiftPanelDialog newInstance() {
    return new LiveGiftPanelDialog();
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.live_gift_send_dialog, container, false);
    initView(rootView);
    return rootView;
  }

  private void initView(View rootView){
    mGiftPanelContainer = rootView.findViewById(R.id.live_gift_panel_container);
    mGiftPanelContainer.addView(createGiftBoxView());

    mEmptyView = rootView.findViewById(R.id.live_gift_panel_space);
    mEmptyView.setOnClickListener(v->{
      dismissAllowingStateLoss();
    });
  }


  private View createGiftBoxView() {
    LiveGifPanelView view = new LiveGifPanelView(getContext());

    LiveGiftPanelTabView giftPanelTabView = new LiveNormalGiftTabView();
    giftPanelTabView.setGiftDataSourceStrategy(new LiveNormalGiftDataSourceStrategy());
    giftPanelTabView.setGiftItemViewStrategy(new LiveNormalGiftTabViewStrategy());

    LiveGiftPanelTabView giftPanelTabView2 = new LiveNormalGiftTabView();
    giftPanelTabView2.setGiftDataSourceStrategy(new LiveNormalGiftDataSourceStrategy2());
    giftPanelTabView2.setGiftItemViewStrategy(new LiveNormalGiftTabViewStrategy());
    view.setGiftPanelTabItems(Arrays.asList(giftPanelTabView,giftPanelTabView2));
    return view;
  }

  @Override
  public void onStart() {
    Dialog dialog = getDialog();
    dialog
        .getWindow()
        .setFlags(
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
    super.onStart();
    if (dialog != null && dialog.getWindow() != null) {
      dialog.getWindow().setDimAmount(0f);
      dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
      dialog.getWindow().getDecorView().setPadding(0, 0, 0, 0);
      dialog.getWindow().setLayout(
          ViewGroup.LayoutParams.MATCH_PARENT,
          ViewGroup.LayoutParams.MATCH_PARENT);
      dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
    }
  }
}
