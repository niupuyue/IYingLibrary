package com.niupuyue.mylibrary.widgets;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.niupuyue.mylibrary.R;
import com.niupuyue.mylibrary.callbacks.ISimpleDialogButtonClickCallback;
import com.niupuyue.mylibrary.utils.BaseUtility;
import com.niupuyue.mylibrary.utils.ListenerUtility;
import com.niupuyue.mylibrary.utils.ScreenUtility;

/**
 * Coder: niupuyue
 * Date: 2019/7/10
 * Time: 17:14
 * Desc: 简单dialog
 * Version:
 */
public class SimpleDialog {


    public static void showSimpleDialog(Context context, String title, String content, ISimpleDialogButtonClickCallback clickCallback) {
        showSimpleDialog(context, title, content, "取消", "确认", Gravity.CENTER, false, clickCallback);
    }

    public static void showSimpleDialog(Context context, String title, ISimpleDialogButtonClickCallback clickCallback) {
        showSimpleDialog(context, title, null, "取消", "确认", Gravity.CENTER, false, clickCallback);
    }

    public static void showSimpleDialog(Context context, String title, String content, String leftButton, String rightButton, int gravity, final boolean cancel, final ISimpleDialogButtonClickCallback clickCallback) {
        if (null == context) return;
        if (context instanceof Activity && ((Activity) context).isFinishing()) return;
        final Dialog dialog = new Dialog(context, android.R.style.Theme_Holo_Dialog_NoActionBar);
        dialog.setCanceledOnTouchOutside(cancel);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        View view = LayoutInflater.from(context).inflate(R.layout.view_simple_dialog, null);
        TextView tvTitle = view.findViewById(R.id.tvTitle);
        TextView tvContent = view.findViewById(R.id.tvContent);
        TextView tvOnlyContent = view.findViewById(R.id.tvOnlyContent);
        TextView tvOk = view.findViewById(R.id.tvOk);
        TextView tvCancel = view.findViewById(R.id.tvCancel);

        if (!BaseUtility.isEmpty(content) && BaseUtility.isEmpty(title)) {
            // 只有内容
            BaseUtility.resetVisibility(tvTitle, false);
            BaseUtility.resetVisibility(tvContent, false);
            BaseUtility.resetVisibility(tvOnlyContent, !BaseUtility.isEmpty(content));
            BaseUtility.setGravity(tvOnlyContent, gravity);
            BaseUtility.setText(tvOnlyContent, content);
        } else if (BaseUtility.isEmpty(content) && !BaseUtility.isEmpty(title)) {
            // 只有标题
            BaseUtility.resetVisibility(tvTitle, false);
            BaseUtility.resetVisibility(tvContent, false);
            BaseUtility.resetVisibility(tvOnlyContent, !BaseUtility.isEmpty(title));
            BaseUtility.setGravity(tvOnlyContent, gravity);
            BaseUtility.setText(tvOnlyContent, title);
        } else {
            // 既有标题又有内容
            tvTitle.getPaint().setFakeBoldText(true);
            BaseUtility.setText(tvTitle, title);
            BaseUtility.setText(tvContent, content);
            BaseUtility.setGravity(tvContent, gravity);
            BaseUtility.resetVisibility(tvTitle, true);
            BaseUtility.resetVisibility(tvContent, true);
            BaseUtility.resetVisibility(tvOnlyContent, false);
        }

        BaseUtility.resetVisibility(tvCancel, !BaseUtility.isEmpty(leftButton));
        BaseUtility.setText(tvCancel, leftButton);
        ListenerUtility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                if (null != clickCallback) {
                    clickCallback.onLeftButtonClick();
                }
            }
        }, tvCancel);

        BaseUtility.resetVisibility(tvOk, !BaseUtility.isEmpty(rightButton));
        BaseUtility.setText(tvOk, rightButton);
        ListenerUtility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                if (null != clickCallback) {
                    clickCallback.onRightButtonClick();
                }
            }
        }, tvOk);
        dialog.setContentView(view);
        dialog.show();

        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = ScreenUtility.dp2px(290);
        dialog.getWindow().setAttributes(params);
    }

}
