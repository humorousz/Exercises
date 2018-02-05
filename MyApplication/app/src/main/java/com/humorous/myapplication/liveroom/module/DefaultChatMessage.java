package com.humorous.myapplication.liveroom.module;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;

/**
 * Created by zhangzhiquan on 2018/2/5.
 */

public class DefaultChatMessage implements IChatMessage {
    private static final ForegroundColorSpan NAME_COLOR_SPAN = new ForegroundColorSpan(Color.parseColor("#99ffffff"));
    private static final ForegroundColorSpan CONTENT_COLOR_SPAN = new ForegroundColorSpan(Color.parseColor("#ffffff"));
    private CharSequence name;
    private CharSequence content;
    private String userId;

    public DefaultChatMessage(CharSequence name,CharSequence content){
        this(name,content,"");
    }

    public DefaultChatMessage(CharSequence name,CharSequence content,String userId){
        this.name = name;
        this.content = content;
        this.userId = userId;
    }
    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public CharSequence getName() {
        return name;
    }

    @Override
    public CharSequence getContent() {
        return content;
    }

    @Override
    public CharSequence getRealContent() {
        //设置文字Color
        SpannableString userNameSpan = new SpannableString(getName());
        userNameSpan.setSpan(NAME_COLOR_SPAN, 0, userNameSpan.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        SpannableString contentDataSpan = new SpannableString(getContent());
        contentDataSpan.setSpan(CONTENT_COLOR_SPAN, 0, contentDataSpan.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        SpannableStringBuilder textBuilder = new SpannableStringBuilder();
        //拼接名字和内容
        textBuilder.append(userNameSpan).append(" ").append(contentDataSpan);
        return textBuilder;
    }
}
