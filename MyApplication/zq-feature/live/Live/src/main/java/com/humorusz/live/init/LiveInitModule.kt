package com.humorusz.live.init

import android.app.Application
import com.example.api.page.PAGE_TYPE
import com.example.plugin.ZQPluginManager
import com.example.plugin.biz.PagePlugin
import com.humorusz.moudle.init.InitModule

/**
 * Description:
 *
 * author：zhangzhiquan
 * Date: 2021/8/3
 */
class LiveInitModule : InitModule {
  override fun onApplicationCreate(application: Application?) {
    ZQPluginManager.getInstance().getPlugin(PagePlugin::class.java)
      ?.registerPageProvider(PAGE_TYPE.LIVE_PRE_PUSH, LivePageProvider());
  }
}