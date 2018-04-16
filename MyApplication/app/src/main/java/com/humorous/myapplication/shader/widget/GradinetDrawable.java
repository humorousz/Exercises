package com.humorous.myapplication.shader.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.humorousz.commonutils.log.Logger;

public class GradinetDrawable extends Drawable {
    private Bitmap mBitmap;
    private Context mContext;
    public GradinetDrawable(Context context,Bitmap bitmap){
        mBitmap = bitmap;
        mContext = context;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        if(mBitmap == null){
            return;
        }
        Logger.d("MRT","draw  width:"+mBitmap.getWidth()+" height:"+mBitmap.getHeight());
//        canvas.drawBitmap(mBitmap,new Rect(0,0,mBitmap.getWidth(),mBitmap.getHeight()/2),getBounds(),new Paint());
        BitmapShader shader = new BitmapShader(mBitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        Paint paint = new Paint();
        paint.setShader(shader);
        paint.setColor(Color.RED);
        canvas.drawRect(new Rect(0,0,getBounds().width(),getBounds().height()),paint);
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return 0;
    }
}
