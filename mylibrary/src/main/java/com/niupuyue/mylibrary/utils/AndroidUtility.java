package com.niupuyue.mylibrary.utils;

import android.os.Build;
import android.util.Log;

import java.lang.reflect.Method;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019/6/13
 * Time: 23:17
 * Desc: 获取手机基本信息
 * Version:
 */
public class AndroidUtility {

    private static String TAG = AndroidUtility.class.getSimpleName();

    // 手机品牌
    public enum PhoneType {
        Huawei(1),
        Xiaomi(2),
        Oppo(3),
        Vivo(4),
        Meizu(5),
        Oneplus(6),
        Samsung(7),
        Other(0);

        public int value;

        PhoneType(int value) {
            this.value = value;
        }

    }

    /**
     * 获取当前手机的品牌
     *
     * @return
     */
    public static PhoneType getPhoneType() {
        if (!BaseUtility.isEmpty(getEmuiVersion())) {
            Log.e(TAG, "当前手机是：华为");
            return PhoneType.Huawei;
        } else if (BaseUtility.equalsIgnoreCase(Build.MANUFACTURER, "xiaomi")) {
            Log.e(TAG, "当前手机是：小米");
            return PhoneType.Xiaomi;
        } else if (isFlyme()) {
            Log.e(TAG, "当前手机是：魅族");
            return PhoneType.Meizu;
        } else if (isViVo()) {
            Log.e(TAG, "当前手机是：Vivo");
            return PhoneType.Vivo;
        } else if (isOppo()) {
            Log.e(TAG, "当前手机是：Oppo");
            return PhoneType.Oppo;
        } else if (isSamsung()) {
            Log.e(TAG, "当前手机是：三星");
            return PhoneType.Samsung;
        } else {
            Log.e(TAG, "当前手机是：其他");
            return PhoneType.Other;
        }
    }

    /**
     * 返回是否是EMUI版本
     *
     * @return
     */
    private static String getEmuiVersion() {
        String emuiVersion = "";
        Class<?>[] clsArray = new Class[]{String.class};
        Object[] objArray = new Object[]{"ro.build.version.emui"};
        try {
            Class<?> systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method get = systemPropertiesClass.getDeclaredMethod("get", clsArray);
            String version = (String) get.invoke(systemPropertiesClass, objArray);
            if (!BaseUtility.isEmpty(version)) {
                return version;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return emuiVersion;
    }

    /**
     * 判断是否是魅族系统
     */
    private static boolean isFlyme() {
        try {
            // Invoke Build.hasSmartBar()
            final Method method = Build.class.getMethod("hasSmartBar");
            return method != null;
        } catch (final Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断是否是Vivo系统
     */
    public static boolean isViVo() {
        try {
            return android.os.Build.BRAND.toLowerCase().contains("vivo");
        } catch (final Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断是否是oppo手机
     *
     * @return
     */
    public static boolean isOppo() {
        try {
            return android.os.Build.BRAND.toLowerCase().contains("oppo");
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * 判断是否是三星手机
     *
     * @return
     */
    public static boolean isSamsung() {
        try {
            return android.os.Build.BRAND.toLowerCase().contains("samsung");
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

}
