package com.humorous.myapplication.application;

import android.app.Application;

import com.humorousz.init.ZQInitManagerImpl;

/**
 * Created by zhangzhiquan on 2017/5/31.
 *
 * @author zhangzhiquan
 */

public class HumorousZApplication extends Application {
  @Override
  public void onCreate() {
    super.onCreate();
    ZQInitManagerImpl.getInstance().onApplicationCreate(this);
  }

}
