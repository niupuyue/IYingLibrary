package com.niupuyue.mylibrary.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import java.lang.reflect.Method;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019/6/12
 * Time: 23:02
 * Desc: 网络环境工具类
 * Version:
 */
public class NetStatusUtility {

    private static String TAG = NetStatusUtility.class.getSimpleName();


    /**
     * 网络类型枚举
     */
    public enum NetType {
        None(1),
        Mobile(2),
        Wifi(4),
        Other(8);

        public int netType;

        NetType(int value) {
            this.netType = value;
        }
    }

    public enum NetWorkType {
        UnKown(-1),
        Wifi(1),
        Net2G(2),
        Net3G(3),
        Net4G(4),
        Net5G(5);

        public int value;

        NetWorkType(int value) {
            this.value = value;
        }

    }

    /**
     * 获取ConnectivityManager对象
     *
     * @param context
     * @return
     */
    public static ConnectivityManager getConnectivityManager(Context context) {
        return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    /**
     * 获取TelephonyManager对象
     *
     * @param context
     * @return
     */
    public static TelephonyManager getTelephonyManager(Context context) {
        return (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    }

    /**
     * 判断网络连接是否有效
     *
     * @param context
     * @return 不管是wifi还是mobile net 只要当前有连接状态，就返回true
     */
    public static boolean isConnected(Context context) {
        NetworkInfo info = getConnectivityManager(context).getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    /**
     * 判断是否有网络正在连接中
     *
     * @param context
     * @return
     */
    public static boolean isConnectedOrConnecting(Context context) {
        NetworkInfo[] infos = getConnectivityManager(context).getAllNetworkInfo();
        if (infos != null) {
            for (NetworkInfo info : infos) {
                if (info.isConnectedOrConnecting()) return true;
            }
        }
        return false;
    }

    /**
     * 获取当前网络连接类型
     *
     * @param context
     * @return
     */
    public static NetType getConnectedType(Context context) {
        NetworkInfo info = getConnectivityManager(context).getActiveNetworkInfo();
        if (info != null) {
            switch (info.getType()) {
                case ConnectivityManager.TYPE_WIFI:
                    return NetType.Wifi;
                case ConnectivityManager.TYPE_MOBILE:
                    return NetType.Mobile;
                default:
                    return NetType.Other;
            }
        }
        return NetType.None;
    }

    /**
     * 判断是否存在有效wifi连接
     *
     * @param context
     * @return
     */
    public static boolean isWifiConnected(Context context) {
        NetworkInfo info = getConnectivityManager(context).getActiveNetworkInfo();
        return info != null && info.getType() == ConnectivityManager.TYPE_WIFI && info.isConnected();
    }

    /**
     * 判断是否存在有效移动网络连接
     *
     * @param context
     * @return
     */
    public static boolean isMobileConnected(Context context) {
        NetworkInfo info = getConnectivityManager(context).getActiveNetworkInfo();
        return info != null && info.getType() == ConnectivityManager.TYPE_MOBILE && info.isConnected();
    }

    /**
     * 判断是否有可用状态的wifi，以下情况之一返回false
     * 1. 设备wifi开关关闭
     * 2. 已经打开飞行模式
     * 3. 设备所在的区域没有信号覆盖
     * 4. 设备在漫游区域，且关闭了网络漫游
     *
     * @param context
     * @return
     */
    public static boolean isWifiAvailable(Context context) {
        NetworkInfo[] infos = getConnectivityManager(context).getAllNetworkInfo();
        if (infos != null && infos.length > 0) {
            for (NetworkInfo info : infos) {
                if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                    return info.isAvailable();
                }
            }
        }
        return false;
    }

    /**
     * 判断有无可用状态的移动网络，注意关掉设备移动网络直接不影响此函数。
     * 也就是即使关掉移动网络，那么移动网络也可能是可用的(彩信等服务)，即返回true。
     * 以下情况它是不可用的，将返回false：
     * 1. 设备打开飞行模式；
     * 2. 设备所在区域没有信号覆盖；
     * 3. 设备在漫游区域，且关闭了网络漫游。
     *
     * @param context
     * @return
     */
    public static boolean isMobileAvailable(Context context) {
        NetworkInfo[] infos = getConnectivityManager(context).getAllNetworkInfo();
        if (infos != null && infos.length > 0) {
            for (NetworkInfo info : infos) {
                if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                    return info.isAvailable();
                }
            }
        }
        return false;
    }

    /**
     * 设备是否打开移动网络开关
     *
     * @param context
     * @return
     */
    public static boolean isMobileEnabled(Context context) {
        try {
            Method getMobileDataEnabledMethod = ConnectivityManager.class.getDeclaredMethod("getMobileDataEnabled");
            getMobileDataEnabledMethod.setAccessible(true);
            return (Boolean) getMobileDataEnabledMethod.invoke(getConnectivityManager(context));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * 检查网络是否为可用状态
     *
     * @param context
     * @return
     */
    public static boolean isNetAvailable(Context context) {
        return isWifiAvailable(context) || (isMobileAvailable(context) && isMobileEnabled(context));
    }

}
