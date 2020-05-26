package se.emilsjolander.flipview;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.VelocityTracker;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.Scroller;

import com.humorousz.uiutils.R;

import se.emilsjolander.flipview.Recycler.Scrap;

public class FlipView extends FrameLayout {
  /**
   * @author emilsjolander
   * <p>
   * Class to hold a view and its corresponding info
   */
  static class Page {
    View v;
    int position;
    int viewType;
    boolean valid;
  }

  // this will be the postion when there is not data
  private static final int INVALID_PAGE_POSITION = -1;
  // "null" flip distance
  private static final int INVALID_FLIP_DISTANCE = -1;

  private static final int MAX_SINGLE_PAGE_FLIP_ANIM_DURATION = 1000;// in ms

  // for normalizing width/height
  private static final int FLIP_DISTANCE_PER_PAGE = 180;
  private static final int MAX_SHADOW_ALPHA = 180;// out of 255
  private static final int MAX_SHADE_ALPHA = 130;// out of 255
  private static final int MAX_SHINE_ALPHA = 100;// out of 255

  // value for no pointer
  private static final int INVALID_POINTER = -1;

  // constant used by the attributes
  private static final int VERTICAL_FLIP = 0;

  // constant used by the attributes
  @SuppressWarnings("unused")
  private static final int HORIZONTAL_FLIP = 1;

  private DataSetObserver dataSetObserver = new DataSetObserver() {

    @Override
    public void onChanged() {
      dataSetChanged();
    }

    @Override
    public void onInvalidated() {
      dataSetInvalidated();
    }

  };

  private Scroller mScroller;
  private final Interpolator flipInterpolator = new DecelerateInterpolator();

  private boolean mIsFlippingVertically = true;
  private boolean mIsFlipping;

  // velocity stuff
  private VelocityTracker mVelocityTracker;
  // views get recycled after they have been pushed out of the active queue
  private Recycler mRecycler = new Recycler();

  private ListAdapter mAdapter;
  private int mPageCount = 0;
  private Page mPreviousPage = new Page();
  private Page mCurrentPage = new Page();
  private Page mNextPage = new Page();

  private float mFlipDistance = INVALID_FLIP_DISTANCE;
  private int mCurrentPageIndex = INVALID_PAGE_POSITION;
  private long mCurrentPageId = 0;

  private OverFlipMode mOverFlipMode;
  private OverFlipper mOverFlipper;

  // clipping rects
  private Rect mTopRect = new Rect();
  private Rect mBottomRect = new Rect();
  private Rect mRightRect = new Rect();
  private Rect mLeftRect = new Rect();

  // used for transforming the canvas
  private Camera mCamera = new Camera();
  private Matrix mMatrix = new Matrix();

  // paints drawn above views when flipping
  private Paint mShadowPaint = new Paint();
  private Paint mShadePaint = new Paint();
  private Paint mShinePaint = new Paint();

  public FlipView(Context context) {
    this(context, null);
  }

