package com.niupuyue.iyinglibrary;

import android.content.Context;
import android.widget.TextView;

import com.niupuyue.mylibrary.base.IAdapterItem;
import com.niupuyue.mylibrary.base.PubViewHolder;

/**
 * Coder: niupuyue
 * Date: 2019/8/23
 * Time: 17:11
 * Desc:
 * Version:
 */
public class TestViewHolder extends PubViewHolder {

    private IAdapterItem item;

    public TextView title;

    @Override
    public IAdapterItem getItem(Context context) {
        if (item == null) {
            item = new TestViewItemVIew(context);
        }
        return item;
    }
}
