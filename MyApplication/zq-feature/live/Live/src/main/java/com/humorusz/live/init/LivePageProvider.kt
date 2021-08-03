package com.humorusz.live.init

import com.humorousz.router.PageProvider
import com.humorousz.router.factory.PAGE_TYPE
import com.humorousz.uiutils.view.BaseFragment
import com.humorusz.live.LivePrePushFragment

/**
 * Description:
 *
 * author：zhangzhiquan
 * Date: 2021/8/3
 */
class LivePageProvider : PageProvider {
  override fun createPage(type: PAGE_TYPE?): BaseFragment? {
    if (type == PAGE_TYPE.LIVE_PRE_PUSH) {
      return LivePrePushFragment()
    }
    return null;
  }
}