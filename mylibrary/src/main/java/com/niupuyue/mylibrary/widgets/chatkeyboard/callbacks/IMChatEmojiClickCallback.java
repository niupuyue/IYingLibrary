package com.niupuyue.mylibrary.widgets.chatkeyboard.callbacks;

/**
 * Desc:表情点击事件
 */
public interface IMChatEmojiClickCallback {
    /**
     * 表情点击事件
     *
     * @param emojiType 表情类型
     * @param emojiName 表情名称
     */
    void onEmojiClick(int emojiType, String emojiName);
}
