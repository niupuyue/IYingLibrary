package com.niupuyue.mylibrary.utils;

import java.io.File;
import java.nio.charset.Charset;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019/6/13
 * Time: 23:52
 * Desc: 文件操作工具类
 * Version:
 */
public class FileUtility {

    private static String TAG = FileUtility.class.getSimpleName();

    public static final long ONE_KB = 1024;
    public static final long ONE_MB = ONE_KB * ONE_KB;
    public static final long ONE_GB = ONE_MB * ONE_MB;

    public static final long FILE_COPY_BUFFER_SIZE = ONE_MB * 30;
    public static final File[] EMPTY_FILE_ARRAY = new File[0];
    public static final Charset UTF8 = Charset.forName("UTF-8");

    //------------------------------------------------------------------

    public static File getFile(File directory, String... names) {
        if (directory == null) {
            throw new NullPointerException("directorydirectory must not be null");
        }
        if (names == null) {
            throw new NullPointerException("names must not be null");
        }
        File file = directory;
        for (String name : names) {
            file = new File(file, name);
        }
        return file;
    }

    public static File getFile(String... names) {
        if (names == null) {
            throw new NullPointerException("names must not be null");
        }
        File file = null;
        for (String name : names) {
            if (file == null) {
                file = new File(name);
            } else {
                file = new File(file, name);
            }
        }
        return file;
    }


}
