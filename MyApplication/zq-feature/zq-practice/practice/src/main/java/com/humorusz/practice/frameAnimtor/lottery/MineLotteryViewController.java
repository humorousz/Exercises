package com.humorusz.practice.frameAnimtor.lottery;

import android.app.FragmentManager;
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
    private   LotteryDialog dialog;

    private FragmentManager manager;
    public MineLotteryViewController(ViewGroup container){
        this.mContainer = container;
        mQueue = new LinkedList<>();
    }

    public MineLotteryViewController(ViewGroup container,FragmentManager manager){
        this.mContainer = container;
        mQueue = new LinkedList<>();
        this.manager = manager;
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
        if(dialog != null){
            dialog.dismiss();
            dialog = null;
        }
        mContainer.removeView(mineLotteryView);
        mineLotteryView = null;
    }
    private void startAnim(List<MineLotteryData> message){
        isRunning = true;
        if(mContainer == null)
            return;
        if(mineLotteryView != null)
            clearView();
        dialog = new LotteryDialog();
        mineLotteryView = new MineLotteryView(mContainer.getContext());
        dialog.setLotteryView(mineLotteryView);
//        mContainer.addView(mineLotteryView);
        mineLotteryView.setOnAnimationStateListener(this);
        mineLotteryView.setData(message);
        dialog.show(manager,"adf");
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
