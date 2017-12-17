package com.humorous.myapplication.shader;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.humorous.myapplication.R;
import com.humorousz.uiutils.view.BaseFragment;

/**
 * Created by zhangzhiquan on 2017/12/17.
 * @author zhangzhiquan
 */

public class ShaderTestFragment  extends BaseFragment{
    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_fragment_test_shader,container,false);
    }

    @Override
    public void initView(View root) {

    }
}
