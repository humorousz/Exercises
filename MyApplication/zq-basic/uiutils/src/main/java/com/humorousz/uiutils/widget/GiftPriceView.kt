package com.humorousz.uiutils.widget

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.humorousz.uiutils.R
import com.humorousz.uiutils.helper.UIUtils

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
  private val mViewList: ArrayList<View> = ArrayList()
  private var mCenterIndex = 0
  private var mStartIndex = 1
  private var mEndIndex = 2
  private var mAnimSet: AnimatorSet? = null
  override fun onFinishInflate() {
    super.onFinishInflate()
    val right = findViewById<View>(R.id.item_end)
    val center = findViewById<View>(R.id.item_center)
    val start = findViewById<View>(R.id.item_start)
    mViewList.add(center)
    mViewList.add(start)
    mViewList.add(right)
  }

  override fun onDetachedFromWindow() {
    super.onDetachedFromWindow()
    mAnimSet?.isRunning?.let {
      if (it) {
        mAnimSet?.cancel()
      }
    }
  }

  fun startLoop() {
    val startView = mViewList[mStartIndex]
    val centerView = mViewList[mCenterIndex]
    val endView = mViewList[mEndIndex]

    if (mStartIndex == 1) {
      startView.translationX = 0f
      centerView.translationX = 0f
      endView.translationX = 0f
    }
    if (mAnimSet?.isRunning == true) {
      mAnimSet?.cancel()
      mAnimSet = null
    }
    mAnimSet = AnimatorSet()
    mAnimSet?.playTogether(
      createStartView(startView),
      createCenterViewAnim(centerView),
      createEndViewAnim(endView)
    )
    mAnimSet?.duration = ANIM_DURATION
    mAnimSet?.start()

    mStartIndex = (mStartIndex + 1) % DISPLAY_SIZE
    mCenterIndex = (mCenterIndex + 1) % DISPLAY_SIZE
    mEndIndex = (mEndIndex + 1) % DISPLAY_SIZE
    postDelayed({
      startLoop()
    }, ANIM_DURATION * 2)

  }

  private fun createStartView(startView: View): AnimatorSet {
    val startTranslate = ObjectAnimator.ofFloat(
      startView, View.TRANSLATION_X, startView
        .translationX, startView.translationX + TRANSLATE_X_OFF_SET
    )
    val startScaleX = ObjectAnimator.ofFloat(
      startView, View.SCALE_X, SCALE_END, 1f
    )
    val startScaleY = ObjectAnimator.ofFloat(
      startView, View.SCALE_Y, SCALE_END, 1f
    )
    val startAlpha = ObjectAnimator.ofFloat(startView, View.ALPHA, ALPHA_END, 1f)
    val animSet = AnimatorSet()
    animSet.playTogether(startTranslate, startScaleX, startScaleY, startAlpha)
    return animSet
  }

  private fun createCenterViewAnim(centerView: View): AnimatorSet {
    val centerTranslate = ObjectAnimator.ofFloat(
      centerView, View.TRANSLATION_X, centerView
        .translationX, centerView.translationX + TRANSLATE_X_OFF_SET
    )
    val centerScaleX = ObjectAnimator.ofFloat(
      centerView, View.SCALE_X, SCALE_START, SCALE_END
    )
    val centerScaleY = ObjectAnimator.ofFloat(
      centerView, View.SCALE_Y, SCALE_START, SCALE_END
    )
    val centerAlpha = ObjectAnimator.ofFloat(centerView, View.ALPHA, 1f, ALPHA_END)
    val animSet = AnimatorSet()
    animSet.playTogether(centerTranslate, centerScaleX, centerScaleY, centerAlpha)
    return animSet
  }

  private fun createEndViewAnim(endView: View): AnimatorSet {
    val endTranslate = ObjectAnimator.ofFloat(
      endView, View.TRANSLATION_X, endView
        .translationX, endView.translationX - TRANSLATE_X_OFF_SET * 2
    )
    val endScaleX = ObjectAnimator.ofFloat(
      endView, View.SCALE_X, SCALE_END, 0f, SCALE_END
    )
    val endScaleY = ObjectAnimator.ofFloat(
      endView, View.SCALE_Y, SCALE_END, 0f, SCALE_END
    )
    val animSet = AnimatorSet()
    animSet.playTogether(endTranslate, endScaleX, endScaleY)
    return animSet
  }

  companion object {
    const val SCALE_END = 0.5f
    const val SCALE_START = 1f
    const val ALPHA_END = 0.2f
    var TRANSLATE_X_OFF_SET = UIUtils.dip2px(20)
    const val DISPLAY_SIZE = 3
    const val ANIM_DURATION = 500L
  }
}