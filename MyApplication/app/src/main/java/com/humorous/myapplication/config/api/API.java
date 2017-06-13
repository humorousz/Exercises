package com.humorous.myapplication.config.api;

import com.humorous.myapplication.home.model.HomeItemModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangzhiquan on 2017/6/5.
 */

public class API {

    public static List<HomeItemModel> getAllItem(){
        List<HomeItemModel> models = new ArrayList<>(12);

        HomeItemModel itemModel = new HomeItemModel();
        itemModel.setLink(TestProtocol.ANT_TEST);
        itemModel.setTitle("芝麻信用效果");
        models.add(itemModel);

        itemModel = new HomeItemModel();
        itemModel.setLink(TestProtocol.TOPIC_RECYCLER_TEST);
        itemModel.setTitle("抖音新鲜页滑动渐变");
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
        itemModel.setLink(TestProtocol.NESTED_TEST);
        itemModel.setTitle("NestedScrolling");
        models.add(itemModel);

        itemModel = new HomeItemModel();
        itemModel.setLink(TestProtocol.COORDINATOR_DEMO);
        itemModel.setTitle("上滑悬浮");
        models.add(itemModel);


        return models;
    }
}
