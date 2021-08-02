package com.humorusz.practice.chart;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class DrawPieCircleView extends View {
  public static final int PINOTSELECTED = -1;
  private static final int ANIMATIONTIME = 300;
  private static final float CIRCLETOTALDEGREE = 360.0f;

  private static final float COMMONPIARCSPACE = 2;//普通饼图偏移量
  private static final float PUSHOUTPIARCSPACE = 15;//点击状态饼图偏移量

  private static final Point point = new Point(300, 300);//圆心
  private static final float circleR = 200;//圆半径
  String[] mColors = {"#FF1EBC71", "#FFF55253", "#FFDAA520"
      , "#FF90EE90", "#FB4E09", "#AA49D2CC", "#00A0E9"};
  ValueAnimator mValueAnimator;//点击动画
  float mAnimatedValue = 0f;//动画瞬时值
  float mTotalAnimateValue = 0f;//动画总值
  Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
  private int piSelectedPosition = PINOTSELECTED;//当前点击的饼图
  private int prePiSelectedPosition = PINOTSELECTED;//上一个点击的饼图
  private boolean hasPieNeedPushOutShow = false;//是否有点击状态的饼图需要展示

  private List<Double> percentValueList = Arrays.asList(12.9, 15.0, 18.0, 16.0, 38.1);//饼图所占百分比

  public DrawPieCircleView(Context context) {
    super(context);
  }

  public DrawPieCircleView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public DrawPieCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public void setPercentValueList(List<Double> percentValueList) {
    this.percentValueList = percentValueList;
    postInvalidate();
  }

  @Override
  protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    super.onLayout(changed, left, top, right, bottom);
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    if (event.getAction() == MotionEvent.ACTION_DOWN) {
      int nowSelectedPi = caculateShowPie(event);
      if (nowSelectedPi == PINOTSELECTED) return super.onTouchEvent(event);
      if (hasPieNeedPushOutShow) {
        if (piSelectedPosition == nowSelectedPi) {
          hasPieNeedPushOutShow = false;
          prePiSelectedPosition = piSelectedPosition;
          piSelectedPosition = PINOTSELECTED;
        } else {
          prePiSelectedPosition = piSelectedPosition;
          piSelectedPosition = nowSelectedPi;
        }
      } else {
        hasPieNeedPushOutShow = true;
        prePiSelectedPosition = PINOTSELECTED;
        piSelectedPosition = nowSelectedPi;
      }
      startAnim();
      return true;
    }

    return super.onTouchEvent(event);
  }

  /**
   * 计算点击位置
   *
   * @param event
   * @return
   */
  private int caculateShowPie(MotionEvent event) {
    float pointX = event.getX();
    float pointY = event.getY();

    float deltaX = pointX - point.x;
    float deltaY = pointY - point.y;

    double deltaXPow = Math.pow(BigDecimal.valueOf(deltaX).doubleValue(), 2);
    double deltaYPow = Math.pow(BigDecimal.valueOf(deltaY).doubleValue(), 2);
    double rPow = deltaXPow + deltaYPow;

    if (rPow < Math.pow(COMMONPIARCSPACE, 2) || rPow > Math.pow(circleR + COMMONPIARCSPACE, 2)) {
      return PINOTSELECTED;
    }

    double sinA = deltaY / Math.sqrt(rPow);
    double positionDegree = Math.toDegrees(Math.asin(sinA));
    double scrennDegree = turnDegreeToScreenNormal(deltaX, deltaY, positionDegree);
    float degree = 0;
    double startDegree = 0;

    for (int i = 0; i < percentValueList.size(); i++) {
      Double percent = percentValueList.get(i);
      degree = (float) (percent * CIRCLETOTALDEGREE / 100);

      if (scrennDegree > startDegree && scrennDegree < startDegree + degree) {
        return i;
      }
      startDegree += degree;
    }
    return PINOTSELECTED;
  }

  /**
   * 由于是采用的顺时针绘制饼图，所以需要将角度调整
   * 计算角度所属象限，返回正确角度
   *
   * @param deltaX
   * @param deltaY
   * @param degree
   * @return
   */
  private double turnDegreeToScreenNormal(float deltaX, float deltaY, double degree) {
    if (deltaX > 0 && deltaY < 0) {
      //第一象限
      return 360 + degree;
    } else if (deltaX < 0 && deltaY < 0) {
      //第二象限
      return 180 - degree;
    } else if (deltaX < 0 && deltaY > 0) {
      //第三象限
      return 90 + degree;
    } else {
      return degree;
    }
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    float degree = 0;
    double startDegree = 0;
    double totalDegre = CIRCLETOTALDEGREE;

    for (int i = 0; i < percentValueList.size(); i++) {
      Double percent = percentValueList.get(i);
      degree = (float) (percent * totalDegre / 100);
      float animatDistance = 0;
      if (i != prePiSelectedPosition && i != piSelectedPosition) {
        animatDistance = COMMONPIARCSPACE;
      } else {
        if (i == prePiSelectedPosition) {
          animatDistance = (mTotalAnimateValue - mAnimatedValue) / mTotalAnimateValue * (PUSHOUTPIARCSPACE - COMMONPIARCSPACE) + COMMONPIARCSPACE;
        } else {
          animatDistance = mAnimatedValue / mTotalAnimateValue * (PUSHOUTPIARCSPACE - COMMONPIARCSPACE) + COMMONPIARCSPACE;
        }
      }

      commonDrawPi(canvas, startDegree, degree, mColors[i], animatDistance);
      startDegree += degree;
    }

    if (mAnimatedValue == 1f) {
      if (hasPieNeedPushOutShow) {
        prePiSelectedPosition = PINOTSELECTED;
      } else {
        prePiSelectedPosition = PINOTSELECTED;
        piSelectedPosition = PINOTSELECTED;
      }
    }
  }

  /**
   * 绘制每个百分比饼图
   *
   * @param canvas
   * @param startDegree    圆弧开始的角度
   * @param degree         圆弧划过的角度
   * @param color          饼图颜色
   * @param drawDeltaValue 饼图与基准圆心的偏移量
   */
  private void commonDrawPi(Canvas canvas, double startDegree, float degree, String color, float drawDeltaValue) {
    double averageDegree = startDegree + degree / 2;
    double deltaX = Math.cos(Math.toRadians(averageDegree));
    double deltaY = Math.sin(Math.toRadians(averageDegree));

    BigDecimal bigDeltaX = new BigDecimal(String.valueOf(deltaX)).setScale(16, BigDecimal.ROUND_HALF_UP);
    BigDecimal bigDeltaY = new BigDecimal(String.valueOf(deltaY)).setScale(16, BigDecimal.ROUND_HALF_UP);

    float changeX = bigDeltaX.floatValue() * drawDeltaValue;
    float changeY = bigDeltaY.floatValue() * drawDeltaValue;

    RectF rectF = new RectF(point.x - circleR + changeX
        , point.y - circleR + changeY
        , point.x + circleR + changeX
        , point.y + circleR + changeY);
    paint.setColor(Color.parseColor(color));
    canvas.drawArc(rectF, BigDecimal.valueOf(startDegree).floatValue(), degree, true, paint);
  }

  public void startAnim() {
    stopAnim();
    startAnimation(0f, 1f, ANIMATIONTIME);
  }

  public void stopAnim() {
    if (mValueAnimator != null) {
      clearAnimation();
      mAnimatedValue = 0f;
      mValueAnimator.end();
    }
  }

  private ValueAnimator startAnimation(float startF, final float endF, long time) {
    mValueAnimator = ValueAnimator.ofFloat(startF, endF);
    mTotalAnimateValue = endF - startF;
    mValueAnimator.setDuration(time);
    mValueAnimator.setInterpolator(new LinearInterpolator());
    mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator valueAnimator) {
        mAnimatedValue = (float) valueAnimator.getAnimatedValue();
        postInvalidate();
      }
    });

    if (!mValueAnimator.isRunning()) {
      mValueAnimator.start();

    }
    return mValueAnimator;
  }
}