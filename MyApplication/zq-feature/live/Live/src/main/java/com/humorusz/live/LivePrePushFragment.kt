package com.humorusz.live

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.humorousz.commonutils.log.Logger
import com.humorousz.uiutils.helper.ToastUtil
import com.humorousz.uiutils.view.BaseFragment
import com.humorusz.live.permission.LivePermissionViewModelFactory
import com.humorusz.live.permission.LivePushPermissionViewModel
import io.agora.rtc.IRtcEngineEventHandler
import io.agora.rtc.RtcEngine
import io.agora.rtc.RtcEngineConfig
import io.agora.rtc.video.VideoCanvas

/**
 * @author zhangzhiquan
 * @date 2021/6/27
 *
 */
class LivePrePushFragment : BaseFragment() {
  companion object {
    private const val TAG = "LivePrePushFragment"
  }

  private var appid = "57072160652442e6ac654a816b6a053b"
  private var mRootView: ViewGroup? = null
  private var mRtcEngine: RtcEngine? = null
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
      mRootView = root.findViewById(R.id.live_video_container)
      checkPermission()
    }
  }

  private fun checkPermission() {
    permissionViewModel.getPermission().observe(this) {
      if (it) {
        ToastUtil.showToast(context, "获取权限成功")
        initRtcEngine()
        setPreview(mRootView)
      }
    }
    permissionViewModel.requestPermission(activity!!)
  }

  private fun initRtcEngine() {
    val config = RtcEngineConfig()
    config.mContext = activity?.applicationContext
    config.mAppId = appid
    config.mEventHandler = object : IRtcEngineEventHandler() {
      override fun onError(err: Int) {
        super.onError(err)
        Logger.d(TAG, "error code:$err");
      }

      override fun onFirstLocalVideoFrame(width: Int, height: Int, elapsed: Int) {
        super.onFirstLocalVideoFrame(width, height, elapsed)
        Logger.d(TAG, "first local frame: $width")
      }
    }
    mRtcEngine = RtcEngine.create(config)
    mRtcEngine?.setClientRole(IRtcEngineEventHandler.ClientRole.CLIENT_ROLE_BROADCASTER)
  }

  private fun setPreview(root: ViewGroup?) {
    root?.let {
      mRtcEngine?.enableVideo()
      val surfaceView = RtcEngine.CreateRendererView(context)
      surfaceView.setZOrderMediaOverlay(true)
      root.addView(surfaceView)
      val videoCanvas = VideoCanvas(surfaceView, VideoCanvas.RENDER_MODE_HIDDEN, 0)
      mRtcEngine?.setupLocalVideo(videoCanvas)
      mRtcEngine?.startPreview()
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    mRtcEngine?.stopPreview()
  }


}