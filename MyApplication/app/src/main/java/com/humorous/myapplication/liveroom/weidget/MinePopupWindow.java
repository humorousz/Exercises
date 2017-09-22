package com.humorous.myapplication.liveroom.weidget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.humorous.myapplication.R;
import com.humorousz.commonutils.log.Logger;
import com.humorousz.uiutils.helper.UIUtils;

/**
 * Created by zhangzhiquan on 2017/9/21.
 */

public class MinePopupWindow extends PopupWindow {

    public MinePopupWindow(Activity activity,View parent){
        super(activity);
        LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.layout_mine_popup, null);
        this.setContentView(contentView);
        // 设置弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置弹出窗体可点击
        this.setTouchable(true);
        this.setFocusable(true);
        // 设置点击是否消失
        this.setOutsideTouchable(true);
        //设置弹出窗体动画效果
        //this.setAnimationStyle(R.style.PopupAnimation);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable background = new ColorDrawable(0x80000000);
        //设置弹出窗体的背景
        this.setBackgroundDrawable(background);
    }

    public void showPopupWindow(View parent) {

        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            if (Build.VERSION.SDK_INT < 24) {
                this.showAsDropDown(parent);
            } else {
                int[] location = new int[2];
                parent.getLocationInWindow(location);
                int y = location[1];
                Logger.d("ChangePop","y:"+location[1]+" size:"+(y+parent.getHeight()));
                setHeight(UIUtils.getScreenHeight() - y - parent.getHeight());
                this.showAtLocation(parent, Gravity.NO_GRAVITY, 0, y + parent.getHeight());
            }
        } else {
            this.dismiss();
        }
    }
}
