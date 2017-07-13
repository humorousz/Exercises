package com.humorous.myapplication.drawerLayout;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.humorous.myapplication.R;
import com.humorousz.uiutils.view.BaseFragment;

/**
 * Created by zhangzhiquan on 2017/7/12.
 * 参考简书：http://www.jianshu.com/p/0026621c70bc
 */

public class DrawerLayoutFragment extends BaseFragment {
    private Toolbar mToolbar;
    private DrawerLayout mDrawerlayout;
    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_fragment_drawer_layout,container,false);
    }

    @Override
    public void initView(View root) {
        mToolbar = (Toolbar) root.findViewById(R.id.id_drawer_layout_toolbar);
        mDrawerlayout = (DrawerLayout) root.findViewById(R.id.id_drawer_layout);
        //DrawerLayout监听器
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(),
                mDrawerlayout,
                mToolbar,
                R.string.app_name,
                R.string.app_name
        );
        mDrawerlayout.addDrawerListener(toggle);
        toggle.syncState();
    }
}
