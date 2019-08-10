package com.hunter.acp.util;

import android.util.Log;


/**
 * ================================================================
 * <p>
 * 版    权： HunterHuang(c)2017
 * <p>
 * 作    者： Hunter_1125607007@QQ.COM
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2017/11/15 15:41
 * <p>
 * 描    述：
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class LogUtil {

    private static boolean isDebug = false;

    public LogUtil() {
    }

    public static void debugLog(String tag, String content) {
        if (isDebug) {
            Log.i(tag, content);
        }

    }

    public static void errorLog(String tag, String content) {
        Log.e(tag, content);
    }

    public static void infoLog(String tag, String content) {
        if (isDebug) {
            Log.i(tag, content);
        }

    }

    public static void verboseLog(String tag, String content) {
        if (isDebug) {
            Log.v(tag, content);
        }

    }

    public static void warnLog(String tag, String content) {
        if (isDebug) {
            Log.w(tag, content);
        }

    }

    public static void debugLog(String tag, String content, Exception e) {
        if (isDebug) {
            Log.d(tag, content, e);
        }

    }

    public static void errorLog(String tag, String content, Exception e) {
        Log.e(tag, content, e);
    }

    public static void warnLog(String tag, String content, Exception e) {
        if (isDebug) {
            Log.w(tag, content, e);
        }

    }

    public static void warnLog(String tag, Exception ex) {
        if (isDebug) {
            Log.w(tag, ex);
        }

    }

    public static void d(String tag, String content) {
        if (isDebug) {
            Log.i(tag, content);
        }

    }

    public static void e(String tag, String content) {
        Log.e(tag, content);
    }

    public static void i(String tag, String content) {
        Log.i(tag, content);
    }

    public static void v(String tag, String content) {
        if (isDebug) {
            Log.v(tag, content);
        }

    }

    public static void w(String tag, String content) {
        if (isDebug) {
            Log.w(tag, content);
        }

    }

    public static void d(String tag, String content, Exception e) {
        if (isDebug) {
            Log.d(tag, content, e);
        }

    }

    public static void e(String tag, String content, Exception e) {
        Log.e(tag, content, e);
    }

    public static void w(String tag, String content, Exception e) {
        if (isDebug) {
            Log.w(tag, content, e);
        }

    }

    public static void w(String tag, Exception ex) {
        if (isDebug) {
            Log.w(tag, ex);
        }

    }

    public static String makeLogTag(Class cls) {
        return "Androidpn_" + cls.getSimpleName();
    }

    public static boolean isDebug() {
        return isDebug;
    }

    public static void setDebug(boolean isDebug) {
        LogUtil.isDebug = isDebug;
    }
}
