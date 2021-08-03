package com.humorousz.home.init;

import android.app.Application;

import com.humorousz.commonutils.log.Logger;
import com.humorusz.moudle.init.InitModule;

/**
 * Description:
 * author：zhangzhiquan
 * Date: 2021/8/2
 */
public class HomeInitModule implements InitModule {
  private static final String TAG = "HomeInitModule";
  @Override
  public void onApplicationCreate(Application application) {
    Logger.d(TAG, "HomeInitModule create");
  }
}
