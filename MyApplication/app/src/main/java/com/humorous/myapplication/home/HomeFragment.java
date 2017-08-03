package com.humorous.myapplication.home;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.humorous.myapplication.R;
import com.humorous.myapplication.home.adapter.HomeAdapter;
import com.humorous.myapplication.config.api.API;
import com.humorousz.uiutils.view.BaseFragment;
import com.humorousz.uiutils.widget.GridItemDecoration;

/**
 * Created by zhangzhiquan on 2017/6/4.
 */

public class HomeFragment extends BaseFragment {
    private static final String TAG = "HomeFragment";
    private RecyclerView mRecyclerView;
    private API.SECOND_MENU type;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = (API.SECOND_MENU) getArguments().getSerializable("type");
    }

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_home_fragment,container,false);
    }

    @Override
    public void initView(View root) {
        mRecyclerView = (RecyclerView) root.findViewById(R.id.recycler_home);
        mRecyclerView.setAdapter(new HomeAdapter(getContext(),API.getList(type)));
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.addItemDecoration(new GridItemDecoration());
    }

    @Override
    public String getLogTitle() {
        return TAG;
    }

    public static HomeFragment newInstance(API.SECOND_MENU type) {
        
        Bundle args = new Bundle();
        args.putSerializable("type",type);
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

}
