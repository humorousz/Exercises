package com.example.zhangzhiquan.myapplication.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zhangzhiquan.myapplication.R;

/**
 * Created by zhangzhiquan on 2017/4/4.
 */

public class ChildAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_recycler_item,parent,false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder holder2 = (ViewHolder) holder;
        holder2.mText.setText("this is :"+position);
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView mText;
        public ViewHolder(View itemView) {
            super(itemView);
            mText = (TextView) itemView.findViewById(R.id.child_text);
        }
    }
}
