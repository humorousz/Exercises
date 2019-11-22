package com.humorous.myapplication.shader.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import androidx.annotation.IntDef;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.humorous.myapplication.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * Created by zhangzhiquan on 2017/12/14.
 *
 * @author zhangzhiquan
 */

public class MaskLoadingView extends View {
    private static final String TAG = "MaskLoadingView";
    private Paint mPaint;
    private Shader mShader;
    private ValueAnimator mTranslateAnimator;
    private Matrix mMatrix;
    private RectF mRectF;
    private float mTranslate = 0;
    private int[] mColors;
    private float[] mPositions;
    private long mDuration;
    private int repeatMode;
    private int repeatCount;
    private int animMode;
    private boolean isNullPosition = false;
    private MaskLoadingCallBack mListener;

    public static final int LEFT_TO_RIGHT = 1;
    public static final int RIGHT_TO_LEFT = 2;

    public static final int RECTANGLE = 1;
    public static final int CIRCLE = 2;

    /**
     * @hide
     */
    @IntDef({LEFT_TO_RIGHT, RIGHT_TO_LEFT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface AnimMode {}

    /**
     * @hide
     */
    @IntDef({RECTANGLE,CIRCLE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Shape{}

    public MaskLoadingView(Context context) {
        this(context, null);
    }

    public MaskLoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public MaskLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mMatrix = new Matrix();
    }


    private void initAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MaskLoadingView, defStyleAttr, 0);
        repeatCount = a.getInt(R.styleable.MaskLoadingView_repeatCount, -1);
        animMode = a.getInt(R.styleable.MaskLoadingView_animMode, LEFT_TO_RIGHT);
        mDuration = a.getInt(R.styleable.MaskLoadingView_duration, 2000);
        repeatMode = a.getInt(R.styleable.MaskLoadingView_repeatMode, 1);
        repeatCount = repeatCount < -1 ? -1 : repeatCount;
        repeatCount = repeatCount > 1 ? repeatCount - 1 : repeatCount;
        a.recycle();
    }

    private void init() {
        if (animMode == LEFT_TO_RIGHT) {
            mTranslateAnimator = ValueAnimator.ofFloat(-getWidth(), getWidth());
        } else if (animMode == RIGHT_TO_LEFT) {
            mTranslateAnimator = ValueAnimator.ofFloat(getWidth(), -getWidth());
        }
        mTranslateAnimator.addUpdateListener((animation) -> {
            float v = (float) animation.getAnimatedValue();
            mTranslate = v;
            invalidate();
        });
        mTranslateAnimator.setDuration(mDuration);
        mTranslateAnimator.setRepeatMode(repeatMode);
        mTranslateAnimator.setRepeatCount(repeatCount);
        mTranslateAnimator.start();
    }


    /**
     * 设置渐变颜色
     *
     * @param colors
     */
    public void setColors(int[] colors) {
        mColors = colors;
    }

    /**
     * 设置渐变颜色位置
     *
     * @param positions
     */
    public void setPositions(float[] positions) {
        mPositions = positions;
    }

    public void setNullPosition(boolean flag) {
        isNullPosition = flag;
    }

    public void setCallbackListener(MaskLoadingCallBack listener) {
        mListener = listener;
    }

    public void setAnimMode(@AnimMode int mode ){
        animMode = mode;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        post(() -> init());
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mTranslateAnimator != null && mTranslateAnimator.isRunning()) {
            mTranslateAnimator.cancel();
        }
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 设置shader
        if (mShader == null) {
            if (mListener == null || (mShader = mListener.getShader(canvas)) == null) {
                if (mColors == null) {
                    mColors = new int[]{Color.TRANSPARENT, Color.parseColor("#02000000"), Color.parseColor("#05000000"), Color.parseColor("#02000000"), Color.TRANSPARENT};
                }
                if (mPositions == null && !isNullPosition) {
                    mPositions = new float[]{0.3f, 0.4f, 0.5f, 0.6f, 0.7f};
                }
                mShader = new LinearGradient(0, 0, getWidth(), getHeight(), mColors, mPositions, Shader.TileMode.CLAMP);
            }
            mRectF = new RectF(0, 0, getWidth(), canvas.getHeight());
        }
        mMatrix.setTranslate(mTranslate,0);
        mShader.setLocalMatrix(mMatrix);
        mPaint.setShader(mShader);
        canvas.drawRoundRect(mRectF,150,150, mPaint);
    }


    public interface MaskLoadingCallBack {
        /**
         * 获取Shader，如果使用时需要自定义
         *
         * @param canvas 可以通过这个Canvas获取宽高
         * @return
         */
        default Shader getShader(Canvas canvas) {
            return null;
        }
    }


}
