package com.niupuyue.mylibrary.callbacks;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019-07-08
 * Time: 22:54
 * Desc: 语音录制接口回调
 * Version:
 */
public interface IAudioRecordCallback {

    /**
     * 录音成功
     *
     * @param var1 var1 数量存在多个，其中第一个和第二个是文件缓存路径和录音时长
     */
    void onSuccess(Object... var1);

    /**
     * 语音错误
     *
     * @param errorCode 错误代码
     * @param errorMsg  错误信息
     */
    void onError(int errorCode, String errorMsg);

    /**
     * 正在录音
     *
     * @param timeCount 时间计数
     */
    void onProgress(int timeCount);


}
