package com.humorousz.commonutils.json;

import org.json.JSONObject;

/**
 * Created by zhangzhiquan on 2017/5/1.
 */

public abstract class JsonParseBase {
    public abstract <T> T parse(JSONObject jsonObj,Class<T> cls);
}
