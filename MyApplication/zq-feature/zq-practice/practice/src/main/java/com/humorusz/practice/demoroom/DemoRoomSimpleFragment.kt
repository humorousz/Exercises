package com.humorusz.practice.demoroom

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.graphics.PointF
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.humorousz.uiutils.helper.UIUtils
import com.humorousz.uiutils.view.BaseFragment
import com.humorousz.uiutils.widget.MovingBitmapView
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
  private var bView: MovingBitmapView? = null
  private var posView1: View? = null
  private var posView2: View? = null
  private var posView3: View? = null
  private var posView4: View? = null
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
      bView = root.findViewById(R.id.bezier_view)
      posView1 = root.findViewById(R.id.bezier_1)
      posView2 = root.findViewById(R.id.bezier_2)
      posView3 = root.findViewById(R.id.bezier_3)
      posView4 = root.findViewById(R.id.bezier_4)
      posView1?.setOnClickListener {
        bezierViewHandle()
      }
      posView3?.setOnClickListener {
        bezierViewHandle2()
      }
    }
  }

  private fun bezierViewHandle() {
    val pos1 = getWindowPoint(posView1)
    val pos2 = getWindowPoint(posView2)
    bView?.addMovingBitmap(
      pos1 ?: return,
      pos2 ?: return,
      createRedBitmap(UIUtils.dip2px(50), UIUtils.dip2px(50), R.color.color_ffffca),
      1000
    )
  }

  private fun bezierViewHandle2() {
    val pos1 = getWindowCenterPoint(posView3)
    val pos2 = getWindowCenterPoint(posView4)
    bView?.addMovingBitmap(
      pos1 ?: return,
      pos2 ?: return,
      createRedBitmap(UIUtils.dip2px(50), UIUtils.dip2px(50), R.color.colorAccent),
      2000L
    )
  }


  private fun getCenterCoordinatesOnScreen(view: View?): PointF? {
    if (view == null) {
      return null
    }

    val location = IntArray(2)
    view.getLocationOnScreen(location)

    val centerX = location[0] + view.width / 2f
    val centerY = location[1] + view.height / 2f

    return PointF(centerX, centerY)
  }

  private fun getCenterPoint(view: View?): PointF? {
    if (view == null) {
      return view
    }
    val centerX = view.left + view.width / 2f
    val centerY = view.top + view.height / 2f
    return PointF(centerX, centerY)
  }

  private fun getLeftPoint(view: View?): PointF? {
    if (view == null) {
      return view
    }
    val centerX = view.left.toFloat()
    val centerY = view.top.toFloat()
    return PointF(centerX, centerY)
  }

  fun getWindowPoint(view: View?): PointF? {
    if (view == null) {
      return null
    }
    val location = IntArray(2)
    view.getLocationOnScreen(location)
    var offsetX = 0
    var offsetY = 0
    var parent = view.parent
    while (parent is View) {
      val parentView = parent as View
      offsetX += parentView.left
      offsetY += parentView.top
      parent = parentView.parent
    }
    return PointF(location[0] - offsetX.toFloat(), location[1] - offsetY.toFloat())
  }

  fun getWindowCenterPoint(view: View?): PointF? {
    if (view == null) {
      return null
    }
    val location = IntArray(2)
    view.getLocationOnScreen(location)
    var offsetX = 0
    var offsetY = 0
    var parent = view.parent
    while (parent is View) {
      val parentView = parent as View
      offsetX += parentView.left
      offsetY += parentView.top
      parent = parentView.parent
    }
    return PointF(
      location[0] - offsetX.toFloat() + view.width / 2, location[1] - offsetY.toFloat
        () + view.height / 2
    )
  }

  fun getWindowCenterTopPoint(view: View?): PointF? {
    if (view == null) {
      return null
    }
    val location = IntArray(2)
    view.getLocationOnScreen(location)
    var offsetX = 0
    var offsetY = 0
    var parent = view.parent
    while (parent is View) {
      val parentView = parent as View
      offsetX += parentView.left
      offsetY += parentView.top
      parent = parentView.parent
    }
    return PointF(
      location[0] - offsetX.toFloat() + view.width / 2, location[1].toFloat() - offsetY.toFloat()
          + view.height/2
    )
  }

  private fun createRedBitmap(width: Int, height: Int, colorId: Int): Bitmap {
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    val paint = Paint().apply {
      color = context?.resources?.getColor(colorId) ?: Color.TRANSPARENT
      style = Paint.Style.FILL
    }
    canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
    return bitmap
  }
}