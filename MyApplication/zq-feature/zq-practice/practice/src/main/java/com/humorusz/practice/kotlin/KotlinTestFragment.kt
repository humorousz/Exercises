package com.humorusz.practice.kotlin

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.humorousz.uiutils.helper.ToastUtil
import com.humorousz.uiutils.view.BaseFragment
import com.humorusz.practice.R

/**
 *@author zhangzhiquan
 *@date 2019-09-07
 *
 */
class KotlinTestFragment : BaseFragment() {
  lateinit var btn: Button
  override fun createView(
    inflater: LayoutInflater?,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    return inflater!!.inflate(R.layout.layout_fragment_kotlin_test, container, false)
  }

  override fun initView(root: View?) {
    btn = root!!.findViewById(R.id.test_btn)
    btn.setOnClickListener {
      ToastUtil.showToast(context, "aa")
    }
    val recyclerView = root?.findViewById<RecyclerView>(R.id.test_recycler_view)
    recyclerView.adapter = GiftPriceAdapter()
    val snapHelper = PagerSnapHelper()
    snapHelper.attachToRecyclerView(recyclerView)
    recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
      override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        Log.d("MRZZ", dx.toString())
      }
    })
  }


  private fun initRecyclerView() {

  }

  class GiftPriceAdapter : RecyclerView.Adapter<GiftPriceHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GiftPriceHolder {
      return GiftPriceHolder(
        LayoutInflater.from(parent.context).inflate(
          R.layout
            .layout_test_gift_price_item, parent, false
        )
      )
    }

    override fun onBindViewHolder(holder: GiftPriceHolder, position: Int) {
      holder.itemView.findViewById<TextView>(R.id.test_text).text = position.toString()
    }

    override fun getItemCount(): Int {
      return 10
    }

  }

  class GiftPriceHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

  }
}