package com.humorousz.uiutils.helper;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by zhangzhiquan on 2017/5/27.
 */

public class ToastUtil {

    public static void showToast(Context context,String msg){
        showToast(context,msg,false);
    }

    public static void showToast(Context context,String msg,boolean isLong){
        Toast.makeText(context,msg,isLong?Toast.LENGTH_LONG:Toast.LENGTH_SHORT).show();
    }

}
