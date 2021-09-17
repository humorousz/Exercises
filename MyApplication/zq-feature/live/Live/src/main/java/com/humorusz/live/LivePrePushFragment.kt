package com.humorusz.live

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.humorousz.uiutils.helper.ToastUtil
import com.humorousz.uiutils.view.BaseFragment
import com.humorusz.live.permission.LivePermissionViewModelFactory
import com.humorusz.live.permission.LivePushPermissionViewModel

/**
 * @author zhangzhiquan
 * @date 2021/6/27
 *
 */
class LivePrePushFragment : BaseFragment() {
  private val permissionViewModel by lazy {
    ViewModelProvider(
      this,
      LivePermissionViewModelFactory()
    ).get(LivePushPermissionViewModel::class.java)
  }

  override fun createView(
    inflater: LayoutInflater?,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater?.inflate(R.layout.live_push_fragment, container, false)
  }

  override fun initView(root: View?) {
    root?.let {
      checkPermission()
    }
  }

  private fun checkPermission(){
    permissionViewModel.getPermission().observe(this) {
      if (it) {
        ToastUtil.showToast(context, "获取权限成功")
      }
    }
    permissionViewModel.requestPermission(activity!!)
  }


}