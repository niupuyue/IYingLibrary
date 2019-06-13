package com.niupuyue.mylibrary.widgets;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019/6/12
 * Time: 23:05
 * Desc: 圆形图片
 * Version:
 */
public class CircleImageView extends AppCompatImageView {
    public CircleImageView(Context context) {
        super(context);
        initView(context);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {

    }
}
