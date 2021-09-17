package com.humorousz.router.factory;

import com.example.api.page.PAGE_TYPE;

/**
 * Created by zhangzhiquan on 2017/6/5.
 *
 * @author zhangzhiquan
 */

public class TestProtocol {

  public static final String MENU = "menu://humorous" + TestProtocol.TYPE;

  public static final String TYPE = "/?type=";

  private static final String BASE = "test://humorous" + TYPE;

  private static final String HAS_TITLE = "&hasTitle=" + String.valueOf(Boolean.TRUE);

  private static final String HAS_NO_TITLE = "&hasTitle=" + String.valueOf(Boolean.FALSE);

  /**
   * 芝麻信用效果
   */
  public static final String ANT_TEST = BASE + PAGE_TYPE.ANT + HAS_TITLE;

  /**
   * 抖音滑动渐变
   */
  public static final String TOPIC_RECYCLER_TEST =
      BASE + PAGE_TYPE.TOPIC_RECYCLER + HAS_TITLE;

  /**
   * 上滑悬浮
   */
  public static final String COORDINATOR_DEMO =
      BASE + PAGE_TYPE.COORDINATOR + HAS_NO_TITLE;

  /**
   * Coordinator练习
   */
  public static final String COORDINATOR_TEST =
      BASE + PAGE_TYPE.TEST_COORDINATOR + HAS_NO_TITLE;

  /**
   * Coordinator 进阶
   */
  public static final String COORDINATOR_ADVANCE =
      BASE + PAGE_TYPE.ADVANCE_COORDINATOR + HAS_NO_TITLE;

  /**
   * Nested 练习
   */
  public static final String NESTED_TEST =
      BASE + PAGE_TYPE.NESTED_SCROLLING + HAS_NO_TITLE;

  /**
   * NestedScrollView 练习
   */
  public static final String NESTED_SCROLL_VIEW =
      BASE + PAGE_TYPE.NESTED_SCROLL_VIEW + HAS_NO_TITLE;

  /**
   * UncaughtException 练习
   */
  public static final String EXCEPTION_TEST =
      BASE + PAGE_TYPE.EXCEPTION + HAS_NO_TITLE;

  /**
   * DrawerLayout 练习
   */
  public static final String DRAWER_LAYOUT =
      BASE + PAGE_TYPE.DRAWER_LAYOUT + HAS_NO_TITLE;

  /**
   * 帧动画 练习
   */
  public static final String FRAME_ANIMTOR =
      BASE + PAGE_TYPE.FRAME_ANIMTOR + HAS_NO_TITLE;

  /**
   * demoRoom 练习
   */
  public static final String DEMO_ROOM = BASE + PAGE_TYPE.DEMO_ROOM + HAS_NO_TITLE;

  /**
   * span 练习
   */
  public static final String TEXT_SPAN = BASE + PAGE_TYPE.TEXT_SPAN + HAS_NO_TITLE;

  /**
   * demoUI 练习
   */
  public static final String DEMO_UI = BASE + PAGE_TYPE.DEMO_UI + HAS_NO_TITLE;

  /**
   * translate动画
   */
  public static final String VECTOR_DRAWABLE =
      BASE + PAGE_TYPE.VECTOR_DRAWABLE + HAS_NO_TITLE;

  /**
   * shader 练习
   */
  public static final String SHADER = BASE + PAGE_TYPE.SHADER + HAS_TITLE;

  /**
   * RxJava 练习
   */
  public static final String RXJAVA = BASE + PAGE_TYPE.RXJAVA + HAS_TITLE;

  /**
   * Weex
   */
  public static final String WEEX = "test://weex/?jsBundle=2222.js";

  /**
   * 扫码
   */
  public static final String SAO_YI_SAO = BASE + PAGE_TYPE.SAO_YI_SAO + HAS_TITLE;

  /**
   * Diff
   */
  public static final String DIFF_UTIL = BASE + PAGE_TYPE.DIFF_UTIL + HAS_TITLE;

  /**
   * jet peck
   */
  public static final String JET_PACK = BASE + PAGE_TYPE.JET_PACK + HAS_TITLE;

  /**
   * kotlin
   */
  public static final String KOTLIN = BASE + PAGE_TYPE.KOTLIN + HAS_TITLE;

  /**
   * Dagger
   */
  public static final String DAGGER = BASE + PAGE_TYPE.DAGGER + HAS_TITLE;

  /**
   * Live_PrePush
   */
  public static final String LIVE_PRE_PUSH = BASE + PAGE_TYPE.LIVE_PRE_PUSH + HAS_TITLE;
}
