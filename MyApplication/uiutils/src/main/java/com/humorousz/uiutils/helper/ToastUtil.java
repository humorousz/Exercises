package com.humorousz.uiutils.helper;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by zhangzhiquan on 2017/5/27.
 */

public class ToastUtil {
    private static Toast mToast;
    public static void showToast(Context context,CharSequence msg){
        showToast(context,msg,false);
    }

    public static void showToast(Context context,CharSequence msg,boolean isLong){
        if(mToast != null){
            mToast.cancel();
        }
        mToast = Toast.makeText(context,msg,isLong?Toast.LENGTH_LONG:Toast.LENGTH_SHORT);
        mToast.show();

    }

}
