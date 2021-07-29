package com.humorusz.practice.liveroom.weidget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.humorousz.commonutils.log.Logger;
import com.humorousz.uiutils.helper.UIUtils;
import com.humorusz.practice.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangzhiquan on 2017/8/28.
 */

public class LoadingImageView extends ImageView {
    private final Bitmap mSrc;
    private final Activity mActivity;
    private Matrix mShaderMatrix;
    private Paint mImagePaint;//画笔-image
    private Paint mViewPaint;//画笔-波浪进度
    private int mProgressColor;//波浪色(深)
    private int mFrontProgressColor;//波浪色(浅)
    private BitmapShader mWaveShader;

    private static final float DEFAULT_AMPLITUDE_RATIO = 0.05f;
    private static final float DEFAULT_WATER_LEVEL_RATIO = 0.5f;
    private static final float DEFAULT_WAVE_LENGTH_RATIO = 1.0f;
    private static final float DEFAULT_WAVE_SHIFT_RATIO = 0.0f;

    private float mAmplitudeRatio = DEFAULT_AMPLITUDE_RATIO;//振幅
    private float mWaveLengthRatio = DEFAULT_WAVE_LENGTH_RATIO;//波长
    private float mWaterLevelRatio;//水深
    private float mWaveShiftRatio = DEFAULT_WAVE_SHIFT_RATIO;

    private double mDefaultAngularFrequency;
    private float mDefaultAmplitude;
    private float mDefaultWaterLevel;
    private int mDefaultWaveLength;

    private Bitmap mBitmap;
    private Canvas bitmapCanvas;
    private Paint mBorderPaint;
    private AnimatorSet mAnimatorSet;
    private Rect rect,rect2;
    private WindowManager window;


    public LoadingImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mActivity = (Activity) context;
        TypedArray t = context.obtainStyledAttributes(attrs,
                R.styleable.LF_LoadingImageView, 0, 0);
        BitmapDrawable drawable = (BitmapDrawable) t.getDrawable(R.styleable.LF_LoadingImageView_src);
        drawable.setAntiAlias(true);
        mProgressColor = t.getColor(R.styleable.LF_LoadingImageView_backColor, Color.parseColor("#b2ffc805"));
        mFrontProgressColor = t.getColor(R.styleable.LF_LoadingImageView_frontColor, Color.parseColor("#80ffc805"));
        mSrc = drawableToBitamp(drawable);
        t.recycle();
        init();
    }


    private void init() {
        window = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        mShaderMatrix = new Matrix();
        mViewPaint = new Paint();
        PorterDuffXfermode mode = new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP);
        mViewPaint.setXfermode(mode);
        mViewPaint.setDither(true);
        mViewPaint.setFilterBitmap(true);
        mViewPaint.setAntiAlias(true);

        mImagePaint = new Paint();
        mImagePaint.setDither(true);
        mImagePaint.setFilterBitmap(true);
        mImagePaint.setAntiAlias(true);

        mBorderPaint = new Paint();
        mBorderPaint.setDither(true);
        mBorderPaint.setFilterBitmap(true);
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setStrokeWidth(0);
        initAnimation();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);


        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    private float mOffsetX;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        createShader();
        int i = UIUtils.dip2px(0);
        rect = new Rect(i, i, getWidth() - i, getHeight() - i);

    }

    public void setOffsetX(float offsetX) {
        this.mOffsetX = offsetX;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (window == null)
            window = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);


        mAnimatorSet.start();

