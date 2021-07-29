package com.humorusz.practice.kotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.humorousz.uiutils.helper.ToastUtil
import com.humorousz.uiutils.view.BaseFragment
import com.humorusz.practice.R

/**
 *@author zhangzhiquan
 *@date 2019-09-07
 *
 */
class KotlinTestFragment : BaseFragment() {
  lateinit var btn: Button
  override fun createView(
    inflater: LayoutInflater?,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    return inflater!!.inflate(R.layout.layout_fragment_kotlin_test, container, false)
  }

  override fun initView(root: View?) {
    btn = root!!.findViewById(R.id.test_btn)
    btn.setOnClickListener {
      ToastUtil.showToast(context, "aa")
    }
  }
}