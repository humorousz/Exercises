package com.example.zhangzhiquan.myapplication.lifeCyclerTest;

import android.content.Intent;

/**
 * Created by zhangzhiquan on 2017/4/6.
 */

public class LifeActivity1 extends LifeCyclerBaseActivity {
    protected static final String TAG = "LifeActivity1";

    @Override
    String getTitleString() {
        return "LifeActivty1";
    }

    @Override
    protected void onTextClick() {
        startActivity(new Intent(LifeActivity1.this,LifeActivity2.class));
    }
}
