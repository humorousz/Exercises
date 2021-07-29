package com.humorusz.practice.nested.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by zhangzhiquan on 2017/6/12.
 */

public class TestImageView extends ImageView {
    public TestImageView(Context context) {
        this(context,null);
    }

    public TestImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
