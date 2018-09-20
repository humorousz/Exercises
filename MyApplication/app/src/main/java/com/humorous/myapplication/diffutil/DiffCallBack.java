package com.humorous.myapplication.diffutil;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import java.util.List;

public class DiffCallBack extends DiffUtil.Callback{
  private List<CharSequence> mOldDatas,mNewDatas;

  public DiffCallBack(List<CharSequence> oldData,List<CharSequence> newData){
    mOldDatas = oldData;
    mNewDatas = newData;
  }

  @Override
  public int getOldListSize() {
    return mOldDatas == null ? 0 : mOldDatas.size();
  }

  @Override
  public int getNewListSize() {
    return mNewDatas == null ? 0 : mNewDatas.size();
  }

  @Override
  public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
    return mOldDatas.get(oldItemPosition).equals(mNewDatas.get(newItemPosition));
  }

  @Override
  public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
    return mOldDatas.get(oldItemPosition).equals(mNewDatas.get(newItemPosition));
  }

  @Nullable
  @Override
  public Object getChangePayload(int oldItemPosition, int newItemPosition) {
    CharSequence oldChar = mOldDatas.get(oldItemPosition);
    CharSequence newChar = mNewDatas.get(newItemPosition);
    Bundle payload = new Bundle();
    if(!oldChar.equals(newChar)){
      payload.putCharSequence("Char",newChar);
    }
    return payload.size() == 0 ? super.getChangePayload(oldItemPosition, newItemPosition)
        : payload;
  }
}