  public FlipView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public FlipView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);

    TypedArray a = context.obtainStyledAttributes(attrs,
        R.styleable.FlipView);

    // 0 is vertical, 1 is horizontal
    mIsFlippingVertically = a.getInt(R.styleable.FlipView_orientation,
        VERTICAL_FLIP) == VERTICAL_FLIP;

    setOverFlipMode(OverFlipMode.values()[a.getInt(
        R.styleable.FlipView_overFlipMode, 0)]);

    a.recycle();

    init();
  }

  private void init() {
    final Context context = getContext();
    mScroller = new Scroller(context, flipInterpolator);
    mShadowPaint.setColor(Color.RED);
    mShadowPaint.setStyle(Style.FILL);
    mShadePaint.setColor(Color.BLACK);
    mShadePaint.setStyle(Style.FILL);
    mShinePaint.setColor(Color.WHITE);
    mShinePaint.setStyle(Style.FILL);
  }

  private void dataSetChanged() {
    final int currentPage = mCurrentPageIndex;
    int newPosition = currentPage;

    // if the adapter has stable ids, try to keep the page currently on
    // stable.
    if (mAdapter.hasStableIds() && currentPage != INVALID_PAGE_POSITION) {
      newPosition = getNewPositionOfCurrentPage();
    } else if (currentPage == INVALID_PAGE_POSITION) {
      newPosition = 0;
    }

    // remove all the current views
    recycleActiveViews();
    mRecycler.setViewTypeCount(mAdapter.getViewTypeCount());
    mRecycler.invalidateScraps();

    mPageCount = mAdapter.getCount();

    // put the current page within the new adapter range
    newPosition = Math.min(mPageCount - 1,
        newPosition == INVALID_PAGE_POSITION ? 0 : newPosition);

    if (newPosition != INVALID_PAGE_POSITION) {
      // TODO pretty confusing
      // this will be correctly set in setFlipDistance method
      mCurrentPageIndex = INVALID_PAGE_POSITION;
      mFlipDistance = INVALID_FLIP_DISTANCE;
      flipTo(newPosition);
    } else {
      mFlipDistance = INVALID_FLIP_DISTANCE;
      mPageCount = 0;
      setFlipDistance(0);
    }
  }

  private int getNewPositionOfCurrentPage() {
    // check if id is on same position, this is because it will
    // often be that and this way you do not need to iterate the whole
    // dataset. If it is the same position, you are done.
    if (mCurrentPageId == mAdapter.getItemId(mCurrentPageIndex)) {
      return mCurrentPageIndex;
    }

    // iterate the dataset and look for the correct id. If it
    // exists, set that position as the current position.
    for (int i = 0; i < mAdapter.getCount(); i++) {
      if (mCurrentPageId == mAdapter.getItemId(i)) {
        return i;
      }
    }

    // Id no longer is dataset, keep current page
    return mCurrentPageIndex;
  }

  private void dataSetInvalidated() {
    if (mAdapter != null) {
      mAdapter.unregisterDataSetObserver(dataSetObserver);
      mAdapter = null;
    }
    mRecycler = new Recycler();
    removeAllViews();
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    int width = getDefaultSize(0, widthMeasureSpec);
    int height = getDefaultSize(0, heightMeasureSpec);

    measureChildren(widthMeasureSpec, heightMeasureSpec);

    setMeasuredDimension(width, height);
  }

  @Override
  protected void measureChildren(int widthMeasureSpec, int heightMeasureSpec) {
    int width = getDefaultSize(0, widthMeasureSpec);
    int height = getDefaultSize(0, heightMeasureSpec);

    int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(width,
        MeasureSpec.EXACTLY);
    int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height,
        MeasureSpec.EXACTLY);
    final int childCount = getChildCount();
    for (int i = 0; i < childCount; i++) {
      final View child = getChildAt(i);
      measureChild(child, childWidthMeasureSpec, childHeightMeasureSpec);
    }
  }

  @Override
  protected void measureChild(View child, int parentWidthMeasureSpec,
                              int parentHeightMeasureSpec) {
    child.measure(parentWidthMeasureSpec, parentHeightMeasureSpec);
  }

  @Override
  protected void onLayout(boolean changed, int l, int t, int r, int b) {
    layoutChildren();

    mTopRect.top = 0;
    mTopRect.left = 0;
    mTopRect.right = getWidth();
    mTopRect.bottom = getHeight() / 2;

    mBottomRect.top = getHeight() / 2;
    mBottomRect.left = 0;
    mBottomRect.right = getWidth();
    mBottomRect.bottom = getHeight();

    mLeftRect.top = 0;
    mLeftRect.left = 0;
    mLeftRect.right = getWidth() / 2;
    mLeftRect.bottom = getHeight();

    mRightRect.top = 0;
    mRightRect.left = getWidth() / 2;
    mRightRect.right = getWidth();
    mRightRect.bottom = getHeight();
  }

  private void layoutChildren() {
    final int childCount = getChildCount();
    for (int i = 0; i < childCount; i++) {
      final View child = getChildAt(i);
      layoutChild(child);
    }
  }

  private void layoutChild(View child) {
    child.layout(0, 0, getWidth(), getHeight());
  }

  private void setFlipDistance(float flipDistance) {

    if (mPageCount < 1) {
      mFlipDistance = 0;
      mCurrentPageIndex = INVALID_PAGE_POSITION;
      mCurrentPageId = -1;
      recycleActiveViews();
      return;
    }

    if (flipDistance == mFlipDistance) {
      return;
    }

    mFlipDistance = flipDistance;

    final int currentPageIndex = (int) Math.round(mFlipDistance
        / FLIP_DISTANCE_PER_PAGE);

    if (mCurrentPageIndex != currentPageIndex) {
      mCurrentPageIndex = currentPageIndex;
      mCurrentPageId = mAdapter.getItemId(mCurrentPageIndex);

      // TODO be smarter about this. Dont remove a view that will be added
      // again on the next line.
      recycleActiveViews();

      // add the new active views
      if (mCurrentPageIndex > 0) {
        fillPageForIndex(mPreviousPage, mCurrentPageIndex - 1);
        addView(mPreviousPage.v);
      }
      if (mCurrentPageIndex >= 0 && mCurrentPageIndex < mPageCount) {
        fillPageForIndex(mCurrentPage, mCurrentPageIndex);
        addView(mCurrentPage.v);
      }
      if (mCurrentPageIndex < mPageCount - 1) {
        fillPageForIndex(mNextPage, mCurrentPageIndex + 1);
        addView(mNextPage.v);
      }
    }

    invalidate();
  }

  private void fillPageForIndex(Page p, int i) {
    p.position = i;
    p.viewType = mAdapter.getItemViewType(p.position);
    p.v = getView(p.position, p.viewType);
    p.valid = true;
  }

  private void recycleActiveViews() {
    // remove and recycle the currently active views
    if (mPreviousPage.valid) {
      removeView(mPreviousPage.v);
      mRecycler.addScrapView(mPreviousPage.v, mPreviousPage.position,
          mPreviousPage.viewType);
      mPreviousPage.valid = false;
    }
    if (mCurrentPage.valid) {
      removeView(mCurrentPage.v);
      mRecycler.addScrapView(mCurrentPage.v, mCurrentPage.position,
          mCurrentPage.viewType);
      mCurrentPage.valid = false;
    }
    if (mNextPage.valid) {
      removeView(mNextPage.v);
      mRecycler.addScrapView(mNextPage.v, mNextPage.position,
          mNextPage.viewType);
      mNextPage.valid = false;
    }
  }

  private View getView(int index, int viewType) {
    // get the scrap from the recycler corresponding to the correct view
    // type
    Scrap scrap = mRecycler.getScrapView(index, viewType);

    // get a view from the adapter if a scrap was not found or it is
    // invalid.
    View v = null;
    if (scrap == null || !scrap.valid) {
      v = mAdapter.getView(index, scrap == null ? null : scrap.v, this);
    } else {
      v = scrap.v;
    }

    // return view
    return v;
  }

  @Override
  protected void dispatchDraw(Canvas canvas) {

    if (mPageCount < 1) {
      return;
    }

    if (!mScroller.isFinished() && mScroller.computeScrollOffset()) {
      setFlipDistance(mScroller.getCurrY());
    }

    if (mIsFlipping || !mScroller.isFinished()) {
      showAllPages();
      drawPreviousHalf(canvas);
      drawNextHalf(canvas);
      drawFlippingHalf(canvas);
    } else {
      endScroll();
      setDrawWithLayer(mCurrentPage.v, false);
      hideOtherPages(mCurrentPage);
      drawChild(canvas, mCurrentPage.v, 0);
    }

    // if overflip is GLOW mode and the edge effects needed drawing, make
    // sure to invalidate
    if (mOverFlipper.draw(canvas)) {
      // always invalidate whole screen as it is needed 99% of the time.
      // This is because of the shadows and shines put on the non-flipping
      // pages
      invalidate();
    }
  }

  private void hideOtherPages(Page p) {
    if (mPreviousPage != p && mPreviousPage.valid && mPreviousPage.v.getVisibility() != GONE) {
      mPreviousPage.v.setVisibility(GONE);
    }
    if (mCurrentPage != p && mCurrentPage.valid && mCurrentPage.v.getVisibility() != GONE) {
      mCurrentPage.v.setVisibility(GONE);
    }
    if (mNextPage != p && mNextPage.valid && mNextPage.v.getVisibility() != GONE) {
      mNextPage.v.setVisibility(GONE);
    }
    p.v.setVisibility(VISIBLE);
  }

  private void showAllPages() {
    if (mPreviousPage.valid && mPreviousPage.v.getVisibility() != VISIBLE) {
      mPreviousPage.v.setVisibility(VISIBLE);
    }
    if (mCurrentPage.valid && mCurrentPage.v.getVisibility() != VISIBLE) {
      mCurrentPage.v.setVisibility(VISIBLE);
    }
    if (mNextPage.valid && mNextPage.v.getVisibility() != VISIBLE) {
      mNextPage.v.setVisibility(VISIBLE);
    }
  }

  /**
   * draw top/left half
   *
   * @param canvas
   */
  private void drawPreviousHalf(Canvas canvas) {
    canvas.save();
    canvas.clipRect(isFlippingVertically() ? mTopRect : mLeftRect);

    final float degreesFlipped = getDegreesFlipped();
    final Page p = degreesFlipped > 90 ? mPreviousPage : mCurrentPage;

    // if the view does not exist, skip drawing it
    if (p.valid) {
      setDrawWithLayer(p.v, true);
      drawChild(canvas, p.v, 0);
    }

    drawPreviousShadow(canvas);
    canvas.restore();
  }

  /**
   * draw top/left half shadow
   *
   * @param canvas
   */
  private void drawPreviousShadow(Canvas canvas) {
    final float degreesFlipped = getDegreesFlipped();
    if (degreesFlipped > 90) {
      final int alpha = (int) (((degreesFlipped - 90) / 90f) * MAX_SHADOW_ALPHA);
      mShadowPaint.setAlpha(alpha);
      canvas.drawPaint(mShadowPaint);
    }
  }

  /**
   * draw bottom/right half
   *
   * @param canvas
   */
  private void drawNextHalf(Canvas canvas) {
    canvas.save();
    canvas.clipRect(isFlippingVertically() ? mBottomRect : mRightRect);

    final float degreesFlipped = getDegreesFlipped();
    final Page p = degreesFlipped > 90 ? mCurrentPage : mNextPage;

    // if the view does not exist, skip drawing it
    if (p.valid) {
      setDrawWithLayer(p.v, true);
      drawChild(canvas, p.v, 0);
    }

    drawNextShadow(canvas);
    canvas.restore();
  }

  /**
   * draw bottom/right half shadow
   *
   * @param canvas
   */
  private void drawNextShadow(Canvas canvas) {
    final float degreesFlipped = getDegreesFlipped();
    if (degreesFlipped < 90) {
      final int alpha = (int) ((Math.abs(degreesFlipped - 90) / 90f) * MAX_SHADOW_ALPHA);
      mShadowPaint.setAlpha(alpha);
      canvas.drawPaint(mShadowPaint);
    }
  }

  private void drawFlippingHalf(Canvas canvas) {
    canvas.save();
    mCamera.save();

    final float degreesFlipped = getDegreesFlipped();
    Log.d("FLIP", "de:" + degreesFlipped);
    if (degreesFlipped > 90) {
      canvas.clipRect(isFlippingVertically() ? mTopRect : mLeftRect);
      if (mIsFlippingVertically) {
        mCamera.rotateX(degreesFlipped - 180);
      } else {
        mCamera.rotateY(180 - degreesFlipped);
      }
    } else {
      canvas.clipRect(isFlippingVertically() ? mBottomRect : mRightRect);
      if (mIsFlippingVertically) {
        mCamera.rotateX(degreesFlipped);
      } else {
        mCamera.rotateY(-degreesFlipped);
      }
    }

    mCamera.getMatrix(mMatrix);

    positionMatrix();
    canvas.concat(mMatrix);

    setDrawWithLayer(mCurrentPage.v, true);
    drawChild(canvas, mCurrentPage.v, 0);

//    drawFlippingShadeShine(canvas);

    mCamera.restore();
    canvas.restore();
  }

  /**
   * will draw a shade if flipping on the previous(top/left) half and a shine
   * if flipping on the next(bottom/right) half
   *
   * @param canvas
   */
  private void drawFlippingShadeShine(Canvas canvas) {
    final float degreesFlipped = getDegreesFlipped();
    if (degreesFlipped < 90) {
      final int alpha = (int) ((degreesFlipped / 90f) * MAX_SHINE_ALPHA);
      mShinePaint.setAlpha(alpha);
      canvas.drawRect(isFlippingVertically() ? mBottomRect : mRightRect,
          mShinePaint);
    } else {
      final int alpha = (int) ((Math.abs(degreesFlipped - 180) / 90f) * MAX_SHADE_ALPHA);
      mShadePaint.setAlpha(alpha);
      canvas.drawRect(isFlippingVertically() ? mTopRect : mLeftRect,
          mShadePaint);
    }
  }

  /**
   * Enable a hardware layer for the view.
   *
   * @param v
   * @param drawWithLayer
   */
  private void setDrawWithLayer(View v, boolean drawWithLayer) {
    if (isHardwareAccelerated()) {
      if (v.getLayerType() != LAYER_TYPE_HARDWARE && drawWithLayer) {
        v.setLayerType(LAYER_TYPE_HARDWARE, null);
      } else if (v.getLayerType() != LAYER_TYPE_NONE && !drawWithLayer) {
        v.setLayerType(LAYER_TYPE_NONE, null);
      }
    }
  }

  private void positionMatrix() {
    mMatrix.preScale(0.25f, 0.25f);
    mMatrix.postScale(4.0f, 4.0f);
    mMatrix.preTranslate(-getWidth() / 2, -getHeight() / 2);
    mMatrix.postTranslate(getWidth() / 2, getHeight() / 2);
  }

  private float getDegreesFlipped() {
    float localFlipDistance = mFlipDistance % FLIP_DISTANCE_PER_PAGE;

    // fix for negative modulo. always want a positive flip degree
    if (localFlipDistance < 0) {
      localFlipDistance += FLIP_DISTANCE_PER_PAGE;
    }

    return (localFlipDistance / FLIP_DISTANCE_PER_PAGE) * 180;
  }

  /**
   * @param deltaFlipDistance The distance to flip.
   * @return The duration for a flip, bigger deltaFlipDistance = longer
   * duration. The increase if duration gets smaller for bigger values
   * of deltaFlipDistance.
   */
  private int getFlipDuration(int deltaFlipDistance) {
    float distance = Math.abs(deltaFlipDistance);
    return (int) (MAX_SINGLE_PAGE_FLIP_ANIM_DURATION * Math.sqrt(distance
        / FLIP_DISTANCE_PER_PAGE));
  }

  private int getCurrentPageRound() {
    return Math.round(mFlipDistance / FLIP_DISTANCE_PER_PAGE);
  }

  private int getCurrentPageFloor() {
    return (int) Math.floor(mFlipDistance / FLIP_DISTANCE_PER_PAGE);
  }

  private int getCurrentPageCeil() {
    return (int) Math.ceil(mFlipDistance / FLIP_DISTANCE_PER_PAGE);
  }

  /**
   * @return true if ended a flip
   */
  private boolean endFlip() {
    final boolean wasflipping = mIsFlipping;
    mIsFlipping = false;
    if (mVelocityTracker != null) {
      mVelocityTracker.recycle();
      mVelocityTracker = null;
    }
    return wasflipping;
  }

  /**
   * @return true if ended a scroll
   */
  private boolean endScroll() {
    final boolean wasScrolling = !mScroller.isFinished();
    mScroller.abortAnimation();
    return wasScrolling;
  }


  /* ---------- API ---------- */

  /**
   * @param adapter a regular ListAdapter, not all methods if the list adapter are
   *                used by the flipview
   */
  public void setAdapter(ListAdapter adapter) {
    if (mAdapter != null) {
      mAdapter.unregisterDataSetObserver(dataSetObserver);
    }

    // remove all the current views
    removeAllViews();

    mAdapter = adapter;
    mPageCount = adapter == null ? 0 : mAdapter.getCount();

    if (adapter != null) {
      mAdapter.registerDataSetObserver(dataSetObserver);

      mRecycler.setViewTypeCount(mAdapter.getViewTypeCount());
      mRecycler.invalidateScraps();
    }

    // TODO pretty confusing
    // this will be correctly set in setFlipDistance method
    mCurrentPageIndex = INVALID_PAGE_POSITION;
    mFlipDistance = INVALID_FLIP_DISTANCE;
    setFlipDistance(0);
  }

  public ListAdapter getAdapter() {
    return mAdapter;
  }

  public int getPageCount() {
    return mPageCount;
  }

  public int getCurrentPage() {
    return mCurrentPageIndex;
  }

  public void flipTo(int page) {
    if (page < 0 || page > mPageCount - 1) {
      throw new IllegalArgumentException("That page does not exist");
    }
    endFlip();
    setFlipDistance(page * FLIP_DISTANCE_PER_PAGE);
  }

  public void smoothFlipTo(int page) {
    if (page < 0 || page > mPageCount - 1) {
      throw new IllegalArgumentException("That page does not exist");
    }
    final int start = (int) mFlipDistance;
    final int delta = page * FLIP_DISTANCE_PER_PAGE - start;

    endFlip();
    mScroller.startScroll(0, start, 0, delta, getFlipDuration(delta));
    invalidate();
  }

  /**
   * @return true if the view is flipping vertically, can only be set via xml
   * attribute "orientation"
   */
  public boolean isFlippingVertically() {
    return mIsFlippingVertically;
  }

  public void setOverFlipMode(OverFlipMode overFlipMode) {
    this.mOverFlipMode = overFlipMode;
    mOverFlipper = OverFlipperFactory.create(this, mOverFlipMode);
  }
}
