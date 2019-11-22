package com.humorous.myapplication.coordinatorTest.behavior;

import android.content.Context;
import com.google.android.material.appbar.AppBarLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by zhangzhiquan on 2017/6/9.
 */

public class BelowBehavior extends AppBarLayout.ScrollingViewBehavior{


    public BelowBehavior(Context context, AttributeSet attr){
        super(context,attr);
    }
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof ImageView;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        child.setY(dependency.getY()+dependency.getHeight());
        return true;
    }
}
