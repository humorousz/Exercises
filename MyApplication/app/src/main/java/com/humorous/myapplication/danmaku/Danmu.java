package com.humorous.myapplication.danmaku;

import android.text.SpannableString;

/**
 * Created by liuli on 2016/11/4.
 */

public class Danmu {
    public long id;
    public long userId;
    public boolean isGuest;
    public String avatarUrl;
    public String nickName;
    public SpannableString content;
    public DanmuConfig danmuConfig;

    public Danmu(long userId, boolean isGuest, String avatarUrl, String nickName, SpannableString content) {
        this.id = System.currentTimeMillis();
        this.userId = userId;
        this.isGuest = isGuest;
        this.avatarUrl = avatarUrl;
        this.content = content;
        this.nickName = nickName;
    }


    public Danmu(long userId, boolean isGuest, String avatarUrl, String nickName, SpannableString content, DanmuConfig config) {
        this.id = System.currentTimeMillis();
        this.userId = userId;
        this.isGuest = isGuest;
        this.avatarUrl = avatarUrl;
        this.content = content;
        this.nickName = nickName;
        this.danmuConfig = config;
    }

}
