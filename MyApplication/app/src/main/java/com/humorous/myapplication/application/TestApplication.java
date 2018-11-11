package com.humorous.myapplication.application;

import android.app.Application;

import com.humorous.myapplication.exceptionTest.CatchExceptionHandler;
import com.humorous.weexlib.WeexManager;
import com.humorousz.commonutils.service.CommonService;
import com.humorousz.networklibrary.NetworkManager;
import com.humorousz.uiutils.helper.ImageLoaderHelper;
import com.yzq.zxinglibrary.bean.ZxingConfig;

/**
 * Created by zhangzhiquan on 2017/5/31.
 * @author zhangzhiquan
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
        CatchExceptionHandler.getInstance().init(this);
        NetworkManager.getInstance().init();
        WeexManager.init(this);
    }

}
