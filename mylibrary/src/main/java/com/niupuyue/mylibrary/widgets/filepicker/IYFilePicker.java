package com.niupuyue.mylibrary.widgets.filepicker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.niupuyue.mylibrary.utils.BaseUtility;

/**
 * Coder: niupuyue
 * Date: 2019/6/25
 * Time: 10:55
 * Desc: 文件选择器管理类
 * Version:
 */
public class IYFilePicker {

    private Activity mActivity;
    private Fragment mFragment;
    private android.app.Fragment mAppFragment;

    private String mTitle;
    private boolean mMutilyMode;
    private int mFolderIconStyle;
    private String mStartPath;
    private String[] filters;
    private int mRequestCode;

    public IYFilePicker with(Activity activity) {
        this.mActivity = activity;
        return this;
    }

    public IYFilePicker with(Fragment fragment) {
        this.mFragment = fragment;
        return this;
    }

    public IYFilePicker with(android.app.Fragment fragment) {
        this.mAppFragment = fragment;
        return this;
    }

    public IYFilePicker withTitle(String title) {
        this.mTitle = title;
        return this;
    }

    public IYFilePicker withMutilyMode(boolean isMutily) {
        this.mMutilyMode = isMutily;
        return this;
    }

    public IYFilePicker withFolderIconStyle(int style) {
        this.mFolderIconStyle = style;
        return this;
    }

    public IYFilePicker withFileFilter(String[] filter) {
        this.filters = filter;
        return this;
    }

    public IYFilePicker withStartPath(String path) {
        if (!BaseUtility.isEmpty(path)) {
            this.mStartPath = path;
        }
        return this;
    }

    public IYFilePicker withRequestCode(int requestCode) {
        this.mRequestCode = requestCode;
        return this;
    }

    public void start() {
        if (null == mActivity || null == mFragment || null == mAppFragment) {
            throw new RuntimeException("必须传递相应的Activity或Fragment");
        }
        Intent intent = getIntent();
        Bundle bundle = getBundle();
        intent.putExtras(bundle);

        if (mActivity != null) {
            mActivity.startActivityForResult(intent, mRequestCode);
        } else if (mFragment != null) {
            mFragment.startActivityForResult(intent, mRequestCode);
        } else {
            mAppFragment.startActivityForResult(intent, mRequestCode);
        }
    }

    private Intent getIntent() {
        Intent intent;
        if (mActivity != null) {
            intent = new Intent(mActivity, FilePickerActivity.class);
        } else if (mFragment != null) {
            intent = new Intent(mFragment.getActivity(), FilePickerActivity.class);
        } else {
            intent = new Intent(mAppFragment.getActivity(), FilePickerActivity.class);
        }
        return intent;
    }

    private Bundle getBundle() {
        Bundle bundle = new Bundle();
        FilePickerModel model = new FilePickerModel();
        model.setTitle(mTitle);
        model.setFilters(filters);
        model.setFolderIconStyle(mFolderIconStyle);
        model.setStartPath(mStartPath);
        bundle.putSerializable("param", model);
        return bundle;
    }

}
