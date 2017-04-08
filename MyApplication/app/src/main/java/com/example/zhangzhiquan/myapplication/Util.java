package com.example.zhangzhiquan.myapplication;

import android.content.Context;

/**
 * Created by Zhangzhiquan on 2016/10/26.
 */
public class Util {
    public static final int dip2px(Context context, int dip) {
        if(context == null) {
            return dip;
        }
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }
}
