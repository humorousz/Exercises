package com.humorous.myapplication.application;

import android.app.Application;

import com.humorousz.commonutils.service.CommonService;
import com.humorousz.uiutils.helper.ImageLoaderHelper;

/**
 * Created by zhangzhiquan on 2017/5/31.
 */

public class TestApplication extends Application{
    private static final String TAG = "TestApplication";
    private static  TestApplication mInstance = null ;

    public static TestApplication getInstance(){
        return mInstance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        CommonService.getService().init(this);
        ImageLoaderHelper.init(getApplicationContext());
    }

}
