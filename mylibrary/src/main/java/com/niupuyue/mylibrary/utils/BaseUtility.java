package com.niupuyue.mylibrary.utils;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019/6/14
 * Time: 00:31
 * Desc:
 * Version:
 */
public class BaseUtility {

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0 || str.isEmpty();
    }

    public static boolean isEmpty(List<?> list) {
        return null == list || list.size() == 0;
    }

    public static boolean isEmpty(Object[] objs) {
        return null == objs || objs.length == 0;
    }

    public static boolean isEmpty(Set<?> set) {
        return null == set || set.isEmpty() || set.size() == 0;
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return null == map || map.isEmpty() || isEmpty(map.keySet());
    }

}
