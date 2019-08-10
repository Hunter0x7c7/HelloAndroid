package com.hunter.helloandroid.module.update.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.support.annotation.StringRes;
import android.view.WindowManager;

import com.hunter.helloandroid.base.BaseApplication;
import com.hunter.helloandroid.util.DirectoryUtil;

import java.lang.reflect.Field;


/**
 * ================================================================
 * <p>
 * 版    权： HunterHuang(c)2018
 * <p>
 * 作    者： Hunter_1125607007@QQ.COM
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2018/3/26 16:50
 * <p>
 * 描    述：
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class SystemUtil {


    /**
     * 输出打印
     *
     * @param str 输出内容
     */
    public static void println(CharSequence str) {
        if (ConfigUtil.isDebugMode())
            systemOutPrintln(str);
    }

    /**
     * 输出打印
     *
     * @param resId 输出内容
     */
    public static void println(@StringRes int resId) {
        println(BaseApplication.getContext().getResources().getString(resId));
    }

    /**
     * 输出打印
     *
     * @param resId 输出内容
     */
    public static void print(@StringRes int resId) {
        print(BaseApplication.getContext().getResources().getString(resId));
    }

    /**
     * 输出打印
     *
     * @param str 输出内容
     */
    public static void print(CharSequence str) {
        if (ConfigUtil.isDebugMode())
            systemOutPrint(str);
    }

    private static void systemOutPrint(CharSequence str) {
        System.out.print(str);
        logWrite(str);
    }

    private static void systemOutPrintln(CharSequence str) {
        System.out.println(str);
        logWrite(str);
    }

    private static void logWrite(CharSequence str) {
        String log = "log";
        String separator = "/";
        String fileType = ".log";
        String datePattern = "yyyy-MM-dd";
        String fileNamePattern = "yyyy-MM-dd_H点";
        String fileName = DateUtil.getToday(fileNamePattern) + fileType;
        String logPath = DirectoryUtil.getDir(log) + separator + DateUtil.getToday(datePattern) + separator + fileName;

        FileUtil.appendText(logPath, "\r\n\r\n" + DateUtil.getToday() + "\r\n" + str);
    }

    /**
     * 获取ActionBar高度 ?attr/actionBarSize
     *
     * @return 返回actionBar高度
     */
    public static float actionbarSize() {

        int[] attrs = {android.R.attr.actionBarSize};
        TypedArray typedArray = BaseApplication.getContext().obtainStyledAttributes(attrs);
        float dimension = typedArray.getDimension(0, 0);
        typedArray.recycle();
        return dimension;
    }

    /**
     * 获取Android状态栏高度
     */
    public static int getStatusBarHeight() {
        Class<?> c;
        Object obj;
        Field field;
        int x, sbar = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = BaseApplication.getContext().getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return sbar;
    }

    /**
     * 获取Android屏幕高度
     */
    public static int getHeight() {
        Context context = BaseApplication.getContext();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();
    }

    /**
     * 获取Android屏幕宽度
     */
    public static int getWidth() {
        Context context = BaseApplication.getContext();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

    /**
     * 返回当前屏幕方向
     */
    public static int getOrientation() {
        Context context = BaseApplication.getContext();
        return context.getResources().getConfiguration().orientation;
    }

    /**
     * 返回当前屏幕是否为竖屏
     *
     * @return 当前屏幕为竖屏时返回true, 否则返回false。
     */
    public static boolean isOrientationPortrait() {
        Context context = BaseApplication.getContext();
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }


    public  static  class  ConfigUtil {
        /**
         * 是否显示调试信息
         */
        public static boolean isDebugMode() {
            return false;
        } /**
         * 获取指定包名程序的版本号
         *
         * @return 返回版本号
         */
        public static int getVersionCode() {
            return getVersionCode(getMyPackageName());
        }

        /**
         * 获取指定包名程序的版本号
         *
         * @param packageName 指定包名
         * @return 返回版本号
         */
        public static int getVersionCode(String packageName) {
            int verCode = -1;
            try {
                Context context = BaseApplication.getContext();
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
                verCode = packageInfo.versionCode;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return verCode;
        }
        /**
         * 获取程序的包名
         */
        public static String getMyPackageName() {
            Context context = BaseApplication.getContext();
            return context.getPackageName();
        }

    }
}
