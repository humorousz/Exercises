package com.humorusz.practice.init;

import android.app.Application;

import com.example.plugin.ZQPluginManager;
import com.example.plugin.biz.ZXingPlugIn;
import com.humorousz.commonutils.log.Logger;
import com.humorousz.router.PageManager;
import com.humorusz.moudle.init.InitModule;
import com.humorusz.practice.TestFragmentFactory;

/**
 * Description:
 * 初始化模块
 * author：zhangzhiquan
 * Date: 2021/8/2
 */
public class PracticeInitModule implements InitModule {
  private static final String TAG = "PracticeInitModule";

  @Override
  public void onApplicationCreate(Application application) {
    Logger.d(TAG, "PracticeInitModule create");
    PageManager.getInstance().registerPageProvider(new TestFragmentFactory());
    ZQPluginManager.getInstance().registerPlugin(ZXingPlugIn.class,new ZXingPluginImpl());
  }
}
