package com.humorousz.router.init;

import org.jetbrains.annotations.NotNull;

import android.app.Application;

import com.example.api.page.PAGE_TYPE;
import com.example.api.page.PageProvider;
import com.example.plugin.ZQPluginManager;
import com.example.plugin.biz.PagePlugin;
import com.humorousz.router.PageManager;
import com.humorusz.moudle.init.InitModule;

/**
 * Description:
 * <p>
 * author：zhangzhiquan
 * Date: 2021/8/2
 */
public class RouterInitModule implements InitModule {
  @Override
  public void onApplicationCreate(Application application) {
    ZQPluginManager.getInstance().registerPlugin(PagePlugin.class, new PagePlugin() {
      @Override
      public boolean registerPageProvider(@NotNull PageProvider pageProvider) {
        return PageManager.getInstance().registerPageProvider(pageProvider);
      }

      @Override
      public boolean registerPageProvider(@NotNull PAGE_TYPE pageType,
          @NotNull PageProvider pageProvider) {
        return PageManager.getInstance().registerPageProvider(pageProvider);
      }
    });
  }
}
