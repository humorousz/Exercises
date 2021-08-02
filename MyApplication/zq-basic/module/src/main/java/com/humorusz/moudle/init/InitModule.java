package com.humorusz.moudle.init;

import android.app.Application;

/**
 * Description:
 * 初始化模块
 * author：zhangzhiquan
 * Date: 2021/8/2
 */
public interface InitModule {
  /**
   * 创建application
   * @param application app
   */
  void onApplicationCreate(Application application);
}
