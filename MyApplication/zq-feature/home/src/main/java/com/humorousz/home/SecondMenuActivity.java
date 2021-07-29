package com.humorousz.home;


import android.os.Bundle;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.widget.TextView;

import com.humorousz.home.config.api.Api;
import com.humorousz.home.home.HomeFragment;
import com.humorousz.uiutils.view.BaseFragment;
import com.humorousz.uiutils.view.ImmerseActivity;

public class SecondMenuActivity extends ImmerseActivity {

    public static final String TYPE = "type";
    private BaseFragment mFragment;
    private Toolbar mToolBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_menu);
        initToolBar();
        Api.SECOND_MENU type = (Api.SECOND_MENU) getIntent().getSerializableExtra(TYPE);
        mFragment = HomeFragment.newInstance(type);
        FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
        tr.add(R.id.second_menu_container,mFragment);
        tr.commit();
    }

    private void initToolBar(){
        mToolBar = findViewById(R.id.toolbar);
        TextView title = mToolBar.findViewById(R.id.title);
        String titleString = getIntent().getStringExtra(TITLE);
        if(!TextUtils.isEmpty(titleString)){
            title.setText(titleString);
            mToolBar.setTitle("");
        }
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected boolean isSupportSwipeBack() {
        return super.isSupportSwipeBack();
    }
}
