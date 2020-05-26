package se.emilsjolander.flipview;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ClipView extends FrameLayout {
  private Rect mTopRect = new Rect();
  private Camera mCamera = new Camera();
  private Matrix mMatrix = new Matrix();

  public ClipView(@NonNull Context context) {
    super(context);
  }

  public ClipView(@NonNull Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public ClipView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    super.onLayout(changed, left, top, right, bottom);
    mTopRect.top = 0;
    mTopRect.left = 0;
    mTopRect.right = getWidth();
    mTopRect.bottom = getHeight() / 2;
  }

  @Override
  protected void dispatchDraw(Canvas canvas) {
    canvas.save();
    mCamera.save();
    canvas.clipRect(mTopRect);
    mCamera.rotateX(-70);
    mCamera.getMatrix(mMatrix);
    positionMatrix();
    canvas.concat(mMatrix);
    drawChild(canvas, getChildAt(0), 0);
    mCamera.restore();
    canvas.restore();
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
  }

  private void positionMatrix() {
    mMatrix.preScale(0.25f, 0.25f);
    mMatrix.postScale(4.0f, 4.0f);
    mMatrix.preTranslate(-getWidth() / 2, -getHeight() / 2);
    mMatrix.postTranslate(getWidth() / 2, getHeight() / 2);
  }
}
