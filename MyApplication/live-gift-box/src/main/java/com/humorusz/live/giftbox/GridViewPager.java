package com.humorusz.live.giftbox;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class GridViewPager extends ViewPager {

  private List<BGGridView> mGridViewList = null;

  private static final int DEFAULT_COLUMN_NUMBER = 2;
  private static final int DEFAULT_ROW_NUMBER = 3;

  private int mRowNumber = 0;
  private int mColumnNumber = 0;

  private float mCellMinWidth = 0;
  private float mCellMinHeight = 0;
  private float mColumnMargin = 0;
  private float mRowMargin = 0;

  private BaseAdapter mAdapter;

  private View mEmptyView = null;

  private int mPaddingLeft = 0;
  private int mPaddingRight = 0;

  private int mSelection = -1;
  private GridPagerAdapter mGridPagerAdapter;
  private PageTransformer mTransformer;

  public GridViewPager(Context context) {
    this(context, null);
  }

  public GridViewPager(Context context, AttributeSet attrs) {
    super(context, attrs);
    if (attrs != null) {
      TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.GridViewPager);
      final int N = a.getIndexCount();
      for (int i = 0; i < N; i++) {
        int attr = a.getIndex(i);
        if (attr == R.styleable.GridViewPager_gvpColumnNumber) {
          mColumnNumber = a.getInt(attr, -1);
        } else if (attr == R.styleable.GridViewPager_gvpRowNumber) {
          mRowNumber = a.getInt(attr, -1);
        } else if (attr == R.styleable.GridViewPager_gvpColumnMargin) {
          mColumnMargin = a.getDimension(attr, 0);
        } else if (attr == R.styleable.GridViewPager_gvpRowMargin) {
          mRowMargin = a.getDimension(attr, 0);
        } else if (attr == R.styleable.GridViewPager_gvpMinCellWidth) {
          mCellMinWidth = a.getDimension(attr, -1);
        } else if (attr == R.styleable.GridViewPager_gvpMinCellHeight) {
          mCellMinHeight = a.getDimension(attr, -1);
        } else if (attr == R.styleable.GridViewPager_android_padding) {
          int padding = a.getDimensionPixelSize(attr, 0);
          setPadding(padding, padding, padding, padding);
        } else if (attr == R.styleable.GridViewPager_android_paddingLeft) {
          mPaddingLeft = a.getDimensionPixelSize(attr, 0);
        } else if (attr == R.styleable.GridViewPager_android_paddingRight) {
          mPaddingRight = a.getDimensionPixelSize(attr, 0);
        }
      }
      if (mColumnNumber <= 0 && mCellMinWidth <= 0) {
        mColumnNumber = DEFAULT_COLUMN_NUMBER;
      }

      if (mRowNumber <= 0 && mCellMinHeight <= 0) {
        mRowNumber = DEFAULT_ROW_NUMBER;
      }
      a.recycle();
    }
    init();
  }

  private void init() {
    mGridViewList = new ArrayList<>();
  }

  public void changeTransformer(boolean isVerticalTransformer) {
    if (isVerticalTransformer) {
      setOverScrollMode(OVER_SCROLL_NEVER);
      setPageTransformer(false, new PageTransformer() {
        @Override
        public void transformPage(View page, float position) {
          page.setTranslationX(page.getWidth() * -position);
          page.setTranslationY(page.getHeight() * position);
        }
      });
    } else {
      setOverScrollMode(OVER_SCROLL_ALWAYS);
      setPageTransformer(false, new PageTransformer() {
        @Override
        public void transformPage(View page, float position) {
          page.setTranslationX(0f);
          page.setTranslationY(0f);
        }
      });
    }
  }

  public boolean isLandscape() {
    int requestedOrientation = ((Activity) getContext()).getRequestedOrientation();
    return requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        || requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
        || requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE;
  }

  private MotionEvent swapTouchEvent(MotionEvent event) {
    float swappedX = event.getY() * 2;
    float swappedY = event.getX() / 2;
    event.setLocation(swappedX, swappedY);
    return event;
  }

  private int getClientWidth() {
    return this.getMeasuredWidth() - this.getPaddingLeft() - this.getPaddingRight();
  }

  /**
   * android4.1+设置PageTransformer会使ViewPager的子页里面的触摸事件异常
   * （当前看到的子页并非在最上面，所以触摸事件被隐藏在其上面的View给消费了）
   * 所以结合setPageTransformer()，在onPageScrolled()里“手动”调用切换页面的动画
   *
   * @param position
   * @param offset
   * @param offsetPixels
   */
  @Override
  protected void onPageScrolled(int position, float offset, int offsetPixels) {
    super.onPageScrolled(position, offset, offsetPixels);
    // 下面的源码来自super.onPageScrolled()
    if (mTransformer != null) {
      final int scrollX = getScrollX();
      final int childCount = getChildCount();
      for (int i = 0; i < childCount; i++) {
        final View child = getChildAt(i);
        final LayoutParams lp = (LayoutParams) child.getLayoutParams();

        if (lp.isDecor) continue;
        final float transformPos = (float) (child.getLeft() - scrollX) / getClientWidth();
        mTransformer.transformPage(child, transformPos);
      }
    }
  }

  @Override
  public void setPageTransformer(boolean reverseDrawingOrder, PageTransformer transformer) {
    super.setPageTransformer(reverseDrawingOrder, null);
    mTransformer = transformer;
  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent event) {
    if (!isLandscape()) {
      return super.onInterceptTouchEvent(event);
    }
    boolean intercept = super.onInterceptTouchEvent(swapTouchEvent(event));
    // If not intercept, touch event should not be swapped.
    swapTouchEvent(event);
    return intercept;
  }

  @Override
  public boolean onTouchEvent(MotionEvent ev) {
    if (!isLandscape()) {
      return super.onTouchEvent(ev);
    }
    return super.onTouchEvent(swapTouchEvent(ev));
  }

  @Override
  public void setPadding(int left, int top, int right, int bottom) {
    mPaddingLeft = left;
    mPaddingRight = right;
    super.setPadding(0, top, 0, bottom);
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    // 设置宽度和高度
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    int columnNumberOld = mColumnNumber;
    int rowNumberOld = mRowNumber;
    if (mCellMinWidth > 0) {
      mColumnNumber = (int) Math
          .floor((MeasureSpec.getSize(widthMeasureSpec)
              + mColumnMargin - mPaddingLeft - mPaddingRight)
              / (mCellMinWidth + mColumnMargin));
    }
    if (mCellMinHeight > 0) {
      mRowNumber = (int) Math
          .floor((MeasureSpec.getSize(heightMeasureSpec)
              + mRowMargin)
              / (mCellMinHeight + mRowMargin));
    }
    if (rowNumberOld != mRowNumber || columnNumberOld != mColumnNumber) {
      notifyDataSetChanged();
    }
  }

  public int getPageCount() {
    return mGridViewList.size();
  }

  public int getPageSize() {
    return mColumnNumber * mRowNumber;
  }

  public void setSelection(int position) {
    final int pageSize = getPageSize();
    if (mAdapter == null || pageSize <= 0) {
      mSelection = position;
      return;
    }
    mSelection = -1;
    setCurrentItem(position / pageSize, true);
  }

  public int getSelection() {
    return getCurrentItem() * getPageSize();
  }

  public void setRowNumber(int rowNumber) {
    mRowNumber = rowNumber;
  }

  public void setRowMargin(float rowMargin) {
    mRowMargin = rowMargin;
  }

  public void setColumnNumber(int columnNumber) {
    mColumnNumber = columnNumber;
  }

  @Override
  public Parcelable onSaveInstanceState() {
    Bundle bundle = new Bundle();
    bundle.putParcelable("instanceState", super.onSaveInstanceState());
    bundle.putInt("selection", getSelection());
    return bundle;
  }

  @Override
  public void onRestoreInstanceState(Parcelable state) {
    if (state instanceof Bundle) {
      Bundle bundle = (Bundle) state;
      this.mSelection = bundle.getInt("selection");
      state = bundle.getParcelable("instanceState");
    }
    super.onRestoreInstanceState(state);
  }

  public void setEmptyView(TextView emptyView) {
    mEmptyView = emptyView;
  }

  private DataSetObserver mObserver = new DataSetObserver() {
    @Override
    public void onChanged() {
      super.onChanged();
      notifyDataSetChanged();
    }
  };

  public void setAdapter(BaseAdapter adapter) {
    if (mAdapter != null) {
      mAdapter.unregisterDataSetObserver(mObserver);
    }

    mAdapter = adapter;
    mAdapter.registerDataSetObserver(mObserver);
    mGridViewList.clear();
    mGridPagerAdapter = null;
    notifyDataSetChanged();
  }

  public void notifyDataSetChanged() {
    int pageSize = mColumnNumber * mRowNumber;
    if (pageSize <= 0) {
      return;
    }
    if (mAdapter.getCount() == 0) {
      mGridViewList.clear();
      if (mEmptyView != null) {
        mEmptyView.setVisibility(View.VISIBLE);
      }
    } else {
      if (mEmptyView != null) {
        mEmptyView.setVisibility(View.GONE);
      }
    }
    int pageCount = mAdapter.getCount() / pageSize;
    if (mAdapter.getCount() % pageSize == 0) {
      pageCount--;
    }
    int listSize = mGridViewList.size() - 1;
    BGGridView gridview;
    GridAdapter gridAdapter;
    for (int i = 0; i <= Math.max(listSize, pageCount); i++) {
      if (i <= listSize && i <= pageCount) {
        gridview = mGridViewList.get(i);
        if (gridview.getAdapter() == null ||
            gridview.getAdapter().getCount() != mColumnNumber * mRowNumber) {
          gridAdapter = new GridAdapter(i, pageSize, mAdapter);
          gridview.setAdapter(gridAdapter);
        } else {
          // 复用以前的 adapter 中的 view 和数据.
          ((GridAdapter) gridview.getAdapter()).notifyDataSetChanged();
        }
        mGridViewList.set(i, gridview);
        continue;
      }
      if (i > listSize && i <= pageCount) {
        gridview = new BGGridView();
        gridAdapter = new GridAdapter(i, pageSize, mAdapter);
        gridview.setAdapter(gridAdapter);
        mGridViewList.add(gridview);
        continue;
      }
      if (i > pageCount && i <= listSize) {
        mGridViewList.remove(pageCount + 1);
      }
    }

    if (mGridPagerAdapter == null) {
      mGridPagerAdapter = new GridPagerAdapter();
      super.setAdapter(mGridPagerAdapter);
    } else {
      mGridPagerAdapter.notifyDataSetChanged();
    }
    if (mSelection >= 0) {
      setSelection(mSelection);
    }
  }

  private class GridPagerAdapter extends PagerAdapter {

    @Override
    public int getCount() {
      return mGridViewList.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
      return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
      container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
      container.addView(mGridViewList.get(position),
          new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
      return mGridViewList.get(position);
    }
  }

  private class GridAdapter extends BaseAdapter {
    int mPage;
    int mSize;
    BaseAdapter mAdapter;

    public GridAdapter(int page, int size, BaseAdapter adapter) {
      mPage = page;
      mSize = size;
      mAdapter = adapter;
    }

    @Override
    public int getCount() {
      if (mAdapter.getCount() % mSize == 0)
        return mSize;
      else if (mPage < mAdapter.getCount() / mSize) {
        return mSize;
      } else {
        return mAdapter.getCount() % mSize;
      }
    }

    @Override
    public Object getItem(int position) {
      return mAdapter.getItem(mPage * mSize + position);
    }

    @Override
    public long getItemId(int position) {
      return mAdapter.getItemId(mPage * mSize + position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      return mAdapter.getView(mPage * mSize + position, convertView, parent);
    }
  }


  private class BGGridView extends AdapterView<ListAdapter> {

    private ListAdapter mAdapter;
    private DataSetObserver mObserver = new DataSetObserver() {
      @Override
      public void onChanged() {
        super.onChanged();
        layoutChildren();
      }
    };

    public BGGridView() {
      super(GridViewPager.this.getContext());
    }

    /**
     * 继承AdapterView需要实现以下四个方法
     * getAdapter()
     * setAdapter(ListAdapter adapter)
     * getSelectedView()
     * setSelection(int position)
     */
    @Override
    public ListAdapter getAdapter() {
      return mAdapter;
    }

    @Override
    public void setAdapter(ListAdapter adapter) {

      if (mAdapter != null && mObserver != null) {
        mAdapter.unregisterDataSetObserver(mObserver);
      }

      this.mAdapter = adapter;
      mAdapter.registerDataSetObserver(mObserver);
      layoutChildren();
    }

    private void layoutChildren() {
      // 把所有的child添加到布局中
      int oldChildCount = getChildCount();
      int newChildCount = mAdapter.getCount();

      for (int i = 0; i < oldChildCount && i < newChildCount; i++) {
        mAdapter.getView(i, getChildAt(i), this);
      }
      for (int i = oldChildCount; i < newChildCount; i++) {
        View child = mAdapter.getView(i, null, this);
        addViewInLayout(child, i, new LayoutParams(0, 0));
      }
      int d = oldChildCount - newChildCount;
      if (d > 0) {
        removeViewsInLayout(newChildCount, d);
      }
    }

    @Override
    public View getSelectedView() {
      if (getChildCount() > 0) {
        return getChildAt(0);
      }
      return null;
    }

    @Override
    public void setSelection(int position) {}


    @Override
    public int getPaddingLeft() {
      return mPaddingLeft;
    }

    @Override
    public int getPaddingRight() {
      return mPaddingRight;
    }

    /**
     * 设置大小
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
      // 设置宽度和高度
      int childWidth =
          (int) (MeasureSpec.getSize(widthMeasureSpec) - mColumnMargin * (mColumnNumber - 1)
              - getPaddingLeft() - getPaddingRight()) / mColumnNumber;
      int childHeight =
          (int) (MeasureSpec.getSize(heightMeasureSpec) - mRowMargin * (mRowNumber - 1))
              / mRowNumber;
      for (int i = 0; i < getChildCount(); i++) {
        View child = getChildAt(i);
        LayoutParams lp = child.getLayoutParams();
        lp.width = childWidth;
        lp.height = childHeight;
        child.measure(MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY));
      }
      setMeasuredDimension(
          getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
          getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));
    }

    /**
     * 设置布局
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

      int childCount = getChildCount();
      int pLeft = 0;
      int pTop = getPaddingTop();


      for (int i = 0; i < childCount && i < mColumnNumber * mRowNumber; i++) {
        View child = getChildAt(i);
        int x = i % mColumnNumber;
        if (x == 0) {
          pLeft = getPaddingLeft();
        }
        LayoutParams lp = child.getLayoutParams();
        child.layout(pLeft, pTop, pLeft + lp.width, pTop + lp.height);

        pLeft += lp.width + mColumnMargin;
        if (x == mColumnNumber - 1) {
          pTop += lp.height + mRowMargin;
        }
      }
    }
  }

}
