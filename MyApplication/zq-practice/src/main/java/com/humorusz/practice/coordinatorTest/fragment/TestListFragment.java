package com.humorusz.practice.coordinatorTest.fragment;

import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.humorousz.uiutils.view.BaseFragment;
import com.humorousz.uiutils.widget.LinearItemDecoration;
import com.humorusz.practice.R;


/**
 * Created by zhangzhiquan on 2017/5/31.
 */

public class TestListFragment extends BaseFragment {
    RecyclerView mRecycler;
    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_test_list_fragment,container,false);
    }

    @Override
    public void initView(View root) {
        mRecycler = (RecyclerView) root.findViewById(R.id.test_list_recycler_view);
//        mRecycler.setLayoutManager(new LinearLayoutManager(getContext(), OrientationHelper.VERTICAL, false));
        mRecycler.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        mRecycler.addItemDecoration(new LinearItemDecoration());
        mRecycler.setAdapter(adapter);


    }

    @Override
    public String getLogTitle() {
        return null;
    }

    @Override
    public String getTitle() {
        return "test";
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
            return 30;
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
