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
  private val paint by lazy {
    Paint(Paint.ANTI_ALIAS_FLAG)
  }
  private val mMatrix = Matrix()
  private val pos = FloatArray(2)
  private val tan = FloatArray(2)

  private val pathMeasure = PathMeasure()

  fun addMovingBitmap(start: PointF, end: PointF, bitmap: Bitmap, duration: Long = 1000L) {
    val path = Path().apply {
      reset()
      moveTo(start.x, start.y);
      quadTo((start.x + end.x) / 2f, (start.y + end.y) / 2f, end.x, end.y)
    }
    bitmapItems.add(MovingBitmapItem(bitmap, duration, path))
    invalidate()
  }

  override fun onDraw(canvas: Canvas) {
    super.onDraw(canvas)

    for (item in bitmapItems) {
      if (item.isAnimationFinished()) {
        continue
      }

      val elapsedTime = System.currentTimeMillis() - item.startTime
      val fraction = elapsedTime.toFloat() / item.duration

      // 计算位移后图片的位置和大小
      pathMeasure.setPath(item.path, false)
      pathMeasure.getPosTan(pathMeasure.length * fraction, pos, tan)

      val scale = 1 - fraction
      mMatrix.reset()
      mMatrix.postScale(scale, scale, item.bitmap.width / 2f, item.bitmap.height / 2f)
      mMatrix.postTranslate(pos[0] - item.bitmap.width / 2f, pos[1] - item.bitmap.height / 2f)

      // 应用透明度和缩放变化效果
      val alpha = (255 * (1 - fraction)).toInt()
      paint.alpha = alpha

      canvas.drawBitmap(item.bitmap, mMatrix, paint)
    }
    bitmapItems.removeAll { it.isAnimationFinished() } // 移除已完成的动画
    if (bitmapItems.isNotEmpty()) {
      invalidate() // 触发重绘以继续动画
    }
  }

  private inner class MovingBitmapItem(
    val bitmap: Bitmap,
    val duration: Long,
    val path: Path
  ) {
    val startTime = System.currentTimeMillis()

    fun isAnimationFinished(): Boolean {
      return System.currentTimeMillis() - startTime >= duration
    }
  }

}