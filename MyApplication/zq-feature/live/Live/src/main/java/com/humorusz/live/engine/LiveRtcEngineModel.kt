package com.humorusz.live.engine

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.agora.rtc.RtcEngine

/**
 * Description:
 * RTC Model
 * author：zhangzhiquan
 * Date: 2021/9/17
 */
class LiveRtcEngineModel : ViewModel() {
  private var mRtcEngine : MutableLiveData<RtcEngine>? = null
}