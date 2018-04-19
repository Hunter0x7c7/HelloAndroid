package com.hunter.helloandroid.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.StringRes;
import android.view.WindowManager;

import com.hunter.helloandroid.base.BaseApplication;

import java.lang.reflect.Field;


/**
 * ================================================================
 * <p>
 * 版    权：  (c)2017
 * <p>
 * 作    者： Huang zihang
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2017/2/28 16:50
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
        if (true)
            System.out.println(str);
    }

    /**
     * 输出打印
     *
     * @param resId 输出内容
     */
    public static void println(@StringRes int resId) {

        println( BaseApplication.getContext().getResources().getString(resId));
    }

    /**
     * 输出打印
     *
     * @param str 输出内容
     */
    public static void print(CharSequence str) {
        if (true)
            System.out.print(str);
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
     * 获取ActionBar高度 ?attr/actionBarSize
     *
     * @return 返回actionBar高度
     */
    public static float actionbarSize() {

        int[] attrs = {android.R.attr.actionBarSize};
        TypedArray actionbarSizeTypedArray = BaseApplication.getContext().obtainStyledAttributes(attrs);
        return actionbarSizeTypedArray.getDimension(0, 0);
    }

    /**
     * 获取Android状态栏高度
     *
     * @return
     */
    public static int getStatusBarHeight() {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, sbar = 0;
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
     *
     * @return
     */
    public static int getHeight() {
        Context context = BaseApplication.getContext();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();
    }

    /**
     * 获取Android屏幕宽度
     *
     * @return
     */
    public static int getWidth() {
        Context context = BaseApplication.getContext();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

}
