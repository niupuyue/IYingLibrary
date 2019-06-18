package com.niupuyue.mylibrary.utils;

import android.view.View;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019/6/18
 * Time: 22:25
 * Desc: 注册监听事件
 * Version:
 */
public class ListenerUtility {

    public static void setOnClickListener(View view, View.OnClickListener listener) {
        if (view == null || listener == null) return;
        try {
            view.setOnClickListener(listener);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void setOnClickListener(View.OnClickListener listener, View... views) {
        if (listener == null || views == null) return;
        try {
            for (View view : views) {
                if (view == null) continue;
                setOnClickListener(view, listener);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void setOnTouchListener(View view, View.OnTouchListener listener) {
        if (view == null || listener == null) return;
        try {
            view.setOnTouchListener(listener);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void setOnTouchListener(View.OnTouchListener listener, View... views) {
        if (null == listener || null == views) return;
        try {
            for (View view : views) {
                if (view == null) continue;
                setOnTouchListener(view, listener);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
