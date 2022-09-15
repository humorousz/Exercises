package com.humorusz.practice.kotlin

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.humorousz.uiutils.helper.UIUtils

/**
 * Description:
 *
 * author：zhangzhiquan
 * Date: 2022/9/14
 */
class LoopLinearLayoutManager : RecyclerView.LayoutManager() {
  var loopEnable = true
  var mRecyclerView: RecyclerView? = null
  var mItemWidth = 0
  override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
    return RecyclerView.LayoutParams(
      ViewGroup.LayoutParams.WRAP_CONTENT,
      ViewGroup.LayoutParams.WRAP_CONTENT
    )
  }

  override fun canScrollHorizontally(): Boolean {
    return true
  }

  override fun onAttachedToWindow(view: RecyclerView?) {
    super.onAttachedToWindow(view)
    mRecyclerView = view
    mRecyclerView?.post {
      mRecyclerView?.scrollBy(-1 * mItemWidth / 2, 0)
//      mRecyclerView?.requestLayout()
    }
  }

  override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
    if (itemCount <= 0) return
    if (state.isPreLayout) return
    detachAndScrapAttachedViews(recycler)
    var authWidth = 0
    for (i in 0 until itemCount) {
      val view = recycler.getViewForPosition(i)
      addView(view)
      measureChildWithMargins(view, 0, 0)
      val width = getDecoratedMeasuredWidth(view)
      val height = getDecoratedMeasuredHeight(view)
      mItemWidth = width
      layoutDecorated(view, authWidth, 0, authWidth + width, height)
      authWidth += width
      if (authWidth > width) break
    }
    if (authWidth < width) {
      fill(width - authWidth, recycler, state)
    }
  }

  override fun scrollHorizontallyBy(
    dx: Int,
    recycler: RecyclerView.Recycler,
    state: RecyclerView.State
  ): Int {
    loopEnable = childCount > 1
    val travel = fill(dx, recycler, state)
    if (travel == 0) return 0
    offsetChildrenHorizontal(-travel)
    recyclerHideView(dx, recycler, state)
    return travel
  }

  private fun recyclerHideView(
    dx: Int,
    recycler: RecyclerView.Recycler,
    state: RecyclerView.State
  ) {
    for (i in 0 until childCount) {
      val view = getChildAt(i) ?: continue
      if (dx > 0) {
        if (view.right < 0) {
          removeAndRecycleView(view, recycler)
        }
      } else {
        if (view.left > width) {
          removeAndRecycleView(view, recycler)
        }
      }
    }
  }

  private fun fill(dx: Int, recycler: RecyclerView.Recycler, state: RecyclerView.State): Int {
    var dx = dx
    if (dx > 0) {
      val lastView = getChildAt(childCount - 1) ?: return 0
      val lastPos = getPosition(lastView)
      if (lastView.right < width) {
        var scrap: View? = null
        if (lastPos == itemCount - 1) {
          if (loopEnable) {
            scrap = recycler.getViewForPosition(0)
          } else {
            dx = 0
          }
        } else {
          scrap = recycler.getViewForPosition(lastPos + 1)
        }
        if (scrap == null) return dx
        addView(scrap)
        measureChildWithMargins(scrap, 0, 0)
        val width = getDecoratedMeasuredWidth(scrap)
        val height = getDecoratedMeasuredHeight(scrap)
        layoutDecorated(scrap, lastView.right, 0, lastView.right + width, height)
        return dx
      }
    } else {
      val firstView = getChildAt(0) ?: return 0
      val firstPos = getPosition(firstView)
      if (firstView.left >= 0) {
        var scrap: View? = null
        if (firstPos == 0) {
          if (loopEnable) {
            scrap = recycler.getViewForPosition(itemCount - 1)
          } else {
            dx = 0
          }
        } else {
          scrap = recycler.getViewForPosition(firstPos - 1)
        }
        if (scrap == null) {
          return 0
        }
        addView(scrap, 0)
        measureChildWithMargins(scrap, 0, 0)
        val width = getDecoratedMeasuredWidth(scrap)
        val height = getDecoratedMeasuredHeight(scrap)
        layoutDecorated(scrap, firstView.left - width, 0, firstView.left, height)
      }
    }
    return dx
  }

  fun scrollToNext() {
    mRecyclerView?.smoothScrollBy(mItemWidth, 0)
  }
}