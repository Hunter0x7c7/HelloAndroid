package com.hunter.helloandroid.module.rocket;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.hunter.helloandroid.util.SystemUtil;

/**
 * 创建日期： 2017/12/1 14:22
 * 描    述：WindowManager
 */
public class WinManagerUtil implements View.OnTouchListener {

    private static WinManagerUtil mInstance;
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams params;
    private int startX;
    private int startY;
    private View view;

    private WinManagerUtil() {
    }

    public static WinManagerUtil getInstance() {
        if (mInstance == null) {
            synchronized (WinManagerUtil.class) {
                mInstance = new WinManagerUtil();
            }
        }
        return mInstance;
    }

    public void setup(Context context, View view) {
        setup(context, view, -1, -1);
    }

    public void setup(Context context, View view, int x, int y) {

        if (this.view == view) {
            remove(this.view);
        }
        this.view = view;

        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        params = new WindowManager.LayoutParams();
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.format = PixelFormat.TRANSLUCENT;
        params.type = WindowManager.LayoutParams.TYPE_APPLICATION;
        params.gravity = Gravity.START + Gravity.TOP;

        params.x = x > 0 ? x : 0;
        params.y = y > 0 ? y : (SystemUtil.getHeight() - view.getHeight()) * 2 / 5;

        // 设置view的触摸事件，让它可以被拖拽
        view.setOnTouchListener(this);
        mWindowManager.addView(view, params);
    }

    public void remove(View view) {
        try {
            mWindowManager.removeView(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addOnTouchListener(ViewGroup view, MotionEvent event) {
        onTouch(view, event);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            try {
                View view = (View) msg.obj;
                Bundle data = msg.getData();
                WindowManager.LayoutParams params = data.getParcelable("params");
                params.x = msg.arg1;
                mWindowManager.updateViewLayout(view, params);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    // 发射靠边
    private void sendRocket(View view, int rawX, WindowManager.LayoutParams params) {
        int winWidth = SystemUtil.getWidth();
        if (rawX <= winWidth / 2) {
            sendLeftRocket(view, params);
        } else {
            sendRightRocket(view, params, winWidth);
        }
    }

    private void sendLeftRocket(final View view, final WindowManager.LayoutParams params) {
        // 用子线程更新y轴
        new Thread(new Runnable() {

            @Override
            public void run() {
//                float off = x / 100.f;
                int x = params.x;
                for (int i = x; i >= 1; i--) {
//                for (int i = 100; i >= 0; i--) {
//                    msg.arg1 = (int) (x - off * (100 - i));
                    Message msg = Message.obtain();
                    Bundle data = new Bundle();
                    data.putParcelable("params", params);
                    msg.setData(data);
                    msg.arg1 = (int) interpolator(i, x);
                    msg.obj = view;
                    mHandler.sendMessage(msg);

                    //休眠
                    if (i % 2 == 0)
                        SystemClock.sleep(1);
                }
            }
        }).start();
    }

    private void sendRightRocket(final View view, final WindowManager.LayoutParams params
            , final int winWidth) {

        // 用子线程更新y轴
        new Thread(new Runnable() {

            @Override
            public void run() {
                int x = params.x;
                int pos = winWidth - x;
//                float off = pos / 100.f;
                for (int i = 1; i <= pos; i++) {
//                    msg.arg1 = (int) (x + off * i);
                    Message msg = Message.obtain();
                    Bundle data = new Bundle();
                    data.putParcelable("params", params);
                    msg.setData(data);
                    msg.arg1 = (int) (x + interpolator(i, pos));
                    msg.obj = view;
                    mHandler.sendMessage(msg);

                    //休眠
                    if (i % 2 == 0)
                        SystemClock.sleep(1);
                }
            }
        }).start();
    }

    private float interpolator(int i, float count) {
        return getInterpolation(i / count) * count;
    }

    //加速减速插值器
    private float getInterpolation(float input) {
        return (float) (Math.cos((input + 1) * Math.PI) / 2.0f) + 0.5f;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = (int) event.getRawX();
                startY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = (int) (event.getRawX() - startX);
                int dy = (int) (event.getRawY() - startY);

                try {
                    params = (WindowManager.LayoutParams) view.getLayoutParams();

                    // 更新浮窗位置
                    params.x += dx;
                    params.y += dy;
                    // 限制窗口坐标不超过屏幕
                    if (params.x < 0) {
                        params.x = 0;
                    }
                    int winWidth = SystemUtil.getWidth();
                    int winHeight = SystemUtil.getHeight();

                    if (params.x > winWidth - view.getWidth()) {
                        params.x = winWidth - view.getWidth();
                    }
                    if (params.y < 0) {
                        params.y = 0;
                    }
                    if (params.y > winHeight - view.getHeight()) {
                        params.y = winHeight - view.getHeight();
                    }

                    mWindowManager.updateViewLayout(view, params);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                startX = (int) event.getRawX();
                startY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                // 手指抬起起，是否需要靠边
                if (view.getTag() instanceof Boolean) {
                    if ((boolean) view.getTag()) {
                        sendRocket(view, startX, params);
                    }
                } else {
                    sendRocket(view, startX, params);
                }
                break;
        }
        return true;
    }
}
