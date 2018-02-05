package com.humorousz.commonutils.handler;

import android.os.Handler;
import android.os.Message;

/**
 * @author zhangzhiquan
 * @date 2018/2/5
 * 普通的Handler 防止在代码中new Handler时编译器报出非静态内部类的泄漏提示
 */

public class NormalHandler extends Handler {
    private HandlerCallback mCallback;
    public NormalHandler(HandlerCallback callback){
        mCallback = callback;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if(mCallback != null){
            mCallback.handleMessage(msg);
        }
    }
}
