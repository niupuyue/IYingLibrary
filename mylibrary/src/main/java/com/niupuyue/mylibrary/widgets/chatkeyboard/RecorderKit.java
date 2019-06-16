package com.niupuyue.mylibrary.widgets.chatkeyboard;

import android.media.MediaRecorder;
import android.os.Handler;
import android.os.HandlerThread;

import com.renrui.job.R;
import com.renrui.job.application.RRApplication;
import com.renrui.job.util.UtilityException;
import com.renrui.job.util.UtilityFile;
import com.renrui.job.widget.im.chatkeyboard.callbacks.IMRecorderKitCallback;

import java.io.File;
import java.io.IOException;

/**
 * Desc: 语音录制模块模块
 */
public class RecorderKit {
    // 输出的默认文件
    private File audioFile;
    // 语音录制对象
    private MediaRecorder mRecorder;
    private boolean isStart = false;
    private long mDuration = 0L;
    private static int SECOND_PEROID = 1000; // 录制最小的时间
    private static int RECORDER_SIMPLE_RATE = 8000; // 录制的频率
    private static int RECORDER_SIMPLE_BIG_RATE = 67000; // 录制最大频率
    private Handler mHandler;
    private final IMRecorderKitCallback mCallback;
    private final long mMaxRecordTime;
    private final long mMinRecordTime;
    private final long mVolumnPeriodTime;
    private static String IM_RECORDER_HANDLER = "ChattingRecorder";
    // 超过最大录制时间停止录音
    private Runnable mTimeOut = new Runnable() {
        public void run() {
            stopRecord();
        }
    };

    private Runnable mPeriod = new Runnable() {
        public void run() {
            try {
                if (mCallback != null) {
                    // 录音过程中每个1秒钟发送一次录制状态
                    mCallback.onProgress((int) (System.currentTimeMillis() - mDuration) / SECOND_PEROID);
                }
                // 每个固定时间执行一次检测音量
                mHandler.postDelayed(this, mVolumnPeriodTime);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };

    public RecorderKit(IMRecorderKitCallback callback, long maxTime, long minTime, long mPeriodTime) {
        HandlerThread thread = new HandlerThread(IM_RECORDER_HANDLER);
        thread.start();
        this.mHandler = new Handler(thread.getLooper());
        this.mCallback = callback;
        this.mMaxRecordTime = maxTime;
        this.mMinRecordTime = minTime;
        this.mVolumnPeriodTime = mPeriodTime;
    }

    /**
     * 开始录制操作
     */
    public void startRecorder() {
        if (this.mHandler == null) return;
        try {
            this.mHandler.post(new Runnable() {
                public void run() {
                    audioFile = UtilityFile.createAudioFile(UtilityFile.getFilePath());
                    if (audioFile == null) {
                        onError(RRApplication.getAppContext().getString(R.string.im_record_create_file_error));
                    } else if (!isStart) {
                        try {
                            if (mRecorder == null) {
                                mRecorder = new MediaRecorder();
                            }

                            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
                            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                            mRecorder.setAudioSamplingRate(RECORDER_SIMPLE_RATE);
                            mRecorder.setAudioEncodingBitRate(RECORDER_SIMPLE_BIG_RATE);
                            mRecorder.setOutputFile(audioFile.getAbsolutePath());
                            mRecorder.prepare();
                        } catch (IllegalStateException var6) {
                            if (mRecorder != null) {
                                mRecorder.reset();
                                mRecorder.release();
                                mRecorder = null;
                            }

                            var6.printStackTrace();
                            onError();
                            return;
                        } catch (IOException var7) {
                            if (mRecorder != null) {
                                mRecorder.reset();
                                mRecorder.release();
                                mRecorder = null;
                            }

                            var7.printStackTrace();
                            onError();
                            return;
                        } catch (RuntimeException var8) {
                            try {
                                if (mRecorder != null) {
                                    mRecorder.reset();
                                    mRecorder.release();
                                }
                            } catch (RuntimeException var4) {

                            }

                            mRecorder = null;
                            var8.printStackTrace();
                            onError();
                            return;
                        }

                        try {
                            if (mRecorder != null) {
                                mDuration = System.currentTimeMillis();
                                mRecorder.start();
                            }
                        } catch (RuntimeException var5) {
                            try {
                                if (mRecorder != null) {
                                    mRecorder.reset();
                                    mRecorder.release();
                                }
                            } catch (RuntimeException var3) {

                            }

                            mRecorder = null;
                            onError();
                            return;
                        }

                        isStart = true;
                    }
                }
            });
            this.mHandler.postDelayed(mTimeOut, mMaxRecordTime);
            this.mHandler.post(this.mPeriod);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void stop() {
        try {
            this.mHandler.post(new Runnable() {
                public void run() {
                    stopRecord();
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void cancel() {
        if (!this.isStart) return;
        try {
            this.mHandler.post(new Runnable() {
                public void run() {
                    mHandler.removeCallbacks(mTimeOut);
                    mHandler.removeCallbacks(mPeriod);
                    if (isStart) {
                        if (mRecorder != null) {
                            try {
                                mRecorder.stop();
                            } catch (RuntimeException var2) {
                                var2.printStackTrace();
                            }

                            mRecorder.release();
                            mRecorder = null;
                        }

                        isStart = false;
                        deleteFile();
                    }
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void stopRecord() {
        if (!this.isStart) return;
        try {
            this.mHandler.removeCallbacks(this.mTimeOut);
            this.mHandler.removeCallbacks(this.mPeriod);
            if (this.mRecorder != null) {
                try {
                    this.mRecorder.stop();
                } catch (RuntimeException var3) {
                    var3.printStackTrace();
                }

                this.mRecorder.release();
                this.mRecorder = null;
            }

            this.isStart = false;
            long duration = System.currentTimeMillis() - this.mDuration;
            if (duration < this.mMinRecordTime) {
                this.onError(RRApplication.getAppContext().getString(R.string.im_record_time_short));
            } else if (this.audioFile == null) {
                this.onError(RRApplication.getAppContext().getString(R.string.im_record_create_file_error));
            } else if (this.mCallback != null) {
                this.mCallback.onSuccess(new Object[]{this.audioFile.getAbsolutePath(), (int) (duration / SECOND_PEROID)});
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /**
     * 回收资源 会将looper清除
     */
    public void recycle() {
        if (this.mHandler == null) return;
        try {
            this.mHandler.post(new Runnable() {
                public void run() {
                    if (mHandler != null) {
                        mHandler.getLooper().quit();
                        mHandler = null;
                    }

                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /**
     * 设置音量
     *
     * @return
     */
    public int getAmplitude() {
        if (!this.isStart || this.mRecorder == null) return 0;
        try {
            return this.mRecorder.getMaxAmplitude();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    /**
     * 删除录音文件
     */
    private void deleteFile() {
        if (this.audioFile == null) return;
        try {
            this.audioFile.delete();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void onError() {
        try {
            deleteFile();
            if (this.mCallback != null) {
                this.mCallback.onError(0, RRApplication.getAppContext().getString(R.string.im_record_error));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void onError(String info) {
        try {
            deleteFile();
            if (this.mCallback != null) {
                this.mCallback.onError(0, info);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
