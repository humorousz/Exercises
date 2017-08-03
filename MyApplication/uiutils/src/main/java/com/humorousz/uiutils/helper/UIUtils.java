package com.humorousz.uiutils.helper;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.humorousz.commonutils.log.Logger;
import com.humorousz.commonutils.service.CommonService;

/**
 * Created by zhangzhiquan on 2017/5/30.
 */

public class UIUtils {
    private static final String TAG = "UIUtils";

    public static final int dip2px(int dip) {
        if(CommonService.getApplication() == null ){
            return -1;
        }
        float scale = CommonService.getApplication().getResources().getDisplayMetrics().density;
        return (int)((float)dip * scale + 0.5F);
    }



    public static final int px2dip(int px) {
        if(CommonService.getApplication() == null ){
            return -1;
        }
        float scale = CommonService.getApplication().getResources().getDisplayMetrics().density;
        return (int)((float)px / scale + 0.5F);
    }

    public static final int getScreenWidth(){
        if(CommonService.getApplication() == null){
            Logger.e(TAG,"not init CommonService in Application");
            return -1;
        }
        return getScreenWidth(CommonService.getApplication());
    }

    public static final int getScreenWidth(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    public static final int getScreenHeight(){
        if(CommonService.getApplication() == null){
            Logger.e(TAG,"not init CommonService in Application");
            return -1;
        }
        return getScreenHeight(CommonService.getApplication());
    }

    public static final int getScreenHeight(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }
}
