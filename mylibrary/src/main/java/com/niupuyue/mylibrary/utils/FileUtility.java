package com.niupuyue.mylibrary.utils;

import android.os.Environment;

import java.io.File;
import java.io.FileFilter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

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

    public static long getFileSize(String filepath) {
        if (BaseUtility.isEmpty(filepath)) return -1;
        try {
            File file = new File(filepath);
            if (file != null)
                return getFileSize(file);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    /**
     * 返回多少KB
     *
     * @param file
     * @return
     */
    public static long getFileSize(File file) {
        if (file == null || !file.isFile()) return -1;
        return file.length() / ONE_KB;
    }

    public static List<File> getFileList(String filePath) {
        return getFileList(filePath, null);
    }

    public static List<File> getFileList(String filePath, FileFilter fileFilter) {
        return getFileList(filePath, fileFilter, 2);
    }

    /**
     * 获取路径下的所有文件列表
     *
     * @param filePath   路径
     * @param fileFilter 过滤文件类型
     * @param targetSize 文件大小(大于@param targetSize)
     * @return 当前路径下所有文件列表
     */
    public static List<File> getFileList(String filePath, FileFilter fileFilter, long targetSize) {
        if (BaseUtility.isEmpty(filePath)) return null;
        List<File> fileList = new ArrayList<>();
        File[] files;
        try {
            File fileDirectory = new File(filePath);
            if (fileDirectory == null || !fileDirectory.isDirectory()) return null;
            if (fileFilter != null) {
                files = fileDirectory.listFiles(fileFilter);
            } else {
                files = fileDirectory.listFiles();
            }
            if (BaseUtility.isEmpty(files)) return null;
            for (File file : files) {
                if (file == null) continue;
                fileList.add(file);
            }
            // 排序
            Collections.sort(fileList, new Comparator<File>() {
                @Override
                public int compare(File f1, File f2) {
                    if (f1 == f2)
                        return 0;
                    if (f1.isDirectory() && f2.isFile()) {
                        // 如果是文件夹，放前面，文件放后面
                        return -1;
                    }
                    if (f1.isFile() && f2.isDirectory()) {
                        return 1;
                    }
                    // 按照文件名称排列
                    return f1.getName().compareToIgnoreCase(f2.getName());
                }
            });
            // 过滤文件大小
            Iterator it = fileList.iterator();
            while (it.hasNext()) {
                File file = (File) it.next();
                if (file.isFile()) {
                    // 根据文件大小进行操作
                    long size = getFileSize(file);
                    if (size < targetSize) {
                        it.remove();
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return fileList;
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
