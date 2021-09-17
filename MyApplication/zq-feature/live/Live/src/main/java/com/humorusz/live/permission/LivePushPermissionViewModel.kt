package com.humorusz.live.permission

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Description:
 *
 * author：zhangzhiquan
 * Date: 2021/9/17
 */
class LivePushPermissionViewModel : ViewModel() {
  companion object PermissionParams {
    var PERMISSION_REQ_ID = 22
    var REQUESTED_PERMISSIONS = arrayOf(
      Manifest.permission.RECORD_AUDIO,
      Manifest.permission.CAMERA
    )
  }

  private var mPermissionViewModel: MutableLiveData<Boolean>? = null

  fun getPermission(): LiveData<Boolean> {
    if (mPermissionViewModel == null) {
      mPermissionViewModel = MutableLiveData<Boolean>();
    }
    return mPermissionViewModel!!
  }

  fun requestPermission(activity: Activity) {
    mPermissionViewModel?.value =
      checkSelfPermission(
        activity,
        REQUESTED_PERMISSIONS[0],
        PERMISSION_REQ_ID
      ) && checkSelfPermission(
        activity,
        REQUESTED_PERMISSIONS[1],
        PERMISSION_REQ_ID
      )
  }

  private fun checkSelfPermission(activity: Activity?, permission: String, requestCode: Int):
      Boolean {
    activity?.let {
      if (ContextCompat.checkSelfPermission(
          activity,
          permission
        ) != PackageManager.PERMISSION_GRANTED
      ) {
        ActivityCompat.requestPermissions(
          activity,
          REQUESTED_PERMISSIONS, requestCode
        )
        return false;
      }
    }
    return true
  }
}