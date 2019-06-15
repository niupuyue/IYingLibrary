package com.niupuyue.mylibrary.utils;

import android.util.Log;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019/6/15
 * Time: 22:29
 * Desc: Logger 日志
 * Version:
 */
public class LoggerUtility {

    public static void e(String tag, String value) {
        if (BaseUtility.isEmpty(tag) || BaseUtility.isEmpty(value)) return;
        Log.e(tag, value);
    }

    public static void e(String tag, int value) {
        if (BaseUtility.isEmpty(tag)) return;
        Log.e(tag, String.valueOf(value));
    }

    public static void e(String tag, boolean value) {
        if (BaseUtility.isEmpty(tag)) return;
        Log.e(tag, String.valueOf(value));
    }

    public static void e(String tag, long value) {
        if (BaseUtility.isEmpty(tag)) return;
        Log.e(tag, String.valueOf(value));
    }

    public static void e(String tag, List<?> values) {
        if (BaseUtility.isEmpty(values) || BaseUtility.isEmpty(tag)) return;
        Log.e(tag, values.toString());
    }

    public static void e(String tag, Object[] objects) {
        if (BaseUtility.isEmpty(tag) || BaseUtility.isEmpty(objects)) return;
        String res = "[";
        for (Object obj : objects) {
            if (obj != null) {
                res += obj.toString();
                res += ";";
            }
        }
        res += "]";
        Log.e(tag, res);
    }

    public static void e(String tag, Set<?> values) {
        if (BaseUtility.isEmpty(tag) || BaseUtility.isEmpty(values)) return;
        String res = "[";
        for (Object obj : values) {
            if (obj != null) {
                res += obj.toString();
                res += ";";
            }
        }
        res += "]";
        Log.e(tag, res);
    }

    public static void e(String tag, Map<?, ?> values) {
        if (BaseUtility.isEmpty(tag) || BaseUtility.isEmpty(values)) return;
        String res = "{ ";
        for (Object key : values.keySet()) {
            if (key != null) {
                Object value = values.get(key);
                res = res + " [ " + key + ":" + value + " ],";
            }
        }
        res += " }";
        Log.e(tag, res);
    }

    public static void e(String tag, Object object) {
        if (BaseUtility.isEmpty(tag) || null == object) return;
        Log.e(tag, object.toString());
    }

    public static void e(String value) {

    }

    public static void e(boolean value) {

    }

    public static void e(int value) {

    }

    public static void e(long value) {

    }

    public static void e(List<?> values) {

    }

    public static void e(Set<?> values) {

    }

    public static void e(Map<?, ?> values) {

    }

    public static void e(Object[] objects) {

    }

    public static void e(Object object) {

    }
}