//        ObjectAnimator waterLevelAnim1 = ObjectAnimator.ofFloat(
//                this, "offsetX", -getHeight() / 2, getHeight() / 2);
//        waterLevelAnim1.setDuration(600000);
//        waterLevelAnim1.start();
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mAnimatorSet.cancel();
    }

    private void createShader() {

        mDefaultAngularFrequency = 2.0f * Math.PI / DEFAULT_WAVE_LENGTH_RATIO / getWidth();
        mDefaultAmplitude = getHeight() * DEFAULT_AMPLITUDE_RATIO;
        mDefaultWaterLevel = getHeight() * DEFAULT_WATER_LEVEL_RATIO;
        mDefaultWaveLength = getHeight();


        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Paint wavePaint = new Paint();
        wavePaint.setAntiAlias(true);

        final int endX = getWidth() + 1;
        final int endY = getHeight() + 1;

        float[] waveY = new float[endX];

        wavePaint.setColor(mProgressColor);
        for (int beginX = 0; beginX < endX; beginX++) {
            double wx = beginX * mDefaultAngularFrequency;
            float beginY = (float) (mDefaultWaterLevel + mDefaultAmplitude * Math.sin(wx));
            canvas.drawLine(beginX, beginY, beginX, endY, wavePaint);

            waveY[beginX] = beginY;
        }

        wavePaint.setColor(mFrontProgressColor);
        final int wave2Shift = (int) (mDefaultWaveLength / 4);
        for (int beginX = 0; beginX < endX; beginX++) {
            canvas.drawLine(beginX, waveY[(beginX + wave2Shift) % endX], beginX, endY, wavePaint);
        }

        mWaveShader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);
        mViewPaint.setShader(mWaveShader);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int rotation = window.getDefaultDisplay().getRotation();
        if (mBitmap == null) {
            mBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_4444);
            bitmapCanvas = new Canvas(mBitmap);
        }

        if(rect2 == null){
            rect2 = new Rect(0,0,mSrc.getWidth(),mSrc.getHeight());
            int left = (getWidth()- mSrc.getWidth()/3*2)/2;
            int top = (getHeight()-mSrc.getHeight()/3*2)/2;
            rect = new Rect(left-UIUtils.dip2px(4),top-UIUtils.dip2px(1) ,left + rect2.width()/3*2,top+rect2.height()/3*2);
            Logger.d("MRZ","src:"+" width:"+ rect2.width()+" height:"+rect2.height());
            Logger.d("MRZ","screen:"+" width"+rect.width()+" height:"+rect.height());
            Logger.d("MRZ","dp:"+UIUtils.px2dip(12));
        }
        bitmapCanvas.drawBitmap(mSrc, rect2, rect, mImagePaint);


        if (mWaveShader != null) {
            if (mViewPaint.getShader() == null) {
                mViewPaint.setShader(mWaveShader);
            }

            mShaderMatrix.setScale(
                    mWaveLengthRatio / DEFAULT_WAVE_LENGTH_RATIO,
                    mAmplitudeRatio / DEFAULT_AMPLITUDE_RATIO,
                    0,
                    mDefaultWaterLevel);

            mShaderMatrix.postTranslate(
                    mWaveShiftRatio * getWidth(),
                    (DEFAULT_WATER_LEVEL_RATIO - mWaterLevelRatio) * getHeight());
            mShaderMatrix.postRotate(rotation, getWidth() / 2, getHeight() / 2);
            mWaveShader.setLocalMatrix(mShaderMatrix);
            float borderWidth = 10;


            if (borderWidth > 0) {
                bitmapCanvas.drawRect(
                        borderWidth / 2f,
                        borderWidth / 2f,
                        getWidth() - borderWidth / 2f - 0.5f,
                        getHeight() - borderWidth / 2f - 0.5f,
                        mBorderPaint);
            }


            bitmapCanvas.drawRect(borderWidth, borderWidth, getWidth()- borderWidth,
                    getHeight() , mViewPaint);


        } else {
            mViewPaint.setShader(null);
        }

        canvas.drawBitmap(mBitmap, 0, 0, null);

    }


    private Bitmap drawableToBitamp(Drawable drawable) {
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap.Config config =
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }


    private void initAnimation() {
        List<Animator> animators = new ArrayList<>();

        ObjectAnimator waveShiftAnim = ObjectAnimator.ofFloat(
                this, "waveShiftRatio", 0f, 1f);
        waveShiftAnim.setRepeatCount(ValueAnimator.INFINITE);
        waveShiftAnim.setDuration(1000);
        waveShiftAnim.setInterpolator(new LinearInterpolator());
        animators.add(waveShiftAnim);

//        ObjectAnimator waterLevelAnim = ObjectAnimator.ofFloat(
//                this, "waterLevelRatio", 0.4f, 4f);
//        waterLevelAnim.setDuration(500000);
//        waterLevelAnim.setInterpolator(new DecelerateInterpolator());
//        animators.add(waterLevelAnim);


        ObjectAnimator amplitudeAnim = ObjectAnimator.ofFloat(
                this, "amplitudeRatio", 0.02f, 0.05f);
        amplitudeAnim.setRepeatCount(ValueAnimator.INFINITE);
        amplitudeAnim.setRepeatMode(ValueAnimator.REVERSE);
        amplitudeAnim.setDuration(5000);
        amplitudeAnim.setInterpolator(new LinearInterpolator());
        animators.add(amplitudeAnim);

        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.playTogether(animators);
    }

    public void setWaveShiftRatio(float mWaveShiftRatio) {
        this.mWaveShiftRatio = mWaveShiftRatio;
        postInvalidate();
    }

    public void setWaterLevelRatio(float mWaterLevelRatio) {
        this.mWaterLevelRatio = mWaterLevelRatio % 1;
        postInvalidate();
    }

    public void setAmplitudeRatio(float mAmplitudeRatio) {
        this.mAmplitudeRatio = mAmplitudeRatio;
        postInvalidate();
    }
}