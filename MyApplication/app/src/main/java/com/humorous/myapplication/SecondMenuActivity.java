package com.humorous.myapplication;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.humorous.myapplication.config.api.Api;
import com.humorous.myapplication.home.HomeFragment;
import com.humorousz.uiutils.helper.StatusBarCompat;
import com.humorousz.uiutils.view.BaseFragment;

public class SecondMenuActivity extends FragmentActivity {

    public static final String TYPE = "type";
    BaseFragment mFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_menu);
        StatusBarCompat.compat(this);
        Api.SECOND_MENU type = (Api.SECOND_MENU) getIntent().getSerializableExtra(TYPE);
        mFragment = HomeFragment.newInstance(type);
        FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
        tr.add(R.id.second_menu_container,mFragment);
        tr.commit();
    }
}
