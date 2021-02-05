package com.humorusz.live.giftbox.normal;

import java.util.List;

import com.humorusz.live.giftbox.base.LiveGiftItem;
import com.humorusz.live.giftbox.base.LiveGiftPanelTabView;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * @author zhangzhiquan
 * @date 2021/2/5
 */
class LiveNormalGiftDataSourceStrategy implements LiveGiftPanelTabView.GiftDataSourceStrategy {
  @Override
  public Observable<List<LiveGiftItem>> getGiftItemsObservable() {
    return Observable.fromArray(GiftStore.mData).observeOn(AndroidSchedulers.mainThread());
  }
}
