package com.niupuyue.mylibrary.utils;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019/6/14
 * Time: 00:31
 * Desc: 基础工具类
 * Version:
 */
public class BaseUtility {

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0 || str.isEmpty();
    }

    public static boolean isEmpty(CharSequence character) {
        return null == character;
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

    public static boolean isEmpty(Collection<?> collection) {
        return null == collection || collection.isEmpty();
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return null == map || map.isEmpty() || isEmpty(map.keySet());
    }

    public static boolean contains(String sourc, String tag) {
        if (isEmpty(sourc) || isEmpty(tag)) return false;
        return sourc.contains(tag);
    }

    public static boolean contains(List<Integer> list, int tag) {
        if (isEmpty(list)) return false;
        try {
            for (Integer value : list) {
                if (value == tag) return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static boolean contains(List<String> list, String tag) {
        if (isEmpty(list) || isEmpty(tag)) return false;
        try {
            for (String str : list) {
                if (equals(str, tag)) {
                    return true;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static boolean contains(Set<String> set, String tag) {
        if (isEmpty(set) || isEmpty(tag)) return false;
        try {
            for (String obg : set) {
                if (equals(obg, tag)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean contains(Map<String, ?> maps, String tag) {
        if (isEmpty(maps) || isEmpty(tag)) return false;
        Set<String> keys = null;
        try {
            keys = maps.keySet();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contains(keys, tag);
    }

    public static boolean startWith(String source, String tag) {
        return startWith(source, tag, 0);
    }

    public static boolean startWith(String source, String tag, int offset) {
        if (isEmpty(source) || isEmpty(tag)) return false;
        try {
            return source.startsWith(tag, offset);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static boolean endWith(String source, String tag) {
        if (isEmpty(source) || isEmpty(tag)) return false;
        try {
            return source.endsWith(tag);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static int size(Collection<?> data) {
        if (isEmpty(data)) return 0;
        try {
            return data.size();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public static int size(Map<?, ?> map) {
        if (isEmpty(map)) return 0;
        try {
            return map.size();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    /**
     * 集合的长度小于limit
     *
     * @param collection
     * @param limit
     * @return 默认返回true
     */
    public static boolean isSizeLimit(Collection<?> collection, int limit) {
        int size = 0;
        if (isEmpty(collection)) return size < limit;
        try {
            return collection.size() < limit;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }

    public static boolean equals(String str1, String str2) {
        return TextUtils.equals(str1, str2);
    }

    public static boolean equalsIgnoreCase(String a, String b) {
        if (a == null && b == null)
            return true;

        if (a == null && b != null)
            return false;

        if (a != null && b == null)
            return false;

        try {
            return TextUtils.equals(a.toLowerCase(), b.toLowerCase());
        } catch (Exception ex) {
            return false;
        }
    }

    public static void remove(List<Integer> list, int tag) {
        remove(list, tag, false);
    }

    /**
     * 如果isObject为true 则删除tag表示需要删除的对象，否则表示需要删除的下标
     *
     * @param list
     * @param tag
     * @param isObject
     * @return
     */
    public static void remove(List<Integer> list, int tag, boolean isObject) {
        if (isEmpty(list)) return;
        try {
            if (isObject) {
                list.remove((Integer) tag);
            } else {
                list.remove(tag);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void resetVisibility(View view, int visiablity) {
        if (view == null || visiablity <= 0) {
            return;
        }
        try {
            view.setVisibility(visiablity);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void resetVisibility(View view, boolean isVisiable) {
        if (view == null) return;
        try {
            view.setVisibility(isVisiable ? View.VISIBLE : View.GONE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static boolean getVisibility(View view) {
        if (null == view) return false;
        try {
            return view.getVisibility() == View.VISIBLE;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static void setBackground(View view, Drawable drawable) {
        if (null == view || null == drawable) return;
        try {
            view.setBackground(drawable);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void setBackgroundResource(View tv, int resoueceID) {
        if (tv == null)
            return;

        try {
            tv.setBackgroundResource(resoueceID);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void setBackgroundDrawable(View view, Drawable drawable) {
        if (null == view || null == drawable) return;
        try {
            view.setBackgroundDrawable(drawable);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void setBackgroundColor(View view, int color) {
        if (view == null || color <= 0) return;
        try {
            view.setBackgroundColor(color);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void setImageResource(ImageView imageView, int resourceId) {
        if (null == imageView || resourceId <= 0) return;
        try {
            imageView.setImageResource(resourceId);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 设置文本内容
     *
     * @param tv
     * @param value
     */
    public static void setText(TextView tv, String value) {
        if (null == tv || isEmpty(value)) return;
        try {
            tv.setText(value);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void setText(TextView tv, int resourceId) {
        if (null == tv || resourceId <= 0) return;
        try {
            tv.setText(LibraryConstants.getContext().getString(resourceId));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void setText(TextView tv, Integer value) {
        if (null == tv) return;
        try {
            tv.setText(String.valueOf(value));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 设置文本颜色
     *
     * @param tv
     * @param color
     */
    public static void setTextColor(TextView tv, int color) {
        if (null == tv || color <= 0) return;
        try {
            tv.setTextColor(ContextCompat.getColor(LibraryConstants.getContext(), color));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 获取文本框内容
     *
     * @param tv
     * @return
     */
    public static String getText(TextView tv) {
        if (null == tv) return null;
        try {
            return tv.getText().toString().trim();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void setHint(TextView tv, String value) {
        if (null == tv) return;
        try {
            tv.setHint(value);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void setHint(TextView tv, int resId) {
        if (null == tv || resId <= 0) return;
        try {
            tv.setHint(LibraryConstants.getContext().getString(resId));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static String getHint(TextView tv) {
        if (null == tv) return null;
        try {
            tv.getHint().toString().trim();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void setGravity(TextView tv, int gravity) {
        if (null == tv || gravity < 0) return;
        try {
            tv.setGravity(gravity);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
