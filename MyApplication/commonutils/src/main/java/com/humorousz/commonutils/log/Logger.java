package com.humorousz.commonutils.log;

import android.util.Log;

/**
 * Created by zzq on 17-4-20.
 */

public class Logger {
  private static final String TAG = "MRZ";

  public static void d(String tag, String msg) {
    Log.d(tag, msg);

  }

  public static void i(String tag, String msg) {
    Log.i(tag, msg);
  }

  public static void e(String tag, String msg) {
    Log.e(tag, msg);
  }

  public static void d(String tag, Object msg) {
    d(tag, String.valueOf(msg));
  }

  public static void e(String tag, Object msg) {
    d(tag, String.valueOf(msg));
  }

  public static void d(String msg) {
    d(TAG, msg);
  }

}
