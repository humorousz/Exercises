package com.humorous.myapplication.model;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;

import com.humorousz.commonutils.log.Logger;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by zhangzhiquan on 2017/10/27.
 */

public class TestModel {
    private static final String TAG = "TestModel";
    private HandlerThread mThread;
    private Handler mHandler;
    private List<Long> mList;
    private static TestModel mInstance;
    private TestModel(){
        mThread = new HandlerThread(TAG);
        mThread.start();
        mHandler = new Handler(mThread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                Long l = (Long) msg.obj;
                if(mList == null){
                    mList = new LinkedList<>();
                }
                mList.add(l);
            }
        };

        try {
            Field field = Looper.class.getDeclaredField("mQueue");
            field.setAccessible(true);
            MessageQueue queue = (MessageQueue) field.get(mThread.getLooper());
            queue.addIdleHandler(new MessageQueue.IdleHandler() {
                @Override
                public boolean queueIdle() {
                    handleQueueIdle();
                    return true;
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void handleQueueIdle(){
        Logger.d(TAG,"update mList");
    }

    public static TestModel getInstance(){
        if(mInstance == null){
            synchronized (TestModel.class){
                if(mInstance == null){
                    mInstance = new TestModel();
                }
            }
        }
        return mInstance;
    }


    public void addTime(long l){
        Message msg = mHandler.obtainMessage();
        msg.obj = l;
        mHandler.sendMessage(msg);
    }


}
