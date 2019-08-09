package com.niupuyue.mylibrary.https;

import android.os.Environment;
import android.os.Handler;
import android.os.Looper;

import com.niupuyue.mylibrary.utils.BaseUtility;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019/6/14
 * Time: 00:06
 * Desc: Retrofit网络请求类
 * Version:
 */
public class YingHttp {

    private static final String TAG = YingHttp.class.getSimpleName();
    private static final long TIME_OUT = 60 * 3;

    private static YingHttp instance;
    // 防止网络重复请求的tagList
    private ArrayList<String> onesTag;
    // 声明一个handler，完成子线程和主线程之间的交互，主线程中不需要在通过handler回调
    private Handler mDelivery;
    // OkHttpClient
    private OkHttpClient okHttpClient;

    private YingHttp() {
        initRetrofit();
    }

    public static YingHttp getInstance() {
        return instance = YingHttpHelper.instance;
    }

    private void initRetrofit() {
        onesTag = new ArrayList<>();
        mDelivery = new Handler(Looper.getMainLooper());
        okHttpClient = new OkHttpClient.Builder()
                // 设置缓存路径
                .cache(new Cache(new File(Environment.getExternalStorageDirectory() + "iying_cache"), 50 * 1024 * 1024))
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        // 证书认证
                        return true;
                    }
                })
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                // 这里是网上对cookie的封装
                // 如果
                .addInterceptor(null)
                .addNetworkInterceptor(null)
                .build();
    }

    /**
     * //////////////////////////////////////匿名内部类实现单例///////////////////////////////////////////////////////
     */
    public static class YingHttpHelper {
        public static YingHttp instance = new YingHttp();
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    public Handler getDelivery() {
        return mDelivery;
    }

    public ArrayList<String> getOnesTag() {
        return onesTag;
    }


    /**
     * tag取消网络请求
     */
    public void cancleOkhttoTag(String tag) {
        Dispatcher dispatcher = okHttpClient.dispatcher();
        synchronized (dispatcher) {
            // 请求列表里的，取消网络请求
            for (Call call : dispatcher.queuedCalls()) {
                if (BaseUtility.equals(tag, call.request().tag())) {
                    call.cancel();
                }
            }
            // 正在请求网络的，取消网络请求
            for (Call call : dispatcher.runningCalls()) {
                if (BaseUtility.equals(tag, call.request().tag())) {
                    call.cancel();
                }
            }
        }
    }

}
