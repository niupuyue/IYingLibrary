package com.niupuyue.mylibrary.widgets.filepicker;

import java.io.Serializable;

/**
 * Coder: niupuyue
 * Date: 2019/6/25
 * Time: 11:26
 * Desc: 文件选择器  需要设置参数的对象
 * Version:
 */
public class FilePickerModel implements Serializable {

    private String title;
    private String startPath;
    private String[] filters;
    private boolean mutilyMode;
    private int folderIconStyle;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartPath() {
        return startPath;
    }

    public void setStartPath(String startPath) {
        this.startPath = startPath;
    }

    public String[] getFilters() {
        return filters;
    }

    public void setFilters(String[] filters) {
        this.filters = filters;
    }

    public boolean isMutilyMode() {
        return mutilyMode;
    }

    public void setMutilyMode(boolean mutilyMode) {
        this.mutilyMode = mutilyMode;
    }

    public int getFolderIconStyle() {
        return folderIconStyle;
    }

    public void setFolderIconStyle(int folderIconStyle) {
        this.folderIconStyle = folderIconStyle;
    }
}
