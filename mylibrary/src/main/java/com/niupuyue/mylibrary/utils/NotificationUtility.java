package com.niupuyue.mylibrary.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;


/**
 * 推送状态栏消息管理类
 */
public class NotificationUtility extends ContextWrapper {

    private static Context mContext;
    private NotificationManager notificationManager;
    private Notification.Builder notification;

    public static String NOTIFICATION_STATIC_ID = "1001";
    private static String NOTIFICATION_STATIC_NAME = "新消息通知";
    private static int NOTIFICATION_CHANNEL_ID = 0x100;

    private NotificationUtility(Context context) {
        super(context);
        // 在构造方法中，声明NotificationManager对象
        if (null == notificationManager && null != context) {
            notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
    }

    /**
     * 判断当前SDK是否大于26
     *
     * @return
     */
    private boolean checkSdkVersionMoreO() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
    }

    /**
     * 创建Notification对象，针对Android8.0以下
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void createPushNotificationAfter3(String title, String content, Intent intent, int icon) {
        if (intent == null) return;
        try {
            if (null == notificationManager) {
                notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
            }
            notification = new Notification.Builder(mContext)
                    .setSmallIcon(icon)
                    .setContentTitle(title)
                    .setContentText(content)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setWhen(System.currentTimeMillis())
                    .setContentIntent(initJumpAction(intent))
                    .setOngoing(false)
                    .setAutoCancel(true);
            int default_setting = 0;
            // 根据设置决定是否给出提示音和震动效果
//            if (EditSharedPreferences.getNotifySound() && EditSharedPreferences.getNotifyVibrator()) {
//                default_setting = Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE;
//            } else if (EditSharedPreferences.getNotifySound() && !EditSharedPreferences.getNotifyVibrator()) {
//                default_setting = Notification.DEFAULT_SOUND;
//            } else if (EditSharedPreferences.getNotifyVibrator() && !EditSharedPreferences.getNotifySound()) {
//                default_setting = Notification.DEFAULT_VIBRATE;
//            } else if (!EditSharedPreferences.getNotifySound() && !EditSharedPreferences.getNotifyVibrator()) {
//                default_setting = 0;
//            }
            notification.setDefaults(default_setting);
            notificationManager.notify(NOTIFICATION_CHANNEL_ID, notification.build());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 创建Notification对象，针对Android8.0以上
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createPushNotificationAfter8(String title, String content, Intent intent, int icon) {
        if (intent == null) return;
        try {
            if (null == notificationManager) {
                notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
            }
            NotificationChannel mChannel = new NotificationChannel(NOTIFICATION_STATIC_ID, NOTIFICATION_STATIC_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(mChannel);
            notification = new Notification.Builder(mContext, NOTIFICATION_STATIC_ID)
                    .setContentTitle(title)
                    .setContentText(content)
                    .setSmallIcon(icon)
                    .setWhen(System.currentTimeMillis())
                    .setAutoCancel(true)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setOngoing(false)
                    .setContentIntent(initJumpAction(intent))
                    .setDefaults(Notification.DEFAULT_ALL);
            //发送
            notificationManager.notify(NOTIFICATION_CHANNEL_ID, notification.build());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 显示Notification对象
     *
     * @param content 通知栏中的content
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void showPushNotification(String title, String content, Intent intent, int icon) {
        if (checkSdkVersionMoreO()) {
            createPushNotificationAfter8(title, content, intent, icon);
        } else {
            createPushNotificationAfter3(title, content, intent, icon);
        }
    }

    /**
     * 清除Notification对象
     */
    public void clearPushNotification() {
        if (notificationManager != null) {
            notificationManager.cancel(NOTIFICATION_CHANNEL_ID);
        }
    }

    /**
     * 初始化跳转PendingIntent
     */
    private PendingIntent initJumpAction(Intent intent) {
        if (intent == null) return null;
        try {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);           //添加为栈顶Activity
            PendingIntent resultPendingIntent = PendingIntent.getActivity(mContext, -1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            return resultPendingIntent;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 添加通知栏消息 这里可以对标题和内容进行格式化
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void addPushNotification(String title, String content, Intent intent, int icon) {
        showPushNotification(title, content, intent, icon);
    }

    /**
     * ///////////////////////////////////////////////////////匿名内部类构造单例模式 开始//////////////////////////////////////////////////////////////////
     */
    public static NotificationUtility getInstance(Context context) {
        mContext = context;
        return PushNotificationManagerHolder.instance;
    }

    private static class PushNotificationManagerHolder {
        private static final NotificationUtility instance = new NotificationUtility(mContext);
    }
    /**
     * ///////////////////////////////////////////////////////匿名内部类构造单例模式 结束//////////////////////////////////////////////////////////////////
     */
}
