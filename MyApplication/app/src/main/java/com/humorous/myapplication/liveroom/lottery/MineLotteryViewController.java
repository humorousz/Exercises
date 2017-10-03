package com.humorous.myapplication.liveroom.lottery;

import android.view.ViewGroup;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by zhangzhiquan on 2017/9/28.
 */

public class MineLotteryViewController implements MineLotteryView.OnAnimationStateListener{
    private ViewGroup mContainer;
    private Queue<List<MineLotteryData>> mQueue;
    private MineLotteryView mineLotteryView;
    private boolean isRunning;

    public MineLotteryViewController(ViewGroup container){
        this.mContainer = container;
        mQueue = new LinkedList<>();
    }


    public void addTask(List<MineLotteryData> list){
        mQueue.offer(list);
        takeTask();
    }

    public void clearTask(){
        if(mQueue != null){
            mQueue.clear();
        }
    }

    public void release() {
        mContainer = null;
        if (mQueue != null && mQueue.size() != 0) {
            mQueue.clear();
        }
        mQueue = null;
    }
    public void init(){
        isRunning = false;
    }

    public void clear(){
        clearTask();
        if(isRunning && mineLotteryView != null){
            mineLotteryView.stopAnim();
        }
        clearView();
        isRunning = false;
    }


    private void takeTask(){
        if(!isRunning && ! mQueue.isEmpty()){
            startAnim(mQueue.poll());
        }
    }

    private void clearView(){
        mContainer.removeView(mineLotteryView);
        mineLotteryView = null;
    }
    private void startAnim(List<MineLotteryData> message){
        isRunning = true;
        if(mContainer == null)
            return;
        if(mineLotteryView != null)
            clearView();
        mineLotteryView = new MineLotteryView(mContainer.getContext());
        mContainer.addView(mineLotteryView);
        mineLotteryView.setOnAnimationStateListener(this);
        mineLotteryView.setData(message);
    }


    @Override
    public void onStart() {
        isRunning = true;
    }

    @Override
    public void onEnd() {
        isRunning = false;
        clearView();
        takeTask();
    }
}
