package com.humorusz.practice.init;

import android.app.Application;

import com.humorousz.commonutils.log.Logger;
import com.humorusz.moudle.init.InitModule;

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
  }
}
