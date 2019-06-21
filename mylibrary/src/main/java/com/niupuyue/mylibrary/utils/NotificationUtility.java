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
import com.niupuyue.mylibrary.model.NotificationModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * 推送状态栏消息管理类
 */
public class NotificationUtility extends ContextWrapper {

    private NotificationManager notificationManager;
    private Notification.Builder notification;

    private Map<String, NotificationChannel> channels;

    private NotificationUtility(Context context) {
        super(context);
        // 在构造方法中，声明NotificationManager对象
        if (null == notificationManager && null != context) {
            notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        if (BaseUtility.isEmpty(channels)) {
            channels = new HashMap<>();
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
    private void createPushNotificationAfter3(final NotificationModel model, final int notificationId) {
        if (model == null || notificationId < 0) return;
        try {
            if (null == notificationManager) {
                notificationManager = (NotificationManager) LibraryConstants.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
            }
            notification = new Notification.Builder(LibraryConstants.getContext());
            notification.setSmallIcon(model.getIcon());
            notification.setLargeIcon(BitmapFactory.decodeResource(LibraryConstants.getContext().getResources(), model.getIcon()));
            notification.setContentTitle(BaseUtility.isEmpty(model.getTitle()) ? LibraryConstants.getContext().getString(R.string.app_name) : model.getTitle());
            notification.setContentText(model.getContent());
            notification.setPriority(model.getPriority());
            notification.setWhen(System.currentTimeMillis());
            notification.setContentIntent(model.getPendingIntent());
            notification.setAutoCancel(model.isAutoCancel());
            notification.setDefaults(model.getDefaults());
            // 小米手机不要使用下面的方式设置，会直接执行intent
            if (AndroidUtility.PhoneType.Xiaomi != AndroidUtility.getPhoneType() && AndroidUtility.PhoneType.Meizu != AndroidUtility.getPhoneType()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    notification.setVisibility(Notification.VISIBILITY_PUBLIC);
                    // 关联PendingIntent
                    notification.setFullScreenIntent(model.getPendingIntent(), true);
                }
            }
            notificationManager.notify(notificationId, notification.build());
            // 如果是vivo手机，3秒之后关闭弹窗
            if (AndroidUtility.getPhoneType() == AndroidUtility.PhoneType.Vivo) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        notificationManager.cancel(notificationId);
                        notification = new Notification.Builder(LibraryConstants.getContext());
                        notification.setSmallIcon(model.getIcon());
                        notification.setLargeIcon(BitmapFactory.decodeResource(LibraryConstants.getContext().getResources(), model.getIcon()));
                        notification.setContentTitle(BaseUtility.isEmpty(model.getTitle()) ? LibraryConstants.getContext().getString(R.string.app_name) : model.getTitle());
                        notification.setContentText(model.getContent());
                        notification.setPriority(model.getPriority());
                        notification.setWhen(System.currentTimeMillis());
                        notification.setContentIntent(model.getPendingIntent());
                        notification.setAutoCancel(model.isAutoCancel());
                        notificationManager.notify(notificationId, notification.build());
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
    private void createPushNotificationAfter8(NotificationModel model, String channelId, int notificationId) {
        if (null == model || BaseUtility.isEmpty(channelId) || notificationId < 0) return;
        try {
            if (null == notificationManager) {
                notificationManager = (NotificationManager) LibraryConstants.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
            }
            NotificationChannel channel = getChannel(channelId);
            notificationManager.createNotificationChannel(channel);
            notification = new Notification.Builder(LibraryConstants.getContext(), channelId)
                    .setContentTitle(BaseUtility.isEmpty(model.getTitle()) ? LibraryConstants.getContext().getString(R.string.app_name) : model.getTitle())
                    .setContentText(model.getContent())
                    .setSmallIcon(model.getIcon())
                    .setLargeIcon(BitmapFactory.decodeResource(LibraryConstants.getContext().getResources(), model.getLargeIcon()))
//                    .setColor()
                    .setWhen(System.currentTimeMillis())
                    .setAutoCancel(model.isAutoCancel())
                    .setPriority(model.getPriority())
                    .setOngoing(false)
                    .setContentIntent(model.getPendingIntent())
                    .setDefaults(model.getDefaults());
            //发送
            notificationManager.notify(notificationId, notification.build());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 显示Notification对象
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void showPushNotification(NotificationModel model, String channelId, int notificationId) {
        if (checkSdkVersionMoreO()) {
            createPushNotificationAfter8(model, channelId, notificationId);
        } else {
            createPushNotificationAfter3(model, notificationId);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createChannel(String channelId, String channelName, int importance) {
        if (BaseUtility.isEmpty(channelId) || BaseUtility.isEmpty(channelName) || importance < 0)
            return;
        if (BaseUtility.isEmpty(channels)) {
            channels = new HashMap<>();
        }
        NotificationChannel channel;
        try {
            if (BaseUtility.contains(channels, channelId)) {
                channel = channels.get(channelId);
                if (BaseUtility.equals(channel.getName().toString(), channelName) && channel.getImportance() == importance) {
                    return;
                }
            }
            channel = new NotificationChannel(channelId, channelName, importance);
            channels.put(channelId, channel);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public NotificationChannel getChannel(String channelId) {
        if (BaseUtility.isEmpty(channelId) || BaseUtility.isEmpty(channels)) return null;
        try {
            if (!BaseUtility.contains(channels, channelId)) return null;
            return channels.get(channelId);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<NotificationChannel> getChannels() {
        if (BaseUtility.isEmpty(channels)) return null;
        List<NotificationChannel> channelList = new ArrayList<>();
        try {
            Set<String> channelIds = channels.keySet();
            for (String key : channelIds) {
                if (BaseUtility.isEmpty(key) || channels.get(key) == null) continue;
                channelList.add(channels.get(key));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return channelList;
    }

    /**
     * 清除Notification对象
     */
    public void clearPushNotification(int notification) {
        if (notificationManager != null) {
            notificationManager.cancel(notification);
        }
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
