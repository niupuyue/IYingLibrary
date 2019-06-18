package com.niupuyue.mylibrary.widgets.chatkeyboard.fragments;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.niupuyue.mylibrary.R;
import com.niupuyue.mylibrary.utils.BaseUtility;
import com.niupuyue.mylibrary.utils.CustomToastUtility;
import com.niupuyue.mylibrary.utils.ListenerUtility;
import com.niupuyue.mylibrary.widgets.chatkeyboard.RecorderKit;
import com.niupuyue.mylibrary.widgets.chatkeyboard.callbacks.IMChatSendMessageCallback;
import com.niupuyue.mylibrary.widgets.chatkeyboard.callbacks.IMRecorderKitCallback;
import com.niupuyue.mylibrary.widgets.chatkeyboard.callbacks.IMRecordingStateChangeCallback;
import com.niupuyue.mylibrary.widgets.chatkeyboard.eventModel.OnSendVoiceEvent;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Desc: 发送语音页面
 */
public class IMChatKeyboardRecorderFragment extends BaseIMFragment implements
        View.OnTouchListener {

    public static IMChatKeyboardRecorderFragment getInstance() {
        IMChatKeyboardRecorderFragment chattingIMChatKeyboardRecorderFragment = new IMChatKeyboardRecorderFragment();
        Bundle bundle = new Bundle();
        chattingIMChatKeyboardRecorderFragment.setArguments(bundle);
        return chattingIMChatKeyboardRecorderFragment;
    }

    public static int LIMIT_TIME = 60;
    public static int BEGIN_TIME = 51;

    private static long SET_VOICE_IMAGE_PEER_TIME = 150L;

    private boolean isCanCount = true;
    private boolean isCanEndCount = true;
    private boolean hasCounting = false;
    private int amplitude = 0;
    private float startY = 0;
    private float endY = 0;
    private long startTime = 0;
    private long endTime = 0;

    private ImageView ivRfVoice;
    private TextView tvRfVoice;
    private View root;

    // 计时操作，当到了50秒时开始倒计时
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case TIME_TAG:
                    startCountTime();
                    break;
            }
        }
    };

    // 发送语音的回调，onSuccess是语音成功，onProgress是录音过程中实时调用
    private IMRecorderKitCallback iWxCallback = new IMRecorderKitCallback() {
        @Override
        public void onSuccess(final Object... result) {
            // 成功之后的操作
            if (result == null || result.length != 2) return;
            try {
                handler.post(new Runnable() {
                    public void run() {
                        // 创建AudioMessage
                        createAudioMessage((String) result[0], (Integer) result[1]);
                    }
                });
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void onError(int i, String s) {
            // 失败
            CustomToastUtility.makeTextError(getContext(), "");
        }

        @Override
        public void onProgress(int i) {
            // 改变音量图片
            try {
                handler.post(recordImageRefresh);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };

    /**
     * 监听音量变化
     */
    private Runnable recordImageRefresh = new Runnable() {
        public void run() {
            amplitude = 0;
            try {
                if (ChatRecorderKit != null) {
                    amplitude = ChatRecorderKit.getAmplitude();
                }
                setHoldToSpeakImage();
                handler.postDelayed(recordImageFakeRefresh, SET_VOICE_IMAGE_PEER_TIME);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };

    /**
     * 修改音量图片
     */
    private Runnable recordImageFakeRefresh = new Runnable() {
        public void run() {
            // 将实际音量进行处理之后再去使用
            try {
                amplitude = (int) ((double) amplitude * 0.8D);
                setHoldToSpeakImage();
                if (amplitude > 0) {
                    handler.postDelayed(recordImageFakeRefresh, SET_VOICE_IMAGE_PEER_TIME);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    };

    private IMChatSendMessageCallback mListener;
    private IMRecordingStateChangeCallback stateChangeCallback;
    // 发送语音消息的标识
    public final static int TIME_TAG = 0x111;
    // 计时器
    private int timeCount = 0;
    // 计时器
    private Timer mTimer;
    private TimerTask timerTasks;
    // 语音模块
    private RecorderKit ChatRecorderKit;
    // 是否可以移动取消
    private boolean isMoveToCancel = true;
    // 当前是否是发送状态
    private boolean isSending = false;

    /**
     * 音量的等级
     */
    public static final int VOICE_NUM_MAX = 32767;
    public static final int VOICE_NUM_LEVEL_1 = 500;
    public static final int VOICE_NUM_LEVEL_2 = 2000;
    public static final int VOICE_NUM_LEVEL_3 = 12000;
    public static final int VOICE_NUM_LEVEL_4 = 29000;

    /**
     * 根据音量等级设置图片
     */
    private void setHoldToSpeakImage() {
        if (amplitude > VOICE_NUM_MAX) {
            amplitude = VOICE_NUM_MAX;
        }
        OnSendVoiceEvent event = new OnSendVoiceEvent();
        if (amplitude >= 0 && amplitude < VOICE_NUM_LEVEL_1) {
            event.status = OnSendVoiceEvent.TYPE_CHANGE_VOICE;
            event.showImageType = OnSendVoiceEvent.SHOW_IMAGE_TYPE_1;
        } else if (amplitude >= VOICE_NUM_LEVEL_1 && amplitude < VOICE_NUM_LEVEL_2) {
            event.status = OnSendVoiceEvent.TYPE_CHANGE_VOICE;
            event.showImageType = OnSendVoiceEvent.SHOW_IMAGE_TYPE_2;
        } else if (amplitude >= VOICE_NUM_LEVEL_2 && amplitude < VOICE_NUM_LEVEL_3) {
            event.status = OnSendVoiceEvent.TYPE_CHANGE_VOICE;
            event.showImageType = OnSendVoiceEvent.SHOW_IMAGE_TYPE_3;
        } else if (amplitude >= VOICE_NUM_LEVEL_3 && amplitude < VOICE_NUM_LEVEL_4) {
            event.status = OnSendVoiceEvent.TYPE_CHANGE_VOICE;
            event.showImageType = OnSendVoiceEvent.SHOW_IMAGE_TYPE_4;
        } else if (amplitude >= VOICE_NUM_LEVEL_4) {
            event.status = OnSendVoiceEvent.TYPE_CHANGE_VOICE;
            event.showImageType = OnSendVoiceEvent.SHOW_IMAGE_TYPE_5;
        }
        if (stateChangeCallback == null) return;
        stateChangeCallback.changeRecorderState(event);
    }

    @Override
    public int getIMLayoutId() {
        return R.layout.fragment_recorder;
    }

    @Override
    public void initViewFindViewById(View root) {
        try {
            ivRfVoice = root.findViewById(R.id.ivFrVoice);
            tvRfVoice = root.findViewById(R.id.tvFrVoice);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void initViewListener() {
        ListenerUtility.setOnTouchListener(ivRfVoice, this);
    }

    @Override
    public void initDataAfterInitLayout() {
        initRecord();
    }

    private static final long MAX_RECORD_TIME = 60000L;
    private static final long PERIOD_RECORD_TIME = 500L;
    private static final int RECORD_MOVE_OFFSET = 200;

    /**
     * 初始化语音
     */
    private void initRecord() {
        try {
            if (ChatRecorderKit == null) {
                ChatRecorderKit = new RecorderKit(iWxCallback, MAX_RECORD_TIME, UtilityTime.lSecondTimes, PERIOD_RECORD_TIME);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setData(IMChatSendMessageCallback listener, IMRecordingStateChangeCallback stateChangeCallback) {
        this.mListener = listener;
        this.stateChangeCallback = stateChangeCallback;
    }

    /**
     * 创建语音消息对象
     *
     * @param audioPath 本地缓存语音路径
     * @param duration  语音时长
     */
    private void createAudioMessage(String audioPath, int duration) {
        try {
            File file = new File(audioPath);
            if (file == null || file.length() == 0) {
                return;
            }

            mListener.sendVoiceMessage("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 发送语音计时操作
     */
    private void startCountTime() {
        if (timeCount == LIMIT_TIME && ChatRecorderKit != null && isCanEndCount) {
            // 超过1分钟，直接取消
            moreOneMinToCancel();
        } else if (timeCount >= BEGIN_TIME && ChatRecorderKit != null && timeCount < LIMIT_TIME) {
            // 时间大于50秒时开始计时 开始倒计时只需要触发一次即可
            beginToCount();
        } else {
            timeCount++;
        }
    }

    private void moreOneMinToCancel() {
        try {
            ChatRecorderKit.stop();
            OnSendVoiceEvent moreLimitEvent = new OnSendVoiceEvent();
            moreLimitEvent.status = OnSendVoiceEvent.TYPE_STOP;
            if (stateChangeCallback != null) {
                stateChangeCallback.changeRecorderState(moreLimitEvent);
            }
            isCanEndCount = false;
            hasCounting = false;
            isSending = false;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 开始倒计时
     */
    private void beginToCount() {
        try {
            OnSendVoiceEvent sendStateEvent = new OnSendVoiceEvent();
            sendStateEvent.status = OnSendVoiceEvent.TYPE_COUNT;
            sendStateEvent.count = LIMIT_TIME - timeCount;
            sendStateEvent.isMoreLimit = isMoreLimit;
            if (stateChangeCallback != null) {
                stateChangeCallback.changeRecorderState(sendStateEvent);
            }
            isCanCount = false;
            hasCounting = true;
            timeCount++;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 开始发送语音
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void startRecord() {
        BaseUtility.setImageResource(ivRfVoice, R.drawable.icon_im_recoder_press);
        startRecordOperation();
    }

    private void startRecordOperation() {
        if (ChatRecorderKit == null || isSending) return;
        try {
            ChatRecorderKit.startRecorder();

            // 开始发送语音事件
            OnSendVoiceEvent startEvent = new OnSendVoiceEvent();
            startEvent.status = OnSendVoiceEvent.TYPE_SENDING;
            if (stateChangeCallback != null) {
                stateChangeCallback.changeRecorderState(startEvent);
            }
            // 语音开始发送，启动一个计时器
            if (mTimer == null) {
                mTimer = new Timer();
            }
            if (timerTasks == null) {
                timerTasks = new TimerTask() {
                    @Override
                    public void run() {
                        // 通过计时器发送handler消息
                        handler.sendEmptyMessage(TIME_TAG);
                    }
                };
            }
            // 开始执行倒计时
            mTimer.schedule(timerTasks, 0, UtilityTime.lSecondTimes);
            isSending = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 发送语音结束
     *
     * @param instanceY
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void endRecord(int instanceY) {
        if (ChatRecorderKit == null) return;
        try {
            // 修改图片(此处因为我们给图片添加了onTouchEvent事件所以无法再布局文件中修改)
            BaseUtility.setImageResource(ivRfVoice, R.drawable.icon_im_recoder_normal);
            // 获取手指抬起的时间
            endTime = System.currentTimeMillis();
            // 获取移动的垂直距离
            instanceY = (int) Math.abs((endY - startY));
            if (endTime - startTime < UtilityTime.lSecondTimes) {
                // 如果时间间隔小于1秒，则取消发送
                ChatRecorderKit.cancel();
            } else if (instanceY > RECORD_MOVE_OFFSET) {
                // 移动距离超过200，执行取消发送操作
                ChatRecorderKit.cancel();
                // 关闭所有效果
                cancelRecord();
            } else {
                // 正常发送消息
                ChatRecorderKit.stop();
            }

            // 消息发送完成，设置图片不可见
            OnSendVoiceEvent lessEvent = new OnSendVoiceEvent();
            lessEvent.status = OnSendVoiceEvent.TYPE_STOP;
            if (stateChangeCallback != null) {
                stateChangeCallback.changeRecorderState(lessEvent);
            }

            // 此处为了保证每次触摸发送语音都能正常使用，在一次发送语音之后将对象置为null
            if (mTimer != null) {
                mTimer.cancel();
                mTimer = null;
            }
            if (timerTasks != null) {
                timerTasks.cancel();
                timerTasks = null;
            }
            timeCount = 0;  // 计数器计数
            isCanCount = true;  // 是否可以再次计数
            isMoveToCancel = true;  // 是否可以通过移动取消发送
            isCanEndCount = true;  // 是否可以结束计数
            hasCounting = false;   // 是否已经在计数
            isSending = false;    // 是否正在发送
            isMoreLimit = false;   // 滑动距离是否超过200
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 移动事件
     *
     * @param event
     */
    private void moveRecord(MotionEvent event) {
        // 移动距离超过200则需要触发取消发送消息
        // 触发取消发送消息在一套操作中只需要触发一次，当手指松开的时候，还原状态
        // 滑动距离超过200之后，又返回到原来的位置，还原原有的样式
        if (!isSending) return;
        try {
            if (isMoveToCancel && (int) Math.abs((event.getY() - startY)) > RECORD_MOVE_OFFSET) {
                cancelRecord();
                isMoveToCancel = false;
                isMoreLimit = true;
            }
            if (!isMoveToCancel && (int) Math.abs((event.getY() - startY)) < RECORD_MOVE_OFFSET) {
                rebackSendingRecordState();
                isMoveToCancel = true;
                isMoreLimit = false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 取消发送语音
     */
    private void cancelRecord() {
        // 移动距离大于200，只需要提示，不需要直接取消操作
        if (stateChangeCallback == null) return;
        try {
            OnSendVoiceEvent cancelEvent = new OnSendVoiceEvent();
            cancelEvent.status = OnSendVoiceEvent.TYPE_CANCEL;
            stateChangeCallback.changeRecorderState(cancelEvent);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private boolean isMoreLimit = false;

    /**
     * 移动距离超过200之后，返回原来位置
     */
    private void rebackSendingRecordState() {
        // 移动距离大于200，只需要提示，不需要直接取消操作
        if (hasCounting) {
            // 如果已经开始计时，则不要切换到sending状态，该切换到counting状态
            OnSendVoiceEvent rebackCounting = new OnSendVoiceEvent();
            rebackCounting.status = OnSendVoiceEvent.TYPE_REBACK_COUNTING;
            if (stateChangeCallback == null) return;
            stateChangeCallback.changeRecorderState(rebackCounting);
        } else {
            OnSendVoiceEvent rebackEvent = new OnSendVoiceEvent();
            rebackEvent.status = OnSendVoiceEvent.TYPE_SENDING;
            if (stateChangeCallback == null) return;
            stateChangeCallback.changeRecorderState(rebackEvent);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int instanceY = 0;
        if (v.getId() == R.id.ivFrVoice) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    startTime = System.currentTimeMillis();
                    startY = event.getY();
                    startRecord();
                    break;
                case MotionEvent.ACTION_UP:
                    endY = event.getY();
                    endRecord(instanceY);
                    break;
                case MotionEvent.ACTION_MOVE:
                    moveRecord(event);
                    break;
            }
            return true;
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onPause() {
        super.onPause();
        // 当前页面关闭时，务必保证当前语音模块被回收，并置为null
        if (ChatRecorderKit == null) return;
        try {
            ChatRecorderKit.stop();
            endRecord(0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 当前页面关闭时，务必保证当前语音模块被回收，并置为null
        if (ChatRecorderKit == null) return;
        try {
            ChatRecorderKit.recycle();
            ChatRecorderKit = null;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
