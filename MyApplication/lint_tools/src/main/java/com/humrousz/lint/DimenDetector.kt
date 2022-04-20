package com.humrousz.lint

import com.android.SdkConstants
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.LayoutDetector
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.XmlContext
import org.w3c.dom.Element


/**
 * Description:
 *
 * author：zhangzhiquan
 * Date: 2022/4/17
 */
class DimenDetector : LayoutDetector() {
  override fun visitElement(context: XmlContext, element: Element) {
    if (!element.hasAttributeNS(SdkConstants.ANDROID_URI, SdkConstants.ATTR_LAYOUT_WIDTH)) {
      return
    }

    val attr = element.getAttributeNodeNS(SdkConstants.ANDROID_URI, SdkConstants.ATTR_LAYOUT_WIDTH)
    val value = attr.value
    if (!(value.startsWith(SdkConstants.DIMEN_PREFIX) || value.startsWith(SdkConstants.VALUE_MATCH_PARENT))) {
      context.report(
        ISSUE,
        attr,
        context.getLocation(attr),
        "dimen value 需要定义到values中"
      )
    }
  }

  companion object {
    val ISSUE = Issue.create(
      "ZQDimenCheck",
      "dimen不能直接写魔法值，需要定义变量",
      "需要定义dimen在values",
      Category.MESSAGES,
      7,
      Severity.ERROR,
      Implementation(
        DimenDetector::class.java,
        Scope.RESOURCE_FILE_SCOPE
      )
    )
  }
}