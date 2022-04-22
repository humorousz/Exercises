import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import com.android.tools.lint.checks.infrastructure.TestFiles.xml
import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import com.humrousz.lint.ViewIdDetector
import org.junit.Test
import java.io.File

/**
 * Description:
 *
 * author：zhangzhiquan
 * Date: 2022/4/22
 */
class TestDemo {
  @Test
  fun testDemo(){
    val testFile = xml(
      "/Users/zhangzhiquan/Github/Exercises/MyApplication/lint_tools/src/test/resources/zq.xml",
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
    ).indented()

    val file = lint().files(testFile).issues(ViewIdDetector.ISSUE)
    file.sdkHome = File("/Users/zhangzhiquan/Library/Android/sdk")
    file.run()
      .expect("No warnings.")

  }
}