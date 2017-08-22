package com.humorousz.uiutils.span;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.text.style.ReplacementSpan;

/**
 * Created by zhangzhiquan on 2017/8/22.
 */

public class ChatBoxNetworkSpan extends ReplacementSpan {
    private int tint;
    private boolean tintFlag;
    private BitmapDrawable drawable;

    public void setDrawable(BitmapDrawable drawable) {
        this.drawable = drawable;
    }

    public BitmapDrawable getDrawable() {
        return drawable;
    }

    /**
     * 一般情况下ImageSpan不能改变图片的背景，在这设置背景色，然后绘制的时候先绘制背景色
     * @param tint 背景颜色
     */
    public void setTintColor(int tint) {
        this.tint = tint;
        this.tintFlag = true;
    }

    /**
     * 设置时候绘制背景颜色
     * @param flag 标记
     */
    public void setTintFlag(boolean flag) {
        tintFlag = flag;
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fontMetricsInt) {
        Rect rect = drawable.getBounds();
        if (fontMetricsInt != null) {
            Paint.FontMetricsInt fmPaint = paint.getFontMetricsInt();
            int fontHeight = fmPaint.bottom - fmPaint.top;
            int drHeight = rect.bottom - rect.top;

            int top = drHeight / 2 - fontHeight / 4;
            int bottom = drHeight / 2 + fontHeight / 4;

            fontMetricsInt.ascent = -bottom;
            fontMetricsInt.top = -bottom;
            fontMetricsInt.bottom = top;
            fontMetricsInt.descent = top;
        }
        return rect.right;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        Bitmap bitmap = drawable.getBitmap();
        if(bitmap == null || bitmap.isRecycled()) {
            return;
        }
        canvas.save();
        int transY = ((bottom - top) - drawable.getBounds().bottom) / 2 + top;
        canvas.translate(x, 0);
        //绘制背景色
        if(tintFlag) {
            Paint newPaint = new Paint();
            newPaint.setColor(tint);
            newPaint.setStyle(Paint.Style.FILL);
            canvas.drawRect(drawable.getBounds().left, top, drawable.getBounds().right, bottom, newPaint);
        }
        canvas.translate(0, transY);
        drawable.draw(canvas);
        canvas.restore();
    }
}
