package com.humrousz.lint

import com.android.SdkConstants
import com.android.resources.ResourceFolderType
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.LayoutDetector
import com.android.tools.lint.detector.api.ResourceXmlDetector
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.SourceCodeScanner
import com.android.tools.lint.detector.api.XmlContext
import org.jetbrains.uast.UElement
import org.w3c.dom.Attr
import org.w3c.dom.Element


/**
 * Description:
 *
 * author：zhangzhiquan
 * Date: 2022/4/17
 */
class DimenDetector : ResourceXmlDetector(), SourceCodeScanner {

  override fun appliesTo(folderType: ResourceFolderType): Boolean {
    return folderType == ResourceFolderType.LAYOUT
  }

  override fun getApplicableAttributes(): Collection<String> {
    return listOf(
      SdkConstants.ATTR_LAYOUT_WIDTH,
      SdkConstants.ATTR_LAYOUT_HEIGHT
    )
  }

  override fun visitAttribute(context: XmlContext, attribute: Attr) {
    val value = attribute.value
    val pass =
      value.startsWith(SdkConstants.DIMEN_PREFIX) || value.startsWith(SdkConstants.VALUE_MATCH_PARENT)
    if (!pass) {
      context.report(
        ISSUE,
        attribute,
        context.getLocation(attribute),
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