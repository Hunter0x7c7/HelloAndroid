package com.hunter.helloandroid.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * 圆形提示View
 * create date: 2016/8/22 11:35
 */
public class CirclePromptView extends View {

    private Context mContext;
    private Paint mPaint;
    private List<CricleData> mCricleList;
    private int mLineWidth = 3;
    private int mPromptRadius = 5;//默认半径
    private int mWidth, mHeight;
    private int mMaxCount = 4;//在显示的最大光圈数量
    private int mColor = Color.RED;
    private Paint.Style mStyle = Paint.Style.STROKE;
    private float mEnlargeDistance ;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 11:
                    if (mCricleList.size() > 0) {
                        mHandler.sendEmptyMessageDelayed(11, 10);
                        invalidate();
                    }
                    break;
                case 12:
                    if (mCricleList.size() > getMaxCount()) {
                        for (int i = 0; i < mCricleList.size(); i++) {
                            if (mCricleList.size() > getMaxCount()) {
                                mCricleList.remove(0);
                                i--;
                            } else {
                                break;
                            }
                        }
                    }
                    mCricleList.add(new CricleData(255, mPromptRadius));
                    mHandler.sendEmptyMessageDelayed(12, 1100);
                    break;
            }
            return true;
        }
    });

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
        mContext = context;
        if (!isInEditMode()) {
            mLineWidth = DensityUtil.dip2px(context, 1.0f);
            mEnlargeDistance = DensityUtil.dip2px(context, 38f) / 100.0f;
        }
        mPaint = new Paint();
        mCricleList = new ArrayList<>();
        mPaint.setAntiAlias(true);                       //设置画笔为无锯齿
        mPaint.setColor(mColor);                    //设置画笔颜色
        mPaint.setStrokeWidth((float) mLineWidth);              //线宽
        mPaint.setStyle(mStyle);
//        mPaint.setStyle(Paint.Style.STROKE); //空心效果
//        mPaint.setStyle(Paint.Style.FILL); //填满效果

//        startPrompt();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < mCricleList.size(); i++) {
            CricleData cd = mCricleList.get(i);
            int opacity = cd.getAlpha();
            if (opacity <= 0) {
                mCricleList.remove(i);
                i--;
            } else {
                if (mWidth <= 0 && mHeight <= 0) {
                    mWidth = canvas.getWidth() / 2;
                    mHeight = canvas.getHeight() / 2;
                }
                float radius = cd.getRadius();
                mPaint.setAlpha(opacity);
                canvas.drawCircle(mWidth, mHeight, radius, mPaint);           //绘制圆形

                cd.setAlpha((int) (opacity - 3.5f));
                cd.setRadius(radius + mEnlargeDistance);
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
        return mPromptRadius;
    }

    public void setPromptRadius(int promptRadius) {
        this.mPromptRadius = DensityUtil.dip2px(mContext, promptRadius);
        invalidate();
    }

    public int getLineWidth() {
        return mLineWidth;
    }

    public void setLineWidth(int lineWidth) {
        this.mLineWidth = lineWidth;
        initPaint(mContext);
    }

    public int getMaxCount() {
        return mMaxCount;
    }

    public void setMaxCount(int mMaxCount) {
        this.mMaxCount = mMaxCount;
        invalidate();
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int mColor) {
        this.mColor = mColor;
        initPaint(mContext);
    }

    public Paint.Style getStyle() {
        return mStyle;
    }

    public void setStyle(Paint.Style mStyle) {
        this.mStyle = mStyle;
        initPaint(mContext);
    }

    public void setFullStyle(boolean isFull) {
        setStyle(isFull ? Paint.Style.FILL : Paint.Style.STROKE);
    }

    public class CricleData {
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
     * dip和px单位之间互相转换
     *
     * @author Zihang Huang
     *         create date 2017/11/17 16:54
     */
    public static class DensityUtil {
        /**
         * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
         */
        public static int dip2px(Context context, float dpValue) {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (dpValue * scale + 0.5f);
        }

        /**
         * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
         */
        public static int px2dip(Context context, float pxValue) {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (pxValue / scale + 0.5f);
        }
    }

}