package com.humorous.myapplication.jetpack;

import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.humorous.myapplication.R;
import com.humorousz.uiutils.view.BaseFragment;

public class JetpackFragment extends BaseFragment {

  private JetpackViewModel mViewModel;

  public static JetpackFragment newInstance() {
    return new JetpackFragment();
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    mViewModel = ViewModelProviders.of(this).get(JetpackViewModel.class);
    mViewModel.getLiveData().observe(this, jetLiveData -> {
      TextView textView = getView().findViewById(R.id.text);
      textView.setText(jetLiveData.time);
    });
  }

  @Override
  public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.jetpack_fragment, container, false);
  }

  @Override
  public void initView(View root) {
    root.findViewById(R.id.click_button).setOnClickListener(v -> {
      mViewModel.getCurrentTime();
    });
  }

}
