package com.humorusz.live

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.humorousz.uiutils.view.BaseFragment

/**
 * @author zhangzhiquan
 * @date 2021/6/27
 *
 */
class LivePrePushFragment : BaseFragment() {
  override fun createView(
    inflater: LayoutInflater?,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater?.inflate(R.layout.live_push_fragment, container, false)
  }

  override fun initView(root: View?) {
    root?.let {

    }
  }

}