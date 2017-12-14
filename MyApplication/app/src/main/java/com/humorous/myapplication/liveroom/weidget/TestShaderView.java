package com.humorous.myapplication.liveroom.weidget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.humorous.myapplication.R;

/**
 * Created by zhangzhiquan on 2017/12/14.
 * @author zhangzhiquan
 */

public class TestShaderView extends View {

    public TestShaderView(Context context) {
        this(context,null);
    }

    public TestShaderView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public TestShaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint mPaint = new Paint();
        Shader shader = new LinearGradient(0, 0, 200, 200,
                new int[] { Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT, Color.WHITE },
                new float[] { 0, 0.1F, 0.5F, 0.7F, 0.8F }, Shader.TileMode.REPEAT);
        // 设置shader
        mPaint.setShader(shader);
        canvas.drawRect(0, 0, 400, 400, mPaint);
    }
}
