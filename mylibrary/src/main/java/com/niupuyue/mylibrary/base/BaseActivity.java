package com.niupuyue.mylibrary.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.niupuyue.mylibrary.AppManager;

/**
 * Coder: niupuyue
 * Date: 2019/7/24
 * Time: 18:37
 * Desc: Activity的基类，完成一些必须要实现的方法
 * Version:
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getInstance().addActivity(this);
        setContentView(getLayoutId());
        // 暂时不考使用注入依赖框架
        initViewById();
        initViewListener();
        initDataAfterListener();
    }

    public abstract int getLayoutId();

    public abstract void initViewById();

    public void initViewListener() {

    }

    public void initDataAfterListener() {

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getInstance().removeActivity(this);
    }
}
