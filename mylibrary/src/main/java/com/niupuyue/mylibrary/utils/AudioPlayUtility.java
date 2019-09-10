package com.niupuyue.mylibrary.utils;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.PowerManager;

import com.niupuyue.mylibrary.callbacks.IAudioPlayCallback;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019-07-08
 * Time: 23:04
 * Desc: 语音播放管理类
 * Version:
 */
public class AudioPlayUtility implements SensorEventListener {

    MediaPlayer.OnCompletionListener mediaPlayCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            // 语音播放完成回调
            if (mAudioManager != null)
                mAudioManager.abandonAudioFocus(null);// 关闭audio focus
            if (mPlayCallback != null) {
                mPlayCallback.onAudioPlayComplete();
            }
            stopListenSenser();
        }
    };

    private MediaPlayer mediaPlayer;
    private AudioManager mAudioManager;
    private Sensor mSensor;
    private PowerManager.WakeLock mWakeLock;
    private PowerManager mPowerManager;
    private SensorManager sensorManager;

    private IAudioPlayCallback mPlayCallback;

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

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private AudioPlayUtility(Context context) {
        try {
            initRecordManagers(context);
            if (mediaPlayer == null) return;
            mediaPlayer.setOnCompletionListener(mediaPlayCompletionListener);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 设置语音播放回调
     *
     * @param callback
     */
    public void setCallback(IAudioPlayCallback callback) {
        this.mPlayCallback = callback;
    }

    /**
     * 注册监听距离
     */
    private void startListenSenser() {
        if (mSensor == null || sensorManager == null) return;
        try {
            sensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
        } catch (Exception ex) {
            ex.printStackTrace();
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
            ex.printStackTrace();
        }
    }

    public void play(String path) {
        if (BaseUtility.isEmpty(path) || mAudioManager == null || mediaPlayer == null) return;
        try {
            if (isPlaying()) {
                // 正在播放中，后面需要两种操作，第一中是停止播放，另一种是播放另一个音频文件

            } else {
                // 重置多媒体播放
                mediaPlayer.reset();
                // 当前多媒体是否被占用，如果被占用，则请求获取焦点
                if (mAudioManager.isMusicActive())
                    mAudioManager.requestAudioFocus(null, AudioManager.STREAM_VOICE_CALL, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                mediaPlayer.setDataSource(path);
                mediaPlayer.prepareAsync();
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        mediaPlayer.start();
                        if (mPlayCallback == null) return;
                        mPlayCallback.onAudioPlayStart();
                        // 开始播放，监听屏幕变化
                        startListenSenser();
                    }
                });

                mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                    @Override
                    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                        return false;
                    }
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
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
        if (this.mediaPlayer == null || mPlayCallback == null) return;
        try {
            mPlayCallback.onAudioPlayStop();
            this.mediaPlayer.stop();
        } catch (Exception ex) {
            ex.printStackTrace();
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
            ex.printStackTrace();
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
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * 回收播放资源 不建议直接回收，因为可以随时会播放语音
     */
    public void recycle() {
        if (this.mediaPlayer == null || !this.mediaPlayer.isPlaying() || mPlayCallback == null)
            return;
        try {
            mPlayCallback.onAudioPlayStop();
            this.mediaPlayer.stop();
            this.mediaPlayer.release();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (mAudioManager == null || mWakeLock == null) return;
        try {
            if (sensorEvent.values[0] == 0.0) {
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
            ex.printStackTrace();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
