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
import java.util.function.Predicate;

/**
 * @author zhangzhiquan
 * @date 2018/2/5
 * 聊天控件适配器
 */

public class ChatBoxAdapter extends RecyclerView.Adapter<ChatBoxAdapter.ViewHolder> {
    private static final int MSG_REFRESH = 0x001;
    private static final int MSG_UPDATE = 0x002;

    private static final int MAX_CHAT_LIST_LENGTH = 10;
    private static final int MAX_CHAT_CACHE_LENGTH = 10;

    private RecyclerView mRecyclerView;
    private List<IChatMessage> mLiveCommentItem = new ArrayList<>();
    private List<IChatMessage> mCacheList = new ArrayList<>();
    private List<IChatMessage> mChatMessage = new ArrayList<>();

    private Handler handler = new NormalHandler(new HandlerCallback(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MSG_REFRESH) {
                synchronized (ChatBoxAdapter.class) {
                    notifyDataSetChanged();
                    scrollToEnd();
                }
            }
            if(msg.what == MSG_UPDATE){
                notifyMessage();
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
            mChatMessage.add(msgInfo);
        }
    }

    public void notifyAddItemNew(IChatMessage msgInfo) {
        if(isSlideToBottom()){
            if(addIntoRoomChatList(mLiveCommentItem,msgInfo)){
                notifyItemChanged(mLiveCommentItem.size()-1);
            }else {
                int start = mLiveCommentItem.size();
                mLiveCommentItem.add(msgInfo);
                notifyItemRangeInserted(start,mLiveCommentItem.size() - start);
                if (mLiveCommentItem.size() > MAX_CHAT_CACHE_LENGTH) {
                    int offset = mLiveCommentItem.size() - MAX_CHAT_CACHE_LENGTH;
                    mLiveCommentItem = mLiveCommentItem.subList(offset,mLiveCommentItem.size());
                    notifyItemRangeRemoved(0,offset);
                }
            }
            scrollToEnd();
        }else {
            if (mCacheList.size() < MAX_CHAT_CACHE_LENGTH && !addIntoRoomChatList(mCacheList,msgInfo)) {
                mCacheList.add(msgInfo);
            }
        }
    }

    public void notifyMessage(){
        synchronized (ChatBoxAdapter.class) {
            if (isSlideToBottom()) {
                int start = mLiveCommentItem.size();
                mLiveCommentItem.addAll(mChatMessage);
                if(mLiveCommentItem.size() != 0){
                    Logger.d("chatBox","notify size:"+mLiveCommentItem.size());
                    notifyItemRangeInserted(start,mLiveCommentItem.size() - start);
                }
                if (mLiveCommentItem.size() > MAX_CHAT_CACHE_LENGTH) {
                    int offset = mLiveCommentItem.size() - MAX_CHAT_CACHE_LENGTH;
                    mLiveCommentItem = mLiveCommentItem.subList(offset,mLiveCommentItem.size());
                    notifyItemRangeRemoved(0,offset);
                }
                scrollToEnd();
            } else {
                if (mCacheList.size() < MAX_CHAT_CACHE_LENGTH) {
                    mCacheList.addAll(mChatMessage);
                }
            }
            mChatMessage.clear();
        }
    }

    private boolean addIntoRoomChatList(List<IChatMessage> list,IChatMessage message){
        //是否需要更新进场消息
        //1.聊天消息不是0
        //2.当前消息是进场消息
        //3.当前显示最后一条消息是进场消息
        boolean needUpdateLast = list.size() > 0 && message.getType() == IChatMessage.INTO_ROOM_MSG && list.get(list.size()-1).getType() == IChatMessage.INTO_ROOM_MSG;
        if(needUpdateLast){
            list.set(list.size()-1,message);
        }
        return needUpdateLast;
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
                        if(!addIntoRoomChatList(mLiveCommentItem,item)){
                            mLiveCommentItem.add(item);
                        }
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
                    Logger.d("<<<<MMM","size:"+mLiveCommentItem.size());
                    mRecyclerView.smoothScrollToPosition(mLiveCommentItem.size());
                }
            }
        } catch (Exception e) {

        }
    }

    public int getNewCount() {
        synchronized (this){
            if (mCacheList != null) {
                return mCacheList.size();
            }
            return 1;
        }
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
