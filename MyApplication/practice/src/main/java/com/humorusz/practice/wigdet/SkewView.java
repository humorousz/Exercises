package com.humorusz.practice.wigdet;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SkewView extends View {
  Paint paint;
  Matrix matrix = new Matrix();
  Rect mRect = new Rect();

  public SkewView(@NonNull Context context) {
    super(context);
  }

  public SkewView(@NonNull Context context,
      @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public SkewView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    super.onLayout(changed, left, top, right, bottom);
    mRect.top = 0;
    mRect.left = 0;
    mRect.right = getWidth();
    mRect.bottom = getHeight();
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    paint = new Paint();
    matrix.setSkew(0,1f,1, 1);
    canvas.setMatrix(matrix);
    paint.setStrokeWidth(20);
    paint.setStyle(Paint.Style.FILL);
    paint.setColor(Color.RED);
    canvas.drawRect(mRect, paint);
  }
}
