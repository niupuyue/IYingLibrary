package com.niupuyue.mylibrary.widgets;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import com.niupuyue.mylibrary.callbacks.ITextviewClickable;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019-07-08
 * Time: 22:28
 * Desc:
 * Version:
 */
public class SpanClickable extends ClickableSpan implements View.OnClickListener {

    private final ITextviewClickable clickListener;
    private int position;
    private boolean isUnderline;
    private int color;
    private boolean isBold = false;
    private float textSize;

    public SpanClickable(ITextviewClickable clickListener, int position, boolean isUnderline, int color, boolean isBold, float textSize) {
        this.clickListener = clickListener;
        this.position = position;
        this.isUnderline = isUnderline;
        this.color = color;
        this.isBold = isBold;
        this.textSize = textSize;
    }

    @Override
    public void onClick(@NonNull View v) {
        if (clickListener != null)
            clickListener.onSpanClick(position);
    }

    @Override
    public void updateDrawState(@NonNull TextPaint ds) {
        super.updateDrawState(ds);
        ds.setUnderlineText(isUnderline);
        ds.setColor(color);
        if (this.isBold) {
            ds.setTypeface(Typeface.DEFAULT_BOLD);
        }
        if (textSize > 0) {
            ds.setTextSize(textSize);
        }
    }

}
