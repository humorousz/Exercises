package com.humorousz.uiutils.widget

import android.animation.TimeInterpolator
import android.graphics.PointF

/**
 * Description:
 * 贝塞尔曲线插值器
 * author：zhangzhiquan
 * Date: 2023/5/16
 */
class EaseCubicInterpolator(x1: Float, y1: Float, x2: Float, y2: Float) : TimeInterpolator {

  private val mControlPoint1 = PointF(x1, y1)

  private val mControlPoint2 = PointF(x2, y2)

  override fun getInterpolation(input: Float): Float {
    var t = input
    var value: Double
    var i = 0
    while (i < ACCURACY) {
      t = 1.0f * i / ACCURACY
      val x = cubicCurves(
        t.toDouble(), 0.0, mControlPoint1.x.toDouble(),
        mControlPoint2.x.toDouble(), 1.0
      )
      if (x >= input.toDouble()) break
      i++
    }
    value = cubicCurves(
      t.toDouble(), 0.0, mControlPoint1.y.toDouble(),
      mControlPoint2.y.toDouble(), 1.0
    )
    if (value > 0.999) {
      value = 1.0
      mLastI = 0
    }
    return value.toFloat()
  }

  companion object {
    private const val ACCURACY = 4096
    private var mLastI = 0

    private fun cubicCurves(
      t: Double, value0: Double, value1: Double,
      value2: Double, value3: Double
    ): Double {
      val u = 1 - t
      val tt = t * t
      val uu = u * u
      val uuu = uu * u
      val ttt = tt * t
      var value = uuu * value0
      value += 3 * uu * t * value1
      value += 3 * u * tt * value2
      value += ttt * value3
      return value
    }
  }
}