package com.humorous.myapplication.coordinatorTest.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.humorous.myapplication.R;
import com.humorousz.uiutils.view.BaseFragment;

/**
 * Created by zhangzhiquan on 2017/6/7.
 */

public class TestCordinatorFragment extends BaseFragment {
    private static final String TAG = "TestCordinatorFragment";
    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_test_cordinator,container,false);
    }

    @Override
    public void initView(View root) {

    }

    @Override
    public String getLogTitle() {
        return TAG;
    }
}
