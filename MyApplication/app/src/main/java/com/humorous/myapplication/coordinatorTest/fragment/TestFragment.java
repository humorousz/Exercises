package com.humorous.myapplication.coordinatorTest.fragment;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.humorous.myapplication.R;
import com.humorous.myapplication.coordinatorTest.HomePagerAdapter;
import com.humorousz.commonutils.log.Logger;
import com.humorousz.uiutils.helper.UIUtils;
import com.humorousz.uiutils.view.BaseFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zhangzhiquan on 2017/4/21.
 */

public class TestFragment extends BaseFragment {
    private static final String TAG = "HomeFragment";
    private ViewPager mFragmentPager;
    private TabLayout mTablayout;
    private HomePagerAdapter mAdapter;
    private CollapsingToolbarLayout mCollaps;
    private Toolbar mToolBar;
    AppBarLayout mAppbar;
    SwipeRefreshLayout mSwipe;
    TextView bottomText,titleText;
    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_test_fragment,container,false);
    }

    @Override
    public void initView(View root) {
        mFragmentPager = (ViewPager) root.findViewById(R.id.test_viewpager);
        mTablayout = (TabLayout) root.findViewById(R.id.test_tab);
        mAppbar = (AppBarLayout) root.findViewById(R.id.appbar);
        mCollaps = (CollapsingToolbarLayout) root.findViewById(R.id.collapsing);
        mToolBar = (Toolbar) root.findViewById(R.id.toolbar);
        bottomText = (TextView) root.findViewById(R.id.bottom_text);
        titleText = (TextView) root.findViewById(R.id.title_text);

        mAppbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(verticalOffset >= 0){
                    Logger.d("MRZ","dddd");
                    mSwipe.setEnabled(true);
                }else {
                    if(!mSwipe.isRefreshing()){
                        Logger.d("MRZ","3333333");
                        mSwipe.setEnabled(false);
                    }else {
                        mSwipe.setRefreshing(false);
                    }
                }
                int all = UIUtils.px2dip(mCollaps.getHeight() - mToolBar.getHeight());
                int cur = UIUtils.px2dip(Math.abs(verticalOffset));
                float p = (cur*1.0f)/(all*1.0f);
                bottomText.setAlpha(1-p);
                if(Float.compare(0.7f,p)<0){
                    titleText.setText("我是头部文字");
                }else{
                    titleText.setText("");
                }
                Logger.d("ZZZ",p);
                Logger.d("DDD", UIUtils.px2dip(Math.abs(verticalOffset)));
            }
        });
        mSwipe = (SwipeRefreshLayout) root.findViewById(R.id.activity_main);
        mSwipe.setProgressViewOffset(true,-20,100);
        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Timer timer = new Timer();

                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mSwipe.setRefreshing(false);
                            }
                        });
                    }
                };
                timer.schedule(timerTask,1000);
            }
        });
        init();
    }

    private void init(){
        List<BaseFragment> fragments = new ArrayList<>();
        fragments.add(new TestListFragment());
        fragments.add(new TestListFragment());
        mAdapter = new HomePagerAdapter(getChildFragmentManager(),fragments);
        mFragmentPager.setAdapter(mAdapter);
        mFragmentPager.setOffscreenPageLimit(4);
        mTablayout.setupWithViewPager(mFragmentPager);
        mTablayout.setTabMode(TabLayout.MODE_FIXED);
    }

    @Override
    public String getLogTitle() {
        return TAG;
    }

    @Override
    public String getTitle() {
        return "首页";
    }
}
