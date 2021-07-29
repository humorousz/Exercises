package com.humorusz.practice.diffutil;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.humorousz.uiutils.view.BaseFragment;
import com.humorousz.uiutils.widget.LinearItemDecoration;
import com.humorusz.practice.R;

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

    @SuppressLint("WrongConstant")
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
        for(int i=0; i < 5; i++){
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
//        list.add(1,"i am the new");
//        mAdapter.notifyDataSetChanged();
        onRefreshV3();
    }

    private void onRefresh() {
        List<CharSequence> newList = new LinkedList<>();
        newList.addAll(list);

        newList.add("i am the new " + newList.size());
        newList.set(0, "abcdefg");
        CharSequence charSequence = newList.get(1);
        newList.remove(charSequence);
        newList.add(charSequence);
        list = newList;
        mAdapter.setList(list);
        mAdapter.notifyDataSetChanged();
    }

    private void onRefreshV2() {
        List<CharSequence> newList = new LinkedList<>();
        newList.addAll(list);

        newList.add("i am the new " + newList.size());
        newList.set(0, "abcdefg");
        CharSequence charSequence = newList.get(1);
        newList.remove(charSequence);
        newList.add(charSequence);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffCallBack(list,newList));
        list = newList;
        mAdapter.setList(list);
        result.dispatchUpdatesTo(mAdapter);
    }

    private void onRefreshV3() {
        List<CharSequence> newList = new LinkedList<>();
        newList.addAll(list);
        newList.add(0,"i am the new " + newList.size());
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffCallBack(list,newList),true);
        list = newList;
        mAdapter.setList(list);
        result.dispatchUpdatesTo(mAdapter);
    }
}
