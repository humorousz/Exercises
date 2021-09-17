package com.example.plugin.biz

import com.example.api.page.PAGE_TYPE
import com.example.api.page.PageProvider

/**
 * Description:
 *
 * author：zhangzhiquan
 * Date: 2021/9/17
 */
interface PagePlugin : ZQPlugin {
  /**
   * 注册page监听
   */
  @Deprecated("过期方法，新业务应该明确自己的类型")
  fun registerPageProvider(pageProvider: PageProvider): Boolean

  /**
   * 注册page监听
   */
  fun registerPageProvider(pageType: PAGE_TYPE, pageProvider: PageProvider): Boolean
}