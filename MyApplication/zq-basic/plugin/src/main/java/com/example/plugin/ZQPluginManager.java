package com.example.plugin;

import java.util.Map;

import com.example.plugin.biz.ZQPlugin;

public class ZQPluginManager {
  private static Map<Class<? extends ZQPlugin>, ? extends ZQPlugin> sZQPluginMap;

  <T extends ZQPlugin> public T getPlugin(Class<? extends ZQPlugin> cls) {
    return sZQPluginMap.get(cls);
  }
}
