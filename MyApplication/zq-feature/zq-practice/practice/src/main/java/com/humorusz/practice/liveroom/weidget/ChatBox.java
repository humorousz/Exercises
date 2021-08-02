package com.humorusz.practice.liveroom.weidget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.humorusz.practice.R;
import com.humorusz.practice.liveroom.adapter.ChatBoxAdapter;
import com.humorusz.practice.liveroom.module.IChatMessage;
import com.humorousz.commonutils.handler.HandlerCallback;
import com.humorousz.commonutils.handler.NormalHandler;
import com.humorousz.commonutils.log.Logger;
import com.humorousz.uiutils.helper.UIUtils;

import java.util.List;

/**
 * @author zhangzhiquan
 * @date 2018/2/5
 * 聊天控件
 */

public class ChatBox extends FrameLayout implements View.OnClickListener {
    private static final String TAG = "ChatBox";
    private static final int CHAT_MSG = 0x01;
    private static final int CHAT_MSG_LOCAL = 0x02;
    private static final int CHAT_UPDATE = 0x03;
    private Context mContext;
    private View mRootView;
    private RecyclerView mChatRecyclerView;
    private ChatBoxAdapter mAdapter;
    private RelativeLayout mNewMessageTips;
    private TextView mNewMessageText;
    private OnScrollListener mChatRecyclerViewScrollListener;
    private String userId;
    private boolean useLocal = false;
    private Handler mHandler = new NormalHandler(new HandlerCallback(){
        @Override
        public void handleMessage(Message msg) {
            IChatMessage module = (IChatMessage) msg.obj;
            switch (msg.what){
                case CHAT_MSG:
                    updateChatData(module,false);
                    break;
                case CHAT_MSG_LOCAL:
                    updateChatData(module,true);
                    break;
                case CHAT_UPDATE:
//                    mAdapter.notifyMessage();
//                    if(mAdapter.isSlideToBottom()){
//                        hideNewMsgLayout();
//                    }else {
//                        showNewMsgLayout();
//                    }
                    break;
                default:
                    break;
            }
        }
    });

    public ChatBox(@NonNull Context context) {
        this(context,null);
    }

    public ChatBox(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public ChatBox(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView(){
        mRootView = LayoutInflater.from(mContext).inflate(R.layout.layout_chat_box,this);
        mChatRecyclerView = (RecyclerView) mRootView.findViewById(R.id.chat_recyclerview);
        mAdapter = new ChatBoxAdapter();
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mChatRecyclerView.setLayoutManager(manager);
        mChatRecyclerView.setAdapter(mAdapter);
        mChatRecyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mChatRecyclerView.setVerticalFadingEdgeEnabled(true);
        mChatRecyclerView.setFadingEdgeLength(UIUtils.dip2px(15));
        mNewMessageTips = (RelativeLayout) mRootView.findViewById(R.id.chat_new_message_tip);
        mNewMessageText = (TextView) mRootView.findViewById(R.id.chat_new_message_tip_text);
        mChatRecyclerViewScrollListener = new OnScrollListener();
        mChatRecyclerView.addOnScrollListener(mChatRecyclerViewScrollListener);
        mNewMessageTips.setOnClickListener(this);
        hideNewMsgLayout();
    }
    /**
     * 设置用户ID
     * @param userId
     */
    public void setUserId(String userId){
        this.userId = userId;
    }

    /**
     * 打开本地回显
     */
    public void turnOnLocal(){
        useLocal = true;
    }

    /**
     * 关闭本地回显
     */
    public void turnOffLocal(){
        useLocal = false;
    }

    /**
     * 是否本地回显
     * @return
     */
    public boolean isUseLocal(){
        return useLocal;
    }

    /**
     * 添加新的消息
     * @param newMessage
     */
    public void addNewChat(IChatMessage newMessage){
        if(newMessage == null){
            return;
        }
        Logger.d(TAG,"addNewChat");
        Message message = mHandler.obtainMessage();
        message.what = CHAT_MSG;
        message.obj = newMessage;
        mHandler.sendMessage(message);
    }

    /**
     * 添加本地消息
     * @param newMessage
     */
    public void addNewChatLocal(IChatMessage newMessage){
        if(newMessage == null){
            return;
        }
        Logger.d(TAG,"addNewChatLocal");
        Message message = mHandler.obtainMessage();
        message.what = CHAT_MSG_LOCAL;
        message.obj = newMessage;
        mHandler.sendMessage(message);
    }

    private void updateChatData(IChatMessage newMessage,boolean isLocal){
        if(newMessage == null || TextUtils.isEmpty(newMessage.getContent())){
            return;
        }
        if(!isLocal && isUseLocal() && userId != null && userId.equals(newMessage.getUserId())){
            Logger.d(TAG,"updateChatData useLocal:"+isUseLocal()+" userId:"+userId+" msg UserId:"+newMessage.getUserId());
            return;
        }
        if (mAdapter != null) {
            Logger.d(TAG,"updateChatData");
//            mAdapter.notifyAddItem(newMessage);
//            if(mHandler.hasMessages(CHAT_UPDATE)){
//                mHandler.removeMessages(CHAT_UPDATE);
//            }
//            mHandler.sendEmptyMessageDelayed(CHAT_UPDATE,100);
            mAdapter.notifyAddItemNew(newMessage);
            if(mAdapter.isSlideToBottom()){
                hideNewMsgLayout();
            }else {
                showNewMsgLayout();
            }
        }
    }

    /**
     * 添加整列消息
     * @param newMessage
     */
    public void updateChatData(final List<IChatMessage> newMessage){
        if(newMessage == null || newMessage.size() == 0){
            return;
        }
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mAdapter != null) {
                    mAdapter.notifyAddList(newMessage);
                    if(mAdapter.isSlideToBottom()){
                        hideNewMsgLayout();
                    }else {
                        showNewMsgLayout();
                    }
                }
            }
        });


    }

    private void hideNewMsgLayout() {
        if (mNewMessageTips != null && mNewMessageTips.getVisibility() == View.VISIBLE) {
            mNewMessageTips.setVisibility(View.GONE);
        }
    }

    private void showNewMsgLayout() {
        if (mNewMessageTips != null && mNewMessageTips.getVisibility() == View.GONE) {
            mNewMessageTips.setVisibility(View.VISIBLE);
        }

        //更新新条数数量
        int count = 1;
        if (mAdapter != null) {
            count = mAdapter.getNewCount();
        }

        if (mNewMessageText != null) {
            mNewMessageText.setText(count + "条新消息");
        }
    }

    private void addCacheData() {
        if (mChatRecyclerView != null) {
            mChatRecyclerView.post(new Runnable() {
                @Override
                public void run() {
                    if (mAdapter != null) {
                        mAdapter.addCacheData();
                    }
                }
            });
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if(mChatRecyclerView != null){
            mChatRecyclerView.removeOnScrollListener(mChatRecyclerViewScrollListener);
        }
        if(mHandler != null){
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    @Override
    public void onClick(View v) {
        if(v == mNewMessageTips){
            addCacheData();
            hideNewMsgLayout();
        }
    }

    private  class OnScrollListener extends RecyclerView.OnScrollListener{
        int nNewState = -2;
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            nNewState = newState;
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (mAdapter != null && mAdapter.isSlideToBottom()) {
                hideNewMsgLayout();
                addCacheData();
            }
        }
    }
}
