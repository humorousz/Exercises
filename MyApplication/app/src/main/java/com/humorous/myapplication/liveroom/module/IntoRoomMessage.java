package com.humorous.myapplication.liveroom.module;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;

/**
 * @author zhangzhiquan
 * @date 2018/8/14
 */
public class IntoRoomMessage extends DefaultChatMessage {
    private static final ForegroundColorSpan NAME_COLOR_SPAN = new ForegroundColorSpan(Color.parseColor("#99ffffff"));
    private static final ForegroundColorSpan CONTENT_COLOR_SPAN = new ForegroundColorSpan(Color.parseColor("#ffffff"));
    public IntoRoomMessage(CharSequence name, CharSequence content) {
        super(name, content);
    }

    public IntoRoomMessage(CharSequence name, CharSequence content, String userId) {
        super(name, content, userId);
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

    @Override
    public int getType() {
        return IChatMessage.INTO_ROOM_MSG;
    }
}
