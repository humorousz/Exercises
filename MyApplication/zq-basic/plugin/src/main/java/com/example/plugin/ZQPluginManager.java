package com.example.plugin;

import java.util.Map;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.example.plugin.biz.ZQPlugin;

public class ZQPluginManager<T extends ZQPlugin> {
  private static Map<Class<? extends ZQPlugin>, ZQPlugin> sZQPluginMap;

  @Nullable
  public T getPlugin(Class<T> cls) {
    Object plugin = sZQPluginMap.get(cls);
    return (T) sZQPluginMap.get(cls);
  }

  public void registerPlugin(Class<T> cls, @NotNull ZQPlugin plugin) {
    sZQPluginMap.put(cls, plugin);
  }
}
