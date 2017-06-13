package com.humorous.myapplication.config.router;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;


import com.humorous.myapplication.SecondMenuActivity;
import com.humorous.myapplication.config.api.API;
import com.humorous.myapplication.config.api.TestProtocol;
import com.humorous.myapplication.config.factory.TestFragmentFactory;
import com.humorous.myapplication.container.ContainerActivity;

import com.humorousz.commonutils.log.Logger;

/**
 * Created by zhangzhiquan on 2017/6/5.
 */

public class Router {
    private static final String TAG = "Router";
    public static void jumpTo(Context context,String link){
        if(TextUtils.isEmpty(link)){
            return;
        }
        try {
            Uri uri = Uri.parse(link);
            String typeString = uri.getQueryParameter("type");
            if(link.contains(TestProtocol.MENU)){
                API.SECOND_MENU type = API.SECOND_MENU.valueOf(typeString);
                startMenuActivity(context,type);
            }else {
                TestFragmentFactory.TYPE type = TestFragmentFactory.TYPE.valueOf(typeString);
                Boolean hasTitle = Boolean.valueOf(uri.getQueryParameter("hasTitle"));
                startActivity(context,hasTitle,type);
            }
        }catch (RuntimeException e){
            Logger.e(TAG,e.getMessage());
        }

    }
    private static void startActivity(Context context,boolean hasTitle,TestFragmentFactory.TYPE type){
        if(context == null){
            Logger.e(TAG,"context must not be null");
            return;
        }
        Intent intent =  new Intent(context, ContainerActivity.class);
        intent.putExtra(ContainerActivity.HAS_TITLE,hasTitle);
        intent.putExtra(ContainerActivity.FRAMGNET_TYPE,type);
        context.startActivity(intent);
    }

    private static void startMenuActivity(Context context, API.SECOND_MENU type){
        if(context == null){
            Logger.e(TAG,"context must not be null");
            return;
        }
        Intent intent = new Intent(context, SecondMenuActivity.class);
        intent.putExtra(SecondMenuActivity.TYPE,type);
        context.startActivity(intent);
    }

}
