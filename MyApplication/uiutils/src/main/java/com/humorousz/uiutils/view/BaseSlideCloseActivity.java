package com.humorousz.uiutils.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;

/**
 * @author zhangzhiquan
 * 滑动退出Actvity的基类
 */
public class BaseSlideCloseActivity extends BaseActivity implements SlidingPaneLayout.PanelSlideListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initSlideBackClose();
        super.onCreate(savedInstanceState);
    }

    private void initSlideBackClose(){
        if(!isSupportSwipeBack()){
            return;
        }
        SlidingPaneLayout slidingPaneLayout = new SlidingPaneLayout(this);
        try {
            Field overhangSize = SlidingPaneLayout.class.getDeclaredField("mOverhangSize");
            overhangSize.setAccessible(true);
            overhangSize.set(slidingPaneLayout,0);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        slidingPaneLayout.setPanelSlideListener(this);
        slidingPaneLayout.setSliderFadeColor(Color.TRANSPARENT);
        View leftView = new View(this);
        leftView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        slidingPaneLayout.addView(leftView,0);
        ViewGroup decorView = (ViewGroup) getWindow().getDecorView();

        ViewGroup decorChild = (ViewGroup) decorView.getChildAt(0);
        decorChild.setBackgroundColor(Color.WHITE);
        decorView.removeView(decorChild);
        decorView.addView(slidingPaneLayout);
        slidingPaneLayout.addView(decorChild,1);

    }

    @Override
    public void onPanelSlide(@NonNull View panel, float slideOffset) {

    }

    @Override
    public void onPanelOpened(@NonNull View panel) {
        finish();
    }

    @Override
    public void onPanelClosed(@NonNull View panel) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    protected boolean isSupportSwipeBack(){
        return true;
    }
}
