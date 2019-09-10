package com.niupuyue.mylibrary.widgets;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019/6/19
 * Time: 22:39
 * Desc: 弹窗基类对象
 * Version:
 */
public class BaseDialog extends DialogFragment {

    @Override
    public void onStart() {
        super.onStart();
        try {
            Dialog dialog = getDialog();
            if (null == dialog) return;
            Window window = dialog.getWindow();
            if (null == window) return;
            window.setGravity(Gravity.CENTER);// 设置对齐方式
            window.setBackgroundDrawableResource(android.R.color.transparent);
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            window.setLayout((int) (dm.widthPixels * 0.8), ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        if (null == manager) return;
        try {
            FragmentTransaction ft = manager.beginTransaction();
            if (!isAdded()) {
                ft.add(this, tag);
            }
            ft.commitAllowingStateLoss();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
