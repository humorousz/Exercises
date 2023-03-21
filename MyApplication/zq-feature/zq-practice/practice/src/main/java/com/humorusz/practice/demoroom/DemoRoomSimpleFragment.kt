package com.humorusz.practice.demoroom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.humorousz.uiutils.view.BaseFragment
import com.humorousz.uiutils.widget.MultiScrollNumber
import com.humorusz.practice.R

/**
 * Description:
 *
 * author：zhangzhiquan
 * Date: 2023/3/21
 */
class DemoRoomSimpleFragment : BaseFragment() {
  private var last = 0
  override fun createView(
    inflater: LayoutInflater?,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater?.inflate(R.layout.demo_room_simple_fragment, container, false)
  }

  override fun initView(root: View?) {
    root?.findViewById<MultiScrollNumber>(R.id.scroll_number)?.let { scrollView ->
      val edit = root.findViewById<EditText>(R.id.input_num_edit)
      root.findViewById<View>(R.id.input_button)?.setOnClickListener {
        val text = edit?.text?.toString()?.toInt()
        scrollView.setNumber(last, text ?: 0)
        last = text ?: 0
      }

      val addEdit = root.findViewById<EditText>(R.id.input_num_add_edit)
      root.findViewById<View>(R.id.input_add_button)?.setOnClickListener {
        val text = addEdit?.text?.toString()?.toInt()
        val aa = last + (text ?: 0)
        scrollView.setNumber(last, aa)
        last = aa
      }
    }
  }
}