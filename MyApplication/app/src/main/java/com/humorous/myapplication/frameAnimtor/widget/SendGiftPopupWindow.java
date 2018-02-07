package com.humorous.myapplication.frameAnimtor.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;


/**
 * @author Created by zhangzhiquan on 2017/8/3.
 */

public class SendGiftPopupWindow extends PopupWindow {
    private View contentView;
    private View mTiedView;
    public SendGiftPopupWindow(Context context,View contentView,View tiedView) {
        super(context);
        this.contentView = contentView;
        this.mTiedView = tiedView;
        this.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        this.setHeight(WindowManager.LayoutParams.MATCH_PARENT);
        this.update();
        this.setOutsideTouchable(true);
        this.setFocusable(true);
        setTouchable(true);
        this.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void show(){
        if(isShowing() ){
            return;
        }
        setContentView(contentView);
        showAtLocation(mTiedView, Gravity.BOTTOM,0,0);
    }

}
