package com.humorous.myapplication.liveroom.intoRoom;

import android.content.Context;
import android.view.ViewGroup;

import com.humorousz.commonutils.log.Logger;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by zhangzhiquan on 2017/8/19.
 */

public class IntoRoomAnimatorController implements IntoRoomAnimatorView.OnAnimationStateListener {
    private static final String TAG = "AnimatorController";
    private Context mContext;
    private ViewGroup mContainer;
    private IntoRoomAnimatorView mAnimatorView;
    private Queue<CharSequence> mQueue;
    private boolean isRunning;

    public IntoRoomAnimatorController(Context context, ViewGroup container) {
        mContext = context;
        mContainer = container;
        mQueue = new LinkedList<>();
        isRunning = false;
    }


    public void addTask(CharSequence sequence) {
        Logger.d(TAG,"addTask");
        mQueue.offer(sequence);
        takeTask();
    }

    public void takeTask() {
        if (!isRunning && !mQueue.isEmpty()) {
            Logger.d(TAG,"takeTask");
            startAnimator(mQueue.poll());
        }
    }

    public void startAnimator(CharSequence sequence) {
        isRunning = true;
        if (mContainer == null)
            return;
        if (mAnimatorView == null) {
            mAnimatorView = new IntoRoomAnimatorView(mContext);
            mAnimatorView.setOnAnimationStateListener(this);
            mContainer.addView(mAnimatorView);
        }
        mAnimatorView.setData(sequence);
        Logger.d(TAG,"start anim");
    }

    public void release() {
        mContainer = null;
        mContext = null;
        mAnimatorView = null;
        if (mQueue != null && mQueue.size() != 0) {
            mQueue.clear();
        }
        mQueue = null;
    }

    @Override
    public void onStart() {
        isRunning = true;
    }

    @Override
    public void onEnd() {
        isRunning = false;
        takeTask();
    }
}
