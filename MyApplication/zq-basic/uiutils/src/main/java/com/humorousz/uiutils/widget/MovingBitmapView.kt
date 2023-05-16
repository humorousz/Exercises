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
  private val mMatrix = Matrix()
  private val path = Path()
  private val pathMeasure = PathMeasure()
  private val pos = FloatArray(2)
  private val tan = FloatArray(2)

  fun addMovingBitmap(start: PointF, end: PointF, bitmap: Bitmap, duration: Long = 1000L) {
    bitmapItems.add(MovingBitmapItem(start, end, bitmap, duration))
    invalidate()
  }

  override fun onDraw(canvas: Canvas) {
    super.onDraw(canvas)

    for (item in bitmapItems) {
      if (item.isAnimationFinished()) {
        continue
      }

      val elapsedTime = item.getElapsedTime()
      val fraction = elapsedTime.toFloat() / item.duration

      // 计算位移后图片的位置和大小

      path.apply {
        reset()
        moveTo(item.startPointF.x, item.startPointF.y);
        quadTo(
          (item.startPointF.x + item.endPointF.x) / 2f,
          (item.startPointF.y + item.endPointF.y) / 2f,
          item.endPointF.x,
          item.endPointF.y
        )
      }
      pathMeasure.setPath(path, false)
      pathMeasure.getPosTan(pathMeasure.length * fraction, pos, tan)

      val scale = 1 - fraction
      mMatrix.reset()
      mMatrix.postScale(scale, scale, item.bitmap.width / 2f, item.bitmap.height / 2f)
      mMatrix.postTranslate(pos[0] - item.bitmap.width / 2f, pos[1] - item.bitmap.height / 2f)

      // 应用透明度和缩放变化效果
      val alpha = (255 * (1 - fraction)).toInt()
      paint.alpha = alpha

      canvas.drawBitmap(item.bitmap, mMatrix, paint)
      invalidate() // 触发重绘以继续动画
    }
    bitmapItems.removeAll { it.isAnimationFinished() } // 移除已完成的动画
  }

  private inner class MovingBitmapItem(
    val startPointF: PointF, val endPointF: PointF,
    val bitmap: Bitmap, val duration: Long
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