package com.humorousz.networklibrary;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhangzhiquan
 * @date 2018/11/11
 */
public class TianBaseResponse implements Serializable{
    @SerializedName("code")
    public int code;
    @SerializedName("msg")
    public String message;
    @SerializedName("newslist")
    public List<BaseItem> itemList;
    public static class BaseItem{
        @SerializedName("ctime")
        String time;
        @SerializedName("title")
        String title;
        @SerializedName("description")
        String description;
        @SerializedName("picUrl")
        String picUrl;
        @SerializedName("url")
        String url;
    }
}
