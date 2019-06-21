package com.niupuyue.mylibrary.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;

import com.niupuyue.mylibrary.R;


/**
 * 推送状态栏消息管理类
 */
public class NotificationUtility extends ContextWrapper {

    private NotificationManager notificationManager;
    private Notification.Builder notification;
    public static String NOTIFICATION_STATIC_ID = "1001";
    private static String NOTIFICATION_STATIC_NAME = "新消息通知";
    private static int NOTIFICATION_CHANNEL_ID = 0x100;

    private String title;
    private String content;
    private int icon;
    private PendingIntent pendingIntent;
    private int textColor;

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
     * @return true 大于26
     */
    private boolean checkSdkVersionMoreO() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
    }

    /**
     * 设置是否给出提示音和震动效果
     */
    private int setSoundAndVibrate() {
        int default_setting;
        // 根据设置决定是否给出提示音和震动效果 默认添加亮屏操作
        default_setting = Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS;
        return default_setting;
    }

    /**
     * 创建Notification对象，针对Android8.0以下
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void createPushNotificationAfter3(final String content, final String title, final int icon, final PendingIntent pendingIntent) {
        try {
            if (null == notificationManager) {
                notificationManager = (NotificationManager) LibraryConstants.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
            }
            notification = new Notification.Builder(LibraryConstants.getContext());
            notification.setSmallIcon(icon);
            notification.setLargeIcon(BitmapFactory.decodeResource(LibraryConstants.getContext().getResources(), icon));
            notification.setContentTitle(BaseUtility.isEmpty(title) ? LibraryConstants.getContext().getString(R.string.app_name) : title);
            notification.setContentText(content);
            notification.setPriority(Notification.PRIORITY_HIGH);
            notification.setWhen(System.currentTimeMillis());
            notification.setContentIntent(pendingIntent);
            notification.setAutoCancel(true);
            notification.setDefaults(setSoundAndVibrate());
            // 小米手机不要使用下面的方式设置，会直接执行intent
            if (AndroidUtility.PhoneType.Xiaomi != AndroidUtility.getPhoneType() && AndroidUtility.PhoneType.Meizu != AndroidUtility.getPhoneType()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    notification.setVisibility(Notification.VISIBILITY_PUBLIC);
                    // 关联PendingIntent
                    notification.setFullScreenIntent(pendingIntent, true);
                }
            }
            notificationManager.notify(NOTIFICATION_CHANNEL_ID, notification.build());
            // 如果是vivo手机，3秒之后关闭弹窗
            if (AndroidUtility.getPhoneType() == AndroidUtility.PhoneType.Vivo) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        notificationManager.cancel(NOTIFICATION_CHANNEL_ID);
                        notification = new Notification.Builder(LibraryConstants.getContext());
                        notification.setSmallIcon(icon);
                        notification.setLargeIcon(BitmapFactory.decodeResource(LibraryConstants.getContext().getResources(), icon));
                        notification.setContentTitle(BaseUtility.isEmpty(title) ? LibraryConstants.getContext().getString(R.string.app_name) : title);
                        notification.setContentText(content);
                        notification.setPriority(Notification.PRIORITY_HIGH);
                        notification.setWhen(System.currentTimeMillis());
                        notification.setContentIntent(pendingIntent);
                        notification.setAutoCancel(true);
                        notificationManager.notify(NOTIFICATION_CHANNEL_ID, notification.build());
                    }
                }, 3000);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 创建Notification对象，针对Android8.0以上
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createPushNotificationAfter8(String content, String title, int icon, PendingIntent pendingIntent) {
        try {
            if (null == notificationManager) {
                notificationManager = (NotificationManager) LibraryConstants.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
            }
            NotificationChannel mChannel = new NotificationChannel(NOTIFICATION_STATIC_ID, NOTIFICATION_STATIC_NAME, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
            notification = new Notification.Builder(LibraryConstants.getContext(), NOTIFICATION_STATIC_ID)
                    .setContentTitle(BaseUtility.isEmpty(title) ? LibraryConstants.getContext().getString(R.string.app_name) : title)
                    .setContentText(content)
                    .setSmallIcon(icon)
//                    .setColor()
                    .setWhen(System.currentTimeMillis())
                    .setAutoCancel(true)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setOngoing(false)
                    .setContentIntent(pendingIntent)
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
    private void showPushNotification(String content, String title, int icon, PendingIntent pendingIntent) {
        if (checkSdkVersionMoreO()) {
            createPushNotificationAfter8(content, title, icon, pendingIntent);
        } else {
            createPushNotificationAfter3(content, title, icon, pendingIntent);
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
     * 返回通知栏对象
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Notification getNotification() {
        if (null == notificationManager) {
            notificationManager = (NotificationManager) LibraryConstants.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        }
        if (notification != null) return notification.build();
        if (checkSdkVersionMoreO()) {
            NotificationChannel mChannel = new NotificationChannel(NOTIFICATION_STATIC_ID, NOTIFICATION_STATIC_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(mChannel);
            notification = new Notification.Builder(LibraryConstants.getContext(), NOTIFICATION_STATIC_ID)
                    .setContentTitle(LibraryConstants.getContext().getString(R.string.app_name))
                    .setContentText(null)
                    .setWhen(System.currentTimeMillis())
                    .setAutoCancel(true)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setOngoing(false)
                    .setDefaults(Notification.DEFAULT_ALL);
        } else {
            notification = new Notification.Builder(LibraryConstants.getContext())
                    .setSmallIcon(icon)
                    .setContentTitle(BaseUtility.isEmpty(title) ? LibraryConstants.getContext().getString(R.string.app_name) : title)
                    .setContentText(content)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setWhen(System.currentTimeMillis())
                    .setOngoing(false)
                    .setAutoCancel(true);
        }
        return notification.build();
    }

    /**
     * ///////////////////////////////////////////////////////匿名内部类构造单例模式 开始//////////////////////////////////////////////////////////////////
     */
    public static NotificationUtility getInstance() {
        return PushNotificationManagerHolder.instance;
    }

    private static class PushNotificationManagerHolder {
        private static final NotificationUtility instance = new NotificationUtility(LibraryConstants.getContext());
    }
    /**
     * ///////////////////////////////////////////////////////匿名内部类构造单例模式 结束//////////////////////////////////////////////////////////////////
     */

}
