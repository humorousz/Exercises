package com.humorousz.commonutils;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * @author zhangzhiquan
 * @date 2018/2/5
 */

public class WeakHandler<T extends WeakHandler.WeakHandlerCallBack> extends Handler {

    WeakReference<T> wr;

    public WeakHandler(T t){
        wr = new WeakReference<>(t);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        T t = wr.get();
        if(t != null){
            t.handleMessage(msg);
        }
    }


    public interface WeakHandlerCallBack{
        /**
         * 处理消息
         * @param message
         */
        void handleMessage(Message message);
    }
}
