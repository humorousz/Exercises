package com.humorous.myapplication.liveroom.adapter;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.humorous.myapplication.R;
import com.humorous.myapplication.liveroom.module.IChatMessage;
import com.humorousz.commonutils.handler.HandlerCallback;
import com.humorousz.commonutils.handler.NormalHandler;
import com.humorousz.commonutils.log.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangzhiquan
 * @date 2018/2/5
 * 聊天控件适配器
 */

public class ChatBoxAdapter extends RecyclerView.Adapter<ChatBoxAdapter.ViewHolder> {
    private static final int MSG_REFRESH = 0x001;
    private static final int MAX_CHAT_LIST_LENGTH = 100;
    private static final int MAX_CHAT_CACHE_LENGTH = 100;

    private RecyclerView mRecyclerView;
    private List<IChatMessage> mLiveCommentItem = new ArrayList<>();
    private List<IChatMessage> mCacheList = new ArrayList<>();

    private Handler handler = new NormalHandler(new HandlerCallback(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MSG_REFRESH) {
                try {
                    synchronized (ChatBoxAdapter.class) {
                        notifyDataSetChanged();
                        scrollToEnd();
                    }
                } catch (Exception e) {

                }
            }
        }
    });

    public void refreshData() {
        handler.sendEmptyMessage(MSG_REFRESH);
    }

    public void clearData() {
        synchronized (ChatBoxAdapter.class){
            mLiveCommentItem.clear();
            notifyDataSetChanged();
        }
    }

    public void notifyAddItem(IChatMessage msgInfo) {
        synchronized (ChatBoxAdapter.class) {
            if (isSlideToBottom()) {
                mLiveCommentItem.add(msgInfo);
                if(mLiveCommentItem.size() != 0){
                    Logger.d("chatBox","notify size:"+mLiveCommentItem.size());
                    notifyItemInserted(mLiveCommentItem.size() - 1);
                }
                if (mLiveCommentItem.size() > MAX_CHAT_LIST_LENGTH) {
                    mLiveCommentItem.remove(0);
                    notifyRemoveItem(0);
                }
                scrollToEnd();
            } else {
                if (mCacheList.size() < MAX_CHAT_CACHE_LENGTH) {
                    mCacheList.add(msgInfo);
                }
            }
        }
    }

    public void notifyAddList(List<IChatMessage> list){
        if(list == null || list.size() == 0){
            return;
        }
        for(IChatMessage module : list){
            notifyAddItem(module);
        }
    }

    public void addCacheData() {
        try {
            synchronized (ChatBoxAdapter.class) {
                if (mRecyclerView != null) {
                    if (mCacheList.size() == 0) {
                        return;
                    }
                    int len = mCacheList.size();
                    for (int i = 0; i < len; i++) {
                        IChatMessage item = mCacheList.get(i);
                        if (mLiveCommentItem.size() > MAX_CHAT_LIST_LENGTH) {
                            mLiveCommentItem.remove(0);
                        }
                        mLiveCommentItem.add(item);
                    }
                    mCacheList.clear();
                    notifyDataSetChanged();
                    scrollToEnd();
                }
            }
        } catch (Exception e) {

        }
    }

    public void scrollToEnd() {
        try {
            synchronized (ChatBoxAdapter.class) {
                if (mRecyclerView != null) {
                    mRecyclerView.smoothScrollToPosition(mLiveCommentItem.size());
                }
            }
        } catch (Exception e) {

        }
    }

    public int getNewCount() {
        if (mCacheList != null) {
            return mCacheList.size();
        }
        return 1;
    }

    private void notifyRemoveItem(int pos) {
        if (isSlideToBottom()) {
            notifyItemRemoved(pos);
        }
    }

    public boolean isSlideToBottom() {
        if (mRecyclerView == null){
            return false;
        }
        if (mRecyclerView.computeVerticalScrollExtent() + mRecyclerView.computeVerticalScrollOffset()
                >= mRecyclerView.computeVerticalScrollRange()){
            return true;
        }
        return false;
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        final ViewHolder holder;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_chat_content_item, parent, false);

        holder = new ViewHolder(view);
        holder.mChatLayout = (RelativeLayout) view.findViewById(R.id.chat_item_layout);
        holder.mUserComment = (TextView) view.findViewById(R.id.tv_portrait_comment_content);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {
            synchronized (this.getClass()) {
                IChatMessage item = mLiveCommentItem.get(position);
                if (item != null) {
                    holder.mUserComment.setText(item.getRealContent());
                }
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        synchronized (this.getClass()) {
            if (mLiveCommentItem == null){
                return 0;
            }
            return mLiveCommentItem.size();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        /**
         * 发言
         */
        public TextView mUserComment;

        public RelativeLayout mChatLayout;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
