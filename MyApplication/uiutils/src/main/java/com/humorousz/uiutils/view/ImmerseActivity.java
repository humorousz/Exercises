package com.humorousz.uiutils.view;

import android.os.Bundle;
import androidx.annotation.Nullable;

import com.humorousz.uiutils.R;
import com.humorousz.uiutils.helper.StatusBarUtil;

/**
 * @author zhangzhiquan
 * @date 2018/6/26
 */
abstract public class ImmerseActivity extends BaseSlideCloseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        StatusBarUtil.setTranslucentForImageView(this,0,findViewById(getPaddingStatusViewId()));
        StatusBarUtil.setColor(this,getStatusBarColor(),getStatusBarAlpha());
    }

    public int getPaddingStatusViewId(){
        return R.id.statusbarutil_offset_view;
    }

    public int getStatusBarColor(){
        return getResources().getColor(R.color.colorPrimary);
    }

    public int getStatusBarAlpha(){
        return 99;
    }
}
