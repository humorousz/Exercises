package com.humorous.myapplication.diffutil;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.humorous.myapplication.R;
import com.humorousz.uiutils.helper.UIUtils;

import java.util.List;

/**
 * @author zhangzhiquan
 * @date 2018/9/19
 */
public class DiffAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<CharSequence> mList;

    public void setList(List<CharSequence> list) {
        this.mList = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_test_item,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List<Object> payloads) {
        if(payloads.isEmpty()){
            this.onBindViewHolder(holder,position);
            return;
        }
        Bundle payload = (Bundle) payloads.get(0);
        CharSequence charSequence = payload.getCharSequence("Char","");
        ViewHolder holder1 = (ViewHolder) holder;
        ViewGroup.LayoutParams params = holder1.textView.getLayoutParams();
        params.height = UIUtils.dip2px(50);
        holder1.textView.setLayoutParams(params);
        holder1.textView.setText(charSequence);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder holder1 = (ViewHolder) holder;
        ViewGroup.LayoutParams params = holder1.textView.getLayoutParams();
        params.height = UIUtils.dip2px(50);
        holder1.textView.setLayoutParams(params);
        holder1.textView.setText(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.item_text);
        }
    }
}
