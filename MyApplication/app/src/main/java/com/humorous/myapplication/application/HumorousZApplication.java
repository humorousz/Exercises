package com.humorous.myapplication.application;

import android.app.Application;
import androidx.multidex.MultiDex;

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
    MultiDex.install(this);
    ZQInitManagerImpl.getInstance().onApplicationCreate(this);
  }

}
