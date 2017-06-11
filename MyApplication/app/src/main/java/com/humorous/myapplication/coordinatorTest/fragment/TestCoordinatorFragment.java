package com.humorous.myapplication.coordinatorTest.fragment;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.humorous.myapplication.R;
import com.humorousz.commonutils.log.Logger;
import com.humorousz.uiutils.view.BaseFragment;

/**
 * Created by zhangzhiquan on 2017/6/7.
 */

public class TestCoordinatorFragment extends BaseFragment {
    private static final String TAG = "TestCoordinatorFragment";
    private TextView mText;
    private Button mBtn;

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_test_cordinator,container,false);
    }

    @Override
    public void initView(View root) {
        Toolbar toolbar = (Toolbar) root.findViewById(R.id.toolbar);
        toolbar.setTitle("这里是Title");
        toolbar.setSubtitle("这里是子标题");
        mText = (TextView) root.findViewById(R.id.textView);
        mBtn = (Button)root.findViewById(R.id.btn);
        mText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) mText.getLayoutParams();
        if(params.getBehavior() != null){
            Logger.d(TAG,"haha");
        }

    }

    @Override
    public String getLogTitle() {
        return TAG;
    }
}
