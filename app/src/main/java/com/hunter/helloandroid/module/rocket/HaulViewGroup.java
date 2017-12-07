package com.hunter.helloandroid.module.rocket;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

/**
 * ================================================================
 * <p>
 * 版    权： 上海田韵物联网科技有限公司(c)2017
 * <p>
 * 作    者： 黄自航
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2017/12/1 14:22
 * <p>
 * 描    述：
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class HaulViewGroup extends RelativeLayout {

    private WindowManager mWindowManager;
    private WindowManager.LayoutParams params;
    private View view;
    private int startX;
    private int startY;

    public HaulViewGroup(Context context) {
        super(context);
        init(context);
    }

    public HaulViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HaulViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private Context mContext;

    private void init(Context context) {
        mContext = context;

//        start(context, this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        View childAt = getChildAt(0);
        if (childAt != null) {
            view = childAt;
//            ViewGroup.LayoutParams layoutParams = childAt.getLayoutParams();
            this.removeAllViews();
        }
    }

    public void start() {
        if (view != null) {
            try {
                mWindowManager.removeView(view);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);

        winWidth = mWindowManager.getDefaultDisplay().getWidth();
        winHeight = mWindowManager.getDefaultDisplay().getHeight();

        params = new WindowManager.LayoutParams();
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.format = PixelFormat.TRANSLUCENT;
        params.type = WindowManager.LayoutParams.TYPE_APPLICATION;
        params.gravity = Gravity.START + Gravity.TOP;

        params.x = winWidth;
        params.y = (winHeight - view.getHeight()) / 2;

        // 设置view的触摸事件，让它可以被拖拽
        mWindowManager.addView(view, params);
    }

    public void stop() {
        try {
            mWindowManager.removeView(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            params.y = msg.arg1;
            mWindowManager.updateViewLayout(view, params);
        }
    };
    private int winWidth;
    private int winHeight;

    // 发射火箭
    private void sendRocket() {
        // 用子线程更新y轴
        new Thread(new Runnable() {

            @Override
            public void run() {
                int pos = 1000;
                for (int i = 0; i <= 10; i++) {
                    int y = pos - 100 * i;
                    //休眠100ms发消息
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Message msg = Message.obtain();
                    msg.arg1 = y;
                    mHandler.sendMessage(msg);
                }
            }
        }).start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = (int) event.getRawX();
                startY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = (int) (event.getRawX() - startX);
                int dy = (int) (event.getRawY() - startY);
                // 更新浮窗位置
                params.x += dx;
                params.y += dy;
                // 限制窗口坐标不超过屏幕
                if (params.x < 0) {
                    params.x = 0;
                }
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
                startX = (int) event.getRawX();
                startY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                // 手指抬起起，需要发射火箭，限定发射火箭的范围
//                if (params.x > 0 && params.x < winWidth && params.y > winHeight - 100) {
////                    sendRocket();
//                }
                break;
        }
        return true;
    }


}
