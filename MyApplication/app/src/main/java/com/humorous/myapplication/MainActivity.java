package com.humorous.myapplication;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.humorous.myapplication.home.HomeFragment;

import com.humorousz.uiutils.view.BaseFragment;

public class MainActivity extends FragmentActivity {
    //    FrameLayout mContainer;
    private static final String TAG = "MainActivity";
    FrameLayout mContainer;
    BaseFragment mFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFragment = new HomeFragment();
        mContainer = (FrameLayout) findViewById(R.id.container);
        FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
        tr.add(R.id.container,mFragment);
        tr.commit();


    }
}
