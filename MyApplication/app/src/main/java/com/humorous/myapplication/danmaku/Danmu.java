package com.humorous.myapplication.danmaku;

import android.graphics.Color;
import android.text.SpannableString;

import com.humorous.myapplication.danmaku.protocol.IDanmakuData;

/**
 * Created by liuli on 2016/11/4.
 */

public class Danmu implements IDanmakuData{
    public long id;
    public long userId;
    public boolean isGuest;
    public String avatarUrl;
    public String nickName;
    public String content;
    public DanmuConfig danmuConfig;
    public int priority;

    public Danmu(long userId, boolean isGuest, String avatarUrl, String nickName, String content,int priority) {
        this.id = System.currentTimeMillis();
        this.userId = userId;
        this.isGuest = isGuest;
        this.avatarUrl = avatarUrl;
        this.content = content;
        this.nickName = nickName;
        this.priority = priority;
    }


    public Danmu(long userId, boolean isGuest, String avatarUrl, String nickName, String content, DanmuConfig config) {
        this.id = System.currentTimeMillis();
        this.userId = userId;
        this.isGuest = isGuest;
        this.avatarUrl = avatarUrl;
        this.content = content;
        this.nickName = nickName;
        this.danmuConfig = config;
    }

    @Override
    public String getNickName() {
        return nickName;
    }

    @Override
    public int getNickNameColor() {
        return Color.RED;
    }

    @Override
    public int getBackgroundColor() {
        return Color.parseColor("#55000000");
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public int getContentColor() {
        return Color.WHITE;
    }

    @Override
    public String getUserIconUrl() {
        return avatarUrl;
    }

    @Override
    public int getPriority() {
        return priority;
    }
}
