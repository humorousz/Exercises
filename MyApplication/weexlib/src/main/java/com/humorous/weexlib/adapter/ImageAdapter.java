package com.humorous.weexlib.adapter;

import android.text.TextUtils;
import android.widget.ImageView;

import com.humorousz.uiutils.helper.ImageLoaderHelper;
import com.taobao.weex.adapter.IWXImgLoaderAdapter;
import com.taobao.weex.common.WXImageStrategy;
import com.taobao.weex.dom.WXImageQuality;

/**
 * @author zhangzhiquan
 * on 2018/5/19
 * 图片加载
 */
public class ImageAdapter implements IWXImgLoaderAdapter {
    @Override
    public void setImage(String url, ImageView view, WXImageQuality quality, WXImageStrategy strategy) {
        if(TextUtils.isEmpty(url) || view == null){
            return;
        }
        ImageLoaderHelper.displayImage(url,view);
    }
}
