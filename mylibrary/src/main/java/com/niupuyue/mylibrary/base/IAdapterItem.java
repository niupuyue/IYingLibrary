package com.niupuyue.mylibrary.base;

/**
 * Coder: niupuyue
 * Date: 2019/8/23
 * Time: 16:21
 * Desc:
 * Version:
 */
public interface IAdapterItem<T> {
    void setData(T item,int position,boolean showDivider);
    void setListener(IAdapter listener);
}
