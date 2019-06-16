package com.niupuyue.mylibrary.widgets.chatkeyboard;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.PowerManager;

import com.danikula.videocache.CacheListener;
import com.danikula.videocache.HttpProxyCacheServer;
import com.renrui.job.R;
import com.renrui.job.util.UtilityException;
import com.renrui.job.widget.im.chatkeyboard.callbacks.IMRecordPlayerCallback;
import com.renrui.job.widget.im.chatkeyboard.utils.UtilityRecordCache;
import com.renrui.libraries.util.CustomToast;
import com.renrui.libraries.util.LibUtility;
import com.renrui.libraries.util.Logger;
import com.renrui.libraries.util.UtilitySecurity;

import java.io.File;

/**
 * Desc: 语音播放模块
 */
public class RecorderPlayKit implements SensorEventListener {

    private MediaPlayer mediaPlayer;
    private AudioManager mAudioManager;
    private Sensor mSensor;
    private PowerManager.WakeLock mWakeLock;
    private PowerManager mPowerManager;
    private SensorManager sensorManager;


    private IMRecordPlayerCallback callback;
    private String currPath;
    private HttpProxyCacheServer proxy;
    private int currPosition;
    private boolean isAutoPlay;

    private static RecorderPlayKit instance;

    private static int CACHE_PRECENTS = 100;

    private static String RECORD_PATH_HTTP = "http://";
    private static String RECORD_PATH_HTTPS = "https://";

    public static RecorderPlayKit getInstance(Context context) {
        if (instance == null) {
            instance = new RecorderPlayKit(context);
        }
        return instance;
    }

