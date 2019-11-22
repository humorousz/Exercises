package com.humorous.myapplication.coordinatorTest;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import android.view.ViewGroup;


import com.humorousz.uiutils.view.BaseFragment;

import java.util.List;

/**
 * Created by zhangzhiquan on 2017/5/30.
 */

public class HomePagerAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> mFragments;
    public HomePagerAdapter(FragmentManager fm, List<BaseFragment> fragments) {
        super(fm);
        this.mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        if(mFragments == null || mFragments.size() <= position)
            return null;
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        if(mFragments == null)
            return 0;
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(mFragments == null || mFragments.size() <= position){
            return null;
        }
        return mFragments.get(position).getTitle();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
