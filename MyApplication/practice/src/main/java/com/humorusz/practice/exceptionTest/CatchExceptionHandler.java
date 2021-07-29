package com.humorusz.practice.exceptionTest;

import android.content.Context;


/**
 * Created by zhangzhiquan on 2017/7/8.
 */

public class CatchExceptionHandler implements Thread.UncaughtExceptionHandler {

    private Context mApplicationContext;
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private static class Holder{
        protected static final CatchExceptionHandler handler = new CatchExceptionHandler();
    }

    public static CatchExceptionHandler getInstance(){
        return Holder.handler;
    }


    public void init(Context context){
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        mApplicationContext = context.getApplicationContext();
    }
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("yi chang");
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(10);
    }

}
