package com.humorous.myapplication.coordinatorTest.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.humorous.myapplication.R;
import com.humorousz.uiutils.view.BaseFragment;

/**
 * Created by zhangzhiquan on 2017/6/9.
 */

public class AdvancedCoordinatorFragment extends BaseFragment {
    private ImageView mImage;
    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_test_advanced_coordinator_fragment,container,false);
    }

    @Override
    public void initView(View root) {
//        mContainer = (FrameLayout) root.findViewById(R.id.advanced_container);
        mImage = (ImageView) root.findViewById(R.id.advance_image);
        FragmentTransaction tr = getChildFragmentManager().beginTransaction();
        tr.add(R.id.advanced_container,new TestListFragment());
        tr.commit();

    }
}
