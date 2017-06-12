package com.humorous.myapplication.nested.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

/**
 * Created by zhangzhiquan on 2017/6/12.
 */

public class NestedRelativeLayout extends RelativeLayout {
    public NestedRelativeLayout(Context context) {
        this(context,null);
    }

    public NestedRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public NestedRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
