package com.humorous.myapplication.shader.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * @author zhangzhiquan
 * @date 2018/04/16
 * 可渐变的ImageView
 */
public class GradientImageView extends AppCompatImageView {
    private Bitmap mBitMap;
    private Paint mPaint;
    private Shader mShader;
    public GradientImageView(Context context) {
        super(context);
    }

    public GradientImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GradientImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    Matrix matrix = new Matrix();
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        Drawable drawable = getDrawable();
//        if (drawable == null) {
//            return; // couldn't resolve the URI
//        }
//        if(drawable.getIntrinsicWidth() == 0 || drawable.getIntrinsicHeight() == 0){
//            // nothing to draw (empty bounds)
//            return;
//        }
//        if(mBitMap == null){
//            BitmapDrawable  bitmapDrawable = (BitmapDrawable) drawable;
//            mBitMap = bitmapDrawable.getBitmap();
//            mPaint = new Paint();
//            mShader = new BitmapShader(mBitMap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
//        }
//
////        drawable.draw(canvas);
//        canvas.drawBitmap(mBitMap,new Rect(0,0,mBitMap.getWidth()/2,mBitMap.getHeight()),new Rect(getLeft(),getTop(),getRight(),getBottom()),new Paint());




//        if (mDrawMatrix == null && mPaddingTop == 0 && mPaddingLeft == 0) {
//            mDrawable.draw(canvas);
//        } else {
//            final int saveCount = canvas.getSaveCount();
//            canvas.save();
//
//            if (mCropToPadding) {
//                final int scrollX = mScrollX;
//                final int scrollY = mScrollY;
//                canvas.clipRect(scrollX + mPaddingLeft, scrollY + mPaddingTop,
//                        scrollX + mRight - mLeft - mPaddingRight,
//                        scrollY + mBottom - mTop - mPaddingBottom);
//            }
//
//            canvas.translate(mPaddingLeft, mPaddingTop);
//
//            if (mDrawMatrix != null) {
//                canvas.concat(mDrawMatrix);
//            }
//            mDrawable.draw(canvas);
//            canvas.restoreToCount(saveCount);
//        }
    }
}
