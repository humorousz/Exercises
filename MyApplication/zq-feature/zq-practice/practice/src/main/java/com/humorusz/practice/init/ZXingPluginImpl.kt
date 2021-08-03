package com.humorusz.practice.init

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.plugin.biz.ZXingPlugIn
import com.yzq.zxinglibrary.android.CaptureActivity
import com.yzq.zxinglibrary.bean.ZxingConfig
import com.yzq.zxinglibrary.common.Constant

/**
 * Description:
 *
 * author：zhangzhiquan
 * Date: 2021/8/3
 */
class ZXingPluginImpl : ZXingPlugIn {
  override fun openZXingPage(context: Context) {
    val intent = Intent(context, CaptureActivity::class.java)
    val config = ZxingConfig()
    //是否播放扫描声音 默认为true
    config.isPlayBeep = true;
    //是否震动  默认为true
    config.isShake = true;
    //是否扫描条形码 默认为true
    config.isDecodeBarCode = false;
    //是否全屏扫描  默认为true  设为false则只会在扫描框中扫描
    config.isFullScreenScan = true;
    intent.putExtra(Constant.INTENT_ZXING_CONFIG, config)
    context.startActivity(intent)
  }

  override fun getResult(bundle: Bundle?): String? {
    return bundle?.getString(Constant.CODED_CONTENT)
  }

}