package com.humorousz.uiutils.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.humorousz.uiutils.R

/**
 * Description:
 *
 * author：zhangzhiquan
 * Date: 2022/9/15
 */
class GiftPriceView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0,
) : RelativeLayout(context, attrs, defStyleAttr) {
  private val loopTime = 0
  private val mViewList: ArrayList<View> = ArrayList()
  private var mStartIndex = 1
  private var mCenterIndex = 0
  private var mEndIndex = 2
  override fun onFinishInflate() {
    super.onFinishInflate()
    val right = findViewById<View>(R.id.item_end)
    val center = findViewById<View>(R.id.item_center)
    val start = findViewById<View>(R.id.item_start)
    mViewList.add(start)
    mViewList.add(center)
    mViewList.add(right)
  }

  fun startLoop() {
    val startView = mViewList[mStartIndex]
    val centerView = mViewList[mCenterIndex]
    val endView = mViewList[mEndIndex]

    mStartIndex = (mStartIndex + 1) % 3
    mCenterIndex = (mCenterIndex + 1) % 3
    mEndIndex = (mEndIndex + 1) % 3
  }
}