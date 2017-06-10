package com.humorous.myapplication.config.api;

import com.humorous.myapplication.config.factory.TestFragmentFactory;

/**
 * Created by zhangzhiquan on 2017/6/5.
 */

public class TestProtocol {

    private static final String TYPE ="/?type=";

    private static final String BASE ="test://humorous" + TYPE;

    private static final String HAS_TITLE = "&hasTitle="+String.valueOf(Boolean.TRUE);

    private static final String HAS_NO_TITLE = "&hasTitle="+String.valueOf(Boolean.FALSE);


    //芝麻信用效果
    public static final String ANT_TEST = BASE  + TestFragmentFactory.TYPE.ANT + HAS_TITLE;

    //抖音滑动渐变
    public static final String TOPIC_RECYCLER_TEST = BASE + TestFragmentFactory.TYPE.TOPIC_RECYCLER + HAS_NO_TITLE;

    //上滑悬浮
    public static final String COORDINATOR_DEMO = BASE + TestFragmentFactory.TYPE.COORDINATOR + HAS_NO_TITLE;

    //Coordinator练习
    public static final String COORDINATOR_TEST = BASE +  TestFragmentFactory.TYPE.TEST_COORDINATOR  + HAS_NO_TITLE;

    //Coordinator 进阶
    public static final String COORDINATOR_ADVANCE = BASE +  TestFragmentFactory.TYPE.ADVANCE_COORDINATOR  + HAS_NO_TITLE;
}
