package com.niupuyue.mylibrary.widgets;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019/6/12
 * Time: 23:03
 * Desc: 没有滑动事件的viewpager
 * Version:
 */
public class NoSlidingViewpager extends ViewPager {
    public NoSlidingViewpager(@NonNull Context context) {
        super(context);
    }

    public NoSlidingViewpager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
}
