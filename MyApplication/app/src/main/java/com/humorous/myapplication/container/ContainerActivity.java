package com.humorous.myapplication.container;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.humorous.myapplication.R;
import com.humorous.myapplication.config.factory.TestFragmentFactory;
import com.humorousz.commonutils.log.Logger;
import com.humorousz.uiutils.helper.StatusBarCompat;
import com.humorousz.uiutils.helper.ToastUtil;
import com.humorousz.uiutils.view.BaseActivity;
import com.humorousz.uiutils.view.BaseFragment;

/**
 * @author zhangzhiquan
 */

public class ContainerActivity extends BaseActivity {
    private static final String TAG = "ContainerActivity";
    public static final String HAS_TITLE = "hasTitle";
    public static final String FRAGMENT_TYPE = "type";
    public static final String LANDSCAPE = "landscape";
    private FrameLayout mContainer;
    private BaseFragment mFragment;
    private Toolbar mToolBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(savedInstanceState != null){
            String FRAGMENTS_TAG = "android:support:fragments";
            savedInstanceState.remove(FRAGMENTS_TAG);
        }
        Boolean land = getIntent().getBooleanExtra(LANDSCAPE,false);
        if(!land){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onCreate(savedInstanceState);
        TestFragmentFactory.TYPE type = (TestFragmentFactory.TYPE) getIntent().getSerializableExtra(FRAGMENT_TYPE);
        if(type == null){
            ToastUtil.showToast(this,"no fragment type");
            finish();
        }
        setContentView(R.layout.activity_container);
        initToolBar();
        StatusBarCompat.compat(this);
        mFragment = TestFragmentFactory.createFragment(type);
        mContainer = (FrameLayout) findViewById(R.id.test_container);
        FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
        tr.add(R.id.test_container,mFragment);
        tr.commit();
    }


    private void initToolBar(){
        boolean hasTitle = getIntent().getBooleanExtra(HAS_TITLE,true);
        if(hasTitle){
            mToolBar = findViewById(R.id.toolbar);
            mToolBar.setVisibility(View.VISIBLE);
            TextView title = mToolBar.findViewById(R.id.title);
            String titleString = getIntent().getStringExtra(TITLE);
            if(!TextUtils.isEmpty(titleString)){
                title.setText(titleString);
                mToolBar.setTitle("");
            }
            setSupportActionBar(mToolBar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}
