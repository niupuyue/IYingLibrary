package com.niupuyue.mylibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;

/**
 * Coder: niupuyue
 * Date: 2019/6/14
 * Time: 12:51
 * Desc: sharedpreference存储
 * Version:
 */
public class SPUtility {

    private static final String TAG = SharedPreferences.class.getSimpleName();
    private static final String SHAREDPREFERENCES_NAME = "app_sharedpreference";

    public static final String INT_KEYBOARD_HEIGHT = "keyboard_height";

    public static void writeString(String key, String value) {
        if (BaseUtility.isEmpty(key) || BaseUtility.isEmpty(value)) return;
        SharedPreferences sharedPreferences = LibraryConstants.getContext().getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (editor != null) {
                editor.putString(key, value);
                editor.commit();
            }
        }
    }

    public static String readString(String key) {
        if (BaseUtility.isEmpty(key)) return null;
        String value = "";
        SharedPreferences sharedPreferences = LibraryConstants.getContext().getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences != null && sharedPreferences.contains(key)) {
            value = sharedPreferences.getString(key, "");
        }
        return value;
    }

    public static void writeInt(String key, Integer value) {
        if (BaseUtility.isEmpty(key)) return;
        SharedPreferences sharedPreferences = LibraryConstants.getContext().getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (editor != null) {
                editor.putInt(key, value);
                editor.commit();
            }
        }
    }

    public static Integer readInt(String key) {
        int value = 0;
        if (BaseUtility.isEmpty(key)) return value;
        SharedPreferences sharedPreferences = LibraryConstants.getContext().getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences != null && sharedPreferences.contains(key)) {
            value = sharedPreferences.getInt(key, 0);
        }
        return value;
    }

    public static void writeBoolean(String key, Boolean value) {
        if (BaseUtility.isEmpty(key)) return;
        SharedPreferences sharedPreferences = LibraryConstants.getContext().getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (editor != null) {
                editor.putBoolean(key, value);
                editor.commit();
            }
        }
    }

    public static Boolean readBoolean(String key) {
        boolean value = false;
        if (BaseUtility.isEmpty(key)) return value;
        SharedPreferences sharedPreferences = LibraryConstants.getContext().getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences != null && sharedPreferences.contains(key)) {
            value = sharedPreferences.getBoolean(key, false);
        }
        return value;
    }

}
