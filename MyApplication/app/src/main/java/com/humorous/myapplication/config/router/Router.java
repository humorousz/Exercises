package com.humorous.myapplication.config.router;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;


import com.humorous.myapplication.SecondMenuActivity;
import com.humorous.myapplication.config.api.Api;
import com.humorous.myapplication.config.api.TestProtocol;
import com.humorous.myapplication.config.factory.TestFragmentFactory;
import com.humorous.myapplication.container.ContainerActivity;

import com.humorousz.commonutils.log.Logger;

/**
 * @author zhangzhiquan
 * @date 2017/6/5
 * 跳转router
 */

public class Router {
    private static final String TAG = "Router";
    public static void jumpTo(Context context,String link){
        long startTime;
        startTime = System.currentTimeMillis();
        Logger.i(TAG,"jumpTo begin");
        if(TextUtils.isEmpty(link)){
            return;
        }
        try {
            Uri uri = Uri.parse(link);
            String typeString = uri.getQueryParameter("type");
            if(link.contains(TestProtocol.MENU)){
                Api.SECOND_MENU type = Api.SECOND_MENU.valueOf(typeString);
                startMenuActivity(context,type);
            }else {
                Boolean hasTitle = Boolean.valueOf(uri.getQueryParameter("hasTitle"));
                Boolean landscape = uri.getBooleanQueryParameter("couldLandscape",false);
                startActivity(context,hasTitle,landscape,typeString,uri);
            }
        }catch (RuntimeException e){
            Logger.e(TAG,e.getMessage());
        }

        Logger.i(TAG,"jumpTo end spend "+(System.currentTimeMillis() - startTime));


    }
    private static void startActivity(Context context,boolean hasTitle,boolean landscape,String typeString,Uri link){
        if(context == null){
            Logger.e(TAG,"context must not be null");
            return;
        }
        Intent intent =  new Intent();
        intent.setAction("com.humorous.test");
        intent.addCategory(Intent.CATEGORY_DEFAULT );
        intent.setData(link);
        if(!TextUtils.isEmpty(typeString)){
            TestFragmentFactory.TYPE type = TestFragmentFactory.TYPE.valueOf(typeString);
            intent.putExtra(ContainerActivity.FRAGMENT_TYPE,type);
        }
        intent.putExtra(ContainerActivity.HAS_TITLE,hasTitle);
        intent.putExtra(ContainerActivity.LANDSCAPE,landscape);

        context.startActivity(intent);
    }

    private static void startMenuActivity(Context context, Api.SECOND_MENU type){
        if(context == null){
            Logger.e(TAG,"context must not be null");
            return;
        }
        Intent intent = new Intent(context, SecondMenuActivity.class);
        intent.putExtra(SecondMenuActivity.TYPE,type);
        context.startActivity(intent);
    }

}
