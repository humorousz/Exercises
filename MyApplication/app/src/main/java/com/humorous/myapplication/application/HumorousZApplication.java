package com.humorous.myapplication.application;

import android.app.Application;

import androidx.multidex.MultiDex;

import com.humorous.myapplication.exceptionTest.CatchExceptionHandler;
import com.humorousz.commonutils.service.CommonService;
import com.humorousz.networklibrary.NetworkManager;
import com.humorousz.uiutils.helper.ImageLoaderHelper;

/**
 * Created by zhangzhiquan on 2017/5/31.
 *
 * @author zhangzhiquan
 */

public class HumorousZApplication extends Application {
  private static final String TAG = "TestApplication";
  private static HumorousZApplication mInstance = null;

  public static HumorousZApplication getInstance() {
    return mInstance;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    mInstance = this;
    MultiDex.install(this);
    CommonService.getService().init(this);
    ImageLoaderHelper.init(getApplicationContext());
    CatchExceptionHandler.getInstance().init(this);
    NetworkManager.getInstance().init();
  }

}
