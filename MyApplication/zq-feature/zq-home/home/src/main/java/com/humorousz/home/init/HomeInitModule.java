package com.humorousz.home.init;

import android.app.Application;
import android.util.Log;

import com.humorusz.moudle.init.InitModule;

/**
 * Description:
 * author：zhangzhiquan
 * Date: 2021/8/2
 */
public class HomeInitModule implements InitModule {
  @Override
  public void onApplicationCreate(Application application) {
    Log.d("MRZ", "Home create");
  }
}
