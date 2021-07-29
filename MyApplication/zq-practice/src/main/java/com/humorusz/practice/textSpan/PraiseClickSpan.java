package com.humorusz.practice.textSpan;

import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.ClickableSpan;

/**
 * Created by zhangzhiquan on 2017/10/22.
 */

public abstract class PraiseClickSpan extends ClickableSpan {
    private int clickBgColor= Color.TRANSPARENT;
    @Override
    public void updateDrawState(TextPaint ds) {
        ds.bgColor = clickBgColor;
        ds.setUnderlineText(false);
    }
    public void setClickBackgroundColor(int color){
        this.clickBgColor=color;
    }
}
