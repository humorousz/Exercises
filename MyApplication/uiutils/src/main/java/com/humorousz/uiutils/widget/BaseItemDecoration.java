package com.humorousz.uiutils.widget;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by zhangzhiquan on 2017/5/3.
 */

public abstract class BaseItemDecoration extends RecyclerView.ItemDecoration {


    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        drawHorizontal(c,parent);
        drawVertical(c,parent);
    }


    protected void drawVertical(Canvas canvas, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int position = params.getViewLayoutPosition();
            int type = parent.getAdapter().getItemViewType(position);
            int dividerH = getItemDecorationInsetValue(position,type);
            int left = child.getRight() + params.rightMargin;
            int top = child.getTop() - params.topMargin - dividerH;
            int right = left + dividerH;
            int bottom = child.getBottom() + params.bottomMargin;
            Drawable drawable = getItemDecorationDrawable(position,type);
            drawable.setBounds(left, top, right, bottom);
            drawable.draw(canvas);
        }
    }

    protected void drawHorizontal(Canvas canvas, RecyclerView parent) {
        int childCount = parent.getChildCount();
        //from the second item
        for (int i = 1; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int position = params.getViewLayoutPosition();
            int type = parent.getAdapter().getItemViewType(position);
            int dividerH = getItemDecorationInsetValue(position,type);
            int left = child.getLeft() - params.leftMargin;
            int top = child.getTop() - params.topMargin - dividerH;
            int right = child.getRight() + params.rightMargin;
            int bottom = top + dividerH;
            Drawable drawable = getItemDecorationDrawable(position,type);
            drawable.setBounds(left, top, right, bottom);
            drawable.draw(canvas);
        }
    }


    protected abstract int getItemDecorationInsetValue(int position,int type);

    protected abstract Drawable getItemDecorationDrawable(int position,int type);
}
