package com.humorous.myapplication.config.factory;

import com.humorous.myapplication.antTest.AntFragment;
import com.humorous.myapplication.coordinatorTest.fragment.AdvancedCoordinatorFragment;
import com.humorous.myapplication.coordinatorTest.fragment.CoordinatorFragment;
import com.humorous.myapplication.coordinatorTest.fragment.TestCoordinatorFragment;
import com.humorous.myapplication.drawerLayout.DrawerLayoutFragment;
import com.humorous.myapplication.exceptionTest.UncaughtExceptionFragment;
import com.humorous.myapplication.frameAnimtor.AnimatorFragment;
import com.humorous.myapplication.liveroom.DemoRoomFragment;
import com.humorous.myapplication.liveroom.DemoUIFragment;
import com.humorous.myapplication.nested.TestNestedFragment;
import com.humorous.myapplication.nestedScrollView.NestScrollViewFragment;
import com.humorous.myapplication.textSpan.TextSpanFragment;
import com.humorous.myapplication.topicRecyclerTest.TestRecyclerFragment;
import com.humorous.myapplication.vectorDrawableTest.VectorTestFragment;
import com.humorousz.uiutils.view.BaseFragment;

/**
 * Created by zhangzhiquan on 2017/6/5.
 */

public class TestFragmentFactory {
    public enum TYPE{
        ANT,COORDINATOR,TOPIC_RECYCLER,TEST_COORDINATOR,ADVANCE_COORDINATOR
        ,NESTED_SCROLLING,NESTED_SCROLL_VIEW,EXCEPTION,DRAWER_LAYOUT,FRAME_ANIMTOR
        ,DEMO_ROOM,TEXT_SPAN,DEMO_UI,VECTOR_DRAWABLE
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
            case ADVANCE_COORDINATOR:
                fragment = new AdvancedCoordinatorFragment();
                break;
            case NESTED_SCROLLING:
                fragment = new TestNestedFragment();
                break;
            case NESTED_SCROLL_VIEW:
                fragment = new NestScrollViewFragment();
                break;
            case EXCEPTION:
                fragment = new UncaughtExceptionFragment();
                break;
            case DRAWER_LAYOUT:
                fragment = new DrawerLayoutFragment();
                break;
            case FRAME_ANIMTOR:
                fragment = new AnimatorFragment();
                break;
            case DEMO_ROOM:
                fragment = new DemoRoomFragment();
                break;
            case TEXT_SPAN:
                fragment = new TextSpanFragment();
                break;
            case DEMO_UI:
                fragment = new DemoUIFragment();
                break;
            case VECTOR_DRAWABLE:
                fragment = new VectorTestFragment();
                break;
        }
        return fragment;
    }

}
