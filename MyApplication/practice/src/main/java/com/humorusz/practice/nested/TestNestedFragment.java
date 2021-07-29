package com.humorusz.practice.nested;

import android.os.Bundle;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.humorousz.uiutils.view.BaseFragment;
import com.humorusz.practice.R;
import com.humorusz.practice.coordinatorTest.fragment.TestListFragment;
import com.humorusz.practice.nested.widget.TestImageView;

/**
 * Created by zhangzhiquan on 2017/6/12.
 */

public class TestNestedFragment extends BaseFragment {
    private FrameLayout mContainer;
    private TestImageView mImageView;
    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_test_nested_fragment,container,false);
    }

    @Override
    public void initView(View root) {
        FragmentTransaction tr = getChildFragmentManager().beginTransaction();
        tr.add(R.id.nested_content,new TestListFragment());
        tr.commit();

    }
}
