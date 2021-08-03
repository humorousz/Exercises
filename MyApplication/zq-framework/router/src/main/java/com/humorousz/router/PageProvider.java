package com.humorousz.router;

import org.jetbrains.annotations.Nullable;

import com.humorousz.router.factory.PAGE_TYPE;
import com.humorousz.uiutils.view.BaseFragment;

/**
 * Description:
 * <p>
 * author：zhangzhiquan
 * Date: 2021/8/3
 */
public interface PageProvider {
  /**
   * 创建一个Fragment
   * @param type 传入的type
   * @return
   */
  @Nullable
  BaseFragment createPage(PAGE_TYPE type);
}
