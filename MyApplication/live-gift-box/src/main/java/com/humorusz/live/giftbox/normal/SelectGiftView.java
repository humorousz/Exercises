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


import java.util.List;

import com.humorusz.live.giftbox.R;
import com.humorusz.live.giftbox.base.LiveGiftItem;

/**
 * @author Created by zhangzhiquan on 2017/8/3.
 */

public class SelectGiftView extends LinearLayout implements View.OnClickListener {
    private static final String TAG = "SelectGiftView";
    private Context mContext;
    private RecyclerView mRecycler;
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
        GiftStore.initData();
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
        mRecycler.setAdapter(new Adapter(GiftStore.mData));
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


    private class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        List<LiveGiftItem>  mDatas;
        public Adapter(List<LiveGiftItem> list){
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
            final GiftInfo info = (GiftInfo) mDatas.get(position);
            textView.setText(info.getName());
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
