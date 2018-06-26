package com.humorousz.uiutils.view;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.humorousz.uiutils.R;
import com.humorousz.uiutils.helper.StatusBarCompat;
import com.humorousz.uiutils.helper.StatusBarUtil;

/**
 * @author zhangzhiquan
 * @date 2018/6/26
 */
abstract public class ImmerseActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        StatusBarUtil.setTranslucentForRootPadding(this,0,getPaddingStatusViewId());
    }

    protected int getPaddingStatusViewId(){
        return R.id.statusbarutil_padding_view;
    }
}
