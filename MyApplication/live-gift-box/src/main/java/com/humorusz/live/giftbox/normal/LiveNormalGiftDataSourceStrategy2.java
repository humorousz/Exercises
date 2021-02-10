package com.humorusz.live.giftbox.normal;

import java.util.List;

import com.humorusz.live.giftbox.base.LiveGiftItem;
import com.humorusz.live.giftbox.base.LiveGiftPanelTabView;
import com.humorusz.live.giftbox.data.GiftStore;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * @author zhangzhiquan
 * @date 2021/2/10
 */
public class LiveNormalGiftDataSourceStrategy2 implements LiveGiftPanelTabView.GiftDataSourceStrategy {
  @Override
  public Observable<List<LiveGiftItem>> getGiftItemsObservable() {
    return Observable.fromArray(GiftStore.getDataPage2()).observeOn(AndroidSchedulers.mainThread());
  }
}
