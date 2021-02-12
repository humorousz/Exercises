package com.humorous.myapplication.liveroom.gift;

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

/**
 * 礼物面板
 *
 * @author zhangzhiquan
 * @date 2021/2/11
 */
public class LiveGiftPanelDialog extends DialogFragment {
  private View mEmptyView;
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
    mEmptyView = rootView.findViewById(R.id.live_gift_panel_space);
    mEmptyView.setOnClickListener(v->{
      dismissAllowingStateLoss();
    });
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
