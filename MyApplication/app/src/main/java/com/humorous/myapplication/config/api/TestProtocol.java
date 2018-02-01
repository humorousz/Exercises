package com.humorous.myapplication.config.api;

import com.humorous.myapplication.config.factory.TestFragmentFactory;

/**
 * Created by zhangzhiquan on 2017/6/5.
 * @author zhangzhiquan
 */

public class TestProtocol {

    private static final String TYPE ="/?type=";

    private static final String BASE ="test://humorous" + TYPE;

    private static final String HAS_TITLE = "&hasTitle="+String.valueOf(Boolean.TRUE);

    private static final String HAS_NO_TITLE = "&hasTitle="+String.valueOf(Boolean.FALSE);


    /**
     * 芝麻信用效果
     */
    public static final String ANT_TEST = BASE  + TestFragmentFactory.TYPE.ANT + HAS_TITLE;

    /**
     * 抖音滑动渐变
     */
    public static final String TOPIC_RECYCLER_TEST = BASE + TestFragmentFactory.TYPE.TOPIC_RECYCLER + HAS_NO_TITLE;

    /**
     * 上滑悬浮
     */
    public static final String COORDINATOR_DEMO = BASE + TestFragmentFactory.TYPE.COORDINATOR + HAS_NO_TITLE;

    /**
     * Coordinator练习
     */
    public static final String COORDINATOR_TEST = BASE +  TestFragmentFactory.TYPE.TEST_COORDINATOR  + HAS_NO_TITLE;

    /**
     * Coordinator 进阶
     */
    public static final String COORDINATOR_ADVANCE = BASE +  TestFragmentFactory.TYPE.ADVANCE_COORDINATOR  + HAS_NO_TITLE;

    /**
     * Nested 练习
     */
    public static final String NESTED_TEST = BASE + TestFragmentFactory.TYPE.NESTED_SCROLLING +  HAS_NO_TITLE;

    /**
     * NestedScrollView 练习
     */
    public static final String NESTED_SCROLL_VIEW = BASE + TestFragmentFactory.TYPE.NESTED_SCROLL_VIEW + HAS_NO_TITLE;

    /**
     * UncaughtException 练习
     */
    public static final String EXCEPTION_TEST = BASE + TestFragmentFactory.TYPE.EXCEPTION + HAS_NO_TITLE;

    /**
     * DrawerLayout 练习
     */
    public static final String DRAWER_LAYOUT = BASE + TestFragmentFactory.TYPE.DRAWER_LAYOUT + HAS_NO_TITLE;

    /**
     * 帧动画 练习
     */
    public static final String FRAME_ANIMTOR = BASE + TestFragmentFactory.TYPE.FRAME_ANIMTOR + HAS_NO_TITLE;

    /**
     * demoRoom 练习
     */
    public static final String DEMO_ROOM = BASE + TestFragmentFactory.TYPE.DEMO_ROOM + HAS_NO_TITLE;

    /**
     * span 练习
     */
    public static final String TEXT_SPAN = BASE + TestFragmentFactory.TYPE.TEXT_SPAN + HAS_NO_TITLE;

    /**
     * demoUI 练习
     */
    public static final String DEMO_UI = BASE + TestFragmentFactory.TYPE.DEMO_UI + HAS_NO_TITLE;

    /**
     * translate动画
     */
    public static final String VECTOR_DRAWABLE = BASE + TestFragmentFactory.TYPE.VECTOR_DRAWABLE + HAS_NO_TITLE;

    /**
     * 共享元素
     */
    public static final String SHARE_ELEMENT = BASE + TestFragmentFactory.TYPE.SHARE_ELEMENT + HAS_TITLE;

    /**
     * shader 练习
     */
    public static final String SHADER = BASE + TestFragmentFactory.TYPE.SHADER + HAS_TITLE;

    /**
     * RxJava 练习
     */
    public static final String RXJAVA = BASE + TestFragmentFactory.TYPE.RXJAVA + HAS_TITLE;

    /**
     * 二级菜单
     */

    public static final String MENU = "menu://humorous" + TYPE;

    public static final String UI_MENU = MENU + Api.SECOND_MENU.UI;

    public static final String ANIM_MENU = MENU + Api.SECOND_MENU.ANIM;

    public static final String OTHER_MENU = MENU + Api.SECOND_MENU.OTHER;


}
