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
public class RoundLinearLayout extends TwinkLinearLayout {

    private float mRoundLayoutRadius;
    private Path mRoundPath;
    private RectF mRectF;

    public RoundLinearLayout(Context context) {
        super(context);
        init();
    }

    public RoundLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundLinearLayout);
        mRoundLayoutRadius = typedArray.getDimension(R.styleable.RoundLinearLayout_radius, 15);
        typedArray.recycle();

        init();
    }

    private void init() {
        mRoundPath = new Path();
        mRectF = new RectF();
    }

    private void setRoundPath() {
        mRoundPath.rewind();//解决布局变化重绘时无法重新裁切
        mRoundPath.addRoundRect(mRectF, mRoundLayoutRadius, mRoundLayoutRadius, Path.Direction.CW);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mRectF.set(0f, 0f, getMeasuredWidth(), getMeasuredHeight());
        setRoundPath();
    }

    @Override
    public void draw(Canvas canvas) {
        if (mRoundLayoutRadius > 0f) {
            canvas.clipPath(mRoundPath);
        }
        super.draw(canvas);
    }
}
