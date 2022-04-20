package com.humorusz.practice.shader.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class GradinetDrawable extends Drawable {
    private Bitmap mBitmap;
    private Context mContext;
    public GradinetDrawable(Context context,Bitmap bitmap){
        mBitmap = bitmap;
        mContext = context;
    }

    private float cur = 0;
    @Override
    public boolean setVisible(boolean visible, boolean restart) {
        ValueAnimator animator = ValueAnimator.ofFloat(0,1);
        animator.setDuration(5000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                cur = (float) animation.getAnimatedValue();
                invalidateSelf();
            }
        });
//        animator.start();
        return super.setVisible(visible, restart);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        if(mBitmap == null){
            return;
        }
        BitmapShader shader = new BitmapShader(mBitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        float scalex = (getBounds().right-getBounds().left) * 1.0f / mBitmap.getWidth();
        float scaley = (getBounds().bottom-getBounds().top) * 1.0f / mBitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.setScale(scalex,scaley);
        shader.setLocalMatrix(matrix);
        Paint paint = new Paint();
        paint.setShader(shader);
        paint.setColor(Color.RED);
        canvas.drawRoundRect(new RectF(0,0, (int) (getBounds().width()),getBounds().height()),150,150,paint);
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }
}
