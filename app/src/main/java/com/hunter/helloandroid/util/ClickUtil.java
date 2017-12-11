package com.hunter.helloandroid.util;

import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;

/**
 * 创建日期： 2017/11/29 14:13
 * 描    述：模拟点击事件工具类
 */
public class ClickUtil {

    /**
     * 模拟点击事件
     *
     * @param view 需要点击的View
     */
    public static void simulateClick(final View view) {
        simulateClick(view, 50);
    }

    public static void simulateClick(final View view, long delayMillis) {
        //模拟点击按钮
        obtain(view, MotionEvent.ACTION_DOWN);
        view.setPressed(true);
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                obtain(view, MotionEvent.ACTION_UP);
                view.setPressed(false);
            }
        }, delayMillis);
    }

    public static void simulateLongClick(final View view) {
        simulateClick(view, 750);
    }

    private static void obtain(View view, int action) {
        if (view == null) {
            return;
        }
        long time = SystemClock.uptimeMillis();
        int x = view.getLeft() + (view.getWidth() / 2);
        int y = view.getTop() + (view.getHeight() / 2);
        MotionEvent obtain = MotionEvent.obtain(time, time, action, x, y, 0);
        view.dispatchTouchEvent(obtain);
        obtain.recycle();
    }

}
