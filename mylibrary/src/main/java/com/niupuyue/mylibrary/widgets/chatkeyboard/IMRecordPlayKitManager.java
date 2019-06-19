package com.niupuyue.mylibrary.widgets.chatkeyboard;

import android.content.Context;

import com.niupuyue.mylibrary.utils.LoggerUtility;
import com.niupuyue.mylibrary.widgets.chatkeyboard.callbacks.IMRecordPlayerCallback;


/**
 * 语音播放管理类
 */
public class IMRecordPlayKitManager implements IMRecordPlayerCallback {

    private Context mContext;
    private RecorderPlayKit recorderPlayKit;
    private int position;

    public IMRecordPlayKitManager(Context context) {
        try {
            this.mContext = context;
            recorderPlayKit = RecorderPlayKit.getInstance(mContext);
            recorderPlayKit.setCallback(this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void play(String url) {
        try {
            recorderPlayKit.play(url, 0, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 播放完成
     * 播放完成之后，播放下一条语音
     */
    @Override
    public void recorderPlayerComplete(int position, boolean isAutoPlay) {
//        if (position < 0 || mAdapter == null || UtilitySecurity.isEmpty(mAdapter.getItems())
//                || position >= mAdapter.getItems().size() || mAdapter.getItems().get(position) == null)
//            return;
        try {
            // 根据返回的path对象，找到对应的viewholder对象，然后再让各自去执行
//            ConversationInfo info = (ConversationInfo) mAdapter.getItems().get(position);
//            info.callback.recordPlayerCompleteUI(position);
//            if (!isAutoPlay) return;
//            // 需要自动播放，判断下面是否有未播放的语音，如果有，则播放，没有则不执行
//            int autoPlayPosition = mAdapter.getNextVoicePostion(position);
//            VoiceContent autoPlayVoice = mAdapter.getVoiceContent(autoPlayPosition);
//            if (autoPlayVoice == null || UtilitySecurity.isEmpty(autoPlayVoice.voice)) return;
//            recorderPlayKit.play(autoPlayVoice.voice, autoPlayPosition, isAutoPlay);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 播放停止
     */
    @Override
    public void recorderPlayerStop(int position) {
//        if (position < 0 || mAdapter == null || UtilitySecurity.isEmpty(mAdapter.getItems())
//                || position >= mAdapter.getItems().size() || mAdapter.getItems().get(position) == null)
//            return;
//        try {
//            ConversationInfo info = (ConversationInfo) mAdapter.getItems().get(position);
//            info.callback.recordPlayerStopUI(position);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
    }

    /**
     * 开始播放
     */
    @Override
    public void recorderPlayerStart(int position, boolean isCache) {
//        if (position < 0 || mAdapter == null || UtilitySecurity.isEmpty(mAdapter.getItems())
//                || position >= mAdapter.getItems().size() || mAdapter.getItems().get(position) == null)
//            return;
//        try {
//            ConversationInfo info = (ConversationInfo) mAdapter.getItems().get(position);
//            info.callback.recordPlayerStartUI(position);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
    }

    /**
     * 缓存状态发生改变
     */
    @Override
    public void recorderPlayerCaching(int position, boolean isCache) {
//        if (position < 0 || mAdapter == null || UtilitySecurity.isEmpty(mAdapter.getItems())
//                || position >= mAdapter.getItems().size() || mAdapter.getItems().get(position) == null)
//            return;
//        try {
//            ConversationInfo info = (ConversationInfo) mAdapter.getItems().get(position);
//            info.callback.recordPlayerCachingUI(position, isCache);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
    }
}
