package com.niupuyue.mylibrary.callbacks;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019-07-08
 * Time: 23:08
 * Desc: 语音播放状态接口回调
 * Version:
 * next：添加语音播放的百分比
 */
public interface IAudioPlayCallback {

    /**
     * 语音播放开始
     */
    void onAudioPlayStart();

    /**
     * 音频播放完成
     */
    void onAudioPlayComplete();

    /**
     * 音频播放停止
     */
    void onAudioPlayStop();

}
