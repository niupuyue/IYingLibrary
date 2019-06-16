package com.niupuyue.mylibrary.widgets.chatkeyboard.callbacks;

/**
 * Desc: 音频播放更新UI回调
 * 注意：此处所有的语音地址都是网络地址，与本地缓存地址无关
 */
public interface IMRecordPlayerUIChangeCallback {

    /**
     * 开始播放语音，更新UI
     */
    void recordPlayerStartUI(int position);

    /**
     * 播放语音完成，更新UI
     */
    void recordPlayerCompleteUI(int position);

    /**
     * 播放语音停止，更新UI
     */
    void recordPlayerStopUI(int position);

    /**
     * 当前页面是否正在缓存
     */
    void recordPlayerCachingUI(int position, boolean isCache);

}
