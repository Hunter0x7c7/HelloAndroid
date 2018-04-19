package com.hunter.helloandroid.util;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

import com.hunter.helloandroid.base.BaseApplication;

import java.io.File;

/**
 * ================================================================
 * <p>
 * 版    权：  (c)2018
 * <p>
 * 作    者： Huang zihang
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2018/3/26 16:50
 * <p>
 * 描    述：文件目录工具类
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class DirectoryUtil {

    /**
     * 获取一个文件目录
     */
    public static String getDir(String dir) {
        String path;
        Context context = BaseApplication.getContext();
        if (existsSdcard()) {
            File storageDirectory = Environment.getExternalStorageDirectory();
            path = storageDirectory + "/" + context.getPackageName() + "/" + dir;
            File dirFile = new File(path);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
        } else {
            path = context.getDir(dir, Context.MODE_PRIVATE).getAbsolutePath();
        }
        return path;
    }

    /**
     * 获取一个Private文件目录
     */
    public static String getPrivateDir(String dir) {
        Context context = BaseApplication.getContext();
        File contextDir = context.getDir(dir, Context.MODE_PRIVATE);
        return contextDir.getAbsolutePath();
    }

    /**
     * 是否存在SD卡
     */
    public static boolean existsSdcard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static File getSDCardPath() {
        return Environment.getExternalStorageDirectory();
    }

    public static long getSDCardRemainSize() {
        StatFs statfs = new StatFs(Environment.getExternalStorageDirectory().getPath());
        long blockSize = (long) statfs.getBlockSize();
        long availableBlocks = (long) statfs.getAvailableBlocks();
        return availableBlocks * blockSize;
    }

    public static boolean isSDCardUseable() {
        return Environment.getExternalStorageState().equals("mounted");
    }

}
