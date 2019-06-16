package com.niupuyue.mylibrary.widgets.chatkeyboard.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;


/**
 * IM自定义键盘fragment的基类
 */
public abstract class BaseIMFragment extends Fragment {

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

    public void initEventBus() {
        try {
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
