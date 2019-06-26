package com.niupuyue.mylibrary.widgets.filepicker.enums;

/**
 * Coder: niupuyue
 * Date: 2019/6/26
 * Time: 20:00
 * Desc: 主题样式枚举类型
 * Version:
 */
public enum IYFilePickerEnum {

    Normal(1),
    Mobile(2),
    Wifi(4),
    Other(8);

    public int netType;

    IYFilePickerEnum(int value) {
        this.netType = value;
    }

}
