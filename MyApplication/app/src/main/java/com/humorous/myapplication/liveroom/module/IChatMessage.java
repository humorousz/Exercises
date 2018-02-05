package com.humorous.myapplication.liveroom.module;


/**
 * @author zhangzhiquan
 * @date 2018/2/5
 * 聊天item信息获取的module
 * */

public interface IChatMessage {

    /**
     * 获取用户ID
     * @return
     */
    String getUserId();

    /**
     * 获取用户昵称
     * @return 用户昵称
     */
    CharSequence getName();

    /**
     * 获取发言内容
     * @return 发言内容
     */
    CharSequence getContent();

    /**
     * 获取完整需要展示的每条聊天信息
     * <eg>testUser:i am test message</eg>
     * @return 需要展示的聊天信息
     */
    CharSequence getRealContent();
}
