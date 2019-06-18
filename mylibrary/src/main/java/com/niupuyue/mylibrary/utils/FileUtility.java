package com.niupuyue.mylibrary.utils;

import android.os.Environment;

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

    public static final int FILE_UTILITY_TYPE_EXTRAL = 0;
    public static final int FILE_UTILITY_TYPE_TEMP = 1;

    // 默认创建文件路径
    public static final String EXTRAL_ROOT_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/IYingLib/caches";

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

    public static File createFile(int type, String fileName, String fileType) {
        String fileParentPath;
        if (BaseUtility.isEmpty(fileType)) return null;
        try {
            if (type == FILE_UTILITY_TYPE_EXTRAL) {

            } else if (type == FILE_UTILITY_TYPE_TEMP) {
                fileParentPath = EXTRAL_ROOT_PATH;
                File file = new File(fileParentPath);
                File audioFile;
                if (!file.exists()) {
                    file.mkdirs();
                }
                if (BaseUtility.isEmpty(fileName)) {
                    fileName = "record_" + System.currentTimeMillis();
                }
                audioFile = File.createTempFile(fileName, "." + fileType, file);
                return audioFile;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


}
