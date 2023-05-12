package com.humorousz.uiutils.widget

/**
 * Description:
 *
 * author：zhangzhiquan
 * Date: 2023/5/12
 */
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class MovingBitmapView(context: Context, attrs: AttributeSet) : View(context, attrs) {

  private var bitmapItems = mutableListOf<MovingBitmapItem>()
  private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

  fun addMovingBitmap(start: PointF, end: PointF, bitmap: Bitmap, duration: Long = 1000L) {
    bitmapItems.add(
      MovingBitmapItem(
        start,
        end,
        bitmap,
        duration
      )
    )
    invalidate() // Trigger a redraw to start the animation
  }

  override fun onDraw(canvas: Canvas) {
    super.onDraw(canvas)

    for (item in bitmapItems) {
      if (item.isAnimationFinished()) {
        continue
      }
      val elapsedTime = item.getElapsedTime()
      val fraction = elapsedTime.toFloat() / item.duration
      val x = item.startPointF.x + (item.endPointF.x - item.startPointF.x) * fraction
      val y = item.startPointF.y + (item.endPointF.y - item.startPointF.y) * fraction
      canvas.drawBitmap(item.bitmap, x, y, paint)
      invalidate() // Trigger another redraw to continue the animation
    }
    bitmapItems.removeAll { it.isAnimationFinished() } // Remove finished animations
  }

  private inner class MovingBitmapItem(
    val startPointF: PointF,
    val endPointF: PointF,
    val bitmap: Bitmap,
    val duration: Long
  ) {
    private var startTime: Long = System.currentTimeMillis()

    fun getElapsedTime(): Long {
      return System.currentTimeMillis() - startTime
    }

    fun isAnimationFinished(): Boolean {
      return getElapsedTime() >= duration
    }

  }
}