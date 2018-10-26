package com.humorousz.uiutils.helper;

import android.app.Activity;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import com.humorousz.commonutils.log.Logger;

/**
 * simple and powerful Keyboard show/hidden listener,view {@android.R.id.content} and {@ViewTreeObserver.
 * }
 * Created by yes.cpu@gmail.com 2016/7/13.
 */
public class KeyboardChangeListener implements ViewTreeObserver.OnGlobalLayoutListener,View.OnLayoutChangeListener {
  private static final String TAG = "ListenerHandler";
  private View mContentView;
  private int mOriginHeight;
  private int mPreHeight;
  private KeyBoardListener mKeyBoardListen;

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
       if(oldBottom !=0 && bottom != 0){
         Logger.d(TAG,"height="+(oldBottom - bottom));
       }
    }

    public interface KeyBoardListener {
    /**
     * call back
     *
     * @param isShow         true is show else hidden
     * @param keyboardHeight keyboard height
     */
    void onKeyboardChange(boolean isShow, int keyboardHeight);
  }

  public void setKeyBoardListener(KeyBoardListener keyBoardListen) {
    this.mKeyBoardListen = keyBoardListen;
  }

  public KeyboardChangeListener(Activity contextObj) {
    if (contextObj == null) {
      Log.i(TAG, "contextObj is null");
      return;
    }
    mContentView = findContentView(contextObj);
    if (mContentView != null) {
      addContentTreeObserver();
    }
  }

  private View findContentView(Activity contextObj) {
    return contextObj.findViewById(android.R.id.content);
  }

  private void addContentTreeObserver() {
    mContentView.getViewTreeObserver().addOnGlobalLayoutListener(this);
    mContentView.addOnLayoutChangeListener(this);
  }

  @Override
  public void onGlobalLayout() {
    int currHeight = mContentView.getHeight();
    if (currHeight == 0) {
      Log.i(TAG, "currHeight is 0");
      return;
    }
    boolean hasChange = false;
    if (mPreHeight == 0) {
      mPreHeight = currHeight;
      mOriginHeight = currHeight;
    } else {
      if (mPreHeight != currHeight) {
        hasChange = true;
        mPreHeight = currHeight;
      } else {
        hasChange = false;
      }
    }
    if (hasChange) {
      boolean isShow;
      int keyboardHeight = 0;
      if (mOriginHeight == currHeight) {
        //hidden
        isShow = false;
      } else {
        //show
        keyboardHeight = mOriginHeight - currHeight;
        isShow = true;
      }

      if (mKeyBoardListen != null) {
        mKeyBoardListen.onKeyboardChange(isShow, keyboardHeight);
      }
    }
  }

  public void destroy() {
    if (mContentView != null) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        mContentView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
      }
    }
  }
}