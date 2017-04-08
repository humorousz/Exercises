package com.example.zhangzhiquan.myapplication.utils;

import android.util.Log;

/**
 * Created by zhangzhiquan on 2017/4/7.
 */

public class MyLog {

    public static void e(String tag,String msg){
        Log.e(tag,msg);
    }

    public static void d(String tag,String msg){
        Log.d(tag,msg);
    }

    public static void i(String tag,String msg){
        Log.i(tag,msg);
    }

    public static void e(String tag,Exception ex){
        e("FFF"+tag,ex.getStackTrace()[0].getMethodName());
    }

    public static String getMethodName(){
        return new Exception().getStackTrace()[6].getMethodName();
    }
}
