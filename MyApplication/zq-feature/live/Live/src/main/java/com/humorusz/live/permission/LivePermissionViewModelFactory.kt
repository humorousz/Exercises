package com.humorusz.live.permission

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Description:
 * 创建直播权限ViewModel
 * author：zhangzhiquan
 * Date: 2021/9/17
 */
class LivePermissionViewModelFactory : ViewModelProvider.Factory {
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    return LivePushPermissionViewModel() as T
  }
}