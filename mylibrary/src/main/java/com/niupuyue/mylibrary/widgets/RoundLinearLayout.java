package com.niupuyue.mylibrary.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.niupuyue.mylibrary.R;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019/6/12
 * Time: 23:04
 * Desc: 圆角LinearLayout布局
 * Version:
 */
public class RoundLinearLayout extends LinearLayout {

    private float radius;
    private Path path;
    private RectF rectF;

    public RoundLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
        init();
    }

    public RoundLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
        init();
    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundLinearLayout);
        radius = typedArray.getDimension(R.styleable.RoundLinearLayout_radius, 15);
        typedArray.recycle();
        init();
    }

    private void init() {
        path = new Path();
        rectF = new RectF();
    }

    private void setRoundPath() {
        path.rewind();// 解决布局变化重绘时无法重新裁剪
        path.addRoundRect(rectF, radius, radius, Path.Direction.CW);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        rectF.set(0f, 0f, getMeasuredWidth(), getMeasuredHeight());
        setRoundPath();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (radius > 0f) {
            canvas.clipPath(path);
        }
        super.onDraw(canvas);
    }
}
