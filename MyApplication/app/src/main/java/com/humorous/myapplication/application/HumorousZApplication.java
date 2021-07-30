package com.humorous.myapplication.application;

import android.app.Application;
import androidx.multidex.MultiDex;

import com.humorousz.commonutils.service.CommonService;
import com.humorousz.init.ZQInitManagerImpl;
import com.humorousz.networklibrary.NetworkManager;
import com.humorousz.uiutils.helper.ImageLoaderHelper;

/**
 * Created by zhangzhiquan on 2017/5/31.
 *
 * @author zhangzhiquan
 */

public class HumorousZApplication extends Application {
  @Override
  public void onCreate() {
    super.onCreate();
    MultiDex.install(this);
    CommonService.getService().init(this);
    ImageLoaderHelper.init(getApplicationContext());
    NetworkManager.getInstance().init();
    ZQInitManagerImpl.getInstance().onApplicationCreate(this);
  }

}
