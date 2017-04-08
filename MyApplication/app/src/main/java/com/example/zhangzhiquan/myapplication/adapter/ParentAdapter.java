package com.example.zhangzhiquan.myapplication.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zhangzhiquan.myapplication.R;
import com.example.zhangzhiquan.myapplication.widget.InnerRecyclerView;
import com.example.zhangzhiquan.myapplication.widget.OuterRecyclerView;
import com.example.zhangzhiquan.myapplication.widget.ViewCard;

/**
 * Created by zhangzhiquan on 2017/4/4.
 */

public class ParentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


    OuterRecyclerView container;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public void setContainer(OuterRecyclerView container) {
        this.container = container;
    }

    private class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View itemView) {
            super(itemView);

        }
    }
}
