package com.niupuyue.mylibrary.widgets.chatkeyboard.callbacks;


/**
 * Desc: 语音播放回调
 */
public interface IMRecordPlayerCallback {

    /**
     * 播放完成
     *
     * @param position   需要操作对象所在的位置
     * @param isAutoPlay 是否需要自动播放下一条未读消息
     */
    void recorderPlayerComplete(int position, boolean isAutoPlay);

    /**
     * 停止播放
     *
     * @param position 需要操作对象所在的位置
     */
    void recorderPlayerStop(int position);

    /**
     * 开始播放
     *
     * @param isCache 是否已经缓存完毕
     */
    void recorderPlayerStart(int position, boolean isCache);

    /**
     * 缓存状态发生改变
     *
     * @param position 需要操作对象的位置
     * @param isCache  是否完成缓存
     */
    void recorderPlayerCaching(int position, boolean isCache);

}
