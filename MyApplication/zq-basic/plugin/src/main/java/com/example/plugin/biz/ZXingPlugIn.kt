package com.example.plugin.biz

import android.content.Context
import android.os.Bundle

interface ZXingPlugIn : ZQPlugin {
  fun openZXingPage(context: Context)
  fun getResult(bundle: Bundle?): String?
}