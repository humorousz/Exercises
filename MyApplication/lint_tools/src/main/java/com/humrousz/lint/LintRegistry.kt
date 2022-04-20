package com.humrousz.lint

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.Issue

/**
 * Description:
 *
 * author：zhangzhiquan
 * Date: 2022/4/17
 */
class LintRegistry : IssueRegistry(){
  override val issues: List<Issue>
    get() = listOf(ViewIdDetector.ISSUE,
    DimenDetector.ISSUE)
}