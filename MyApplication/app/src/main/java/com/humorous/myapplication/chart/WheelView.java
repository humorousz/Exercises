package com.humorous.myapplication.chart;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.humorous.myapplication.R;
import com.humorousz.commonutils.util.CollectionsUtil;

import java.util.List;

public class WheelView extends View {
  private static final float CIRCLE_TOTAL_DEGREE = 360.0f;
  private static final float CIRCLE_ROTATE_DEGREE = 90.0f;
  private Paint mPiePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
  private TextPaint mTextPaint;
  private float mRadius;
  private List<WheelData> mDataList;
  private int mTextWidth;
  private int mTextPaddingTop;
  private int mTextSize;

  public WheelView(Context context) {
    this(context, null);
  }

  public WheelView(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, -1);
  }

  public WheelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initAttrs(attrs, defStyleAttr);
    mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
    mTextPaint.setTextSize(mTextSize);
    mTextPaint.setColor(Color.BLACK);
  }

  private void initAttrs(AttributeSet attrs, int defStyleAttr) {
    if (attrs == null) {
      return;
    }
    TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.WheelView, defStyleAttr, 0);
    mTextWidth = a.getDimensionPixelOffset(R.styleable.WheelView_wheelTextWidth, 0);
    mTextPaddingTop = a.getDimensionPixelOffset(R.styleable.WheelView_wheelTextPaddingTop, 0);
    mTextSize = a.getDimensionPixelOffset(R.styleable.WheelView_wheelTextSize, 0);
    a.recycle();
  }

  public void setDataList(List<WheelData> list) {
    if (list == null || list.size() == 0) {
      return;
    }
    mDataList = list;
    invalidate();
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    mRadius = Math.min(getMeasuredHeight(), getMeasuredWidth()) / 2.0f;
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    if (CollectionsUtil.isEmpty(mDataList)) {
      return;
    }
    float startDegree = 0;
    float degree = CIRCLE_TOTAL_DEGREE / mDataList.size();
    canvas.rotate(-(CIRCLE_ROTATE_DEGREE + degree / 2), mRadius, mRadius);
    for (WheelData pieChartData : mDataList) {
      drawPart(canvas, startDegree, degree, pieChartData.mBackgroundColor, pieChartData.mTextColor, pieChartData.mText);
      startDegree += degree;
    }
  }

  /**
   * 绘制每个百分比饼图
   *
   * @param canvas
   * @param startDegree     圆弧开始的角度
   * @param degree          圆弧划过的角度
   * @param backgroundColor 饼图颜色
   * @param drawText        绘制的文字
   */
  private void drawPart(Canvas canvas, float startDegree, float degree, int backgroundColor, int textColor, CharSequence drawText) {
    RectF rectF = new RectF(0, 0, mRadius * 2, mRadius * 2);
    mPiePaint.setColor(backgroundColor);
    canvas.drawArc(rectF, startDegree, degree, true, mPiePaint);
    canvas.save();
    canvas.rotate(startDegree + degree / 2 + CIRCLE_ROTATE_DEGREE, mRadius, mRadius);
    mTextPaint.setColor(textColor);
    StaticLayout staticLayout = new StaticLayout(drawText, mTextPaint, mTextWidth, Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
    float translateX = (mRadius * 2 - mTextWidth) / 2;
    float translateY = mTextPaddingTop;
    canvas.translate(translateX, translateY);
    staticLayout.draw(canvas);
    canvas.restore();
  }
}
