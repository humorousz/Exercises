package com.example.plugin;

import java.util.Map;

import com.example.plugin.biz.ZQPlugin;

public class ZQPluginManager<T extends ZQPlugin> {
  private static Map<Class<? extends ZQPlugin>, ? extends ZQPlugin> sZQPluginMap;

  public T getPlugin(Class<T> cls) {
    return (T) sZQPluginMap.get(cls);
  }
}
