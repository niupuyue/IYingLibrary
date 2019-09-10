package com.niupuyue.mylibrary.model;

import android.app.PendingIntent;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019/6/21
 * Time: 23:46
 * Desc: 通知栏channel对象
 * Version:
 */
public class NotificationModel {

    private String title;
    private String content;
    private int icon;
    private PendingIntent pendingIntent;
    private int priority;
    private boolean autoCancel;
    private int largeIcon;
    private int defaults;
    private int textColor;

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isAutoCancel() {
        return autoCancel;
    }

    public void setAutoCancel(boolean autoCancel) {
        this.autoCancel = autoCancel;
    }

    public int getLargeIcon() {
        return largeIcon;
    }

    public void setLargeIcon(int largeIcon) {
        this.largeIcon = largeIcon;
    }

    public int getDefaults() {
        return defaults;
    }

    public void setDefaults(int defaults) {
        this.defaults = defaults;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public PendingIntent getPendingIntent() {
        return pendingIntent;
    }

    public void setPendingIntent(PendingIntent pendingIntent) {
        this.pendingIntent = pendingIntent;
    }
}
