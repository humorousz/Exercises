package com.example.zhangzhiquan.myapplication.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;


import com.example.zhangzhiquan.myapplication.R;
import com.example.zhangzhiquan.myapplication.Util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Zhangzhiquan on 2017/2/14.
 */

public class AntView extends View {

    Context mContext;
    int defaultSize;
    int arcDistance;
    int defaultPadding;
    Paint mMiddleArcPaint; //外层圆画笔
    Paint mInnerArcpaint;  //内层圆画笔
    Paint mTextPaint;      //正中间字体画笔
    Paint mCalibrationPaint; //圆环大刻度画笔
    Paint mSmallCalibrationPaint;//圆环小刻度画笔
    Paint mCalibrationTextPaint; //圆环刻度文本画笔
    Paint mArcProgressPaint; //外层进度画笔
    Paint mBitmapPaint; //外层圆环上小圆点Bitmap画笔
    Bitmap bitmap;
    float[] pos;
    float[] tan;
    Matrix matrix;

    int width;
    int height;
    int radius;

    RectF mMiddleRect; //外层圆环矩形
    RectF mInnerRect ; //内层圆环矩形
    RectF mMiddleProgressRect; //外层进度矩形

    float mStartAngle;
    float mEndAngle;

    int mMinNum;
    int mMaxNum;
    String sesameLevel;
    String evaluationTime;
    float mCurrentAngle;
    float mTotalAngle;

    final String[] sesameStr = {"350","较差","550","中等","600","良好","650","优秀","700","极好","950"};

    public AntView(Context context) {
        this(context,null);
    }

    public AntView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AntView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init(){
        //View的默认大小
        defaultSize = dp2px(250);
        arcDistance = dp2px(14);
        defaultPadding = dp2px(5);

        //外层圆画笔
        mMiddleArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mMiddleArcPaint.setStrokeWidth(8);
        mMiddleArcPaint.setColor(Color.WHITE);
        mMiddleArcPaint.setStyle(Paint.Style.STROKE);
        mMiddleArcPaint.setAlpha(80);

        //内层圆画笔
        mInnerArcpaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mInnerArcpaint.setStrokeWidth(30);
        mInnerArcpaint.setColor(Color.WHITE);
        mInnerArcpaint.setAlpha(80);
        mInnerArcpaint.setStyle(Paint.Style.STROKE);

        //正中间字体画笔
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.BLUE);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        //圆环大刻度画笔
        mCalibrationPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCalibrationPaint.setStrokeWidth(4);
        mCalibrationPaint.setStyle(Paint.Style.STROKE);
        mCalibrationPaint.setColor(Color.WHITE);
        mCalibrationPaint.setAlpha(120);

        //圆环小刻度画笔
        mSmallCalibrationPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSmallCalibrationPaint.setStrokeWidth(1);
        mSmallCalibrationPaint.setStyle(Paint.Style.STROKE);
        mSmallCalibrationPaint.setColor(Color.WHITE);
        mSmallCalibrationPaint.setAlpha(130);

        //圆环刻度文本画笔
        mCalibrationTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCalibrationTextPaint.setTextSize(30);
        mCalibrationTextPaint.setColor(Color.BLACK);

        //外层进度画笔
        mArcProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mArcProgressPaint.setStrokeWidth(8);
        mArcProgressPaint.setColor(Color.BLUE);
        mArcProgressPaint.setStyle(Paint.Style.STROKE);
        mArcProgressPaint.setStrokeCap(Paint.Cap.ROUND);

        //外层圆环上的小圆点Bitmap画笔
        mBitmapPaint = new Paint();
        mBitmapPaint.setStyle(Paint.Style.FILL);
        mBitmapPaint.setAntiAlias(true);

