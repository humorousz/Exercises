package com.humorousz.home.config.router;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;


import com.humorousz.home.SecondMenuActivity;
import com.humorousz.home.config.api.Api;
import com.humorousz.home.config.api.TestProtocol;
import com.humorousz.home.config.factory.TestFragmentFactory;
import com.humorousz.home.container.ContainerActivity;

import com.humorousz.commonutils.log.Logger;

/**
 * @author zhangzhiquan
 * @date 2017/6/5
 * 跳转router
 */

public class Router {
    private static final String TAG = "Router";
    public static void jumpTo(Context context,String link){
       jumpTo(context,link,"");
    }


    public static void jumpTo(Context context,String link,String title){
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
                startMenuActivity(context,type,title);
            }else {
                Boolean hasTitle = Boolean.valueOf(uri.getQueryParameter("hasTitle"));
                Boolean landscape = uri.getBooleanQueryParameter("couldLandscape",false);
                startActivity(context,hasTitle,landscape,typeString,uri,title);
            }
        }catch (RuntimeException e){
            Logger.e(TAG,e.getMessage());
        }
        Logger.i(TAG,"jumpTo end spend "+(System.currentTimeMillis() - startTime));
    }
    private static void startActivity(Context context,boolean hasTitle,boolean landscape,String typeString,Uri link,String title){
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
        if(!TextUtils.isEmpty(title)){
            intent.putExtra(ContainerActivity.TITLE,title);
        }
        context.startActivity(intent);
    }

    private static void startMenuActivity(Context context, Api.SECOND_MENU type,String title){
        if(context == null){
            Logger.e(TAG,"context must not be null");
            return;
        }
        Intent intent = new Intent(context, SecondMenuActivity.class);
        intent.putExtra(SecondMenuActivity.TYPE,type);
        intent.putExtra(SecondMenuActivity.TITLE,title);
        context.startActivity(intent);
    }

    public static void open(Context context,String url){
        if(context == null || TextUtils.isEmpty(url)){
            return;
        }
        Intent intent = new Intent();
        intent.setAction("com.humorous.test");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setData(Uri.parse(url));
        context.startActivity(intent);
    }

}
