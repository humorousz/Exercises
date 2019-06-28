package com.humorousz.commonutils.util;

import java.util.Collection;

/**
 * 一些集合的操作
 */
public final class CollectionsUtil {
  private CollectionsUtil() {
  }

  public static boolean isEmpty(Collection collection) {
    return collection == null || collection.isEmpty();
  }
}
