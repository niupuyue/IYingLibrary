package com.niupuyue.mylibrary.https;

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

    private YingHttp() {
        initRetrofit();
    }

    private static YingHttp instance;

    public static YingHttp getInstance() {
        return instance = YingHttpHelper.instance;
    }

    private void initRetrofit() {

    }


    /**
     * //////////////////////////////////////匿名内部类实现单例///////////////////////////////////////////////////////
     */
    public static class YingHttpHelper {
        public static YingHttp instance = new YingHttp();
    }

}
