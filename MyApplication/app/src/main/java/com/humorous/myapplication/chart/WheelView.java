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
import android.view.MotionEvent;
import android.view.View;

import com.humorous.myapplication.R;
import com.humorousz.commonutils.util.CollectionUtils;

import java.util.List;

public class WheelView extends View {
  public static final float CIRCLE_TOTAL_DEGREE = 360.0f;
  public static final float CIRCLE_ROTATE_DEGREE = 90.0f;
  /**
   * item的画笔
   */
  private Paint mPiePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
  /**
   * 文字画笔
   */
  private TextPaint mTextPaint;
  private float mRadius;
  /**
   * 屏幕旋转的角度，改变第一个position显示的位置
   */
  private float mViewRotateDegree;
  /**
   * 每个Item所占据的角度
   */
  private float mItemDegree;
  private int mTextWidth;
  private int mTextPaddingTop;
  private int mTextSize;
  private List<WheelData> mDataList;
  private OnWheelDataClickListener mClickListener;

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

  public void setOnWheelDataClickListener(OnWheelDataClickListener listener) {
    mClickListener = listener;
  }

  /**
   * 更新指定位置的数据
   *
   * @param position 位置
   * @param data     新的元素
   */
  public void updateData(int position, WheelData data) {
    if (CollectionUtils.isEmpty(mDataList) || position > mDataList.size()) {
      return;
    }
    mDataList.set(position, data);
    invalidate();
  }

  public List<WheelData> getDataList() {
    return mDataList;
  }

  public void setDataList(List<WheelData> list) {
    if (list == null || list.size() == 0) {
      return;
    }
    mDataList = list;
    mItemDegree = CIRCLE_TOTAL_DEGREE / mDataList.size();
    mViewRotateDegree = -(CIRCLE_ROTATE_DEGREE + mItemDegree / 2);
    invalidate();
  }

  public float getItemDegree() {
    return mItemDegree;
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    mRadius = Math.min(getMeasuredHeight(), getMeasuredWidth()) / 2.0f;
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    if (event.getAction() == MotionEvent.ACTION_DOWN) {
      int position = getClickItemPosition(event);
      if (mDataList.size() > position && mClickListener != null) {
        mClickListener.onWheelDataClick(position, mDataList.get(position));
      }
    }
    return super.onTouchEvent(event);
  }

  /**
   * 获取点击的Item位置
   *
   * @param event MotionEvent
   * @return 点击的position
   */
  private int getClickItemPosition(MotionEvent event) {
    float pointX = event.getX();
    float pointY = event.getY();
    float deltaX = pointX - mRadius;
    float deltaY = pointY - mRadius;
    double sinA = deltaY / Math.sqrt(Math.pow(Math.abs(deltaX), 2) + Math.pow(Math.abs(deltaY), 2));
    double positionDegree = Math.toDegrees(Math.asin(sinA));
    double contrastDegree = mViewRotateDegree < 0 ? mViewRotateDegree + CIRCLE_TOTAL_DEGREE : mViewRotateDegree;
    double realDegree = turnToContrasDegree(deltaX, deltaY, contrastDegree, positionDegree);
    return (int) (realDegree / mItemDegree);
  }

  /**
   * 计算基于contrastDegree角度
   *
   * @param deltaX         x坐标
   * @param deltaY         y坐标
   * @param contrastDegree 实际需要参考的
   * @param degree         点击角度
   * @return 基于contrastDegree的实际角度
   */
  private double turnToContrasDegree(float deltaX, float deltaY, double contrastDegree, double degree) {
    double tmpDegree;
    if (deltaX > 0 && deltaY < 0) {
      //第一象限
      tmpDegree = CIRCLE_TOTAL_DEGREE - Math.abs(degree);
    } else if (deltaX < 0 && deltaY < 0) {
      //第二象限
      tmpDegree = CIRCLE_TOTAL_DEGREE / 2 + Math.abs(degree);
    } else if (deltaX < 0 && deltaY > 0) {
      //第三象限
      tmpDegree = CIRCLE_TOTAL_DEGREE / 4 + (CIRCLE_TOTAL_DEGREE / 4 - degree);
    } else {
      tmpDegree = degree;
    }
    return tmpDegree > contrastDegree ? tmpDegree - contrastDegree : CIRCLE_TOTAL_DEGREE - (contrastDegree - tmpDegree);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    if (CollectionUtils.isEmpty(mDataList)) {
      return;
    }
    float startDegree = 0;
    canvas.rotate(mViewRotateDegree, mRadius, mRadius);
    for (WheelData pieChartData : mDataList) {
      drawPart(canvas, startDegree, mItemDegree, pieChartData.mBackgroundColor, pieChartData.mTextColor, pieChartData.mText);
      startDegree += mItemDegree;
    }
  }

  /**
   * 绘制每个Item的扇形
   *
   * @param canvas          绘制画布
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
