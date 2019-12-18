package com.humorous.myapplication.dagger;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.humorous.myapplication.R;
import com.humorousz.uiutils.view.BaseFragment;

public class DaggerFragment extends BaseFragment {
  @Override
  public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.dagger_fragment, container, false);
  }

  @Override
  public void initView(View root) {

  }
}
