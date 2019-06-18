package com.niupuyue.iyinglibrary;

import android.app.Application;

import com.niupuyue.mylibrary.utils.LibraryConstants;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019/6/18
 * Time: 23:00
 * Desc:
 * Version:
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LibraryConstants.setContext(this);
    }
}
