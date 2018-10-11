package com.hunter.zxingdemo.util;


import android.content.Context;

import com.hunter.zxingdemo.base.BaseApplication;


/**
 * dip和px单位之间互相转换
 *
 * @author Zihang Huang
 *         create date 2017/11/17 16:54
 */
public class DensityUtil {
    /**
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
     */
    public static int dip2px(float dpValue) {
        Context context = BaseApplication.getContext();
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(float pxValue) {
        Context context = BaseApplication.getContext();
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
