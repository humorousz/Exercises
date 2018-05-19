package com.humorous.weexlib;

import android.app.Application;

import com.humorous.weexlib.adapter.ImageAdapter;
import com.taobao.weex.InitConfig;
import com.taobao.weex.WXSDKEngine;

/**
 * @author zhangzhiquan
 * on 2018/5/19
 * Weex初始化
 */
public class WeexManager {
    public static void init(Application application){
        InitConfig config = new InitConfig.Builder().setImgAdapter(new ImageAdapter()).build();
        WXSDKEngine.initialize(application,config);
        registerComponent();
        registerModule();
    }

    private static void registerComponent(){

    }

    private static void registerModule(){

    }
}
