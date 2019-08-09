package com.niupuyue.mylibrary.utils;

import android.content.Context;
import android.os.Build;
import android.view.WindowManager;

import java.lang.reflect.Method;

/**
 * Coder: niupuyue
 * Date: 2019/8/9
 * Time: 14:15
 * Desc: 刘海屏，水滴屏设配工具类
 * Version:
 */
public class NotchScreenUtility {

    /**
     * 目前的主要方法是获取刘海屏，水滴屏的宽度和高度
     * 目前使用了刘海品的手机厂商是华为，VIVO，OPPO，Xiaomi，而四个厂商都有自己的适配方法
     *
     * VIVO：https://dev.vivo.com.cn/documentCenter/doc/103
     *
     * OPPO：https://open.oppomobile.com/wiki/doc#id=10159
     *
     * 小米：https://dev.mi.com/console/doc/detail?pId=1293
     *
     * 华为：https://developer.huawei.com/consumer/cn/devservice/doc/50114?from=timeline
     *
     */

    /**
     * android的异类屏幕都是在android8.0以上版本
     */
    public static boolean isNotchSupportVersion() {
        int currVersion = Build.VERSION.SDK_INT;
        return currVersion > 26;
    }

    /**
     * 获取手机屏幕旋转角度
     */
    public static int getScreenAngle(Context context) {
        return ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRotation();
    }

    /**
     * 判断手机是否存在刘海屏
     * 需要将四种类型的手机进行统一适配
     */
    public static boolean isNotch(Context context) {
        return isNotchVivo(context) || isNotchOppo(context) || isNotchXiaomi(context) || isNotchHuawei(context);
    }

    /**
     * 判断vivo手机是否存在刘海屏
     */
    public static boolean isNotchVivo(Context context) {
        boolean isNotch = false;
        try {
            ClassLoader loader = context.getClassLoader();
            Class cls = loader.loadClass("android.util.FtFeature");
            Method method = cls.getMethod("isFeatureSupport", int.class);
            //0x00000020：是否有刘海  0x00000008：是否有圆角
            isNotch = (boolean) method.invoke(cls, 0x00000020);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            return isNotch;
        }
    }

    /**
     * 判断Oppo手机是否存在刘海屏
     */
    public static boolean isNotchOppo(Context context) {
        boolean isNotch = false;
        try {
            isNotch = context.getPackageManager().hasSystemFeature("com.oppo.feature.screen.heterromorphism");
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            return isNotch;
        }
    }

    /**
     * 判断华为手机是否存在刘海屏
     */
    public static boolean isNotchHuawei(Context context) {
        boolean isNotch = false;
        try {
            ClassLoader loader = context.getClassLoader();
            Class cls = loader.loadClass("com.huawei.android.util.HwNotchSizeUtil");
            Method method = cls.getMethod("hasNotchInScreen");
            isNotch = (boolean) method.invoke(cls);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            return isNotch;
        }
    }

    /**
     * 判断xiaomi手机是否存在刘海屏
     */
    public static boolean isNotchXiaomi(Context context) {
        boolean isNotch = false;
        try {
            ClassLoader loader = context.getClassLoader();
            Class cls = loader.loadClass("android.os.SystemProperties");
            Method method = cls.getMethod("getInt", String.class, int.class);
            isNotch = ((int) method.invoke(null, "ro.miui.notch", 0) == 1);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            return isNotch;
        }
    }

    /**
     * 获取华为手机刘海屏的宽高
     */
    public static int[] getNotchSizeForHuawei(Context context) {
        int[] notchSize = new int[]{0, 0};
        try {
            ClassLoader loader = context.getClassLoader();
            Class cls = loader.loadClass("com.huawei.android.util.HwNotchSizeUtil");
            Method method = cls.getMethod("getNotchSize");
            notchSize = (int[]) method.invoke(cls);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            return notchSize;
        }
    }

    /**
     * 获取小米手机刘海屏的宽高
     */
    public static int[] getNotchSizeForXiaomi(Context context) {
        int[] notchSize = new int[]{0, 0};
        try {
            if (isNotchXiaomi(context)) {
                int resourceId1 = context.getResources().getIdentifier("notch_width", "dimen", "android");
                if (resourceId1 > 0) {
                    notchSize[0] = context.getResources().getDimensionPixelSize(resourceId1);
                }
                int resourceId2 = context.getResources().getIdentifier("notch_height", "dimen", "android");
                if (resourceId2 > 0) {
                    notchSize[1] = context.getResources().getDimensionPixelSize(resourceId2);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            return notchSize;
        }
    }

    /**
     * 获取Vivo，Oppo手机的刘海屏宽高
     * Vivo官网解释说，刘海屏高度为27dp，状态栏高度为32dp
     * Oppo官网解释说，刘海屏的高度大于为80px
     * 由此我们可以认为，对于Vivo和Oppo手机来说，刘海屏的高度大约是状态栏的高度
     */
    public static int getNotchHeight(Context context) {
        int notchHeight = 0;
        try {
            if (isNotchVivo(context) || isNotchOppo(context)) {
                //若不想采用状态栏高度作为刘海高度或者可以采用官方给出的刘海固定高度：vivo刘海固定高度：27dp（need dp2px）  oppo刘海固定高度：80px
                int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
                if (resourceId > 0) {
                    notchHeight = context.getResources().getDimensionPixelSize(resourceId);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            return notchHeight;
        }
    }

}
