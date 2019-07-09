package com.niupuyue.mylibrary;

import android.app.Activity;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019-07-09
 * Time: 00:03
 * Desc: Activity管理类
 * Version:
 */
public class AppManager {

    /**
     * 添加Activity
     */
    public void addActivity(Activity activity) {

    }

    public void addActivity(Class<Activity> cls) {

    }

    /**
     * 移除Activity
     */
    public void removeActivity(Activity activity) {

    }

    public void removeActivity(Class<Activity> cls) {

    }

    /**
     * 判断Activity是否存在于集合中
     */
    public boolean isExistActivity(Activity activity) {
        boolean isExist = false;

        return isExist;
    }

    public boolean isExistActivity(Class<Activity> cls) {
        boolean isExist = false;

        return isExist;
    }

    /**
     * 获取栈顶对象
     *
     * @return
     */
    public Activity getTopActivity() {
        Activity topActivity = null;

        return topActivity;
    }

    /**
     * 移除所有的Activity
     */
    public void removeAll() {

    }

}
