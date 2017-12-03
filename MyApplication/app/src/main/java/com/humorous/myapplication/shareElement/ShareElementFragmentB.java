package com.humorous.myapplication.shareElement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.humorous.myapplication.R;
import com.humorousz.uiutils.view.BaseFragment;

/**
 * Created by zhangzhiquan on 2017/12/3.
 */

public class ShareElementFragmentB extends BaseFragment {
    private ImageView mShareImg;
    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_fragment_share_b,container,false);
    }

    @Override
    public void initView(View root) {
        mShareImg = root.findViewById(R.id.share_b_img);
    }
}
