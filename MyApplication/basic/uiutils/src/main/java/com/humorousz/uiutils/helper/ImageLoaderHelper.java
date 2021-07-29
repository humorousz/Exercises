package com.humorousz.uiutils.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.facebook.common.internal.Preconditions;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.datasource.DataSubscriber;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeHolder;
import com.facebook.fresco.animation.drawable.AnimatedDrawable2;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.CloseableStaticBitmap;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import static android.graphics.Bitmap.createBitmap;

/**
 * @author zhangzhiquan
 * @date 2017/5/29
 * <p>
 * ImageLoader
 */

public class ImageLoaderHelper {
  private ImageLoaderHelper() {
  }

  public static void init(Context context) {
    ImageLoaderConfiguration config = ImageLoaderConfiguration.createDefault(context.getApplicationContext());
    ImageLoader.getInstance().init(config);
    Fresco.initialize(context);
  }

  public static void displayImage(String url, ImageView imageView) {
    DisplayImageOptions options = new DisplayImageOptions.Builder()
        .cacheInMemory(true)
        .cacheOnDisk(true)
        .bitmapConfig(Bitmap.Config.RGB_565)
        .build();
    loadImage(url, imageView, options);
  }

  public static void loadImage(final String url, final ImageLoadingListener listener) {
    if (TextUtils.isEmpty(url) || listener == null) {
      return;
    }
    ImageLoader.getInstance().loadImage(url, new com.nostra13.universalimageloader.core.listener.ImageLoadingListener() {
      @Override
      public void onLoadingStarted(String imageUri, View view) {
        listener.onLoadingStarted(imageUri, view);
      }

      @Override
      public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
        listener.onLoadingFailed(url, view, failReason.toString());
      }

      @Override
      public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
        listener.onLoadingComplete(imageUri, view, loadedImage);
      }

      @Override
      public void onLoadingCancelled(String imageUri, View view) {
        listener.onLoadingCancelled(imageUri, view);
      }
    });
  }

  public static void displayCircleImage(String url, ImageView imageView) {
    DisplayImageOptions options = new DisplayImageOptions.Builder()
        .cacheInMemory(true)
        .cacheOnDisk(true)
        .bitmapConfig(Bitmap.Config.RGB_565)
        .displayer(new RoundedBitmapDisplayer(150))
        .build();
    loadImage(url, imageView, options);
  }

  private static void loadImage(String url, ImageView imageView, DisplayImageOptions displayImageOptions) {
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

  public static Drawable createCircleDrawable(Bitmap bitmap) {
    return new CircleBitmapDisplayer.CircleDrawable(bitmap, null, 0);
  }

  public static void downLoadDrawable(Context context, String res, final ImageDownloadListener listener) {
    if (listener == null) {
      return;
    }
    ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(res)).build();
    DraweeController draweeController = Fresco.newDraweeControllerBuilder()
        .setAutoPlayAnimations(true)
        .setUri(Uri.parse(res))
        .setControllerListener(new BaseControllerListener<ImageInfo>() {
          @Override
          public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
            if (animatable instanceof AnimatedDrawable2) {
              AnimatedDrawable2 drawable = (AnimatedDrawable2) animatable;
              listener.onDrwableDownload(drawable);
              drawable.start();
            }
          }
        })
        .build();
    DraweeHolder mDraweeHolder = DraweeHolder.create(null, context);
    mDraweeHolder.setController(draweeController);
    mDraweeHolder.getTopLevelDrawable();
  }


  private static Drawable createDrawable(CloseableReference<CloseableImage> image) {
    Preconditions.checkState(CloseableReference.isValid(image));
    CloseableImage closeableImage = image.get();
    if (closeableImage instanceof CloseableStaticBitmap) {
      CloseableStaticBitmap closeableStaticBitmap = (CloseableStaticBitmap) closeableImage;
      return new BitmapDrawable(createBitmap(closeableStaticBitmap.getUnderlyingBitmap()));
    } else {
      throw new UnsupportedOperationException("Unrecognized image class: " + closeableImage);
    }
  }


}
