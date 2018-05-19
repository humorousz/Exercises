package com.humorousz.uiutils.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * Created by zhangzhiquan on 2017/5/29.
 */

public class ImageLoaderHelper {
    public static void init(Context context){
        ImageLoaderConfiguration config = ImageLoaderConfiguration.createDefault(context.getApplicationContext());
        ImageLoader.getInstance().init(config);
    }

    public static void displayImage(String url,ImageView imageView){
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        loadImage(url,imageView,options);
    }

    public static void loadImage(final String url , final ImageLoadingListener listener){
        if(TextUtils.isEmpty(url) || listener == null){
            return;
        }
        ImageLoader.getInstance().loadImage(url, new com.nostra13.universalimageloader.core.listener.ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                listener.onLoadingStarted(imageUri,view);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                listener.onLoadingFailed(url,view,failReason.toString());
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                listener.onLoadingComplete(imageUri,view,loadedImage);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                listener.onLoadingCancelled(imageUri,view);
            }
        });
    }

    public static void displayCircleImage(String url,ImageView imageView){
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new RoundedBitmapDisplayer(150))
                .build();
        loadImage(url,imageView,options);
    }

    private static void loadImage(String url,ImageView imageView,  DisplayImageOptions displayImageOptions) {
        if (TextUtils.isEmpty(url)) {
            ImageLoader.getInstance().displayImage(null, imageView, displayImageOptions);
            imageView.setTag(null);
            return;
        }
        if (imageView.getTag() == null || (!TextUtils.isEmpty(url) && !url.equals(imageView.getTag()))) {
            imageView.setTag(url);
            ImageLoader.getInstance().displayImage(url, imageView, displayImageOptions);
            return;
        }
    }

    public static Drawable createCircleDrawable(Bitmap bitmap){
        return  new CircleBitmapDisplayer.CircleDrawable(bitmap, null, 0);
    }
}
