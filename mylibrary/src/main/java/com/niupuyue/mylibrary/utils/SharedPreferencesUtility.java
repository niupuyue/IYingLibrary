package com.niupuyue.mylibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;

/**
 * Coder: niupuyue
 * Date: 2019/6/14
 * Time: 12:51
 * Desc:
 * Version:
 */
public class SharedPreferencesUtility {

    private static final String TAG = SharedPreferences.class.getSimpleName();
    private static final String SHAREDPREFERENCES_NAME = "app_sharedpreference";

    public static void writeString(Context context, String key, String value) {
        if (context == null) return;
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (editor != null) {
                editor.putString(key, value);
                editor.commit();
            }
        }
    }

    public static String readString(Context context, String key) {
        String value = "";
        if (context == null) return null;
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences != null && sharedPreferences.contains(key)) {
            value = sharedPreferences.getString(key, "");
        }
        return value;
    }

    public static void writeInt(Context context, String key, Integer value) {
        if (context == null) return;
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (editor != null) {
                editor.putInt(key, value);
                editor.commit();
            }
        }
    }

    public static Integer readInt(Context context, String key) {
        int value = 0;
        if (context == null) return value;
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences != null && sharedPreferences.contains(key)) {
            value = sharedPreferences.getInt(key, 0);
        }
        return value;
    }

    public static void writeBoolean(Context context, String key, Boolean value) {
        if (context == null) return;
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (editor != null) {
                editor.putBoolean(key, value);
                editor.commit();
            }
        }
    }

    public static Boolean readBoolean(Context context, String key) {
        boolean value = false;
        if (context == null) return value;
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences != null && sharedPreferences.contains(key)) {
            value = sharedPreferences.getBoolean(key, false);
        }
        return value;
    }

}
