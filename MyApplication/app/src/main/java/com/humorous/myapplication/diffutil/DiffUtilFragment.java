package com.humorous.myapplication.diffutil;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.humorous.myapplication.R;
import com.humorousz.uiutils.view.BaseFragment;
import com.humorousz.uiutils.widget.LinearItemDecoration;

import java.util.LinkedList;
import java.util.List;

/**
 * @author zhangzhiquan
 * @date 2018/9/19
 */
public class DiffUtilFragment extends BaseFragment implements View.OnClickListener {
    RecyclerView mRecycler;
    private DiffAdapter mAdapter;
    private Button mButton;
    private List<CharSequence> list;
    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_fragment_diff_utils,container,false);
    }

    @Override
    public void initView(View root) {
        mRecycler = root.findViewById(R.id.test_list_recycler_view);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext(), OrientationHelper.VERTICAL, false));
        mRecycler.addItemDecoration(new LinearItemDecoration());
        mAdapter = new DiffAdapter();
        mButton = root.findViewById(R.id.add_btn);
        mButton.setOnClickListener(this);
        list = new LinkedList<>();
        initData();
        mAdapter.setList(list);
        mRecycler.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private void initData(){
        for(int i=0; i < 30; i++){
            list.add("i am the "+i+"th");
        }
    }

    @Override
    public String getLogTitle() {
        return null;
    }

    @Override
    public String getTitle() {
        return "test";
    }

    @Override
    public void onClick(View v) {

    }
}
