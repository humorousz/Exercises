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

  fun addMovingBitmap(
    start: PointF,
    end: PointF,
    bitmap: Bitmap,
    duration: Long = 2000L
  ) {
    val path = Path().apply {
      reset()
      moveTo(start.x, start.y);
      quadTo((start.x + end.x) / 2f, (start.y + end.y) / 2f, end.x, end.y)
    }
    bitmapItems.add(MovingBitmapItem(bitmap = bitmap, duration = duration, path = path))
    invalidate()
  }

  override fun onDraw(canvas: Canvas) {
    super.onDraw(canvas)

    for (item in bitmapItems) {
      if (item.isAnimationFinished()) {
        continue
      }
      // 计算位移后图片的位置和大小
      pathMeasure.setPath(item.path, false)
      pathMeasure.getPosTan(pathMeasure.length * item.getMoveFraction(), pos, tan)

      mMatrix.reset()
      mMatrix.postScale(
        item.getScale(),
        item.getScale(),
        item.bitmap.width / 2f,
        item.bitmap.height / 2f
      )
      mMatrix.postTranslate(pos[0] - item.bitmap.width / 2f, pos[1] - item.bitmap.height / 2f)

      // 应用透明度和缩放变化效果
      val alpha = (255 * item.getAlphaFaction()).toInt()
      paint.alpha = alpha

      canvas.drawBitmap(item.bitmap, mMatrix, paint)
    }
    bitmapItems.removeAll { it.isAnimationFinished() } // 移除已完成的动画
    if (bitmapItems.isNotEmpty()) {
      invalidate() // 触发重绘以继续动画
    }
  }

  private inner class MovingBitmapItem(
    val moveDuration: Long = 875L,
    val scale1Duration: Long = 375L,
    val scale2Duration: Long = 1000L,
    val scale3Duration: Long = 625L,
    val alpha1Duration: Long = 1375L,
    val alpha2Duration: Long = 625L,
    val bitmap: Bitmap,
    val duration: Long,
    val path: Path
  ) {
    val startTime = System.currentTimeMillis()
    val moveInterpolator = EaseCubicInterpolator(0.26f, 1.00f, 0.48f, 1.00f)
    val scaleInterpolator = EaseCubicInterpolator(0.26f, 1.00f, 0.48f, 1.00f)

    fun isAnimationFinished(): Boolean {
      return System.currentTimeMillis() - startTime >= duration
    }

    fun currentDuration(): Long {
      return System.currentTimeMillis() - startTime
    }

    fun getScale(): Float {
      val currentDuration = currentDuration()
      return if (currentDuration < scale1Duration) {
        val fraction = currentDuration.toFloat() / scale1Duration.toFloat()
        1f + (0.3f * scaleInterpolator.getInterpolation(fraction))
      } else if (currentDuration < scale1Duration + scale2Duration) {
        val fraction = (currentDuration - scale1Duration).toFloat() / scale2Duration.toFloat()
        1.3f - (0.3f * scaleInterpolator.getInterpolation(fraction))
      } else {
        val fraction =
          (currentDuration - scale1Duration - scale2Duration).toFloat() / scale3Duration.toFloat()
        1f + (1.2f * scaleInterpolator.getInterpolation(fraction))
      }
    }

    fun getMoveFraction(): Float {
      val currentDuration = currentDuration()
      if (currentDuration > moveDuration) {
        return 1f
      }
      return moveInterpolator.getInterpolation(currentDuration.toFloat() / moveDuration.toFloat())
    }

    fun getAlphaFaction(): Float {
      val currentDuration = currentDuration()
      if (currentDuration > alpha1Duration) {
        return (1 - (currentDuration - alpha1Duration).toFloat() / alpha2Duration.toFloat())
      }
      return 1f
    }
  }

}