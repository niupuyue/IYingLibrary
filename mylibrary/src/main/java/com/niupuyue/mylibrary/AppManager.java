package com.niupuyue.mylibrary;

import android.app.Activity;

import com.niupuyue.mylibrary.utils.BaseUtility;

import java.util.Stack;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019-07-09
 * Time: 00:03
 * Desc: Activity管理类
 * Version:
 */
public class AppManager {

    private AppManager() {
    }

    //静态内部类单例模式
    private static class AppManagerHelper {
        private static final AppManager mAppManager = new AppManager();
    }

    private static Stack<Activity> activityStack;

    /**
     * 添加Activity
     */
    public void addActivity(Activity activity) {
        if (null == activity) return;
        if (BaseUtility.isEmpty(activityStack)) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    /**
     * 移除Activity
     */
    public void removeActivity(Activity activity) {
        if (null == activity) return;
        if (BaseUtility.isEmpty(activityStack)) {
            activityStack = new Stack<>();
        }
        activityStack.remove(activity);
        activity.finish();
        activity = null;
    }

    public void removeActivity(Class<Activity> cls) {
        if (null == cls) return;
        if (BaseUtility.isEmpty(activityStack)) {
            activityStack = new Stack<>();
        }
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                removeActivity(activity);
                break;
            }
        }
    }

    /**
     * 判断Activity是否存在于集合中
     */
    public boolean isExistActivity(Activity activity) {
        boolean isExist = false;
        if (null == activity) return false;
        if (BaseUtility.isEmpty(activityStack)) {
            activityStack = new Stack<>();
        }
        for (Activity act : activityStack) {
            if (act.equals(activity)) {
                isExist = true;
                break;
            }
        }
        return isExist;
    }

    public boolean isExistActivity(Class<Activity> cls) {
        boolean isExist = false;
        if (null == cls) return false;
        if (BaseUtility.isEmpty(activityStack)) {
            activityStack = new Stack<>();
        }
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                isExist = true;
                break;
            }
        }
        return isExist;
    }

    /**
     * 获取栈顶对象
     *
     * @return
     */
    public Activity getTopActivity() {
        Activity topActivity = null;
        if (BaseUtility.isEmpty(activityStack)) {
            activityStack = new Stack<>();
        }
        topActivity = activityStack.lastElement();
        return topActivity;
    }

    /**
     * 移除所有的Activity
     */
    public void removeAll() {
        if (BaseUtility.isEmpty(activityStack)) {
            activityStack = new Stack<>();
        }
        for (Activity activity : activityStack) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

}
