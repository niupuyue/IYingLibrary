package com.niupuyue.mylibrary.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.niupuyue.mylibrary.utils.BaseUtility;

/**
 * Coder: niupuyue
 * Date: 2019/6/27
 * Time: 14:10
 * Desc: 单行字体大小会改变的文本框
 * Version:
 */
public class SingleLineZoomTextSizeTextView extends AppCompatTextView {

    private Paint mPaint;
    private float mTextSize;

    public SingleLineZoomTextSizeTextView(Context context) {
        super(context);
    }

    public SingleLineZoomTextSizeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SingleLineZoomTextSizeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        refitText(this.getText().toString().trim(), this.getWidth());
    }

    private void refitText(String text, int textWidth) {
        if (textWidth <= 0) return;
        mTextSize = this.getTextSize();// 获取当前textSize

        // 设置画笔
        mPaint = new Paint();
        mPaint.set(this.getPaint());

        int drawWid = 0;// drawableLeft right top bottom 所有图片的宽
        Drawable[] drawas = getCompoundDrawables();
        for (int i = 0; i < drawas.length; i++) {
            if (drawas[i] != null) {
                drawWid += drawas[i].getBounds().width();
            }
        }

        // 获取当前TextView的有效宽度
        int availableWidth = textWidth - this.getPaddingLeft() - this.getPaddingRight() - getCompoundDrawablePadding() - drawWid;
        // 所有字符所占像素宽度
        float textWidths = getTextWidth(text, mTextSize);
        while (textWidths > availableWidth) {
            mPaint.setTextSize(--mTextSize);
            textWidths = getTextWidth(text, mTextSize);
        }
        this.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
    }

    /**
     * 获取字符串所占的宽度
     *
     * @param text
     * @param textSize
     * @return
     */
    private float getTextWidth(String text, float textSize) {
        if (mPaint != null) {
            mPaint.setTextSize(textSize);
            return mPaint.measureText(text);
        }
        return -1;
    }

}
