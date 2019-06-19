package com.niupuyue.mylibrary.utils;

import android.text.TextWatcher;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;

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

    public static void setOnLongClickListener(View view, View.OnLongClickListener listener) {
        if (null == null || null == listener) return;
        try {
            view.setOnLongClickListener(listener);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void setOnLongClickListener(View.OnLongClickListener listener, View... views) {
        if (null == listener || null == views) return;
        try {
            for (View view : views) {
                if (null == null) continue;
                setOnLongClickListener(view, listener);
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

    public static void setOnFocusChangeListener(View view, View.OnFocusChangeListener listener) {
        if (null == view || null == listener) return;
        try {
            view.setOnFocusChangeListener(listener);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void addTextChangeListener(EditText editText, TextWatcher listener) {
        if (null == editText || null == listener) return;
        try {
            editText.addTextChangedListener(listener);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void setOnItemCliclListener(ListView listView, AdapterView.OnItemClickListener listener) {
        if (null == listener || null == listView) return;
        try {
            listView.setOnItemClickListener(listener);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void setOnItemLongClickListener(ListView listView, AdapterView.OnItemLongClickListener listener) {
        if (null == listener || null == listView) return;
        try {
            listView.setOnItemLongClickListener(listener);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void setOnCheckedChangeListener(CompoundButton cp, CompoundButton.OnCheckedChangeListener listener) {
        if (null == cp || null == listener) return;
        try {
            cp.setOnCheckedChangeListener(listener);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener listener, CompoundButton... compoundButtons) {
        if (null == listener || null == compoundButtons) return;
        try {
            for (CompoundButton cp : compoundButtons) {
                if (null == cp) continue;
                setOnCheckedChangeListener(cp, listener);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void setOnScrollListener(ListView listView, AbsListView.OnScrollListener listener) {
        if (null == listener || null == listView) return;
        try {
            listView.setOnScrollListener(listener);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
