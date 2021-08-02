package com.humorousz.init;

import com.humorousz.home.init.HomeInitModule;
import com.humorusz.practice.init.PracticeInitModule;

/**
 * Description:
 * 初始化Config
 * author：zhangzhiquan
 * Date: 2021/8/2
 */
public class InitConfig {
  public static String[] mInitConfig = new String[]{
      HomeInitModule.class.getName(),
      PracticeInitModule.class.getName()
  };
}
