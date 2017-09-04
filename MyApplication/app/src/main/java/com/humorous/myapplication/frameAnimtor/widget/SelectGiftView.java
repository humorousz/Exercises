package com.humorous.myapplication.frameAnimtor.widget;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.humorous.myapplication.R;
import com.humorousz.uiutils.widget.GridItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangzhiquan on 2017/8/3.
 */

public class SelectGiftView extends LinearLayout {
    private static String Path = "f3/";
    private Context mContext;
    private RecyclerView mRecycler;
    private List<GiftInfo> mData;
    private OnGiftItemClick mListener;
    public SelectGiftView(Context context) {
        this(context,null);
    }

    public SelectGiftView(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public SelectGiftView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initData();
        initView();
    }

    public void setOnGiftItemClickListener(OnGiftItemClick listener){
        mListener = listener;
    }

    private void initView(){
        View root = LayoutInflater.from(mContext).inflate(R.layout.layout_animtor_send_view,this);
        mRecycler  = (RecyclerView) root.findViewById(R.id.recycler_send_gift);
        mRecycler.setLayoutManager(new GridLayoutManager(mContext,3));
        mRecycler.addItemDecoration(new GridItemDecoration());
        mRecycler.setAdapter(new Adapter(mData));
    }

    private void initData(){
        mData = new ArrayList<>(12);
        mData.add(new GiftInfo("跑车","car.webp"));
        mData.add(new GiftInfo("新年快乐","newyear.webp"));
        mData.add(new GiftInfo("人民币","rmb.webp"));
        mData.add(new GiftInfo("鲜花","flower.webp"));
        mData.add(new GiftInfo("城堡","house.webp"));
        mData.add(new GiftInfo("邮轮","ship.webp"));
        mData.add(new GiftInfo("心动","animation_out.webp"));
        mData.add(new GiftInfo("香蕉","banana.webp"));
        mData.add(new GiftInfo("星星 ","star.webp"));
        mData.add(new GiftInfo("超跑 ","newcar.webp"));
    }

    private static class GiftInfo{
        String name;
        String path;
        public GiftInfo(String name,String path){
            this.name = name;
            this.path = Path + path;
        }
    }


    private class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        List<GiftInfo>  mDatas;
        public Adapter(List<GiftInfo> list){
            mDatas = list;
        }
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            TextView textView = new TextView(getContext());
            textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            textView.setTextSize(15);
            textView.setPadding(40,40,40,40);
            textView.setTextColor(getResources().getColorStateList(R.color.text));
            textView.setGravity(Gravity.CENTER);
            return new ViewHolder(textView);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ViewHolder viewHolder = (ViewHolder) holder;
            TextView textView = viewHolder.mText;
            final GiftInfo info = mDatas.get(position);
            textView.setText(info.name);
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener != null){
                        mListener.onGiftItemClick(info.path,info.name);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mDatas == null ? 0 : mDatas.size();
        }

        private class ViewHolder extends RecyclerView.ViewHolder{
            TextView mText;
            public ViewHolder(View itemView) {
                super(itemView);
                mText = (TextView) itemView;
            }
        }
    }


    public interface OnGiftItemClick{
        void onGiftItemClick(String path,String name);
    }
}
