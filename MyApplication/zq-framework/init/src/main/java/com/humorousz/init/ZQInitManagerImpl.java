package com.humorousz.init;

import android.app.Application;

import com.humorousz.commonutils.log.Logger;
import com.humorousz.commonutils.service.CommonService;
import com.humorousz.networklibrary.NetworkManager;
import com.humorousz.uiutils.helper.ImageLoaderHelper;
import com.humorusz.moudle.init.InitModule;
import com.humorusz.practice.exceptionTest.CatchExceptionHandler;

/**
 * 初始化模块实现
 */
public class ZQInitManagerImpl implements ZQInitManager {
  private static final String TAG = "ZQInitManagerImpl";
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
    NetworkManager.getInstance().init();
    CommonService.getService().init(application);
    ImageLoaderHelper.init(application);
    initModule(application);
  }

  private void initModule(Application application) {
    for (String clsStr : InitConfig.mInitConfig) {
      try {
        Class<?> cls = Class.forName(clsStr);
        InitModule initModule = (InitModule) cls.newInstance();
        initModule.onApplicationCreate(application);
      } catch (ClassNotFoundException e) {
        Logger.d(TAG, e.getMessage());
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
        Logger.d(TAG, e.getMessage());
      } catch (InstantiationException e) {
        e.printStackTrace();
        Logger.d(TAG, e.getMessage());
      }
    }
  }
}