        //初始化小圆点
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_circle);

        //当前点的实际位置
        pos = new float[2];
        matrix = new Matrix();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(resolveMeasure(widthMeasureSpec,defaultSize)
                ,resolveMeasure(heightMeasureSpec,defaultSize));
    }

    //根据传入的值进行测量
    public int resolveMeasure(int measureSpec,int defaultSize){
        int result = 0;
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (MeasureSpec.getMode(measureSpec)){
            case MeasureSpec.UNSPECIFIED:
                result = defaultSize;
                break;
            case MeasureSpec.AT_MOST:
                result = Math.min(specSize,defaultSize);
                break;
            case MeasureSpec.EXACTLY:
                result = defaultSize;
                break;
        }

        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCalibratioinAndText(canvas);
        drawCententText(canvas);
        drawInnerArc(canvas);
        drawMiddleArc(canvas);
        drawRingProgress(canvas);
        drawSmallCalibration(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        radius = width / 2;

        //外层圆环矩形
        mMiddleRect = new RectF(defaultPadding,defaultPadding,width - defaultPadding,height-defaultPadding);
        //内层圆环矩形
        mInnerRect = new RectF(defaultPadding+arcDistance,defaultPadding+arcDistance,width-defaultPadding-arcDistance,height-defaultPadding-arcDistance);
        //外层进度矩形
        mMiddleProgressRect = new RectF(defaultPadding,defaultPadding,width - defaultPadding,height-defaultPadding);
    }

    private void drawMiddleArc(Canvas canvas){
        canvas.drawArc(mMiddleRect,mStartAngle,mEndAngle,false,mMiddleArcPaint);
    }

    private void drawInnerArc(Canvas canvas){
        canvas.drawArc(mInnerRect,mStartAngle,mEndAngle,false,mInnerArcpaint);
    }

    private void drawSmallCalibration(Canvas canvas){
        canvas.save();
        canvas.rotate(-105,radius,radius);
        int startDst = (int) (defaultPadding+arcDistance - mInnerArcpaint.getStrokeWidth()/2);
        int endDst = (int) (startDst+mInnerArcpaint.getStrokeWidth());
        for(int i=0;i<=35;i++){
            canvas.drawLine(radius,startDst,radius,endDst,mSmallCalibrationPaint);
            canvas.rotate(6,radius,radius);
        }
        canvas.restore();
    }

    private void drawCalibratioinAndText(Canvas canvas){
        canvas.save();
        canvas.rotate(-105,radius,radius);
        int startDst = (int) (defaultPadding+arcDistance - mInnerArcpaint.getStrokeWidth()/2 -1);
        int endDst = (int) (startDst + mInnerArcpaint.getStrokeWidth());
        int rotateAngle = 210 / 10 ;
        for(int i=1 ; i<12 ; i++){
            if(i%2 != 0){
                canvas.drawLine(radius,startDst,radius,endDst,mCalibrationPaint);
            }
            float textLen = mCalibrationTextPaint.measureText(sesameStr[i-1]);
            canvas.drawText(sesameStr[i-1],radius-textLen/2,endDst + 40,mCalibrationTextPaint);
            canvas.rotate(rotateAngle,radius,radius);
        }

        canvas.restore();
    }

    private void drawCententText(Canvas canvas){
        mTextPaint.setTextSize(30);
        canvas.drawText("BETA",radius,radius-130,mTextPaint);

        mTextPaint.setTextSize(200);
        mTextPaint.setStyle(Paint.Style.STROKE);
        canvas.drawText(String.valueOf(mMinNum),radius,radius+70,mTextPaint);

        mTextPaint.setTextSize(80);
        canvas.drawText(sesameLevel,radius,radius+160,mTextPaint);

        mTextPaint.setTextSize(30);
        canvas.drawText(evaluationTime,radius,radius+205,mTextPaint);
    }

    private void drawRingProgress(Canvas canvas){

        Path path = new Path();
        path.addArc(mMiddleProgressRect, mStartAngle, mCurrentAngle);
        PathMeasure pathMeasure = new PathMeasure(path, false);
        pathMeasure.getPosTan(pathMeasure.getLength() * 1, pos, tan);
        matrix.reset();
        matrix.postTranslate(pos[0] - bitmap.getWidth() / 2, pos[1] - bitmap.getHeight() / 2);
        canvas.drawPath(path, mArcProgressPaint);
        //起始角度不为0时候才进行绘制小圆点
        if (mCurrentAngle == 0)
            return;
        canvas.drawBitmap(bitmap, matrix, mBitmapPaint);
        mBitmapPaint.setColor(Color.WHITE);
//        canvas.drawCircle(pos[0], pos[1], 8, mBitmapPaint);
    }

    public void startAnim(){
        ValueAnimator mAngleAnim = ValueAnimator.ofFloat(mCurrentAngle,mTotalAngle);
        mAngleAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        mAngleAnim.setDuration(3000);
        mAngleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurrentAngle = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });

        mAngleAnim.start();

        ValueAnimator mNumAnim = ValueAnimator.ofInt(mMinNum,mMaxNum);
        mNumAnim.setDuration(3000);
        mNumAnim.setInterpolator(new LinearInterpolator());
        mNumAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mMinNum = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });

        mNumAnim.start();
    }

    public void setSesameValues(int values){
        if (values <= 350){
            mMaxNum = values;
            mTotalAngle = 0f;
            sesameLevel = "信用较差";
            evaluationTime = "评估时间:" + getCurrentTime();
        } else if (values <= 550){
            mMaxNum = values;
            mTotalAngle = (values - 350) * 80 / 400f + 2;
            sesameLevel = "信用较差";
            evaluationTime = "评估时间:" + getCurrentTime();
        } else if (values <= 700)
        {
            mMaxNum = values;
            if (values > 550 && values <= 600){
                sesameLevel = "信用中等";
            } else if (values > 600 && values <= 650){
                sesameLevel = "信用良好";
            } else {
                sesameLevel = "信用优秀";
            }
            mTotalAngle = (values - 550) * 120 / 150f + 43;
            evaluationTime = "评估时间:" + getCurrentTime();
        } else if (values <= 950){
            mMaxNum = values;
            mTotalAngle = (values - 700) * 40 / 250f + 170;
            sesameLevel = "信用极好";
            evaluationTime = "评估时间:" + getCurrentTime();
        } else{
            mTotalAngle = 240f;
        }

        mStartAngle=-195f;
        mEndAngle =210f;
        startAnim();
    }

    private String getCurrentTime(){
        Date curDate = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        return new String(format.format(curDate));
    }


    private int dp2px(int dp){
        if(dp <0)
            dp=0;
        return Util.dip2px(mContext,dp);
    }


}
