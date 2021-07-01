package com.humorousz.uiutils.helper;

import android.graphics.Bitmap;
import android.view.View;

/**
 * @author zhangzhiquan
 * on 2018/5/19
 */
public interface ImageLoadingListener {
    public void onLoadingStarted(String imageUri, View view);

    public void onLoadingFailed(String imageUri, View view, String failReason);

    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage);

    public void onLoadingCancelled(String imageUri, View view);
}
