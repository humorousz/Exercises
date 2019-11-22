package com.humorous.myapplication.coordinatorTest.widget;

import android.content.Context;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by zhangzhiquan on 2017/5/31.
 */

public class TestViewPager extends ViewPager {
    public TestViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestViewPager(Context context) {
        super(context);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }
}
