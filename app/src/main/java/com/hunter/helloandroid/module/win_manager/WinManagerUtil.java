package com.hunter.helloandroid.module.win_manager;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.hunter.helloandroid.util.SystemUtil;


/**
 * ================================================================
 * <p>
 * 版    权：  (c)2017
 * <p>
 * 作    者： Huang zihang
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2017/12/1 14:22
 * <p>
 * 描    述：WindowManager
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class WinManagerUtil {

    private static WinManagerUtil mInstance;
    private WindowManager mWindowManager;
    private SparseArray<ViewManager> mViewManagerMap = new SparseArray<>();

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
        setup(context, view, ModeEnum.LAUNCH_MODE_NORMAL);
    }

    public void setup(Context context, View view, int x, int y) {
        setup(context, view, x, y, ModeEnum.LAUNCH_MODE_NORMAL);
    }

    public void setup(Context context, View view, ModeEnum mode) {
        int y = (SystemUtil.getHeight() - view.getHeight()) / 4;
        setup(context, view, 0, y, mode);
    }

    public void setup(Context context, View view, int x, int y, ModeEnum mode) {
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        remove(view);

        ViewManager viewManager = new ViewManager();
        viewManager.setLaunchMode(mode);
        viewManager.setup(view, x, y);
        mViewManagerMap.put(view.hashCode(), viewManager);
    }

    public void remove(View view) {
        if (view != null) {
            int key = view.hashCode();
            ViewManager viewManager = mViewManagerMap.get(key);
            if (viewManager != null) {
                try {
                    mViewManagerMap.remove(key);
                    mWindowManager.removeViewImmediate(view);
                } catch (Exception e1) {
                    try {
                        mWindowManager.removeView(view);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 解决子View消费掉OnTouch事件后，整个View无法移动
     */
    public void addOnTouchListener(ViewGroup view, MotionEvent event) {
        ViewManager viewManager = mViewManagerMap.get(view.hashCode());
        if (viewManager != null)
            viewManager.onTouch(view, event);
    }

    private float interpolator(int i, float count) {
        return getInterpolation(i / count) * count;
    }

    //加速减速插值器
    private float getInterpolation(float input) {
        return (float) (Math.cos((input + 1) * Math.PI) / 2.0f) + 0.5f;
    }

    public void onConfigurationChanged(Configuration newConfig, ViewGroup view) {
        ViewManager viewManager = mViewManagerMap.get(view.hashCode());
        if (viewManager != null) {
            viewManager.onConfigurationChanged(newConfig);
        }

    }

    public interface OnEmbedListener {
        void onUnembed();

        void onEmbed(int arg1);
    }

    public interface OnChildTouchListener {
        void setOnChildTouchListener(View.OnTouchListener onTouchListener);
    }

    public enum ModeEnum {
        //正常
        LAUNCH_MODE_NORMAL
        //靠边
        , LAUNCH_MODE_KEEP_SIDE
        //靠边时一半内嵌入屏幕
        , LAUNCH_MODE_EMBED
        //靠边并且靠边时一半内嵌入屏幕
        , LAUNCH_MODE_KEEP_SIDE_EMBED
    }

    private class ViewManager implements View.OnTouchListener {

        private View view;
        private int startX, startY;
        private WindowManager.LayoutParams params;
        private ModeEnum mLaunchMode = ModeEnum.LAUNCH_MODE_NORMAL;
        private int EMBED_DELAY_MILLIS = 1000 * 3;
        private boolean isEmbed = true;

        private void setup(View view, int x, int y) {
            this.view = view;

            params = new WindowManager.LayoutParams();
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.width = WindowManager.LayoutParams.WRAP_CONTENT;
            params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
            params.format = PixelFormat.TRANSLUCENT;
            params.type = WindowManager.LayoutParams.TYPE_APPLICATION;
            params.gravity = Gravity.START + Gravity.TOP;

            x = x > 0 ? x : 0;
            y = y > 0 ? y : 0;

            int maxWidth = SystemUtil.getWidth() - view.getWidth();
            int maxHeight = SystemUtil.getHeight() - view.getHeight();
            params.x = x > maxWidth ? maxWidth : x;
            params.y = y > maxHeight ? maxHeight : y;

            // 设置view的触摸事件，让它可以被拖拽
            view.setOnTouchListener(this);
            try {
                mWindowManager.addView(view, params);
            } catch (Exception e) {
                e.printStackTrace();
            }
            isSendEmbed();
        }

        private void isSendEmbed() {
            if (isEmbed()) {
                sendEmbedHandler();
            }
        }

        private Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        sendEmbed(msg.arg1);
                        break;
                    default:

                        try {
                            View view = (View) msg.obj;
                            Bundle data = msg.getData();
                            WindowManager.LayoutParams params = data.getParcelable("params");
                            if (params != null) {
                                params.x = msg.arg1;
                                mWindowManager.updateViewLayout(view, params);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        isSendEmbed();
                        break;
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

        private void sendRightRocket(final View view, final WindowManager.LayoutParams params, final int winWidth) {
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

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            if (isEmbed()) {
                sendUnembed();
            }
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
                    onTouchActionUp();
                    break;
            }
            return true;
        }

        private void onTouchActionUp() {
            switch (getLaunchMode()) {
                case LAUNCH_MODE_KEEP_SIDE://保持靠边
                    sendRocket(view, startX, params);
                    break;
                case LAUNCH_MODE_EMBED://靠边时一半内嵌入屏幕
                    sendUnembed();
                    sendEmbedHandler();
                    break;
                case LAUNCH_MODE_KEEP_SIDE_EMBED://靠边并且靠边时一半内嵌入屏幕
                    sendRocket(view, startX, params);

                    sendUnembed();
                    sendEmbedHandler();
                    break;
                case LAUNCH_MODE_NORMAL://正常
                default:
                    break;
            }
        }

        private void sendEmbedHandler() {
            int serviceWidth = SystemUtil.getWidth();
            int maxWidth = serviceWidth - view.getWidth();
            int paramsX = params.x;
            if (paramsX <= 0 || params.x >= maxWidth) {
                mHandler.removeMessages(1);
                Message msg = Message.obtain();
                msg.what = 1;
                msg.arg1 = paramsX <= 0 ? 1 : 2;
                mHandler.sendMessageDelayed(msg, EMBED_DELAY_MILLIS);
            }
        }

        private void sendEmbed(int arg1) {
            if (view instanceof OnEmbedListener) {
                ((OnEmbedListener) view).onEmbed(arg1);
            }
        }

        private void sendUnembed() {
            mHandler.removeMessages(1);
            final int paramsX = params.x;
            final int maxWidth = SystemUtil.getWidth() - view.getWidth();
            if (paramsX <= 0 || params.x >= maxWidth) {
                if (view instanceof OnEmbedListener) {
                    ((OnEmbedListener) view).onUnembed();
                }
            }
        }

        public ModeEnum getLaunchMode() {
            return mLaunchMode;
        }

        public void setLaunchMode(ModeEnum mLaunchMode) {
            this.mLaunchMode = mLaunchMode;

            //靠边
            boolean embed = mLaunchMode == ModeEnum.LAUNCH_MODE_KEEP_SIDE_EMBED
                    || mLaunchMode == ModeEnum.LAUNCH_MODE_EMBED;
            setEmbed(embed);
        }

        public boolean isEmbed() {
            return isEmbed;
        }

        public void setEmbed(boolean embed) {
            isEmbed = embed;
        }

        private void onConfigurationChanged(Configuration newConfig) {
            if (newConfig.orientation != Configuration.ORIENTATION_PORTRAIT) {
                if (params.x >= SystemUtil.getHeight()) {
                    //面板应该在右边
                    SystemUtil.println("onConfigurationChanged.orientation:right");
                }
            }
            onTouchActionUp();
        }
    }

}
