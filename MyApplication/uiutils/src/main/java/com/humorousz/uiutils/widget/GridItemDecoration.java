package com.humorousz.uiutils.widget;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.humorousz.uiutils.helper.UIUtils;

/**
 * Created by zhangzhiquan on 2017/6/5.
 */

public class GridItemDecoration extends BaseItemDecoration {


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        RecyclerView.Adapter adapter = parent.getAdapter();
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();

        if (layoutParams instanceof StaggeredGridLayoutManager.LayoutParams && layoutManager instanceof StaggeredGridLayoutManager) {
            setStaggerGridOffset(layoutParams, layoutManager, adapter, outRect);
        } else if (layoutParams instanceof GridLayoutManager.LayoutParams && layoutManager instanceof GridLayoutManager) {
            setGridOffset(layoutParams, layoutManager, adapter, outRect);
        } else {
            outRect.set(0, 0, 0, 0);
        }
    }


    private void setGridOffset(ViewGroup.LayoutParams layoutParams,RecyclerView.LayoutManager layoutManager,RecyclerView.Adapter adapter,Rect outRect){
        GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
        GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams) layoutParams;

        int right = 0;
        int bottom ;
        int type = adapter.getItemViewType(params.getViewLayoutPosition());
        if (!isLastColum(params, gridLayoutManager.getSpanCount())) {
            right = getItemDecorationInsetValue(0, type);
        }
        bottom = getItemDecorationInsetValue(0, type);
        outRect.set(0, 0, right, bottom);
    }

    private void setStaggerGridOffset(ViewGroup.LayoutParams layoutParams, RecyclerView.LayoutManager layoutManager,RecyclerView.Adapter adapter,Rect outRect){
        StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) layoutParams;
        StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) layoutManager;
        int right = 0;
        int bottom;
        int type = adapter.getItemViewType(params.getViewLayoutPosition());
        if (!isLastColum(params, manager.getSpanCount())) {
            right = getItemDecorationInsetValue(0,type);
        }
        bottom = getItemDecorationInsetValue(0,type);
        outRect.set(0, 0, right, bottom);
    }

    private boolean isLastColum(StaggeredGridLayoutManager.LayoutParams params, int spanCount) {
        int spanIndex = params.getSpanIndex();
        return spanIndex + 1 == spanCount;
    }

    private boolean isLastColum(GridLayoutManager.LayoutParams params,int spanCount){
        int spanIndex = params.getSpanIndex();
        return spanIndex + 1 == spanCount;
    }


    @Override
    protected int getItemDecorationInsetValue(int position, int type) {
        return UIUtils.dip2px(3);
    }

    @Override
    protected Drawable getItemDecorationDrawable(int position, int type) {
        return new ColorDrawable(Color.parseColor("#f1f1f1"));
    }

    @Override
    protected void drawHorizontal(Canvas canvas, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int position = params.getViewLayoutPosition();
            int type = parent.getAdapter().getItemViewType(position);
            int dividerH = getItemDecorationInsetValue(position,type);
            int left = child.getLeft() - params.leftMargin;
            int top = child.getBottom() + params.bottomMargin;
            int right = child.getRight() + params.rightMargin + dividerH;
            int bottom = top + dividerH;
            Drawable drawable = getItemDecorationDrawable(position,type);
            drawable.setBounds(left, top, right, bottom);
            drawable.draw(canvas);
        }
    }
}
