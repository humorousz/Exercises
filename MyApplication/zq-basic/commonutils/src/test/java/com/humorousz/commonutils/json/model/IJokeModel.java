package com.humorousz.commonutils.json.model;


import java.util.List;

/**
 * Created by zhangzhiquan on 2017/4/22.
 */

public interface IJokeModel {
    JokeModel getModel();
    List<JokeModel.JokeItem> getJokeItems();

}
