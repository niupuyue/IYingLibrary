package com.niupuyue.mylibrary.utils;

import android.content.Context;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019/6/18
 * Time: 23:01
 * Desc:
 * Version:
 */
public class LibraryConstants {

    private static Context mContext;

    public static void setContext(Context context) {
        mContext = context;
    }

    public static Context getContext() {
        return mContext;
    }

}
