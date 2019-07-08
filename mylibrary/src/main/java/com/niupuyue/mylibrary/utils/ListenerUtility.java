package com.niupuyue.mylibrary.utils;

import android.text.TextWatcher;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019/6/18
 * Time: 22:25
 * Desc: 注册监听事件
 * Version:
 */
public class ListenerUtility {

    /**
     * 添加点击事件安全方法
     *
     * @param listener
     * @param views
     */
    public static void setOnClickListener(View.OnClickListener listener, View... views) {
        if (listener == null || views == null) return;
        try {
            for (View view : views) {
                if (view == null) continue;
                view.setOnClickListener(listener);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 添加长摁时间安全方法
     *
     * @param listener
     * @param views
     */
    public static void setOnLongClickListener(View.OnLongClickListener listener, View... views) {
        if (null == listener || null == views) return;
        try {
            for (View view : views) {
                if (null == null) continue;
                view.setOnLongClickListener(listener);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 添加触摸事件安全方法
     *
     * @param listener
     * @param views
     */
    public static void setOnTouchListener(View.OnTouchListener listener, View... views) {
        if (null == listener || null == views) return;
        try {
            for (View view : views) {
                if (view == null) continue;
                view.setOnTouchListener(listener);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 添加焦点改变安全方法
     *
     * @param view
     * @param listener
     */
    public static void setOnFocusChangeListener(View view, View.OnFocusChangeListener listener) {
        if (null == view || null == listener) return;
        try {
            view.setOnFocusChangeListener(listener);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 添加文本输入框监听安全方法
     *
     * @param editText
     * @param listener
     */
    public static void addTextChangeListener(EditText editText, TextWatcher listener) {
        if (null == editText || null == listener) return;
        try {
            editText.addTextChangedListener(listener);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 设置item点击事件安全方法
     *
     * @param listView
     * @param listener
     */
    public static void setOnItemCliclListener(ListView listView, AdapterView.OnItemClickListener listener) {
        if (null == listener || null == listView) return;
        try {
            listView.setOnItemClickListener(listener);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 设置item长摁事件安全方法
     *
     * @param listView
     * @param listener
     */
    public static void setOnItemLongClickListener(ListView listView, AdapterView.OnItemLongClickListener listener) {
        if (null == listener || null == listView) return;
        try {
            listView.setOnItemLongClickListener(listener);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 设置选框按钮选中状态改变安全方法
     *
     * @param listener
     * @param compoundButtons
     */
    public static void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener listener, CompoundButton... compoundButtons) {
        if (null == listener || null == compoundButtons) return;
        try {
            for (CompoundButton cp : compoundButtons) {
                if (null == cp) continue;
                cp.setOnCheckedChangeListener(listener);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 设置单选框组选中状态改变监听安全方法
     *
     * @param listener
     * @param radioGroups
     */
    public static void setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener listener, RadioGroup... radioGroups) {
        if (null == listener || null == radioGroups) return;
        try {
            for (RadioGroup radioGroup : radioGroups) {
                if (null == radioGroup) continue;
                radioGroup.setOnCheckedChangeListener(listener);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 设置listview滚动事件安全方法
     *
     * @param absListView
     * @param listener
     */
    public static void setOnScrollListener(AbsListView absListView, AbsListView.OnScrollListener listener) {
        if (null == listener || null == absListView) return;
        try {
            absListView.setOnScrollListener(listener);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 设置view的样式改变所引起的全局高度变化监听
     */
    public static void addOnGlobalLayoutListener(ViewTreeObserver.OnGlobalLayoutListener listener, View... views) {
        if (null == listener || null == views) return;
        try {
            for (View view : views) {
                if (null == view) continue;
                view.getViewTreeObserver().addOnGlobalLayoutListener(listener);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
