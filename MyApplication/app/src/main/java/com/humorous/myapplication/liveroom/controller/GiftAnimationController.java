package com.humorous.myapplication.liveroom.controller;

import android.util.Pair;
import android.view.View;

import com.humorousz.commonutils.log.Logger;
import com.humorousz.uiutils.widget.AnimatedImageView;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author zhangzhiquan
 * @date 2018/2/7
 * 礼物效果控制器
 */

public class GiftAnimationController implements AnimatedImageView.OnFinishedListener {
    private static final String TAG = "GiftAnimationController";
    private Queue<Pair<String,String>> mQueue;
    private AnimatedImageView mImageView;
    private boolean isRunning;
    private Object mLock = new Object();
    public GiftAnimationController(AnimatedImageView imageView){
        if(imageView == null){
            throw new NullPointerException("AnimatedImageView should not be null");
        }
        mImageView = imageView;
        mQueue = new LinkedList<>();
        isRunning = false;
    }

    public void addTask(String path,String name){
        synchronized (mLock){
            Logger.d(TAG,"addTask");
            mQueue.offer(new Pair<>(path,name));
            takeTask();
        }
    }

    private void takeTask(){
        String path;
        synchronized (mLock){
            if (!isRunning && !mQueue.isEmpty()) {
                path = mQueue.poll().first;
            } else {
                return;
            }
        }
        Logger.d(TAG,"takeTask");
        startAnim(path);
    }
    private void startAnim(String path){
        isRunning = true;
        mImageView.setVisibility(View.VISIBLE);
        mImageView.setOnFinishedListener(this);
        mImageView.setImageResourceFromAssets(path);
        Logger.d(TAG,"startAnim");
    }

    private void playNext(){
        synchronized (mLock){
            isRunning = false;
            takeTask();
            Logger.d(TAG,"playNext");
        }
    }

    @Override
    public void onFinished() {
        Logger.d(TAG,"onFinished");
        mImageView.setVisibility(View.INVISIBLE);
        playNext();
    }
}
