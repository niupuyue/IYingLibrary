package com.niupuyue.mylibrary.widgets.chatkeyboard.utils;

import com.danikula.videocache.HttpProxyCacheServer;
import com.renrui.job.application.RRApplication;

import java.io.File;

/**
 * Desc: 语音缓冲初始化工具类
 */
public class UtilityRecordCache {

    private static UtilityRecordCache instance = null;

    private static int MAX_CACHE_FILE_COUNT = 100;
    private static long MAX_CACHE_FILE_STORAGE = 1027 * 1027 * 10;
    private static String AUDIO_CACHE_PATH = "audio_cache";

    private UtilityRecordCache() {

    }

    public static UtilityRecordCache getInstance() {
        if (instance == null) {
            instance = new UtilityRecordCache();
        }
        return instance;
    }

    private static HttpProxyCacheServer proxy;

    /**
     * 初始化语音缓存
     */
    public HttpProxyCacheServer getProxy() {
        return proxy == null ? (proxy = newProxy()) : proxy;
    }

    private HttpProxyCacheServer newProxy() {
        return new HttpProxyCacheServer.Builder(RRApplication.getAppContext())
                .cacheDirectory(new File(RRApplication.getAppContext().getExternalCacheDir(), AUDIO_CACHE_PATH))
                .maxCacheFilesCount(MAX_CACHE_FILE_COUNT)
                .maxCacheSize(MAX_CACHE_FILE_STORAGE)// 最多存储10M的内容
                .build();
    }

}
