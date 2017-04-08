package com.example.zhangzhiquan.myapplication.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.zhangzhiquan.myapplication.R;
import com.example.zhangzhiquan.myapplication.adapter.ChildAdapter;

/**
 * Created by zhangzhiquan on 2017/4/4.
 */

public class ViewCard extends LinearLayout {
    Context mContext;
    InnerRecyclerView recyclerView;
    public ViewCard(Context context) {
        this(context,null);
    }

    public ViewCard(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ViewCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }


    private void initView(){
        View root = LayoutInflater.from(mContext).inflate(R.layout.parent_recycler_item,this);
        recyclerView = (InnerRecyclerView) root.findViewById(R.id.parent_recycler);
        ChildAdapter adapter= new ChildAdapter();
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }
}
