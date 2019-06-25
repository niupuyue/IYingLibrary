package com.niupuyue.mylibrary.widgets.filepicker.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Coder: niupuyue
 * Date: 2019/6/25
 * Time: 13:52
 * Desc: recyclerview 当数据为空时显示不同的样式
 * Version:
 */
public class EmptyRecycler extends RecyclerView {

    private View mEmptyView;

    public EmptyRecycler(@NonNull Context context, View mEmptyView) {
        super(context);
    }

    public EmptyRecycler(@NonNull Context context, @Nullable AttributeSet attrs, View mEmptyView) {
        super(context, attrs);
    }

    public EmptyRecycler(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle, View mEmptyView) {
        super(context, attrs, defStyle);
    }

    /**
     * 根据数据判断是否显示空白view
     */
    private void checkShowEmpty() {
        if (mEmptyView != null || getAdapter() != null) {
            mEmptyView.setVisibility(getAdapter().getItemCount() > 0 ? GONE : VISIBLE);
        }
    }

    public void setEmptyView(View emptyView) {
        this.mEmptyView = emptyView;
        checkShowEmpty();
    }

    @Override
    public void setAdapter(@Nullable Adapter adapter) {
        Adapter adapterOld = getAdapter();
        if (adapterOld != null) {
            adapterOld.unregisterAdapterDataObserver(observer);
        }
        super.setAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(observer);
        }
    }

    AdapterDataObserver observer = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            checkShowEmpty();
        }
    };
}
