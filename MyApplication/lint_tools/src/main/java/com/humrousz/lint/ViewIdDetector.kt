package com.humrousz.lint

import com.android.SdkConstants
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.LayoutDetector
import com.android.tools.lint.detector.api.Scope.Companion.RESOURCE_FILE_SCOPE
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.XmlContext
import org.w3c.dom.Element

/**
 * Description:
 * ViewId检查
 * author：zhangzhiquan
 * Date: 2022/4/17
 */
class ViewIdDetector : LayoutDetector() {
  override fun getApplicableElements(): Collection<String>? {
    return listOf(
      SdkConstants.TEXT_VIEW,
      SdkConstants.IMAGE_VIEW,
      SdkConstants.BUTTON
    )
  }

  override fun visitElement(context: XmlContext, element: Element) {
    if(!element.hasAttributeNS(SdkConstants.ANDROID_URI,SdkConstants.ATTR_ID)){
      return
    }
    println("Zhangzhiquan")
    val attr = element.getAttributeNodeNS(SdkConstants.ANDROID_URI,SdkConstants.ATTR_ID)
    val value = attr.value
    if(value.startsWith(SdkConstants.NEW_ID_PREFIX)){
      val idValue = value.substring(SdkConstants.NEW_ID_PREFIX.length)
      var matchRule = true
      var expMsg = ""
      when(element.tagName){
        SdkConstants.TEXT_VIEW -> {
          expMsg = "tv"
          matchRule = idValue.startsWith(expMsg)
        }
      }
      if(!matchRule){
        context.report(
          ISSUE,
          attr,
          context.getLocation(attr),
          "ViewIdName建议使用view的缩写_xxx; ${element.tagName} 建议使用 `${expMsg}_xxx`"
          )
      }
    }
  }

  companion object{
    val ISSUE:Issue = Issue.create(
      "ViewCheck",
      "ViewId命名不规范",
      "建议tv_开头",
      Category.CORRECTNESS,
      5,
      Severity.ERROR,
      Implementation(
        ViewIdDetector::class.java,
        RESOURCE_FILE_SCOPE
      )
    )
  }
}