package com.example.plugin;

import java.util.HashMap;
import java.util.Map;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.example.plugin.biz.ZQPlugin;

public class ZQPluginManager {
  private static final Map<Class<? extends ZQPlugin>, ZQPlugin> sZQPluginMap = new HashMap<>();
  private static volatile ZQPluginManager sZQPluginManager;

  private ZQPluginManager() {}


  public static ZQPluginManager getInstance() {
    if (sZQPluginManager == null) {
      synchronized (ZQPluginManager.class) {
        if (sZQPluginManager == null) {
          sZQPluginManager = new ZQPluginManager();
        }
      }
    }
    return sZQPluginManager;
  }

  @Nullable
  public <T extends ZQPlugin> T getPlugin(Class<T> cls) {
    return (T) sZQPluginMap.get(cls);
  }

  public <T extends ZQPlugin> void registerPlugin(Class<T> cls, @NotNull ZQPlugin plugin) {
    sZQPluginMap.put(cls, plugin);
  }
}
