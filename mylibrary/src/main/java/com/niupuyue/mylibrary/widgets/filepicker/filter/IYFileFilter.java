package com.niupuyue.mylibrary.widgets.filepicker.filter;

import com.niupuyue.mylibrary.utils.BaseUtility;

import java.io.File;
import java.io.FileFilter;

/**
 * Coder: niupuyue
 * Date: 2019/6/25
 * Time: 14:01
 * Desc:
 * Version:
 */
public class IYFileFilter implements FileFilter {

    private String[] mTypes;

    public IYFileFilter(String[] types) {
        this.mTypes = types;
    }

    @Override
    public boolean accept(File file) {
        if (file.isDirectory()) {
            return true;
        }
        if (!BaseUtility.isEmpty(mTypes)) {
            for (int i = 0; i < mTypes.length; ++i) {
                if (file.getName().endsWith(mTypes[i].toLowerCase()) || file.getName().endsWith(mTypes[i].toUpperCase())) {
                    return true;
                }
            }
        } else {
            return true;
        }
        return false;
    }
}
