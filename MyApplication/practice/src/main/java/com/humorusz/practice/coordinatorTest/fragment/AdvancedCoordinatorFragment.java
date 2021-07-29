package com.humorusz.practice.coordinatorTest.fragment;

import android.os.Bundle;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.humorousz.uiutils.helper.StatusBarUtil;
import com.humorousz.uiutils.view.BaseFragment;
import com.humorousz.uiutils.view.ImmerseActivity;
import com.humorusz.practice.R;

/**
 * Created by zhangzhiquan on 2017/6/9.
 */

public class AdvancedCoordinatorFragment extends BaseFragment {
    private ImageView mImage;
    private Toolbar mToolBar;
    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_test_advanced_coordinator_fragment,container,false);
    }

    @Override
    public void initView(View root) {
//        mContainer = (FrameLayout) root.findViewById(R.id.advanced_container);
        mToolBar = (Toolbar) root.findViewById(R.id.toolbar);
        mToolBar.setTitle("哈哈哈哈");
        mImage = (ImageView) root.findViewById(R.id.advance_image);
        FragmentTransaction tr = getChildFragmentManager().beginTransaction();
        tr.add(R.id.advanced_container,new TestListFragment());
        tr.commit();
        setStatusBar();
    }
    private void setStatusBar(){
        if(getActivity() instanceof ImmerseActivity){
            StatusBarUtil.clearTranslucentForImageView(getActivity(),getActivity().findViewById(((ImmerseActivity) getActivity()).getPaddingStatusViewId()));
            StatusBarUtil.setColor(getActivity(),0,255);
        }
        StatusBarUtil.setTranslucentForRootPadding(getActivity(),0,mToolBar);
    }
}
