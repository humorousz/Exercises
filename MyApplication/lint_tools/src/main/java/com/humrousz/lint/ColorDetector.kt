package com.humrousz.lint

import com.android.SdkConstants
import com.android.resources.ResourceFolderType
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.ResourceXmlDetector
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.SourceCodeScanner
import com.android.tools.lint.detector.api.XmlContext
import org.w3c.dom.Attr

/**
 * Description:
 * 颜色检查
 * author：zhangzhiquan
 * Date: 2022/4/22
 */
class ColorDetector : ResourceXmlDetector(), SourceCodeScanner {
  override fun appliesTo(folderType: ResourceFolderType): Boolean {
    return folderType == ResourceFolderType.LAYOUT
  }

  override fun getApplicableAttributes(): Collection<String> {
    return listOf(
      SdkConstants.ATTR_BACKGROUND,
      SdkConstants.ATTR_DRAWABLE
    )
  }

  override fun visitAttribute(context: XmlContext, attribute: Attr) {
    super.visitAttribute(context, attribute)
    val value = attribute.value
    if (value.startsWith("#")) {
      context.report(
        ISSUE,
        attribute,
        context.getLocation(attribute),
        "color需要使用引用变量，不能直接写"
      )
    }
  }

  companion object {
    val ISSUE = Issue.create(
      "ColorValueCheck",
      "color需要使用引用变量，不能直接写",
      "color需要使用引用变量，不能直接写",
      Category.MESSAGES,
      7,
      Severity.ERROR,
      Implementation(
        ColorDetector::class.java,
        Scope.RESOURCE_FILE_SCOPE
      )
    )
  }

}