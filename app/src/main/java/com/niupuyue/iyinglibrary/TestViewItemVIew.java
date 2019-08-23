package com.niupuyue.iyinglibrary;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.niupuyue.mylibrary.base.IAdapter;
import com.niupuyue.mylibrary.base.IAdapterItem;
import com.niupuyue.mylibrary.utils.BaseUtility;

/**
 * Coder: niupuyue
 * Date: 2019/8/23
 * Time: 17:13
 * Desc:
 * Version:
 */
public class TestViewItemVIew extends LinearLayout implements IAdapterItem {

    private TestViewHolder mViewHolder;

    public TestViewItemVIew(Context context) {
        this(context, null);
    }

    public TestViewItemVIew(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestViewItemVIew(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View topCardItem = View.inflate(getContext(), R.layout.item_view, null);
        mViewHolder = new TestViewHolder();
        mViewHolder.title = topCardItem.findViewById(R.id.title);
        addView(topCardItem);
    }

    @Override
    public void setData(Object item, int position, boolean showDivider) {
        BaseUtility.setText(mViewHolder.title, (String) item);
    }

    @Override
    public void setListener(IAdapter listener) {

    }
}
