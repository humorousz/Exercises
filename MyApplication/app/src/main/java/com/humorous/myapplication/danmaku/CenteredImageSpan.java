package com.humorous.myapplication.danmaku;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.style.ImageSpan;

import java.lang.ref.WeakReference;

/**
 * 图文混排使图片文字基于中线对齐
 * Created by liuli on 16/11/4.
 */
public class CenteredImageSpan extends ImageSpan {

    private WeakReference<Drawable> mDrawableRef;
    private int size;

    public CenteredImageSpan(final Drawable drawable, int size) {
        super(drawable);
        this.size = size;
    }

    @Override
    public int getSize(Paint paint, CharSequence text,
                       int start, int end,
                       Paint.FontMetricsInt fm) {
        Drawable d = getCachedDrawable();
        Rect rect = d.getBounds();

        if (fm != null) {
            Paint.FontMetricsInt pfm = paint.getFontMetricsInt();
            fm.ascent = pfm.ascent;
            fm.descent = pfm.descent;
            fm.top = pfm.top;
            fm.bottom = pfm.bottom;
        }

        return rect.right;
    }

    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text,
                     int start, int end, float x,
                     int top, int y, int bottom, @NonNull Paint paint) {
        Drawable b = getCachedDrawable();
        canvas.save();

        int drawableHeight = size;
        int fontAscent = paint.getFontMetricsInt().top;
        int fontDescent = paint.getFontMetricsInt().bottom;
        int transY = bottom - b.getBounds().bottom +
                (drawableHeight - fontDescent + fontAscent) / 2;
        canvas.translate(x, transY);
        b.draw(canvas);
        canvas.restore();
    }

    private Drawable getCachedDrawable() {
        WeakReference<Drawable> wr = mDrawableRef;
        Drawable d = null;

        if (wr != null)
            d = wr.get();

        if (d == null) {
            d = getDrawable();
            mDrawableRef = new WeakReference<>(d);
        }

        return d;
    }
}

