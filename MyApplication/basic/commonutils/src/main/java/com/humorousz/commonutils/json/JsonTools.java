package com.humorousz.commonutils.json;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zhangzhiquan on 2017/4/24.
 */

public class JsonTools {

    public static final JsonParseBase  jsonParse = new JsonParse();

    public static <T> T parse(String jsonString,Class<T> cls){
        if(jsonString.isEmpty() || jsonString.trim().isEmpty()){
            return null;
        }
        try {
            JSONObject object = new JSONObject(jsonString);
            return parse(object,cls);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static<T> T parse(JSONObject jsonObj,Class<T> cls){
        return jsonParse.parse(jsonObj, cls);
    }

}
