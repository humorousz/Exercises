package com.humorousz.home.config.api;

import static com.humorousz.router.factory.TestProtocol.MENU;

import com.humorousz.home.home.model.HomeItemModel;
import com.humorousz.router.factory.TestProtocol;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by zhangzhiquan on 2017/6/5.
 */

public class Api {

  public enum SECOND_MENU {
    /**
     * 主菜单
     */
    MAIN,
    /**
     * UI相关
     */
    UI,
    /**
     * 动画相关
     */
    ANIM,
    /**
     * 普通练习
     */
    OTHER,
    /**
     * 探索新内容
     */
    EXPLORE,
    /**
     * 直播
     */
    LIVE
  }

  public static List<HomeItemModel> getList(SECOND_MENU type) {
    switch (type) {
      case UI:
        return getUI();
      case MAIN:
        return getAllItem();
      case ANIM:
        return getAnim();
      case OTHER:
        return getOther();
      case EXPLORE:
        return getExplore();
      case LIVE:
        return getLive();
      default:
        break;
    }
    return null;
  }

  /**
   * 二级菜单
   */
  public static final String UI_MENU = MENU + Api.SECOND_MENU.UI;

  public static final String ANIM_MENU = MENU + Api.SECOND_MENU.ANIM;

  public static final String OTHER_MENU = MENU + Api.SECOND_MENU.OTHER;

  public static final String EXPLORE = MENU + Api.SECOND_MENU.EXPLORE;

  public static final String LIVE = MENU + Api.SECOND_MENU.LIVE;

  public static List<HomeItemModel> getAllItem() {
    List<HomeItemModel> models = new ArrayList<>(12);

    HomeItemModel itemModel;

    itemModel = new HomeItemModel();
    itemModel.setLink(UI_MENU);
    itemModel.setTitle("UI相关");
    models.add(itemModel);

    itemModel = new HomeItemModel();
    itemModel.setLink(ANIM_MENU);
    itemModel.setTitle("动画练习");
    models.add(itemModel);


    itemModel = new HomeItemModel();
    itemModel.setLink(OTHER_MENU);
    itemModel.setTitle("练习");
    models.add(itemModel);

    itemModel = new HomeItemModel();
    itemModel.setLink(EXPLORE);
    itemModel.setTitle("探索新内容");
    models.add(itemModel);

    itemModel = new HomeItemModel();
    itemModel.setLink(LIVE);
    itemModel.setTitle("直播");
    models.add(itemModel);

    return models;
  }


  public static List<HomeItemModel> getUI() {

    List<HomeItemModel> models = new ArrayList<>(12);
    HomeItemModel itemModel;

    itemModel = new HomeItemModel();
    itemModel.setLink(TestProtocol.TOPIC_RECYCLER_TEST);
    itemModel.setTitle("抖音新鲜页滑动渐变");
    models.add(itemModel);

    itemModel = new HomeItemModel();
    itemModel.setLink(TestProtocol.COORDINATOR_DEMO);
    itemModel.setTitle("上滑悬浮");
    models.add(itemModel);


    itemModel = new HomeItemModel();
    itemModel.setLink(TestProtocol.NESTED_TEST);
    itemModel.setTitle("NestedScrolling");
    models.add(itemModel);

    itemModel = new HomeItemModel();
    itemModel.setLink(TestProtocol.TEXT_SPAN);
    itemModel.setTitle("Span 练习");
    models.add(itemModel);

    itemModel = new HomeItemModel();
    itemModel.setLink(TestProtocol.COORDINATOR_TEST);
    itemModel.setTitle("Behavior练习");
    models.add(itemModel);

    itemModel = new HomeItemModel();
    itemModel.setLink(TestProtocol.COORDINATOR_ADVANCE);
    itemModel.setTitle("Coordinator练习");
    models.add(itemModel);


    itemModel = new HomeItemModel();
    itemModel.setLink(TestProtocol.NESTED_SCROLL_VIEW);
    itemModel.setTitle("NestedScrollView");
    models.add(itemModel);

    itemModel = new HomeItemModel();
    itemModel.setLink(TestProtocol.DRAWER_LAYOUT);
    itemModel.setTitle("DrawerLayout练习");
    models.add(itemModel);

    return models;
  }

  public static List<HomeItemModel> getAnim() {
    List<HomeItemModel> models = new ArrayList<>(12);
    HomeItemModel itemModel;

    itemModel = new HomeItemModel();
    itemModel.setLink(TestProtocol.FRAME_ANIMTOR);
    itemModel.setTitle("送礼");
    models.add(itemModel);

    itemModel = new HomeItemModel();
    itemModel.setLink(TestProtocol.SHADER);
    itemModel.setTitle("Shader练习");
    models.add(itemModel);

    itemModel = new HomeItemModel();
    itemModel.setLink(TestProtocol.VECTOR_DRAWABLE);
    itemModel.setTitle("属性动画");
    models.add(itemModel);

    itemModel = new HomeItemModel();
    itemModel.setLink(TestProtocol.ANT_TEST);
    itemModel.setTitle("芝麻信用效果");
    models.add(itemModel);
    return models;

  }

  public static List<HomeItemModel> getOther() {
    List<HomeItemModel> models = new ArrayList<>(12);
    HomeItemModel itemModel;
    itemModel = new HomeItemModel();
    itemModel.setLink(TestProtocol.DEMO_ROOM);
    itemModel.setTitle("demo房间");
    models.add(itemModel);

    itemModel = new HomeItemModel();
    itemModel.setLink(TestProtocol.DEMO_UI);
    itemModel.setTitle("demoUI");
    models.add(itemModel);

    itemModel = new HomeItemModel();
    itemModel.setLink(TestProtocol.DIFF_UTIL);
    itemModel.setTitle("DiffUtil");
    models.add(itemModel);

    itemModel = new HomeItemModel();
    itemModel.setLink(TestProtocol.SAO_YI_SAO);
    itemModel.setTitle("扫码");
    models.add(itemModel);


    itemModel = new HomeItemModel();
    itemModel.setLink(TestProtocol.RXJAVA);
    itemModel.setTitle("RxJava");
    models.add(itemModel);

    itemModel = new HomeItemModel();
    itemModel.setLink(TestProtocol.EXCEPTION_TEST);
    itemModel.setTitle("异常捕获器练习");
    models.add(itemModel);


    return models;
  }

  public static List<HomeItemModel> getExplore() {
    List<HomeItemModel> models = new ArrayList<>(12);
    HomeItemModel itemModel;
    itemModel = new HomeItemModel();
    itemModel.setLink(TestProtocol.JET_PACK);
    itemModel.setTitle("Jetpack");
    models.add(itemModel);

    itemModel = new HomeItemModel();
    itemModel.setLink(TestProtocol.KOTLIN);
    itemModel.setTitle("Kotlin");
    models.add(itemModel);

    itemModel = new HomeItemModel();
    itemModel.setLink(TestProtocol.DAGGER);
    itemModel.setTitle("Dagger");
    models.add(itemModel);

    itemModel = new HomeItemModel();
    itemModel.setLink(TestProtocol.LIVE_PRE_PUSH);
    itemModel.setTitle("直播");
    models.add(itemModel);
    return models;
  }

  public static List<HomeItemModel> getLive(){
    List<HomeItemModel> models = new ArrayList<>(12);
    HomeItemModel itemModel;
    itemModel = new HomeItemModel();
    itemModel.setLink(TestProtocol.LIVE_PRE_PUSH);
    itemModel.setTitle("PrePush");
    models.add(itemModel);

    return models;
  }
}
