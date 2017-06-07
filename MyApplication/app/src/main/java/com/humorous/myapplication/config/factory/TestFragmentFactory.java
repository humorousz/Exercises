package com.humorous.myapplication.config.factory;

import com.humorous.myapplication.antTest.AntFragment;
import com.humorous.myapplication.coordinatorTest.fragment.CoordinatorFragment;
import com.humorous.myapplication.coordinatorTest.fragment.TestCoordinatorFragment;
import com.humorous.myapplication.topicRecyclerTest.TestRecyclerFragment;
import com.humorousz.uiutils.view.BaseFragment;

/**
 * Created by zhangzhiquan on 2017/6/5.
 */

public class TestFragmentFactory {
    public enum TYPE{
        ANT,COORDINATOR,TOPIC_RECYCLER,TEST_COORDINATOR
    }
    public static BaseFragment createFragment(TYPE type){
        BaseFragment fragment = null;
        switch (type){
            case ANT:
                fragment = new AntFragment();
                break;
            case COORDINATOR:
                fragment = new CoordinatorFragment();
                break;
            case TOPIC_RECYCLER:
                fragment = new TestRecyclerFragment();
                break;
            case TEST_COORDINATOR:
                fragment = new TestCoordinatorFragment();
                break;
        }
        return fragment;
    }

}
