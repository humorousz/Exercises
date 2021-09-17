package com.humorusz.live

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.humorousz.uiutils.helper.ToastUtil
import com.humorousz.uiutils.view.BaseFragment

/**
 * @author zhangzhiquan
 * @date 2021/6/27
 *
 */
class LivePrePushFragment : BaseFragment() {

  companion object PermissionParams {
    var PERMISSION_REQ_ID = 22
    var REQUESTED_PERMISSIONS = arrayOf(
      Manifest.permission.RECORD_AUDIO,
      Manifest.permission.CAMERA
    )
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
      if (checkSelfPermission(REQUESTED_PERMISSIONS[0], PERMISSION_REQ_ID) &&
        checkSelfPermission(REQUESTED_PERMISSIONS[1], PERMISSION_REQ_ID)
      ) {
        ToastUtil.showToast(context, "获取权限成功")
      }
    }
  }


  private fun checkSelfPermission(permission: String, requestCode: Int): Boolean {
    activity?.let {
      if (ContextCompat.checkSelfPermission(
          activity!!,
          permission
        ) != PackageManager.PERMISSION_GRANTED
      ) {
        ActivityCompat.requestPermissions(activity!!, REQUESTED_PERMISSIONS, requestCode);
        return false;
      }
    }
    return true
  }
}