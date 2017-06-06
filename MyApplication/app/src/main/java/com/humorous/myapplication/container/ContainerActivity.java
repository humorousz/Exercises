package com.humorous.myapplication.container;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Window;
import android.widget.FrameLayout;

import com.humorous.myapplication.R;
import com.humorous.myapplication.config.factory.TestFragmentFactory;
import com.humorousz.uiutils.helper.ToastUtil;
import com.humorousz.uiutils.view.BaseFragment;

public class ContainerActivity extends FragmentActivity {
    public static final String HAS_TITLE = "hasTitle";
    public static final String FRAMGNET_TYPE = "type";
    FrameLayout mContainer;
    BaseFragment mFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean hasTitle = getIntent().getBooleanExtra(HAS_TITLE,true);
        if(!hasTitle){
            getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        TestFragmentFactory.TYPE type = (TestFragmentFactory.TYPE) getIntent().getSerializableExtra(FRAMGNET_TYPE);
        if(type == null){
            ToastUtil.showToast(this,"no fragment type");
            finish();
        }
        setContentView(R.layout.activity_container);
        mFragment = TestFragmentFactory.createFragment(type);
        mContainer = (FrameLayout) findViewById(R.id.test_container);
        FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
        tr.add(R.id.test_container,mFragment);
        tr.commit();
    }
}
