package com.humorousz.uiutils.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;

import com.humorousz.uiutils.R;
import com.humorousz.uiutils.helper.StatusBarUtil;

/**
 * @author zhangzhiquan
 * @date 2018/6/28
 * 全屏Activity.
 */
abstract public class FullScreenActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        StatusBarUtil.setTranslucentForImageView(this,0,findViewById(getPaddingStatusViewId()));
        StatusBarUtil.setColor(this,getStatusBarColor(),55);
    }

    public int getPaddingStatusViewId(){
        return R.id.statusbarutil_offset_view;
    }

    @IntRange(from = 1,to = 255) public int getStatusBarAlpha(){
        return 255;
    }

    public int getStatusBarColor(){
        return Color.TRANSPARENT;
    }

}
