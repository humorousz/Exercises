package com.humorous.myapplication.nestedScrollView;

import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.humorous.myapplication.R;
import com.humorousz.uiutils.view.BaseFragment;
import com.humorousz.uiutils.widget.LinearItemDecoration;

/**
 * Created by zhangzhiquan on 2017/6/27.
 */

public class NestScrollViewFragment extends BaseFragment {
    private RecyclerView mReyclerView;
    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_fragment_nested_scroll_view,container,false);
    }

    @Override
    public void initView(View root) {
        mReyclerView = root.findViewById(R.id.recycler);
        mReyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        mReyclerView.addItemDecoration(new LinearItemDecoration());
        mReyclerView.setAdapter(adapter);
    }

    RecyclerView.Adapter adapter = new RecyclerView.Adapter() {
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ViewHolder holder = new ViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.layout_test_item,parent,false));
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ViewHolder holder1 = (ViewHolder) holder;
            holder1.textView.setText("abcd"+position);
        }

        @Override
        public int getItemCount() {
            return 50;
        }


        class ViewHolder extends RecyclerView.ViewHolder{
            TextView textView;
            public ViewHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView.findViewById(R.id.item_text);
            }
        }


    };
}
