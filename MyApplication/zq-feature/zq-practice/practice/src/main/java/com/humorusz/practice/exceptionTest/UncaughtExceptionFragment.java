package com.humorusz.practice.exceptionTest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.humorousz.uiutils.view.BaseFragment;
import com.humorusz.practice.R;

/**
 * Created by zhangzhiquan on 2017/7/8.
 */

public class UncaughtExceptionFragment extends BaseFragment {
    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_test_fragment,container);
    }

    @Override
    public void initView(View root) {
        String s=null;
        if(s.equals("ddd")){
            return;
        }
    }
}
