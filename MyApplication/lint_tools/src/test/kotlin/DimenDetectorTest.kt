import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Issue
import com.humrousz.lint.DimenDetector
import com.humrousz.lint.ViewIdDetector
import java.io.File

/**
 * Description:
 *
 * author：zhangzhiquan
 * Date: 2022/4/21
 */
class DimenDetectorTest : LintDetectorTest() {
  override fun getDetector(): Detector {
    return ViewIdDetector()
  }

  override fun getIssues(): MutableList<Issue> {
    return mutableListOf(ViewIdDetector.ISSUE)
  }

  fun test() {
    val testFile = xml(
      "/Users/zhangzhiquan/Github/Exercises/MyApplication/lint_tools/src/test/resources/test.xml",
      """ 
         <?xml version="1.0" encoding="utf-8"?>
         <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
           android:layout_width="match_parent"
           android:layout_height="match_parent"
           android:orientation="vertical">

           <TextView
             android:id="@+id/live_text"
             android:layout_width="200dp"
             android:layout_height="200dp"
             android:gravity="center"
             android:text="Hello PrePush" />
         </LinearLayout>
        """.trimIndent()
    )

    val file = lint().files(testFile).issues(ViewIdDetector.ISSUE)
    file.sdkHome = File("/Users/zhangzhiquan/Library/Android/sdk")
    file.run()
      .expect("No warnings")


  }
}