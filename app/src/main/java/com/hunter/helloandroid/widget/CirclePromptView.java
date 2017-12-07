package com.hunter.helloandroid.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import com.hunter.helloandroid.R;

import java.util.ArrayList;
import java.util.List;

public class CirclePromptView extends View {

    private Paint paint;
    private List<CricleData> arrayList;
    private int lineWidth = 3;
    int promptRadius = 200;//默认半径
    private int width;
    private int height;
    private int maxCount = 4;//在显示的最大光圈数量

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 11:

                    if (arrayList.size() > 0) {
                        mHandler.sendEmptyMessageDelayed(11, 10);
                        invalidate();
                    }
                    break;
                case 12:

                    if (arrayList.size() > maxCount) {
                        for (int i = 0; i < arrayList.size(); i++) {
                            if (arrayList.size() > maxCount) {
                                arrayList.remove(0);
                                i--;
                            } else {
                                break;
                            }
                        }
                    }
                    arrayList.add(new CricleData(255, promptRadius));
                    mHandler.sendEmptyMessageDelayed(12, 1100);
                    break;
            }
        }
    };

    public CirclePromptView(Context context) {
        super(context);
        initPaint(context);
    }

    public CirclePromptView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint(context);
    }

    public CirclePromptView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint(context);
    }

    private void initPaint(Context context) {
        if (!isInEditMode()) {
            lineWidth = dip2px(context, 1.0f);
            enlargeDistance = dip2px(context, 58f) / 100.0f;
        }
        paint = new Paint();
        arrayList = new ArrayList<>();
        paint.setAntiAlias(true);                       //设置画笔为无锯齿
        paint.setColor(getResources().getColor(R.color.colorPrimaryDark));                    //设置画笔颜色
        paint.setStrokeWidth((float) lineWidth);              //线宽
        paint.setStyle(Paint.Style.STROKE);                   //空心效果

        startPrompt();
    }

    private float enlargeDistance = 18f;

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < arrayList.size(); i++) {
            CricleData cd = arrayList.get(i);
            int opacity = cd.getAlpha();
            if (opacity <= 0) {
                arrayList.remove(i);
                i--;
            } else {

                if (width <= 0 && height <= 0) {
                    width = canvas.getWidth() / 2;
                    height = canvas.getHeight() / 2;
                }
                float radius = cd.getRadius();
                paint.setAlpha(opacity);
//                paint.setStyle(Paint.Style.FILL);//设置填满
                canvas.drawCircle(width, height, radius, paint);           //绘制圆形

                cd.setAlpha((int) (opacity - 2.5f));
                cd.setRadius(radius + enlargeDistance);
            }
        }
    }

    boolean isStart = false;

    public void startPrompt() {
        if (!isStart) {
            isStart = true;
            mHandler.sendEmptyMessage(12);
            mHandler.sendEmptyMessage(11);
        }
    }

    public void stopPrompt() {
        isStart = false;
        mHandler.removeMessages(12);
    }

    public int getPromptRadius() {
        return promptRadius;
    }

    public void setPromptRadius(int promptRadius) {
        this.promptRadius = promptRadius;
    }

    public int getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(int lineWidth) {

        this.lineWidth = lineWidth;
    }

    class CricleData {
        private int alpha;
        private float radius;

        public CricleData(int alpha, float radius) {
            this.alpha = alpha;
            this.radius = radius;
        }

        public int getAlpha() {
            return alpha;
        }

        public void setAlpha(int alpha) {
            this.alpha = alpha;
        }

        public float getRadius() {
            return radius;
        }

        public void setRadius(float radius) {
            this.radius = radius;
        }
    }

    /**
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}