package com.humorousz.router;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.humorousz.router.factory.PAGE_TYPE;
import com.humorousz.uiutils.view.BaseFragment;

/**
 * Description:
 * <p>
 * author：zhangzhiquan
 * Date: 2021/8/3
 */
public class PageManager {
  private final List<PageProvider> mPageProviders = new ArrayList<>();
  private static volatile PageManager mInstance;

  private PageManager() {

  }

  public static PageManager getInstance() {
    if (mInstance == null) {
      synchronized (PageManager.class) {
        if (mInstance == null) {
          mInstance = new PageManager();
        }
      }
    }
    return mInstance;
  }

  @Nullable
  public BaseFragment createFragment(@Nullable PAGE_TYPE type) {
    if (type == null) {
      return null;
    }
    BaseFragment res = null;
    for (PageProvider pageProvider : mPageProviders) {
      res = pageProvider.createPage(type);
      if (res != null) {
        return res;

      }
    }
    return null;
  }

  public boolean registerPageProvider(PageProvider pageProvider) {
    if (mPageProviders.contains(pageProvider)) {
      return false;
    }
    return mPageProviders.add(pageProvider);
  }
}
