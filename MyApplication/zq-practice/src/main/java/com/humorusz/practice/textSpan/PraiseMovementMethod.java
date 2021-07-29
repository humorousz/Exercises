package com.humorusz.practice.textSpan;

import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by zhangzhiquan on 2017/10/22.
 */

public class PraiseMovementMethod extends LinkMovementMethod {
    private static PraiseMovementMethod sInstance;
    private int color;
    private int defaultBgColor;
    private PraiseClickSpan mPressedSpan;

    private PraiseMovementMethod(int color,int defaultBgColor){
        this.color = color;
        this.defaultBgColor = defaultBgColor;
    }

    @Override
    public boolean onTouchEvent(TextView textView, Spannable spannable, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            mPressedSpan = getPressedSpan(textView, spannable, event);
            if (mPressedSpan != null) {
                mPressedSpan.setClickBackgroundColor(color);
                Selection.setSelection(spannable, spannable.getSpanStart(mPressedSpan),
                        spannable.getSpanEnd(mPressedSpan));
            }
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            PraiseClickSpan touchedSpan = getPressedSpan(textView, spannable, event);
            if (mPressedSpan != null && touchedSpan != mPressedSpan) {
                mPressedSpan.setClickBackgroundColor(defaultBgColor);
                mPressedSpan = null;
                Selection.removeSelection(spannable);
            }
        } else {
            if (mPressedSpan != null) {
                mPressedSpan.setClickBackgroundColor(defaultBgColor);
                super.onTouchEvent(textView, spannable, event);
            }
            mPressedSpan = null;
            Selection.removeSelection(spannable);
        }
        return true;
    }

    private PraiseClickSpan getPressedSpan(TextView textView, Spannable spannable, MotionEvent event) {

        int x = (int) event.getX();
        int y = (int) event.getY();

        x -= textView.getTotalPaddingLeft();
        y -= textView.getTotalPaddingTop();

        x += textView.getScrollX();
        y += textView.getScrollY();

        Layout layout = textView.getLayout();
        int line = layout.getLineForVertical(y);
        int off = layout.getOffsetForHorizontal(line, x);

        PraiseClickSpan[] link = spannable.getSpans(off, off, PraiseClickSpan.class);
        PraiseClickSpan touchedSpan = null;
        if (link.length > 0) {
            touchedSpan = link[0];
        }
        return touchedSpan;
    }

    public static PraiseMovementMethod getInstance(int color,int defaultBgColor){
        if(sInstance == null){
            sInstance = new PraiseMovementMethod(color,defaultBgColor);
        }

        return sInstance;
    }
}
