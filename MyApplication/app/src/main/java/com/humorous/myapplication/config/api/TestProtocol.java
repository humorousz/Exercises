package com.humorous.myapplication.config.api;

import com.humorous.myapplication.config.factory.TestFragmentFactory;

/**
 * Created by zhangzhiquan on 2017/6/5.
 */

public class TestProtocol {

    private static final String TYPE ="/?type=";

    private static final String BASE ="test://humorous" + TYPE;

    public static final String ANT_TEST = BASE  + TestFragmentFactory.TYPE.ANT;

    public static final String TOPIC_RECYCLER_TEST = BASE + TestFragmentFactory.TYPE.TOPIC_RECYCLER;

    public static final String COORDINATOR_DEMO = BASE + TestFragmentFactory.TYPE.COORDINATOR;

    public static final String COORDINATOR_TEST = BASE +  TestFragmentFactory.TYPE.TEST_COORDINATOR;
}
