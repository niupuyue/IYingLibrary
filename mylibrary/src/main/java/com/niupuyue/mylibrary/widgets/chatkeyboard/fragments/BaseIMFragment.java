package com.niupuyue.mylibrary.widgets.chatkeyboard.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.renrui.job.baseInfo.BaseFragment;


/**
 * IM自定义键盘fragment的基类
 */
public abstract class BaseIMFragment extends BaseFragment {

    private View root;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.root = inflater.inflate(getIMLayoutId(), container, false);
        initEventBus();
        initExtralData();
        initViewFindViewById(root);
        initViewListener();
        initDataAfterInitLayout();
        return root;
    }

    public abstract int getIMLayoutId();

    public void initExtralData() {
    }

    public abstract void initViewFindViewById(View root);

    public void initViewListener() {
    }

    public void initDataAfterInitLayout() {
    }

}
