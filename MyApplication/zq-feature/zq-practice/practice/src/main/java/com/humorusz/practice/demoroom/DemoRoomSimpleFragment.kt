package com.humorusz.practice.demoroom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.humorousz.uiutils.view.BaseFragment
import com.humorusz.practice.R

/**
 * Description:
 *
 * author：zhangzhiquan
 * Date: 2023/3/21
 */
class DemoRoomSimpleFragment : BaseFragment() {
  override fun createView(
    inflater: LayoutInflater?,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater?.inflate(R.layout.demo_room_simple_fragment, container, false)
  }

  override fun initView(root: View?) {
  }
}