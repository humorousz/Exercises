package com.humorousz.init;
import android.app.Application;

import com.humorusz.practice.exceptionTest.CatchExceptionHandler;

/**
 * 初始化模块实现
 */
public class ZQInitManagerImpl implements ZQInitManager {
  private static volatile ZQInitManager mZQInitManager;

  private ZQInitManagerImpl() {}

  public static ZQInitManager getInstance() {
    if (mZQInitManager == null) {
      synchronized (ZQInitManagerImpl.class) {
        if (mZQInitManager == null) {
          mZQInitManager = new ZQInitManagerImpl();
        }
      }
    }
    return mZQInitManager;
  }

  @Override
  public void onApplicationCreate(Application application) {
    CatchExceptionHandler.getInstance().init(application);
  }
}
