package com.niupuyue.mylibrary.widgets.chatkeyboard.eventModel;

/**
 * Coder: niupuyue
 * Date: 2019/5/8
 * Time: 18:52
 * Desc: 发送语音消息的event事件
 * Version:
 */
public class OnSendVoiceEvent {
    public int status;
    public int count;
    public boolean isMoreLimit;
    public int showImageType = 0;

    /**
     * 消息状态
     */
    public static final int TYPE_SENDING = 1;
    public static final int TYPE_STOP = 2;
    public static final int TYPE_CANCEL = 3;
    public static final int TYPE_COUNT = 4;
    public static final int TYPE_REBACK_COUNTING = 5;
    public static final int TYPE_CHANGE_VOICE = 6;

    /**
     * 设置语音图片
     */
    public static final int SHOW_IMAGE_TYPE_1 = 1;
    public static final int SHOW_IMAGE_TYPE_2 = 2;
    public static final int SHOW_IMAGE_TYPE_3 = 3;
    public static final int SHOW_IMAGE_TYPE_4 = 4;
    public static final int SHOW_IMAGE_TYPE_5 = 5;
}
