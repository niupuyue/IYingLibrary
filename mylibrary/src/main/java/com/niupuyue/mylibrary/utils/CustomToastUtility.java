package com.niupuyue.mylibrary.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.niupuyue.mylibrary.R;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019/6/14
 * Time: 23:23
 * Desc: 公共自定义Toast
 * Version:
 */
public class CustomToastUtility {

    private static final int TYPE_SUCCESS = 1;
    private static final int TYPE_ERROR = 2;
    private static final int TYPE_WARN = 3;

    /**
     * 显示吐司
     *
     * @param type     1：成功 2：失败 3：警告
     * @param title    标题
     * @param content  内容
     * @param duration Toast.LENGTH_SHORT or Toast.LENGTH_LONG
     */
    private static void show(int type, String title, String content, int duration) {
        View toastView;
        if (duration == Toast.LENGTH_SHORT) {
            toastView = ToastUtility.showCustomShort(LibraryConstants.getContext(), R.layout.view_mytoast);
        } else {
            toastView = ToastUtility.showCustomLong(LibraryConstants.getContext(), R.layout.view_mytoast);
        }

        ImageView ivStat = toastView.findViewById(R.id.ivStat);
        TextView tvTitle = toastView.findViewById(R.id.tvTitle);
        TextView tvContent = toastView.findViewById(R.id.tvContent);

        int drawableId = R.drawable.toast_sucess_icon;

        switch (type) {
            case TYPE_SUCCESS:
                drawableId = R.drawable.toast_sucess_icon;
                break;
            case TYPE_ERROR:
                drawableId = R.drawable.toast_error_icon;
                break;
            case TYPE_WARN:
                drawableId = R.drawable.toast_warn_icon;
                break;
            default:
                break;
        }

        try {
            ivStat.setBackgroundResource(drawableId);
            tvTitle.setText(title);
            tvContent.setVisibility(!TextUtils.isEmpty(content) ? View.VISIBLE : View.GONE);
            tvContent.setText(content);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 提示成功信息
     *
     * @param title 标题，不能为空
     */
    public static void makeTextSucess(String title) {
        makeTextSucess(title, "");
    }

    /**
     * 提示成功信息
     *
     * @param resourceID String资源ID
     */
    public static void makeTextSucess(int resourceID) {
        makeTextSucess(LibraryConstants.getContext().getString(resourceID));
    }

    /**
     * 提示成功信息
     *
     * @param title   标题，不能为空
     * @param content 内容可以为空
     */
    public static void makeTextSucess(String title, String content) {
        makeTextSucess(title, content, Toast.LENGTH_LONG);
    }

    /**
     * 提示成功信息
     *
     * @param resourceID 标题资源ID
     * @param content    内容可以为空
     */
    public static void makeTextSucess(int resourceID, String content) {
        makeTextSucess(LibraryConstants.getContext().getString(resourceID), content);
    }

    public static void makeTextSucess(String title, String content, int duration) {
        show(TYPE_SUCCESS, title, content, duration);
    }

    /**
     * 提示失败信息
     *
     * @param title 标题
     */
    public static void makeTextError(String title) {
        makeTextError(title, "");
    }

    /**
     * 提示失败信息
     *
     * @param resourceID String资源ID
     */
    public static void makeTextError(int resourceID) {
        makeTextError(LibraryConstants.getContext().getString(resourceID));
    }

    /**
     * 提示失败信息
     *
     * @param title   标题
     * @param content 内容
     */
    public static void makeTextError(String title, String content) {
        makeTextError(title, content, Toast.LENGTH_LONG);
    }

    /**
     * 提示失败信息
     *
     * @param resourceID String资源ID
     */
    public static void makeTextError(int resourceID, String content) {
        makeTextError(LibraryConstants.getContext().getString(resourceID), content, Toast.LENGTH_LONG);
    }

    /**
     * 提示失败信息
     *
     * @param title   标题
     * @param content 内容
     */
    public static void makeTextError(String title, String content, int duration) {
        show(TYPE_ERROR, title, content, duration);
    }

    /**
     * 提示警告信息
     */
    public static void makeTextWarn(String title) {
        makeTextWarn(title, "");
    }

    /**
     * 提示警告信息
     */
    public static void makeTextWarn(int resourceID) {
        makeTextWarn(LibraryConstants.getContext().getString(resourceID));
    }

    /**
     * 提示警告信息
     */
    public static void makeTextWarn(int resourceID, String content) {
        makeTextWarn(LibraryConstants.getContext().getString(resourceID), content);
    }

    /**
     * 提示警告信息
     */
    public static void makeTextWarn(int resourceID, int duration) {
        makeTextWarn(LibraryConstants.getContext().getString(resourceID), "", Toast.LENGTH_LONG);
    }

    /**
     * 提示警告信息
     */
    public static void makeTextWarn(String title, String content) {
        makeTextWarn(title, content, Toast.LENGTH_LONG);
    }

    /**
     * 提示警告信息
     */
    public static void makeTextWarn(String title, String content, int duration) {
        show(TYPE_WARN, title, content, duration);
    }

}
