package com.humorusz.live.init

import android.app.Application
import com.humorousz.router.PageManager
import com.humorusz.moudle.init.InitModule

/**
 * Description:
 *
 * author：zhangzhiquan
 * Date: 2021/8/3
 */
class LiveInitModule : InitModule {
  override fun onApplicationCreate(application: Application?) {
    PageManager.getInstance().registerPageProvider(LivePageProvider())
  }
}