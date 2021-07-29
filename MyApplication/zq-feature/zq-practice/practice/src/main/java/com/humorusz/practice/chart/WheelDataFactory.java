package com.humorusz.practice.chart;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

public final class WheelDataFactory {
  private WheelDataFactory() {
  }

  public static List<WheelData> createDataList(List<String> list) {
    List<WheelData> dataList = new ArrayList<>();
    if (list == null || list.size() == 0) {
      return dataList;
    }
    for (int i = 0; i < list.size(); i++) {
      WheelData data = new WheelData();
      data.mBackgroundColor = i % 2 == 0 ? Color.BLUE : Color.GRAY;
      data.mTextColor = i % 2 == 0 ? Color.YELLOW : Color.RED;
      data.mPosition = i;
      data.mText = list.get(i);
      dataList.add(data);
    }
    return dataList;
  }
}
