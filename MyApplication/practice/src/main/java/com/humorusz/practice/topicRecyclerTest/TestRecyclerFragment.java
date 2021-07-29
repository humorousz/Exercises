package com.humorusz.practice.topicRecyclerTest;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.humorousz.uiutils.helper.ToastUtil;
import com.humorousz.uiutils.helper.UIUtils;
import com.humorousz.uiutils.view.BaseFragment;
import com.humorousz.uiutils.widget.LinearItemDecoration;
import com.humorusz.practice.R;


/**
 * Created by zhangzhiquan on 2017/6/1.
 */

public class TestRecyclerFragment extends BaseFragment{
    RecyclerView mRecycler;
    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_test_list_fragment,container,false);
    }

    @Override
    public void initView(View root) {
        mRecycler = (RecyclerView) root.findViewById(R.id.test_list_recycler_view);
        mRecycler.setLayoutManager(new GridLayoutManager(getContext(),2));
        mRecycler.addItemDecoration(new LinearItemDecoration());
        mRecycler.setAdapter(adapter);
        mRecycler.addOnScrollListener(mScrollListener);


    }

    @Override
    public String getLogTitle() {
        return null;
    }

    @Override
    public String getTitle() {
        return "test";
    }

    RecyclerView.OnScrollListener mScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
            if(manager instanceof GridLayoutManager){
                GridLayoutManager gridLayoutManager = (GridLayoutManager) manager;
                int firstVisiblePos = gridLayoutManager.findFirstVisibleItemPosition();
                setViewAplha(gridLayoutManager.findViewByPosition(firstVisiblePos));
                setViewAplha(gridLayoutManager.findViewByPosition(firstVisiblePos+1));

                int firstCompletelyVisible = gridLayoutManager.findFirstCompletelyVisibleItemPosition();
                ItemView view1 = (ItemView) gridLayoutManager.findViewByPosition(firstCompletelyVisible);
                ItemView view2 = (ItemView) gridLayoutManager.findViewByPosition(firstCompletelyVisible + 1);
                if(view1 != null){
                    view1.setAlpha(1);
                }
                if(view2 != null){
                    view2.setAlpha(1);
                }

            }


        }
    };


    float beginPercent = 0.2f;
    float endValue = 2;

    private void setViewAplha(View view){
        if (view == null || !(view instanceof ItemView))
            return;
        float p = UIUtils.px2dip(Math.abs((int) view.getY())) * 1.0f / UIUtils.px2dip(view.getHeight()) * 1.0f;
        float curPercent = Float.compare(p - beginPercent, 0.0f) < 0 ? 0.0f : p - beginPercent;
        curPercent = Float.compare(1, curPercent * endValue) < 0 ? 1 : curPercent * endValue;
        view.setAlpha(1 - curPercent);
    }


    RecyclerView.Adapter adapter = new RecyclerView.Adapter() {
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ItemView itemView = new ItemView(parent.getContext());
            ViewHolder holder = new ViewHolder(itemView);
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ViewHolder holder1 = (ViewHolder) holder;
            holder1.itemView.setText("我是Item"+position);
            holder1.itemView.setAlpha(1);
        }

        @Override
        public int getItemCount() {
            return 40;
        }


        class ViewHolder extends RecyclerView.ViewHolder{
            ItemView itemView;
            public ViewHolder(View itemView) {
                super(itemView);
                this.itemView = (ItemView) itemView;
            }
        }


    };

    private static class ItemView extends LinearLayout{
        TextView textView;
        Context mContext;
        public ItemView(Context context) {
            super(context);
            mContext = context;
            init();
        }

        private void init(){
            View root = LayoutInflater.from(mContext).inflate(R.layout.layout_test_item,this);
            textView = (TextView) root.findViewById(R.id.item_text);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.showToast(getContext(),"my alpha:"+v.getAlpha());
                }
            });
        }

        public void setText(String text){
            textView.setText(text);
        }

        public void setAlpha(float alpha){
            textView.setAlpha(alpha);
        }
    }
}
