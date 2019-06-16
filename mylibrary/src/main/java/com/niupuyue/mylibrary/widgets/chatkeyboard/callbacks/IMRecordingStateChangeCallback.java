package com.niupuyue.mylibrary.widgets.chatkeyboard.callbacks;

import com.niupuyue.mylibrary.widgets.chatkeyboard.eventModel.OnSendVoiceEvent;

/**
 * Desc:改变语音播放状态回调
 */
public interface IMRecordingStateChangeCallback {

    /**
     * 改变录音状态
     *
     * @param event 当前需要修改的参数
     */
    void changeRecorderState(OnSendVoiceEvent event);

}
