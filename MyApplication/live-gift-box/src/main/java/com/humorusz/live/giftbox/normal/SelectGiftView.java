package com.humorusz.live.giftbox.normal;

import android.content.Context;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import com.humorusz.live.giftbox.R;

/**
 * @author Created by zhangzhiquan on 2017/8/3.
 */

public class SelectGiftView extends LinearLayout implements View.OnClickListener {
    private static final String TAG = "SelectGiftView";
    private static String Path = "f3/";
    private Context mContext;
    private RecyclerView mRecycler;
    private List<GiftInfo> mData;
    private OnGiftItemClick mListener;
    private OnSpaceClick mSpaceListener;
    public SelectGiftView(Context context) {
        this(context,null);
    }

    public SelectGiftView(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public SelectGiftView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        setOnClickListener(this);
        initData();
        initView();
    }

    public void setOnGiftItemClickListener(OnGiftItemClick listener){
        mListener = listener;
    }

    public void setSpaceListener(OnSpaceClick mSpaceListener) {
        this.mSpaceListener = mSpaceListener;
    }

    private void initView(){
        View root = LayoutInflater.from(mContext).inflate(R.layout.layout_animtor_send_view,this);
        mRecycler  = root.findViewById(R.id.recycler_send_gift);
        mRecycler.setLayoutManager(new GridLayoutManager(mContext,3));
        mRecycler.setAdapter(new Adapter(mData));
    }

    private void initData(){
        mData = new ArrayList<>(12);
        mData.add(new GiftInfo("奢华蛋糕","cake.webp"));
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

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if(mSpaceListener != null){
            mSpaceListener.clickGiftPanelSpace();
        }
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
            textView.setTextColor(getResources().getColorStateList(R.color.design_default_color_primary));
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
        /**
         * 点击礼物item
         * @param path
         * @param name
         */
        void onGiftItemClick(String path,String name);
    }

    public interface OnSpaceClick{
        void clickGiftPanelSpace();
    }
}
