package com.niupuyue.mylibrary.widgets;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019/6/12
 * Time: 23:03
 * Desc: 没有滑动事件的viewpager
 * Version:
 */
public class NoSlidingViewpager extends ViewPager {

    private boolean isSliding = true;

    public NoSlidingViewpager(@NonNull Context context) {
        super(context);
    }

    public NoSlidingViewpager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 设置是否可以滑动
     *
     * @param isSliding
     */
    public void setSliding(boolean isSliding) {
        this.isSliding = isSliding;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {

        if (!isSliding) {
            return false;
        }
        return super.onInterceptTouchEvent(arg0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        if (!isSliding) {
            return false;
        }
        return super.onTouchEvent(arg0);
    }
}
