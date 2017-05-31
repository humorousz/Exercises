package com.humorousz.uiutils.helper;

import com.humorousz.commonutils.service.CommonService;

/**
 * Created by zhangzhiquan on 2017/5/30.
 */

public class UIUtils {

    public static final int dip2px(int dip) {
        if(CommonService.getApplication() == null ){
            return -1;
        }
        float scale = CommonService.getApplication().getResources().getDisplayMetrics().density;
        return (int)((float)dip * scale + 0.5F);
    }



    public static final int px2dip(int px) {
        if(CommonService.getApplication() == null ){
            return -1;
        }
        float scale = CommonService.getApplication().getResources().getDisplayMetrics().density;
        return (int)((float)px / scale + 0.5F);
    }
}
