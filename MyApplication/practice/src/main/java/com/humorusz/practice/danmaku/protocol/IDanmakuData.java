package com.humorusz.practice.danmaku.protocol;

/**
 * @author zhangzhiquan
 * on 2018/5/20
 * 弹幕Data
 */
public interface IDanmakuData {
    /**
     * Nick name
     * @return
     */
    String getNickName();

    /**
     * Nick name color
     * @return
     */
    int getNickNameColor();

    /**
     * Background color
     * @return
     */
    int getBackgroundColor();

    /**
     * content
     * @return
     */
    String getContent();

    /**
     * content color
     * @return
     */
    int getContentColor();

    /**
     * icon url;
     * @return
     */
    String getUserIconUrl();

    int getPriority();
}
