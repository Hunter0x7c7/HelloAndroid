package com.hunter.helloandroid.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
/**
 * android自定义进度条，类似微信视频加载
 *
 * @author Zihang Huang
 *         create date： 2017/7/21 8:57
 */
public class LodingCircleView extends View {


    private Paint paintBgCircle;
    private Paint paintCircle;
    private Paint paintProgressCircle;
    private float startAngle = -90f;//开始角度
    private float sweepAngle = 0;//结束
    private int progressCirclePadding = 0;//进度圆与背景圆的间距
    private boolean fillIn = false;//进度圆是否填充
    private int animDuration = 2000;
    private LodingCircleViewAnim mLodingCircleViewAnim;//动画效果
    private
    @ColorInt
    int circleColor;

    public LodingCircleView(Context context) {
        super(context);
        init();
    }

    public LodingCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LodingCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(21)
    public LodingCircleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public int dip2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    private void init() {
        circleColor = Color.parseColor("#44000000");
        progressCirclePadding = dip2px(1);

        mLodingCircleViewAnim = new LodingCircleViewAnim();
        mLodingCircleViewAnim.setDuration(animDuration);

        paintBgCircle = new Paint();
        paintBgCircle.setAntiAlias(true);
        paintBgCircle.setStyle(Paint.Style.STROKE);
        paintBgCircle.setColor(circleColor);

        paintCircle = new Paint();
        paintCircle.setAntiAlias(true);
        paintCircle.setStyle(Paint.Style.FILL);
        paintCircle.setColor(Color.TRANSPARENT);

        paintProgressCircle = new Paint();
        paintProgressCircle.setAntiAlias(true);
        paintProgressCircle.setStyle(Paint.Style.FILL);
        paintProgressCircle.setColor(circleColor);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int cx = getMeasuredWidth() / 2;
        canvas.drawCircle(cx, cx, cx, paintBgCircle);
        canvas.drawCircle(cx, cx, cx - progressCirclePadding / 2, paintCircle);
        int right = getMeasuredWidth() - progressCirclePadding;
        RectF f = new RectF(progressCirclePadding, progressCirclePadding, right, right);
        canvas.drawArc(f, startAngle, sweepAngle, true, paintProgressCircle);
        if (!fillIn) {
            int radius = cx - progressCirclePadding * 2;
            canvas.drawCircle(cx, cx, radius, paintCircle);
        }
    }


    public void startAnimAutomatic(boolean fillIn) {
        this.fillIn = fillIn;
        if (mLodingCircleViewAnim != null)
            clearAnimation();
        startAnimation(mLodingCircleViewAnim);
    }

    public void stopAnimAutomatic() {
        if (mLodingCircleViewAnim != null)
            clearAnimation();
    }


    public void setProgerss(int progerss, boolean fillIn) {
        this.fillIn = fillIn;
        sweepAngle = (float) (360 / 100.0 * progerss);
        invalidate();
    }


    private class LodingCircleViewAnim extends Animation {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            if (interpolatedTime < 1.0f) {
                sweepAngle = 360 * interpolatedTime;
                invalidate();
            } else {
                startAnimAutomatic(fillIn);
            }
        }
    }

    public int getCircleColor() {
        return circleColor;
    }

    public void setCircleColor(@ColorInt int circleColor) {
        this.circleColor = circleColor;
    }
}
