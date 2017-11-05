package com.humorous.myapplication.vectorDrawableTest;

/**
 * Created by zhangzhiquan on 2017/11/5.
 */

public class GuardMessage {
    public static int GUARD_MONTH = 1;
    public static int GUARD_YEAR = 2;
    private String name;
    private String link;
    private int type;

    public GuardMessage(String name, String link, int type){
        this.name = name;
        this.type = type;
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }

    public int getType() {
        return type;
    }


}
