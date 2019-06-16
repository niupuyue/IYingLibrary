package com.niupuyue.mylibrary.widgets.chatkeyboard.callbacks;


import java.util.List;

/**
 * Desc:发送消息页面
 */
public interface IMChatSendMessageCallback {

    /**
     * 发送普通文本
     */
    void sendTextMessage(String msg);

    /**
     * 发送图片
     */
    void sendImageMessage(List<String> imageContents);

    /**
     * 发送位置
     */
    void sendLocationMessage(Object locationContent);

    /**
     * 发送语音
     */
    void sendVoiceMessage(String voiceModel);
}