    MediaPlayer.OnCompletionListener mediaPlayCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            // 语音播放完成回调
            if (mAudioManager != null)
                mAudioManager.abandonAudioFocus(null);// 关闭audio focus
            if (callback != null) {
                callback.recorderPlayerComplete(currPosition, isAutoPlay);
            }
            stopListenSenser();
        }
    };

    private void initRecordManagers(Context context) {
        try {
            if (mediaPlayer == null) {
                mediaPlayer = new MediaPlayer();
            }

            if (this.mAudioManager == null) {
                this.mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            }

            if (sensorManager == null) {
                sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
            }

            if (mSensor == null) {
                mSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
            }

            if (mPowerManager == null) {
                //息屏设置
                mPowerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            }

            if (mWakeLock == null) {
                mWakeLock = mPowerManager.newWakeLock(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK,
                        PowerManager.WakeLock.class.getSimpleName());
            }

            if (proxy == null) {
                proxy = UtilityRecordCache.getInstance().getProxy();
            }

        } catch (Exception ex) {
            UtilityException.catchException(ex);
        }
    }

    private RecorderPlayKit(Context context) {
        try {
            initRecordManagers(context);
            if (mediaPlayer == null) return;
            mediaPlayer.setOnCompletionListener(mediaPlayCompletionListener);
        } catch (Exception ex) {
            UtilityException.catchException(ex);
        }
    }

    /**
     * 设置语音播放回调
     *
     * @param callback
     */
    public void setCallback(IMRecordPlayerCallback callback) {
        this.callback = callback;
    }

    /**
     * 播放语音
     *
     * @param path       播放语音的路径 可能是本地路径
     * @param position   播放语音在列表中的位置
     * @param isAutoPlay 是否允许自动播放下一条
     */
    public void play(String path, final int position, boolean isAutoPlay) {
        if (position < 0) return;
        if (UtilitySecurity.isEmpty(path) || mAudioManager == null || mediaPlayer == null) {
            CustomToast.makeTextError(R.string.im_chat_play_record_error);
            return;
        }
        try {
            this.isAutoPlay = isAutoPlay;
            // 需要判断当前页面播放的内容和需要播放的内容是否相等，如果相等，则停止播放，如果不等，则停止之前的播放，在播放新内容
            if (isPlaying()) {
                if (mAudioManager != null) {
                    mAudioManager.abandonAudioFocus(null);// 关闭audio focus
                }
                if (!UtilitySecurity.equals(currPath, path)) {
                    // 再次点击的语音和正在播放的语音不是同一个，则将正在播放的语音停止，播放新语音
                    stop(false);
                    mediaPlayer.reset();
                    play(path, position, isAutoPlay);
                } else {
                    stop(true);
                    stopListenSenser();
                }
            } else {
                // 当前不处于播放状态
                mediaPlayer.reset();
                if (proxy == null) return;
                if (path.contains(RECORD_PATH_HTTP) || path.contains(RECORD_PATH_HTTPS)) {
                    playNetRecord(path, position, isAutoPlay);
                } else {
                    playLocalRecord(path, position);
                }
            }
        } catch (Exception ex) {
            UtilityException.catchException(ex);
            this.mediaPlayer = null;
            CustomToast.makeTextError(R.string.im_chat_play_record_error);
        }
        this.currPath = path;
        this.currPosition = position;
    }

    /**
     * 播放网络语音
     *
     * @param path
     * @param position
     * @param isAutoPlay
     */
    private void playNetRecord(String path, final int position, boolean isAutoPlay) {
        // 网络路径
        try {
            proxy.registerCacheListener(new CacheListener() {
                @Override
                public void onCacheAvailable(File cacheFile, String url, int percentsAvailable) {
                    if (callback == null) return;
                    if (percentsAvailable < CACHE_PRECENTS) {
                        callback.recorderPlayerCaching(position, false);
                    } else {
                        callback.recorderPlayerCaching(position, true);
                    }
                    Logger.e("NPL", "缓存百分比=" + percentsAvailable);
                }
            }, path);

            // 当前多媒体是否被占用，如果被占用，则请求获取焦点
            if (mAudioManager.isMusicActive())
                mAudioManager.requestAudioFocus(null, AudioManager.STREAM_VOICE_CALL, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

            boolean isCache = proxy.isCached(path);
            callback.recorderPlayerCaching(position, isCache);
            mediaPlayer.setDataSource(proxy.getProxyUrl(path));
            mediaPlayer.prepareAsync();
            final boolean finalIsCache = isCache;
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.start();
                    if (callback == null) return;
                    callback.recorderPlayerStart(position, !finalIsCache);
                    // 开始播放，监听屏幕变化
                    startListenSenser();
                }
            });
        } catch (Exception ex) {
            UtilityException.catchException(ex);
        }
    }

    /**
     * 播放本地语音
     *
     * @param path
     * @param position
     */
    private void playLocalRecord(String path, final int position) {
        // 本地路径
        try {
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.start();
                    if (callback == null) return;
                    callback.recorderPlayerStart(position, false);
                    // 开始播放，监听屏幕变化
                    startListenSenser();
                }
            });
        } catch (Exception ex) {
            UtilityException.catchException(ex);
        }
    }

    /**
     * 注册监听距离
     */
    private void startListenSenser() {
        if (mSensor == null || sensorManager == null) return;
        try {
            sensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
        } catch (Exception ex) {
            UtilityException.catchException(ex);
        }
    }

    /**
     * 注销监听距离
     */
    private void stopListenSenser() {
        if (sensorManager == null || mWakeLock == null) return;
        try {
            //传感器取消监听
            sensorManager.unregisterListener(this);
            //释放息屏
            if (mWakeLock != null && mWakeLock.isHeld())
                mWakeLock.release();
        } catch (Exception ex) {
            UtilityException.catchException(ex);
        }
    }

    /**
     * 停止播放语音
     * <p>
     * 1.当第一次点击了语音，则需要查找下面的消息中是否存在未读消息，如果存在，则自动播放下一个未读语音
     * 2.如果语音在播放的过程中，只要再次点击了语音，只有暂停或者播放已读的语音，才会停止自动播放下一条未读语音，否则依然自动播放下一条
     * </p>
     *
     * @param isStopAutoPlay 是否需要停止自动播放
     */
    public void stop(boolean isStopAutoPlay) {
        if (this.mediaPlayer == null || callback == null) return;
        try {
            this.isAutoPlay = !isStopAutoPlay;
            callback.recorderPlayerStop(currPosition);
            this.mediaPlayer.stop();
        } catch (Exception ex) {
            UtilityException.catchException(ex);
        }
    }

    /**
     * 暂停播放
     */
    public void pause() {
        if (this.mediaPlayer == null) return;
        try {
            this.mediaPlayer.pause();
        } catch (Exception ex) {
            UtilityException.catchException(ex);
        }
    }

    /**
     * 判断是否正在播放状态
     *
     * @return
     */
    public boolean isPlaying() {
        try {
            return this.mediaPlayer != null && this.mediaPlayer.isPlaying();
        } catch (Exception ex) {
            UtilityException.catchException(ex);
        }
        return false;
    }

    /**
     * 回收播放资源 不建议直接回收，因为可以随时会播放语音
     */
    public void recycle() {
        if (this.mediaPlayer == null || !this.mediaPlayer.isPlaying() || callback == null) return;
        try {
            callback.recorderPlayerStop(currPosition);
            this.mediaPlayer.stop();
            this.mediaPlayer.release();
        } catch (Exception ex) {
            UtilityException.catchException(ex);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (mAudioManager == null || mWakeLock == null) return;
        try {
            if (event.values[0] == 0.0) {
                //贴近手机
                //设置免提
                mAudioManager.setSpeakerphoneOn(false);
                mAudioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
                //关闭屏幕
                if (!mWakeLock.isHeld())
                    mWakeLock.acquire();
            } else {
                //离开手机
                mAudioManager.setMode(AudioManager.MODE_NORMAL);
                //设置免提
                mAudioManager.setSpeakerphoneOn(true);
                //唤醒设备
                if (mWakeLock.isHeld())
                    mWakeLock.release();
            }
        } catch (Exception ex) {
            UtilityException.catchException(ex);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /**
     * 根据语音时长设置语音消息的宽度
     * 规则：最短宽度是90，最常宽度是270
     */
    private static int RECORD_MIN_LENGTH = 60;
    private static int RECORD_LENGTH_DOUBLE = 2;

    public static int calculAudioMessageWidth(int duration) {
        return LibUtility.dp2px(RECORD_MIN_LENGTH + duration * RECORD_LENGTH_DOUBLE);
    }
}
