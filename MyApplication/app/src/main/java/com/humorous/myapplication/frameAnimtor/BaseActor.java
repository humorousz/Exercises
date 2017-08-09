package com.humorous.myapplication.frameAnimtor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.humorousz.commonutils.log.Logger;
import com.humorousz.uiutils.helper.UIUtils;

/**
 * Created by zhangzhiquan on 2017/8/3.
 */

public abstract class BaseActor extends SurfaceView implements SurfaceHolder.Callback{
    public static final int State_Start = 0x01;
    public static final int State_End = 0x02;
    protected AnimStateListener mListener;
    public BaseActor(Context context) {
        super(context);
    }


    public void setAnimStateListener(AnimStateListener listener){
        mListener = listener;
    }


    protected void doDrawCenter(Canvas canvas,Bitmap bitmap){
        int left = UIUtils.getScreenWidth()/2 - bitmap.getWidth()/2;
        int top = UIUtils.getScreenHeight()/2 - bitmap.getHeight()/2;
        Paint paint = new Paint();//实例化画笔
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(0xffffffff);
        paint.setAntiAlias(true);
        doDraw(canvas,bitmap,new Rect(0,0,bitmap.getWidth(),bitmap.getHeight())
                ,new Rect(left,top,left + bitmap.getWidth(),top + bitmap.getHeight()),paint);
    }

    protected void doDrawMatchParent(Canvas canvas,Bitmap bitmap,int width,int height){
        Paint paint = new Paint();//实例化画笔
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(0xffffffff);
        paint.setAntiAlias(true);
        doDraw(canvas,bitmap,null,new Rect(0,0,width,height),paint);
    }
    protected void doDrawBottom(Canvas canvas,Bitmap bitmap,int width,int height){
        Paint paint = new Paint();//实例化画笔
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(0xffffffff);
        paint.setAntiAlias(true);
        int h = bitmap.getWidth() * height / width;
        int begin = h < bitmap.getHeight() ? bitmap.getHeight() - h : 0;
        doDraw(canvas, bitmap, new Rect(0, begin, bitmap.getWidth(), h+begin), new Rect(0, 0, width, height), paint);
    }

    protected void doDraw(Canvas canvas, Bitmap bitmap,Rect src,Rect dst,Paint paint){
        if(canvas == null)
            return;
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        canvas.save();
        canvas.drawBitmap(bitmap,src
                ,dst,paint);
        canvas.restore();
    }

    public interface AnimStateListener{
        void onStartAnim();
        void onEndAnim();
    }
}
