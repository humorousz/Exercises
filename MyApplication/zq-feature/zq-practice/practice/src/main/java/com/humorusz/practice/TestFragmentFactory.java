package com.humorusz.practice;

import com.example.api.page.PageProvider;
import com.example.api.page.PAGE_TYPE;
import com.humorousz.uiutils.view.BaseFragment;
import com.humorusz.practice.antTest.AntFragment;
import com.humorusz.practice.coordinatorTest.fragment.AdvancedCoordinatorFragment;
import com.humorusz.practice.coordinatorTest.fragment.CoordinatorFragment;
import com.humorusz.practice.coordinatorTest.fragment.TestCoordinatorFragment;
import com.humorusz.practice.dagger.DaggerFragment;
import com.humorusz.practice.demoroom.DemoRoomSimpleFragment;
import com.humorusz.practice.diffutil.DiffUtilFragment;
import com.humorusz.practice.drawerLayout.DrawerLayoutFragment;
import com.humorusz.practice.exceptionTest.UncaughtExceptionFragment;
import com.humorusz.practice.frameAnimtor.AnimatorFragment;
import com.humorusz.practice.jetpack.JetpackFragment;
import com.humorusz.practice.kotlin.KotlinTestFragment;
import com.humorusz.practice.liveroom.DemoRoomFragment;
import com.humorusz.practice.liveroom.DemoUIFragment;
import com.humorusz.practice.nested.TestNestedFragment;
import com.humorusz.practice.nestedScrollView.NestScrollViewFragment;
import com.humorusz.practice.rxjava.RxTestFragment;
import com.humorusz.practice.shader.ShaderTestFragment;
import com.humorusz.practice.textSpan.TextSpanFragment;
import com.humorusz.practice.topicRecyclerTest.TestRecyclerFragment;
import com.humorusz.practice.vectorDrawableTest.VectorTestFragment;
import com.humorusz.practice.zxing.ZxingFragment;

/**
 * Created by zhangzhiquan on 2017/6/5.
 *
 * @author zhangzhiquan
 */

public class TestFragmentFactory implements PageProvider {
  @Override
  public BaseFragment createPage(PAGE_TYPE type) {
    BaseFragment fragment = null;
    switch (type) {
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
      case SHADER:
        fragment = new ShaderTestFragment();
        break;
      case RXJAVA:
        fragment = new RxTestFragment();
        break;
      case SAO_YI_SAO:
        fragment = new ZxingFragment();
        break;
      case DIFF_UTIL:
        fragment = new DiffUtilFragment();
        break;
      case JET_PACK:
        fragment = new JetpackFragment();
        break;
      case KOTLIN:
        fragment = new KotlinTestFragment();
        break;
      case DAGGER:
        fragment = new DaggerFragment();
        break;
      case DEMO_ROOM_SIMPLE:
        fragment = new DemoRoomSimpleFragment();
        break;
    }
    return fragment;
  }
}
