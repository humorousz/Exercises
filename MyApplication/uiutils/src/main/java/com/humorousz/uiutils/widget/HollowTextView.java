package com.humorousz.uiutils.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.humorousz.uiutils.R;

public class HollowTextView extends AppCompatTextView{
  private Paint mTextPaint, mBackgroundPaint;
  private Bitmap mBackgroundBitmap,mTextBitmap,mShadingBitmap;
  private Canvas mBackgroundCanvas,mTextCanvas,mShadingCanvas;
  private RectF mBackgroundRect;
  private int mBackgroundColor;
  private boolean mShowTextColor;
  private int mCornerRadius;

  public HollowTextView(Context context) {
    this(context,null);
  }

  public HollowTextView(Context context, AttributeSet attrs) {
    this(context, attrs,-1);
  }

  public HollowTextView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initAttrs(attrs,defStyleAttr);
    initPaint();
  }


  private void initAttrs(AttributeSet attrs,int defStyleAttr){
    if(attrs == null){
      return;
    }
    TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.HollowTextView, defStyleAttr, 0);
    mBackgroundColor = a.getColor(R.styleable.HollowTextView_background_color,Color.TRANSPARENT);
    mCornerRadius = a.getDimensionPixelOffset(R.styleable.HollowTextView_corner_radius,0);
    mShowTextColor = a.getBoolean(R.styleable.HollowTextView_show_text_color,false);
    a.recycle();
  }

  /***
   * 初始化画笔属性
   */
  private void initPaint() {
    //画文字的paint
    mTextPaint = new Paint();
    //这是镂空的关键
    mTextPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
    mTextPaint.setAntiAlias(true);
    mBackgroundPaint = new Paint();
    mBackgroundPaint.setColor(mBackgroundColor);
    mBackgroundPaint.setAntiAlias(true);

  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    mBackgroundBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_4444);
    mBackgroundCanvas = new Canvas(mBackgroundBitmap);
    mTextBitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_4444);
    mTextCanvas = new Canvas(mTextBitmap);
    mBackgroundRect = new RectF(0,0,getWidth(),getHeight());
    mShadingBitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_4444);
    mShadingCanvas = new Canvas(mShadingBitmap);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    if(mTextCanvas == null || mBackgroundCanvas == null || mBackgroundBitmap == null || mTextBitmap == null){
      super.onDraw(canvas);
      return;
    }
    super.onDraw(mTextCanvas);
    drawBackground(mBackgroundCanvas);
    int sc;
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ){
       sc = canvas.saveLayer(0,0,getMeasuredWidth(),getMeasuredHeight(),null);
    }else {
       sc = canvas.saveLayer(0,0,getMeasuredWidth(),getMeasuredHeight(),null,Canvas.ALL_SAVE_FLAG);
    }
    canvas.drawBitmap(mBackgroundBitmap,0,0,null);
    canvas.drawBitmap(mTextBitmap, 0, 0, mTextPaint);
    canvas.restoreToCount(sc);
    if (mShowTextColor) {
      canvas.drawBitmap(mTextBitmap,0,0,null);
    }
  }

  private void drawBackground(Canvas canvas){
    if(mCornerRadius > 0){
      canvas.drawRoundRect(mBackgroundRect,mCornerRadius,mCornerRadius, mBackgroundPaint);
    }else {
      canvas.drawColor(mBackgroundColor);
    }
  }


}
