package com.humorous.myapplication.vectorDrawableTest;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by zhangzhiquan on 2017/11/5.
 */

public class GuardController implements GuardView.GuardStateListener {
    private ViewGroup mParent;
    private Queue<GuardMessage> mQueue;
    private GuardView mGuardView;
    private boolean isRunning;
    public GuardController(@NonNull ViewGroup container){
        mParent = container;
        mQueue = new LinkedList<>();
    }

    public void addTask(GuardMessage message){
        synchronized (mQueue){
            mQueue.offer(message);
        }
        takeTask();
    }

    private void takeTask(){
        if(!isRunning && !mQueue.isEmpty()){
            GuardMessage msg = null;
            synchronized (mQueue){
                if(!mQueue.isEmpty()){
                    msg = mQueue.poll();
                }
            }
            if(msg != null){
                startAnimator(msg);
            }
        }
    }

    private void startAnimator(GuardMessage msg){
        if(mParent == null){
            return;
        }
        isRunning = true;
        mGuardView = null;
        mGuardView = new GuardView(mParent.getContext());
        mGuardView.setGuardStateListener(this);
        mGuardView.setGuardMessage(msg);
        mParent.addView(mGuardView);
    }

    public void clear(){
        if(mQueue.isEmpty())
            return;
        synchronized (mQueue){
            mQueue.clear();
        }
    }


    @Override
    public void onStartAnim() {

    }

    @Override
    public void onEndAnim() {
        if(mParent != null && mGuardView != null){
            mParent.removeView(mGuardView);
        }
        isRunning = false;
        takeTask();

    }
}
