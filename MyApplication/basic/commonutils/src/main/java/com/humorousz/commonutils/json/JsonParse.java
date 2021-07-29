package com.humorousz.commonutils.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangzhiquan on 2017/5/1.
 */

public class JsonParse  extends JsonParseBase {
    @Override
    public <T> T parse(JSONObject jsonObj, Class<T> cls) {
        T a;
        try {
            a = cls.newInstance();
            Field[] fields = cls.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                setField(field, a, jsonObj);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
        return a;
    }

    private void setField(Field field, Object object, JSONObject value) {
        try {

            if (Modifier.isStatic(field.getModifiers())) {
                //静态类型变量 不操作
                return;
            }
            if(value.optString(field.getName()).isEmpty()){
                //值为字符串"null"
                return;
            }
            Class typeClass = field.getType();
            //判断是否是List子类的方法
            if (List.class.isAssignableFrom(typeClass)) {
                setListField(field, object, typeClass, value.getJSONArray(field.getName()));

            } else if (typeClass.isArray()) {
                setArrayField(field, object, typeClass, value.getJSONArray(field.getName()));

            } else if (typeClass == String.class) {
                field.set(object, value.opt(field.getName()));

            } else if (typeClass == Integer.class || typeClass == int.class) {
                field.setInt(object, Integer.valueOf(value.optString(field.getName())));

            } else if (typeClass == Double.class || typeClass == double.class) {
                field.setDouble(object, Double.valueOf(value.optString(field.getName())));

            } else if (typeClass == Long.class || typeClass == long.class) {
                field.setLong(object, Long.valueOf(value.optString(field.getName())));

            } else if (typeClass == Float.class || typeClass == float.class) {
                field.setFloat(object, Float.valueOf(value.optString(field.getName())));

            } else if (typeClass == Boolean.class || typeClass == boolean.class) {
                field.setBoolean(object, Boolean.valueOf(value.optString(field.getName())));

            } else if (typeClass.isPrimitive()) {
                field.set(object, value.opt(field.getName()));

            } else {
                field.set(object, parse(value.getJSONObject(field.getName()), typeClass));

            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private <T> T createList(Class<T> cls) throws IllegalAccessException, InstantiationException {
        if (cls == List.class) {
            return (T) ArrayList.class.newInstance();
        }
        return cls.newInstance();
    }

    private void setListField(Field field, Object object, Class cls, JSONArray jsonArray) throws InstantiationException, IllegalAccessException, JSONException {
        if (!List.class.isAssignableFrom(cls)) {
            return;
        }
        Type type = field.getGenericType();
        ParameterizedType pt = (ParameterizedType) type;
        Class<?> dataClass = (Class<?>) pt.getActualTypeArguments()[0];
        Object typeObj = createList(cls);
        for (int i = 0; i < jsonArray.length(); i++) {
            ((List<Object>) typeObj).add(parse(jsonArray.getJSONObject(i), dataClass));
        }
        field.set(object, typeObj);
    }

    private void setArrayField(Field field, Object object, Class cls, JSONArray jsonArray) throws JSONException, IllegalAccessException {
        if (!cls.isArray() || jsonArray == null || jsonArray.length() <= 0) {
            return;
        }
        Class componentType = cls.getComponentType();
        int length = jsonArray.length();
        Object array = Array.newInstance(componentType, length);
        for (int i = 0; i < length; i++) {
            Array.set(array, i, parse(jsonArray.getJSONObject(i), componentType));
        }
        field.set(object, array);

    }

}
